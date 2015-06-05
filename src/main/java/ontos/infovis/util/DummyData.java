package ontos.infovis.util;

import java.util.*;

import ontos.infovis.pojo.Component;
import ontos.infovis.pojo.Composition;


/**
 * Dummydata singleton which maintains two containers for CRUD of components and compositions in
 * memory.
 * 
 * @author Natalie Hube
 * @date 15-06-04
 * @copyright infovis@tu-dresden.de
 */

public class DummyData {

  public static DummyData dummyData = new DummyData();

  private DummyData() {
    System.out.println("dummy data instance initialized...");
  }

  private Map<String, Component> componentContainer = new HashMap<String, Component>();
  private List<Composition> compositionContainer = new ArrayList<Composition>();

  /**
   * Add a component into the container
   * 
   * @param cmp
   * @return false if failed,otherwise true
   */
  public boolean addComponent(Component cmp) {

    String key = cmp.getId() + cmp.getVersion();
    if (key == null) {
      System.out.println("key of the component is null !");
      return false;
    } else {
      if (componentContainer.get(key) != null) {
        System.out.println("key of the component already exists!");
        return false;
      } else {
        componentContainer.put(key, cmp);
        return true;
      }
    }
  }

  /**
   * Update a component in the container
   * 
   * @param cmp
   * @return false if failed,otherwise true
   */
  public boolean updateComponent(Component cmp) {

    String key = cmp.getId() + cmp.getVersion();

    if (key == null || componentContainer.get(key) == null) {
      System.out.println("the component does not exist... ");
      return false;
    } else {
      componentContainer.put(key, cmp);
      return true;
    }

  }

  /**
   * get a component in the container
   * 
   * @param uri,version
   * @return false if failed,otherwise true
   */
  public Component getComponent(String uri, String version) {
    String key = uri + version;
    if (key == null) {
      System.out.println("key of the component is null...");
      return null;
    } else {
      if (componentContainer.get(key) == null) {
        System.out.println("component does not exist...");
        return null;
      } else {
        return componentContainer.get(key);
      }
    }
  }

  /**
   * get all available components in the container.
   * 
   * @return ArrayList of available components in the container.
   */
  public List<Component> getAllComponents() {

    List<Component> componentList = new ArrayList<Component>();
    Iterator<String> iterator = componentContainer.keySet().iterator();
    while (iterator.hasNext()) {
      String key = iterator.next();
      componentList.add(componentContainer.get(key));
    }
    return componentList;
  }

  /**
   * delete a specific component in the container.
   * 
   * @param uri
   * @param version
   * @return false if failed,otherwise true
   */
  public boolean deleteComponent(String uri, String version) {

    String key = uri + version;
    if (key == null) {
      System.out.println("key of component is null...");
      return false;
    } else {
      if (componentContainer.get(key) == null) {
        System.out.println("component does not exist...");
        return false;
      } else {
        componentContainer.remove(key);
        return true;
      }
    }
  }
}
