package de.nedelosk.forestmods.common.plugins.thermalexpansion;

import de.nedelosk.forestmods.api.modules.IModuleSaver;
import de.nedelosk.forestmods.api.modules.storage.tanks.IModuleTank;
import de.nedelosk.forestmods.api.utils.ModuleStack;
import de.nedelosk.forestmods.common.modules.storage.ModuleTank;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fluids.FluidStack;

public class ModulePortableTank extends ModuleTank {

	public ModulePortableTank(String modifier) {
		super(modifier);
	}

	@Override
	public void setStorageFluid(FluidStack stack, ModuleStack<IModuleTank, IModuleSaver> moduleStack, ItemStack itemStack) {
		if (stack != null) {
			NBTTagCompound nbtTag = new NBTTagCompound();
			NBTTagCompound fluidTag = new NBTTagCompound();
			stack.writeToNBT(fluidTag);
			nbtTag.setTag("Fluid", fluidTag);
			itemStack.setTagCompound(nbtTag);
		}
	}

	@Override
	public FluidStack getStorageFluid(ModuleStack<IModuleTank, IModuleSaver> moduleStack, ItemStack itemStack) {
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
