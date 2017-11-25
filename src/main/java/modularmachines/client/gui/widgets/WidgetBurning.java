package modularmachines.client.gui.widgets;

import java.util.Collections;
import java.util.List;

import modularmachines.common.utils.RenderUtil;

public class WidgetBurning<M> extends Widget {
	
	protected M module;
	
	public WidgetBurning(int posX, int posY, M module) {
		super(posX, posY, 14, 14);
		this.module = module;
	}
	
	@Override
	public List<String> getTooltip() {
		return Collections.emptyList();
	}
	
	@Override
	public void handleMouseClick(int mouseX, int mouseY, int mouseButton) {
	}
	
	@Override
	public void draw(int guiLeft, int guiTop) {
		RenderUtil.texture(widgetTexture);
		gui.drawTexturedModalRect(guiLeft + pos.x, guiTop + pos.y, 0, 176, pos.width, pos.height);
		/*int burnTime = module.getFuel();
		if (burnTime > 0) {
			int burnTimeTotal = module.getFuelTotal();
			int fuel = (burnTime * pos.height) / burnTimeTotal;
			gui.drawTexturedModalRect(guiLeft + pos.x, guiTop + pos.y + 14 - fuel, 14, 176 + 14 - fuel, pos.width, fuel);
		}*/
	}
}