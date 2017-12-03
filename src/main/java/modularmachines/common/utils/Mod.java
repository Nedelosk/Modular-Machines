package modularmachines.common.utils;

import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;

import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

public enum Mod {
	FORESTRY("forestry"),
	THERMAL_EXPANSION("thermalexpansion"),
	JEI("jei");
	
	private final String modID;
	private boolean isLoaded;
	
	Mod(String modID) {
		this.modID = modID;
		this.isLoaded = Loader.isModLoaded(this.modID);
	}
	
	public boolean active() {
		return isLoaded;
	}
	
	
	public String modID() {
		return modID;
	}
	
	@Override
	public String toString() {
		return modID;
	}
	
	public Item getItem(String registryName) {
		Item item = ForgeRegistries.ITEMS.getValue(new ResourceLocation(modID, registryName));
		if (item == null) {
			Log.warn("Failed to find the item {}.", modID + ":" + registryName);
			item = Item.getItemFromBlock(Blocks.AIR);
		}
		return item;
	}
	
}
