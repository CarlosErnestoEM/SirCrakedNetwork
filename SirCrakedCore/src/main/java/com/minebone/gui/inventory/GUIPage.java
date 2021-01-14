package com.minebone.gui.inventory;

import com.minebone.gui.inventory.button.NullButton;
import com.minebone.gui.inventory.page.FailedPage;
import com.minebone.util.Utils;
import com.pollogamer.SirCrakedCore;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;

public abstract class GUIPage<PluginType extends JavaPlugin> implements Listener {

    protected final HashMap<Integer, GUIButton> buttons;
    protected final PluginType plugin;
    protected boolean overrideClose = false;
    protected final boolean blockInventoryMovement = true;
    protected final int size;
    protected ClickType type;

    protected final Inventory menu;
    private final Player user;
    private final String name;

    public GUIPage(PluginType plugin, Player player, String rawName, int size, boolean override) {
        this(plugin, player, rawName, size);
        this.overrideClose = true;
    }

    public GUIPage(PluginType plugin, Player player, String rawName, int size) {
        this.user = player;
        Utils.invokeStaticMethod(Utils.getCBSClass("event.CraftEventFactory"), "handleInventoryCloseEvent", new Class[]{Utils.getNMSClass("EntityHuman")}, Utils.invokeMethod(player, "getHandle", new Class[]{}, null));

        this.plugin = plugin;
        this.size = size;
        this.name = (rawName.length() > 32 ? rawName.substring(0, 32) : rawName);
        this.buttons = new HashMap<>();

        plugin.getServer().getPluginManager().registerEvents(this, plugin);
        this.menu = Bukkit.getServer().createInventory(null, size, name);

        Utils.setFieldValue(Utils.getNMSClass("EntityHuman"), "activeContainer", Utils.invokeMethod(player, "getHandle", new Class[]{}, null), Utils.getFieldValue(Utils.getNMSClass("EntityHuman"), "defaultContainer", Utils.invokeMethod(player, "getHandle", new Class[]{}, null)));
        user.openInventory(menu);
    }

    @SuppressWarnings("deprecation")
    public void build() {
        if (!getPlayer().isOnline()) {
            destroy();
            return;
        }

        try {
            buildPage();
            getPlayer().updateInventory();
        } catch (Exception e) {
            e.printStackTrace();
            new FailedPage<>(SirCrakedCore.getCore(), getPlayer(), "§eError xd");
        }
    }

    public abstract void buildPage();

    public PluginType getPlugin() {
        return plugin;
    }

    public Player getPlayer() {
        return user;
    }

    public void addButton(GUIButton button, int slot) {
        if (slot >= size) {
            return;
        }

        if (!(button instanceof NullButton) && button.getItem() != null) {
            menu.setItem(slot, button.getItem());
        }

        buttons.put(slot, button);
    }

    public void removeButton(int slot) {
        menu.setItem(slot, null);

        if (buttons.get(slot) != null) {
            buttons.get(slot).destroy();
        }

        buttons.remove(slot);
    }

    public void removeAll() {
        for (int i = 0; i <= size - 1; i++) {
            removeButton(i);
        }

        buttons.clear();
    }

    public void refresh() {
        removeAll();
        build();
    }

    public boolean isFree(int slot) {
        return !buttons.keySet().contains(slot);
    }

    public void onInventoryCloseOverride() {

    }

    @EventHandler
    public void onPlayerCloseInventory(InventoryCloseEvent event) {
        if (overrideClose) {
            onInventoryCloseOverride();
            return;
        }

        Player player = (Player) event.getPlayer();

        if (user.getName().equalsIgnoreCase(player.getName())) {
            destroy();
            destroyInternal();
        }
    }

    @EventHandler
    public void onClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        type = event.getClick();
        if (!user.getName().equalsIgnoreCase(player.getName())) {
            return;
        }

        if (!user.getOpenInventory().getTitle().equalsIgnoreCase(name)) {
            return;
        }

        event.setCancelled(blockInventoryMovement);

        if (!buttons.containsKey(event.getRawSlot())) {
            return;
        }

        event.setCancelled(true);

        buttons.get(event.getRawSlot()).click(this);
    }

    public ClickType getType() {
        return type;
    }

    public abstract void destroy();

    public void destroyInternal() {
        HandlerList.unregisterAll(this);
        this.buttons.values().forEach(GUIButton::destroy);
        this.buttons.clear();
    }

}
