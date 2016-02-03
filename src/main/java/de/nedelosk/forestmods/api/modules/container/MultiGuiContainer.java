package de.nedelosk.forestmods.api.modules.container;

import java.util.ArrayList;
import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import de.nedelosk.forestmods.api.modules.IModule;
import de.nedelosk.forestmods.api.modules.IModuleSaver;
import de.nedelosk.forestmods.api.modules.gui.IModuleGui;

@SideOnly(Side.CLIENT)
public class MultiGuiContainer<M extends IModule, S extends IModuleSaver> implements IMultiGuiContainer<M, S, List<IModuleGui<M, S>>> {

	private List<IModuleGui<M, S>> guis = new ArrayList();
	private String categoryUID;

	public MultiGuiContainer(IModuleGui<M, S> gui, String categoryUID) {
		guis.add(gui);
		this.categoryUID = categoryUID;
	}

	public MultiGuiContainer() {
	}

	@Override
	public String getCategoryUID() {
		return categoryUID;
	}

	@Override
	public void setCategoryUID(String categoryUID) {
		this.categoryUID = categoryUID;
	}

	@Override
	public void addGui(IModuleGui<M, S> gui) {
		this.guis.add(gui);
	}

	@Override
	public void addGui(int index, IModuleGui<M, S> gui) {
		this.guis.add(index, gui);
	}

	@Override
	public void setGuis(List<IModuleGui<M, S>> collection) {
		this.guis = collection;
	}

	@Override
	public int getIndex(IModuleGui<M, S> gui) {
		return guis.indexOf(gui);
	}

	@Override
	public List<IModuleGui<M, S>> getGuis() {
		return guis;
	}

	@Override
	public IModuleGui<M, S> getGui(int index) {
		return guis.get(index);
	}

	@Override
	public IModuleGui<M, S> getGui(String guiName) {
		for ( IModuleGui gui : guis ) {
			if (gui.getModuleUID().equals(guiName)) {
				return gui;
			}
		}
		return null;
	}
}
