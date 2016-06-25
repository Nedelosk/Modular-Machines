package de.nedelosk.modularmachines.api.modular;

import javax.annotation.Nonnull;

import de.nedelosk.modularmachines.api.modules.IModuleModelHandler;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public interface IModularLogic {

	@Nonnull
	IModularLogicType getType();

	@SideOnly(Side.CLIENT)
	IModuleModelHandler getModelHandler();

	void readFromNBT(NBTTagCompound nbt);

	void writeToNBT(NBTTagCompound nbt);

	@Nonnull
	IModular getModular();

}
