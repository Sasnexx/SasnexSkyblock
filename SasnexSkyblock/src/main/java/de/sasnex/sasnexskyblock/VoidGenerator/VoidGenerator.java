package de.sasnex.sasnexskyblock.VoidGenerator;

import org.bukkit.generator.ChunkGenerator;
import org.bukkit.generator.WorldInfo;

import java.util.Random;

public class VoidGenerator extends ChunkGenerator {

    @Override
    public void generateSurface(WorldInfo info, Random random, int x, int z, ChunkData data){

    }

    @Override
    public boolean shouldGenerateCaves() { return false; }
    @Override
    public boolean shouldGenerateDecorations() { return false; }
}
