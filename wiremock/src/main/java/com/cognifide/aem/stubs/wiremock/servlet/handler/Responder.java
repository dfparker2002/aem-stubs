package com.cognifide.aem.stubs.wiremock.servlet.handler;

import static com.github.tomakehurst.wiremock.common.Exceptions.throwUnchecked;
import static com.github.tomakehurst.wiremock.servlet.WireMockHttpServletRequestAdapter.ORIGINAL_REQUEST_KEY;

import java.io.IOException;
import java.io.InputStream;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.tomakehurst.wiremock.http.HttpHeader;
import com.github.tomakehurst.wiremock.http.HttpResponder;
import com.github.tomakehurst.wiremock.http.Request;
import com.github.tomakehurst.wiremock.http.Response;
import com.github.tomakehurst.wiremock.verification.LoggedRequest;
import com.google.common.io.ByteStreams;

class Responder implements HttpResponder {

  private static final Logger LOG = LoggerFactory.getLogger(Responder.class);

  private final HttpServletRequest httpRequest;
  private final HttpServletResponse httpResponse;

  public Responder(HttpServletRequest httpRequest,
    HttpServletResponse httpResponse) {
    this.httpRequest = httpRequest;
    this.httpResponse = httpResponse;
  }

  @Override
  @SuppressWarnings("PMD.AvoidCatchingGenericException")
  public void respond(Request request,
    Response response) {
    httpRequest.setAttribute(ORIGINAL_REQUEST_KEY, LoggedRequest.createFrom(request));
    try {
      applyResponse(response, httpResponse);
    } catch (Exception e) {
      throwUnchecked(e);
    }
  }

  @SuppressWarnings("deprecation")
  private void applyResponse(Response response, HttpServletResponse servletResponse)
    throws IOException {
    FaultResponse faultResponse = FaultResponse.fromResponse(response);

    if (faultResponse.hasError()) {
      faultResponse.sendError(servletResponse);
      return;
    }

    if (response.getStatusMessage() == null) {
      servletResponse.setStatus(response.getStatus());
    } else {
      servletResponse.setStatus(response.getStatus(), response.getStatusMessage());
    }

    for (HttpHeader header : response.getHeaders().all()) {
      for (String value : header.values()) {
        servletResponse.addHeader(header.key(), value);
      }
    }

    write(servletResponse, response.getBodyStream());
  }

  @SuppressWarnings("PMD.UseTryWithResources")
  private static void write(HttpServletResponse response, InputStream content) {
    try (ServletOutputStream out = response.getOutputStream()) {
      ByteStreams.copy(content, out);
      out.flush();
    } catch (IOException e) {
      throwUnchecked(e);
    } finally {
      try {
        content.close();
      } catch (IOException e) {
        LOG.error("Cannot write WireMock AEM Stubs response", e);
        // well, we tried
      }
    }
  }
}
