package ontos.infovis.pojo;

import java.util.List;

/**
 * This is a POJO which represents component instance within Composition model on server side.
 *
 * @author Ye Song
 * @version 1.0
 * @date 15-6-2
 * @copyright infovis@tu-dresden.de
 */
public class ComponentInstance {
  private String id;
  private String instance_id;
  private String version;
//  private List<ComponentConfig> config;

  /**
   * Sets new instance_id.
   *
   * @param instance_id New value of instance_id.
   */
  public void setInstance_id(String instance_id) {
    this.instance_id = instance_id;
  }

  /**
   * Sets new id.
   *
   * @param id New value of id.
   */
  public void setId(String id) {
    this.id = id;
  }

  /**
   * Gets version.
   *
   * @return Value of version.
   */
  public String getVersion() {
    return version;
  }

  /**
   * Gets instance_id.
   *
   * @return Value of instance_id.
   */
  public String getInstance_id() {
    return instance_id;
  }

  /**
   * Gets id.
   *
   * @return Value of id.
   */
  public String getId() {
    return id;
  }

  /**
   * Sets new version.
   *
   * @param version New value of version.
   */
  public void setVersion(String version) {
    this.version = version;
  }

  /**
   * Gets config.
   *
   * @return Value of config.
   */
//  public List<ComponentConfig> getConfig() {
//    return config;
//  }
//
//  /**
//   * Sets new config.
//   *
//   * @param config New value of config.
//   */
//  public void setConfig(List<ComponentConfig> config) {
//    this.config = config;
//  }
}
