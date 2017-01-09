package modularmachines.client.gui.widgets;

import java.util.Arrays;
import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.ResourceLocation;

import modularmachines.api.gui.IGuiBase;
import modularmachines.api.gui.Widget;
import modularmachines.api.modular.IModular;
import modularmachines.api.modular.handlers.IModularHandler;
import modularmachines.api.modules.state.IModuleState;
import modularmachines.common.network.PacketHandler;
import modularmachines.common.network.packets.PacketSelectModule;
import modularmachines.common.network.packets.PacketSelectModulePage;
import modularmachines.common.utils.RenderUtil;

public class WidgetModuleTab extends Widget<IModuleState> {

	protected static final ResourceLocation guiTexture = new ResourceLocation("modularmachines", "textures/gui/modular_widgets.png");
	public final IModularHandler moduleHandler;
	public final boolean right;

	public WidgetModuleTab(int xPosition, int yPosition, IModuleState provider, List<IModuleState> modulesWithPages) {
		super(xPosition, yPosition, 28, 21, provider);
		this.right = modulesWithPages.indexOf(provider) >= 7;
		this.moduleHandler = provider.getModular().getHandler();
	}

	@Override
	public void draw(IGuiBase gui) {
		if (gui != null && provider != null && moduleHandler != null && moduleHandler.getModular() != null && moduleHandler.getModular().getCurrentModule() != null) {
			GlStateManager.color(1F, 1F, 1F, 1F);
			RenderUtil.bindTexture(guiTexture);
			gui.getGui().drawTexturedModalRect(gui.getGuiLeft() + pos.x, gui.getGuiTop() + pos.y, (provider.getPosition() == moduleHandler.getModular().getCurrentModule().getPosition()) ? 0 : 28, right ? 214 : 235, 28, 21);
			gui.drawItemStack(provider.getProvider().getItemStack(), gui.getGuiLeft() + pos.x + (right ? 5 : 7), gui.getGuiTop() + pos.y + 2);
		}
	}

	@Override
	public void handleMouseClick(int mouseX, int mouseY, int mouseButton, IGuiBase gui) {
		IModular modular = moduleHandler.getModular();
		IModuleState currentModule = modular.getCurrentModule();
		if (currentModule.getPosition() != provider.getPosition()) {
			Minecraft.getMinecraft().getSoundHandler().playSound(PositionedSoundRecord.getMasterRecord(SoundEvents.UI_BUTTON_CLICK, 1.0F));
			modular.setCurrentModule(provider);
			PacketHandler.sendToServer(new PacketSelectModule(provider));
			PacketHandler.sendToServer(new PacketSelectModulePage(moduleHandler, moduleHandler.getModular().getCurrentPage().getPageID()));
		}
	}

	@Override
	public void setProvider(IModuleState provider) {
	}

	@Override
	public List<String> getTooltip(IGuiBase gui) {
		return Arrays.asList(provider.getDisplayName());
	}
}
