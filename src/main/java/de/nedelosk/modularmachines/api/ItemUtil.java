package de.nedelosk.modularmachines.api;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.oredict.OreDictionary;

public class ItemUtil {

	public static boolean isIdenticalItem(ItemStack lhs, ItemStack rhs) {
		return isIdenticalItem(lhs, rhs, false);
	}

	public static boolean isIdenticalItem(ItemStack lhs, ItemStack rhs, boolean ignorNBT) {
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
		return ignorNBT || ItemStack.areItemStackTagsEqual(lhs, rhs);
	}

	public static boolean isCraftingEquivalent(ItemStack base, ItemStack comparison) {
		if (base == null || comparison == null) {
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

		if (!base.hasTagCompound() || base.getTagCompound().hasNoTags()) {
			return true;
		} else {
			return ItemStack.areItemStackTagsEqual(base, comparison);
		}
	}

	@Nullable
	public static String getStackToString(@Nonnull ItemStack stack) {
		Item item = stack.getItem();
		if (item == null) {
			return null;
		}

		ResourceLocation itemName = item.getRegistryName();
		if (itemName == null) {
			return null;
		}
		String name = itemName.toString() + ":";

		NBTTagCompound serializedNbt = stack.serializeNBT();
		NBTTagCompound nbtTagCompound = serializedNbt.getCompoundTag("tag").copy();
		if (serializedNbt.hasKey("ForgeCaps")) {
			if (nbtTagCompound == null) {
				nbtTagCompound = new NBTTagCompound();
			}
			nbtTagCompound.setTag("ForgeCaps", serializedNbt.getCompoundTag("ForgeCaps"));
		}
		if (nbtTagCompound != null && !nbtTagCompound.hasNoTags()) {
			name +=':'+nbtTagCompound.toString();
		}
		return name;
	}
}
