package nedelosk.forestbotany.common.book;

import java.util.ArrayList;
import java.util.List;

import nedelosk.forestbotany.api.genetics.allele.IAllelePlant;
import nedelosk.forestbotany.api.genetics.plants.IPlant;
import nedelosk.forestbotany.common.core.tracker.PlantTracker;
import nedelosk.forestbotany.common.genetics.PlantManager;
import nedelosk.forestbotany.common.genetics.allele.Allele;
import nedelosk.forestbotany.common.genetics.templates.crop.CropChromosome;
import net.minecraft.world.World;

import com.mojang.authlib.GameProfile;

public class PlantBookManager {

	public static List<IPlant> getUnlockPlants(GameProfile player, String plantName, World world)
	{
		List<IPlant> plants = new ArrayList<IPlant>();
		if(PlantManager.getPlantManager(plantName) == null)
			return null;
		for(IPlant plant : PlantManager.getPlantManager(plantName).getTemplates())
		{
			if(plant.getGenome().getActiveAllele(CropChromosome.GENDER) == Allele.male && getPlantTracker(world, player).isDiscovered((IAllelePlant)plant.getGenome().getActiveAllele(CropChromosome.PLANT)))
			{
				plants.add(plant);
			}
		}
		return plants;
	}
	
	public static void unlockPlants(GameProfile player, IAllelePlant plant, World world)
	{
		getPlantTracker(world, player).registerPlant(plant);
	}
	
	public static PlantTracker getPlantTracker(World world, GameProfile player) {
		String filename = "PlantTracker." + (player == null ? "common" : player.getId());
		PlantTracker tracker = (PlantTracker) world.loadItemData(PlantTracker.class, filename);

		if (tracker == null) {
			tracker = new PlantTracker(filename);
			world.setItemData(filename, tracker);
		}

		return tracker;
	}
	
}
