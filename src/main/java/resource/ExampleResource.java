package resource;

import entity.ResumeEntity;
import entity.UserEntity;
import java.util.List;
import java.util.Optional;
import java.util.Properties;
import javax.sql.DataSource;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Environment;
import org.hibernate.dialect.PostgreSQL10Dialect;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.web.bind.annotation.RequestParam;

@Path("/resume")
public class ExampleResource {

  private static Integer ADMIN_ID = 1;

  @GET
  @Produces(MediaType.APPLICATION_JSON)
  public Response getResumes(@QueryParam(value = "limit") Integer limit) {
    List<ResumeEntity> resumes = getResumesFromDB(limit);

    return Response.ok(resumes).build();
  }

  @GET
  @Path(value = "/{resumeId}")
  @Produces(MediaType.APPLICATION_JSON)
  public Response getResumeById(@PathParam(value = "resumeId") Integer resumeId) {
    ResumeEntity resume = getResume(resumeId).orElseThrow(() -> new IllegalArgumentException("Resume not found"));

    return Response.ok(resume).build();
  }

  @POST
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public Response createResume(@RequestParam(value = "title") String title, @RequestParam(value = "role") String role) {
    ResumeEntity resume = new ResumeEntity(title, role);
    save(resume);
    return Response.ok(resume.getId()).build();
  }

  @PUT
  @Path(value = "/{resumeId}")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public Response updateResume(@RequestParam(value = "title") String title,
                               @RequestParam(value = "role") String role,
                               @PathParam(value = "resumeId") Integer resumeId) {
    ResumeEntity resume = getResume(resumeId).orElseThrow(() -> new IllegalArgumentException("Resume not found"));

    resume.setRole(role);
    resume.setTitle(title);
    save(resume);
    return Response.ok().build();
  }

  @POST
  @Path(value = "/{resumeId}/archive")
  public Response archiveResume(@PathParam(value = "resumeId") Integer resumeId) {
    ResumeEntity resume = getResume(resumeId).orElseThrow(() -> new IllegalArgumentException("Resume not found"));

    UserEntity user = getUser(ADMIN_ID).orElseThrow(NotFoundException::new);

    resume.archive(user);
    user.setResumeArchivedCount(user.getResumeArchivedCount() + 1);
    save(user);
    save(resume);
    notifyResumeArchived();
    return Response.ok().build();
  }

  public void notifyResumeArchived() {
    System.out.println("Notify from rabbit");
  }

  private Integer save(ResumeEntity resumeEntity) {
    Session session = getSessionFactory().getCurrentSession();
    session.save(resumeEntity);
    return resumeEntity.getId();
  }

  private Integer save(UserEntity user) {
    Session session = getSessionFactory().getCurrentSession();
    session.saveOrUpdate(user);
    return user.getId();
  }

  private List<ResumeEntity> getResumesFromDB(Integer limit) {
    Session session = getSessionFactory().getCurrentSession();
    return session
        .createQuery("SELECT r FROM ResumeEntity r", ResumeEntity.class)
        .setMaxResults(limit)
        .list();
  }

  private Optional<ResumeEntity> getResume(Integer resumeId) {
    return getSessionFactory().getCurrentSession()
        .createQuery("SELECT r FROM ResumeEntity r WHERE r.id = :id", ResumeEntity.class)
        .setParameter("id", resumeId)
        .uniqueResultOptional();
  }


  private Optional<UserEntity> getUser(Integer userId) {
    Session session = getSessionFactory().getCurrentSession();
    UserEntity user = session.createQuery("SELECT u From UserEntity u WHERE u.id = :id", UserEntity.class)
        .setParameter("id", userId)
        .getSingleResult();
    return Optional.of(user);
  }

  private SessionFactory getSessionFactory() {
    return getLocalSessionFactoryBean().getObject();
  }

  private LocalSessionFactoryBean getLocalSessionFactoryBean() {
    LocalSessionFactoryBean localSessionFactoryBean = new LocalSessionFactoryBean();
    localSessionFactoryBean.setDataSource(getDataSource());

    Properties properties = new Properties();
    properties.put(Environment.DIALECT, PostgreSQL10Dialect.class.getName());
    properties.put(Environment.HBM2DDL_AUTO, "update");
    localSessionFactoryBean.setHibernateProperties(properties);
    localSessionFactoryBean.setPackagesToScan("dao");
    localSessionFactoryBean.setAnnotatedClasses(ResumeEntity.class, UserEntity.class);
    return localSessionFactoryBean;
  }

  private DataSource getDataSource() {
    return new DriverManagerDataSource("jdbc:postgresql://localhost:5432/hh", "hh", "hh");
  }
}
