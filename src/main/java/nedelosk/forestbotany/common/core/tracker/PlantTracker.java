package nedelosk.forestbotany.common.core.tracker;

import java.util.ArrayList;

import nedelosk.forestbotany.api.botany.IPlantTracker;
import nedelosk.forestbotany.api.genetics.allele.IAllelePlant;
import nedelosk.forestbotany.api.genetics.plants.IPlantMutation;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.WorldSavedData;

public class PlantTracker extends WorldSavedData implements IPlantTracker {

	private ArrayList<String> discoveredPlants = new ArrayList<String>();
	private ArrayList<String> discoveredMutations = new ArrayList<String>();
	
	public PlantTracker(String s) {
		super(s);
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt) {

		discoveredPlants = new ArrayList<String>();
		int count = nbt.getInteger("PlantsCount");
		for (int i = 0; i < count; i++) {
			discoveredPlants.add(nbt.getString("PD" + i));
		}

		discoveredMutations = new ArrayList<String>();
		count = nbt.getInteger("MutationsCount");
		for (int i = 0; i < count; i++) {
			discoveredMutations.add(nbt.getString("MD" + i));
		}
		
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt) {

		nbt.setInteger("PlantsCount", discoveredPlants.size());
		for (int i = 0; i < discoveredPlants.size(); i++) {
			if (discoveredPlants.get(i) != null) {
				nbt.setString("PD" + i, discoveredPlants.get(i));
			}
		}

		nbt.setInteger("MutationsCount", discoveredMutations.size());
		for (int i = 0; i < discoveredMutations.size(); i++) {
			if (discoveredMutations.get(i) != null) {
				nbt.setString("MD" + i, discoveredMutations.get(i));
			}
		}
	}

	@Override
	public void registerPlant(IAllelePlant plant) {
		if(discoveredPlants.contains(plant))
			return;
		discoveredPlants.add(plant.getUID());
	}

	private static final String MUTATION_FORMAT = "%s-%s=%s";
	
	@Override
	public void registerMutation(IPlantMutation mutation) {
		if(discoveredMutations.contains(mutation))
			return;
		discoveredPlants.add(String.format(MUTATION_FORMAT, mutation.getPlantMale().getUID(), mutation.getPlantFemale().getUID(), mutation.getTemplate()[0].getUID()));
	}

	@Override
	public boolean isDiscovered(IPlantMutation mutation) {
		if(discoveredMutations.contains(String.format(MUTATION_FORMAT, mutation.getPlantMale().getUID(), mutation.getPlantFemale().getUID(), mutation.getTemplate()[0].getUID())))
			return true;
		return false;
	}

	@Override
	public boolean isDiscovered(IAllelePlant plant) {
		if(discoveredPlants.contains(plant.getUID()))
			return true;
		return false;
	}

	@Override
	public void synchToPlayer(EntityPlayer player) {
		
	}

	@Override
	public void decodeFromNBT(NBTTagCompound nbt) {
		readFromNBT(nbt);
	}

	@Override
	public void encodeToNBT(NBTTagCompound nbt) {
		writeToNBT(nbt);
		
	}

}
