package de.nedelosk.forestmods.api.modules.container;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import de.nedelosk.forestmods.api.modules.IModule;
import de.nedelosk.forestmods.api.modules.IModuleSaver;
import de.nedelosk.forestmods.api.modules.gui.IModuleGui;

@SideOnly(Side.CLIENT)
public class GuiContainer<M extends IModule, S extends IModuleSaver> implements ISingleGuiContainer<M, S> {

	private IModuleGui<M, S> gui;
	private String categoryUID;

	public GuiContainer(IModuleGui<M, S> gui, String categoryUID) {
		this.gui = gui;
		this.categoryUID = categoryUID;
	}

	public GuiContainer() {
	}

	@Override
	public void setGui(IModuleGui<M, S> gui) {
		this.gui = gui;
	}

	@Override
	public IModuleGui<M, S> getGui() {
		return gui;
	}

	@Override
	public String getCategoryUID() {
		return categoryUID;
	}

	@Override
	public void setCategoryUID(String categoryUID) {
		this.categoryUID = categoryUID;
	}
}
