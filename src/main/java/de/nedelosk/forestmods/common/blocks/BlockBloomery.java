package de.nedelosk.forestmods.common.blocks;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import de.nedelosk.forestmods.common.blocks.tile.TileBloomery;
import de.nedelosk.forestmods.common.blocks.tile.TileMachineBase;
import de.nedelosk.forestmods.common.core.ForestMods;
import de.nedelosk.forestmods.common.core.ItemManager;
import de.nedelosk.forestmods.library.Tabs;
import de.nedelosk.forestmods.library.utils.WorldUtil;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockBloomery extends BlockContainerForest {

	public BlockBloomery() {
		super(Material.wood);
		setStepSound(soundTypeWood);
		setCreativeTab(Tabs.tabForestMods);
		setBlockName("bloomery");
		setHardness(0.5F);
	}

	@Override
	public void registerBlockIcons(IIconRegister IIconRegister) {
	}

	@Override
	public IIcon getIcon(int blockSide, int blockMeta) {
		return Blocks.clay.getIcon(0, 0);
	}

	@Override
	public IIcon getIcon(IBlockAccess world, int x, int y, int z, int blockSide) {
		return Blocks.clay.getIcon(0, 0);
	}

	@Override
	public ItemStack getPickBlock(MovingObjectPosition target, World world, int x, int y, int z, EntityPlayer player) {
		return new ItemStack(ItemManager.itemCampfireCurb, 1, 0);
	}

	@Override
	public ArrayList<ItemStack> getDrops(World world, int x, int y, int z, int metadata, int fortune) {
		return new ArrayList();
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void randomDisplayTick(World world, int x, int y, int z, Random random) {
		TileEntity tile = world.getTileEntity(x, y, z);
		if (tile instanceof TileBloomery && ((TileBloomery) world.getTileEntity(x, y, z)).isWorking) {
			float f = x + 0.5F;
			float f1 = y + 0.0F + random.nextFloat() * 6.0F / 16.0F;
			float f2 = z + 0.5F;
			float f3 = 0.52F;
			float f4 = random.nextFloat() * 0.6F - 0.3F;
			world.spawnParticle("smoke", f + f3 - 0.5, f1 + 0.3, f2 + f4, 0.0D, 0.0D, 0.0D);
			world.spawnParticle("smoke", f + f3 - 0.5, f1 + 0.3, f2 + f4, 0.0D, 0.0D, 0.0D);
			world.spawnParticle("smoke", f + f3 - 0.5, f1 + 0.3, f2 + f4, 0.0D, 0.0D, 0.0D);
			world.spawnParticle("flame", f + f3 - 0.5, f1 + 0.3, f2 + f4, 0.0D, 0.0D, 0.0D);
			world.spawnParticle("smoke", f + f3 - 0.5, f1, f2 + f4, 0.0D, 0.0D, 0.0D);
			world.spawnParticle("smoke", f + f3 - 0.5, f1, f2 + f4, 0.0D, 0.0D, 0.0D);
			world.spawnParticle("smoke", f + f3 - 0.5, f1, f2 + f4, 0.0D, 0.0D, 0.0D);
			world.spawnParticle("flame", f + f3 - 0.5, f1, f2 + f4, 0.0D, 0.0D, 0.0D);
			world.playSound(x + 0.5F, y + 0.5F, z + 0.5F, "fire.fire", 1.0F + random.nextFloat(), random.nextFloat() * 0.7F + 0.3F, false);
		}
	}

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float par7, float par8, float par9) {
		if (player.getHeldItem() != null) {
			ItemStack heldItem = player.getHeldItem();
			if(heldItem != null){
				TileEntity tile = world.getTileEntity(x, y, z);
				if (tile instanceof TileBloomery) {
					TileBloomery bloomery = (TileBloomery) tile;
					if(bloomery.isBurned){
					}
				}
			}
		}
		if (world.isRemote) {
			return false;
		}
		player.openGui(ForestMods.instance, 0, player.worldObj, x, y, z);
		return true;
	}

	@Override
	public boolean isOpaqueCube() {
		return false;
	}

	@Override
	public int getRenderType() {
		return -1;
	}

	@Override
	public boolean renderAsNormalBlock() {
		return false;
	}

	@Override
	public void breakBlock(World world, int x, int y, int z, Block p_149749_5_, int p_149749_6_) {
		if (!world.isRemote) {
			WorldUtil.dropItems(world, x, y, z);
		}
		super.breakBlock(world, x, y, z, p_149749_5_, p_149749_6_);
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		return new TileBloomery();
	}

	@Override
	public void getSubBlocks(Item item, CreativeTabs tab, List list) {
	}

	@Override
	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase player, ItemStack stack) {
		super.onBlockPlacedBy(world, x, y, z, player, stack);
		if (world.isRemote) {
			return;
		}
		int heading = MathHelper.floor_double(player.rotationYaw * 4.0F / 360.0F + 0.5D) & 3;
		TileMachineBase tile = (TileMachineBase) world.getTileEntity(x, y, z);
		if (player instanceof EntityPlayer) {
			tile.setOwner(((EntityPlayer) player).getGameProfile());
		}
		tile.facing = getFacingForHeading(heading);
		world.markBlockForUpdate(x, y, z);
	}

	protected short getFacingForHeading(int heading) {
		switch (heading) {
			case 0:
				return 2;
			case 1:
				return 5;
			case 2:
				return 3;
			case 3:
			default:
				return 4;
		}
	}
}
