package de.nedelosk.forestmods.library.gui;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import de.nedelosk.forestmods.library.inventory.IGuiHandler;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

public interface IGuiBase<T extends IGuiHandler> {

	@SideOnly(Side.CLIENT)
	IButtonManager getButtonManager();

	@SideOnly(Side.CLIENT)
	IWidgetManager getWidgetManager();

	@SideOnly(Side.CLIENT)
	void setZLevel(float zLevel);

	@SideOnly(Side.CLIENT)
	float getZLevel();

	@SideOnly(Side.CLIENT)
	int getGuiLeft();

	@SideOnly(Side.CLIENT)
	int getGuiTop();

	@SideOnly(Side.CLIENT)
	FontRenderer getFontRenderer();

	void drawItemStack(ItemStack stack, int x, int y);
	
	Gui getGui();

	EntityPlayer getPlayer();

	T getHandler();
	
	
}
