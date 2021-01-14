package com.minebone.gui.anvil;

import com.minebone.util.Utils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;

public class AnvilGUI<PluginType extends JavaPlugin> implements Listener {

    private final Player player;
    private final HashMap<AnvilSlot, ItemStack> items;
    private final AnvilClickEventHandler handler;
    private Inventory inv;

    public AnvilGUI(final PluginType plugin, final Player player, final AnvilClickEventHandler handler) {
        super();
        this.items = new HashMap<>();
        this.player = player;
        this.handler = handler;

        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (!event.getInventory().equals(inv)) {
            return;
        }

        event.setCancelled(true);
        ItemStack item = event.getCurrentItem();
        int slot = event.getRawSlot();
        String name = "";

        if (item != null && item.hasItemMeta()) {
            ItemMeta meta = item.getItemMeta();
            if (meta.hasDisplayName()) {
                name = meta.getDisplayName();
            }
        }

        AnvilClickEvent clickEvent = new AnvilClickEvent(AnvilSlot.bySlot(slot), name, player);
        handler.onAnvilClick(clickEvent);

        if (clickEvent.getWillClose()) {
            event.getWhoClicked().closeInventory();
        }

        if (clickEvent.getWillDestroy()) {
            destroy();
        }
    }

    @EventHandler
    public void onInventoryClose(InventoryCloseEvent event) {
        Inventory inv = event.getInventory();
        if (!inv.equals(this.inv)) {
            return;
        }

        inv.clear();
        destroy();
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        if (!event.getPlayer().equals(getPlayer())) {
            return;
        }

        destroy();
    }

    public void open() {
        try {
            open0();
        } catch (IllegalAccessException | InstantiationException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    private void open0() throws IllegalAccessException, InvocationTargetException, InstantiationException {
        Object entityPlayer = Utils.getHandle(player);
        Object container = Utils.invokeStaticMethod(Utils.getCBSClass("event.CraftEventFactory"), "callInventoryOpenEvent", new Class[]{Utils.getNMSClass("EntityPlayer"), Utils.getNMSClass("Container")}, Utils.invokeMethod(player, "getHandle", new Class[]{}, null), AnvilHandler.getNewAnvilContainer(entityPlayer));

        if (container == null) {
            return;
        }

        this.inv = (Inventory) Utils.invokeMethod(Utils.invokeMethod(container, "getBukkitView", null), "getTopInventory", null);

        for (final AnvilSlot slot : this.items.keySet()) {
            this.inv.setItem(slot.getSlot(), this.items.get(slot));
        }

        int c = (int) Utils.invokeMethod(entityPlayer, "nextContainerCounter", null);

        if (!Utils.SERVER_VERSION.contains("1_7")) {
            Utils.sendPacket(player, AnvilHandler.PACKET_PLAY_OUT_OPEN_WINDOW_CLASS.getConstructors()[1].newInstance(c, "minecraft:anvil", AnvilHandler.CHAT_MESSAGE_CLASS.getConstructors()[0].newInstance("Repairing", new Object[0]), 0));
        } else {
            Utils.sendPacket(player, AnvilHandler.PACKET_PLAY_OUT_OPEN_WINDOW_CLASS.getConstructors()[1].newInstance(c, 8, "Repairing", 0, true));
        }

        Utils.setFieldValueNotDeclared(entityPlayer.getClass(), "activeContainer", entityPlayer, container);
        Utils.setFieldValueNotDeclared(Utils.getFieldValueNotDeclared(entityPlayer.getClass(), "activeContainer", entityPlayer).getClass(), "windowId", Utils.getFieldValueNotDeclared(entityPlayer.getClass(), "activeContainer", entityPlayer), c);
        Method m = Utils.getMethodNotDeclaredValue(container.getClass(), "addSlotListener", AnvilHandler.ICRAFTING_CLASS);
        Utils.invokeMethod(container, m, entityPlayer);
    }

    public void destroy() {
        HandlerList.unregisterAll(this);
    }

    public Player getPlayer() {
        return this.player;
    }

    public AnvilGUI setSlot(final AnvilSlot slot, final ItemStack item) {
        this.items.put(slot, item);
        return this;
    }

    public HashMap<AnvilSlot, ItemStack> getItems() {
        return items;
    }
}
