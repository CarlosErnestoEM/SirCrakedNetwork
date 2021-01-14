package com.pollogamer.sircrakedserver.cleangenerator;

import org.bukkit.Chunk;
import org.bukkit.World;
import org.bukkit.generator.BlockPopulator;

import java.util.Random;

public class CleanroomBlockPopulator extends BlockPopulator {
    byte[] layerDataValues;

    protected CleanroomBlockPopulator(final byte[] layerDataValues) {
        super();
        this.layerDataValues = layerDataValues;
    }

    public void populate(final World world, final Random random, final Chunk chunk) {
        if (this.layerDataValues != null) {
            final int x = chunk.getX() << 4;
            final int z = chunk.getZ() << 4;
            for (int y = 0; y < this.layerDataValues.length; ++y) {
                final byte dataValue = this.layerDataValues[y];
                if (dataValue != 0) {
                    for (int xx = 0; xx < 16; ++xx) {
                        for (int zz = 0; zz < 16; ++zz) {
                            world.getBlockAt(x + xx, y, z + zz).setData(dataValue);
                        }
                    }
                }
            }
        }
    }
}

