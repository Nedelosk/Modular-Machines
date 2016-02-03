package de.nedelosk.forestmods.api.modular.managers;

import java.util.List;
import java.util.Map;

import de.nedelosk.forestmods.api.modular.tile.IModularTileEntity;
import de.nedelosk.forestmods.api.modules.container.IGuiContainer;
import de.nedelosk.forestmods.api.modules.container.IModuleContainer;
import de.nedelosk.forestmods.api.modules.gui.IModuleGui;
import de.nedelosk.forestmods.api.utils.ModuleStack;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.tileentity.TileEntity;

public interface IModularGuiManager extends IModularManager {

	Map<String, IGuiContainer> getGuis();

	List<IModuleGui> getAllGuis();

	IModuleGui getCurrentGui();

	void setCurrentGui(IModuleGui gui);

	void addGuis();

	IModuleGui getGui(ModuleStack stack);

	IModuleGui getGui(String UID);

	void addGui(IModuleGui gui, ModuleStack stack, IModuleContainer moduleContainer);

	<T extends TileEntity & IModularTileEntity> GuiContainer getGUIContainer(T tile, InventoryPlayer inventory);
}
