package ontos.infovis.pojo;

import java.util.List;

/**
 * This is a POJO which represents composition model on server side.
 * 
 * @author Ye Song
 * @date 15-6-2
 * @copyright infovis@tu-dresden.de
 */
public class Composition {
  private String id;
  private String title;
  private String description;
  private String version;
  private String owner;
  private List<Right> rights;

  private int creation_date;
  private int last_update;
  private String structure;
  private List<ComponentInstance> components;

  /**
   * Sets new version.
   *
   * @param version New value of version.
   */
  public void setVersion(String version) {
    this.version = version;
  }

  /**
   * Sets new rights.
   *
   * @param rights New value of rights.
   */
  public void setRights(List<Right> rights) {
    this.rights = rights;
  }

  /**
   * Sets new structure.
   *
   * @param structure New value of structure.
   */
  public void setStructure(String structure) {
    this.structure = structure;
  }

  /**
   * Gets rights.
   *
   * @return Value of rights.
   */
  public List<Right> getRights() {
    return rights;
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
   * Gets structure.
   *
   * @return Value of structure.
   */
  public String getStructure() {
    return structure;
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
   * Sets new creation_date.
   *
   * @param creation_date New value of creation_date.
   */
  public void setCreation_date(int creation_date) {
    this.creation_date = creation_date;
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
   * Gets last_update.
   *
   * @return Value of last_update.
   */
  public int getLast_update() {
    return last_update;
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
   * Gets title.
   *
   * @return Value of title.
   */
  public String getTitle() {
    return title;
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
   * Gets creation_date.
   *
   * @return Value of creation_date.
   */
  public int getCreation_date() {
    return creation_date;
  }

  /**
   * Gets components.
   *
   * @return Value of components.
   */
  public List<ComponentInstance> getComponents() {
    return components;
  }

  /**
   * Sets new components.
   *
   * @param components New value of components.
   */
  public void setComponents(List<ComponentInstance> components) {
    this.components = components;
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
   * Gets owner.
   *
   * @return Value of owner.
   */
  public String getOwner() {
    return owner;
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
   * override toString.
   *
   * @return String.
   */
  @Override
  public String toString() {
    return "Composition{" + "id='" + id + '\'' + ", title='" + title + '\'' + ", description='"
        + description + '\'' + ", version='" + version + '\'' + ", owner='" + owner + '\''
        + ", rights=" + rights + ", creation_date=" + creation_date + ", last_update="
        + last_update + ", structure='" + structure + '\'' + ", components=" + components + '}';
  }

  /**
   * Sets new owner.
   *
   * @param owner New value of owner.
   */
  public void setOwner(String owner) {
    this.owner = owner;
  }
}
