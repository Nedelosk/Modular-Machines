package nedelosk.modularmachines.common.core.manager;

import nedelosk.forestcore.library.core.Registry;
import nedelosk.modularmachines.common.items.ItemComponent;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

public enum MMItemManager {

	// Metals
	Dusts, Dusts_Others, Ingots_Others, Nuggets_Others, Alloy_Ingots, Alloy_Nuggets,

	// Crafting
	Metallic,

	// Module
	Module_Item_Capacitor, Module_Item_Engine,

	Producers,

	// Components
	Component_Connection_Wires, Component_Rods, Component_Gears, Component_Plates, Component_Screws, Component_Saw_Blades,

	// Pattern
	WoodPattern;

	private Item item;

	public void registerItem(Item item) {
		this.item = item;
		Registry.registerItem(item, item.getUnlocalizedName().replace("item.", ""), "mm");
	}

	public void addMetaData(int color, String name, String... oreDict) {
		if (item instanceof ItemComponent) {
			ItemComponent.addMetaData(this, color, name, oreDict);
		}
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
