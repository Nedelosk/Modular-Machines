package nedelosk.modularmachines.api.client.gui;

import java.util.Arrays;
import java.util.List;

import org.lwjgl.opengl.GL11;

import nedelosk.forestcore.library.gui.Button;
import nedelosk.forestcore.library.gui.IGuiBase;
import nedelosk.forestcore.library.utils.RenderUtil;
import nedelosk.modularmachines.api.modular.IModular;
import nedelosk.modularmachines.api.modular.tile.IModularTileEntity;
import nedelosk.modularmachines.api.modules.managers.IModuleManager;
import nedelosk.modularmachines.api.modules.managers.IModuleManagerSaver;
import nedelosk.modularmachines.api.packets.PacketHandler;
import nedelosk.modularmachines.api.packets.pages.PacketSelectTankManagerTab;
import nedelosk.modularmachines.api.utils.ModuleStack;
import net.minecraft.client.Minecraft;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;

public class ButtonManagerTab<P extends IModuleManager> extends Button<IModularTileEntity<IModular>> {

	protected ResourceLocation guiTextureOverlay = RenderUtil.getResourceLocation("modularmachines", "modular_machine", "gui");
	public ModuleStack<P> stack;
	public boolean down;
	public int tabID;

	public ButtonManagerTab(int p_i1021_1_, int p_i1021_2_, int p_i1021_3_, ModuleStack<P> stack, boolean down, int tabID) {
		super(p_i1021_1_, p_i1021_2_, p_i1021_3_, 28, 21, null);
		this.stack = stack;
		this.down = down;
		this.tabID = tabID;
	}

	@Override
	public void drawButton(Minecraft mc, int mx, int my) {
		GL11.glPushMatrix();
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		GL11.glColor4f(1F, 1F, 1F, 1F);
		GuiModular machine = (GuiModular) mc.currentScreen;
		IModuleManager manager = stack.getModule();
		IModuleManagerSaver managerSaver = (IModuleManagerSaver) stack.getSaver();
		RenderUtil.bindTexture(guiTextureOverlay);
		machine.drawTexturedModalRect(xPosition, yPosition, managerSaver.getTab() == tabID ? 74 : 103, down ? 237 : 218, 29, 19);
		RenderUtil.bindTexture(RenderUtil.getResourceLocation("modularmachines", "widgets", "gui"));
		machine.drawTexturedModalRect(xPosition, yPosition, 0, 18 + tabID * 18, 18, 18);
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glPopMatrix();
	}

	@Override
	public void onButtonClick(IGuiBase<IModularTileEntity<IModular>> gui) {
		IModularTileEntity<IModular> tile = gui.getTile();
		IModuleManager manager = stack.getModule();
		IModuleManagerSaver managerSaver = (IModuleManagerSaver) stack.getSaver();
		if (!(tabID == managerSaver.getTab())) {
			managerSaver.setTab(tabID);
			PacketHandler.INSTANCE.sendToServer(new PacketSelectTankManagerTab((TileEntity) tile, tabID));
		}
	}

	@Override
	public List<String> getTooltip(IGuiBase<IModularTileEntity<IModular>> gui) {
		return Arrays.asList(Integer.toString(tabID + 1));
	}
}
