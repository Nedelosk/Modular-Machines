package nedelosk.modularmachines.api.client.gui;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import nedelosk.forestcore.library.gui.Button;
import nedelosk.forestcore.library.gui.IGuiBase;
import nedelosk.forestcore.library.utils.RenderUtil;
import nedelosk.modularmachines.api.modular.IModular;
import nedelosk.modularmachines.api.modular.basic.managers.IModularGuiManager;
import nedelosk.modularmachines.api.modular.tile.IModularTileEntity;
import nedelosk.modularmachines.api.packets.PacketHandler;
import nedelosk.modularmachines.api.packets.pages.PacketSelectPage;
import nedelosk.modularmachines.api.utils.ModuleStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;

public class ButtonTabPage extends Button<IModularTileEntity<IModular>> {

	protected ResourceLocation guiTextureOverlay = RenderUtil.getResourceLocation("modularmachines", "modular_machine", "gui");

	public ModuleStack stack;
	public boolean right;

	public ButtonTabPage(int p_i1021_1_, int p_i1021_2_, int p_i1021_3_, ModuleStack stack, boolean right) {
		super(p_i1021_1_, p_i1021_2_, p_i1021_3_, 28, 21, null);
		this.stack = stack;
		this.right = right;
	}

	@Override
	public void drawButton(Minecraft mc, int mx, int my) {
		GL11.glPushMatrix();
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		GL11.glColor4f(1F, 1F, 1F, 1F);

		GuiModularMachine machine = (GuiModularMachine) mc.currentScreen;
		RenderUtil.bindTexture(guiTextureOverlay);
		RenderUtil.drawTexturedModalRect(xPosition, yPosition, 1, ((IModularTileEntity)machine.getTile()).getModular().getGuiManager().getPage().equals(stack.getModule().getName(stack, false)) ? 0 : 28, right ? 214 : 235, 28, 21);
		RenderHelper.enableGUIStandardItemLighting();
		GL11.glEnable(GL12.GL_RESCALE_NORMAL);
		ItemStack item = stack.getItem();
		drawItemStack(item, xPosition + 6, yPosition + 3);
		RenderHelper.disableStandardItemLighting();
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glPopMatrix();

	}
	
	@Override
	public void onButtonClick(IGuiBase<IModularTileEntity<IModular>> gui) {
		IModularGuiManager guiManager = gui.getTile().getModular().getGuiManager();

		IModularTileEntity<IModular> tile = gui.getTile();
		
		if (!guiManager.getPage().equals(stack.getModule().getName(stack, false))) {
			guiManager.setPage(stack.getModule().getName(stack, false));
			PacketHandler.INSTANCE.sendToServer(new PacketSelectPage((TileEntity) tile, stack.getModule().getName(stack, false)));
		}
	}
	
	@Override
	public List<String> getTooltip(IGuiBase<IModularTileEntity<IModular>> gui) {
		return Arrays.asList(StatCollector.translateToLocal("mm.modularmachine.bookmark." + stack.getModule().getModuleName().toLowerCase(Locale.ENGLISH) + ".name"));
	}

}
