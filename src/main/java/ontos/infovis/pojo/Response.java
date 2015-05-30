package ontos.infovis.pojo;


/**
 * @author Ye Song
 * @description Response represents response from server side.
 * @date 15-5-30
 * @copyright infovis@tu-dresden.de
 */
public class Response {
  private boolean bool;
  private String error;
  private String exception;

  /**
   * Sets new bool.
   *
   * @param bool New value of bool.
   */
  public void setBool(boolean bool) {
    this.bool = bool;
  }

  /**
   * Gets exception.
   *
   * @return Value of exception.
   */
  public String getException() {
    return exception;
  }

  /**
   * Sets new error.
   *
   * @param error New value of error.
   */
  public void setError(String error) {
    this.error = error;
  }

  /**
   * Gets error.
   *
   * @return Value of error.
   */
  public String getError() {
    return error;
  }

  /**
   * Gets bool.
   *
   * @return Value of bool.
   */
  public boolean isBool() {
    return bool;
  }

  /**
   * Sets new exception.
   *
   * @param exception New value of exception.
   */
  public void setException(String exception) {
    this.exception = exception;
  }
}
