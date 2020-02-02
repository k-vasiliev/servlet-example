import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletHandler;
import org.eclipse.jetty.servlet.ServletHolder;

public class EmbeddedJetty {

  private static Server createServer(int port) {
    Server server = new Server(port);
    ServletHandler servletHandler = new ServletHandler();
    servletHandler.addServletWithMapping(ExampleServlet.class, "/");
    server.setHandler(servletHandler);
    return server;
  }

  public static void main(String[] args) throws Exception {
    int port = 8080;
    Server server = createServer(port);
    server.start();
    server.join();
  }
}
