package resource;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

@Path("/")
public class ExampleResource {

  @GET
  @Path(value = "/")
  @Produces("application/json")
  public Response getSingleResume() {
    Resume resume = new Resume("Тестовое резюме");
    return Response.ok(resume).build();
  }
}
