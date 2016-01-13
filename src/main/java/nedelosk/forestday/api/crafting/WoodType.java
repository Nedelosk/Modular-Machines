package nedelosk.forestday.api.crafting;

import java.util.ArrayList;

import net.minecraft.item.ItemStack;

public final class WoodType {

	private ItemStack wood;
	private String name;
	private ArrayList<ItemStack> charcoalDropps = new ArrayList<ItemStack>();

	public WoodType(String name, ItemStack wood, ItemStack... charcoalDropps) {
		this.wood = wood;
		for ( ItemStack dropp : charcoalDropps ) {
			this.charcoalDropps.add(dropp);
		}
		this.name = name;
	}

	public WoodType(String name, ItemStack wood, ArrayList<ItemStack> charcoalDropps) {
		this.wood = wood;
		this.charcoalDropps = charcoalDropps;
		this.name = name;
	}

	public ArrayList<ItemStack> getCharcoalDropps() {
		return charcoalDropps;
	}

	public String getName() {
		return name;
	}

	public ItemStack getWood() {
		return wood;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof WoodType) {
			if (!((WoodType) obj).name.equals(name)) {
				return false;
			}
			return true;
		}
		return false;
	}
}
