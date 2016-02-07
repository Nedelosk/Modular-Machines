package de.nedelosk.forestmods.common.blocks;

import java.util.List;

import de.nedelosk.forestcore.blocks.BlockForest;
import de.nedelosk.forestmods.common.core.TabModularMachines;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

public class BlockCasing extends BlockForest {

	public IIcon[][] icons;
	public String[] textures;

	public BlockCasing(String[] textures) {
		super(Material.ground, TabModularMachines.tabModules);
		this.textures = textures;
		setBlockName("casing");
	}

	@Override
	public void registerBlockIcons(IIconRegister IIconRegister) {
		icons = new IIcon[textures.length][3];
		for ( int i = 0; i < textures.length; i++ ) {
			String texture = textures[i];
			icons[i][0] = IIconRegister.registerIcon("modularmachines:casing/" + texture + "_down");
			icons[i][1] = IIconRegister.registerIcon("modularmachines:casing/" + texture + "_top");
			icons[i][2] = IIconRegister.registerIcon("modularmachines:casing/" + texture + "_side");
		}
	}

	@Override
	public IIcon getIcon(int side, int meta) {
		if (side > 1) {
			return icons[meta][2];
		} else {
			return icons[meta][side];
		}
	}

	@Override
	public void getSubBlocks(Item item, CreativeTabs tab, List subItems) {
		for ( int i = 0; i < textures.length; i++ ) {
			subItems.add(new ItemStack(item, 1, i));
		}
	}
}
