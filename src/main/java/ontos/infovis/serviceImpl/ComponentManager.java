package ontos.infovis.serviceImpl;


import ontos.infovis.pojo.Bool;

import java.net.URI;

/**
 * ComponentManager is responsible for CRUD of component. Created by root on 15-5-19.
 */
public class ComponentManager {

  /**
   *
   * @param componentURI uri of component.
   * @return Bool,true if registered successfully,otherwise false.
   */
  public Bool registerComponent(URI componentURI) {
    Bool bool = new Bool();
    bool.setBool(true);
    return bool;
  }

  /**
   *
   * @param componentURI uri of component.
   * @return boolean,true if updated successfully,otherwise false.
   */
  public boolean updateComponent(URI componentURI) {
    return true;
  }

  // public getComponent(identifier:String, version:String):
}
