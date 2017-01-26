package modularmachines.client.gui.widgets;

import java.util.Collections;
import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.init.SoundEvents;

/*public class WidgetTransportHandlerWrapper extends Widget<ITransportHandlerWrapper> {

	public WidgetTransportHandlerWrapper(int posX, int posY, int width, int height, ITransportHandlerWrapper provider) {
		super(posX, posY, width, height, provider);
	}

	@Override
	public List<String> getTooltip(IGuiBase gui) {
		if (source != null) {
			return Collections.singletonList(source.getTabTooltip());
		}
		return Collections.emptyList();
	}

	@Override
	public void draw(IGuiBase gui) {
		if (source != null) {
			GlStateManager.color(1F, 1F, 1F, 1F);
			GlStateManager.enableAlpha();
			Minecraft.getMinecraft().renderEngine.bindTexture(widgetTexture);
			int sx = gui.getGuiLeft();
			int sy = gui.getGuiTop();
			gui.getGui().drawTexturedModalRect(sx + positon.x, sy + positon.y, 220, 0, 18, 18);
			gui.drawItemStack(source.getTabItem(), sx + positon.x + 1, sy + positon.y + 1);
			GlStateManager.disableAlpha();
		}
	}

	@Override
	public void handleMouseClick(int mouseX, int mouseY, int mouseButton, IGuiBase gui) {
		if (source != null) {
			Minecraft.getMinecraft().getSoundHandler().playSound(PositionedSoundRecord.getMasterRecord(SoundEvents.UI_BUTTON_CLICK, 1.0F));
			// PacketHandler.sendToServer(new
			// PacketSyncPermission(state.getModular().getHandler(), provider,
			// state));
		}
	}
}*/
