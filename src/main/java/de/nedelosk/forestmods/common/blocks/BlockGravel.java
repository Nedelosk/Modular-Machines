package de.nedelosk.forestmods.common.blocks;

import java.util.ArrayList;
import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import de.nedelosk.forestcore.blocks.BlockForest;
import de.nedelosk.forestcore.utils.WorldUtil;
import de.nedelosk.forestmods.api.Tabs;
import de.nedelosk.forestmods.api.crafting.WoodType;
import de.nedelosk.forestmods.common.blocks.tile.TileAsh;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

public class BlockGravel extends BlockForest implements ITileEntityProvider {

	public String[] textures = new String[] { "loam", "ash" };
	@SideOnly(Side.CLIENT)
	public IIcon[] icon;

	public BlockGravel() {
		super(Material.ground, Tabs.tabForestMods);
		this.setHardness(1.0f);
		this.setStepSound(Block.soundTypeGravel);
		this.setHarvestLevel("shovel", 1, 0);
		this.setHarvestLevel("shovel", 1, 1);
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
		for ( int i = 0; i < this.icon.length; ++i ) {
			this.icon[i] = iconRegister.registerIcon("forestday:" + textures[i]);
		}
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void getSubBlocks(Item par1, CreativeTabs par2CreativeTabs, List par3List) {
		for ( int i = 0; i < 1; i++ ) {
			par3List.add(new ItemStack(par1, 1, i));
		}
	}

	@Override
	public void breakBlock(World world, int x, int y, int z, Block block, int metadata) {
		TileEntity tile = world.getTileEntity(x, y, z);
		if (tile instanceof TileAsh) {
			TileAsh ash = (TileAsh) tile;
			if (ash.getWoodTypes() != null) {
				for ( WoodType type : ash.getWoodTypes() ) {
					WorldUtil.dropItem(world, x, y, z, type.getCharcoalDropps());
				}
			}
		}
		world.removeTileEntity(x, y, z);
		super.breakBlock(world, x, y, z, block, metadata);
	}

	@Override
	public ArrayList<ItemStack> getDrops(World world, int x, int y, int z, int metadata, int fortune) {
		if (metadata == 1) {
			return new ArrayList<>();
		}
		return super.getDrops(world, x, y, z, metadata, fortune);
	}

	@Override
	public TileEntity createNewTileEntity(World p_149915_1_, int p_149915_2_) {
		if (p_149915_2_ == 1) {
			return new TileAsh();
		}
		return null;
	}

	@Override
	public boolean onBlockEventReceived(World p_149696_1_, int p_149696_2_, int p_149696_3_, int p_149696_4_, int p_149696_5_, int p_149696_6_) {
		super.onBlockEventReceived(p_149696_1_, p_149696_2_, p_149696_3_, p_149696_4_, p_149696_5_, p_149696_6_);
		TileEntity tileentity = p_149696_1_.getTileEntity(p_149696_2_, p_149696_3_, p_149696_4_);
		return tileentity != null ? tileentity.receiveClientEvent(p_149696_5_, p_149696_6_) : false;
	}
}
