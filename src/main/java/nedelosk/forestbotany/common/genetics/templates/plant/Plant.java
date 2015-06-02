package nedelosk.forestbotany.common.genetics.templates.plant;

import nedelosk.forestbotany.api.genetics.IGenome;
import nedelosk.forestbotany.api.genetics.plants.IPlant;
import nedelosk.forestbotany.common.genetics.templates.tree.TreeTemplates;
import net.minecraft.nbt.NBTTagCompound;

public abstract class Plant implements IPlant {

	protected IGenome genome;
	protected IGenome partner;
	
	public IGenome getPartner()
	{
		return partner;
	}
	
	public IGenome getGenome() {
		return genome;
	}
	
	public void setGenome(IGenome genome) {
		this.genome = genome;
	}
	
}
