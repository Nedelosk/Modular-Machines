package nedelosk.modularmachines.api.modular.basic.managers;

import java.util.ArrayList;
import java.util.Vector;

import nedelosk.forestday.api.tile.TileBaseInventory;
import nedelosk.modularmachines.api.client.gui.GuiModularMachine;
import nedelosk.modularmachines.api.inventory.ContainerModularMachine;
import nedelosk.modularmachines.api.modular.IModular;
import nedelosk.modularmachines.api.modular.tile.IModularTileEntity;
import nedelosk.modularmachines.api.packets.pages.ModularPageSaver;
import nedelosk.modularmachines.api.producers.gui.IProducerGui;
import nedelosk.modularmachines.api.utils.ModuleStack;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;

public class ModularGuiManager implements IModularGuiManager {

	public IModular modular;
	private String page = "";

	public ModularGuiManager() {
	}

	@Override
	public void setModular(IModular modular) {
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
		for (Vector<ModuleStack> stacks : modular.getModules().values())
			for (ModuleStack module : stacks) {
				if (module != null && module.getProducer() != null)
					if (module.getProducer() instanceof IProducerGui)
						guis.add(module);
			}
		return guis;
	}

	@Override
	public ModuleStack getModuleWithGui(EntityPlayer player, TileEntity tile) {
		if(modular != null){
			if(player.getExtendedProperties(ModularPageSaver.class.getName()) != null && ((ModularPageSaver)player.getExtendedProperties(ModularPageSaver.class.getName())).getSave(tile.xCoord, tile.yCoord, tile.zCoord) != null)
				this.page = ((ModularPageSaver)player.getExtendedProperties(ModularPageSaver.class.getName())).getSave(tile.xCoord, tile.yCoord, tile.zCoord).page;
			else
				page = getModuleWithGuis().get(0).getModule().getName(getModuleWithGuis().get(0), false);
		}
		for (ModuleStack module : getModuleWithGuis()) {
			if (module.getModule().getName(module, false).equals(page))
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
	public <T extends TileBaseInventory & IModularTileEntity> Container getContainer(T tile, InventoryPlayer inventory) {
		if(modular != null){
			if(inventory.player.getExtendedProperties(ModularPageSaver.class.getName()) != null && ((ModularPageSaver)inventory.player.getExtendedProperties(ModularPageSaver.class.getName())).getSave(tile.getXCoord(), tile.getYCoord(), tile.getZCoord()) != null)
				this.page = ((ModularPageSaver)inventory.player.getExtendedProperties(ModularPageSaver.class.getName())).getSave(tile.getXCoord(), tile.getYCoord(), tile.getZCoord()).page;
			else
				page = getModuleWithGuis().get(0).getModule().getName(getModuleWithGuis().get(0), false);
		}
		if (page == null || page.length() == 0 || page.length() < 0)
			page = getModuleWithGuis().get(0).getModule().getName(getModuleWithGuis().get(0), false);
		return new ContainerModularMachine(tile, inventory);
	}

	@Override
	public <T extends TileBaseInventory & IModularTileEntity> Object getGUIContainer(T tile, InventoryPlayer inventory) {
		if(modular != null){
			if(inventory.player.getExtendedProperties(ModularPageSaver.class.getName()) != null && ((ModularPageSaver)inventory.player.getExtendedProperties(ModularPageSaver.class.getName())).getSave(tile.getXCoord(), tile.getYCoord(), tile.getZCoord()) != null)
				this.page = ((ModularPageSaver)inventory.player.getExtendedProperties(ModularPageSaver.class.getName())).getSave(tile.getXCoord(), tile.getYCoord(), tile.getZCoord()).page;
			else
				page = getModuleWithGuis().get(0).getModule().getName(getModuleWithGuis().get(0), false);
		}
		if (page == null || page.length() == 0 || page.length() < 0)
			page = getModuleWithGuis().get(0).getModule().getName(getModuleWithGuis().get(0), false);
		return new GuiModularMachine(tile, inventory);
	}

}
