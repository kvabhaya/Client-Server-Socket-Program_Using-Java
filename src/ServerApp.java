import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerApp {
    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(8082);
            System.out.println("Server is up & running. now waiting for a client...");

            Socket socket = serverSocket.accept();
            System.out.println("client connected...");

            OutputStream outputStream = socket.getOutputStream();
            PrintWriter printWriter = new PrintWriter(outputStream, true);

            printWriter.println("Hello client...");

            socket.close();
            serverSocket.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private static class Handler extends Thread {
        private Socket socket;
        private PrintWriter printWriter;
        private BufferedReader bufferedReader;

        public Handler(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {
            try {
                bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                printWriter = new PrintWriter(socket.getOutputStream(), true);
                synchronized (null) {

                }
                String message;
                while ((message = bufferedReader.readLine()) != null) {
                    System.out.println("Received : " + message);
                    synchronized (null) {
                        for (PrintWriter writer : null) {
                            writer.write(message);
                        }
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    socket.close();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                synchronized (null) {

                }
            }
        }
    }

}