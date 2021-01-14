package com.pollogamer.sircrakedserver.sanciones.ui;

import com.minebone.gui.inventory.GUIButton;
import com.minebone.gui.inventory.GUIPage;
import com.minebone.gui.inventory.page.ConfirmationPage;
import com.minebone.itemstack.ItemStackBuilder;
import com.pollogamer.SirCrakedCore;
import com.pollogamer.sircrakedserver.sanciones.Punish;
import com.pollogamer.sircrakedserver.sanciones.util.BanRecord;
import com.pollogamer.sircrakedserver.sanciones.util.IRecord;
import com.pollogamer.sircrakedserver.sanciones.util.MuteRecord;
import com.pollogamer.sircrakedserver.sanciones.util.WarningRecord;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class DeletePunishButton implements GUIButton<SirCrakedCore> {

    private IRecord record;

    public DeletePunishButton(IRecord record) {
        this.record = record;
    }

    @Override
    public void click(GUIPage<SirCrakedCore> page) {
        if (!page.getPlayer().hasPermission("sircraked.s.advanced")) {
            if (!record.getPunishedBy().equalsIgnoreCase(page.getPlayer().getName())) {
                return;
            }
        }

        new ConfirmationPage<SirCrakedCore>(page.getPlugin(), page.getPlayer(), new ItemStackBuilder().setMaterial(Material.BOOK_AND_QUILL).setName(ChatColor.RED + "Borrar la siguiente sanción?"), record.getItemStack()) {
            @Override
            public void onConfirm() {
                Punish punish = plugin.getPunish();
                if (record instanceof BanRecord) {
                    if (((BanRecord) record).isUnbanned()) {
                        if (!page.getPlayer().hasPermission("sircraked.s.advanced")) {
                            return;
                        }

                        punish.deletePunish("bm_ban_records", "ban_record_id", ((BanRecord) record).getId());
                    } else {
                        getPlugin().getPunish().unban(record.getPunished(), getPlayer(), true);
                    }
                } else if (record instanceof MuteRecord) {
                    if (((MuteRecord) record).isUnmuted()) {
                        if (!page.getPlayer().hasPermission("sircraked.s.advanced")) {
                            return;
                        }

                        punish.deletePunish("bm_mutes_records", "mute_record_id", ((MuteRecord) record).getId());
                    } else {
                        punish.unmute(record.getPunished(), getPlayer(), true);
                    }
                } else {
                    if (!page.getPlayer().hasPermission("sircraked.s.advanced")) {
                        return;
                    }

                    punish.deletePunish("bm_warnings", "warn_id", ((WarningRecord) record).getId());
                }

                getPlayer().sendMessage(format("Sanciones", "La sanción para el usuario " + ChatColor.GOLD + record.getPunished() + ChatColor.GRAY + " ha sido removida!"));
                getPlayer().closeInventory();
            }

            @Override
            public void onCancel() {
                if (page.getPlayer().hasPermission("sircraked.s.advanced")) {
                    new PunishUIMainPage(getPlugin(), getPlayer(), record.getPunished());
                } else {
                    new PunishUIMainPageHelper(getPlugin(), getPlayer(), record.getPunished());
                }
            }
        };
    }

    public String format(String module, String message) {
        return ChatColor.DARK_GRAY + "[" + ChatColor.RED + module + ChatColor.DARK_GRAY + "] » " + ChatColor.GRAY + message;
    }

    @Override
    public void destroy() {

    }

    @Override
    public ItemStack getItem() {
        return record.getItemStack();
    }
}
