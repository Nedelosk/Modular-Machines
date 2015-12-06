package nedelosk.modularmachines.api.modular.type;

import java.util.ArrayList;

import com.google.common.collect.Lists;

import net.minecraft.util.StatCollector;

public class Types {

	private static ArrayList<Type> types = Lists.newArrayList();

	public static Type WOOD = addType(0, "Wood", "wood");
	public static Type STONE = addType(1, "Stone", "stone");
	public static Type IRON = addType(2, "Iron", "iron");
	public static Type BRONZE = addType(3, "Bronze", "bronze");
	public static Type OBSIDIAN = addType(4, "Obsidian", "obsidian");
	public static Type STEEL = addType(5, "Steel", "steel");
	public static Type MAGMARIUM = addType(7, "Magmarium", "magmarium");
	
	//Thermal Expansion
	public static Type Lead = Types.addType(2, "Lead", "lead");
	public static Type Invar = Types.addType(3, "Invar", "invar");
	public static Type Electrum = Types.addType(5, "Electrum", "electrum");
	public static Type Signalum = Types.addType(6, "Signalum", "signalum");
	public static Type Enderium = Types.addType(7, "Enderium", "enderium");

	public static Type addType(String name, String localName) {
		Type tier = new Type(0, name, localName, types.size());
		types.add(tier);
		return tier;
	}

	public static Type addType(int stage, String name, String localName) {
		Type tier = new Type(stage, name, localName, types.size());
		types.add(tier);
		return tier;
	}

	public static ArrayList<Type> getTypes() {
		return types;
	}

	public static Type getType(int position) {
		return types.get(position);
	}

	public static Type getType(String name) {
		for (Type tier : types)
			if (tier.getName().equals(name))
				return tier;
		return null;
	}

	public static class Type {

		protected int tier;
		protected int position;
		protected String name;
		protected String localName;

		public Type(int tier, String name, String localName, int position) {
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
			return StatCollector.translateToLocal("type." + localName + ".name");
		}
		
		@Override
		public boolean equals(Object obj) {
			if(obj instanceof Type){
				Type type = (Type) obj;
				if(type.getName().equals(getName()) && type.getTier() == getTier())
					return true;
			}
			return false;
		}
	}

}
