import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class SimpleHttpServer {

  public static void main(String[] args) throws Throwable {
    try (ServerSocket serverSocket = new ServerSocket(80)) {
      while (true) {
        try (
            Socket socket = serverSocket.accept();
            InputStream inputStream = socket.getInputStream();
            OutputStream outputStream = socket.getOutputStream();

            // читаем заголовки
            BufferedReader br = new BufferedReader(new InputStreamReader(inputStream))) {
          while (true) {
            String requestInput = br.readLine();
            System.out.println(requestInput);
            if (requestInput == null || requestInput.trim().isEmpty()) {
              break;
            }
          }

          // пишем ответ
          String result = "<html><body><h1>Hello from java stupid server</h1></body></html>";
          outputStream.write(result.getBytes());
          outputStream.flush();
        }
      }
    }
  }
}
