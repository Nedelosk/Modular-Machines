package de.nedelosk.modularmachines.api.material;

import de.nedelosk.modularmachines.common.utils.Translator;

public enum EnumMaterials implements IMaterial {
	WOOD(0, "Wood", "wood"), STONE(1, "Stone", "stone"), IRON(2, "Iron", "iron"), BRONZE(3, "Bronze", "bronze"), OBSIDIAN(4, "Obsidian", "obsidian"), STEEL(5,
			"Steel", "steel"), MAGMARIUM(7, "Magmarium", "magmarium"),
	// Thermal Expansion
	LEAD(2, "Lead", "lead"), INVAR(3, "Invar", "invar"), ELECTRUM(5, "Electrum", "electrum"), SIGNALUM(6, "Signalum", "signalum"), ENDERIUM(7,
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
		return Translator.translateToLocal("material." + unlocalizedName + ".name");
	}
}
