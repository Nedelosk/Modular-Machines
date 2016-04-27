package de.nedelosk.forestmods.library.modules.handlers;

import java.util.List;

import de.nedelosk.forestmods.library.inventory.IContainerBase;
import de.nedelosk.forestmods.library.modular.IModular;
import de.nedelosk.forestmods.library.modular.IModularTileEntity;
import de.nedelosk.forestmods.library.modules.IModule;
import de.nedelosk.forestmods.library.modules.handlers.inventory.IModuleInventoryBuilder;
import de.nedelosk.forestmods.library.modules.handlers.inventory.slots.SlotModule;
import de.nedelosk.forestmods.library.modules.handlers.tank.IModuleTankBuilder;

public interface IModulePage extends IPage {

	void createHandlers(IModuleInventoryBuilder invBuilder, IModuleTankBuilder tankBuilder);

	void createSlots(IContainerBase<IModularTileEntity> container, List<SlotModule> modularSlots);

	int getPageID();

	IModular getModular();

	IModule getModule();
}
