package de.nedelosk.forestmods.common.blocks;

import java.util.ArrayList;
import java.util.List;

import com.google.common.collect.Lists;

import de.nedelosk.forestmods.common.blocks.tile.TileModular;
import de.nedelosk.forestmods.common.core.ForestMods;
import de.nedelosk.forestmods.common.core.TabModularMachines;
import de.nedelosk.forestmods.common.modular.Modular;
import de.nedelosk.forestmods.library.modular.IModularTileEntity;
import de.nedelosk.forestmods.library.modules.IModule;
import de.nedelosk.forestmods.library.utils.WorldUtil;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class BlockModularMachine extends BlockContainerForest {

	public BlockModularMachine() {
		super(Material.iron);
		setStepSound(soundTypeMetal);
		setHardness(2.0F);
		setHarvestLevel("pickaxe", 1);
		setBlockName("modular");
		setBlockTextureName("anvil_base");
		setCreativeTab(TabModularMachines.tabModules);
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
	public TileEntity createNewTileEntity(World world, int meta) {
		return new TileModular();
	}

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float par7, float par8, float par9) {
		if (!world.isRemote) {
			TileEntity tile = world.getTileEntity(x, y, z);
			if (tile instanceof TileModular) {
				TileModular modularMachine = (TileModular) tile;
				if (modularMachine.getModular() == null) {
					return false;
				}
				player.openGui(ForestMods.instance, 0, player.worldObj, x, y, z);
				return true;
			}
		}
		return false;
	}

	@Override
	public void getSubBlocks(Item p_149666_1_, CreativeTabs p_149666_2_, List p_149666_3_) {
	}

	@Override
	public void breakBlock(World world, int x, int y, int z, Block block, int meta) {
		TileEntity tile = world.getTileEntity(x, y, z);
		if (tile instanceof IModularTileEntity) {
			IModularTileEntity modular = (IModularTileEntity) tile;
			if (modular.getModular() != null) {
				List<ItemStack> drops = Lists.newArrayList();
				for(IModule module : modular.getModular().getModules()) {
					if (module != null) {
						drops.add(module.getDropItem().copy());
					}
				}
				WorldUtil.dropItem(world, x, y, z, drops);
			}
		}
		super.breakBlock(world, x, y, z, block, meta);
	}

	@Override
	public ItemStack getPickBlock(MovingObjectPosition target, World world, int x, int y, int z) {
		ItemStack stack = super.getPickBlock(target, world, x, y, z);
		TileEntity tile = world.getTileEntity(x, y, z);
		if (tile instanceof IModularTileEntity) {
			IModularTileEntity modular = (IModularTileEntity) tile;
			NBTTagCompound nbtTag = new NBTTagCompound();
			modular.writeToNBT(nbtTag);
			stack.setTagCompound(nbtTag);
		}
		return stack;
	}

	@Override
	public ArrayList<ItemStack> getDrops(World world, int x, int y, int z, int metadata, int fortune) {
		return new ArrayList<ItemStack>();
	}

	@Override
	public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int x, int y, int z) {
		int md = world.getBlockMetadata(x, y, z);
		if (md == 1) {
			return AxisAlignedBB.getBoundingBox(x, y, z, (double) x + 1, y + 0.8125D, (double) z + 1);
		} else {
			return AxisAlignedBB.getBoundingBox(x, y, z, x + 1D, y + 1D, z + 1D);
		}
	}

	@Override
	public AxisAlignedBB getSelectedBoundingBoxFromPool(World world, int x, int y, int z) {
		int md = world.getBlockMetadata(x, y, z);
		if (md == 1) {
			return AxisAlignedBB.getBoundingBox(x, y, z, (double) x + 1, y + 0.8125D, (double) z + 1);
		} else {
			return AxisAlignedBB.getBoundingBox(x, y, z, x + 1D, y + 1D, z + 1D);
		}
	}

	@Override
	public void setBlockBoundsBasedOnState(IBlockAccess iblockaccess, int x, int y, int z) {
		int md = iblockaccess.getBlockMetadata(x, y, z);
		if (md == 1) {
			setBlockBounds(0F, 0F, 0F, 1F, 0.8125F, 1F);
		} else {
			setBlockBounds(0F, 0F, 0F, 1F, 1F, 1F);
		}
	}

	@Override
	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase entity, ItemStack stack) {
		super.onBlockPlacedBy(world, x, y, z, entity, stack);
		if (world.isRemote) {
			return;
		}
		TileEntity tile = world.getTileEntity(x, y, z);
		if (tile instanceof TileModular) {
			TileModular modular = (TileModular) tile;
			modular.setModular(new Modular());
			int heading = MathHelper.floor_double(entity.rotationYaw * 4.0F / 360.0F + 0.5D) & 3;
			modular.setFacing(getFacingForHeading(heading));
			if (entity instanceof EntityPlayer) {
				EntityPlayer player = (EntityPlayer) entity;
				modular.setOwner(player.getGameProfile());
			}
			world.markBlockForUpdate(x, y, z);
		}
	}

	protected ForgeDirection getFacingForHeading(int heading) {
		switch (heading) {
			case 0:
				return ForgeDirection.NORTH;
			case 1:
				return ForgeDirection.EAST;
			case 2:
				return ForgeDirection.SOUTH;
			case 3:
			default:
				return ForgeDirection.WEST;
		}
	}
}
