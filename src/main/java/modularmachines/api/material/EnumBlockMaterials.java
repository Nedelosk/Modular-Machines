package modularmachines.api.material;

import java.util.Locale;

import net.minecraft.util.text.translation.I18n;

public enum EnumBlockMaterials implements IMaterial {
	WOOD(0, "Wood", "wood"), STONE(1, "Stone", "stone"), BRICK(1, "Brick", "brick");

	private int tier;
	private int color;
	private String name;
	private String unlocalizedName;
	private String[] oreDicts;

	EnumBlockMaterials(int tier, String name, String unlocalizedName) {
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
		return name.toLowerCase(Locale.ENGLISH);
	}

	@Override
	public String getLocalizedName() {
		return I18n.translateToLocal("material." + unlocalizedName + ".name");
	}
}
