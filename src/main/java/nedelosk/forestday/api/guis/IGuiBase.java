package nedelosk.forestday.api.guis;

import net.minecraft.inventory.IInventory;

public interface IGuiBase<T extends IInventory> {

	IButtonManager getButtonManager();

	IWidgetManager getWidgetManager();

	T getTile();

	void setZLevel(float zLevel);
	
	float getZLevel();

	int getGuiLeft();

	int getGuiTop();
	
	void drawTexturedModalRect(int x, int y, int tx, int ty, int w, int h);

}
