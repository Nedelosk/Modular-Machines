package nedelosk.modularmachines.api.modular.basic.container.gui;

import java.util.ArrayList;
import java.util.List;

import nedelosk.modularmachines.api.modules.IModule;
import nedelosk.modularmachines.api.modules.IModuleSaver;
import nedelosk.modularmachines.api.modules.gui.IModuleGui;

public class MultiGuiContainer<M extends IModule, S extends IModuleSaver> implements IMultiGuiContainer<M, S, List<IModuleGui<M, S>>> {

	private List<IModuleGui<M, S>> guis = new ArrayList();

	public MultiGuiContainer(IModuleGui<M, S> gui) {
		guis.add(gui);
	}

	public MultiGuiContainer() {
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
