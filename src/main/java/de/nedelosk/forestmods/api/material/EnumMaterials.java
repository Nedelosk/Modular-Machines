package de.nedelosk.forestmods.api.material;

import net.minecraft.util.StatCollector;

public enum EnumMaterials implements IMaterial {
	WOOD(0, "Wood", "wood"), STONE(1, "Stone", "stone"), IRON(2, "Iron", "iron"), BRONZE(3, "Bronze", "bronze"), OBSIDIAN(4, "Obsidian", "obsidian"), STEEL(5,
			"Steel", "steel"), MAGMARIUM(7, "Magmarium", "magmarium"),
			// Thermal Expansion
			Lead(2, "Lead", "lead"), Invar(3, "Invar", "invar"), Electrum(5, "Electrum", "electrum"), Signalum(6, "Signalum", "signalum"), Enderium(7,
					"Enderium", "enderium");

	private int tier;
	private String name;
	private String unlocalizedName;

	EnumMaterials(int tier, String name, String unlocalizedName) {
		this.tier = tier;
		this.name = name;
		this.unlocalizedName = unlocalizedName;
		MaterialRegistry.registerMaterial(this);
	}

	@Override
	public int getTier() {
		return tier;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public String getLocalizedName() {
		return StatCollector.translateToLocal("material." + unlocalizedName + ".name");
	}
}
