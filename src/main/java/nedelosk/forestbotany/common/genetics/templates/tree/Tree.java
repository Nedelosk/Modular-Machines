package nedelosk.forestbotany.common.genetics.templates.tree;

import java.util.List;

import nedelosk.forestbotany.api.botany.IInfuser;
import nedelosk.forestbotany.api.botany.IInfuserChamber;
import nedelosk.forestbotany.api.genetics.IChromosome;
import nedelosk.forestbotany.api.genetics.IGenome;
import nedelosk.forestbotany.api.genetics.allele.IAlleleGender;
import nedelosk.forestbotany.api.genetics.plants.IPlant;
import nedelosk.forestbotany.api.genetics.plants.tree.ITree;
import nedelosk.forestbotany.common.genetics.Chromosome;
import nedelosk.forestbotany.common.genetics.PlantManager;
import nedelosk.forestbotany.common.genetics.allele.Allele;
import nedelosk.forestbotany.common.genetics.templates.plant.Plant;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class Tree extends Plant implements ITree {

	public Tree(NBTTagCompound nbttagcompound) {
		readFromNBT(nbttagcompound);
	}
	
	public Tree(IGenome genome) {
		setGenome(genome);
	}
	
	private float hardness;
	private float dense;
	
	@Override
	public float getHardness() {
		return hardness;
	}

	@Override
	public float getDense() {
		return dense;
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbttagcompound) {

		if (nbttagcompound.hasKey("Genome")) {
			setGenome(new TreeGenome(nbttagcompound.getCompoundTag("Genome")));
		} else {
			setGenome(PlantManager.treeManager.templateAsGenome(TreeTemplates.getTreeOak()));
		}

		if (nbttagcompound.hasKey("Partner")) {
			partner = new TreeGenome(nbttagcompound.getCompoundTag("Mate"));
		}
		
		if(nbttagcompound.hasKey("Tree"))
		{
			if(nbttagcompound.hasKey("Hardness"))
			{
				hardness = nbttagcompound.getCompoundTag("Tree").getFloat("Hardness");
			}
			if(nbttagcompound.hasKey("Dense"))
			{
				dense = nbttagcompound.getCompoundTag("Tree").getFloat("Dense");
			}
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
		
		if(hardness != 0  || dense != 0)
		{
			if(!nbttagcompound.hasKey("Tree"))
			{
				nbttagcompound.setTag("Tree", new NBTTagCompound());
			}
			if(dense != 0)
			{
				nbttagcompound.getCompoundTag("Tree").setFloat("Dense", dense);
			}
			if(hardness != 0)
			{
				nbttagcompound.getCompoundTag("Tree").setFloat("Hardness", hardness);
			}
		}

	}

	@Override
	public void setGender(IAlleleGender gender) {
		IChromosome chr = genome.getChromosomes()[TreeChromosome.GENDER.ordinal()];
		chr = new Chromosome(gender);
		chr.getActiveAllele();
		IChromosome chr2 = genome.getChromosomes()[TreeChromosome.GENDER.ordinal()];
		chr2 = new Chromosome(gender);
	}

	@Override
	public void addTooltip(List<String> list, ItemStack stack) {
		
	}

	@Override
	public IPlant mutatePlant(IInfuser infuser) {
		return null;
	}
}
