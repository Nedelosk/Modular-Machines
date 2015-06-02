package nedelosk.forestbotany.common.genetics;

import java.util.Random;

import nedelosk.forestbotany.api.genetics.IChromosome;
import nedelosk.forestbotany.api.genetics.allele.AlleleManager;
import nedelosk.forestbotany.api.genetics.allele.IAllele;
import nedelosk.forestbotany.api.genetics.allele.IAlleleGender;
import net.minecraft.nbt.NBTTagCompound;

public class Chromosome implements IChromosome {
	
	protected final String UID0 = "PrimaryAllele";
	protected final String UID1 = "SecondaryAllele";
	
	private Chromosome() {
	}

	public Chromosome(IAllele allele) {
		primary = secondary = allele;
	}

	public Chromosome(IAllele primary, IAllele secondary) {
		this.primary = primary;
		this.secondary = secondary;
	}
	
	private IAllele primary;
	private IAllele secondary;
	
	@Override
	public IAllele getPrimaryAllele() {
		return primary;
	}
	
	@Override
	public IAllele getSecondaryAllele() {
		return secondary;
	}
	
	public void readFromNBT(NBTTagCompound nbttagcompound) {
		primary = AlleleManager.alleleRegistry.getAllele(nbttagcompound.getString(UID0));
		secondary = AlleleManager.alleleRegistry.getAllele(nbttagcompound.getString(UID1));
	}

	public void writeToNBT(NBTTagCompound nbttagcompound) {
		nbttagcompound.setString(UID0, primary.getUID());
		nbttagcompound.setString(UID1, secondary.getUID());
	}

	public IAllele getActiveAllele() {
		if (primary.isDominant()) {
			return primary;
		}
		if (secondary.isDominant()) {
			return secondary;
		}
		return primary;
	}

	public IAllele getInactiveAllele() {
		if (!secondary.isDominant()) {
			return secondary;
		}
		if (!primary.isDominant()) {
			return primary;
		}
		return secondary;
	}
	
	public static Chromosome loadChromosomeFromNBT(NBTTagCompound compound) {
		Chromosome chromosome = new Chromosome();
		chromosome.readFromNBT(compound);
		return chromosome;
	}
	
	public static IChromosome inheritChromosome(Random rand, IChromosome parent1, IChromosome parent2) {

		IAllele choice1;
		if (rand.nextBoolean()) {
			choice1 = parent1.getPrimaryAllele();
		} else {
			choice1 = parent1.getSecondaryAllele();
		}

		IAllele choice2;
		if (rand.nextBoolean()) {
			choice2 = parent2.getPrimaryAllele();
		} else {
			choice2 = parent2.getSecondaryAllele();
		}

		if (rand.nextBoolean()) {
			return new Chromosome(choice1, choice2);
		} else {
			return new Chromosome(choice2, choice1);
		}
	}

	public IAllele getRandomAllele(Random rand) {
		if (rand.nextBoolean()) {
			return primary;
		} else {
			return secondary;
		}
	}
	
}
