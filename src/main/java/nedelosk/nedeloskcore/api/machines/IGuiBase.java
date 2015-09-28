package nedelosk.nedeloskcore.api.machines;

import net.minecraft.inventory.IInventory;

public interface IGuiBase<T extends IInventory> {
	
	IButtonManager getButtonManager();
	
	IWidgetManager getWidgetManager();
	
	public T getTile();

	void setZLevel(float zLevel);

	int getGuiLeft();
	
	int getGuiTop();

}
