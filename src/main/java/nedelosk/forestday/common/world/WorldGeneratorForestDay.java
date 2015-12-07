package nedelosk.forestday.common.world;

import java.util.Random;

import nedelosk.forestday.common.configs.ForestDayConfig;
import nedelosk.forestday.common.core.managers.FBlockManager;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.feature.WorldGenMinable;
import cpw.mods.fml.common.IWorldGenerator;

public class WorldGeneratorForestDay implements IWorldGenerator {

	@Override
	public void generate(Random random, int chunkX, int chunkZ, World world, IChunkProvider chunkGenerator,
			IChunkProvider chunkRovider) {

		switch (world.provider.dimensionId) {

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
		if (ForestDayConfig.generateOre[0]) {
			generateOre(FBlockManager.Ore.block(), 0, world, random, x, z, 5 + random.nextInt(6), 20, 10, 70);
		}
		if (ForestDayConfig.generateOre[1]) {
			generateOre(FBlockManager.Ore.block(), 1, world, random, x, z, 3 + random.nextInt(2), 10, 0, 40);
		}
		if (ForestDayConfig.generateOre[2]) {
			generateOre(FBlockManager.Ore.block(), 2, world, random, x, z, 7 + random.nextInt(3), 8, 7, 55);
		}
		if (ForestDayConfig.generateOre[3]) {
			generateOre(FBlockManager.Ore.block(), 3, world, random, x, z, 4 + random.nextInt(2), 8, 16, 36);
		}
		if (ForestDayConfig.generateOre[4]) {
			generateOre(FBlockManager.Ore.block(), 4, world, random, x, z, 3 + random.nextInt(2), 8, 11, 27);
		}
	}

	private void generateNether(World world, Random random, int x, int z) {
	}

	public void generateOre(Block ore, int meta, World world, Random random, int posX, int posZ, int maxOre,
			int spawnChance, int minY, int maxY) {

		int differenzMinMaxY = maxY - minY;
		for (int x = 0; x < spawnChance; x++) {

			int positionX = posX + random.nextInt(16);
			int positionY = minY + random.nextInt(differenzMinMaxY);
			int positionZ = posZ + random.nextInt(16);
			(new WorldGenMinable(ore, meta, maxOre, Blocks.stone)).generate(world, random, positionX, positionY,
					positionZ);

		}

	}

}
