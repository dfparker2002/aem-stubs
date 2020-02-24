package com.cognifide.wiremockonaem.aem.core.groovy;

import static com.cognifide.wiremockonaem.aem.core.Configuration.GROOVY_SCRIPT_LOCATION;

import java.util.List;

import javax.annotation.Nonnull;

import org.apache.sling.api.resource.observation.ResourceChange;
import org.apache.sling.api.resource.observation.ResourceChangeListener;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import com.cognifide.wiremockonaem.aem.core.wiremock.Wiremock;

@Component(
  service = ResourceChangeListener.class,
  property = {
    ResourceChangeListener.PATHS + "=" + GROOVY_SCRIPT_LOCATION,
    ResourceChangeListener.CHANGES + "=" + "REMOVED"
  }
)
public class StubScriptRemoveListener implements ResourceChangeListener {

  @Reference
  private GroovyScriptExecutor scriptExecutor;

  @Reference
  private Wiremock wiremock;

  @Override
  public void onChange(@Nonnull List<ResourceChange> changes) {
    wiremock.clearStubs();
    scriptExecutor.runAllScripts();
  }
}
