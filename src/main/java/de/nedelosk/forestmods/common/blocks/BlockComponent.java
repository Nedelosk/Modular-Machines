package de.nedelosk.forestmods.common.blocks;

import java.util.ArrayList;
import java.util.List;

import com.google.common.collect.Lists;

import de.nedelosk.forestmods.common.core.TabModularMachines;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.IBlockAccess;

public class BlockComponent extends BlockForest {

	public ArrayList<List> metas = Lists.newArrayList();
	public String name;

	public BlockComponent(Material material, String name) {
		super(material, TabModularMachines.tabComponents);
		this.setBlockName("component." + name);
		this.name = name;
	}

	public BlockComponent addMetaData(int color, String name, String... oreDict) {
		metas.add(Lists.newArrayList(color, name, oreDict));
		return this;
	}

	@Override
	public void registerBlockIcons(IIconRegister IIconRegister) {
		blockIcon = IIconRegister.registerIcon("forestmods:components/" + name);
	}

	@Override
	public int getRenderColor(int meta) {
		return (int) metas.get(meta).get(0);
	}

	@Override
	public int colorMultiplier(IBlockAccess world, int x, int y, int z) {
		int meta = world.getBlockMetadata(x, y, z);
		return (int) metas.get(meta).get(0);
	}

	@Override
	public void getSubBlocks(Item item, CreativeTabs tab, List subItems) {
		for(int i = 0; i < metas.size(); i++) {
			subItems.add(new ItemStack(item, 1, i));
		}
	}
}
