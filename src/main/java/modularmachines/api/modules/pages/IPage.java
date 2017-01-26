package modularmachines.api.modules.pages;

import javax.annotation.Nullable;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.inventory.Container;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public interface IPage {

	@SideOnly(Side.CLIENT)
	@Nullable
	GuiContainer getGui();

	@SideOnly(Side.CLIENT)
	void setGui(GuiContainer gui);

	@Nullable
	Container getContainer();

	void setContainer(Container container);

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
	void addWidgets();

	void detectAndSendChanges();

	String getPageTitle();
	
}
