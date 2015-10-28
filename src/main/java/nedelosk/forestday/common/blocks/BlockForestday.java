package nedelosk.forestday.common.blocks;

import nedelosk.forestday.common.core.registry.FRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;

public class BlockForestday extends Block {

	private String name;

	public BlockForestday(String name, Material material, CreativeTabs tab) {
		super(material);
		this.name = name;
		this.setCreativeTab(tab);
		this.setBlockName(FRegistry.setUnlocalizedBlockName(name, "fd"));
	}

	@Override
	public String getUnlocalizedName() {
		return name;
	}

}
