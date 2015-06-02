package nedelosk.forestbotany.api.genetics.plants.tree;

import java.util.List;

import nedelosk.forestbotany.api.genetics.IPlantManager;
import nedelosk.forestbotany.api.genetics.allele.IAllele;
import nedelosk.forestbotany.common.genetics.templates.tree.TreeGenome;

public interface ITreeManager extends IPlantManager {
	
	ITreeGenome templateAsGenome(IAllele[] templateActive, IAllele[] templateInactive);
	
	ITreeGenome templateAsGenome(IAllele[] template);

}
