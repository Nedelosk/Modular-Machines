package nedelosk.forestbotany.api.genetics;

import java.util.List;

import nedelosk.forestbotany.api.genetics.allele.IAllele;
import nedelosk.forestbotany.api.genetics.allele.IAlleleGender;
import nedelosk.forestbotany.api.genetics.plants.IPlant;
import nedelosk.forestbotany.common.genetics.templates.tree.TreeChromosome;
import nedelosk.forestbotany.common.genetics.templates.tree.TreeGenome;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public interface IPlantManager {

	IAllele[] getDefaultPlant();
	
	IChromosome[] templateAsChromosomes(IAllele[] templateActive, IAllele[] templateInactive);
	
	IChromosome[] templateAsChromosomes(IAllele[] template);
	
	void registerTemplate(IAllele[] template);
	
	ItemStack getMemberStack(IPlant plant, int growthStage);
	
	boolean hasGender(IAlleleGender gender, ItemStack stack);
	
	List<IPlant> getTemplates();
	
}
