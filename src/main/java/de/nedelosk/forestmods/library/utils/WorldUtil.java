package de.nedelosk.forestmods.library.utils;

import java.util.Collection;
import java.util.Random;

import com.mojang.authlib.GameProfile;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.util.FakePlayerFactory;
import net.minecraftforge.common.util.ForgeDirection;

public class WorldUtil {

	public static TileEntity getTileEntityOnSide(World world, int x, int y, int z, ForgeDirection side) {
		int sx = getXOnSide(x, side);
		int sy = getYOnSide(y, side);
		int sz = getZOnSide(z, side);
		if (isBlockExists(world, sx, sy, sz) && getBlock(world, sx, sy, sz) != Blocks.air) {
			return getBlockTile(world, sx, sy, sz);
		}
		return null;
	}

	public static TileEntity getBlockTile(IBlockAccess world, int x, int y, int z) {
		return world.getTileEntity(x, y, z);
	}

	public static Block getBlock(IBlockAccess world, int x, int y, int z) {
		return world.getBlock(x, y, z);
	}

	public static boolean areCoordinatesOnSide(int x, int y, int z, ForgeDirection side, int xCoord, int yCoord, int zCoord) {
		return x + side.offsetX == xCoord && y + side.offsetY == yCoord && z + side.offsetZ == zCoord;
	}

	public static boolean isBlockExists(World world, int x, int y, int z) {
		return world.blockExists(x, y, z);
	}

	public static int getXOnSide(int x, ForgeDirection side) {
		return x + side.offsetX;
	}

	public static int getYOnSide(int y, ForgeDirection side) {
		return y + side.offsetY;
	}

	public static int getZOnSide(int z, ForgeDirection side) {
		return z + side.offsetZ;
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

	public static void dropItems(World world, int x, int y, int z) {
		Random rand = new Random();
		TileEntity tile = world.getTileEntity(x, y, z);
		if (tile == null || !(tile instanceof IInventory)) {
			return;
		}
		IInventory inventory = (IInventory) tile;
		for(int i = 0; i < inventory.getSizeInventory(); i++) {
			ItemStack item = inventory.getStackInSlot(i);
			if (item != null && item.stackSize > 0) {
				dropItem(world, x, y, z, item);
				inventory.setInventorySlotContents(i, null);
			}
		}
	}

	public static void dropItem(World world, int x, int y, int z, int ID) {
		Random rand = new Random();
		TileEntity tile = world.getTileEntity(x, y, z);
		if (tile == null || !(tile instanceof IInventory)) {
			return;
		}
		IInventory inventory = (IInventory) tile;
		ItemStack item = inventory.getStackInSlot(ID);
		dropItem(world, x, y, z, item);
	}

	public static void dropItem(World world, int x, int y, int z, ItemStack item) {
		Random rand = new Random();
		float f = rand.nextFloat() * 0.8F + 0.1F;
		float f1 = rand.nextFloat() * 0.8F + 0.1F;
		float f2 = rand.nextFloat() * 0.8F + 0.1F;
		if (!world.isRemote && item != null && world.getGameRules().getGameRuleBooleanValue("doTileDrops") && !world.restoringBlockSnapshots
				&& item.stackSize > 0) {
			EntityItem entityitem = new EntityItem(world, x + f, y + f1, z + f2, item.copy());
			entityitem.delayBeforeCanPickup = 10;
			entityitem.motionX = (float) rand.nextGaussian() * 0.05F;
			entityitem.motionY = (float) rand.nextGaussian() * 0.05F + 0.2F;
			entityitem.motionZ = (float) rand.nextGaussian() * 0.05F;
			world.spawnEntityInWorld(entityitem);
		}
	}

	public static void dropItem(Entity entity, ItemStack stack) {
		dropItem(entity.worldObj, (int)entity.posX, (int)entity.posY, (int)entity.posZ, stack);
	}

	public static void dropItems(World world, int x, int y, int z, ItemStack[] stacks) {
		for(ItemStack stack : stacks) {
			dropItem(world, x, y, z, stack);
		}
	}

	public static void dropItem(World world, int x, int y, int z, Collection<ItemStack> items) {
		dropItems(world, x, y, z, items.toArray(new ItemStack[items.size()]));
	}

	public static void dropItems(Entity entity, ItemStack[] stacks) {
		dropItems(entity.worldObj, (int)entity.posX, (int)entity.posY, (int)entity.posZ, stacks);
	}
}
