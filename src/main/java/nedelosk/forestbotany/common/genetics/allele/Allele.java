package nedelosk.forestbotany.common.genetics.allele;

import nedelosk.forestbotany.api.genetics.allele.AlleleManager;
import nedelosk.forestbotany.api.genetics.allele.IAllele;
import nedelosk.forestbotany.api.genetics.allele.IAlleleGender;
import nedelosk.forestbotany.api.genetics.allele.IAllelePlantCrop;
import nedelosk.forestbotany.api.genetics.allele.IAllelePlantTree;

public class Allele implements IAllele {

	private String uid;
	private boolean isDominant;
	private boolean registerAllele;
	
	public Allele(String uid, boolean isDominant) {
		this.uid = uid;
		this.isDominant = isDominant;
		AlleleManager.alleleRegistry.registerAllele(this);
	}
	
	public Allele(String uid, boolean isDominant, boolean registerAllele) {
		this.uid = uid;
		this.isDominant = isDominant;
		this.registerAllele = registerAllele;
		if(registerAllele)
		AlleleManager.alleleRegistry.registerAllele(this);
	}
	
	@Override
	public String getUID() {
		return "fb." + uid;
	}

	@Override
	public boolean isDominant() {
		return isDominant;
	}
	
	//Plants
	public static final IAllele fluidconsumptionLowest = new AlleleInteger("fluidconsumptionLowst", 5);
	public static final IAllele fluidconsumptionLower = new AlleleInteger("fluidconsumptionLower", 15);
	public static final IAllele fluidconsumptionLow = new AlleleInteger("fluidconsumptionLow", 25);
	public static final IAllele fluidconsumptionDefault = new AlleleInteger("fluidconsumptionDefault", 50, true);
	public static final IAllele fluidconsumptionHigh = new AlleleInteger("fluidconsumptionHigh", 75);
	public static final IAllele fluidconsumptionHigher = new AlleleInteger("fluidconsumptionHigher", 150, true);
	public static final IAllele fluidconsumptionHighest = new AlleleInteger("fluidconsumptionHighest", 350, true);
	

	public static final IAllele fluidconsumptionSpeedLowest = new AlleleInteger("fluidconsumptionSpeedLowst", 2500);
	public static final IAllele fluidconsumptionSpeedLower = new AlleleInteger("fluidconsumptionSpeedLower", 1000);
	public static final IAllele fluidconsumptionSpeedLow = new AlleleInteger("fluidconsumptionSpeedLow", 500);
	public static final IAllele fluidconsumptionSpeedDefault = new AlleleInteger("fluidconsumptionSpeedDefault", 125, true);
	public static final IAllele fluidconsumptionSpeedHigh = new AlleleInteger("fluidconsumptionSpeedHigh", 100);
	public static final IAllele fluidconsumptionSpeedHigher = new AlleleInteger("fluidconsumptionSpeedHigher", 75, true);
	public static final IAllele fluidconsumptionSpeedHighest = new AlleleInteger("fluidconsumptionSpeedHighest", 25, true);
	
	public static final IAllele waterconsumptionNone = new AlleleInteger("waterconsumptionNone", 0);
	public static final IAllele waterconsumptionLowest = new AlleleInteger("waterconsumptionLowst", 1);
	public static final IAllele waterconsumptionLower = new AlleleInteger("waterconsumptionLower", 5);
	public static final IAllele waterconsumptionLow = new AlleleInteger("waterconsumptionLow", 15);
	public static final IAllele waterconsumptionDefault = new AlleleInteger("waterconsumptionDefault", 25, true);
	public static final IAllele waterconsumptionHigh = new AlleleInteger("waterconsumptionHigh",50);
	public static final IAllele waterconsumptionHigher = new AlleleInteger("waterconsumptionHigher", 75, true);
	public static final IAllele waterconsumptionHighest = new AlleleInteger("waterconsumptionHighest", 150, true);

	public static final IAllele waterconsumptionSpeedLowest = new AlleleInteger("waterconsumptionSpeedLowst", 2500);
	public static final IAllele waterconsumptionSpeedLower = new AlleleInteger("waterconsumptionSpeedLower", 1000);
	public static final IAllele waterconsumptionSpeedLow = new AlleleInteger("waterconsumptionSpeedLow", 500);
	public static final IAllele waterconsumptionSpeedDefault = new AlleleInteger("waterconsumptionSpeedDefault", 250, true);
	public static final IAllele waterconsumptionSpeedHigh = new AlleleInteger("waterconsumptionSpeedHigh", 125);
	public static final IAllele waterconsumptionSpeedHigher = new AlleleInteger("waterconsumptionSpeedHigher", 75, true);
	public static final IAllele waterconsumptionSpeedHighest = new AlleleInteger("waterconsumptionSpeedHighest", 25, true);
	
