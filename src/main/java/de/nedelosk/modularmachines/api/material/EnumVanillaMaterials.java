package de.nedelosk.modularmachines.api.material;

import de.nedelosk.modularmachines.api.Translator;

public enum EnumVanillaMaterials implements IMetalMaterial {
	COAL(1, "Coal", "coal", 0x3E3E3E, "Coal"), 
	DIAMOND(6, "Diamond", "diamond", 0x68D2DA, "Diamond"),
	OBSIDIAN(6, "Obsidian", "obsidian", 0x7E258C, "Obdidian");

	private int tier;
	private int color;
	private String name;
	private String unlocalizedName;
	private String[] oreDicts;

	EnumVanillaMaterials(int tier, String name, String unlocalizedName, int color, String... oreDicts) {
		this.tier = tier;
		this.name = name;
		this.color = color;
		this.unlocalizedName = unlocalizedName;
		this.oreDicts = oreDicts;
		MaterialRegistry.registerMaterial(this);
	}

	@Override
	public int getTier() {
		return tier;
	}

	@Override
	public int getColor() {
		return color;
	}

	@Override
	public String[] getOreDicts() {
		return oreDicts;
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
