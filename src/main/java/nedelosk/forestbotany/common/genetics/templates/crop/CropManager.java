package nedelosk.forestbotany.common.genetics.templates.crop;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import nedelosk.forestbotany.api.genetics.allele.IAllele;
import nedelosk.forestbotany.api.genetics.allele.IAlleleGender;
import nedelosk.forestbotany.api.genetics.allele.IAllelePlantCrop;
import nedelosk.forestbotany.api.genetics.plants.IPlant;
import nedelosk.forestbotany.api.genetics.plants.crop.ICropGenome;
import nedelosk.forestbotany.api.genetics.plants.crop.ICropManager;
import nedelosk.forestbotany.common.core.registry.ItemRegistry;
import nedelosk.forestbotany.common.genetics.PlantManager;
import nedelosk.forestbotany.common.genetics.allele.Allele;
import nedelosk.forestbotany.common.genetics.templates.tree.Tree;
import nedelosk.forestbotany.common.genetics.templates.tree.TreeChromosome;
import nedelosk.forestbotany.common.genetics.templates.tree.TreeGenome;
import net.minecraft.item.ItemSeeds;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class CropManager extends PlantManager implements ICropManager {

	public Map<IAllelePlantCrop, ItemSeeds> speciesSeeds = new LinkedHashMap<IAllelePlantCrop, ItemSeeds>();
	
	@Override
	public IAllele[] getDefaultPlant() {
		IAllele[] alleles = new IAllele[CropChromosome.values().length];
		alleles[CropChromosome.PLANT.ordinal()] = CropDefinition.Wheat.getPlant();
		alleles[CropChromosome.GENDER.ordinal()] = Allele.male;
		alleles[CropChromosome.FLUIDCONSUMPTION.ordinal()] = Allele.fluidconsumptionDefault;
		alleles[CropChromosome.FLUIDCONSUMPTIONSPEED.ordinal()] = Allele.fluidconsumptionSpeedDefault;
		alleles[CropChromosome.WATERCONSUMPTION.ordinal()] = Allele.waterconsumptionDefault;
		alleles[CropChromosome.WATERCONSUMPTIONSPEED.ordinal()] = Allele.waterconsumptionSpeedDefault;
		alleles[CropChromosome.GROWSPEED.ordinal()] = Allele.growSpeedDefault;
		alleles[CropChromosome.FRUITNESS.ordinal()] = Allele.fruitnessDefault;
		alleles[CropChromosome.FRUITGROWSPEED.ordinal()] = Allele.fruitgrowSpeedDefault;
		alleles[CropChromosome.DEATHTIME.ordinal()] = Allele.deathTimeDefault;
		alleles[CropChromosome.SEEDNESS.ordinal()] = Allele.seednessDefault;
		return alleles;
	}

	@Override
	public ICropGenome templateAsGenome(IAllele[] template) {
		return new CropGenome(templateAsChromosomes(template));
	}

	@Override
	public ICropGenome templateAsGenome(IAllele[] templateActive,
			IAllele[] templateInactive) {
		return new CropGenome(templateAsChromosomes(templateActive,
				templateInactive));
	}

	@Override
	public void registerTemplate(IAllele[] template) {
		for (int i = 0; i < 2; i++) {
			IAlleleGender gender = (i == 0) ? Allele.male : Allele.female;
			template[TreeChromosome.GENDER.ordinal()] = gender;
			plantTemplates.add(new Crop(templateAsGenome(template)));
		}
	}

	@Override
	public ItemStack getMemberStack(IPlant tree, int growthStage) {

		NBTTagCompound nbttagcompound = new NBTTagCompound();
		tree.writeToNBT(nbttagcompound);
		NBTTagCompound nbtTagCrop= new NBTTagCompound();
		nbtTagCrop.setInteger("GrowthStage", growthStage);
		nbtTagCrop.setInteger("GrowthTime", 0);
		nbtTagCrop.setBoolean("IsCrop", false);
		nbtTagCrop.setInteger("DeathTime", 0);
		nbttagcompound.setTag("Crop", nbtTagCrop);
		
		ItemStack treeStack = new ItemStack(ItemRegistry.seed.item());
		treeStack.setTagCompound(nbttagcompound);

		return treeStack;

	}
	
	@Override
	public ItemStack getMemberStackFruit(IPlant tree, int stage) {

		NBTTagCompound nbt = new NBTTagCompound();
		tree.writeToNBT(nbt);
		NBTTagCompound nbtTagCrop= new NBTTagCompound();
		nbtTagCrop.setInteger("GrowthStage", 0);
		nbtTagCrop.setInteger("GrowthTime", 0);
		nbtTagCrop.setBoolean("IsCrop", false);
		nbtTagCrop.setInteger("DeathTime", 0);
		nbt.setTag("Crop", nbtTagCrop);
		NBTTagCompound nbtFruit = new NBTTagCompound();
		nbtFruit.setInteger("Stage", stage);
		nbtFruit.setInteger("Time", 0);
		nbt.setTag("Fruit", nbtFruit);
		
		ItemStack treeStack = new ItemStack(ItemRegistry.fruit.item());
		treeStack.setTagCompound(nbt);

		return treeStack;

	} 

	public boolean hasGender(IAlleleGender gender, ItemStack stack) {
		if (!stack.hasTagCompound()) {
			return false;
		}
		if (!stack.getTagCompound().hasKey("Genome")) {
			return false;
		}
		NBTTagCompound tag = stack.getTagCompound().getCompoundTag("Genome");
		if (new CropGenome(tag).getActiveAllele(CropChromosome.GENDER) == gender) {
			return true;
		}
		return false;
	}

	public ItemSeeds getSeedFromSpecies(IAllelePlantCrop species) {
		if (speciesSeeds.get(species) != null)
			return speciesSeeds.get(species);
		return null;
	}

	public void addSeedToSpecies(IAllelePlantCrop species, ItemSeeds seed) {
		speciesSeeds.put(species, seed);
	}
	
}
