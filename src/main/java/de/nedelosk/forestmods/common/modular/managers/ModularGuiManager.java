package de.nedelosk.forestmods.common.modular.managers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import de.nedelosk.forestmods.api.modular.managers.DefaultModularManager;
import de.nedelosk.forestmods.api.modular.managers.IModularGuiManager;
import de.nedelosk.forestmods.api.modular.managers.IModularModuleManager;
import de.nedelosk.forestmods.api.producers.IModule;
import de.nedelosk.forestmods.api.producers.IModuleAdvanced;
import de.nedelosk.forestmods.api.producers.handlers.gui.IModuleGui;
import de.nedelosk.forestmods.api.utils.ModuleCategoryUIDs;
import de.nedelosk.forestmods.api.utils.ModuleStack;
import de.nedelosk.forestmods.api.utils.ModuleUID;
import de.nedelosk.forestmods.client.gui.GuiModularMachines;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;

public class ModularGuiManager extends DefaultModularManager implements IModularGuiManager {

	private IModuleGui currentGui;

	public ModularGuiManager() {
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		String guiName = nbt.getString("Gui");
		if (guiName == null || guiName.equals("")) {
			currentGui = getCasingGui();
		} else {
			currentGui = getGui(new ModuleUID(guiName));
		}
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		if (currentGui == null) {
			currentGui = getCasingGui();
		}
		nbt.setString("Gui", currentGui.getUID().toString());
	}

	private IModuleGui getCasingGui() {
		return getGui(ModuleCategoryUIDs.CASING);
	}

	@Override
	public IModuleGui getGui(ModuleUID UID) {
		ModuleContainer container = modular.getManager(IModularModuleManager.class).getModuleStack(UID);
		if (container != null && container.getGui() != null) {
			return container.getGui();
		}
		return getCasingGui();
	}

	@Override
	public IModuleGui getGui(Class<? extends IModule> moduleClass) {
		ModuleContainer container = ((List<ModuleContainer>) modular.getManager(IModularModuleManager.class).getModuleSatcks(moduleClass)).get(0);
		if (container != null && container.getGui() != null) {
			return container.getGui();
		}
		return getCasingGui();
	}

	@Override
	public IModuleGui getCurrentGui() {
		if (currentGui == null) {
			currentGui = getCasingGui();
		}
		return currentGui;
	}

	@Override
	public void setCurrentGui(IModuleGui gui) {
		if (currentGui == null) {
			currentGui = getCasingGui();
		}
		if (gui == null || gui.getUID().equals(currentGui.getUID())) {
			return;
		}
		currentGui = gui;
	}

	@Override
	public <T extends TileEntity & IModularState> GuiContainer getGUIContainer(T tile, InventoryPlayer inventory) {
		if (currentGui == null) {
			currentGui = getCasingGui();
		}
		return new GuiModularMachines(tile, inventory, currentGui);
	}

	@Override
	public List<IModuleGui> getAllGuis() {
		List<IModuleGui> guis = new ArrayList();
		for ( IModuleStorage storage : (List<IModuleStorage>) modular.getManager(IModularStorageManager.class).getModuleStacks() ) {
			for ( ModuleContainer container : (Collection<ModuleContainer>) storage.getContainers() ) {
				ModuleStack stack = container.getStack();
				if (stack.getModule() instanceof IModuleAdvanced) {
					if (container.getGui() != null) {
						guis.add(container.getGui());
					}
				}
			}
		}
		return guis;
	}

	@Override
	public void onModularAssembled() {
		for ( IModuleStorage storage : (List<IModuleStorage>) modular.getManager(IModularStorageManager.class).getModuleStacks() ) {
			for ( ModuleContainer container : (Collection<ModuleContainer>) storage.getContainers() ) {
				ModuleStack stack = container.getStack();
				if (stack.getModule() instanceof IModuleAdvanced) {
					if (((IModuleAdvanced) stack.getModule()).createGui(stack) != null) {
						container.setGui(((IModuleAdvanced) stack.getModule()).createGui(stack).init(modular));
					}
				}
			}
		}
	}
}
