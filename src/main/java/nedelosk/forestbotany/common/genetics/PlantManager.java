package nedelosk.forestbotany.common.genetics;

import java.util.ArrayList;
import java.util.List;

import nedelosk.forestbotany.api.genetics.IChromosome;
import nedelosk.forestbotany.api.genetics.IPlantManager;
import nedelosk.forestbotany.api.genetics.allele.IAllele;
import nedelosk.forestbotany.api.genetics.plants.IPlant;
import nedelosk.forestbotany.common.genetics.templates.crop.CropManager;
import nedelosk.forestbotany.common.genetics.templates.tree.TreeManager;
import net.minecraft.item.ItemStack;

public abstract class PlantManager implements IPlantManager {
	
	public static TreeManager treeManager;
	public static CropManager cropManager;
	
	protected List<IPlant> plantTemplates = new ArrayList<IPlant>();
	
	public List<IPlant> getTemplates() {
		return plantTemplates;
	}
	
	@Override
	public IChromosome[] templateAsChromosomes(IAllele[] templateActive, IAllele[] templateInactive) {
		Chromosome[] chromosomes = new Chromosome[templateActive.length];
		for (int i = 0; i < templateActive.length; i++) {
			if (templateActive[i] != null) {
				chromosomes[i] = new Chromosome(templateActive[i], templateInactive[i]);
			}
		}

		return chromosomes;
	}
	
	@Override
	public IChromosome[] templateAsChromosomes(IAllele[] template) {
		Chromosome[] chromosomes = new Chromosome[template.length];
		for (int i = 0; i < template.length; i++) {
			if (template[i] != null) {
				chromosomes[i] = new Chromosome(template[i]);
			}
		}

		return chromosomes;
	}
	
	public static PlantManager getPlantManager(ItemStack stack)
	{
		if(stack == null)
			return null;
		if(!stack.hasTagCompound())
			return null;
		if(stack.getTagCompound().hasKey("Tree"))
		{
			return treeManager;
		}
		else if(stack.getTagCompound().hasKey("Crop"))
		{
			return cropManager;
		}
		else
		{
			return null;
		}
	}
	
	public static PlantManager getPlantManager(String plant)
	{
		if(plant == "Tree")
		{
			return treeManager;
		}
		else if(plant == "Crop")
		{
			return cropManager;
		}
		else
		{
			return null;
		}
	}
	
}
