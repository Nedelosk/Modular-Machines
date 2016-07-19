package de.nedelosk.modularmachines.client.gui.buttons;

import java.util.Arrays;
import java.util.List;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import de.nedelosk.modularmachines.api.gui.Button;
import de.nedelosk.modularmachines.api.gui.IGuiBase;
import de.nedelosk.modularmachines.api.modular.IModular;
import de.nedelosk.modularmachines.api.modular.handlers.IModularHandler;
import de.nedelosk.modularmachines.api.modules.state.IModuleState;
import de.nedelosk.modularmachines.common.utils.RenderUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class ButtonModuleTab extends Button<IModularHandler> {

	protected ResourceLocation guiTextureOverlay = RenderUtil.getResourceLocation("modularmachines", "modular_machine", "gui");
	public final IModuleState state;
	public final IModularHandler tile;
	public final boolean right;

	public ButtonModuleTab(int p_i1021_1_, int p_i1021_2_, int p_i1021_3_, IModuleState state, IModularHandler tile, boolean right) {
		super(p_i1021_1_, p_i1021_2_, p_i1021_3_, 28, 21, null);
		this.state = state;
		this.right = right;
		this.tile = tile;
	}

	@Override
	public void drawButton(Minecraft mc, int mx, int my) {
		GlStateManager.pushMatrix();
		GlStateManager.enableBlend();
		GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
		GlStateManager.color(1F, 1F, 1F, 1F);
		RenderUtil.bindTexture(guiTextureOverlay);
		gui.getGui().drawTexturedModalRect(xPosition, yPosition, (state.equals(tile.getModular().getCurrentModuleState())) ? 0 : 28,
				right ? 214 : 235, 28, 21);
		RenderHelper.enableGUIStandardItemLighting();
		GL11.glEnable(GL12.GL_RESCALE_NORMAL);
		ItemStack item = state.getContainer().getItemStack();
		drawItemStack(item, xPosition + 6, yPosition + 3);
		RenderHelper.disableStandardItemLighting();
		GlStateManager.disableBlend();
		GlStateManager.popMatrix();
	}

	@Override
	public void onButtonClick(IGuiBase<IModularHandler> gui) {
		IModular modular = gui.getHandler().getModular();
		IModuleState currentModule = modular.getCurrentModuleState();
		if (currentModule.getIndex() != state.getIndex()) {
			modular.setCurrentModuleState(state);
		}
	}

	@Override
	public List<String> getTooltip(IGuiBase<IModularHandler> gui) {
		return Arrays.asList(state.getContainer().getDisplayName());
	}
}
