package nedelosk.forestbotany.common.genetics.templates.plant;

import nedelosk.forestbotany.api.genetics.allele.IAllele;
import nedelosk.forestbotany.api.genetics.allele.IAllelePlant;
import nedelosk.forestbotany.api.genetics.plants.IPlantMutation;
import nedelosk.forestbotany.common.soil.SoilType;
import net.minecraftforge.fluids.FluidStack;

public class Mutation  implements IPlantMutation{
	
	private final IAllele parentMale;
	private final IAllele parentFemale;
	
	private final IAllele[] template;
	private FluidStack fluid;
	private SoilType soil;
	private final int chance;
	private boolean canSwitchGender;
	
	public Mutation(IAllele parentMale, IAllele parentFemale, IAllele[] template, int chance){
		this.parentMale = parentMale;
		this.parentFemale = parentFemale;
		this.template = template;
		this.chance = chance;
	}
	
	public Mutation(IAllele parentMale, IAllele parentFemale, IAllele[] template, int chance, SoilType soil){
		this.parentMale = parentMale;
		this.parentFemale = parentFemale;
		this.template = template;
		this.chance = chance;
		this.soil = soil;
	}
	
	public Mutation(IAllele parentMale, IAllele parentFemale, IAllele[] template, int chance, FluidStack fluid){
		this.parentMale = parentMale;
		this.parentFemale = parentFemale;
		this.template = template;
		this.chance = chance;
		this.fluid = fluid;
	}
	
	public Mutation(IAllele parentMale, IAllele parentFemale, IAllele[] template, int chance, SoilType soil, FluidStack fluid){
		this.parentMale = parentMale;
		this.parentFemale = parentFemale;
		this.template = template;
		this.chance = chance;
		this.fluid = fluid;
		this.soil = soil;
	}
	
	public boolean isCanSwitchGender() {
		return canSwitchGender;
	}
	
	public SoilType getSoil() {
		return soil;
	}
	
	public IAllele[] getTemplate() {
		return template;
	}
	
	public FluidStack getFluid() {
		return fluid;
	}
	
	public IAllele getPlantMale() {
		return parentMale;
	}
	
	public IAllele getPlantFemale() {
		return parentFemale;
	}
	
	public int getChance() {
		return chance;
	}
	
	public void setCanSwitchGender() 
	{
		canSwitchGender = true;
	}

}
