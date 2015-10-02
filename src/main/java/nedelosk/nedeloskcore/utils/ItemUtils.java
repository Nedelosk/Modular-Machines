package nedelosk.nedeloskcore.utils;

import java.util.ArrayList;
import java.util.List;
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
	
	public static void dropItem(World world, int x, int y, int z, int ID)
	{
		Random rand = new Random();
		TileEntity tile = world.getTileEntity(x, y, z);
		if(tile == null || !(tile instanceof IInventory))
			return;
		IInventory inventory = (IInventory) tile;
		ItemStack item = inventory.getStackInSlot(ID);
		if(item != null && item.stackSize > 0)
		{
			float rx = rand.nextFloat() * 0.8F + 0.1F;
			float ry = rand.nextFloat() * 0.8F + 0.1F;
			float rz = rand.nextFloat() * 0.8F + 0.1F;
			EntityItem entityItem = new EntityItem(world, x, y, z, new ItemStack(item.getItem(), item.stackSize, item.getItemDamage()));
			world.spawnEntityInWorld(entityItem);
			inventory.setInventorySlotContents(ID, null);
		}
	}
	
	public static void dropItem(World world, int x, int y, int z, ItemStack item)
	{
		Random rand = new Random();
		if(item != null && item.stackSize > 0)
		{
			float rx = rand.nextFloat() * 0.8F + 0.1F;
			float ry = rand.nextFloat() * 0.8F + 0.1F;
			float rz = rand.nextFloat() * 0.8F + 0.1F;
			EntityItem entityItem = new EntityItem(world, x, y, z, new ItemStack(item.getItem(), item.stackSize, item.getItemDamage()));
			world.spawnEntityInWorld(entityItem);
			item = null;
		}
	}
	
	public static void dropItem(World world, int x, int y, int z, ItemStack[] items)
	{
		for(int i = 0;i < items.length;i++)
		{
			ItemStack item = items[i];
			Random rand = new Random();
			if(item != null && item.stackSize > 0)
			{
				float rx = rand.nextFloat() * 0.8F + 0.1F;
				float ry = rand.nextFloat() * 0.8F + 0.1F;
				float rz = rand.nextFloat() * 0.8F + 0.1F;
				EntityItem entityItem = new EntityItem(world, x, y, z, new ItemStack(item.getItem(), item.stackSize, item.getItemDamage()));
				world.spawnEntityInWorld(entityItem);
				item = null;
			}
		}
	}
	
	public static void dropItem(World world, int x, int y, int z, List<ItemStack> items)
	{
		for(int i = 0;i < items.size();i++)
		{
			ItemStack item = items.get(i);
			Random rand = new Random();
			if(item != null && item.stackSize > 0)
			{
				float rx = rand.nextFloat() * 0.8F + 0.1F;
				float ry = rand.nextFloat() * 0.8F + 0.1F;
				float rz = rand.nextFloat() * 0.8F + 0.1F;
				EntityItem entityItem = new EntityItem(world, x, y, z, new ItemStack(item.getItem(), item.stackSize, item.getItemDamage()));
				world.spawnEntityInWorld(entityItem);
				item = null;
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
	
	  public static ItemStack cycleItemStack(Object input)
	  {
	    ItemStack it = null;
	    if ((input instanceof ItemStack))
	    {
	      it = (ItemStack)input;
	      if ((it.getItemDamage() == 32767) && (it.getItem().getHasSubtypes()))
	      {
	        List<ItemStack> q = new ArrayList();
	        it.getItem().getSubItems(it.getItem(), it.getItem().getCreativeTab(), q);
	        if ((q != null) && (q.size() > 0))
	        {
	          int md = (int)(System.currentTimeMillis() / 1000L % q.size());
	          ItemStack it2 = new ItemStack(it.getItem(), 1, md);
	          it2.setTagCompound(it.getTagCompound());
	          it = it2;
	        }
	      }
	      else if ((it.getItemDamage() == 32767) && (it.isItemStackDamageable()))
	      {
	        int md = (int)(System.currentTimeMillis() / 10L % it.getMaxDamage());
	        ItemStack it2 = new ItemStack(it.getItem(), 1, md);
	        it2.setTagCompound(it.getTagCompound());
	        it = it2;
	      }
	    }
	    else if ((input instanceof ArrayList))
	    {
	      ArrayList<ItemStack> q = (ArrayList)input;
	      if ((q != null) && (q.size() > 0))
	      {
	        int idx = (int)(System.currentTimeMillis() / 1000L % q.size());
	        it = cycleItemStack(q.get(idx));
	      }
	    }
	    else if ((input instanceof String))
	    {
	      ArrayList<ItemStack> q = OreDictionary.getOres((String)input);
	      if ((q != null) && (q.size() > 0))
	      {
	        int idx = (int)(System.currentTimeMillis() / 1000L % q.size());
	        it = cycleItemStack(q.get(idx));
	      }
	    }
	    return it;
	  }
}
