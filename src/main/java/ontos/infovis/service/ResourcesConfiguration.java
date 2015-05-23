package ontos.infovis.service;

import org.glassfish.jersey.server.ResourceConfig;

/**
 * @author Ye Song
 * @description register rest resources.
 * @date 15-5-23
 * @copyright infovis@tu-dresden.de
 */
public class ResourcesConfiguration extends ResourceConfig {

  /**
   * register all rest resources
   */
  public ResourcesConfiguration() {
    register(ComponentResource.class);
  }
}
