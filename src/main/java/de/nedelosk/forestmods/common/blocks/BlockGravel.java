package de.nedelosk.forestmods.common.blocks;

import java.util.ArrayList;
import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import de.nedelosk.forestcore.blocks.BlockForest;
import de.nedelosk.forestmods.api.Tabs;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

public class BlockGravel extends BlockForest {

	public String[] textures = new String[] { "loam", "ash" };
	@SideOnly(Side.CLIENT)
	public IIcon[] icon;

	public BlockGravel() {
		super(Material.ground, Tabs.tabForestMods);
		this.setHardness(1.0f);
		this.setStepSound(Block.soundTypeGravel);
		this.setHarvestLevel("shovel", 0);
		this.setBlockName("gravel");
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int side, int meta) {
		return icon[meta];
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister iconRegister) {
		this.icon = new IIcon[textures.length];
		for(int i = 0; i < this.icon.length; ++i) {
			this.icon[i] = iconRegister.registerIcon("forestmods:" + textures[i]);
		}
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void getSubBlocks(Item par1, CreativeTabs par2CreativeTabs, List par3List) {
		for(int i = 0; i < 1; i++) {
			par3List.add(new ItemStack(par1, 1, i));
		}
	}

	@Override
	public ArrayList<ItemStack> getDrops(World world, int x, int y, int z, int metadata, int fortune) {
		if (metadata == 1) {
			return new ArrayList<>();
		}
		return super.getDrops(world, x, y, z, metadata, fortune);
	}
}
