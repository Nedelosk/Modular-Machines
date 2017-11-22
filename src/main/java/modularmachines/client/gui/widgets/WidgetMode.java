package modularmachines.client.gui.widgets;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.client.renderer.GlStateManager;

import modularmachines.api.recipes.IMode;
import modularmachines.common.ModularMachines;
import modularmachines.common.modules.IModuleMode;
import modularmachines.common.modules.Module;
import modularmachines.common.network.PacketHandler;
import modularmachines.common.network.packets.PacketSyncMode;
import modularmachines.common.utils.RenderUtil;
import modularmachines.common.utils.Translator;

public class WidgetMode extends Widget {
	
	public final IModuleMode module;
	
	public WidgetMode(int posX, int posY, IModuleMode module) {
		super(posX, posY, 18, 18);
		this.module = module;
	}
	
	private IMode getMode() {
		return module.getCurrentMode();
	}
	
	@Override
	public List<String> getTooltip() {
		ArrayList<String> list = new ArrayList<>();
		list.add(Translator.translateToLocal("mode." + getMode().getName() + ".name"));
		return list;
	}
	
	@Override
	public void draw(int guiLeft, int guiTop) {
		GlStateManager.color(1F, 1F, 1F, 1F);
		GlStateManager.enableAlpha();
		RenderUtil.texture(widgetTexture);
		gui.drawTexturedModalRect(guiLeft + pos.x, guiTop + pos.y, 238, 0, 18, 18);
		gui.drawTexturedModalRect(guiLeft + pos.x, guiTop + pos.y, 238, 18 * getMode().ordinal() + 18, 18, 18);
		GlStateManager.disableAlpha();
	}
	
	@Override
	public void handleMouseClick(int mouseX, int mouseY, int mouseButton) {
		ModularMachines.proxy.playButtonClick();
		if (mouseButton == 0) {
			module.setCurrentMode(getMode().next().ordinal());
		} else {
			module.setCurrentMode(getMode().previous().ordinal());
		}
		PacketHandler.sendToServer(new PacketSyncMode((Module) module, module));
	}
}