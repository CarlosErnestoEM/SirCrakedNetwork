package com.pollogamer.wrapper.manager;

import com.pollogamer.wrapper.utils.Utils;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class PortManager {

    private List<Integer> availablePorts = new CopyOnWriteArrayList<>();
    private List<Integer> usedPorts = new CopyOnWriteArrayList<>();

    public PortManager() {
        init();
    }

    public void init() {
        for (int i = 27450; i <= 27470; i++) {
            availablePorts.add(i);
        }
    }

    public Integer getPort() {
        Integer port;
        if (availablePorts.size() == 0) {
            port = 0;
        } else {
            port = Utils.getRandomObjectFromList(availablePorts);
            availablePorts.remove(port);
            usedPorts.add(port);
        }
        return port;
    }

    public void putPortAvailable(Integer port) {
        try {
            usedPorts.remove(port);
            availablePorts.add(port);
        } catch (Exception e) {
            format("ERROR", "Port is available");
        }
    }

    public void log(String text) {
        System.out.print(text + "\n");
    }

    public void format(String format, String text) {
        log("[" + format + "] " + text);
    }
}
