package modularmachines.client.gui.widgets;

import java.util.ArrayList;
import java.util.List;

import modularmachines.api.gui.IGuiBase;
import modularmachines.api.gui.Widget;
import modularmachines.api.modules.state.IModuleState;
import modularmachines.api.modules.tools.IModuleModeMachine;
import modularmachines.api.recipes.IToolMode;
import modularmachines.common.network.PacketHandler;
import modularmachines.common.network.packets.PacketSyncToolMode;
import modularmachines.common.utils.RenderUtil;
import modularmachines.common.utils.Translator;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.init.SoundEvents;

public class WidgetMode extends Widget<IModuleState<IModuleModeMachine>> {

	public WidgetMode(int posX, int posY, IModuleState<IModuleModeMachine> provider) {
		super(posX, posY, 18, 18, provider);
	}

	private IToolMode getMode() {
		return provider.getModule().getCurrentMode(provider);
	}

	@Override
	public List<String> getTooltip(IGuiBase gui) {
		ArrayList<String> list = new ArrayList<>();
		list.add(Translator.translateToLocal("mode." + getMode().getName() + ".name"));
		return list;
	}

	@Override
	public void draw(IGuiBase gui) {
		GlStateManager.color(1F, 1F, 1F, 1F);
		GlStateManager.enableAlpha();
		RenderUtil.bindTexture(widgetTexture);
		int sx = gui.getGuiLeft();
		int sy = gui.getGuiTop();
		gui.getGui().drawTexturedModalRect(sx + pos.x, sy + pos.y, 238, 0, 18, 18);
		gui.getGui().drawTexturedModalRect(sx + pos.x, sy + pos.y, 238, 18 * getMode().ordinal() + 18, 18, 18);
		GlStateManager.disableAlpha();
	}

	@Override
	public void handleMouseClick(int mouseX, int mouseY, int mouseButton, IGuiBase gui) {
		Minecraft.getMinecraft().getSoundHandler().playSound(PositionedSoundRecord.getMasterRecord(SoundEvents.UI_BUTTON_CLICK, 1.0F));
		IModuleModeMachine module = provider.getModule();
		module.setCurrentMode(provider, module.getNextMode(provider));
		PacketHandler.sendToServer(new PacketSyncToolMode(provider.getModular().getHandler(), provider));
	}
}