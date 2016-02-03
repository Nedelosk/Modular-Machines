package de.nedelosk.forestmods.api.modules.container;

import java.util.Collection;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import de.nedelosk.forestmods.api.modules.IModule;
import de.nedelosk.forestmods.api.modules.IModuleSaver;
import de.nedelosk.forestmods.api.modules.gui.IModuleGui;

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
