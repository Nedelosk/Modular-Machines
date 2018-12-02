package modularmachines.common.utils;

import javax.annotation.Nullable;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;

import net.minecraftforge.common.util.Constants;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.oredict.OreDictionary;

public class ItemUtil {
	
	public static void setCount(ItemStack itemStack, int count) {
		itemStack.setCount(count);
	}
	
	public static int getCount(ItemStack itemStack) {
		return itemStack.getCount();
	}
	
	public static void shrink(ItemStack itemStack, int quantity) {
		itemStack.shrink(quantity);
	}
	
	public static void grow(ItemStack itemStack, int quantity) {
		itemStack.grow(quantity);
	}
	
	public static boolean isNotEmpty(ItemStack itemStack) {
		return itemStack != null && !itemStack.isEmpty();
	}
	
	public static boolean isEmpty(ItemStack itemStack) {
		return itemStack == null || itemStack.isEmpty();
	}
	
	public static ItemStack empty() {
		return ItemStack.EMPTY;
	}
	
	public static int[] getSlots(IItemHandler handler) {
		int[] slots = new int[handler.getSlots()];
		for (int i = 0; i < slots.length; i++) {
			slots[i] = i;
		}
		return slots;
	}
	
	/*public static void transferStacks(ModuleTransfer<IItemHandler> module, ItemTransferCycle cycle) {
		IItemHandler startHandler = module.getHandler(cycle.getStartHandler());
		ItemTransferHelper helper = new ItemTransferHelper(module.getHandler(cycle.getEndHandler()), cycle.getInsertSlots());
		int amount = cycle.getAmount();
		int[] slots = cycle.getSlots();
		for (int i = 0; i < slots.length; i++) {
			int slotIndex = slots[i];
			ItemStack targetStack = startHandler.extractItem(slotIndex, amount, true);
			if (!targetStack.isEmpty() && (cycle.getFilter() == null || cycle.getFilter().test(targetStack))) {
				int extractStackSize = targetStack.getCount();
				ItemStack remaining = helper.tryAddStack(targetStack);
				if (!remaining.isEmpty()) {
					extractStackSize -= remaining.getCount();
				}
				if (extractStackSize > 0) {
					ItemStack extracted = startHandler.extractItem(slotIndex, extractStackSize, false);
					helper.addStack(extracted);
				}
			}
		}
	}*/
	
	public static boolean isIdenticalItem(ItemStack lhs, ItemStack rhs) {
		return isIdenticalItem(lhs, rhs, false);
	}
	
	public static boolean isIdenticalItem(ItemStack lhs, ItemStack rhs, boolean ignorNBT) {
		return isIdenticalItem(lhs, rhs, ignorNBT, false);
	}
	
	public static boolean isIdenticalItem(ItemStack lhs, ItemStack rhs, boolean ignorNBT, boolean ignorDisplay) {
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
		if (!ignorNBT) {
			if (ignorDisplay) {
				if (rhs.hasTagCompound()) {
					lhs = lhs.copy();
					rhs.getTagCompound().removeTag("display");
				}
			}
			return ItemStack.areItemStackTagsEqual(lhs, rhs);
		}
		return true;
	}
	
	public static boolean isCraftingEquivalent(ItemStack base, ItemStack comparison) {
		if (base.isEmpty() || comparison.isEmpty()) {
			return false;
		}
		if (base.getItem() != comparison.getItem()) {
			return false;
		}
		if (base.getItemDamage() != OreDictionary.WILDCARD_VALUE) {
			if (base.getItemDamage() != comparison.getItemDamage()) {
				return false;
			}
		}
		if (!base.hasTagCompound() || base.getTagCompound().isEmpty()) {
			return true;
		} else {
			return ItemStack.areItemStackTagsEqual(base, comparison);
		}
	}
	
	public static boolean isEquivalent(ItemStack base, ItemStack comparison) {
		if (base.isEmpty() || comparison.isEmpty()) {
			return false;
		}
		if (base.getItem() != comparison.getItem()) {
			return false;
		}
		if (base.getItemDamage() != OreDictionary.WILDCARD_VALUE) {
			if (base.getItemDamage() != comparison.getItemDamage()) {
				return false;
			}
		}
		if (!base.hasTagCompound() || base.getTagCompound().isEmpty()) {
			return true;
		} else {
			return containsNBT(comparison.getTagCompound(), base.getTagCompound());
		}
		
	}
	
	private static boolean containsNBT(@Nullable NBTTagCompound base, @Nullable NBTTagCompound comparison) {
		if (comparison == null || comparison.isEmpty()) {
			return true;
		}
		if (base == null || base.isEmpty()) {
			return false;
		}
		for (String nbtKey : comparison.getKeySet()) {
			NBTBase comparisonNbt = comparison.getTag(nbtKey);
			NBTBase baseNbt = base.getTag(nbtKey);
			if (baseNbt == null || baseNbt.getId() != comparisonNbt.getId()) {
				return false;
			}
			if (comparisonNbt.getId() == Constants.NBT.TAG_COMPOUND) {
				if (!containsNBT((NBTTagCompound) comparisonNbt, (NBTTagCompound) baseNbt)) {
					return false;
				}
			} else if (!baseNbt.equals(comparisonNbt)) {
				return false;
			}
		}
		return true;
	}
}
