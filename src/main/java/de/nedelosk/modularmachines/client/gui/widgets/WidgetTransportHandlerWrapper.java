package de.nedelosk.modularmachines.client.gui.widgets;

import java.util.Collections;
import java.util.List;

import de.nedelosk.modularmachines.api.gui.IGuiBase;
import de.nedelosk.modularmachines.api.gui.Widget;
import de.nedelosk.modularmachines.api.modules.transport.ITransportHandlerWrapper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.init.SoundEvents;

public class WidgetTransportHandlerWrapper extends Widget<ITransportHandlerWrapper> {

	public WidgetTransportHandlerWrapper(int posX, int posY, int width, int height, ITransportHandlerWrapper provider) {
		super(posX, posY, width, height, provider);
	}

	@Override
	public List<String> getTooltip(IGuiBase gui) {
		if (provider != null) {
			return Collections.singletonList(provider.getTabTooltip());
		}
		return Collections.emptyList();
	}

	@Override
	public void draw(IGuiBase gui) {
		if (provider != null) {
			GlStateManager.color(1F, 1F, 1F, 1F);
			GlStateManager.enableAlpha();
			Minecraft.getMinecraft().renderEngine.bindTexture(widgetTexture);
			int sx = gui.getGuiLeft();
			int sy = gui.getGuiTop();
			gui.getGui().drawTexturedModalRect(sx + pos.x, sy + pos.y, 220, 0, 18, 18);
			gui.drawItemStack(provider.getTabItem(), sx + pos.x + 1, sy + pos.y + 1);
			GlStateManager.disableAlpha();
		}
	}

	@Override
	public void handleMouseClick(int mouseX, int mouseY, int mouseButton, IGuiBase gui) {
		if (provider != null) {
			Minecraft.getMinecraft().getSoundHandler().playSound(PositionedSoundRecord.getMasterRecord(SoundEvents.UI_BUTTON_CLICK, 1.0F));
			// PacketHandler.sendToServer(new
			// PacketSyncPermission(state.getModular().getHandler(), provider,
			// state));
		}
	}
}
