package com.pollogamer.sircrakedserver.wrapper;

import com.pollogamer.SirCrakedCore;
import com.pollogamer.sircrakedserver.packet.packets.PacketDisconnect;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class WrapperConnector {

    private String host;
    private int port;
    private String password;
    private Socket socket;
    private DataOutputStream dataOutputStream;
    private Thread thread;
    private DataInputStream dataInputStream;

    public WrapperConnector() {
        this.host = "localhost";
        this.port = 4575;
        password = "";
        connect();
        new BukkitRunnable() {
            @Override
            public void run() {
                if (PacketDisconnect.canConnect) {
                    if (socket != null) {
                        if (socket.isClosed()) {
                            connect();
                        }
                    } else {
                        connect();
                    }
                }
            }
        }.runTaskTimerAsynchronously(SirCrakedCore.getCore(), 15, 15);
    }

    public void connect() {
        try {
            socket = new Socket(host, port);
            dataOutputStream = new DataOutputStream(socket.getOutputStream());
            dataInputStream = new DataInputStream(socket.getInputStream());
            dataOutputStream.writeUTF(password);
            dataOutputStream.writeUTF(Bukkit.getServerName());
            dataOutputStream.writeUTF("158.69.253.240");
            dataOutputStream.writeInt(Bukkit.getPort());
            format("ServerConnector", "Now connected this server!");
            readText();
            PacketDisconnect.canConnect = false;
        } catch (IOException e) {
        }
    }

    public void sendPacket(String message) {
        try {
            dataOutputStream.writeUTF(message);
            dataOutputStream.flush();
            format("Packet-Manager", "Sended packet to Wrapper");
        } catch (IOException e) {
            format("ERROR", "An error ocurred sending packet to Wrapper...");
        }
    }


    public void readText() {
        thread = new Thread(() -> {
            try {
                while (!(socket.isClosed())) {
                    String text = dataInputStream.readUTF();
                    if (text != null) {
                        String[] command = text.trim().split(" ");
                        SirCrakedCore.getCore().getPacketManager().runPacket(command);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        thread.start();
    }

    public void disconnect() {
        try {
            format("ServerConnector", "Try disconnecting...");
            dataOutputStream.writeUTF("remove");
            dataOutputStream.flush();
            socket.close();
            dataOutputStream.close();
            format("ServerConnector", "Server is disconnected!");
            thread.stop();
        } catch (IOException e) {
        }
    }

    public Socket getSocket() {
        return socket;
    }

    public DataOutputStream getDataOutputStream() {
        return dataOutputStream;
    }

    public Thread getThread() {
        return thread;
    }

    public DataInputStream getDataInputStream() {
        return dataInputStream;
    }

    public void log(String text) {
        Bukkit.getConsoleSender().sendMessage(text);
    }

    public void format(String format, String text) {
        log("[" + format + "] " + text);
    }
}
