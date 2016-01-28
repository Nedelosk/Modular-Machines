package nedelosk.modularmachines.api.client.gui;

import java.util.Arrays;
import java.util.List;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import nedelosk.forestcore.library.gui.Button;
import nedelosk.forestcore.library.gui.IGuiBase;
import nedelosk.forestcore.library.utils.RenderUtil;
import nedelosk.modularmachines.api.modular.basic.IModularInventory;
import nedelosk.modularmachines.api.modular.basic.managers.IModularGuiManager;
import nedelosk.modularmachines.api.modular.tile.IModularTileEntity;
import nedelosk.modularmachines.api.modules.IModuleGui;
import nedelosk.modularmachines.api.packets.PacketHandler;
import nedelosk.modularmachines.api.packets.pages.PacketSelectPage;
import nedelosk.modularmachines.api.utils.ModuleStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;

public class ButtonGuiTab extends Button<IModularTileEntity<IModularInventory>> {

	protected ResourceLocation guiTextureOverlay = RenderUtil.getResourceLocation("modularmachines", "modular_machine", "gui");
	public final ModuleStack stack;
	public final IModuleGui moduleGui;
	public final boolean right;

	public ButtonGuiTab(int p_i1021_1_, int p_i1021_2_, int p_i1021_3_, ModuleStack stack, IModuleGui gui, boolean right) {
		super(p_i1021_1_, p_i1021_2_, p_i1021_3_, 28, 21, null);
		this.stack = stack;
		this.right = right;
		this.moduleGui = gui;
	}

	@Override
	public void drawButton(Minecraft mc, int mx, int my) {
		GL11.glPushMatrix();
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		GL11.glColor4f(1F, 1F, 1F, 1F);
		GuiModular machine = (GuiModular) mc.currentScreen;
		IModularGuiManager guiManager = ((IModularTileEntity<IModularInventory>) machine.getTile()).getModular().getGuiManager();
		IModuleGui currentGui = guiManager.getCurrentGui();
		RenderUtil.bindTexture(guiTextureOverlay);
		RenderUtil.drawTexturedModalRect(xPosition, yPosition, 1,
				currentGui.getCategoryUID().equals(moduleGui.getCategoryUID()) && (moduleGui.getModuleUID().equals(currentGui.getModuleUID())) ? 0 : 28,
				right ? 214 : 235, 28, 21);
		RenderHelper.enableGUIStandardItemLighting();
		GL11.glEnable(GL12.GL_RESCALE_NORMAL);
		ItemStack item = stack.getItemStack();
		drawItemStack(item, xPosition + 6, yPosition + 3);
		RenderHelper.disableStandardItemLighting();
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glPopMatrix();
	}

	@Override
	public void onButtonClick(IGuiBase<IModularTileEntity<IModularInventory>> gui) {
		IModularGuiManager guiManager = gui.getTile().getModular().getGuiManager();
		IModuleGui currentGui = guiManager.getCurrentGui();
		if (!currentGui.getCategoryUID().equals(moduleGui.getCategoryUID()) && (moduleGui.getModuleUID().equals(currentGui.getModuleUID()))) {
			guiManager.setCurrentGui(moduleGui);
			PacketHandler.INSTANCE.sendToServer(new PacketSelectPage(((TileEntity) gui.getTile()), moduleGui));
		}
	}

	@Override
	public List<String> getTooltip(IGuiBase<IModularTileEntity<IModularInventory>> gui) {
		return Arrays
				.asList(StatCollector.translateToLocal("mm.modularmachine.bookmark." + moduleGui.getCategoryUID() + "." + moduleGui.getModuleUID() + ".name"));
	}
}
