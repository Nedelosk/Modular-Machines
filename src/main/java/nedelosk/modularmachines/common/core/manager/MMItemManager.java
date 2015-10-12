package nedelosk.modularmachines.common.core.manager;

import nedelosk.nedeloskcore.common.core.registry.NCRegistry;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

public enum MMItemManager {

	//Metals
	Dusts,
	Dusts_Others,
	Ingots_Others,
	Nuggets_Others,
	Alloy_Ingots,
	Alloy_Nuggets,
	
	//Crafting
	Metallic,
	
	//Module
	Module_Item_Capacitor,
	
	//Parts
	Part_Battery,
	Part_Engine,
	Part_Module,
	Part_Modules,
	Part_Burning_Chamber,
	Part_Grinding_Wheel,
	Part_Centrifuge_Chamber,
	
	//Components
	Component_Connection_Wires,
	Component_Rods,
	Component_Energy_Crystals,
	Component_Gears,
	Component_Plates,
	Component_Screws,
	Component_Saw_Blades,
	
	//Pattern
	WoodPattern,
	MetalPattern;

	private Item item;

	public void registerItem(Item item) {
		this.item = item;
		NCRegistry.registerItem(item, item.getUnlocalizedName().replace("item.", ""), "mm");
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
