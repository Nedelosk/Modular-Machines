package nedelosk.modularmachines.api.modular.basic.managers;

import java.util.ArrayList;

import nedelosk.forestcore.api.INBTTagable;
import nedelosk.forestcore.api.tile.TileBaseInventory;
import nedelosk.modularmachines.api.modular.IModular;
import nedelosk.modularmachines.api.modular.tile.IModularTileEntity;
import nedelosk.modularmachines.api.utils.ModuleStack;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.tileentity.TileEntity;

public interface IModularGuiManager extends INBTTagable {

	ArrayList<ModuleStack> getModuleWithGuis();

	ModuleStack getModuleWithGui(EntityPlayer player, TileEntity tile);

	String getPage();

	void setPage(String page);

	<T extends TileBaseInventory & IModularTileEntity> Container getContainer(T tile, InventoryPlayer inventory);

	<T extends TileBaseInventory & IModularTileEntity> Object getGUIContainer(T tile, InventoryPlayer inventory);

	void setModular(IModular modular);

}
