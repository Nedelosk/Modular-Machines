package de.nedelosk.modularmachines.api.modules.storage;

import java.util.List;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import de.nedelosk.modularmachines.api.gui.IPage;
import de.nedelosk.modularmachines.api.modular.AssemblerException;
import de.nedelosk.modularmachines.api.modular.IModular;
import de.nedelosk.modularmachines.api.modular.IModularAssembler;
import de.nedelosk.modularmachines.api.modular.assembler.IAssemblerContainer;
import de.nedelosk.modularmachines.api.modular.assembler.SlotAssembler;
import de.nedelosk.modularmachines.api.modular.assembler.SlotAssemblerStorage;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public interface IStoragePage extends IPage {

	@Nonnull
	IItemHandlerStorage getItemHandler();

	void createSlots(@Nonnull IAssemblerContainer container, @Nonnull List<Slot> slots);

	boolean isItemValid(ItemStack stack, SlotAssembler slot, SlotAssemblerStorage storageSlot);

	int getPlayerInvPosition();

	void onSlotChanged(@Nonnull IAssemblerContainer container);

	void setAssembler(@Nonnull IModularAssembler assembler);

	@Nullable
	IModularAssembler getAssembler();

	@Nonnull
	ItemStack getStorageStack();

	@Nonnull
	NBTTagCompound serializeNBT();

	void deserializeNBT(NBTTagCompound nbt);

	void canAssemble(@Nonnull IModular modular) throws AssemblerException;

	@Nullable
	IStorage assemble(IModular modular) throws AssemblerException;

}
