package nedelosk.forestbotany.common.genetics.templates.tree;

import nedelosk.forestbotany.api.genetics.IChromosome;
import nedelosk.forestbotany.api.genetics.IPlantManager;
import nedelosk.forestbotany.api.genetics.allele.IAlleleGender;
import nedelosk.forestbotany.api.genetics.allele.IAllelePlantTree;
import nedelosk.forestbotany.api.genetics.plants.tree.ITreeGenome;
import nedelosk.forestbotany.common.genetics.Genome;
import nedelosk.forestbotany.common.genetics.PlantManager;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class TreeGenome extends Genome implements ITreeGenome {

	public TreeGenome(IChromosome[] chromosomes) {
		super(chromosomes);
	}

	public TreeGenome(NBTTagCompound nbttagcompound) {
		super(nbttagcompound);
	}
	
	public static IAllelePlantTree getSpecies(ItemStack itemStack) {
		return (IAllelePlantTree) getActiveAllele(itemStack, TreeChromosome.PLANT, PlantManager.treeManager);
	}
	
	public static IAlleleGender getGender(ItemStack itemStack) {
		return (IAlleleGender) getActiveAllele(itemStack, TreeChromosome.GENDER, PlantManager.treeManager);
	}
	
	@Override
	public IPlantManager getPlantManager() {
		return PlantManager.treeManager;
	}

}
