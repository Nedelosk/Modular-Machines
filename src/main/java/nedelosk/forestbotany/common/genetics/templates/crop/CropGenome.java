package nedelosk.forestbotany.common.genetics.templates.crop;

import nedelosk.forestbotany.api.genetics.IChromosome;
import nedelosk.forestbotany.api.genetics.IPlantManager;
import nedelosk.forestbotany.api.genetics.allele.IAlleleGender;
import nedelosk.forestbotany.api.genetics.allele.IAllelePlantCrop;
import nedelosk.forestbotany.api.genetics.allele.IAllelePlantTree;
import nedelosk.forestbotany.api.genetics.plants.crop.ICropGenome;
import nedelosk.forestbotany.common.genetics.Genome;
import nedelosk.forestbotany.common.genetics.PlantManager;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class CropGenome extends Genome implements ICropGenome {

	public CropGenome(IChromosome[] chromosomes) {
		super(chromosomes);
	}

	public CropGenome(NBTTagCompound nbttagcompound) {
		super(nbttagcompound);
	}
	
	public static IAllelePlantCrop getSpecies(ItemStack itemStack) {
		return (IAllelePlantCrop) getActiveAllele(itemStack, CropChromosome.PLANT, PlantManager.cropManager);
	}
	
	public static IAlleleGender getGender(ItemStack itemStack) {
		return (IAlleleGender) getActiveAllele(itemStack, CropChromosome.GENDER, PlantManager.cropManager);
	}
	
	@Override
	public IPlantManager getPlantManager() {
		return PlantManager.cropManager;
	}

}
