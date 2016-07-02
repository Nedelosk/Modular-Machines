package de.nedelosk.modularmachines.common.utils;

import java.util.Collection;
import java.util.Random;

import com.mojang.authlib.GameProfile;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.util.FakePlayerFactory;

public class WorldUtil {

	public static TileEntity getBlockTile(IBlockAccess world, BlockPos pos) {
		return world.getTileEntity(pos);
	}

	public static IBlockState getBlockState(IBlockAccess world, BlockPos pos) {
		return world.getBlockState(pos);
	}

	public static Block getBlock(IBlockAccess world, BlockPos pos) {
		return getBlockState(world, pos).getBlock();
	}

	public static boolean isBlockExists(World world, BlockPos pos) {
		return world.isBlockLoaded(pos);
	}

	public static EntityPlayer getPlayer(World world, GameProfile profile) {
		if (world == null) {
			throw new IllegalArgumentException("World cannot be null");
		}
		if (profile == null || profile.getName() == null) {
			return FakePlayerFactory.getMinecraft((WorldServer) world);
		}
		EntityPlayer player = world.getPlayerEntityByName(profile.getName());
		if (player != null) {
			return player;
		} else {
			return FakePlayerFactory.get((WorldServer) world, profile);
		}
	}

	public static void dropItems(World world, BlockPos pos) {
		Random rand = new Random();
		TileEntity tile = world.getTileEntity(pos);
		if (tile == null || !(tile instanceof IInventory)) {
			return;
		}
		IInventory inventory = (IInventory) tile;
		for(int i = 0; i < inventory.getSizeInventory(); i++) {
			ItemStack item = inventory.getStackInSlot(i);
			if (item != null && item.stackSize > 0) {
				dropItem(world, pos, item);
				inventory.setInventorySlotContents(i, null);
			}
		}
	}

	public static void dropItem(World world, BlockPos pos, int ID) {
		Random rand = new Random();
		TileEntity tile = world.getTileEntity(pos);
		if (tile == null || !(tile instanceof IInventory)) {
			return;
		}
		IInventory inventory = (IInventory) tile;
		ItemStack item = inventory.getStackInSlot(ID);
		dropItem(world, pos, item);
	}

	public static void dropItem(World world, BlockPos pos, ItemStack item) {
		Random rand = new Random();
		float f = rand.nextFloat() * 0.8F + 0.1F;
		float f1 = rand.nextFloat() * 0.8F + 0.1F;
		float f2 = rand.nextFloat() * 0.8F + 0.1F;
		if (!world.isRemote && item != null && world.getGameRules().getBoolean("doTileDrops") && !world.restoringBlockSnapshots && item.stackSize > 0) {
			EntityItem entityitem = new EntityItem(world, pos.getX() + f, pos.getY() + f1, pos.getZ() + f2, item.copy());
			entityitem.setPickupDelay(10);
			entityitem.motionX = (float) rand.nextGaussian() * 0.05F;
			entityitem.motionY = (float) rand.nextGaussian() * 0.05F + 0.2F;
			entityitem.motionZ = (float) rand.nextGaussian() * 0.05F;
			world.spawnEntityInWorld(entityitem);
		}
	}

	public static void dropItem(Entity entity, ItemStack stack) {
		dropItem(entity.worldObj, entity.getPosition(), stack);
	}

	public static void dropItems(World world, BlockPos pos, ItemStack[] stacks) {
		for(ItemStack stack : stacks) {
			dropItem(world, pos, stack);
		}
	}

	public static void dropItem(World world, BlockPos pos, Collection<ItemStack> items) {
		dropItems(world, pos, items.toArray(new ItemStack[items.size()]));
	}

	public static void dropItems(Entity entity, ItemStack[] stacks) {
		dropItems(entity.worldObj, entity.getPosition(), stacks);
	}
}
