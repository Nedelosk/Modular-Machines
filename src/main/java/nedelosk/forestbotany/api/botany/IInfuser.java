package nedelosk.forestbotany.api.botany;

import nedelosk.forestbotany.api.soil.ISoil;
import nedelosk.forestbotany.common.soil.SoilType;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidTank;

public interface IInfuser {

	ItemStack getMale();
	
	ItemStack getFemale();
	
	void setOutputPlant(ItemStack stack);
	
	SoilType getSoil();
	
	World getWorld();
	
	FluidTank getTank();
	
	IInfuserChamber[] getChambers();
	
}
