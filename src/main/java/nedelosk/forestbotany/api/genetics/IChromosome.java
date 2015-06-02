package nedelosk.forestbotany.api.genetics;

import nedelosk.forestbotany.api.genetics.allele.IAllele;
import nedelosk.nedeloskcore.api.INBTTagable;

public interface IChromosome extends INBTTagable {

	IAllele getActiveAllele();

	IAllele getInactiveAllele();
	
	public IAllele getPrimaryAllele();
	
	public IAllele getSecondaryAllele();
	
}
