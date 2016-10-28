package de.nedelosk.modularmachines.api.material;

import java.util.Locale;

import net.minecraft.util.text.translation.I18n;

public enum EnumMetalMaterials implements IMetalMaterial {
	ALUMINIUM(2, "Aluminum", "aluminium", 0xD4E3E6, "Aluminum", "Aluminium"), NICKEL(2, "Nickel", "nickel", 0xA9A283, "Nickel"), IRON(2, "Iron", "iron", 0xDADADA, "Iron"), TIN(2, "Tin", "tin", 0xCACECF, "Tin"), SILVER(2, "Silver", "silver", 0xE6FDFF,
			"Silver"), COPPER(2, "Copper", "copper", 0xCC6410, "Copper"), GOLD(4, "Gold", "gold", 0xD3B95A, "Gold"), STEEL(5, "Steel", "steel", 0xA0A0A0, "Steel"),
	// Alloy
	BRONZE(3, "Bronze", "bronze", 0xCA9956, "Bronze"), INVAR(3, "Invar", "invar", 0xA1A48C, "Invar"), MAGMARIUM(7, "Magmarium", "magmarium", 0x6E0C08, "Magmarium"),
	// Thermal Expansion
	LEAD(2, "Lead", "lead", 0x826C82, "Lead"), ELECTRUM(5, "Electrum", "electrum", 0xD7D665, "Electrum"), SIGNALUM(6, "Signalum", "signalum", 0xB86424, "Signalum"), ENDERIUM(7, "Enderium", "enderium", 0x1B7A57, "Enderium"),
	// Mekanism
	OSMIUM(3, "Osmium", "osmium", 0xADC9CB, "Osmium");

	private int tier;
	private int color;
	private String name;
	private String unlocalizedName;
	private String[] oreDicts;

	EnumMetalMaterials(int tier, String name, String unlocalizedName, int color, String... oreDicts) {
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
