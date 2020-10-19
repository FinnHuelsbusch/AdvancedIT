package Ports;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class LowPortScanner {

    public static void main(String[] args) throws UnknownHostException {
        InetAddress adr = InetAddress.getLocalHost();

        for ( int i = 40000; i < 65536; i++ ) {
            try {
                Socket s = new Socket(adr, i);
                System.out.printf("There is a Server on port %d at host %s%n", i, adr);
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
