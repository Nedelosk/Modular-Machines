package de.nedelosk.modularmachines.api.modular;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import de.nedelosk.modularmachines.api.modular.handlers.IModularHandler;
import de.nedelosk.modularmachines.api.modules.storaged.EnumPosition;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.items.IItemHandler;

public interface IModularAssembler extends INBTSerializable<NBTTagCompound> {

	EnumPosition getSelectedPosition();

	void setSelectedPosition(EnumPosition position);

	@Nonnull
	IModularHandler getHandler();

	@Nullable
	IModular assemble();

	IAssemblerLogic getLogic(EnumPosition pos);

	IItemHandler getAssemblerHandler();

}
