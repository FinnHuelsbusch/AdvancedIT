package Ports;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class LowPortScanner {

    public static void main(String[] args) {
        String host = "localhost";
        if (args.length > 0) {
            host = args[0];
        }
        for (int i = 0; i < 65536; i++) {
            try {
                Socket s = new Socket(host, i);
                System.out.printf("There is a Server on port %d at host %s%n", i, host);
                s.close();
            } catch (UnknownHostException e) {
                System.err.println(e);
                break;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
