package de.nedelosk.modularmachines.common.blocks;

import java.util.List;

import de.nedelosk.modularmachines.common.core.TabModularMachines;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class BlockCasing extends BlockForest {

	public String[] textures;

	public BlockCasing(String[] textures) {
		super(Material.GROUND, TabModularMachines.tabModules);
		this.textures = textures;
		setUnlocalizedName("casing");
		setHarvestLevel("pickaxe", 1);
	}

	@Override
	public void getSubBlocks(Item item, CreativeTabs tab, List subItems) {
		for(int i = 0; i < textures.length; i++) {
			subItems.add(new ItemStack(item, 1, i));
		}
	}
}
