package nedelosk.nedeloskcore.api;

import net.minecraft.item.ItemStack;

public class Material {

	public static enum MaterialType{
		BRICK, METAL, WOOD
	}
	
	public String materialName;
	public int maxHeat;
	public float resistance;
	public float hardness;
	public MaterialType type;
	public String oreDict;
	public ItemStack block;
	
	public Material(String materialName, int maxHeat, float resistance, float hardness, MaterialType type, String oreDict, ItemStack block) {
		this.materialName = materialName;
		this.maxHeat = maxHeat;
		this.resistance = resistance;
		this.hardness = hardness;
		this.type = type;
		this.oreDict = oreDict;
		this.block = block;
	}
	
}
