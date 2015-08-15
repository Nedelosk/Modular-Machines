package nedelosk.nedeloskcore.common.core.registry;

import nedelosk.nedeloskcore.common.core.registry.NRegistry;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

public enum NCItems {

	Plan,
	Bucket_Wood,
	Bucket_Wood_Water,
	Ingots,
	Nuggets,
	Gems,
	Gears_Wood;

	private Item item;

	public void registerItem(Item item) {
		this.item = item;
		NRegistry.registerItem(item, item.getUnlocalizedName().replace("item.", ""), "nc");
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
