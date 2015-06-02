package nedelosk.forestbotany.common.genetics;

import java.util.Arrays;

import nedelosk.forestbotany.api.genetics.IChromosome;
import nedelosk.forestbotany.api.genetics.IGenome;
import nedelosk.forestbotany.api.genetics.IPlantManager;
import nedelosk.forestbotany.api.genetics.allele.IAllele;
import nedelosk.forestbotany.api.genetics.templates.IPlantChromosome;
import nedelosk.forestbotany.common.genetics.templates.tree.TreeChromosome;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

public abstract class Genome implements IGenome {
	
	private IChromosome[] chromosomes;
	
	public Genome(NBTTagCompound nbttagcompound) {
		this.chromosomes = new Chromosome[getPlantManager().getDefaultPlant().length];
		readFromNBT(nbttagcompound);
	}

	public Genome(IChromosome[] chromosomes) {
		this.chromosomes = chromosomes;
	}
	
	@Override
	public IAllele getActiveAllele(IPlantChromosome chromosomePlant) {
		return chromosomes[chromosomePlant.ordinal()].getActiveAllele();
	}

	@Override
	public IAllele getInactiveAllele(IPlantChromosome chromosomePlant) {
		return chromosomes[chromosomePlant.ordinal()].getInactiveAllele();
	}
	
	public static IChromosome getChromosome(ItemStack itemStack, IPlantChromosome chromosomePlant, IPlantManager plantManager) {
		NBTTagCompound nbtTagCompound = itemStack.getTagCompound();
		if (nbtTagCompound == null) {
			return null;
		}

		NBTTagCompound genome = nbtTagCompound.getCompoundTag("Genome");
		if (genome == null) {
			return null;
		}

		IChromosome[] chromosomes = getChromosomes(genome, plantManager);
		if (chromosomes == null) {
			return null;
		}

		return chromosomes[chromosomePlant.ordinal()];
	}

	@Override
	public void readFromNBT(NBTTagCompound nbttagcompound) {
		chromosomes = getChromosomes(nbttagcompound, getPlantManager());
	}
	
	public static IChromosome[] getChromosomes(NBTTagCompound genomeNBT, IPlantManager plantManager) {

		NBTTagList chromosomesNBT = genomeNBT.getTagList("Chromosomes", 10);
		IChromosome[] chromosomes = new IChromosome[plantManager.getDefaultPlant().length];

		for (int i = 0; i < chromosomesNBT.tagCount(); i++) {
			NBTTagCompound chromosomeNBT = chromosomesNBT.getCompoundTagAt(i);
			byte chromosomeOrdinal = chromosomeNBT.getByte("Slot");

			if (chromosomeOrdinal >= 0 && chromosomeOrdinal < chromosomes.length) {
				Chromosome chromosome = Chromosome.loadChromosomeFromNBT(chromosomeNBT);
				chromosomes[chromosomeOrdinal] = chromosome;
			}
		}

		return chromosomes;
	}
	
	public static IAllele getActiveAllele(ItemStack itemStack, IPlantChromosome chromosomePlant, IPlantManager plantManager) {
		IChromosome chromosome = getChromosome(itemStack, chromosomePlant, plantManager);
		if (chromosome == null) {
			return null;
		}
		return chromosome.getActiveAllele();
	}

	@Override
	public void writeToNBT(NBTTagCompound nbttagcompound) {

		NBTTagList nbttaglist = new NBTTagList();
		for (int i = 0; i < chromosomes.length; i++) {
			if (chromosomes[i] != null) {
				NBTTagCompound nbttagcompound1 = new NBTTagCompound();
				nbttagcompound1.setByte("Slot", (byte) i);
				chromosomes[i].writeToNBT(nbttagcompound1);
				nbttaglist.appendTag(nbttagcompound1);
			}
		}
		nbttagcompound.setTag("Chromosomes", nbttaglist);
	}
	
	@Override
	public IChromosome[] getChromosomes() {
		return Arrays.copyOf(chromosomes, chromosomes.length);
	}
	
}

