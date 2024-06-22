package test;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;

public class MyServer {

    private ServerSocket server;
    private int port;

    private ClientHandler ch;

    private PrintWriter out;
    private BufferedReader in;

    private volatile boolean stop;

	public MyServer(int port, ClientHandler ch) {
        this.port = port;
        this.ch = ch;
        this.stop = false;
    }

    private void runServer() {
        try {
            server = new ServerSocket(port);
            server.setSoTimeout(1000);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        while(!stop) {
            try {
                Socket client = server.accept();
                try {
                    ch.handleClient(client.getInputStream(), client.getOutputStream());
                    ch.close();
                    client.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            } catch (SocketTimeoutException e) {
                throw new RuntimeException(e);

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        try {
            server.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void start() {
        new Thread(this::runServer).start();
    }

    public void close() {
        stop = true;
    }
}
