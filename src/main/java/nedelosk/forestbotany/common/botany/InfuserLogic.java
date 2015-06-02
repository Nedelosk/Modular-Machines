package nedelosk.forestbotany.common.botany;

import nedelosk.forestbotany.api.botany.IInfuser;
import nedelosk.forestbotany.api.botany.IInfuserChamber;
import nedelosk.forestbotany.api.botany.IInfuserLogic;
import nedelosk.forestbotany.api.botany.IInfuserChamber.PlantStatus;
import nedelosk.forestbotany.api.genetics.plants.IPlant;
import nedelosk.forestbotany.common.core.config.Config;
import nedelosk.forestbotany.common.genetics.PlantManager;
import nedelosk.forestbotany.common.genetics.allele.Allele;
import nedelosk.forestbotany.common.items.ItemPlant;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class InfuserLogic implements IInfuserLogic {
 
	private IInfuser infuser;
	private int mutationTime;
	
	public InfuserLogic(IInfuser infuser) {
		this.infuser = infuser;
	}
	
	@Override
	public void update()
	{
		if(infuser.getSoil() == null)
			return;
		if(infuser.getChambers().length < 2)
			return;
		for(IInfuserChamber chamber : infuser.getChambers())
		{
			if(chamber == null)
				return;
			if(chamber.getPlantStatus() == null)
				return;
			if(!(chamber.getPlantStatus() == PlantStatus.Plant || chamber.getPlantStatus() == PlantStatus.Mature))
				return;
		}
		boolean hasMale = false;
		boolean hasFemale = false;
		if(((ItemPlant)infuser.getChambers()[0].getPlant().getItem()).getGender(infuser.getChambers()[0].getPlant()) == Allele.male || ((ItemPlant)infuser.getChambers()[1].getPlant().getItem()).getGender(infuser.getChambers()[1].getPlant()) == Allele.male)
			hasMale = true;
		
		if(((ItemPlant)infuser.getChambers()[0].getPlant().getItem()).getGender(infuser.getChambers()[0].getPlant()) == Allele.female || ((ItemPlant)infuser.getChambers()[1].getPlant().getItem()).getGender(infuser.getChambers()[1].getPlant()) == Allele.female)
			hasFemale = true;
		
		if(hasFemale && hasMale)
		{
			if(mutationTime >= Config.mutationTime)
			{
			IPlant plant = ((ItemPlant)infuser.getChambers()[0].getPlant().getItem()).getPlant(infuser.getChambers()[0].getPlant()).mutatePlant(infuser);
			
			ItemStack seed = PlantManager.getPlantManager(infuser.getChambers()[0].getPlant()).getMemberStack(plant, 0);
			seed.getTagCompound().getCompoundTag("Crop").setBoolean("IsCrop", true);
			infuser.setOutputPlant(seed);
			mutationTime = 0;
			}
			else
			{
				mutationTime++;
			}
		}
		
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		this.mutationTime = nbt.getInteger("MutationTime");
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		nbt.setInteger("MutationTime", this.mutationTime);
	}
	
}
