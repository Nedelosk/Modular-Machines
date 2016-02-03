package de.nedelosk.forestmods.api.modules.container;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import de.nedelosk.forestmods.api.modules.IModule;
import de.nedelosk.forestmods.api.modules.IModuleSaver;
import de.nedelosk.forestmods.api.modules.gui.IModuleGui;

@SideOnly(Side.CLIENT)
public interface ISingleGuiContainer<M extends IModule, S extends IModuleSaver> extends IGuiContainer {

	void setGui(IModuleGui<M, S> gui);

	IModuleGui<M, S> getGui();
}
