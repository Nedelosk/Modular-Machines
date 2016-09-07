package de.nedelosk.modularmachines.api.modules.handlers;

import java.util.List;

import de.nedelosk.modularmachines.api.gui.IContainerBase;
import de.nedelosk.modularmachines.api.gui.IPage;
import de.nedelosk.modularmachines.api.modular.IModular;
import de.nedelosk.modularmachines.api.modular.handlers.IModularHandler;
import de.nedelosk.modularmachines.api.modules.handlers.inventory.IModuleInventory;
import de.nedelosk.modularmachines.api.modules.handlers.inventory.slots.SlotModule;
import de.nedelosk.modularmachines.api.modules.handlers.tank.IModuleTank;
import de.nedelosk.modularmachines.api.modules.state.IModuleState;
import net.minecraft.nbt.NBTTagCompound;

public interface IModulePage extends IPage, IModuleContentProvider {

	IModuleTank getTank();

	IModuleInventory getInventory();

	void createSlots(IContainerBase<IModularHandler> container, List<SlotModule> modularSlots);

	int getPlayerInvPosition();

	IModular getModular();

	IModuleState getModuleState();

	String getPageID();

	@Override
	NBTTagCompound serializeNBT();

	@Override
	void deserializeNBT(NBTTagCompound nbt);
}
