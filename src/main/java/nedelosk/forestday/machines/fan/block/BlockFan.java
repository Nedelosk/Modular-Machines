package nedelosk.forestday.machines.fan.block;

import nedelosk.forestday.api.Tabs;
import nedelosk.forestday.common.config.ForestdayConfig;
import nedelosk.forestday.machines.fan.tile.TileFan;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockFan extends BlockContainer {

	public BlockFan() {
		super(Material.iron);
		this.setBlockName("machine.fan");
			this.setBlockTextureName("forestday:fan");
		this.setCreativeTab(Tabs.tabForestdayBlocks);
	}

	@Override
	public TileEntity createNewTileEntity(World p_149915_1_, int p_149915_2_) {
		return new TileFan();
	}

}
