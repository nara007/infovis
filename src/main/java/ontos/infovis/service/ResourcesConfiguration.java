package ontos.infovis.service;

import org.glassfish.jersey.server.ResourceConfig;

/**
 * All rest service must be registered in this class
 *
 * @author Ye Song
 * @date 15-5-23
 * @copyright infovis@tu-dresden.de
 */
public class ResourcesConfiguration extends ResourceConfig {

  /**
   * register all rest resources
   */
  public ResourcesConfiguration() {
    register(ComponentResource.class);
    register(CompositionResource.class);
  }
}
