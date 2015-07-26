package nedelosk.nedeloskcore.api.machines;

import net.minecraft.tileentity.TileEntity;

public interface IGuiBase {
	
	IButtonManager getButtonManager();
	
	IWidgetManager getWidgetManager();
	
	public TileEntity getTile();

	void setZLevel(float zLevel);

	int getGuiLeft();
	
	int getGuiTop();

}
