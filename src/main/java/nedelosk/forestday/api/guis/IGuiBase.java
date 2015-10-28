package nedelosk.forestday.api.guis;

import net.minecraft.inventory.IInventory;

public interface IGuiBase<T extends IInventory> {

	IButtonManager getButtonManager();

	IWidgetManager getWidgetManager();

	public T getTile();

	void setZLevel(float zLevel);

	int getGuiLeft();

	int getGuiTop();

}
