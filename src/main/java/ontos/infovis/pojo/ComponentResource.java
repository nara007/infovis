package ontos.infovis.pojo;

/**
 * This is a POJO which represents Resource of a component model on server side.
 *
 * @version 1.0
 * @date 15/05/29
 * @author Ye Song
 * @copyright infovis@tu-dresden.de
 */
public class ComponentResource {
  private String type;
  private String path;

  /**
   * Gets path.
   *
   * @return Value of path.
   */
  public String getPath() {
    return path;
  }

  /**
   * Gets type.
   *
   * @return Value of type.
   */
  public String getType() {
    return type;
  }

  /**
   * Sets new path.
   *
   * @param path New value of path.
   */
  public void setPath(String path) {
    this.path = path;
  }

  /**
   * Sets new type.
   *
   * @param type New value of type.
   */
  public void setType(String type) {
    this.type = type;
  }
}
