package de.nedelosk.forestmods.api.modules.basic;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import de.nedelosk.forestmods.api.modules.container.GuiContainer;
import de.nedelosk.forestmods.api.modules.container.IGuiContainer;
import de.nedelosk.forestmods.api.modules.container.IInventoryContainer;
import de.nedelosk.forestmods.api.modules.container.IModuleContainer;
import de.nedelosk.forestmods.api.modules.container.InventoryContainer;
import de.nedelosk.forestmods.api.modules.container.ModuleContainer;
import de.nedelosk.forestmods.api.modules.container.MultiGuiContainer;
import de.nedelosk.forestmods.api.modules.container.MultiInventoryContainer;
import de.nedelosk.forestmods.api.modules.container.MultiModuleContainer;

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
