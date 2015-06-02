package nedelosk.forestbotany.api.botany;

import nedelosk.forestbotany.common.soil.SoilType;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidTank;

public interface IInfuserChamber {

	ItemStack getPlant();
	
	void setPlant(ItemStack stack);
	
	boolean addProduct(ItemStack stack);
	
	SoilType getSoil();
	
	FluidTank getTank();
	
	FluidTank getTankWater();
	
	World getWorld();
	
	PlantStatus getPlantStatus();
	
	ItemStack getFruit(int fruit);
	
	void setFruit(int fruitID, ItemStack fruit);
	
	public enum PlantStatus
	{
		Seed, Sapling, Plant, Mature, None
	}
	
}
