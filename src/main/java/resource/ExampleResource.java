package resource;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/resume")
public class ExampleResource {

  @GET
  @Produces("application/json")
  public Response getSingleResume() {
    List<Resume> resumes = List.of(
        new Resume("Java программист"),
        new Resume("Python developer"),
        new Resume("Js developer"),
        new Resume("Водитель самоката")
    );
    return Response.ok(resumes).build();
  }
}
