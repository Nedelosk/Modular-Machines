package nedelosk.modularmachines.common.world;

import java.util.Random;

import nedelosk.modularmachines.common.config.Config;
import nedelosk.modularmachines.modules.ModuleCore;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.feature.WorldGenMinable;
import cpw.mods.fml.common.IWorldGenerator;

public class WorldGeneratorModularMachines implements IWorldGenerator {

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
		if (Config.generateColumbiteOre) {
			generateOre(ModuleCore.BlockManager.Ore_Others.block(), 0, world, random, x, z, 2 + random.nextInt(3), 1, 0,
					25);
		}
		if (Config.generateAluminiumOre) {
			generateOre(ModuleCore.BlockManager.Ore_Others.block(), 1, world, random, x, z, 4 + random.nextInt(3), 3,
					20, 65);
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
