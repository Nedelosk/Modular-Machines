package nedelosk.modularmachines.api.modular.machines.manager;

import java.util.ArrayList;

import nedelosk.modularmachines.api.modular.machines.basic.IModular;
import nedelosk.modularmachines.api.modular.machines.basic.IModularTileEntity;
import nedelosk.modularmachines.api.modular.utils.ModuleStack;
import nedelosk.nedeloskcore.api.INBTTagable;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;

public interface IModularGuiManager extends INBTTagable {

	ArrayList<ModuleStack> getModuleWithGuis();
	
	ModuleStack getModuleWithGui();
	
	String getPage();
	
	void setPage(String page);
	
	Container getContainer(IModularTileEntity tile, InventoryPlayer inventory);

	Object getGUIContainer(IModularTileEntity tile, InventoryPlayer inventory);
	
	void setModular(IModular modular);
	
}
