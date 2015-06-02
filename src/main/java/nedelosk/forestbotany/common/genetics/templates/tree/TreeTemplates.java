package nedelosk.forestbotany.common.genetics.templates.tree;

import nedelosk.forestbotany.api.genetics.allele.IAllele;
import nedelosk.forestbotany.api.genetics.allele.IAlleleGender;
import nedelosk.forestbotany.common.genetics.allele.Allele;
import nedelosk.forestbotany.common.genetics.allele.AlleleGender;

public class TreeTemplates {

	public static IAllele[] getDefaultPlant()
	{
		IAllele[] alleles = new IAllele[TreeChromosome.values().length];
		alleles[TreeChromosome.PLANT.ordinal()] = Allele.oak;
		alleles[TreeChromosome.GENDER.ordinal()] = Allele.male;
		alleles[TreeChromosome.NUTRIENTSCONSUMPTION.ordinal()] = Allele.waterconsumptionDefault;
		alleles[TreeChromosome.WATERCONSUMPTION.ordinal()] = Allele.waterconsumptionDefault;
		alleles[TreeChromosome.WATERCONSUMPTIONSPEED.ordinal()] = Allele.waterconsumptionSpeedDefault;
		alleles[TreeChromosome.GROWSPEED.ordinal()] = Allele.growSpeedDefault;
		return alleles;
	}
	
	public static IAllele[] getTreeOak()
	{
		return getDefaultPlant();
	}
	
	public static IAllele[] getTreeBirch()
	{
		IAllele[] alleles = getDefaultPlant();
		alleles[TreeChromosome.PLANT.ordinal()] = Allele.birch;
		return alleles;
	}
	
}
