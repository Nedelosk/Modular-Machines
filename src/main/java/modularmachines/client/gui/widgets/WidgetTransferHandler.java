package modularmachines.client.gui.widgets;

import java.util.Collections;
import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.item.ItemStack;

import modularmachines.common.core.ModularMachines;
import modularmachines.common.modules.transfer.ITransferHandlerWrapper;

public class WidgetTransferHandler<H> extends Widget {

	private final List<ITransferHandlerWrapper<H>> wrappers;
	private ITransferHandlerWrapper currentWrapper;
	private ItemStack itemStack;
	
	public WidgetTransferHandler(int posX, int posY, List<ITransferHandlerWrapper<H>> wrappers) {
		super(posX, posY, 18, 18);
		this.currentWrapper = wrappers.get(0);
		this.wrappers = wrappers;
		setItemStack();
	}
	
	private void setItemStack(){
		itemStack = currentWrapper.getTabItem();
	}

	@Override
	public List<String> getTooltip() {
		if (currentWrapper != null) {
			return Collections.singletonList(currentWrapper.getTabTooltip());
		}
		return Collections.emptyList();
	}

	@Override
	public void draw(int guiLeft, int guiTop) {
		GlStateManager.color(1F, 1F, 1F, 1F);
		GlStateManager.enableAlpha();
		Minecraft.getMinecraft().renderEngine.bindTexture(widgetTexture);
		gui.drawTexturedModalRect(guiLeft + pos.x, guiTop + pos.y, 220, 0, pos.width, pos.height);
		if(!itemStack.isEmpty()){
			gui.drawItemStack(itemStack, guiLeft + pos.x + 1, guiTop + pos.y + 1);
		}
		GlStateManager.disableAlpha();
	}

	@Override
	public void handleMouseClick(int mouseX, int mouseY, int mouseButton) {
		if (currentWrapper != null) {
			ModularMachines.proxy.playButtonClick();
			int index = wrappers.indexOf(currentWrapper);
			if(wrappers.size() > 1){
				if(mouseButton == 0){
					if (index == wrappers.size() - 1) {
						index = 0;
					} else {
						index++;
					}
				}else{
					index--;
					if (index < 0) {
						index = wrappers.size() - 1;
					}
				}
			}
			currentWrapper = wrappers.get(index);
			setItemStack();
		}
	}
	
	public ITransferHandlerWrapper getCurrentWrapper() {
		return currentWrapper;
	}
}
