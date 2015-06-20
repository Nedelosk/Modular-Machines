package nedelosk.forestday.common.machines.mutiblock.charcoalkiln;

import java.util.ArrayList;

import nedelosk.forestday.api.crafting.IWoodType;
import net.minecraft.item.ItemStack;

public class WoodTypeManager implements IWoodType {

	public static ArrayList<WoodType> woodTypes = new ArrayList<WoodType>();

	@Override
	public void add(ItemStack wood, ItemStack... dropps) {
		woodTypes.add(new WoodType(wood, dropps));
	}
	
}
