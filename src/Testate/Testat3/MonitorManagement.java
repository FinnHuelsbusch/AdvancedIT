package Testate.Testat3;

import java.util.HashMap;


public class MonitorManagement {
    private static HashMap<String,Monitor> monitors = new HashMap<>();


    //Getter Methode um den Monitor für eine Datei zu erhalten. Wenn kein Moitor vorhanden ist wird ein neuere
    //erstellt.
    public static synchronized Monitor getMonitor(String filename) {
        Monitor monitor = monitors.get(filename);
        if (monitor != null) {
            return monitor;
        } else {
            monitor = new Monitor(filename);
            monitors.put(filename, monitor);
            return monitor;
        }
    }
}
