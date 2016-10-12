package de.nedelosk.modularmachines.api.gui;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.RenderItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public interface IGuiBase<T extends IGuiProvider> {

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

	@SideOnly(Side.CLIENT)
	RenderItem getRenderItem();

	@SideOnly(Side.CLIENT)
	void drawItemStack(ItemStack stack, int x, int y);

	@SideOnly(Side.CLIENT)
	Gui getGui();

	EntityPlayer getPlayer();

	T getHandler();


}
