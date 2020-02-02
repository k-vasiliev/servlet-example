import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.servlet.ServletContainer;

public class EmbeddedJetty {

  private static Server createServer(int port) {
    Server server = new Server(port);

    // config resources, jersey servlet
    ResourceConfig resourceConfig = new ResourceConfig() {{
      packages("resource");
    }};
    ServletContainer container = new ServletContainer(resourceConfig);
    ServletHolder holder = new ServletHolder(container);

    ServletHandler servletHandler = new ServletHandler();
    servletHandler.addServletWithMapping(holder, "/");
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
