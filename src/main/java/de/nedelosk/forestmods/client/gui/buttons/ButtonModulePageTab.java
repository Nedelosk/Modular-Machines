package de.nedelosk.forestmods.client.gui.buttons;

import java.util.Arrays;
import java.util.List;

import org.lwjgl.opengl.GL11;

import de.nedelosk.forestcore.gui.Button;
import de.nedelosk.forestcore.gui.IGuiBase;
import de.nedelosk.forestcore.utils.RenderUtil;
import de.nedelosk.forestmods.api.modular.IModularTileEntity;
import de.nedelosk.forestmods.api.utils.ModuleStack;
import de.nedelosk.forestmods.client.gui.GuiModularMachines;
import de.nedelosk.forestmods.common.network.PacketHandler;
import de.nedelosk.forestmods.common.network.packets.PacketSelectModulePage;
import net.minecraft.client.Minecraft;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;

public class ButtonModulePageTab extends Button<IModularTileEntity> {

	protected ResourceLocation guiTextureOverlay = RenderUtil.getResourceLocation("forestmods", "modular_machine", "gui");
	public ModuleStack stack;
	public boolean isDown;
	public int pageID;

	public ButtonModulePageTab(int buttonID, int xPos, int yPos, ModuleStack stack, boolean isDown, int tabID) {
		super(buttonID, xPos, yPos, 28, 21, null);
		this.stack = stack;
		this.isDown = isDown;
		this.pageID = tabID;
	}

	@Override
	public void drawButton(Minecraft mc, int mx, int my) {
		GL11.glPushMatrix();
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		GL11.glColor4f(1F, 1F, 1F, 1F);
		GuiModularMachines machine = (GuiModularMachines) mc.currentScreen;
		RenderUtil.bindTexture(guiTextureOverlay);
		machine.drawTexturedModalRect(xPosition, yPosition, stack.getModule().getCurrentPage().getPageID() == pageID ? 74 : 103, isDown ? 237 : 218, 29, 19);
		RenderUtil.bindTexture(RenderUtil.getResourceLocation("modularmachines", "widgets", "gui"));
		machine.drawTexturedModalRect(xPosition, yPosition, 0, 18 + pageID * 18, 18, 18);
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glPopMatrix();
	}

	@Override
	public void onButtonClick(IGuiBase<IModularTileEntity> gui) {
		IModularTileEntity tile = gui.getTile();
		if (!(pageID == stack.getModule().getCurrentPage().getPageID())) {
			stack.getModule().setCurrentPage(pageID);
			PacketHandler.INSTANCE.sendToServer(new PacketSelectModulePage((TileEntity) tile, pageID, stack.getUID()));
		}
	}

	@Override
	public List<String> getTooltip(IGuiBase<IModularTileEntity> gui) {
		return Arrays.asList(Integer.toString(pageID + 1));
	}
}
