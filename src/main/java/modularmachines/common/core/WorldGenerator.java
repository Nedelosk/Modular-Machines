package modularmachines.common.core;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkGenerator;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.feature.WorldGenMinable;
import net.minecraftforge.fml.common.IWorldGenerator;

import modularmachines.common.config.Config;
import modularmachines.common.core.managers.BlockManager;

public class WorldGenerator implements IWorldGenerator {

	@Override
	public void generate(Random random, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator, IChunkProvider chunkProvider) {
		switch (world.provider.getDimension()) {
			case -1:
				generateNether(world, random, chunkX * 16, chunkZ * 16);
			case 0:
				generateSurface(world, random, chunkX * 16, chunkZ * 16);
			case 1:
				generateEnd(world, random, chunkX * 16, chunkZ * 16);
		}
	}

	private void generateEnd(World world, Random random, int x, int z) {
	}

	private void generateSurface(World world, Random random, int x, int z) {
		if (Config.generateOre[0]) {
			generateOre(BlockManager.blockOres, 0, world, random, x, z, 5 + random.nextInt(6), 20, 10, 70);
		}
		if (Config.generateOre[1]) {
			generateOre(BlockManager.blockOres, 1, world, random, x, z, 3 + random.nextInt(2), 10, 0, 40);
		}
		if (Config.generateOre[2]) {
			generateOre(BlockManager.blockOres, 2, world, random, x, z, 7 + random.nextInt(3), 8, 7, 55);
		}
		if (Config.generateOre[3]) {
			generateOre(BlockManager.blockOres, 3, world, random, x, z, 4 + random.nextInt(2), 8, 16, 36);
		}
		if (Config.generateOre[4]) {
			generateOre(BlockManager.blockOres, 4, world, random, x, z, 3 + random.nextInt(2), 8, 11, 27);
		}
		if (Config.generateOre[5]) {
			generateOre(BlockManager.blockOres, 5, world, random, x, z, 2 + random.nextInt(3), 1, 0, 25);
		}
	}

	private void generateNether(World world, Random random, int x, int z) {
	}

	public void generateOre(Block ore, int meta, World world, Random random, int posX, int posZ, int maxOre, int spawnChance, int minY, int maxY) {
		int differenzMinMaxY = maxY - minY;
		for (int x = 0; x < spawnChance; x++) {
			int positionX = posX + random.nextInt(16);
			int positionY = minY + random.nextInt(differenzMinMaxY);
			int positionZ = posZ + random.nextInt(16);
			(new WorldGenMinable(ore.getStateFromMeta(meta), maxOre)).generate(world, random, new BlockPos(positionX, positionY, positionZ));
		}
	}
}
