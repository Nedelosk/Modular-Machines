package nedelosk.modularmachines.api.modular.basic.container.gui;

import java.util.ArrayList;
import java.util.List;

import nedelosk.modularmachines.api.modules.IModule;
import nedelosk.modularmachines.api.modules.gui.IModuleGui;

public class MultiGuiContainer<P extends IModule> implements IMultiGuiContainer<P, List<IModuleGui<P>>> {

	private List<IModuleGui<P>> guis = new ArrayList();

	public MultiGuiContainer(IModuleGui<P> gui) {
		guis.add(gui);
	}

	public MultiGuiContainer() {
	}

	@Override
	public void addGui(IModuleGui<P> gui) {
		this.guis.add(gui);
	}

	@Override
	public void addGui(int index, IModuleGui<P> gui) {
		this.guis.add(index, gui);
	}

	@Override
	public void setGuis(List<IModuleGui<P>> collection) {
		this.guis = collection;
	}

	@Override
	public int getIndex(IModuleGui<P> gui) {
		return guis.indexOf(gui);
	}

	@Override
	public List<IModuleGui<P>> getGuis() {
		return guis;
	}

	@Override
	public IModuleGui<P> getGui(int index) {
		return guis.get(index);
	}

	@Override
	public IModuleGui<P> getGui(String guiName) {
		for ( IModuleGui gui : guis ) {
			if (gui.getModuleUID().equals(guiName)) {
				return gui;
			}
		}
		return null;
	}
}
