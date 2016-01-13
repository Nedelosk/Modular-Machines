package nedelosk.forestday.common.crafting;

import java.util.HashMap;

import com.google.common.collect.Maps;

import nedelosk.forestday.api.crafting.IWoodTypeManager;
import nedelosk.forestday.api.crafting.WoodType;
import net.minecraft.item.ItemStack;

public class WoodTypeManager implements IWoodTypeManager {

	private HashMap<String, WoodType> woodTypes = Maps.newHashMap();

	@Override
	public void add(String name, ItemStack wood, ItemStack... charcoalDropps) {
		woodTypes.put(name, new WoodType(name, wood, charcoalDropps));
	}

	@Override
	public WoodType get(String name) {
		return woodTypes.get(name);
	}

	@Override
	public HashMap<String, WoodType> getWoodTypes() {
		return woodTypes;
	}
}
