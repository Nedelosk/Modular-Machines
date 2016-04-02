package de.nedelosk.forestmods.api.modular.managers;

import java.util.List;

import de.nedelosk.forestmods.api.modular.IModularTileEntity;
import de.nedelosk.forestmods.api.producers.IModule;
import de.nedelosk.forestmods.api.producers.handlers.gui.IModuleGui;
import de.nedelosk.forestmods.api.utils.ModuleUID;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.tileentity.TileEntity;

public interface IModularGuiManager extends IModularManager {

	List<IModuleGui> getAllGuis();

	IModuleGui getCurrentGui();

	void setCurrentGui(IModuleGui gui);

	IModuleGui getGui(ModuleUID UID);

	IModuleGui getGui(Class<? extends IModule> moduleClass);

	<T extends TileEntity & IModularTileEntity> GuiContainer getGUIContainer(T tile, InventoryPlayer inventory);
}
