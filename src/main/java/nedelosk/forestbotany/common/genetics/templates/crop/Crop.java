package nedelosk.forestbotany.common.genetics.templates.crop;

import java.util.List;

import nedelosk.forestbotany.api.botany.IInfuser;
import nedelosk.forestbotany.api.botany.IInfuserChamber;
import nedelosk.forestbotany.api.genetics.IChromosome;
import nedelosk.forestbotany.api.genetics.IGenome;
import nedelosk.forestbotany.api.genetics.allele.IAllele;
import nedelosk.forestbotany.api.genetics.allele.IAlleleGender;
import nedelosk.forestbotany.api.genetics.plants.IPlant;
import nedelosk.forestbotany.api.genetics.plants.IPlantMutation;
import nedelosk.forestbotany.api.genetics.plants.crop.ICrop;
import nedelosk.forestbotany.common.genetics.Chromosome;
import nedelosk.forestbotany.common.genetics.PlantManager;
import nedelosk.forestbotany.common.genetics.allele.Allele;
import nedelosk.forestbotany.common.genetics.templates.plant.MutationManager;
import nedelosk.forestbotany.common.genetics.templates.plant.Plant;
import nedelosk.forestbotany.common.items.ItemPlant;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

public class Crop extends Plant implements ICrop {

	public Crop(NBTTagCompound nbttagcompound) {
		readFromNBT(nbttagcompound);
	}
	
	public Crop(IGenome genome) {
		setGenome(genome);
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbttagcompound) {

		if (nbttagcompound.hasKey("Genome")) {
			setGenome(new CropGenome(nbttagcompound.getCompoundTag("Genome")));
		} else {
			setGenome(PlantManager.cropManager.templateAsGenome(CropDefinition.Wheat.getTemplate()));
		}

		if (nbttagcompound.hasKey("Partner")) {
			partner = new CropGenome(nbttagcompound.getCompoundTag("Mate"));
		}

	}

	@Override
	public void writeToNBT(NBTTagCompound nbttagcompound) {

		if (genome != null) {
			NBTTagCompound NBTmachine = new NBTTagCompound();
			genome.writeToNBT(NBTmachine);
			nbttagcompound.setTag("Genome", NBTmachine);
		}
		if (partner != null) {
			NBTTagCompound NBTmachine = new NBTTagCompound();
			partner.writeToNBT(NBTmachine);
			nbttagcompound.setTag("Partner", NBTmachine);
		}

	}

	@Override
	public void setGender(IAlleleGender gender) {
		IChromosome chr = genome.getChromosomes()[CropChromosome.GENDER.ordinal()];
		chr = new Chromosome(gender);
		chr.getActiveAllele();
		IChromosome chr2 = genome.getChromosomes()[CropChromosome.GENDER.ordinal()];
		chr2 = new Chromosome(gender);
	}

	@Override
	public void addTooltip(List<String> list, ItemStack stack) {
		if(stack.getTagCompound().hasKey("Fruit"))
		{
		list.add(StatCollector.translateToLocal("plants.crops.fruit.stage") + ": " + (stack.getTagCompound().getCompoundTag("Fruit").getInteger("Stage") + 1));
		list.add(StatCollector.translateToLocal("plants.crops.fruit.time") + ": " + (stack.getTagCompound().getCompoundTag("Fruit").getInteger("Time")));
		}
		else
		{
			list.add(StatCollector.translateToLocal("allele.gender") + ": " + StatCollector.translateToLocal(getGenome().getActiveAllele(CropChromosome.GENDER).getUID().replace("fb.", "")));
			list.add(StatCollector.translateToLocal("allele.growth.stage") + ": " + stack.getTagCompound().getCompoundTag("Crop").getInteger("GrowthStage"));
			list.add(StatCollector.translateToLocal("allele.growth.time") + ": " + stack.getTagCompound().getCompoundTag("Crop").getInteger("GrowthTime"));
		}
	}

	@Override
	public IPlant mutatePlant(IInfuser infuser) {
		return createPlant(infuser);
	}
	
	private ICrop createPlant(IInfuser infuser)
	{
		World world = infuser.getWorld();

		IChromosome[] chromosomes = new IChromosome[((ItemPlant)infuser.getMale().getItem()).getPlant(infuser.getMale()).getGenome().getChromosomes().length];
		IChromosome[] parent1 = ((ItemPlant)infuser.getMale().getItem()).getPlant(infuser.getMale()).getGenome().getChromosomes();
		IChromosome[] parent2 = ((ItemPlant)infuser.getFemale().getItem()).getPlant(infuser.getMale()).getGenome().getChromosomes();

		IChromosome[] mutated1 = mutate(infuser, ((ItemPlant)infuser.getMale().getItem()).getPlant(infuser.getMale()).getGenome(), ((ItemPlant)infuser.getFemale().getItem()).getPlant(infuser.getMale()).getGenome());
		if (mutated1 != null) {
			parent1 = mutated1;
		}
		IChromosome[] mutated2 = mutate(infuser, ((ItemPlant)infuser.getMale().getItem()).getPlant(infuser.getFemale()).getGenome(), ((ItemPlant)infuser.getMale().getItem()).getPlant(infuser.getMale()).getGenome());
		if (mutated2 != null) {
			parent2 = mutated2;
		}

		for (int i = 0; i < parent1.length; i++) {
			if (parent1[i] != null && parent2[i] != null) {
				chromosomes[i] = Chromosome.inheritChromosome(world.rand, parent1[i], parent2[i]);
			}
		}

		return new Crop(new CropGenome(chromosomes));
	}
	
	private IChromosome[] mutate(IInfuser chamber, IGenome genomeMale, IGenome genomeFemale)
	{
		World world = chamber.getWorld();
		
		IChromosome[] parentMale = genomeMale.getChromosomes();
		IChromosome[] parentFemale = genomeFemale.getChromosomes();

		IGenome genome0;
		IGenome genome1;
		
		IAllele alleleMale;
		IAllele alleleFemale;

		if (world.rand.nextBoolean()) {
			alleleMale = parentMale[CropChromosome.PLANT.ordinal()].getPrimaryAllele();
			alleleFemale = parentFemale[CropChromosome.PLANT.ordinal()].getSecondaryAllele();

			genome0 = genomeMale;
			genome1 = genomeFemale;
		} else {
			alleleMale = parentMale[CropChromosome.PLANT.ordinal()].getSecondaryAllele();
			alleleFemale = parentFemale[CropChromosome.PLANT.ordinal()].getPrimaryAllele();

			genome0 = genomeMale;
			genome1 = genomeFemale;
		}

		IPlantMutation mutation = MutationManager.getMatchCropMutation(alleleMale, alleleFemale, chamber.getTank().getFluid(), chamber.getSoil());
		if(mutation == null)
			return null;
		if(mutation.getChance() > 100 || world.rand.nextInt(100) < mutation.getChance())
		{
			return PlantManager.cropManager.templateAsChromosomes(mutation.getTemplate());
		}

		return null;
	}
}
