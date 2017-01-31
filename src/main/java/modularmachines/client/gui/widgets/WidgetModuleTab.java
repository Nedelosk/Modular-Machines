package modularmachines.client.gui.widgets;

import java.util.Arrays;
import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.ResourceLocation;
import modularmachines.api.modules.Module;
import modularmachines.api.modules.logic.IModuleGuiLogic;
import modularmachines.api.modules.logic.IModuleLogic;
import modularmachines.common.utils.ModuleUtil;
import modularmachines.common.utils.RenderUtil;

public class WidgetModuleTab extends Widget<IModuleLogic> {

	protected static final ResourceLocation guiTexture = new ResourceLocation("modularmachines", "textures/gui/modular_widgets.png");
	public final Module module;
	public final boolean onRightSide;
	public final IModuleGuiLogic guiLogic;
	public final int currentIndex;
	public final int moduleIndex;

	public WidgetModuleTab(int xPosition, int yPosition, Module module, boolean onRightSide) {
		super(xPosition, yPosition, 28, 21);
		this.onRightSide = onRightSide;
		this.module = module;
		this.moduleIndex = module.getIndex();
		this.guiLogic = ModuleUtil.getClientGuiLogic();
		this.currentIndex = guiLogic.getCurrentModule().getIndex();
	}

	@Override
	public void draw(int guiLeft, int guiTop) {
		GlStateManager.color(1F, 1F, 1F, 1F);
		RenderUtil.texture(guiTexture);
		gui.drawTexturedModalRect(guiLeft + pos.x, guiTop + pos.y, (moduleIndex == currentIndex) ? 0 : 28, onRightSide ? 214 : 235, 28, 21);
		//TODO: Find an item
		//gui.drawItemStack(source.getSource().getItemStack(), guiLeft + positon.x + (onRightSide ? 5 : 7), guiTop + positon.y + 2);
	}

	@Override
	public void handleMouseClick(int mouseX, int mouseY, int mouseButton) {
		if (moduleIndex == currentIndex) {
			Minecraft.getMinecraft().getSoundHandler().playSound(PositionedSoundRecord.getMasterRecord(SoundEvents.UI_BUTTON_CLICK, 1.0F));
			guiLogic.setCurrentPage(module.getPage(0), true);
		}
	}

	@Override
	public List<String> getTooltip() {
		return Arrays.asList(module.getData().getDisplayName());
	}
}
