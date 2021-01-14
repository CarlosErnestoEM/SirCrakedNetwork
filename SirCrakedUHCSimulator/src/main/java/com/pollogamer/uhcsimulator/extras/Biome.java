package com.pollogamer.uhcsimulator.extras;

import net.minecraft.server.v1_8_R3.BiomeBase;
import org.bukkit.plugin.Plugin;

import java.lang.reflect.Field;

public class Biome {

    public static void setup(Plugin paramPlugin) {
        BiomeBase[] arrayOfBiomeBase1;
        try {
            Field localField1 = BiomeBase.class.getDeclaredField("biomes");
            localField1.setAccessible(true);
            if ((localField1.get(null) instanceof BiomeBase[])) {
                arrayOfBiomeBase1 = (BiomeBase[]) localField1.get(null);
                arrayOfBiomeBase1[BiomeBase.DEEP_OCEAN.id] = BiomeBase.PLAINS;
                arrayOfBiomeBase1[BiomeBase.OCEAN.id] = BiomeBase.PLAINS;

                localField1.set(null, arrayOfBiomeBase1);
            }
        } catch (Exception localException1) {
        }
        Field localField2 = null;
        try {
            localField2 = BiomeBase.class.getDeclaredField("biomes");
            localField2.setAccessible(true);
            if ((localField2.get(null) instanceof BiomeBase[])) {
                arrayOfBiomeBase1 = (BiomeBase[]) localField2.get(null);
                //for(BiomeBase biomeBase : arrayOfBiomeBase1){
                //   arrayOfBiomeBase1[biomeBase] = BiomeBase.PLAINS;
                //}
                arrayOfBiomeBase1[BiomeBase.DEEP_OCEAN.id] = BiomeBase.PLAINS;
                arrayOfBiomeBase1[BiomeBase.OCEAN.id] = BiomeBase.PLAINS;
                arrayOfBiomeBase1[BiomeBase.SWAMPLAND.id] = BiomeBase.PLAINS;
                arrayOfBiomeBase1[BiomeBase.JUNGLE.id] = BiomeBase.PLAINS;
                arrayOfBiomeBase1[BiomeBase.JUNGLE_EDGE.id] = BiomeBase.PLAINS;
                arrayOfBiomeBase1[BiomeBase.JUNGLE_HILLS.id] = BiomeBase.PLAINS;
                arrayOfBiomeBase1[BiomeBase.EXTREME_HILLS.id] = BiomeBase.PLAINS;
                arrayOfBiomeBase1[BiomeBase.EXTREME_HILLS_PLUS.id] = BiomeBase.PLAINS;
                arrayOfBiomeBase1[BiomeBase.MESA.id] = BiomeBase.PLAINS;
                arrayOfBiomeBase1[BiomeBase.MESA_PLATEAU.id] = BiomeBase.PLAINS;
                arrayOfBiomeBase1[BiomeBase.MESA_PLATEAU_F.id] = BiomeBase.PLAINS;
                BiomeBase[] arrayOfBiomeBase2;
                int j = (arrayOfBiomeBase2 = BiomeBase.getBiomes()).length;
                for (int i = 0; i < j; i++) {
                    BiomeBase localBiomeBase = arrayOfBiomeBase2[i];
                    arrayOfBiomeBase1[localBiomeBase.id] = BiomeBase.PLAINS;
                }
                localField2.set(null, arrayOfBiomeBase1);
            }
        } catch (Exception localException2) {
        }
    }
}

