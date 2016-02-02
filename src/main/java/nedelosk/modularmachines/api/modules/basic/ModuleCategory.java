package nedelosk.modularmachines.api.modules.basic;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import nedelosk.modularmachines.api.modules.container.gui.GuiContainer;
import nedelosk.modularmachines.api.modules.container.gui.IGuiContainer;
import nedelosk.modularmachines.api.modules.container.gui.MultiGuiContainer;
import nedelosk.modularmachines.api.modules.container.inventory.IInventoryContainer;
import nedelosk.modularmachines.api.modules.container.inventory.InventoryContainer;
import nedelosk.modularmachines.api.modules.container.inventory.MultiInventoryContainer;
import nedelosk.modularmachines.api.modules.container.module.IModuleContainer;
import nedelosk.modularmachines.api.modules.container.module.ModuleContainer;
import nedelosk.modularmachines.api.modules.container.module.MultiModuleContainer;

public class ModuleCategory implements IModuleCategory {

	private final String UID;
	private Class<? extends IModuleContainer> moduleContainer;
	@SideOnly(Side.CLIENT)
	private Class<? extends IGuiContainer> guiContainer;
	private Class<? extends IInventoryContainer> inventoryContainer;

	private ModuleCategory(String UID, Class<? extends IModuleContainer> moduleContainer, Class<? extends IGuiContainer> guiContainer,
			Class<? extends IInventoryContainer> inventoryContainer) {
		this.UID = UID;
		this.moduleContainer = moduleContainer;
		this.guiContainer = guiContainer;
		this.inventoryContainer = inventoryContainer;
	}

	private ModuleCategory(String UID, Class<? extends IModuleContainer> moduleContainer, Class<? extends IGuiContainer> guiContainer) {
		this.UID = UID;
		this.moduleContainer = moduleContainer;
		this.guiContainer = guiContainer;
		this.inventoryContainer = InventoryContainer.class;
	}

	private ModuleCategory(String UID, Class<? extends IModuleContainer> moduleContainer) {
		this.UID = UID;
		this.moduleContainer = moduleContainer;
		this.inventoryContainer = InventoryContainer.class;
		this.guiContainer = GuiContainer.class;
	}

	public ModuleCategory(String UID, boolean isMulti) {
		this.UID = UID;
		if (isMulti) {
			this.moduleContainer = MultiModuleContainer.class;
			this.inventoryContainer = MultiInventoryContainer.class;
			if (FMLCommonHandler.instance().getSide() == Side.CLIENT) {
				this.guiContainer = MultiGuiContainer.class;
			}
		} else {
			this.moduleContainer = ModuleContainer.class;
			this.inventoryContainer = InventoryContainer.class;
			if (FMLCommonHandler.instance().getSide() == Side.CLIENT) {
				this.guiContainer = GuiContainer.class;
			}
		}
	}

	@Override
	public String getUID() {
		return UID;
	}

	@Override
	public Class<? extends IModuleContainer> getModuleContainerClass() {
		return moduleContainer;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public Class<? extends IGuiContainer> getGuiContainerClass() {
		return guiContainer;
	}

	@Override
	public Class<? extends IInventoryContainer> getInventoryContainerClass() {
		return inventoryContainer;
	}
}
