package nedelosk.modularmachines.api.modular.managers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.common.collect.Maps;

import nedelosk.forestcore.library.tile.TileBaseInventory;
import nedelosk.modularmachines.api.client.gui.GuiModular;
import nedelosk.modularmachines.api.inventory.ContainerModularMachine;
import nedelosk.modularmachines.api.modular.IModular;
import nedelosk.modularmachines.api.modular.tile.IModularTileEntity;
import nedelosk.modularmachines.api.modules.IModule;
import nedelosk.modularmachines.api.modules.IModuleDefault;
import nedelosk.modularmachines.api.modules.IModuleSaver;
import nedelosk.modularmachines.api.modules.container.gui.IGuiContainer;
import nedelosk.modularmachines.api.modules.container.gui.IMultiGuiContainer;
import nedelosk.modularmachines.api.modules.container.gui.ISingleGuiContainer;
import nedelosk.modularmachines.api.modules.container.module.IModuleContainer;
import nedelosk.modularmachines.api.modules.container.module.IMultiModuleContainer;
import nedelosk.modularmachines.api.modules.container.module.ISingleModuleContainer;
import nedelosk.modularmachines.api.modules.gui.IModuleGui;
import nedelosk.modularmachines.api.utils.ModuleCategoryUIDs;
import nedelosk.modularmachines.api.utils.ModuleRegistry;
import nedelosk.modularmachines.api.utils.ModuleStack;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.nbt.NBTTagCompound;

public class ModularGuiManager implements IModularGuiManager {

	public IModular modular;
	private HashMap<String, IGuiContainer> guis = Maps.newHashMap();
	private IModuleGui currentGui;

	public ModularGuiManager() {
	}

	@Override
	public void setModular(IModular modular) {
		this.modular = modular;
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		if(guis.isEmpty()){
			searchForGuis();
		}
		String guiName = nbt.getString("Gui");
		if (guiName == null || guiName.equals("")) {
			currentGui = getCasingGui();
		} else {
			currentGui = getGui(guiName);
		}
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		if (currentGui == null) {
			currentGui = getCasingGui();
		}
		nbt.setString("Gui", currentGui.getUID());
	}

	private IModuleGui getCasingGui() {
		ISingleGuiContainer container = (ISingleGuiContainer) getGuis().get(ModuleCategoryUIDs.CASING);
		if(container == null)
			return null;
		return container.getGui();
	}

	@Override
	public IModuleGui getGui(String UID) {
		String[] UIDS = UID.split(":");
		IGuiContainer container = getGuis().get(UIDS[0]);
		if (container instanceof ISingleGuiContainer) {
			ISingleGuiContainer single = (ISingleGuiContainer) container;
			if (single.getGui() != null) {
				return single.getGui();
			}
		} else if (container instanceof IMultiGuiContainer) {
			IMultiGuiContainer multi = (IMultiGuiContainer) container;
			IModuleGui gui = multi.getGui(UIDS[1]);
			if (gui != null) {
				return gui;
			}
		}
		return currentGui;
	}

	@Override
	public IModuleGui getGui(ModuleStack stack) {
		IModule module = stack.getModule();
		return getGui(module.getUID());
	}

	@Override
	public Map<String, IGuiContainer> getGuis() {
		if (guis.isEmpty()) {
			searchForGuis();
		}
		return guis;
	}

	@Override
	public void searchForGuis() {
		if (modular == null) {
			return;
		}
		for ( IModuleContainer container : (Collection<IModuleContainer>) modular.getModuleManager().getModuleContainers().values() ) {
			if (container instanceof ISingleModuleContainer) {
				if (!searchForGuis(((ISingleModuleContainer) container).getStack(), container)) {
					continue;
				}
			} else if (container instanceof IMultiModuleContainer) {
				IMultiModuleContainer<IModule, IModuleSaver, Collection<ModuleStack<IModule, IModuleSaver>>> multiContainer = (IMultiModuleContainer) container;
				for ( ModuleStack stack : multiContainer.getStacks() ) {
					if (!searchForGuis(stack, container)) {
						continue;
					}
				}
			}
		}
	}

	protected boolean searchForGuis(ModuleStack stack, IModuleContainer container) {
		IModule module = stack.getModule();
		if (module == null || !(module instanceof IModuleDefault)) {
			return false;
		}
		IModuleGui gui = ((IModuleDefault) module).createGui(stack);
		if (gui == null) {
			return false;
		}
		addGui(gui, stack, container);
		return true;
	}

	@Override
	public void addGui(IModuleGui gui, ModuleStack stack, IModuleContainer moduleContainer) {
		if (guis.get(gui.getCategoryUID()) == null) {
			Class<? extends IGuiContainer> containerClass = ModuleRegistry.getCategory(gui.getCategoryUID()).getGuiContainerClass();
			IGuiContainer container;
			try {
				if (containerClass.isInterface()) {
					return;
				}
				container = containerClass.newInstance();
			} catch (Exception e) {
				return;
			}
			if (container instanceof ISingleGuiContainer) {
				if (!(moduleContainer instanceof ISingleModuleContainer)) {
					return;
				}
				((ISingleGuiContainer) container).setGui(gui);;
			} else if (container instanceof IMultiGuiContainer) {
				if (!(moduleContainer instanceof IMultiModuleContainer)) {
					return;
				}
				int index = ((IMultiModuleContainer) moduleContainer).getIndex(stack);
				((IMultiGuiContainer) container).addGui(index, gui);
			}
			container.setCategoryUID(stack.getModule().getCategoryUID());
			guis.put(gui.getCategoryUID(), container);
		} else {
			IGuiContainer container = guis.get(gui.getCategoryUID());
			if (container instanceof IMultiGuiContainer) {
				if (!(moduleContainer instanceof IMultiModuleContainer)) {
					return;
				}
				int index = ((IMultiModuleContainer) moduleContainer).getIndex(stack);
				((IMultiGuiContainer) container).addGui(index, gui);
			}
		}
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
	public <T extends TileBaseInventory & IModularTileEntity> Container getContainer(T tile, InventoryPlayer inventory) {
		if (currentGui == null) {
			currentGui = getCasingGui();
		}
		return new ContainerModularMachine(tile, inventory);
	}

	@Override
	public <T extends TileBaseInventory & IModularTileEntity> GuiContainer getGUIContainer(T tile, InventoryPlayer inventory) {
		if (currentGui == null) {
			currentGui = getCasingGui();
		}
		return new GuiModular(tile, inventory, currentGui);
	}

	@Override
	public List<IModuleGui> getAllGuis() {
		List<IModuleGui> guis = new ArrayList();
		for ( IGuiContainer container : this.guis.values() ) {
			if (container instanceof ISingleGuiContainer) {
				guis.add(((ISingleGuiContainer) container).getGui());
			} else if (container instanceof IMultiGuiContainer) {
				guis.addAll(((IMultiGuiContainer) container).getGuis());
			}
		}
		return guis;
	}

	@Override
	public IModular getModular() {
		return modular;
	}
}
