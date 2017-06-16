package modularmachines.client.gui.widgets;

import net.minecraft.client.renderer.GlStateManager;

import modularmachines.common.core.ModularMachines;
import modularmachines.common.modules.transfer.ModuleTransferPage;
import modularmachines.common.utils.RenderUtil;

public class WidgetTransferCycle extends Widget{

	public ModuleTransferPage transferPage;
	
	public WidgetTransferCycle(int posX, int posY, ModuleTransferPage transferPage) {
		super(posX, posY, 12, 16);
		this.transferPage = transferPage;
	}
	
	@Override
	public void draw(int guiLeft, int guiTop) {
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
		RenderUtil.texture(widgetTexture);
		if(transferPage.doAdd){
			gui.drawTexturedModalRect(guiLeft + pos.x, guiTop + pos.y, 220, 18, pos.width, pos.height);
		}else{
			gui.drawTexturedModalRect(guiLeft + pos.x, guiTop + pos.y, 202, 18, pos.width, pos.height);
		}
	}

	@Override
	public void handleMouseClick(int mouseX, int mouseY, int mouseButton) {
		ModularMachines.proxy.playButtonClick();
		if(transferPage.doAdd){
			transferPage.setAdd(false);
		}else{
			transferPage.setAdd(true);
		}
	}
	
}
