package com.pollogamer.wrapper.objects;

import com.pollogamer.wrapper.Main;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class McServer {

    private static List<McServer> servers = new CopyOnWriteArrayList<>();
    private static List<String> queue = new ArrayList<>();

    private Socket socket;
    private String serverName;
    private String IP;
    private Integer port;
    private DataInputStream dataInputStream;
    private DataOutputStream dataOutputStream;
    private String motd;
    private final int maxPlayers = 0;
    private int players;

    private volatile boolean active;

    public McServer(Socket socket, DataInputStream dataInputStream) {
        this.socket = socket;
        this.active = true;

        try {
            this.dataInputStream = dataInputStream;
            this.dataOutputStream = new DataOutputStream(socket.getOutputStream());
        } catch (Exception e) {
            e.printStackTrace();
        }

        init();
        format("ServerConnector", "Server " + serverName + " is now conected!");
        servers.add(this);
        if (!isBungee()) {
            sendPacketToAllServers("server add " + getServerName());
            Main.getMain().dispatchCommand(("server add " + getServerName()).split(" "));
        } else {
            executeQueue();
        }
        GroupServer.addToAGroup(this);
        readText();
    }

    public String joinArray(String[] array) {
        StringBuilder text = new StringBuilder();
        for (String s : array) {
            text.append(s);
        }

        return text.toString();
    }

    public void sendCommand(String command) {
        sendPacket("command " + command);
    }

    public void stop(boolean removeFromBungee) {
        if (!active) {
            return;
        }

        try {
            format("ServerConnector", "Server " + serverName + " disconnected");
            if (!isBungee()) {
                if (removeFromBungee) {
                    Main.getMain().dispatchCommand(("server remove " + serverName).split(" "));
                }
                sendPacketToAllServers("server remove " + getServerName());
                GroupServer.removeFromGroup(this);
            }
            servers.remove(Main.getMain().getServer().getServer(serverName));

            dataInputStream.close();
            dataOutputStream.close();

            if (!socket.isClosed()) {
                socket.close();
            }

            active = false;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void readText() {
        new Thread(() -> {
            try {
                while (active) {
                    String text = dataInputStream.readUTF();

                    if (text != null) {
                        String[] command = text.trim().split(" ");
                        Main.getMain().getPacketManager().sendPacket(command, this);
                    }
                }
            } catch (IOException e) {
                //e.printStackTrace();
            }
        }).start();
    }

    public void init() {
        try {
            this.serverName = dataInputStream.readUTF();
            this.IP = dataInputStream.readUTF();
            this.port = dataInputStream.readInt();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void disconnectMcServer() {
        if (!active) {
            return;
        }

        try {
            dataOutputStream.writeUTF("disconnect");
        } catch (IOException e) {
            //e.printStackTrace();
        }
    }

    public void sendPacket(String string) {
        try {
            dataOutputStream.writeUTF(string);
            //format("Packet-Manager", "You send packet to " + serverName);
        } catch (Exception e) {
            format("ERROR", "Could send packet " + string);
        }
    }

    public static List<McServer> getBungeeServer() {
        List<McServer> bungees = new ArrayList<>();
        for (McServer mcServer : servers) {
            if (mcServer.getServerName().contains("BungeeCord")) {
                bungees.add(mcServer);
            }
        }
        return bungees;
    }

    public static void sendPacketToBungees(String packet) {
        List<McServer> getBungees = getBungeeServer();
        if (getBungees.size() == 0) {
            format("ERROR", "No available a BungeeCord instance, adding to queue");
            queue.add(packet);
        } else {
            for (McServer getBungee : getBungees) {
                getBungee.sendPacket(packet);
            }
            format("Packet-Manager", "You sended packet to all BungeeCord instances");
        }
    }

    public void sendPacketToAllServers(String packet) {
        List<McServer> servers = getServers();
        for (McServer mcServer : servers) {
            if (!mcServer.isBungee()) {
                mcServer.sendPacket(packet);
            }
        }
    }

    public static void executeQueue() {
        if (queue.size() > 0) {
            List<McServer> bungees = getBungeeServer();
            format("SYSTEM", "Executing queue commands!");
            for (String s : queue) {
                for (McServer bungee : bungees) {
                    bungee.sendPacket(s);
                }
            }
            format("SYSTEM", "Queue cleared! All packets has been send");
            queue.clear();
        }
    }

    public boolean isBungee() {
        return serverName.contains("BungeeCord");
    }

    public String getIP() {
        return IP;
    }

    public Integer getPort() {
        return port;
    }

    public DataOutputStream getDataOutputStream() {
        return dataOutputStream;
    }

    public boolean isActive() {
        return active;
    }

    public static void log(String text) {
        System.out.print(text + "\n");
    }

    public static void format(String format, String text) {
        log("[" + format + "] " + text);
    }

    public static List<McServer> getServers() {
        return servers;
    }

    public Socket getSocket() {
        return socket;
    }

    public String getServerName() {
        return serverName;
    }

    public DataInputStream getDataInputStream() {
        return dataInputStream;
    }

    @Override
    public String toString() {
        return serverName;
    }
}
