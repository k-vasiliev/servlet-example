package resource;

import dao.ResumeDao;
import dto.ResumeDto;
import entity.ResumeEntity;
import service.ResumeMapper;
import service.ResumeService;

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
import java.util.stream.Collectors;

@Path("/resume")
public class ExampleResource {

  @GET
  @Produces(MediaType.APPLICATION_JSON)
  public Response getResumes(@QueryParam(value = "limit") Integer limit) {
    if (limit == null || limit < 1) {
      throw new IllegalArgumentException("Limit is not present");
    }

    ResumeDao resumeDao = new ResumeDao();
    ResumeMapper resumeMapper = new ResumeMapper();
    List<ResumeEntity> resumes = resumeDao.getResumes(limit);
    List<ResumeDto> resumesDto = resumes.stream().map(resumeMapper::map).collect(Collectors.toList());

    return Response.ok(resumesDto).build();
  }

  @GET
  @Path(value = "/{resumeId}")
  @Produces(MediaType.APPLICATION_JSON)
  public Response getResumeById(@PathParam(value = "resumeId") Integer resumeId) {
    ResumeDao resumeDao = new ResumeDao();
    ResumeMapper resumeMapper = new ResumeMapper();
    ResumeEntity resume = resumeDao.getResume(resumeId).orElseThrow(NotFoundException::new);
    return Response.ok(resumeMapper.map(resume)).build();
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
    ResumeService resumeService = new ResumeService();
    resumeService.archiveResume(resumeId);

    return Response.ok().build();
  }
}
