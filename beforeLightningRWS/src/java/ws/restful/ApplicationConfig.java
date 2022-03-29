/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.restful;
import javax.ws.rs.core.Application;
import org.glassfish.jersey.media.multipart.MultiPartFeature;
/**
 *
 * @author Koh Wen Jie
 */
import java.util.Set;

@javax.ws.rs.ApplicationPath("Resources")
public class ApplicationConfig extends Application {

    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> resources = new java.util.HashSet<>();
        addRestResourceClasses(resources);

        resources.add(MultiPartFeature.class);

        return resources;
    }

    private void addRestResourceClasses(Set<Class<?>> resources) {
        resources.add(ws.restful.CorsFilter.class);
        resources.add(ws.restful.EmployeeResource.class);
        resources.add(ws.restful.ForumResource.class);
        resources.add(ws.restful.MemberResource.class);
    }
}
