package com.pollogamer.sircrakedserver.sanciones;

import com.pollogamer.SirCrakedCore;
import com.pollogamer.sircrakedserver.sanciones.util.BanRecord;
import com.pollogamer.sircrakedserver.sanciones.util.HistoryPage;
import com.pollogamer.sircrakedserver.sanciones.util.MuteRecord;
import com.pollogamer.sircrakedserver.sanciones.util.WarningRecord;
import com.pollogamer.sircrakedserver.sql.HikariSQLManager;
import com.pollogamer.sircrakedserver.utils.TripleEntry;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;

import java.net.InetAddress;
import java.sql.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Punish implements Listener {

    private final static String INSERT_BAN = "INSERT INTO bm_bans VALUES(?, ?, ?, ?, ?, ?, ?)";
    private final static String INSERT_BAN_RECORD = "INSERT INTO bm_ban_records VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?)";
    private final static String INSERT_MUTE = "INSERT INTO bm_mutes VALUES(?, ?, ?, ?, ?, ?, ?)";
    private final static String INSERT_MUTE_RECORD = "INSERT INTO bm_mutes_records VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?)";
    private final static String INSERT_WARNING = "INSERT INTO bm_warnings VALUES(?, ?, ?, ?, ?, ?)";

    private final static String DELETE_BAN = "DELETE FROM bm_bans WHERE ban_id = ?";
    private final static String DELETE_MUTE = "DELETE FROM bm_mutes WHERE mute_id = ?";
    private final static String DELETE_WARNING = "DELETE FROM bm_warnings WHERE warn_id = ?";

    private final static String SELECT_BAN = "SELECT * FROM bm_bans WHERE banned = ?";
    private final static String SELECT_IP = "SELECT * FROM bm_player_ips WHERE ip = ?";
    private final static String SELECT_BAN_RECORD = "SELECT * FROM bm_ban_records WHERE banned = ?";
    private final static String SELECT_MUTE = "SELECT * FROM bm_mutes WHERE muted = ?";
    private final static String SELECT_MUTE_RECORD = "SELECT * FROM bm_mutes_records WHERE muted = ?";
    private final static String SELECT_WARNING = "SELECT * FROM bm_warnings WHERE warned = ?";

    private final SirCrakedCore plugin;
    private final HikariSQLManager punishmysql;

    public Punish(SirCrakedCore plugin) {
        this.plugin = plugin;
        punishmysql = new HikariSQLManager("sanciones");
    }

    public void ban(String banned, CommandSender bannedBy, String expires, String reason, boolean permanent) {
        long secondsNow = System.currentTimeMillis() / 1000L;
        long secondsUntilUnbanExpires = 0;

        if (!permanent) {
            try {
                secondsUntilUnbanExpires = parseDateDiff(expires, true);
            } catch (Exception e) {
                bannedBy.sendMessage(format("Castigos", "Fecha inválida!"));
                return;
            }
        }

        Connection connection = null;
        PreparedStatement banStatement = null;

        try {
            connection = getConnection();
            banStatement = connection.prepareStatement(INSERT_BAN);
            banStatement.setNull(1, Types.INTEGER);
            banStatement.setString(2, banned);
            banStatement.setString(3, bannedBy.getName());
            banStatement.setString(4, reason);
            banStatement.setLong(5, secondsNow);
            banStatement.setLong(6, secondsUntilUnbanExpires);
            banStatement.setString(7, Bukkit.getServerName());
            banStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (banStatement != null) {
                try {
                    banStatement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    public boolean unban(String banned, CommandSender unbannedBy, boolean force) {
        ResultSet query = null;

        Connection connection = null;
        PreparedStatement selectStatement = null;
        PreparedStatement deleteStatement = null;
        PreparedStatement insertStatement = null;
        try {
            connection = getConnection();
            selectStatement = connection.prepareStatement(SELECT_BAN);
            selectStatement.setString(1, banned);
            query = selectStatement.executeQuery();

            if (!query.next()) {
                query.close();
                return false;
            }

            int id = query.getInt("ban_id");
            String bannedBy = query.getString("banned_by");
            String reason = query.getString("ban_reason");
            String server = query.getString("server");
            long banTime = query.getLong("ban_time");
            long banExpiersOn = query.getLong("ban_expires_on");
            long now = System.currentTimeMillis() / 1000L;
            query.close();

            if (banExpiersOn >= now && !force) {
                return false;
            }

            if (banExpiersOn == 0 && !force) {
                return false;
            }

            deleteStatement = connection.prepareStatement(DELETE_BAN);
            deleteStatement.setInt(1, id);
            deleteStatement.execute();

            insertStatement = connection.prepareStatement(INSERT_BAN_RECORD);
            insertStatement.setNull(1, Types.INTEGER);
            insertStatement.setString(2, banned);
            insertStatement.setString(3, bannedBy);
            insertStatement.setString(4, reason);
            insertStatement.setLong(5, banTime);
            insertStatement.setLong(6, banExpiersOn);
            insertStatement.setString(7, unbannedBy.getName());
            insertStatement.setLong(8, now);
            insertStatement.setString(9, server);
            insertStatement.execute();

            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            if (selectStatement != null) {
                try {
                    selectStatement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (deleteStatement != null) {
                try {
                    deleteStatement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (insertStatement != null) {
                try {
                    insertStatement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public String isBanned(String banned, CommandSender unbannedBy) {
        ResultSet query = null;

        Connection connection = null;
        PreparedStatement selectStatement = null;
        try {
            connection = getConnection();
            selectStatement = connection.prepareStatement(SELECT_BAN);
            selectStatement.setString(1, banned);
            query = selectStatement.executeQuery();

            if (!query.next()) {
                query.close();
                return null;
            }

            String reason = query.getString("ban_reason");
            long banExpiersOn = query.getLong("ban_expires_on");
            long now = System.currentTimeMillis() / 1000L;
            query.close();

            if (banExpiersOn == 0) {
                return reason;
            }

            if (banExpiersOn >= now) {
                return reason;
            }

            unban(banned, unbannedBy, true);
            return null;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        } finally {
            if (selectStatement != null) {
                try {
                    selectStatement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public String isBannedIp(long ip, CommandSender unbannedBy) {
        ResultSet query = null;

        Connection connection = null;
        PreparedStatement selectStatement = null;

        ResultSet isBannedquery = null;
        PreparedStatement isBannedSelectStatement = null;
        try {
            connection = getConnection();
            selectStatement = connection.prepareStatement(SELECT_IP);
            selectStatement.setLong(1, ip);
            query = selectStatement.executeQuery();

            while (query.next()) {
                isBannedSelectStatement = connection.prepareStatement(SELECT_BAN);
                isBannedSelectStatement.setString(1, query.getString("player"));
                isBannedquery = isBannedSelectStatement.executeQuery();

                if (!isBannedquery.next()) {
                    isBannedquery.close();
                    continue;
                }

                String reason = isBannedquery.getString("ban_reason");
                long banExpiersOn = isBannedquery.getLong("ban_expires_on");
                long now = System.currentTimeMillis() / 1000L;
                isBannedquery.close();
                isBannedSelectStatement.close();

                if (banExpiersOn == 0) {
                    return reason;
                }

                if (banExpiersOn >= now) {
                    return reason;
                }

                unban(query.getString("player"), unbannedBy, true);
            }

            return null;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        } finally {
            if (selectStatement != null) {
                try {
                    selectStatement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public String getBannedBy(long ip, CommandSender unbannedBy) {
        ResultSet query = null;

        Connection connection = null;
        PreparedStatement selectStatement = null;

        ResultSet isBannedquery = null;
        PreparedStatement isBannedSelectStatement = null;
        try {
            connection = getConnection();
            selectStatement = connection.prepareStatement(SELECT_IP);
            selectStatement.setLong(1, ip);
            query = selectStatement.executeQuery();
            while (query.next()) {
                isBannedSelectStatement = connection.prepareStatement(SELECT_BAN);
                isBannedSelectStatement.setString(1, query.getString("player"));
                isBannedquery = isBannedSelectStatement.executeQuery();

                if (!isBannedquery.next()) {
                    isBannedquery.close();
                    continue;
                }
                String bannedBy = isBannedquery.getString("banned_by");
                long banExpiersOn = isBannedquery.getLong("ban_expires_on");
                long now = System.currentTimeMillis() / 1000L;
                isBannedquery.close();
                isBannedSelectStatement.close();
                if (banExpiersOn == 0) {
                    return bannedBy;
                }

                if (banExpiersOn >= now) {
                    return bannedBy;
                }

                unban(query.getString("player"), unbannedBy, true);
            }

            return null;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        } finally {
            if (selectStatement != null) {
                try {
                    selectStatement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public String getBannedServer(long ip, CommandSender unbannedBy) {
        ResultSet query = null;

        Connection connection = null;
        PreparedStatement selectStatement = null;

        ResultSet isBannedquery = null;
        PreparedStatement isBannedSelectStatement = null;
        try {
            connection = getConnection();
            selectStatement = connection.prepareStatement(SELECT_IP);
            selectStatement.setLong(1, ip);
            query = selectStatement.executeQuery();

            while (query.next()) {
                isBannedSelectStatement = connection.prepareStatement(SELECT_BAN);
                isBannedSelectStatement.setString(1, query.getString("player"));
                isBannedquery = isBannedSelectStatement.executeQuery();

                if (!isBannedquery.next()) {
                    isBannedquery.close();
                    continue;
                }
                String server = isBannedquery.getString("server");
                long banExpiersOn = isBannedquery.getLong("ban_expires_on");
                long now = System.currentTimeMillis() / 1000L;
                isBannedquery.close();
                isBannedSelectStatement.close();
                if (banExpiersOn == 0) {
                    return server;
                }

                if (banExpiersOn >= now) {
                    return server;
                }

                unban(query.getString("player"), unbannedBy, true);
            }

            return null;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        } finally {
            if (selectStatement != null) {
                try {
                    selectStatement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public String getBannedTimeLeft(long ip, CommandSender unbannedBy) {
        ResultSet query = null;

        Connection connection = null;
        PreparedStatement selectStatement = null;

        ResultSet isBannedquery = null;
        PreparedStatement isBannedSelectStatement = null;
        try {
            connection = getConnection();
            selectStatement = connection.prepareStatement(SELECT_IP);
            selectStatement.setLong(1, ip);
            query = selectStatement.executeQuery();

            while (query.next()) {
                isBannedSelectStatement = connection.prepareStatement(SELECT_BAN);
                isBannedSelectStatement.setString(1, query.getString("player"));
                isBannedquery = isBannedSelectStatement.executeQuery();

                if (!isBannedquery.next()) {
                    isBannedquery.close();
                    continue;
                }
                long banExpiersOn = isBannedquery.getLong("ban_expires_on");
                isBannedquery.close();
                isBannedSelectStatement.close();
                return getDifferenceFormat(banExpiersOn);
            }

            return null;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        } finally {
            if (selectStatement != null) {
                try {
                    selectStatement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    public void mute(String muted, CommandSender mutedBy, String expires, String reason, boolean permanent) {
        long secondsNow = System.currentTimeMillis() / 1000L;
        long secondsUntilUnbanExpires = 0;
        if (!permanent) {
            try {
                secondsUntilUnbanExpires = parseDateDiff(expires, true);
            } catch (Exception e) {
                mutedBy.sendMessage(format("Castigos", "Fecha inválida!"));
                return;
            }
        }

        Connection connection = null;
        PreparedStatement muteStatement = null;

        try {
            connection = getConnection();
            muteStatement = connection.prepareStatement(INSERT_MUTE);
            muteStatement.setNull(1, Types.INTEGER);
            muteStatement.setString(2, muted);
            muteStatement.setString(3, mutedBy.getName());
            muteStatement.setString(4, reason);
            muteStatement.setLong(5, secondsNow);
            muteStatement.setLong(6, secondsUntilUnbanExpires);
            muteStatement.setString(7, Bukkit.getServerName());
            muteStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (muteStatement != null) {
                try {
                    muteStatement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    public void warn(String warned, CommandSender warnedBy, String reason) {
        long secondsNow = System.currentTimeMillis() / 1000L;

        Connection connection = null;
        PreparedStatement warnStatement = null;

        try {
            connection = getConnection();
            warnStatement = connection.prepareStatement(INSERT_WARNING);
            warnStatement.setNull(1, Types.INTEGER);
            warnStatement.setString(2, warned);
            warnStatement.setString(3, warnedBy.getName());
            warnStatement.setString(4, reason);
            warnStatement.setLong(5, secondsNow);
            warnStatement.setString(6, Bukkit.getServerName());
            warnStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (warnStatement != null) {
                try {
                    warnStatement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    public String checkMuted(String muted, CommandSender unmutedBy) {
        ResultSet query = null;

        Connection connection = null;
        PreparedStatement selectStatement = null;
        try {
            connection = getConnection();
            selectStatement = connection.prepareStatement(SELECT_MUTE);
            selectStatement.setString(1, muted);
            query = selectStatement.executeQuery();

            if (!query.next()) {
                query.close();
                return null;
            }

            long muteExpiersOn = query.getLong("mute_expires_on");
            long now = System.currentTimeMillis() / 1000L;
            String reason = query.getString("mute_reason");
            String mutedBy = query.getString("muted_by");
            query.close();

            if (muteExpiersOn >= now || muteExpiersOn == 0) {
                return " por " + mutedBy + ". Razón: " + reason + " Expira en: " + getDifferenceFormat(muteExpiersOn);
            }

            unmute(muted, unmutedBy, true);
            return null;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        } finally {
            if (selectStatement != null) {
                try {
                    selectStatement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public boolean unmute(String muted, CommandSender mutedBy, boolean force) {
        ResultSet query = null;

        Connection connection = null;
        PreparedStatement selectStatement = null;
        PreparedStatement deleteStatement = null;
        PreparedStatement insertStatement = null;
        try {
            connection = getConnection();
            selectStatement = connection.prepareStatement(SELECT_MUTE);
            selectStatement.setString(1, muted);
            query = selectStatement.executeQuery();

            if (!query.next()) {
                query.close();
                return false;
            }

            int id = query.getInt("mute_id");
            String bannedBy = query.getString("muted_by");
            String reason = query.getString("mute_reason");
            String server = query.getString("server");
            long muteTime = query.getLong("mute_time");
            long muteExpiersOn = query.getLong("mute_expires_on");
            long now = System.currentTimeMillis() / 1000L;
            query.close();

            if (muteExpiersOn >= now && !force) {
                return false;
            }

            if (muteExpiersOn == 0 && !force) {
                return false;
            }

            deleteStatement = connection.prepareStatement(DELETE_MUTE);
            deleteStatement.setInt(1, id);
            deleteStatement.execute();

            insertStatement = connection.prepareStatement(INSERT_MUTE_RECORD);
            insertStatement.setNull(1, Types.INTEGER);
            insertStatement.setString(2, muted);
            insertStatement.setString(3, bannedBy);
            insertStatement.setString(4, reason);
            insertStatement.setLong(5, muteTime);
            insertStatement.setLong(6, muteExpiersOn);
            insertStatement.setString(7, mutedBy.getName());
            insertStatement.setLong(8, now);
            insertStatement.setString(9, server);
            insertStatement.execute();

            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            if (selectStatement != null) {
                try {
                    selectStatement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (deleteStatement != null) {
                try {
                    deleteStatement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (insertStatement != null) {
                try {
                    insertStatement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void deletePunish(String tableName, String columnName, int id) {
        try {
            Connection connection = getConnection();
            Statement statement = connection.createStatement();
            statement.execute("DELETE FROM " + tableName + " WHERE " + columnName + "=" + id);
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public TripleEntry<List<BanRecord>, List<MuteRecord>, List<WarningRecord>> getRecords(String user, HistoryPage page) {
        List<BanRecord> banRecords = new ArrayList<>();
        List<MuteRecord> muteRecords = new ArrayList<>();
        List<WarningRecord> warningRecords = new ArrayList<>();

        unmute(user, Bukkit.getConsoleSender(), false);
        unban(user, Bukkit.getConsoleSender(), false);

        ResultSet muteQuery;
        ResultSet muteRecordQuery;
        ResultSet banQuery;
        ResultSet banRecordQuery;
        ResultSet warningRecordQuery;

        Connection connection = null;
        PreparedStatement selectMuteStatement = null;
        PreparedStatement selectMuteRecordStatement = null;
        PreparedStatement selectBanStatement = null;
        PreparedStatement selectBanRecordStatement = null;
        PreparedStatement selectWarningRecordStatement = null;
        try {
            connection = getConnection();

            if (page == HistoryPage.MUTE) {
                selectMuteStatement = connection.prepareStatement(SELECT_MUTE);
                selectMuteStatement.setString(1, user);
                muteQuery = selectMuteStatement.executeQuery();

                while (muteQuery.next()) {
                    MuteRecord record = new MuteRecord();
                    record.setMuted(user);
                    record.setId(muteQuery.getInt("mute_id"));
                    record.setMutedBy(muteQuery.getString("muted_by"));
                    record.setMuteExpiresOn(muteQuery.getLong("mute_expires_on"));
                    record.setReason(muteQuery.getString("mute_reason"));
                    record.setServer(muteQuery.getString("server"));
                    record.setTime(muteQuery.getLong("mute_time"));
                    record.setUnmuted(false);
                    muteRecords.add(record);
                }
                muteQuery.close();

                selectMuteRecordStatement = connection.prepareStatement(SELECT_MUTE_RECORD);
                selectMuteRecordStatement.setString(1, user);
                muteRecordQuery = selectMuteRecordStatement.executeQuery();

                while (muteRecordQuery.next()) {
                    MuteRecord record = new MuteRecord();
                    record.setMuted(user);
                    record.setId(muteRecordQuery.getInt("mute_record_id"));
                    record.setMutedBy(muteRecordQuery.getString("muted_by"));
                    record.setMuteExpiresOn(muteRecordQuery.getLong("mute_expired_on"));
                    record.setReason(muteRecordQuery.getString("mute_reason"));
                    record.setServer(muteRecordQuery.getString("server"));
                    record.setTime(muteRecordQuery.getLong("mute_time"));
                    record.setUnmuted(true);
                    record.setUnmutedBy(muteRecordQuery.getString("unmuted_by"));
                    record.setUnmutedtime(muteRecordQuery.getLong("unmuted_time"));
                    muteRecords.add(record);
                }
                muteRecordQuery.close();
            }

            if (page == HistoryPage.BAN) {
                selectBanStatement = connection.prepareStatement(SELECT_BAN);
                selectBanStatement.setString(1, user);
                banQuery = selectBanStatement.executeQuery();

                while (banQuery.next()) {
                    BanRecord record = new BanRecord();
                    record.setBanned(user);
                    record.setId(banQuery.getInt("ban_id"));
                    record.setBannedBy(banQuery.getString("banned_by"));
                    record.setBanExpiresOn(banQuery.getLong("ban_expires_on"));
                    record.setReason(banQuery.getString("ban_reason"));
                    record.setServer(banQuery.getString("server"));
                    record.setTime(banQuery.getLong("ban_time"));
                    record.setUnbanned(false);
                    banRecords.add(record);
                }
                banQuery.close();

                selectBanRecordStatement = connection.prepareStatement(SELECT_BAN_RECORD);
                selectBanRecordStatement.setString(1, user);
                banRecordQuery = selectBanRecordStatement.executeQuery();

                while (banRecordQuery.next()) {
                    BanRecord record = new BanRecord();
                    record.setBanned(user);
                    record.setId(banRecordQuery.getInt("ban_record_id"));
                    record.setBannedBy(banRecordQuery.getString("banned_by"));
                    record.setBanExpiresOn(banRecordQuery.getLong("ban_expired_on"));
                    record.setReason(banRecordQuery.getString("ban_reason"));
                    record.setServer(banRecordQuery.getString("server"));
                    record.setTime(banRecordQuery.getLong("ban_time"));
                    record.setUnbanned(true);
                    record.setUnbannedBy(banRecordQuery.getString("unbanned_by"));
                    record.setUnbannedtime(banRecordQuery.getLong("unbanned_time"));
                    banRecords.add(record);
                }
                banRecordQuery.close();
            }

            if (page == HistoryPage.WARNING) {
                selectWarningRecordStatement = connection.prepareStatement(SELECT_WARNING);
                selectWarningRecordStatement.setString(1, user);
                warningRecordQuery = selectWarningRecordStatement.executeQuery();

                while (warningRecordQuery.next()) {
                    WarningRecord record = new WarningRecord();
                    record.setId(warningRecordQuery.getInt("warn_id"));
                    record.setWarned(warningRecordQuery.getString("warned"));
                    record.setWarnedBy(warningRecordQuery.getString("warned_by"));
                    record.setReason(warningRecordQuery.getString("warn_reason"));
                    record.setTime(warningRecordQuery.getLong("warn_time"));
                    record.setServer(warningRecordQuery.getString("server"));
                    warningRecords.add(record);
                }
                warningRecordQuery.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (selectMuteStatement != null) {
                try {
                    selectMuteStatement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (selectMuteRecordStatement != null) {
                try {
                    selectMuteRecordStatement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (selectBanStatement != null) {
                try {
                    selectBanStatement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (selectBanRecordStatement != null) {
                try {
                    selectBanRecordStatement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

        return new TripleEntry<>(banRecords, muteRecords, warningRecords);
    }

    public String getIP(InetAddress ip) {
        return ip.getHostAddress().replace("/", "");
    }

    public long toLong(String ip) {
        String[] addressArray = ip.split("\\.");
        long result = 0;

        for (int i = 0; i < addressArray.length; i++) {
            int power = 3 - i;

            result += ((Integer.parseInt(addressArray[i]) % 256 * Math.pow(256, power)));
        }

        return result;
    }

    public long toLong(InetAddress ip) {
        return toLong(getIP(ip));
    }

    public String toString(long ip) {
        return ((ip >> 24) & 0xFF) + "." + ((ip >> 16) & 0xFF) + "." + ((ip >> 8) & 0xFF) + "." + (ip & 0xFF);
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

    @EventHandler(priority = EventPriority.LOWEST)
    public void handleChat(final AsyncPlayerChatEvent event) {
        if (event.isCancelled()) {
            return;
        }
        final Player sender = event.getPlayer();
        String reason = checkMuted(sender.getName(), Bukkit.getConsoleSender());
        if (reason != null) {
            sender.sendMessage(format("Castigos", "Shh, estas silenciado" + reason));
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void playerLogin(AsyncPlayerPreLoginEvent event) {
        String reason = isBannedIp(toLong(getIP(event.getAddress())), Bukkit.getConsoleSender());
        String expiry = getBannedTimeLeft(toLong(getIP(event.getAddress())), Bukkit.getConsoleSender());
        String bannedby = getBannedBy(toLong(getIP(event.getAddress())), Bukkit.getConsoleSender());
        String server = getBannedServer(toLong(getIP(event.getAddress())), Bukkit.getConsoleSender());
        if (reason != null) {
            event.setLoginResult(AsyncPlayerPreLoginEvent.Result.KICK_BANNED);
            event.setKickMessage("§cTu estas baneado de este servidor!\n\n§7Baneado por: §a" + bannedby + "\n§7Razón: §a" + reason + "\n§7Servidor: §a" + server + "\n§7Expira en: §a" + expiry + "\n\n§eAdquiere tu desbaneo en tienda.sircraked.com\n" + "§eo apela en SirCraked.com");
        }

    }

    public String getDifferenceFormat(long timestamp) {
        if (timestamp == 0) {
            return "Nunca";
        }
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

    public Connection getConnection() throws SQLException {
        return punishmysql.getDataSource().getConnection();
    }

    public SirCrakedCore getPlugin() {
        return plugin;
    }
}
