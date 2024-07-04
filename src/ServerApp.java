import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashSet;
import java.util.Set;

public class ServerApp {
    private static Set<PrintWriter> clientWriters = new HashSet<>();
    public static void main(String[] args) {
        System.out.println("Started...");
        try(ServerSocket serverSocket = new ServerSocket(8082)){
            while (true){
                new Handler(serverSocket.accept()).start();
            }
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
                synchronized (clientWriters) {
                    clientWriters.add(printWriter);
                }
                String message;
                while ((message = bufferedReader.readLine()) != null) {
                    System.out.println("Received : " + message);
                    synchronized (clientWriters) {
                        for (PrintWriter writer : clientWriters) {
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
                synchronized (clientWriters) {
                    clientWriters.remove(printWriter);
                }
            }
        }
    }

}