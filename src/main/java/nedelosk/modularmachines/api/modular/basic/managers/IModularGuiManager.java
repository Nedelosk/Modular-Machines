package nedelosk.modularmachines.api.modular.basic.managers;

import java.util.ArrayList;

import nedelosk.forestcore.library.INBTTagable;
import nedelosk.forestcore.library.tile.TileBaseInventory;
import nedelosk.modularmachines.api.modular.IModular;
import nedelosk.modularmachines.api.modular.tile.IModularTileEntity;
import nedelosk.modularmachines.api.utils.ModuleStack;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;

public interface IModularGuiManager extends IModularManager {

	ArrayList<ModuleStack> getModuleWithGuis();

	ModuleStack getModuleWithGui();

	String getPage();

	void setPage(String page);

	<T extends TileBaseInventory & IModularTileEntity> Container getContainer(T tile, InventoryPlayer inventory);

	<T extends TileBaseInventory & IModularTileEntity> GuiContainer getGUIContainer(T tile, InventoryPlayer inventory);

	void setModular(IModular modular);
}
