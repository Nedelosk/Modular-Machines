package nedelosk.forestday.common.world;

import java.util.Random;

import nedelosk.forestday.common.config.ForestdayConfig;
import nedelosk.forestday.common.registrys.ForestdayBlockRegistry;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.feature.WorldGenMinable;
import cpw.mods.fml.common.IWorldGenerator;

public class WorldGeneratorForestday implements IWorldGenerator {

	@Override
	public void generate(Random random, int chunkX, int chunkZ, World world,IChunkProvider chunkGenerator, IChunkProvider chunkRovider) {

		switch(world.provider.dimensionId){
		
		case -1: generateNether(world, random, chunkX * 16, chunkZ * 16);
		case  0: generateSurface(world, random, chunkX * 16, chunkZ * 16);
		case  1: generateEnd(world, random, chunkX * 16, chunkZ * 16);
		
		}
		
	}

	private void generateEnd(World world, Random random, int x, int z) {
		
	}

	private void generateSurface(World world, Random random, int x, int z) {
		if(ForestdayConfig.generateCopper)
		{
		generateOre(ForestdayBlockRegistry.oreBlock, 0, world, random, x, z, 5 + random.nextInt(3), 20, 12, 45);
		}
		if(ForestdayConfig.generateTin)
		{
		generateOre(ForestdayBlockRegistry.oreBlock, 1, world, random, x, z, 3 + random.nextInt(3), 10, 3, 20);
		}
		if(ForestdayConfig.generateLimestone)
		{
		generateOre(ForestdayBlockRegistry.oreBlock, 2, world, random, x, z, 13 + random.nextInt(7), 8, 20, 56);
		}
	}

	private void generateNether(World world, Random random, int x, int z) {
	}
	
	public void generateOre(Block ore,int meta, World world, Random random, int posX, int posZ, int maxOre, int spawnChance 
	,int minY, int maxY){
		
		int differenzMinMaxY = maxY - minY;
		for(int x = 0; x < spawnChance; x++){
			
			int positionX = posX + random.nextInt(16);
			int positionY = minY + random.nextInt(differenzMinMaxY);
			int positionZ = posZ + random.nextInt(16);
			(new WorldGenMinable(ore, meta, maxOre, Blocks.stone)).generate(world, random, positionX, positionY, positionZ);
			
		}
		
	}

}
