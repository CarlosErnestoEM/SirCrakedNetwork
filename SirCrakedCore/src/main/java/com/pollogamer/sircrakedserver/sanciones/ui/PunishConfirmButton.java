package com.pollogamer.sircrakedserver.sanciones.ui;

import com.minebone.gui.anvil.AnvilGUI;
import com.minebone.gui.anvil.AnvilSlot;
import com.minebone.gui.inventory.GUIButton;
import com.minebone.gui.inventory.GUIPage;
import com.minebone.itemstack.ItemStackBuilder;
import com.pollogamer.SirCrakedCore;
import com.pollogamer.sircrakedserver.sanciones.Punish;
import com.pollogamer.sircrakedserver.sanciones.util.Offense;
import com.pollogamer.sircrakedserver.utils.MessagesController;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PunishConfirmButton implements GUIButton<SirCrakedCore> {

    private ItemStack item;
    private String punishedPlayer;
    private Offense offense;
    private String reason;

    public ItemStack getItem() {
        return item;
    }

    public PunishConfirmButton(ItemStack item, String punishedPlayer, Offense offense, String reason) {
        this.item = item;
        this.punishedPlayer = punishedPlayer;
        this.offense = offense;
        this.reason = reason;
    }

    public void click(GUIPage<SirCrakedCore> page) {
        Player player = page.getPlayer();

        if (reason.equalsIgnoreCase("custom")) {
            AnvilGUI<JavaPlugin> gui = new AnvilGUI<>(page.getPlugin(), page.getPlayer(), event -> {
                if (event.getSlot() == AnvilSlot.OUTPUT) {
                    event.setWillClose(true);
                    event.setWillDestroy(true);
                    reason = event.getOutput();
                    onClick(page.getPlugin().getPunish(), player);
                } else {
                    event.setWillClose(true);
                    event.setWillDestroy(true);
                    new PunishUI(page.getPlugin(), player, punishedPlayer, offense.getType(), false);
                    destroy();
                }
            });
            gui.setSlot(AnvilSlot.INPUT_LEFT, new ItemStackBuilder().setMaterial(Material.PAPER).setName("Razón"));
            player.closeInventory();
            gui.open();
            return;
        }

        onClick(page.getPlugin().getPunish(), player);
    }

    private void onClick(Punish punish, Player player) {
        String message = "&7[&9Castigos&7] &2" + punishedPlayer + " &eha sido ";
        String shortMessage = format("Castigos", punishedPlayer + " ha recibido un castigo.");

        switch (offense.getPuinishmentType()) {
            case BAN:
                punish.ban(punishedPlayer, player, offense.getTime(), reason, true);
                message += "sancionado ";
                if (Bukkit.getPlayer(punishedPlayer) != null) {
                    Bukkit.getPlayer(punishedPlayer).kickPlayer(ChatColor.RED + "Tu estas baneado de este servidor!\n" + ChatColor.GRAY + "Razón: " + ChatColor.GREEN + reason + "\n\n" + ChatColor.YELLOW +
                            "Adquiere tu desbaneo en tienda.sircraked.com\n" + ChatColor.YELLOW + "o apela en SirCraked.com");
                }
                break;
            case MUTE:
                punish.mute(punishedPlayer, player, offense.getTime(), reason, true);
                message += "silenciado ";
                break;
            case SHADOWMUTE:
                break;
            case TEMPBAN:
                punish.ban(punishedPlayer, player, offense.getTime(), reason, false);
                message += "sancionado ";
                if (Bukkit.getPlayer(punishedPlayer) != null) {
                    Bukkit.getPlayer(punishedPlayer).kickPlayer(ChatColor.RED + "Tu estas baneado de este servidor!\n" + ChatColor.GRAY + "Razón: " + ChatColor.GREEN + reason + "\n\n" + ChatColor.YELLOW +
                            "Adquiere tu desbaneo en tienda.sircraked.com\n" + ChatColor.YELLOW + "o apela en SirCraked.com");
                }
                break;
            case TEMPMUTE:
                punish.mute(punishedPlayer, player, offense.getTime(), reason, false);
                message += "silenciado ";
                break;
            case WARNING:
                punish.warn(punishedPlayer, player, reason);
                MessagesController.sendTitle(Bukkit.getPlayer(punishedPlayer), "§c¡Advertencia!", "§e" + reason, 20, 60, 20);
                message += "advertido ";
                break;
        }

        message += "(" + (offense.getTime().equalsIgnoreCase("Permanente") ? "Permanente" : StringUtils.removeEnd(getDifferenceFormat(parseDateDiff(offense.getTime(), true)), " ")) + ") por &c" + player.getName() + "&e. Razón: &6" + reason;
        String finalMessage = ChatColor.translateAlternateColorCodes('&', message);
        Bukkit.getOnlinePlayers().stream().filter(o -> o.hasPermission("sircraked.s")).forEach(o -> o.sendMessage(finalMessage));
        if (Bukkit.getPlayer(punishedPlayer) != null) {
            Bukkit.getPlayer(punishedPlayer).sendMessage(finalMessage);
        }

        for (Player player1 : Bukkit.getOnlinePlayers()) {
            if (player1.getName().equalsIgnoreCase(punishedPlayer) || player1.getName().equalsIgnoreCase(player.getName())) {
                continue;
            }

            player1.sendMessage(shortMessage);
        }

        if (offense == Offense.MISUSE_HELPOP_SEVERITY_4) {
            //punish.getCore().sendPacket(new PacketServerSendCommandToBungee("msgmute " + punishedPlayer));
        }

        player.closeInventory();
        destroy0();
    }

    private void destroy0() {
        item = null;
        offense = null;
        punishedPlayer = null;
        reason = null;
    }

    public String format(String module, String message) {
        return ChatColor.DARK_GRAY + "[" + ChatColor.RED + module + ChatColor.DARK_GRAY + "] » " + ChatColor.GRAY + message;
    }

    public long parseDateDiff(String time, boolean future) {
        Pattern timePattern = Pattern.compile("(?:([0-9]+)\\s*y[a-z]*[,\\s]*)?" + "(?:([0-9]+)\\s*mo[a-z]*[,\\s]*)?" + "(?:([0-9]+)\\s*w[a-z]*[,\\s]*)?" + "(?:([0-9]+)\\s*d[a-z]*[,\\s]*)?" + "(?:([0-9]+)\\s*h[a-z]*[,\\s]*)?" + "(?:([0-9]+)\\s*m[a-z]*[,\\s]*)?" + "(?:([0-9]+)\\s*(?:s[a-z]*)?)?", Pattern.CASE_INSENSITIVE);
        Matcher m = timePattern.matcher(time);
        int years = 0;
        int months = 0;
        int weeks = 0;
        int days = 0;
        int hours = 0;
        int minutes = 0;
        int seconds = 0;
        boolean found = false;
        while (m.find()) {
            if (m.group() == null || m.group().isEmpty()) {
                continue;
            }
            for (int i = 0; i < m.groupCount(); i++) {
                if (m.group(i) != null && !m.group(i).isEmpty()) {
                    found = true;
                    break;
                }
            }
            if (found) {
                if (m.group(1) != null && !m.group(1).isEmpty()) {
                    years = Integer.parseInt(m.group(1));
                }
                if (m.group(2) != null && !m.group(2).isEmpty()) {
                    months = Integer.parseInt(m.group(2));
                }
                if (m.group(3) != null && !m.group(3).isEmpty()) {
                    weeks = Integer.parseInt(m.group(3));
                }
                if (m.group(4) != null && !m.group(4).isEmpty()) {
                    days = Integer.parseInt(m.group(4));
                }
                if (m.group(5) != null && !m.group(5).isEmpty()) {
                    hours = Integer.parseInt(m.group(5));
                }
                if (m.group(6) != null && !m.group(6).isEmpty()) {
                    minutes = Integer.parseInt(m.group(6));
                }
                if (m.group(7) != null && !m.group(7).isEmpty()) {
                    seconds = Integer.parseInt(m.group(7));
                }
                break;
            }
        }
        if (!found) {
            throw new IllegalArgumentException("Illegal Date");
        }

        if (years > 20) {
            throw new IllegalArgumentException("Illegal Date");
        }

        Calendar c = new GregorianCalendar();
        if (years > 0) {
            c.add(Calendar.YEAR, years * (future ? 1 : -1));
        }
        if (months > 0) {
            c.add(Calendar.MONTH, months * (future ? 1 : -1));
        }
        if (weeks > 0) {
            c.add(Calendar.WEEK_OF_YEAR, weeks * (future ? 1 : -1));
        }
        if (days > 0) {
            c.add(Calendar.DAY_OF_MONTH, days * (future ? 1 : -1));
        }
        if (hours > 0) {
            c.add(Calendar.HOUR_OF_DAY, hours * (future ? 1 : -1));
        }
        if (minutes > 0) {
            c.add(Calendar.MINUTE, minutes * (future ? 1 : -1));
        }
        if (seconds > 0) {
            c.add(Calendar.SECOND, seconds * (future ? 1 : -1));
        }
        return c.getTimeInMillis() / 1000L;
    }

    public String getDifferenceFormat(long timestamp) {
        return formatDifference(timestamp - (System.currentTimeMillis() / 1000L));
    }

    public String formatDifference(long time) {
        if (time == 0) {
            return "Nunca";
        }

        long day = java.util.concurrent.TimeUnit.SECONDS.toDays(time);
        long hours = java.util.concurrent.TimeUnit.SECONDS.toHours(time) - (day * 24);
        long minutes = java.util.concurrent.TimeUnit.SECONDS.toMinutes(time) - (java.util.concurrent.TimeUnit.SECONDS.toHours(time) * 60);
        long seconds = java.util.concurrent.TimeUnit.SECONDS.toSeconds(time) - (java.util.concurrent.TimeUnit.SECONDS.toMinutes(time) * 60);

        StringBuilder sb = new StringBuilder();

        if (day > 0) {
            sb.append(day).append(" ").append(day == 1 ? "dia" : "dias").append(" ");
        }

        if (hours > 0) {
            sb.append(hours).append(" ").append(hours == 1 ? "hora" : "horas").append(" ");
        }

        if (minutes > 0) {
            sb.append(minutes).append(" ").append(minutes == 1 ? "minuto" : "minutos").append(" ");
        }

        if (seconds > 0) {
            sb.append(seconds).append(" ").append(seconds == 1 ? "segundo" : "segundos");
        }

        String diff = sb.toString();

        return diff.isEmpty() ? "ahora" : diff;
    }

    @Override
    public void destroy() {

    }
}
