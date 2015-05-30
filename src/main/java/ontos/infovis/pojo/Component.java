package ontos.infovis.pojo;


import java.util.List;

/**
 * This is a POJO which represents component model on server side.
 *
 * @version 1.0
 * @date 15/05/29
 * @author Ye Song
 * @copyright infovis@tu-dresden.de
 */
public class Component {
  private String id;
  private String title;
  private String description;
  private String version;
  private String owner;
  private int creation_date;
  private int last_update;
  private String screenshot;
  private List<ComponentDependency> dependencies;
  private List<ComponentResource> resources;


  /**
   * Sets new id.
   *
   * @param id New value of id.
   */
  public void setId(String id) {
    this.id = id;
  }

  /**
   * Sets new creation_date.
   *
   * @param creation_date New value of creation_date.
   */
  public void setCreation_date(int creation_date) {
    this.creation_date = creation_date;
  }

  /**
   * Sets new owner.
   *
   * @param owner New value of owner.
   */
  public void setOwner(String owner) {
    this.owner = owner;
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
   * Sets new last_update.
   *
   * @param last_update New value of last_update.
   */
  public void setLast_update(int last_update) {
    this.last_update = last_update;
  }

  /**
   * Sets new screenshot.
   *
   * @param screenshot New value of screenshot.
   */
  public void setScreenshot(String screenshot) {
    this.screenshot = screenshot;
  }

  /**
   * Gets creation_date.
   *
   * @return Value of creation_date.
   */
  public int getCreation_date() {
    return creation_date;
  }

  /**
   * Sets new resources.
   *
   * @param resources New value of resources.
   */
  public void setResources(List<ComponentResource> resources) {
    this.resources = resources;
  }

  /**
   * Sets new title.
   *
   * @param title New value of title.
   */
  public void setTitle(String title) {
    this.title = title;
  }

  /**
   * Gets owner.
   *
   * @return Value of owner.
   */
  public String getOwner() {
    return owner;
  }

  /**
   * Gets last_update.
   *
   * @return Value of last_update.
   */
  public int getLast_update() {
    return last_update;
  }

  /**
   * Gets screenshot.
   *
   * @return Value of screenshot.
   */
  public String getScreenshot() {
    return screenshot;
  }

  /**
   * Sets new dependencies.
   *
   * @param dependencies New value of dependencies.
   */
  public void setDependencies(List<ComponentDependency> dependencies) {
    this.dependencies = dependencies;
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
   * Gets dependencies.
   *
   * @return Value of dependencies.
   */
  public List<ComponentDependency> getDependencies() {
    return dependencies;
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
   * Sets new description.
   *
   * @param description New value of description.
   */
  public void setDescription(String description) {
    this.description = description;
  }

  /**
   * Gets description.
   *
   * @return Value of description.
   */
  public String getDescription() {
    return description;
  }

  /**
   * Gets resources.
   *
   * @return Value of resources.
   */
  public List<ComponentResource> getResources() {
    return resources;
  }

  /**
   * Gets title.
   *
   * @return Value of title.
   */
  public String getTitle() {
    return title;
  }

  /**
   * override toString.
   *
   * @return String.
   */
  @Override
  public String toString() {
    return "Component{" + "id='" + id + '\'' + ", title='" + title + '\'' + ", description='"
        + description + '\'' + ", version='" + version + '\'' + ", owner='" + owner + '\''
        + ", creation_date=" + creation_date + ", last_update=" + last_update + ", screenshot='"
        + screenshot + '\'' + ", dependencies=" + dependencies + ", resources=" + resources + '}';
  }
}
