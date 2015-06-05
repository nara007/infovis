package ontos.infovis.util;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author Ye Song
 * @description a singleton which represents context of spring framework.
 * @date 15-5-30
 * @copyright infovis@tu-dresden.de
 */
public class ApplicationManager {
  public static ApplicationManager appManager = new ApplicationManager();
  private ApplicationContext springContext;

  /**
   * private constructor to achieve singleton.
   */
  private ApplicationManager() {
	// basic logging with log4j
	org.apache.log4j.BasicConfigurator.configure();
	  
    this.springContext = new ClassPathXmlApplicationContext("beans.xml");
    System.out.println("spring context initialized...\n");
  }

  /**
   * get context of spring framework.
   *
   * @return ApplicationContext context of spring framework
   */
  public ApplicationContext getSpringContext() {
    return this.springContext;
  }
}
