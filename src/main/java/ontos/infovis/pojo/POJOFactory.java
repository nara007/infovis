package ontos.infovis.pojo;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author Ye Song
 * @description POJOFactory is a singleton which represents context of spring framework
 * @date 15-5-23
 * @copyright infovis@tu-dresden.de
 */
public class POJOFactory {

  public static POJOFactory POJOContext = new POJOFactory();
  private ApplicationContext springContext;

  /**
   * private constructor to achieve singleton.
   */
  private POJOFactory() {
    this.springContext = new ClassPathXmlApplicationContext("beans.xml");
  }

  /**
   * get context of spring framework.
   * 
   * @return ApplicationContext context of spring framework
   */
  public ApplicationContext getSpringContext() {
    return springContext;
  }
}
