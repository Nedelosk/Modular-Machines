package de.nedelosk.modularmachines.client.gui.buttons;

import java.util.Arrays;
import java.util.List;

import de.nedelosk.modularmachines.api.gui.Button;
import de.nedelosk.modularmachines.api.gui.IGuiBase;
import de.nedelosk.modularmachines.api.modular.IModular;
import de.nedelosk.modularmachines.api.modular.handlers.IModularHandler;
import de.nedelosk.modularmachines.api.modules.state.IModuleState;
import de.nedelosk.modularmachines.common.utils.RenderUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
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
		GlStateManager.color(1F, 1F, 1F, 1F);
		RenderUtil.bindTexture(guiTextureOverlay);
		gui.getGui().drawTexturedModalRect(xPosition, yPosition, (state.equals(tile.getModular().getCurrentModuleState())) ? 0 : 28,
				right ? 214 : 235, 28, 21);
		drawItemStack(state.getContainer().getItemStack(), xPosition + (right ? 5 : 7), yPosition + 2);
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
