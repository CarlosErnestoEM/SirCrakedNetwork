package com.minebone.gui.inventory.button;

import com.minebone.gui.anvil.AnvilGUI;
import com.minebone.gui.anvil.AnvilSlot;
import com.minebone.gui.inventory.GUIButton;
import com.minebone.gui.inventory.GUIPage;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.Map;

public class AnvilButton<PluginType extends JavaPlugin> implements GUIButton {

    private final ItemStack item;
    private final Map<AnvilSlot, ItemStack> items;
    private AnvilButtonConfirmAction<PluginType> confirmAction;
    private AnvilButtonCancelAction<PluginType> cancelAction;
    private PluginType plugin;
    private Player player;

    public AnvilButton(ItemStack item) {
        this.item = item;
        this.items = new HashMap<>();
    }

    public AnvilButton(ItemStack item, AnvilButtonConfirmAction<PluginType> confirmAction, AnvilButtonCancelAction<PluginType> cancelAction) {
        this.item = item;
        this.confirmAction = confirmAction;
        this.cancelAction = cancelAction;
        this.items = new HashMap<>();
    }

    @Override
    public void click(GUIPage page) {
        this.player = page.getPlayer();
        this.plugin = (PluginType) page.getPlugin();

        AnvilGUI<JavaPlugin> gui = new AnvilGUI<>(page.getPlugin(), page.getPlayer(), event -> {
            event.setWillDestroy(true);

            if (event.getSlot() == AnvilSlot.OUTPUT) {
                confirmAction.onConfirm(plugin, player, event);
            } else {
                cancelAction.onCancel(plugin, player);
            }
        });
        page.destroy();
        page.destroyInternal();
        gui.getItems().putAll(items);
        items.clear();
        gui.open();
    }

    public AnvilButton<PluginType> setConfirmAction(AnvilButtonConfirmAction<PluginType> confirmAction) {
        this.confirmAction = confirmAction;
        return this;
    }

    public AnvilButton<PluginType> setCancelAction(AnvilButtonCancelAction<PluginType> cancelAction) {
        this.cancelAction = cancelAction;
        return this;
    }

    @Override
    public ItemStack getItem() {
        return item;
    }

    public AnvilButton<PluginType> setSlot(final AnvilSlot slot, final ItemStack item) {
        this.items.put(slot, item);
        return this;
    }

    @Override
    public void destroy() {

    }
}
