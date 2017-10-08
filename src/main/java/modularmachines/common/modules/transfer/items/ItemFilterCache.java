/*
 * Copyright (c) 2017 Nedelosk
 *
 * This work (the MOD) is licensed under the "MIT" License, see LICENSE for details.
 */
package modularmachines.common.modules.transfer.items;

import javax.annotation.Nonnull;

import java.util.HashSet;
import java.util.Set;

import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;

import net.minecraftforge.oredict.OreDictionary;

public class ItemFilterCache {
	private boolean matchDamage = true;
	private boolean oredictMode = false;
	private boolean blacklistMode = true;
	private boolean nbtMode = false;
	private NonNullList<ItemStack> stacks;
	private Set<Integer> oredictMatches = new HashSet<>();
	
	public ItemFilterCache(boolean matchDamage, boolean oredictMode, boolean blacklistMode, boolean nbtMode, @Nonnull NonNullList<ItemStack> stacks) {
		this.matchDamage = matchDamage;
		this.oredictMode = oredictMode;
		this.blacklistMode = blacklistMode;
		this.nbtMode = nbtMode;
		this.stacks = stacks;
		for (ItemStack s : stacks) {
			for (int id : OreDictionary.getOreIDs(s)) {
				oredictMatches.add(id);
			}
		}
	}
	
	public boolean match(ItemStack stack) {
		if(!stack.isEmpty()) {
			boolean match = false;
			
			if (oredictMode) {
				int[] oreIDs = OreDictionary.getOreIDs(stack);
				if (oreIDs.length == 0) {
					match = itemMatches(stack);
				} else {
					for (int id : oreIDs) {
						if (oredictMatches.contains(id)) {
							match = true;
							break;
						}
					}
				}
			} else {
				match = itemMatches(stack);
			}
			return match != blacklistMode;
		}
		return false;
	}
	
	private boolean itemMatches(ItemStack stack) {
		if (stacks != null) {
			for (ItemStack itemStack : stacks) {
				if (matchDamage && itemStack.getMetadata() != stack.getMetadata()) {
					continue;
				}
				if (nbtMode && !ItemStack.areItemStackTagsEqual(itemStack, stack)) {
					continue;
				}
				if (itemStack.getItem().equals(stack.getItem())) {
					return true;
				}
			}
		}
		return false;
	}
}