	public static final IAllele deathTimeNone = new AlleleInteger("deathTimeNone", 0, true);
	public static final IAllele deathTimeLow= new AlleleInteger("deathTimeLow", 500, true);
	public static final IAllele deathTimeDefault = new AlleleInteger("deathTimeDefault", 1000, true);
	public static final IAllele deathTimeHigh = new AlleleInteger("deathTimeHigh", 1500, true);
	public static final IAllele deathTimeNoneDeath = new AlleleInteger("deathTimeHigh", 1500, true);
	
	
	//Crops
	public static final IAllele growSpeedLowest = new AlleleInteger("growSpeedLowst", 50, true);
	public static final IAllele growSpeedLower = new AlleleInteger("growSpeedLower", 75, true);
	public static final IAllele growSpeedLow = new AlleleInteger("growSpeedLow", 100, true);
	public static final IAllele growSpeedDefault = new AlleleInteger("growSpeedDefault", 125, true);
	public static final IAllele growSpeedHigh = new AlleleInteger("growSpeedHigh", 150);
	public static final IAllele growSpeedHigher = new AlleleInteger("growSpeedHigher", 175);
	public static final IAllele growSpeedHighest = new AlleleInteger("growSpeedHighest", 200);
	
	public static final IAllele fruitgrowSpeedLowest = new AlleleInteger("fruitgrowSpeedLowst", 1000, true);
	public static final IAllele fruitgrowSpeedLower = new AlleleInteger("fruitgrowSpeedLower", 500, true);
	public static final IAllele fruitgrowSpeedLow = new AlleleInteger("fruitgrowSpeedLow", 250, true);
	public static final IAllele fruitgrowSpeedDefault = new AlleleInteger("fruitgrowSpeedDefault", 125, true);
	public static final IAllele fruitgrowSpeedHigh = new AlleleInteger("fruitgrowSpeedHigh", 75);
	public static final IAllele fruitgrowSpeedHigher = new AlleleInteger("fruitgrowSpeedHigher", 50);
	public static final IAllele fruitgrowSpeedHighest = new AlleleInteger("fruitgrowSpeedHighest", 25);
	
	public static final IAllele fruitnessNone = new AlleleInteger("fruitnessNone", 0, true);
	public static final IAllele fruitnessLower = new AlleleInteger("fruitnessLower", 1, true);
	public static final IAllele fruitnessLow = new AlleleInteger("fruitnessLow", 2, true);
	public static final IAllele fruitnessDefault = new AlleleInteger("fruitnessDefault", 3, true);
	public static final IAllele fruitnessHigh = new AlleleInteger("fruitnessHigh", 4, true);
	public static final IAllele fruitnessHigher = new AlleleInteger("fruitnessHigher", 5, true);
	
	public static final IAllele seednessLowest = new AlleleInteger("seednessLowest", 0, true);
	public static final IAllele seednessLower = new AlleleInteger("seednessLower", 1, true);
	public static final IAllele seednessLow = new AlleleInteger("seednessLow", 2, true);
	public static final IAllele seednessDefault = new AlleleInteger("seednessDefault", 4, true);
	public static final IAllele seednessHigh = new AlleleInteger("seednessHigh", 8, true);
	public static final IAllele seednessHigher = new AlleleInteger("seednessHigher", 12, true);
	public static final IAllele seednessHighest = new AlleleInteger("seednessHighest", 12, true);
	
	//Plants
	
	public static final IAlleleGender male = new AlleleGender("male", true);
	public static final IAlleleGender female = new AlleleGender("female", true);
	
	//Trees
	public static IAllelePlantTree oak = new AllelePlantTree("oak", true, 15, 45, 20, 60);
	public static IAllelePlantTree spruce = new AllelePlantTree("oak", true, -25, 10, 20, 60);
	public static IAllelePlantTree birch = new AllelePlantTree("oak", true, 15, 45, 20, 60);
	public static IAllelePlantTree jungle = new AllelePlantTree("oak", true, 35, 65, 65, 80);
	
	//Crops
	//public static IAllelePlantCrop wheat = new AllelePlantCrop("wheat", true, 5, 35, 20, 70, 8, SeedTypes.Default);
	//public static IAllelePlantCrop carrot = new AllelePlantCrop("carrot", true, 13, 35, 20, 70, 4, SeedTypes.Vegetable).setRenderPass(2).setIsSpecial();
	//public static IAllelePlantCrop potatoe = new AllelePlantCrop("potatoe", true, 13, 35, 20, 70, 4, SeedTypes.Vegetable).setRenderPass(1).setIsSpecial();
	//public static IAllelePlantCrop pumpkin = new AllelePlantCrop("pumpkin", true, 13, 35, 20, 70, 5, SeedTypes.Default);
	//public static IAllelePlantCrop melon = new AllelePlantCrop("melon", true, 13, 35, 20, 70, 5, SeedTypes.Default);
}
