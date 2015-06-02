package nedelosk.forestbotany.common.blocks;

import java.util.Random;

import nedelosk.forestbotany.common.blocks.tile.TileTree;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public abstract class BlockTree extends BlockContainer {

	public BlockTree(Material material) {
		super(material);
		setTickRandomly(true);
	}

	@Override
	public void updateTick(World world, int x, int y, int z, Random rand) {

		if (world.rand.nextFloat() > 0.1) {
			return;
		}

		TileEntity tile = world.getTileEntity(x, y, z);
		if (!(tile instanceof TileTree)) {
			return;
		}

		((TileTree) tile).onBlockTick();
	}

	@Override
	public boolean hasTileEntity(int metadata) {
		return true;
	}

}
