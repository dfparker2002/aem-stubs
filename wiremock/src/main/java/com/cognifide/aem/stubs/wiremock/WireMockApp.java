package com.cognifide.aem.stubs.wiremock;

import com.github.tomakehurst.wiremock.client.MappingBuilder;
import com.github.tomakehurst.wiremock.http.AdminRequestHandler;
import com.github.tomakehurst.wiremock.http.StubRequestHandler;
import com.github.tomakehurst.wiremock.servlet.NotImplementedContainer;
import com.github.tomakehurst.wiremock.standalone.MappingsLoader;

public class WireMockApp {

  private final com.github.tomakehurst.wiremock.core.WireMockApp app;

  public WireMockApp(WireMockOptions wiremockOptions) {
    app = new com.github.tomakehurst.wiremock.core.WireMockApp(wiremockOptions,
      new NotImplementedContainer());
  }

  public void mappingFrom(MappingsLoader loader) {
    app.loadMappingsUsing(loader);
  }

  public void stubFor(MappingBuilder mappingBuilder) {
    app.addStubMapping(mappingBuilder.build());
  }

  public StubRequestHandler buildStubRequestHandler() {
    return app.buildStubRequestHandler();
  }

  public AdminRequestHandler buildAdminHandler() {
    return app.buildAdminRequestHandler();
  }
}
