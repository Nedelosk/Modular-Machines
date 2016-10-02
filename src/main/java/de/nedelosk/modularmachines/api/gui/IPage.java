package de.nedelosk.modularmachines.api.gui;

import net.minecraft.client.gui.FontRenderer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public interface IPage<T extends IGuiHandler> {

	@SideOnly(Side.CLIENT)
	IGuiProvider<T> getGui();

	@SideOnly(Side.CLIENT)
	void setGui(IGuiProvider<T> gui);
	
	IContainerBase<T> getContainer();

	void setContainer(IContainerBase<T> container);

	@SideOnly(Side.CLIENT)
	void updateGui();

	@SideOnly(Side.CLIENT)
	void initGui();

	@SideOnly(Side.CLIENT)
	void handleMouseClicked(int mouseX, int mouseY, int mouseButton);

	@SideOnly(Side.CLIENT)
	void drawForeground(FontRenderer fontRenderer, int mouseX, int mouseY);

	@SideOnly(Side.CLIENT)
	void drawBackground(int mouseX, int mouseY);

	@SideOnly(Side.CLIENT)
	void drawTooltips(int mouseX, int mouseY);

	@SideOnly(Side.CLIENT)
	void drawFrontBackground(int mouseX, int mouseY);

	@SideOnly(Side.CLIENT)
	int getXSize();

	@SideOnly(Side.CLIENT)
	int getYSize();

	@SideOnly(Side.CLIENT)
	void addButtons();

	@SideOnly(Side.CLIENT)
	void addWidgets();
	
	void detectAndSendChanges();

	String getPageTitle();
}
