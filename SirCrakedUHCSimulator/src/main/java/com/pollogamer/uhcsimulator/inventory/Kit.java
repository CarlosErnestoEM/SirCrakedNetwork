package com.pollogamer.uhcsimulator.inventory;

import com.pollogamer.uhcsimulator.Principal;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class Kit {

    public static List<ItemStack[]> inventory = new ArrayList<>();

    public Kit() {
        addInventories();
    }

    public void addInventories() {
        Principal.getPlugin().log("Anadiendo Kits...");
        for (String s : Principal.getPlugin().getKits().getConfigurationSection("").getKeys(false)) {
            ItemStack[] items = (Principal.getPlugin().getKits().getList(s + ".contents")).toArray(new ItemStack[0]);
            inventory.add(items);
        }
    }


}
