import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;

public class ClientApp {
    public static void main(String[] args) {
        try {

            Socket socket = new Socket("localhost", 8082);
            System.out.println("Connected to the server.....");

            InputStream inputStream = socket.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

            String message = bufferedReader.readLine();
            System.out.println("server says.. : " + message);

            socket.close();


        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
