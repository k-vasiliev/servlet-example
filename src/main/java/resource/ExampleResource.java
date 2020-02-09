package resource;

import entity.ResumeEntity;
import entity.UserEntity;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.dialect.PostgreSQL10Dialect;
import org.hibernate.service.ServiceRegistry;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.Properties;
import java.util.UUID;

@Path("/resume")
public class ExampleResource {

  private static Integer ADMIN_ID = 1;

  @GET
  @Produces(MediaType.APPLICATION_JSON)
  public Response getResumes(@QueryParam(value = "limit") Integer limit) {
    if (limit == null || limit < 1) {
      throw new IllegalArgumentException("Limit is not present");
    }

    Session session = getSessionFactory().getCurrentSession();
    session.beginTransaction();
    List<ResumeEntity> resumes = session
        .createQuery("SELECT r FROM ResumeEntity r", ResumeEntity.class)
        .setMaxResults(limit)
        .list();
    session.getTransaction().commit();

    return Response.ok(resumes).build();

  }

  @GET
  @Path(value = "/{resumeId}")
  @Produces(MediaType.APPLICATION_JSON)
  public Response getResumeById(@PathParam(value = "resumeId") Integer resumeId) {
    Session session = getSessionFactory().getCurrentSession();
    session.beginTransaction();
    ResumeEntity resume = session
        .createQuery("SELECT r FROM ResumeEntity r WHERE r.id = :id", ResumeEntity.class)
        .setParameter("id", resumeId)
        .getSingleResult();
    session.getTransaction().commit();

    if (resume == null) {
      throw new NotFoundException();
    }

    return Response.ok(resume).build();
  }

  @POST
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public Response createResume() {
    Session session = getSessionFactory().getCurrentSession();
    session.beginTransaction();
    var resume = new ResumeEntity("Resume title " + UUID.randomUUID().toString());
    session.save(resume);
    session.getTransaction().commit();

    return Response.ok(resume.getId()).build();
  }

  @POST
  @Path(value = "/{resumeId}/archive")
  public Response archiveResume(@PathParam(value = "resumeId") Integer resumeId) {
    Session session = getSessionFactory().getCurrentSession();
    session.beginTransaction();
    ResumeEntity resume = session
        .createQuery("SELECT r FROM ResumeEntity r WHERE r.id = :id", ResumeEntity.class)
        .setParameter("id", resumeId)
        .getSingleResult();

    if (resume == null) {
      throw new NotFoundException();
    }

    UserEntity user = session.createQuery("SELECT u From UserEntity u WHERE u.id = :id", UserEntity.class)
        .setParameter("id", ADMIN_ID)
        .getSingleResult();

    if (user == null) {
      throw new NotFoundException();
    }

    resume.archive(user);
    user.setResumeArchivedCount(user.getResumeArchivedCount() + 1);
    session.save(user);
    session.save(resume);
    session.getTransaction().commit();

    return Response.ok().build();
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
