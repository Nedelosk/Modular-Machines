package nedelosk.forestbotany.common.genetics.templates.tree;

import java.util.ArrayList;
import java.util.List;

import nedelosk.forestbotany.api.genetics.allele.IAllele;
import nedelosk.forestbotany.api.genetics.allele.IAlleleGender;
import nedelosk.forestbotany.api.genetics.plants.IPlant;
import nedelosk.forestbotany.api.genetics.plants.tree.ITreeGenome;
import nedelosk.forestbotany.api.genetics.plants.tree.ITreeManager;
import nedelosk.forestbotany.common.core.registry.ItemRegistry;
import nedelosk.forestbotany.common.genetics.PlantManager;
import nedelosk.forestbotany.common.genetics.allele.Allele;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class TreeManager extends PlantManager implements ITreeManager {
	
	@Override
	public IAllele[] getDefaultPlant() {
		return TreeTemplates.getDefaultPlant();
	}

	@Override
	public ITreeGenome templateAsGenome(IAllele[] template) {
		return new TreeGenome(templateAsChromosomes(template));
	}
	
	@Override
	public ITreeGenome templateAsGenome(IAllele[] templateActive, IAllele[] templateInactive) {
		return new TreeGenome(templateAsChromosomes(templateActive, templateInactive));
	}

	@Override
	public void registerTemplate(IAllele[] template) {
		for(int i = 0;i < 2;i++)
		{
			IAlleleGender gender = (i == 0) ? Allele.male : Allele.female;
		template[TreeChromosome.GENDER.ordinal()] = gender;
		plantTemplates.add(new Tree(templateAsGenome(template)));
		}
	}
	
	@Override
	public ItemStack getMemberStack(IPlant tree, int growthStage) {

		NBTTagCompound nbttagcompound = new NBTTagCompound();
		tree.writeToNBT(nbttagcompound);

		ItemStack treeStack = new ItemStack(ItemRegistry.sapling.item());
		treeStack.setTagCompound(nbttagcompound);

		return treeStack;

	}
	
	public boolean hasGender(IAlleleGender gender, ItemStack stack)
	{
		if(!stack.hasTagCompound())
		{
			return false;
		}
		if(!stack.getTagCompound().hasKey("Genome"))
		{
			return false;
		}
		NBTTagCompound tag = stack.getTagCompound().getCompoundTag("Genome");
		if(new TreeGenome(tag).getActiveAllele(TreeChromosome.GENDER) == gender)
		{
			return true;
		}
		return false;
	}
}
