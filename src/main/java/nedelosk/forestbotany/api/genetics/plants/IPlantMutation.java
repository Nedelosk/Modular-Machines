package nedelosk.forestbotany.api.genetics.plants;

import nedelosk.forestbotany.api.genetics.allele.IAllele;
import nedelosk.forestbotany.common.soil.SoilType;
import net.minecraftforge.fluids.FluidStack;

public interface IPlantMutation {

	public boolean isCanSwitchGender();
	
	public IAllele[] getTemplate();
	
	public FluidStack getFluid();
	
	public IAllele getPlantMale();
	
	public IAllele getPlantFemale();
	
	public int getChance();
	
	public void setCanSwitchGender();
	
	public SoilType getSoil();
	
}
