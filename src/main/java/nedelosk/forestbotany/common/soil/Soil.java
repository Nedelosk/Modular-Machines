package nedelosk.forestbotany.common.soil;

import nedelosk.forestbotany.api.soil.ISoil;
import nedelosk.nedeloskcore.common.fluids.FluidTankNedelosk;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;

public class Soil implements ISoil{

	private SoilType soil;
	private FluidTankNedelosk waterTank = new FluidTankNedelosk(16000);
	private FluidTankNedelosk nutrientTank = new FluidTankNedelosk(16000);
	
	public Soil(SoilType soil) {
		this.soil = soil;
	}
	
	@Override
	public SoilType getSoil() {
		return soil;
	}
	
	@Override
	public int getMaxWaterStorage() {
		return soil.getMaxWaterStorage();
	}

	@Override
	public int getMaxNutrientsStorage() {
		return soil.getMaxNutrientsStorage();
	}

	@Override
	public int getWaterStorage() {
		return waterTank.getFluidAmount();
	}

	@Override
	public int getNutrientsStorage() {
		return nutrientTank.getFluidAmount();
	}

	@Override
	public int addWaterToSoil(int amount) {
        return waterTank.fill(new FluidStack(FluidRegistry.WATER, amount), true);
	}

	@Override
	public int getWaterFromSoil(int amount) {
        return waterTank.drain(amount, true).amount;
	}

	@Override
	public int addNutrientsToSoil(int amount) {
        return nutrientTank.fill(new FluidStack(FluidRegistry.WATER, amount), true);
	}

	@Override
	public int getNutrientsFromSoil(int amount) {
        return nutrientTank.drain(amount, true).amount;
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		if(nbt.hasKey("WaterTanks"))
		{
			NBTTagCompound nbtWater = nbt.getCompoundTag("WaterTanks");
			waterTank.readFromNBT(nbtWater);
		}
		if(nbt.hasKey("NutrientTanks"))
		{
			NBTTagCompound nbtNutrient = nbt.getCompoundTag("NutrientTanks");
			nutrientTank.readFromNBT(nbtNutrient);
		}
		
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		NBTTagCompound nbtWater = new NBTTagCompound();
		waterTank.writeToNBT(nbtWater);
		nbt.setTag("WaterTanks", nbtWater);
		
		NBTTagCompound nbtNutrient = new NBTTagCompound();
		nutrientTank.writeToNBT(nbtNutrient);
		nbt.setTag("NutrientTanks", nbtNutrient);
		
	}
}
