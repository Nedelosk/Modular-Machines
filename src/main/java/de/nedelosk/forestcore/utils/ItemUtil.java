package de.nedelosk.forestcore.utils;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

public class ItemUtil {

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

	public static boolean isItemEqual(ItemStack a, ItemStack b) {
		return isItemEqual(a, b, true, true);
	}

	public static boolean isItemEqualIgnoreNBT(ItemStack a, ItemStack b) {
		return isItemEqual(a, b, true, false);
	}

	public static boolean isItemEqual(final ItemStack a, final ItemStack b, final boolean matchDamage, final boolean matchNBT) {
		if (a == null || b == null) {
			return false;
		}
		if (a.getItem() != b.getItem()) {
			return false;
		}
		if (matchNBT && !ItemStack.areItemStackTagsEqual(a, b)) {
			return false;
		}
		if (matchDamage && a.getHasSubtypes()) {
			if (isWildcard(a) || isWildcard(b)) {
				return true;
			}
			if (a.getItemDamage() != b.getItemDamage()) {
				return false;
			}
		}
		return true;
	}

	public static boolean isWildcard(ItemStack stack) {
		return isWildcard(stack.getItemDamage());
	}

	public static boolean isWildcard(int damage) {
		return damage == -1 || damage == OreDictionary.WILDCARD_VALUE;
	}

	public static ItemStack cycleItemStack(Object input) {
		ItemStack it = null;
		if ((input instanceof ItemStack)) {
			it = (ItemStack) input;
			if ((it.getItemDamage() == 32767) && (it.getItem().getHasSubtypes())) {
				List<ItemStack> q = new ArrayList();
				it.getItem().getSubItems(it.getItem(), it.getItem().getCreativeTab(), q);
				if ((q != null) && (q.size() > 0)) {
					int md = (int) (System.currentTimeMillis() / 1000L % q.size());
					ItemStack it2 = new ItemStack(it.getItem(), 1, md);
					it2.setTagCompound(it.getTagCompound());
					it = it2;
				}
			} else if ((it.getItemDamage() == 32767) && (it.isItemStackDamageable())) {
				int md = (int) (System.currentTimeMillis() / 10L % it.getMaxDamage());
				ItemStack it2 = new ItemStack(it.getItem(), 1, md);
				it2.setTagCompound(it.getTagCompound());
				it = it2;
			}
		} else if ((input instanceof ArrayList)) {
			ArrayList<ItemStack> q = (ArrayList) input;
			if ((q != null) && (q.size() > 0)) {
				int idx = (int) (System.currentTimeMillis() / 1000L % q.size());
				it = cycleItemStack(q.get(idx));
			}
		} else if ((input instanceof String)) {
			ArrayList<ItemStack> q = OreDictionary.getOres((String) input);
			if ((q != null) && (q.size() > 0)) {
				int idx = (int) (System.currentTimeMillis() / 1000L % q.size());
				it = cycleItemStack(q.get(idx));
			}
		}
		return it;
	}
}
