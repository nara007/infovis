package ontos.infovis.service;

import org.glassfish.jersey.server.ResourceConfig;

/**
 * Created by root on 15-5-22.
 */
public class ResourcesConfiguration extends ResourceConfig {

    public ResourcesConfiguration() {
        register(ComponentResource.class);
    }
}
