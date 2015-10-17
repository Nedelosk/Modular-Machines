package nedelosk.modularmachines.api.modular.type;

import java.util.ArrayList;

import com.google.common.collect.Lists;

import net.minecraft.util.StatCollector;

public class Types {

	private static ArrayList<Type> types = Lists.newArrayList();
	
	public static Type WOOD = addType(0, "Wood", "wood");
	public static Type STONE = addType(1, "Stone", "stone");
	public static Type BRONZE = addType(2, "Bronze", "bronze");
	public static Type IRON = addType(3, "Iron", "iron");
	public static Type STEEL = addType(4, "Steel", "steel");
	public static Type MAGMARIUM = addType(5, "Magmarium", "magmarium");
	
	public static Type addType(String name, String localName){
		Type tier = new Type(0, name, localName, types.size());
		types.add(tier);
		return tier; 
	}
	
	public static Type addType(int stage, String name, String localName){
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
		for(Type tier : types)
			if(tier.getName().equals(name))
				return tier;
		return null;
	}
	
	public static class Type{
		
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
			return StatCollector.translateToLocal(localName);
		}
	}
	
}
