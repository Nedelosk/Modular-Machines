package nedelosk.nedeloskcore.utils;

import java.util.Random;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.oredict.OreDictionary;

public class ItemUtils {

	public static void dropItems(World world, int x, int y, int z)
	{
		Random rand = new Random();
		TileEntity tile = world.getTileEntity(x, y, z);
		if(tile == null || !(tile instanceof IInventory))
			return;
		IInventory inventory = (IInventory) tile;
		for(int i = 0; i < inventory.getSizeInventory();i++)
		{
			ItemStack item = inventory.getStackInSlot(i);
			if(item != null && item.stackSize > 0)
			{
				float rx = rand.nextFloat() * 0.8F + 0.1F;
				float ry = rand.nextFloat() * 0.8F + 0.1F;
				float rz = rand.nextFloat() * 0.8F + 0.1F;
				EntityItem entityItem = new EntityItem(world, x, y, z, new ItemStack(item.getItem(), item.stackSize, item.getItemDamage()));
				world.spawnEntityInWorld(entityItem);
				inventory.setInventorySlotContents(i, null);
			}
		}
	}
	
	public static boolean isIdenticalItem(ItemStack lhs, ItemStack rhs) {
		if (lhs == null || rhs == null) {
			return false;
		}

		if (lhs.getItem() != rhs.getItem()) {
			return false;
		}

		if (lhs.getItemDamage() != OreDictionary.WILDCARD_VALUE) {
			if (lhs.getItemDamage() != rhs.getItemDamage()) {
				return false;
			}
		}

		return ItemStack.areItemStackTagsEqual(lhs, rhs);
	}
}
