package nedelosk.modularmachines.api.modular.basic.managers;

import java.util.List;
import java.util.Map;

import nedelosk.forestcore.library.tile.TileBaseInventory;
import nedelosk.modularmachines.api.modular.basic.container.gui.IGuiContainer;
import nedelosk.modularmachines.api.modular.basic.container.module.IModuleContainer;
import nedelosk.modularmachines.api.modular.tile.IModularTileEntity;
import nedelosk.modularmachines.api.modules.IModuleGui;
import nedelosk.modularmachines.api.utils.ModuleStack;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;

public interface IModularGuiManager extends IModularManager {

	Map<String, IGuiContainer> getGuis();

	List<IModuleGui> getAllGuis();

	IModuleGui getCurrentGui();

	void setCurrentGui(IModuleGui gui);

	void testForGuis();

	IModuleGui getGui(ModuleStack stack);

	IModuleGui getGui(String UID);

	void addGui(IModuleGui gui, ModuleStack stack, IModuleContainer moduleContainer);

	<T extends TileBaseInventory & IModularTileEntity> Container getContainer(T tile, InventoryPlayer inventory);

	<T extends TileBaseInventory & IModularTileEntity> GuiContainer getGUIContainer(T tile, InventoryPlayer inventory);
}
