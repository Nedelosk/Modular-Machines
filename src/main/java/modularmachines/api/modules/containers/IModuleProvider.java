package modularmachines.api.modules.containers;

import java.util.List;

import javax.annotation.Nonnull;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.INBTSerializable;

import modularmachines.api.modular.IModular;
import modularmachines.api.modules.state.IModuleState;

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
