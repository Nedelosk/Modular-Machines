package de.nedelosk.modularmachines.common.modular;

import de.nedelosk.modularmachines.api.modular.IModularAssembler;
import de.nedelosk.modularmachines.api.modular.handlers.IModularHandler;
import de.nedelosk.modularmachines.client.gui.GuiAssembler;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.items.IItemHandler;

public abstract class ModularAssembler implements IModularAssembler {

	protected final AssemblerItemHandler assemblerHandler;
	protected final IModularHandler modularHandler;

	public ModularAssembler(IModularHandler modularHandler, NBTTagCompound nbtTag) {
		this.modularHandler = modularHandler;
		this.assemblerHandler = new AssemblerItemHandler();
		deserializeNBT(nbtTag);
	}

	public ModularAssembler(IModularHandler modularHandler, ItemStack[] moduleStacks) {
		this.modularHandler = modularHandler;
		this.assemblerHandler = new AssemblerItemHandler(moduleStacks);
	}

	@Override
	public NBTTagCompound serializeNBT() {
		NBTTagCompound nbt = new NBTTagCompound();
		nbt.setTag("assemblerHandler", assemblerHandler.serializeNBT());
		return nbt;
	}

	@Override
	public void deserializeNBT(NBTTagCompound nbt) {
		assemblerHandler.deserializeNBT(nbt.getCompoundTag("assemblerHandler"));
	}

	@Override
	public IModularHandler getHandler() {
		return modularHandler;
	}

	@Override
	public GuiContainer createGui(IModularHandler tile, InventoryPlayer inventory) {
		return new GuiAssembler(tile, inventory);
	}

	@Override
	public IItemHandler getAssemblerHandler() {
		return assemblerHandler;
	}
}
