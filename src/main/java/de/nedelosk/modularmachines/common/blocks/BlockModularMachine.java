package de.nedelosk.modularmachines.common.blocks;

import java.util.Collections;
import java.util.List;

import com.google.common.collect.Lists;

import de.nedelosk.modularmachines.api.modular.IModularTileEntity;
import de.nedelosk.modularmachines.api.modules.state.IModuleState;
import de.nedelosk.modularmachines.common.blocks.tile.TileModular;
import de.nedelosk.modularmachines.common.core.ModularMachines;
import de.nedelosk.modularmachines.common.core.TabModularMachines;
import de.nedelosk.modularmachines.common.modular.Modular;
import de.nedelosk.modularmachines.common.utils.WorldUtil;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockModularMachine extends BlockContainerForest {

	public BlockModularMachine() {
		super(Material.IRON);
		setSoundType(SoundType.METAL);
		setHardness(2.0F);
		setHarvestLevel("pickaxe", 1);
		setUnlocalizedName("modular");
		setCreativeTab(TabModularMachines.tabModules);
	}
	
	@Override
	public boolean isOpaqueCube(IBlockState state) {
		return false;
	}
	
	@Override
	public EnumBlockRenderType getRenderType(IBlockState state) {
		return EnumBlockRenderType.INVISIBLE;
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		return new TileModular();
	}
	
	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, ItemStack heldItem, EnumFacing side, float hitX, float hitY, float hitZ) {
		if (!world.isRemote) {
			TileEntity tile = world.getTileEntity(pos);
			if (tile instanceof TileModular) {
				TileModular modularMachine = (TileModular) tile;
				if (modularMachine.getModular() == null) {
					return false;
				}
				player.openGui(ModularMachines.instance, 0, player.worldObj, pos.getX(), pos.getY(), pos.getZ());
				return true;
			}
		}
		return false;
	}

	@Override
	public void getSubBlocks(Item p_149666_1_, CreativeTabs p_149666_2_, List p_149666_3_) {
	}

	
	@Override
	public void breakBlock(World world, BlockPos pos, IBlockState blockState) {
		TileEntity tile = world.getTileEntity(pos);
		if (tile instanceof IModularTileEntity) {
			IModularTileEntity modular = (IModularTileEntity) tile;
			if (modular.getModular() != null) {
				List<ItemStack> drops = Lists.newArrayList();
				for(IModuleState state : modular.getModular().getModuleStates()) {
					if (state != null) {
						drops.add(state.getModule().getDropItem(state).copy());
					}
				}
				WorldUtil.dropItem(world, pos, drops);
			}
		}
		super.breakBlock(world, pos, blockState);
	}
	
	@Override
	public ItemStack getPickBlock(IBlockState state, RayTraceResult target, World world, BlockPos pos, EntityPlayer player) {
		ItemStack stack = super.getPickBlock(state, target, world, pos, player);
		TileEntity tile = world.getTileEntity(pos);
		if (tile instanceof IModularTileEntity) {
			IModularTileEntity modular = (IModularTileEntity) tile;
			NBTTagCompound nbtTag = new NBTTagCompound();
			modular.writeToNBT(nbtTag);
			stack.setTagCompound(nbtTag);
		}
		return stack;
	}
	
	@Override
	public List<ItemStack> getDrops(IBlockAccess world, BlockPos pos, IBlockState state, int fortune) {
		return Collections.emptyList();
	}
	
	@Override
	public AxisAlignedBB getCollisionBoundingBox(IBlockState state, World world, BlockPos pos) {
		int x = pos.getX();
		int y = pos.getY();
		int z = pos.getZ();
		int md = getMetaFromState(state);
		if (md == 1) {
			return new AxisAlignedBB(x, y, z, (double) x + 1, y + 0.8125D, (double) z + 1);
		} else {
			return new AxisAlignedBB(x, y, z, x + 1D, y + 1D, z + 1D);
		}
	}
	
	@Override
	public AxisAlignedBB getSelectedBoundingBox(IBlockState state, World world, BlockPos pos) {
		int x = pos.getX();
		int y = pos.getY();
		int z = pos.getZ();
		int md = getMetaFromState(state);
		if (md == 1) {
			return new AxisAlignedBB(x, y, z, (double) x + 1, y + 0.8125D, (double) z + 1);
		} else {
			return new AxisAlignedBB(x, y, z, x + 1D, y + 1D, z + 1D);
		}
	}
	
	@Override
	public void onBlockPlacedBy(World world, BlockPos pos, IBlockState state, EntityLivingBase entity, ItemStack stack) {
		if (world.isRemote) {
			return;
		}
		TileEntity tile = world.getTileEntity(pos);
		if (tile instanceof TileModular) {
			TileModular modular = (TileModular) tile;
			modular.setModular(new Modular());
			int heading = MathHelper.floor_double(entity.rotationYaw * 4.0F / 360.0F + 0.5D) & 3;
			modular.setFacing(getFacingForHeading(heading));
			if (entity instanceof EntityPlayer) {
				EntityPlayer player = (EntityPlayer) entity;
				modular.setOwner(player.getGameProfile());
			}
			modular.markDirty();
		}
	}

	protected EnumFacing getFacingForHeading(int heading) {
		switch (heading) {
			case 0:
				return EnumFacing.NORTH;
			case 1:
				return EnumFacing.EAST;
			case 2:
				return EnumFacing.SOUTH;
			case 3:
			default:
				return EnumFacing.WEST;
		}
	}
}
