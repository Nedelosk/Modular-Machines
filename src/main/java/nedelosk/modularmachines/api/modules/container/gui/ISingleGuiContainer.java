package nedelosk.modularmachines.api.modules.container.gui;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import nedelosk.modularmachines.api.modules.IModule;
import nedelosk.modularmachines.api.modules.IModuleSaver;
import nedelosk.modularmachines.api.modules.gui.IModuleGui;

@SideOnly(Side.CLIENT)
public interface ISingleGuiContainer<M extends IModule, S extends IModuleSaver> extends IGuiContainer {

	void setGui(IModuleGui<M, S> gui);

	IModuleGui<M, S> getGui();
}
