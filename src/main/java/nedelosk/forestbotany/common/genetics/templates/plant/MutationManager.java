package nedelosk.forestbotany.common.genetics.templates.plant;

import java.util.ArrayList;
import java.util.List;

import nedelosk.forestbotany.api.genetics.IChromosome;
import nedelosk.forestbotany.api.genetics.allele.IAllele;
import nedelosk.forestbotany.api.genetics.allele.IAllelePlantTree;
import nedelosk.forestbotany.api.genetics.plants.IPlantMutation;
import nedelosk.forestbotany.common.soil.SoilType;
import net.minecraftforge.fluids.FluidStack;

public class MutationManager {

	public static List<Mutation> treeMutations = new ArrayList();
	public static List<Mutation> cropMutations = new ArrayList();
	
	public static Mutation registerCropMutation(Mutation mutation)
	{
		cropMutations.add(mutation);
		return mutation;
	}
	
	public static Mutation getMatchTreeMutation(IAllele male, IAllele female)
	{
		for(Mutation mutation : treeMutations)
		{
			if(mutation.getPlantMale() == male && mutation.getPlantFemale() == female)
			{
				return mutation;
			}
			else if(mutation.isCanSwitchGender())
			{
				if(mutation.getPlantFemale() == male && mutation.getPlantMale() == female)
				{
					return mutation;
				}
			}
		}
		return null;
	}
	
	public static IPlantMutation getMatchCropMutation(IAllele male, IAllele female, FluidStack fluid, SoilType soil)
	{
		for(Mutation mutation : treeMutations)
		{
			if(mutation.getPlantMale() == male && mutation.getPlantFemale() == female)
			{
				if(fluid.getFluid() == mutation.getFluid().getFluid() && fluid.amount >= mutation.getFluid().amount || mutation.getFluid() == null)
				{
					if(mutation.getSoil() == null || mutation.getSoil() == soil)
					{
						return mutation;
					}
				}
			}
			else if(mutation.isCanSwitchGender())
			{
				if(mutation.getPlantFemale() == male && mutation.getPlantMale() == female)
				{
					if(fluid.getFluid() == mutation.getFluid().getFluid() && fluid.amount >= mutation.getFluid().amount || mutation.getFluid() == null)
					{
						if(mutation.getSoil() == null || mutation.getSoil() == soil)
						{
							return mutation;
						}
					}
				}
			}
		}
		return null;
	}
	
}
