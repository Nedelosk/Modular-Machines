package de.nedelosk.modularmachines.api.modules.handlers;

import java.util.List;

import de.nedelosk.modularmachines.api.gui.Button;
import de.nedelosk.modularmachines.api.gui.IGuiBase;
import de.nedelosk.modularmachines.api.gui.IGuiHandler;
import de.nedelosk.modularmachines.api.gui.Widget;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public interface IPage<T extends IGuiHandler> {

	@SideOnly(Side.CLIENT)
	IGuiBase getGui();

	@SideOnly(Side.CLIENT)
	void setGui(IGuiBase gui);

	@SideOnly(Side.CLIENT)
	void updateGui(int mouseX, int mouseY);

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
	void drawSlots();

	@SideOnly(Side.CLIENT)
	void drawPlayerInventory();

	@SideOnly(Side.CLIENT)
	int getXSize();

	@SideOnly(Side.CLIENT)
	int getYSize();

	int getPlayerInvPosition();

	@SideOnly(Side.CLIENT)
	ResourceLocation getGuiTexture();

	@SideOnly(Side.CLIENT)
	void addButtons(List<Button> buttons);

	@SideOnly(Side.CLIENT)
	void addWidgets(List<Widget> widgets);
}
