package nedelosk.modularmachines.api.modules.container.gui;

import java.util.Collection;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import nedelosk.modularmachines.api.modules.IModule;
import nedelosk.modularmachines.api.modules.IModuleSaver;
import nedelosk.modularmachines.api.modules.gui.IModuleGui;

@SideOnly(Side.CLIENT)
public interface IMultiGuiContainer<M extends IModule, S extends IModuleSaver, O extends Collection<IModuleGui<M, S>>> extends IGuiContainer {

	void addGui(IModuleGui<M, S> gui);

	void addGui(int index, IModuleGui<M, S> gui);

	void setGuis(O collection);

	int getIndex(IModuleGui<M, S> gui);

	O getGuis();

	IModuleGui<M, S> getGui(int index);

	IModuleGui<M, S> getGui(String guiName);
}
