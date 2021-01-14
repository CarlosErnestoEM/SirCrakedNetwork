package com.pollogamer.wrapper.objects;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.BindException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server extends Thread {

    private ServerSocket serversocket;
    private int port;
    private Thread thread;

    public Server(String s, int port) {
        super(s);
        this.port = port;
        format("SYSTEM", "Trying to open a server using the port " + port);
        start();
    }

    public void run() {
        try {
            /*
            Intentando abrir el servidor en el puerto
             */
            serversocket = new ServerSocket(port);
            /*
            Esperando conexiones de todos los servidores en 1 bucle infinito
             */
            format("SYSTEM", "Server openned on port " + port);
            waitConnections();
        } catch (BindException e) {
            format("ERROR", "One proccess has using this port!");
            format("SYSTEM", "Stopping server...");
            System.exit(0);
        } catch (Exception e) {
            e.printStackTrace();
            format("SYSTEM", "Stopping server...");
            System.exit(0);
        }
    }

    public void waitConnections() {
        String password = "noputono";
        thread = new Thread(() -> {
            try {
                while (!serversocket.isClosed()) {
                    Socket socket = serversocket.accept();
                    DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());
                    if (dataInputStream.readUTF().trim().equals(password)) {
                        new McServer(socket, dataInputStream);
                    } else {
                        socket.close();
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        thread.start();
    }

    public McServer getServer(String servername) {
        for (McServer mcServer : McServer.getServers()) {
            if (servername.toLowerCase().equalsIgnoreCase(mcServer.getServerName())) {
                return mcServer;
            }
        }
        return null;
    }

    public void end() {
        try {
            thread.stop();
            serversocket.close();
        } catch (IOException e) {
            //e.printStackTrace();
        }
    }

    public void log(String text) {
        System.out.print(text + "\n");
    }

    public void format(String format, String text) {
        log("[" + format + "] " + text);
    }
}
