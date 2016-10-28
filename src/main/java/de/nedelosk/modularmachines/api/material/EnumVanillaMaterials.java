package de.nedelosk.modularmachines.api.material;

import java.util.Locale;

import net.minecraft.util.text.translation.I18n;

public enum EnumVanillaMaterials implements IMetalMaterial {
	WOOD(0, "Wood", "wood", 0x967853, "Wood"), COAL(1, "Coal", "coal", 0x3E3E3E, "Coal"), DIAMOND(6, "Diamond", "diamond", 0x68D2DA, "Diamond"), OBSIDIAN(6, "Obsidian", "obsidian", 0x7E258C, "Obdidian");

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
		return name.toLowerCase(Locale.ENGLISH);
	}

	@Override
	public String getLocalizedName() {
		return I18n.translateToLocal("material." + unlocalizedName + ".name");
	}
}
