package ontos.infovis.pojo;

/**
 * This is a POJO which represents Configuration of a component model on server side.
 * 
 * @author Ye Song
 * @version 1.0
 * @date 15-6-2
 * @copyright infovis@tu-dresden.de
 */
public class ComponentConfig {
  private String query;
  private String location;

  /**
   * Sets new query.
   *
   * @param query New value of query.
   */
  public void setQuery(String query) {
    this.query = query;
  }

  /**
   * Gets query.
   *
   * @return Value of query.
   */
  public String getQuery() {
    return query;
  }

  /**
   * Sets new location.
   *
   * @param location New value of location.
   */
  public void setLocation(String location) {
    this.location = location;
  }

  /**
   * Gets location.
   *
   * @return Value of location.
   */
  public String getLocation() {
    return location;
  }
}
