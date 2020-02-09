package resource;

import dao.ResumeDao;
import dao.UserDao;
import entity.ResumeEntity;
import entity.UserEntity;

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

    ResumeDao resumeDao = new ResumeDao();
    List<ResumeEntity> resumes = resumeDao.getResumes(limit);

    return Response.ok(resumes).build();
  }

  @GET
  @Path(value = "/{resumeId}")
  @Produces(MediaType.APPLICATION_JSON)
  public Response getResumeById(@PathParam(value = "resumeId") Integer resumeId) {
    ResumeDao resumeDao = new ResumeDao();
    ResumeEntity resume = resumeDao.getResume(resumeId).orElseThrow(NotFoundException::new);
    return Response.ok(resume).build();
  }

  @POST
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public Response createResume() {
    ResumeDao resumeDao = new ResumeDao();
    Integer resumeId = resumeDao.save(new ResumeEntity("Resume title " + UUID.randomUUID().toString()));

    return Response.ok(resumeId).build();
  }

  @POST
  @Path(value = "/{resumeId}/archive")
  public Response archiveResume(@PathParam(value = "resumeId") Integer resumeId) {
    ResumeDao resumeDao = new ResumeDao();
    UserDao userDao = new UserDao();

    ResumeEntity resume = resumeDao.getResume(resumeId).orElseThrow(NotFoundException::new);
    UserEntity user = userDao.getUser(ADMIN_ID).orElseThrow(NotFoundException::new);

    resume.archive(user);
    user.setResumeArchivedCount(user.getResumeArchivedCount() + 1);
    userDao.save(user);
    resumeDao.save(resume);

    return Response.ok().build();
  }
}
