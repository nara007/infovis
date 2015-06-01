package ontos.infovis.pojo;

/**
 * @author Ye Song
 * @description
 * @date 15-6-1
 * @copyright infovis@tu-dresden.de
 */
public class Param {

  private String uri;
  private String version;

  /**
   * Sets new uri.
   *
   * @param uri New value of uri.
   */
  public void setUri(String uri) {
    this.uri = uri;
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
   * Gets uri.
   *
   * @return Value of uri.
   */
  public String getUri() {
    return uri;
  }

  /**
   * Gets version.
   *
   * @return Value of version.
   */
  public String getVersion() {
    return version;
  }
}
