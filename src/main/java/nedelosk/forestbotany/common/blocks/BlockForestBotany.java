package nedelosk.forestbotany.common.blocks;

import nedelosk.forestbotany.common.core.ForestBotanyTab;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;

public abstract class BlockForestBotany extends BlockContainer {

	protected BlockForestBotany(Material mat, String name) {
		super(mat);
		this.setBlockName("fb." + name);
		this.setCreativeTab(ForestBotanyTab.instance);
		this.setHardness(2.0F);
		this.setResistance(4.0F);
	}
	
	@Override
	public int damageDropped(int metadata) {
		return metadata;
	}

}
