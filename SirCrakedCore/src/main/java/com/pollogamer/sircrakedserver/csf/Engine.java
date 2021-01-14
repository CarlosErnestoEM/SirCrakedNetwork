package com.pollogamer.sircrakedserver.csf;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.core.Filter;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.Logger;
import org.apache.logging.log4j.message.Message;

import java.util.ArrayList;
import java.util.List;

public class Engine {

    List<String> log = new ArrayList<>();

    private int msgHidden;

    public Engine() {
        this.msgHidden = 0;
        log.add("UUID of player");
        log.add("[AntiLaby/INFO]");
        log.add("Version v1_8_R3");
        log.add("logged in!");
        log.add("[HolographicDisplays]");
        log.add("Parameters:");
        log.add("net.minecraft");
        log.add("Creando confi");
        hideConsoleMessages();
    }

    public int getHiddenMessagesCount() {
        return this.msgHidden;
    }

    public void hideConsoleMessages() {
        ((Logger) LogManager.getRootLogger()).addFilter(new Filter() {
            public Filter.Result filter(final LogEvent event) {
                for (String s : log) {
                    if (event.getMessage().toString().contains(s)) {
                        Engine this$0 = Engine.this;
                        Engine.access$2(this$0, this$0.msgHidden + 1);
                        return Filter.Result.DENY;
                    }
                }
                return null;
            }

            public Filter.Result filter(final Logger arg0, final Level arg1, final Marker arg2, final String arg3, final Object... arg4) {
                return null;
            }

            public Filter.Result filter(final Logger arg0, final Level arg1, final Marker arg2, final Object arg3, final Throwable arg4) {
                return null;
            }

            public Filter.Result filter(final Logger arg0, final Level arg1, final Marker arg2, final Message arg3, final Throwable arg4) {
                return null;
            }

            public Filter.Result getOnMatch() {
                return null;
            }

            public Filter.Result getOnMismatch() {
                return null;
            }
        });
    }

    static /* synthetic */ void access$2(final Engine engine, final int msgHidden) {
        engine.msgHidden = msgHidden;
    }
}
