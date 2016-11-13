package modularmachines.api.modules.containers;

import java.util.List;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;

import modularmachines.api.modules.state.IModuleState;

public interface IModuleItemProvider extends ICapabilitySerializable<NBTTagCompound>, Iterable<IModuleState> {

	@Nullable
	IModuleItemContainer getContainer();

	@Nullable
	ItemStack getItemStack();

	void setItemStack(@Nonnull ItemStack itemStack);

	boolean addModuleState(@Nonnull IModuleState moduleState);

	boolean removeModuleState(@Nonnull IModuleState moduleState);

	boolean isEmpty();

	@Nonnull
	List<IModuleState> getModuleStates();
}
