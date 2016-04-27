package de.nedelosk.forestmods.common.multiblocks.charcoal;

import java.util.ArrayList;
import java.util.List;

import de.nedelosk.forestmods.common.blocks.BlockCharcoalKiln;
import de.nedelosk.forestmods.common.core.BlockManager;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.oredict.OreDictionary;

public class CharcoalKilnHelper {

	public static ItemStack createKiln(ItemStack woodStack) {
		if (woodStack != null) {
			ItemStack stack = new ItemStack(BlockManager.blockCharcoalKiln);
			NBTTagCompound nbtTag = new NBTTagCompound();
			woodStack.writeToNBT(nbtTag);
			stack.setTagCompound(nbtTag);
			return stack;
		}
		return null;
	}

	public static ItemStack getFromKiln(ItemStack stack) {
		Block block = Block.getBlockFromItem(stack.getItem());
		if (stack != null && stack.getItem() != null && stack.hasTagCompound() && block != null && block instanceof BlockCharcoalKiln) {
			return ItemStack.loadItemStackFromNBT(stack.getTagCompound());
		}
		return null;
	}

	public static boolean isWood(ItemStack stack) {
		if (stack == null || stack.getItem() == null) {
			return false;
		}
		Block block = Block.getBlockFromItem(stack.getItem());
		if (block == null) {
			return false;
		}
		int woodOreID = OreDictionary.getOreID("logWood");
		for(int oreID : OreDictionary.getOreIDs(stack)) {
			if (oreID == woodOreID) {
				return true;
			}
		}
		return false;
	}

	public static List<ItemStack> getWoods() {
		List<ItemStack> woodStacks = new ArrayList();
		for(ItemStack oreStack : OreDictionary.getOres("logWood")) {
			if (oreStack == null || oreStack.getItem() == null) {
				continue;
			}
			Block block = Block.getBlockFromItem(oreStack.getItem());
			if (block == null) {
				continue;
			}
			if (oreStack.getHasSubtypes()) {
				List<ItemStack> subItems = new ArrayList();
				oreStack.getItem().getSubItems(oreStack.getItem(), oreStack.getItem().getCreativeTab(), subItems);
				for(ItemStack stack : subItems) {
					if (isWood(stack)) {
						woodStacks.add(stack.copy());
					}
				}
			} else {
				if (isWood(oreStack)) {
					woodStacks.add(oreStack.copy());
				}
			}
		}
		return woodStacks;
	}

	public static List<ItemStack> createKilnItems() {
		List<ItemStack> kilns = new ArrayList();
		for(ItemStack woodStack : getWoods()) {
			kilns.add(CharcoalKilnHelper.createKiln(woodStack));
		}
		return kilns;
	}
}
