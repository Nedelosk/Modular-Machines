package nedelosk.forestday.api.guis;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;

public interface IGuiBase<T extends IInventory> {

	IButtonManager getButtonManager();

	IWidgetManager getWidgetManager();
	
	EntityPlayer getPlayer();

	T getTile();

	void setZLevel(float zLevel);
	
	float getZLevel();

	int getGuiLeft();

	int getGuiTop();
	
	FontRenderer getFontRenderer();
	
	void drawTexturedModalRect(int x, int y, int tx, int ty, int w, int h);

}
