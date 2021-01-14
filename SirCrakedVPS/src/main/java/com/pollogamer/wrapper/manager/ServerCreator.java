package com.pollogamer.wrapper.manager;

import com.pollogamer.wrapper.Main;
import com.pollogamer.wrapper.objects.MinigameServer;
import com.pollogamer.wrapper.utils.FileUtils;

import java.io.File;

public class ServerCreator {

    public static MinigameServer createServer(String svtype) {
        File template = new File("templates" + File.separator + svtype);
        if (FileUtils.existFile(template)) {
            Integer port = Main.getMain().getPortManager().getPort();
            if (port == 0) {
                format("ERROR", "Any ports availables! :( Please wait");
            } else {
                File target = new File("temp" + File.separator + svtype + "-" + port);
                try {
                    FileUtils.copy(template, target);
                    MinigameServer minigameServer = new MinigameServer(target, svtype, port);
                    return minigameServer;
                } catch (Exception e) {
                    format("ERROR", "Could copy the template " + svtype);
                }
            }
        } else {
            format("ERROR", "Template '" + svtype + "' doesnt exist!");
        }
        return null;
    }


    public static void log(String text) {
        System.out.print(text + "\n");
    }

    public static void format(String format, String text) {
        log("[" + format + "] " + text);
    }
}
