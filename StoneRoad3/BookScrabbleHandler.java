package test;


import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Scanner;

public class BookScrabbleHandler implements ClientHandler {

    private PrintWriter out;
    private Scanner in;

    @Override
    public void handleClient(InputStream inFromClient, OutputStream outToClient) {
        in = new Scanner(inFromClient);
        out = new PrintWriter(outToClient);
        String text = in.next();
        String[] query = text.split(",");
        DictionaryManager dm = DictionaryManager.get();
        boolean result;
        if(query[0] == "Q") {
            result = dm.query(Arrays.copyOfRange(query, 1, query.length));
        } else {
            result = dm.challenge(Arrays.copyOfRange(query, 1, query.length));
        }
        out.print(result);
        out.flush();
    }

    @Override
    public void close() {
        in.close();
        out.close();
    }
}
