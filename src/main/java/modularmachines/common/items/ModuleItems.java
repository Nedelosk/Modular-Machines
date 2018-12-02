package modularmachines.common.items;

import java.util.Locale;

import net.minecraft.item.ItemStack;

import modularmachines.registry.ModItems;

public enum ModuleItems {
	FIREBOX, LARGE_TANK, WATER_INTAKE, BOILER;
	
	public static final ModuleItems[] VALUES = values();
	
	public static ModuleItems getItem(int index) {
		if (index >= VALUES.length || index < 0) {
			return VALUES[0];
		}
		return VALUES[index];
	}
	
	public ItemStack get(int amount) {
		return new ItemStack(ModItems.itemModules, 1, ordinal());
	}
	
	public ItemStack get() {
		return get(1);
	}
	
	public String getName() {
		return name().toLowerCase(Locale.ENGLISH);
	}
}
