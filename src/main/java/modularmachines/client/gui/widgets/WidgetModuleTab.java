package modularmachines.client.gui.widgets;

import java.util.Arrays;
import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.ResourceLocation;
import modularmachines.api.modules.IModuleGuiLogic;
import modularmachines.api.modules.IModuleLogic;
import modularmachines.api.modules.Module;
import modularmachines.api.modules.pages.ModulePage;
import modularmachines.api.modules.storages.IStoragePosition;
import modularmachines.client.gui.GuiBase;
import modularmachines.common.network.PacketHandler;
import modularmachines.common.network.packets.PacketSelectModule;
import modularmachines.common.network.packets.PacketSelectModulePage;
import modularmachines.common.utils.ModuleUtil;
import modularmachines.common.utils.RenderUtil;

public class WidgetModuleTab extends Widget<IModuleLogic> {

	protected static final ResourceLocation guiTexture = new ResourceLocation("modularmachines", "textures/gui/modular_widgets.png");
	public final Module module;
	public final boolean onRightSide;
	public final IModuleGuiLogic guiLogic;

	public WidgetModuleTab(int xPosition, int yPosition, Module module, boolean onRightSide) {
		super(xPosition, yPosition, 28, 21);
		this.onRightSide = onRightSide;
		this.module = module;
		this.guiLogic = ModuleUtil.getClientGuiLogic();
	}

	@Override
	public void draw() {
		GuiBase gui = manager.getGui();
		int guiLeft = gui.getGuiLeft();
		int guiTop = gui.getGuiTop();
		int moduleIndex = module.getIndex();
		int currentIndex = guiLogic.getCurrentModule().getIndex();
		GlStateManager.color(1F, 1F, 1F, 1F);
		RenderUtil.bindTexture(guiTexture);
		gui.drawTexturedModalRect(guiLeft + positon.x, guiTop + positon.y, (moduleIndex == currentIndex) ? 0 : 28, onRightSide ? 214 : 235, 28, 21);
		//TODO: Find an item
		//gui.drawItemStack(source.getSource().getItemStack(), guiLeft + positon.x + (onRightSide ? 5 : 7), guiTop + positon.y + 2);
	}

	@Override
	public void handleMouseClick(int mouseX, int mouseY, int mouseButton) {
		IModular modular = moduleHandler.getModular();
		IModuleState currentModule = modular.getCurrentModule();
		if (currentModule.getPosition() != source.getPosition()) {
			Minecraft.getMinecraft().getSoundHandler().playSound(PositionedSoundRecord.getMasterRecord(SoundEvents.UI_BUTTON_CLICK, 1.0F));
			modular.setCurrentModule(source);
			PacketHandler.sendToServer(new PacketSelectModule(source));
			PacketHandler.sendToServer(new PacketSelectModulePage(moduleHandler, moduleHandler.getModular().getCurrentPage().getPageID()));
		}
	}

	@Override
	public List<String> getTooltip() {
		return Arrays.asList(source.getDisplayName());
	}
}
