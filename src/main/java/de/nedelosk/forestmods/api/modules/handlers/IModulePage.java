package de.nedelosk.forestmods.api.modules.handlers;

import java.util.List;

import de.nedelosk.forestcore.inventory.IContainerBase;
import de.nedelosk.forestmods.api.modular.IModular;
import de.nedelosk.forestmods.api.modular.IModularTileEntity;
import de.nedelosk.forestmods.api.modules.handlers.inventory.IModuleInventoryBuilder;
import de.nedelosk.forestmods.api.modules.handlers.inventory.slots.SlotModule;
import de.nedelosk.forestmods.api.modules.handlers.tank.IModuleTankBuilder;
import de.nedelosk.forestmods.api.utils.ModuleStack;

public interface IModulePage extends IPage {

	void createHandlers(IModuleInventoryBuilder invBuilder, IModuleTankBuilder tankBuilder);

	void createSlots(IContainerBase<IModularTileEntity> container, List<SlotModule> modularSlots);

	int getPageID();

	IModular getModular();

	ModuleStack getModuleStack();
}
