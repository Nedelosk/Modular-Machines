package de.nedelosk.forestmods.common.plugins.enderio;

import de.nedelosk.forestmods.api.modules.IModuleSaver;
import de.nedelosk.forestmods.api.modules.storage.tanks.IModuleTank;
import de.nedelosk.forestmods.api.utils.ModuleStack;
import de.nedelosk.forestmods.common.modules.storage.ModuleTank;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraftforge.fluids.FluidStack;

public class ModuleTankEnderIO extends ModuleTank {

	public ModuleTankEnderIO(String modifier, int capacity) {
		super(modifier, capacity);
	}

	@Override
	public void setStorageFluid(FluidStack stack, ModuleStack<IModuleTank, IModuleSaver> moduleStack, ItemStack itemStack) {
		if (stack != null) {
			NBTTagCompound nbtTag = new NBTTagCompound();
			NBTTagCompound fluidTag = new NBTTagCompound();
			stack.writeToNBT(fluidTag);
			nbtTag.setTag("tankContents", fluidTag);
			nbtTag.setInteger("tankType", itemStack.getItemDamage());
			nbtTag.setInteger("voidMode", 2);
			nbtTag.setByte("eio.abstractMachine", (byte) 1);
			NBTTagCompound displayTag = new NBTTagCompound();
			displayTag.setString("Name", itemStack.getItemDamage() == 0 ? "Fluid Tank (Configured)" : "Pressurized Fluid Tank (Configured)");
			nbtTag.setTag("display", displayTag);
			nbtTag.setTag("Items", new NBTTagList());
			nbtTag.setInteger("slotLayoutVersion", 1);
			nbtTag.setInteger("redstoneControlMode", 0);
			itemStack.setTagCompound(nbtTag);
		}
	}

	@Override
	public FluidStack getStorageFluid(ModuleStack<IModuleTank, IModuleSaver> moduleStack, ItemStack itemStack) {
		if (!itemStack.hasTagCompound()) {
			return null;
		}
		if (!itemStack.getTagCompound().hasKey("tankContents")) {
			return null;
		}
		NBTTagCompound tag = itemStack.getTagCompound();
		NBTTagCompound fluidTag = tag.getCompoundTag("tankContents");
		return FluidStack.loadFluidStackFromNBT(fluidTag);
	}
}