package nedelosk.forestbotany.api.genetics.plants.crop;

import nedelosk.forestbotany.api.genetics.IPlantManager;
import nedelosk.forestbotany.api.genetics.allele.IAllele;
import nedelosk.forestbotany.api.genetics.plants.IPlant;
import net.minecraft.item.ItemStack;

public interface ICropManager extends IPlantManager {
	
	ICropGenome templateAsGenome(IAllele[] templateActive, IAllele[] templateInactive);
	
	ICropGenome templateAsGenome(IAllele[] template);
	
	ItemStack getMemberStackFruit(IPlant tree, int stage); 

}
