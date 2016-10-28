package de.nedelosk.modularmachines.api.modular;

import javax.annotation.Nonnull;

import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public interface IAssemblerLogic {

	boolean isItemValid(ItemStack stack, Slot slot, Slot storageSlot);

	void canAssemble(IModular modular) throws AssemblerException;

	@Nonnull
	IModularAssembler getAssembler();
}
