package nedelosk.modularmachines.api.modular.tier;

import java.util.ArrayList;

import com.google.common.collect.Lists;

import net.minecraft.util.StatCollector;

public class Tiers {

	public static Tier WOOD = addTier(0, "Wood", "wood");
	public static Tier STONE = addTier(1, "Stone", "stone");
	public static Tier BRONZE = addTier(2, "Bronze", "bronze");
	public static Tier IRON = addTier(3, "Iron", "iron");
	public static Tier STEEL = addTier(4, "Steel", "steel");
	public static Tier MAGMARIUM = addTier(5, "Magmarium", "magmarium");
	
	private static ArrayList<Tier> tiers = Lists.newArrayList();
	
	public static Tier addTier(String name, String localName){
		Tier tier = new Tier(0, name, localName, tiers.size());
		tiers.add(tier);
		return tier; 
	}
	
	public static Tier addTier(int stage, String name, String localName){
		Tier tier = new Tier(stage, name, localName, tiers.size());
		tiers.add(tier);
		return tier; 
	}
	
	public static ArrayList<Tier> getTiers() {
		return tiers;
	}
	
	public static Tier getTier(int position) {
		return tiers.get(position);
	}
	
	public static Tier getTier(String name) {
		for(Tier tier : tiers)
			if(tier.getName().equals(name))
				return tier;
		return null;
	}
	
	public static class Tier{
		
		protected int stage;
		protected int position;
		protected String name;
		protected String localName;
		
		public Tier(int stage, String name, String localName, int position) {
			this.stage = stage;
			this.name = name;
			this.localName = localName;
			this.position = position;
		}
		
		public int getStage() {
			return stage;
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
