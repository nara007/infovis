package ontos.infovis.pojo;

/**
 * This is a POJO which represents Right reference of Composition model on server side.
 *
 * @author Ye Song
 * @version 1.0
 * @date 15-6-2
 * @copyright infovis@tu-dresden.de
 */
public class Right {
  private String user;
  private String right;

  /**
   * Gets right.
   *
   * @return Value of right.
   */
  public String getRight() {
    return right;
  }

  /**
   * Gets user.
   *
   * @return Value of user.
   */
  public String getUser() {
    return user;
  }

  /**
   * Sets new right.
   *
   * @param right New value of right.
   */
  public void setRight(String right) {
    this.right = right;
  }

  /**
   * Sets new user.
   *
   * @param user New value of user.
   */
  public void setUser(String user) {
    this.user = user;
  }
}
