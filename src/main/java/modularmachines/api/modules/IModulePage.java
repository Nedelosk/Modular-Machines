package modularmachines.api.modules;

import java.util.List;

import modularmachines.api.gui.IContainerBase;
import modularmachines.api.gui.IPage;
import modularmachines.api.modular.IModular;
import modularmachines.api.modular.handlers.IModularHandler;
import modularmachines.api.modules.handlers.IModuleContentHandlerProvider;
import modularmachines.api.modules.handlers.inventory.IModuleInventory;
import modularmachines.api.modules.handlers.inventory.slots.SlotModule;
import modularmachines.api.modules.handlers.tank.IModuleTank;
import modularmachines.api.modules.state.IModuleState;
import net.minecraft.nbt.NBTTagCompound;

public interface IModulePage extends IPage, IModuleContentHandlerProvider {

	IModuleTank getTank();

	IModuleInventory getInventory();

	void createSlots(IContainerBase<IModularHandler> container, List<SlotModule> modularSlots);

	int getPlayerInvPosition();

	IModular getModular();

	IModuleState getModuleState();

	String getTabTitle();

	String getPageID();

	@Override
	NBTTagCompound serializeNBT();

	@Override
	void deserializeNBT(NBTTagCompound nbt);
}
