package modularmachines.client.gui.widgets;

import java.util.List;

import net.minecraft.client.renderer.GlStateManager;

import modularmachines.common.core.ModularMachines;
import modularmachines.common.modules.transfer.ITransferCycle;
import modularmachines.common.modules.transfer.ModuleTransfer;
import modularmachines.common.modules.transfer.PageTransfer;
import modularmachines.common.network.PacketHandler;
import modularmachines.common.network.packets.PacketRemoveCycle;
import modularmachines.common.utils.RenderUtil;

public class WidgetTransferButton<H> extends Widget {

	public boolean addButton;
	public PageTransfer<ModuleTransfer<H>, H> transferPage;
	
	public WidgetTransferButton(int posX, int posY, boolean addButton, PageTransfer<ModuleTransfer<H>, H> transferPage) {
		super(posX, posY, 18, 18);
		this.addButton = addButton;
		this.transferPage = transferPage;
	}
	
	@Override
	public void draw(int guiLeft, int guiTop) {
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
		RenderUtil.texture(widgetTexture);
		if(addButton){
			gui.drawTexturedModalRect(guiLeft + pos.x, guiTop + pos.y, 166, 18, 18, 18);
		}else{
			gui.drawTexturedModalRect(guiLeft + pos.x, guiTop + pos.y, 184, 18, 18, 18);
		}
	}
	
	@Override
	public void handleMouseClick(int mouseX, int mouseY, int mouseButton) {
		ModularMachines.proxy.playButtonClick();
		if(addButton){
			transferPage.addCycle();
		}else{
			WidgetTransfer widget = transferPage.selectedWidget;
			if(widget != null){
				ModuleTransfer module = transferPage.getModule();
				List<ITransferCycle> cycles = module.getTransferCycles();
				PacketHandler.sendToServer(new PacketRemoveCycle(transferPage.getModule(), cycles.indexOf(widget.cycle)));
			}
		}
		
	}

}
