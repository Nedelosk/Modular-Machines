package nedelosk.forestday.common.registrys;

import nedelosk.nedeloskcore.common.core.registry.NRegistry;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

public enum FItems {

	//Materials
	nature,
	charcoal,
	crop_corn,
	
	//Campfire
	curb,
	pot,
	pot_holder,
	
	//Tools
	
	//charcoal_kiln,
	
	file_stone,
	file_iron,
	file_diamond,
	knife_stone,
	bow_and_stick,
	cutter,
	hammer,
	adze,
	adze_long,
	axe_flint,
	tool_parts;

	private Item item;

	public void registerItem(Item item) {
		this.item = item;
		NRegistry.registerItem(item, item.getUnlocalizedName(), "fd");
	}

	public boolean isItemEqual(ItemStack stack) {
		return stack != null && this.item == stack.getItem();
	}

	public boolean isItemEqual(Item i) {
		return i != null && this.item == i;
	}

	public Item item() {
		return item;
	}

	public ItemStack getWildcard() {
		return getItemStack(1, OreDictionary.WILDCARD_VALUE);
	}

	public ItemStack getItemStack() {
		return getItemStack(1, 0);
	}

	public ItemStack getItemStack(int qty) {
		return getItemStack(qty, 0);
	}

	public ItemStack getItemStack(int qty, int meta) {
		if (item == null) {
			return null;
		}
		return new ItemStack(item, qty, meta);
	}
}
