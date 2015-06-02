package nedelosk.forestbotany.api.genetics;

import nedelosk.forestbotany.api.genetics.allele.IAllele;
import nedelosk.forestbotany.api.genetics.templates.IPlantChromosome;
import nedelosk.nedeloskcore.api.INBTTagable;

public interface IGenome extends INBTTagable{

	IChromosome[] getChromosomes();

	IPlantManager getPlantManager();

	IAllele getActiveAllele(IPlantChromosome chromosomePlant);

	IAllele getInactiveAllele(IPlantChromosome chromosomePlant);

}
