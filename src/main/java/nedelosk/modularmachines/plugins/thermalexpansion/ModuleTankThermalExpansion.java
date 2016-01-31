package nedelosk.modularmachines.plugins.thermalexpansion;

import nedelosk.modularmachines.api.modules.storage.tanks.IModuleTank;
import nedelosk.modularmachines.api.modules.storage.tanks.ModuleTank;
import nedelosk.modularmachines.api.utils.ModuleStack;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fluids.FluidStack;

public class ModuleTankThermalExpansion extends ModuleTank {

	public ModuleTankThermalExpansion(String modifier, int capacity) {
		super(modifier, capacity);
	}

	@Override
	public void setStorageFluid(FluidStack stack, ModuleStack<IModuleTank> moduleStack, ItemStack itemStack) {
		if (stack != null) {
			NBTTagCompound nbtTag = new NBTTagCompound();
			NBTTagCompound fluidTag = new NBTTagCompound();
			stack.writeToNBT(fluidTag);
			nbtTag.setTag("Fluid", fluidTag);
			itemStack.setTagCompound(nbtTag);
		}
	}

	@Override
	public FluidStack getStorageFluid(ModuleStack<IModuleTank> moduleStack, ItemStack itemStack) {
		if (!itemStack.hasTagCompound()) {
			return null;
		}
		if (!itemStack.getTagCompound().hasKey("Fluid")) {
			return null;
		}
		NBTTagCompound fluidTag = itemStack.getTagCompound().getCompoundTag("Fluid");
		return FluidStack.loadFluidStackFromNBT(fluidTag);
	}
}
