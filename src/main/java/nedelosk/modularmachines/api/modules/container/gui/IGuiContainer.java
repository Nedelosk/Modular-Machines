package nedelosk.modularmachines.api.modules.container.gui;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public interface IGuiContainer {

	String getCategoryUID();

	void setCategoryUID(String categoryUID);
}
