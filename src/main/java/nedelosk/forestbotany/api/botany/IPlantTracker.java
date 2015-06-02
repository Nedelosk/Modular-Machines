package nedelosk.forestbotany.api.botany;

import nedelosk.forestbotany.api.genetics.allele.IAllelePlant;
import nedelosk.forestbotany.api.genetics.plants.IPlantMutation;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;

public interface IPlantTracker {

	void registerPlant(IAllelePlant plant);

	void registerMutation(IPlantMutation mutation);

	boolean isDiscovered(IPlantMutation mutation);

	boolean isDiscovered(IAllelePlant plant);
	
	void synchToPlayer(EntityPlayer player);
	
	void decodeFromNBT(NBTTagCompound nbt);

	void encodeToNBT(NBTTagCompound nbt);
	
}
