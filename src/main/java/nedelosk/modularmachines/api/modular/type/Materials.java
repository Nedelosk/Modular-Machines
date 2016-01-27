package nedelosk.modularmachines.api.modular.type;

import java.util.ArrayList;

import com.google.common.collect.Lists;

import net.minecraft.util.StatCollector;

public class Materials {

	private static ArrayList<Material> materials = Lists.newArrayList();
	public static Material WOOD = addMaterial(0, "Wood", "wood");
	public static Material STONE = addMaterial(1, "Stone", "stone");
	public static Material IRON = addMaterial(2, "Iron", "iron");
	public static Material BRONZE = addMaterial(3, "Bronze", "bronze");
	public static Material OBSIDIAN = addMaterial(4, "Obsidian", "obsidian");
	public static Material STEEL = addMaterial(5, "Steel", "steel");
	public static Material MAGMARIUM = addMaterial(7, "Magmarium", "magmarium");
	// Thermal Expansion
	public static Material Lead = addMaterial(2, "Lead", "lead");
	public static Material Invar = addMaterial(3, "Invar", "invar");
	public static Material Electrum = addMaterial(5, "Electrum", "electrum");
	public static Material Signalum = addMaterial(6, "Signalum", "signalum");
	public static Material Enderium = addMaterial(7, "Enderium", "enderium");

	public static Material addType(String name, String localName) {
		Material material = new Material(0, name, localName, materials.size());
		materials.add(material);
		return material;
	}

	public static Material addMaterial(int tier, String name, String localName) {
		Material material = new Material(tier, name, localName, materials.size());
		materials.add(material);
		return material;
	}

	public static ArrayList<Material> getMaterials() {
		return materials;
	}

	public static Material getMaterial(int position) {
		return materials.get(position);
	}

	public static Material getMaterial(String name) {
		for ( Material material : materials ) {
			if (material.getName().equals(name)) {
				return material;
			}
		}
		return null;
	}

	public static class Material {

		protected int tier;
		protected int position;
		protected String name;
		protected String localName;

		public Material(int tier, String name, String localName, int position) {
			this.tier = tier;
			this.name = name;
			this.localName = localName;
			this.position = position;
		}

		public int getTier() {
			return tier;
		}

		public int getPosition() {
			return position;
		}

		public String getName() {
			return name;
		}

		public String getLocalName() {
			return StatCollector.translateToLocal("material." + localName + ".name");
		}

		@Override
		public boolean equals(Object obj) {
			if (obj instanceof Material) {
				Material material = (Material) obj;
				if (material.getName().equals(getName()) && material.getTier() == getTier()) {
					return true;
				}
			}
			return false;
		}
	}
}
