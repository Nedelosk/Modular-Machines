package de.nedelosk.modularmachines.api.modules.containers;

import java.util.List;

import javax.annotation.Nonnull;

import de.nedelosk.modularmachines.api.modular.IModular;
import de.nedelosk.modularmachines.api.modules.state.IModuleState;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.INBTSerializable;

public interface IModuleProvider extends INBTSerializable<NBTTagCompound>, Iterable<IModuleState> {

	@Nonnull
	IModuleItemContainer getContainer();

	@Nonnull
	ItemStack getItemStack();

	void addModuleState(@Nonnull IModuleState moduleState);

	@Nonnull
	List<IModuleState> getModuleStates();

	@Nonnull
	IModular getModular();
}
