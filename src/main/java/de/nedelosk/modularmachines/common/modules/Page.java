package de.nedelosk.modularmachines.common.modules;

import de.nedelosk.modularmachines.api.gui.IGuiHandler;
import de.nedelosk.modularmachines.api.gui.IGuiProvider;
import de.nedelosk.modularmachines.api.gui.IPage;
import de.nedelosk.modularmachines.common.utils.RenderUtil;
import net.minecraft.client.gui.FontRenderer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public abstract class Page<T extends IGuiHandler> implements IPage<T> {

	protected String title;
	@SideOnly(Side.CLIENT)
	protected IGuiProvider gui;

	public Page(String title) {
		this.title = title;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void updateGui() {
	}

	@SideOnly(Side.CLIENT)
	protected boolean renderPageTitle() {
		return true;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void drawForeground(FontRenderer fontRenderer, int mouseX, int mouseY) {
		if (renderPageTitle()) {
			fontRenderer.drawString(getPageTitle(), 90 - (fontRenderer.getStringWidth(getPageTitle()) / 2),
					6, 4210752);
		}
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void drawBackground(int mouseX, int mouseY) {
		if(gui != null){
			RenderUtil.bindTexture(getGuiTexture());
			gui.getGui().drawTexturedModalRect(gui.getGuiLeft(), gui.getGuiTop(), 0, 0, getXSize(), getYSize());
		}
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void drawFrontBackground(int mouseX, int mouseY) {
	}

	@SideOnly(Side.CLIENT)
	@Override
	public int getXSize() {
		return 176;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public int getYSize() {
		return 166;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void addButtons() {
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void addWidgets() {
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void handleMouseClicked(int mouseX, int mouseY, int mouseButton) {
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void drawTooltips(int mouseX, int mouseY) {
	}

	@SideOnly(Side.CLIENT)
	@Override
	public IGuiProvider getGui() {
		return gui;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void setGui(IGuiProvider gui) {
		this.gui = gui;
	}

}
