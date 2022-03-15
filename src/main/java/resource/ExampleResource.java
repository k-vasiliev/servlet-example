package resource;

import dao.ResumeDao;
import dto.ResumeDto;
import entity.ResumeEntity;
import java.util.List;
import java.util.stream.Collectors;
import javax.inject.Inject;
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
import service.ResumeMapper;
import service.ResumeService;
import service.ResumeValidator;

@Path("/resume")
public class ExampleResource {

  private final ResumeService resumeService;
  private final ResumeDao resumeDao;
  private final ResumeMapper resumeMapper;
  private final ResumeValidator resumeValidator;

  @Inject
  public ExampleResource(ResumeService resumeService,
                         ResumeDao resumeDao,
                         ResumeMapper resumeMapper,
                         ResumeValidator resumeValidator) {
    this.resumeService = resumeService;
    this.resumeDao = resumeDao;
    this.resumeMapper = resumeMapper;
    this.resumeValidator = resumeValidator;
  }

  @GET
  @Produces(MediaType.APPLICATION_JSON)
  public Response getResumes(@QueryParam(value = "limit") Integer limit) {
    if (limit == null || limit < 1) {
      throw new IllegalArgumentException("Limit is not present");
    }
    List<ResumeEntity> resumes = resumeService.getResumes(limit);
    List<ResumeDto> resumesDto = resumes.stream().map(resumeMapper::map).collect(Collectors.toList());

    return Response.ok(resumesDto).build();
  }

  @GET
  @Path(value = "/{resumeId}")
  @Produces(MediaType.APPLICATION_JSON)
  public Response getResumeById(@PathParam(value = "resumeId") Integer resumeId) {
    ResumeEntity resume = resumeDao.getResume(resumeId).orElseThrow(NotFoundException::new);
    return Response.ok(resumeMapper.map(resume)).build();
  }

  @POST
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public Response createResume(ResumeDto resumeDto) {
    resumeValidator.validate(resumeDto);
    Integer resumeId = resumeService.create(resumeDto);
    return Response.ok(resumeId).build();
  }

  @PUT
  @Path(value = "/{resumeId}")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public Response updateResume(ResumeDto resumeDto, @PathParam(value = "resumeId") Integer resumeId) {
    resumeValidator.validate(resumeDto);
    resumeService.update(resumeDto, resumeId);
    return Response.ok().build();
  }

  @POST
  @Path(value = "/{resumeId}/archive")
  public Response archiveResume(@PathParam(value = "resumeId") Integer resumeId) {
    resumeService.archiveResume(resumeId);
    return Response.ok().build();
  }
}
