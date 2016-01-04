package nedelosk.modularmachines.plugins.thermalexpansion;

import nedelosk.modularmachines.api.modular.IModular;
import nedelosk.modularmachines.api.modules.IModule;
import nedelosk.modularmachines.api.producers.fluids.IProducerTank;
import nedelosk.modularmachines.api.producers.fluids.ProducerTank;
import nedelosk.modularmachines.api.utils.ModuleStack;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fluids.FluidStack;

public class ProducerTankThermalExpansion extends ProducerTank {

	public ProducerTankThermalExpansion(String modifier, int capacity) {
		super(modifier, capacity);
	}

	public ProducerTankThermalExpansion(NBTTagCompound nbt, IModular modular, ModuleStack stack) {
		super(nbt, modular, stack);
	}

	@Override
	public void setStorageFluid(FluidStack stack, ModuleStack<IModule, IProducerTank> moduleStack,
			ItemStack itemStack) {
		if (stack != null) {
			NBTTagCompound nbtTag = new NBTTagCompound();
			NBTTagCompound fluidTag = new NBTTagCompound();
			stack.writeToNBT(fluidTag);
			nbtTag.setTag("Fluid", fluidTag);
			itemStack.setTagCompound(nbtTag);
		}
	}

	@Override
	public FluidStack getStorageFluid(ModuleStack<IModule, IProducerTank> moduleStack, ItemStack itemStack) {
		if (!itemStack.hasTagCompound())
			return null;
		if (!itemStack.getTagCompound().hasKey("Fluid"))
			return null;
		NBTTagCompound fluidTag = itemStack.getTagCompound().getCompoundTag("Fluid");
		return FluidStack.loadFluidStackFromNBT(fluidTag);
	}

}
