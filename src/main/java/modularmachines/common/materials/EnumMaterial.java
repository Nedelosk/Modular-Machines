package modularmachines.common.materials;

import java.util.Locale;

import net.minecraft.util.text.translation.I18n;

public enum EnumMaterial {
	ALUMINIUM("Aluminum", "aluminium", 0xD4E3E6, "Aluminum", "Aluminium"),
	NICKEL("Nickel", "nickel", 0xA9A283, "Nickel"),
	IRON("Iron", "iron", 0xDADADA, "Iron"),
	TIN("Tin", "tin", 0xCACECF, "Tin"),
	SILVER("Silver", "silver", 0xE6FDFF, "Silver"),
	COPPER("Copper", "copper", 0xCC6410, "Copper"),
	GOLD("Gold", "gold", 0xD3B95A, "Gold"),
	STEEL("Steel", "steel", 0xA0A0A0, "Steel"),
	// Alloy
	BRONZE("Bronze", "bronze", 0xCA9956, "Bronze"),
	INVAR("Invar", "invar", 0xA1A48C, "Invar"),
	MAGMARIUM("Magmarium", "magmarium", 0x6E0C08, "Magmarium"),
	// Thermal Expansion
	LEAD("Lead", "lead", 0x826C82, "Lead"),
	ELECTRUM("Electrum", "electrum", 0xD7D665, "Electrum"),
	SIGNALUM("Signalum", "signalum", 0xB86424, "Signalum"),
	ENDERIUM("Enderium", "enderium", 0x1B7A57, "Enderium"),
	// Mekanism
	OSMIUM("Osmium", "osmium", 0xADC9CB, "Osmium");
	
	public static final EnumMaterial[] VALUES = values();
	
	private int color;
	private String name;
	private String unlocalizedName;
	private String[] oreDicts;
	
	EnumMaterial(String name, String unlocalizedName, int color, String... oreDicts) {
		this.name = name;
		this.color = color;
		this.unlocalizedName = unlocalizedName;
		this.oreDicts = oreDicts;
	}
	
	public int getColor() {
		return color;
	}
	
	public String[] getOreDicts() {
		return oreDicts;
	}
	
	public String getName() {
		return name.toLowerCase(Locale.ENGLISH);
	}
	
	public String getLocalizedName() {
		return I18n.translateToLocal("material." + unlocalizedName + ".name");
	}
}
