package ontos.infovis.pojo;

/**
 * This is a POJO which represents dependency of a component model on server side.
 *
 * @version 1.0
 * @date 15/05/29
 * @author Ye Song
 * @copyright infovis@tu-dresden.de
 */
public class ComponentDependency {
  private String name;
  private String path;


  /**
   * Gets name.
   *
   * @return Value of name.
   */
  public String getName() {
    return name;
  }

  /**
   * Sets new name.
   *
   * @param name New value of name.
   */
  public void setName(String name) {
    this.name = name;
  }

  /**
   * Gets path.
   *
   * @return Value of path.
   */
  public String getPath() {
    return path;
  }

  /**
   * Sets new path.
   *
   * @param path New value of path.
   */
  public void setPath(String path) {
    this.path = path;
  }
}
