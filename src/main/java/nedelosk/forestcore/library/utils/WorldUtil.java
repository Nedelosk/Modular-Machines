package nedelosk.forestcore.library.utils;

import java.util.List;
import java.util.Random;

import com.mojang.authlib.GameProfile;

import net.minecraft.block.Block;
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
		for ( int i = 0; i < inventory.getSizeInventory(); i++ ) {
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
		if (!world.isRemote && item != null && item.stackSize > 0) {
			/*
			 * float rx = rand.nextFloat() * 0.8F + 0.1F; float ry =
			 * rand.nextFloat() * 0.8F + 0.1F; float rz = rand.nextFloat() *
			 * 0.8F + 0.1F;
			 */
			float f = 0.7F;
			double rx = rand.nextFloat() * f + (1.0F - f) * 0.5D;
			double ry = rand.nextFloat() * f + (1.0F - f) * 0.5D;
			double rz = rand.nextFloat() * f + (1.0F - f) * 0.5D;
			EntityItem entityItem = new EntityItem(world, x, y, z, item.copy());
			world.spawnEntityInWorld(entityItem);
			item = null;
		}
	}

	public static void dropItem(World world, int x, int y, int z, ItemStack[] stacks) {
		for ( ItemStack stack : stacks ) {
			dropItem(world, x, y, z, stack);
		}
	}

	public static void dropItem(World world, int x, int y, int z, List<ItemStack> items) {
		dropItem(world, x, y, z, items.toArray(new ItemStack[items.size()]));
	}
}
