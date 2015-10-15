package nedelosk.modularmachines.common.modular.machines.modular.managers;

import java.util.ArrayList;
import java.util.Vector;

import nedelosk.modularmachines.api.modular.machines.basic.IModular;
import nedelosk.modularmachines.api.modular.machines.basic.IModularTileEntity;
import nedelosk.modularmachines.api.modular.machines.manager.IModularGuiManager;
import nedelosk.modularmachines.api.modular.module.basic.gui.IModuleGui;
import nedelosk.modularmachines.api.modular.utils.ModuleStack;
import nedelosk.modularmachines.client.gui.machine.GuiModularMachine;
import nedelosk.modularmachines.common.blocks.tile.TileModular;
import nedelosk.modularmachines.common.inventory.machine.ContainerModularMachine;
import nedelosk.modularmachines.common.network.packets.saver.ModularSaveModule;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.nbt.NBTTagCompound;

public class ModularGuiManager implements IModularGuiManager {

	public IModular modular;
	private String page = "";
	
	public ModularGuiManager() {
	}
	
	@Override
	public void setModular(IModular modular){
		this.modular = modular;
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		page = nbt.getString("Page");
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		nbt.setString("Page", page);
	}

	@Override
	public ArrayList<ModuleStack> getModuleWithGuis() {
		ArrayList<ModuleStack> guis = new ArrayList<ModuleStack>();
		for(Vector<ModuleStack> stacks : modular.getModules().values())
			for(ModuleStack module : stacks)
			{
				if(module != null)
					if(module.getModule() instanceof IModuleGui)
						guis.add(module);
			}
		return guis;
	}
	
	@Override
	public ModuleStack getModuleWithGui()
	{
		for(ModuleStack module : getModuleWithGuis())
		{
			if(module.getModule().getName(module).equals(page))
				return module;
		}
		return null;
	}

	@Override
	public String getPage() {
		return page;
	}
	
	@Override
	public void setPage(String page) {
		this.page = page;
	}

	@Override
	public Container getContainer(IModularTileEntity tile, InventoryPlayer inventory) {
		if(page == null || page.length() == 0 || page.length() < 0 )
			page = modular.getGuiManager().getModuleWithGuis().get(0).getModule().getName(modular.getGuiManager().getModuleWithGuis().get(0));
		return new ContainerModularMachine((TileModular) tile, inventory);
	}

	@Override
	public Object getGUIContainer(IModularTileEntity tile, InventoryPlayer inventory) {
		if(modular != null){
			if(inventory.player.getExtendedProperties(ModularSaveModule.class.getName()) != null)
				if(((ModularSaveModule)inventory.player.getExtendedProperties(ModularSaveModule.class.getName())).getSave(tile.getXCoord(), tile.getYCoord(), tile.getZCoord()) != null)
					this.page = ((ModularSaveModule)inventory.player.getExtendedProperties(ModularSaveModule.class.getName())).getSave(tile.getXCoord(), tile.getYCoord(), tile.getZCoord()).page;
				else
					page = modular.getGuiManager().getModuleWithGuis().get(0).getModule().getName(modular.getGuiManager().getModuleWithGuis().get(0));
		}
		if(page == null || page.length() == 0 || page.length() < 0 )
			page = modular.getGuiManager().getModuleWithGuis().get(0).getModule().getName(modular.getGuiManager().getModuleWithGuis().get(0));
		return new GuiModularMachine((TileModular) tile, inventory);
	}

}
