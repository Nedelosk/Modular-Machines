package de.nedelosk.forestmods.common.modular.managers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.common.collect.Maps;

import de.nedelosk.forestmods.api.modular.IModular;
import de.nedelosk.forestmods.api.modular.managers.IModularGuiManager;
import de.nedelosk.forestmods.api.modular.tile.IModularTileEntity;
import de.nedelosk.forestmods.api.modules.IModule;
import de.nedelosk.forestmods.api.modules.IModuleDefault;
import de.nedelosk.forestmods.api.modules.IModuleSaver;
import de.nedelosk.forestmods.api.modules.container.IGuiContainer;
import de.nedelosk.forestmods.api.modules.container.IModuleContainer;
import de.nedelosk.forestmods.api.modules.container.IMultiGuiContainer;
import de.nedelosk.forestmods.api.modules.container.IMultiModuleContainer;
import de.nedelosk.forestmods.api.modules.container.ISingleGuiContainer;
import de.nedelosk.forestmods.api.modules.container.ISingleModuleContainer;
import de.nedelosk.forestmods.api.modules.gui.IModuleGui;
import de.nedelosk.forestmods.api.utils.ModuleCategoryUIDs;
import de.nedelosk.forestmods.api.utils.ModuleRegistry;
import de.nedelosk.forestmods.api.utils.ModuleStack;
import de.nedelosk.forestmods.client.gui.GuiModularMachines;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;

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
		if (guis.isEmpty()) {
			addGuis();
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
		if (container == null) {
			return null;
		}
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
		return getCasingGui();
	}

	@Override
	public IModuleGui getGui(ModuleStack stack) {
		IModule module = stack.getModule();
		return getGui(module.getUID());
	}

	@Override
	public Map<String, IGuiContainer> getGuis() {
		if (guis.isEmpty()) {
			addGuis();
		}
		return guis;
	}

	@Override
	public void addGuis() {
		if (modular == null) {
			return;
		}
		for ( IModuleContainer container : (Collection<IModuleContainer>) modular.getModuleManager().getModuleContainers().values() ) {
			if (container instanceof ISingleModuleContainer) {
				if (!addGuis(((ISingleModuleContainer) container).getStack(), container)) {
					continue;
				}
			} else if (container instanceof IMultiModuleContainer) {
				IMultiModuleContainer<IModule, IModuleSaver, Collection<ModuleStack<IModule, IModuleSaver>>> multiContainer = (IMultiModuleContainer) container;
				for ( ModuleStack stack : multiContainer.getStacks() ) {
					if (!addGuis(stack, container)) {
						continue;
					}
				}
			}
		}
	}

	protected boolean addGuis(ModuleStack stack, IModuleContainer container) {
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
			if (ModuleRegistry.getCategory(gui.getCategoryUID()) == null) {
				return;
			}
			Class<? extends IGuiContainer> containerClass = ModuleRegistry.getCategory(gui.getCategoryUID()).getGuiContainerClass();
			if (containerClass == null) {
				return;
			}
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
				((ISingleGuiContainer) container).setGui(gui);
				;
			} else if (container instanceof IMultiGuiContainer) {
				if (!(moduleContainer instanceof IMultiModuleContainer)) {
					return;
				}
				int index = ((IMultiModuleContainer) moduleContainer).getIndex(stack);
				((IMultiGuiContainer) container).addGui(index, gui);
			}
			if (container != null) {
				container.setCategoryUID(stack.getModule().getCategoryUID());
				guis.put(gui.getCategoryUID(), container);
			}
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
	public <T extends TileEntity & IModularTileEntity> GuiContainer getGUIContainer(T tile, InventoryPlayer inventory) {
		if (currentGui == null) {
			currentGui = getCasingGui();
		}
		return new GuiModularMachines(tile, inventory, currentGui);
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
