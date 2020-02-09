package resource;

import entity.ResumeEntity;
import entity.UserEntity;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.dialect.PostgreSQL10Dialect;
import org.hibernate.service.ServiceRegistry;

import javax.ws.rs.Path;
import javax.ws.rs.core.Response;
import java.util.Properties;

@Path("/resume")
public class ExampleResource {

  public Response getResumes(Integer limit) {
    return null;
  }

  public Response getResumeById(Integer id) {
    return null;
  }

  public Response createResume() {
    return null;
  }

  public Response archiveResume(Integer resumeId) {
    return null;
  }

  private SessionFactory getSessionFactory() {
    Configuration configuration = new Configuration();
    Properties settings = new Properties();
    settings.put(Environment.DRIVER, "org.postgresql.Driver");
    settings.put(Environment.URL, "jdbc:postgresql://localhost:5432/hh");
    settings.put(Environment.USER, "hh");
    settings.put(Environment.PASS, "hh");
    settings.put(Environment.DIALECT, PostgreSQL10Dialect.class.getName());
    settings.put(Environment.CURRENT_SESSION_CONTEXT_CLASS, "thread");
    settings.put(Environment.HBM2DDL_AUTO, "update");
    configuration.setProperties(settings);

    configuration.addAnnotatedClass(ResumeEntity.class);
    configuration.addAnnotatedClass(UserEntity.class);

    ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
        .applySettings(configuration.getProperties()).build();
    return configuration.buildSessionFactory(serviceRegistry);
  }
}
