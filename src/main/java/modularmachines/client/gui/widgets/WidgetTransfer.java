package modularmachines.client.gui.widgets;

import javax.annotation.Nullable;
import java.awt.Color;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import modularmachines.common.modules.transfer.ITransferCycle;
import modularmachines.common.modules.transfer.items.ItemTransferCycle;
import modularmachines.common.utils.RenderUtil;
import modularmachines.common.utils.Translator;

public class WidgetTransfer extends Widget {

	public static final ResourceLocation TEXTURE = new ResourceLocation("modularmachines:textures/gui/module_transfer.png");
	
	public boolean selected;
	@Nullable
	public final ITransferCycle cycle;
	
	public WidgetTransfer(int posX, int posY, ITransferCycle cycle) {
		super(posX, posY, 134, 22);
		this.cycle = cycle;
	}
	
	@Override
	public void draw(int guiLeft, int guiTop) {
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
		RenderUtil.texture(TEXTURE);
		gui.drawTexturedModalRect(guiLeft + pos.x, guiTop + pos.y, 0, selected ? 188 : 166, pos.width, pos.height);
		if(cycle != null){
			ItemStack startItem = cycle.getStartHandler().getTabItem();
			ItemStack endItem = cycle.getEndHandler().getTabItem();
			gui.drawItemStack(startItem, guiLeft + pos.x + 3, guiTop + pos.y + 3);
			FontRenderer fontRenderer = gui.getFontRenderer();
			String time = Translator.translateToLocal("module.transfer.item.page.time") + ": " + cycle.getTime();
			fontRenderer.drawString(time, guiLeft + pos.x + 20, guiTop + pos.y + 3, Color.WHITE.getRGB());
			String priority = Translator.translateToLocal("module.transfer.item.page.priority") + ": " + cycle.getPriority();
			fontRenderer.drawString(priority, guiLeft + pos.x + 40, guiTop + pos.y + 12, Color.WHITE.getRGB());
			if(cycle instanceof ItemTransferCycle){
				String amount = Translator.translateToLocal("module.transfer.item.page.amount") + ": " + ((ItemTransferCycle)cycle).getAmount();
				fontRenderer.drawString(amount, guiLeft + pos.x + 65, guiTop + pos.y + 3, Color.WHITE.getRGB());
			}
			gui.drawItemStack(endItem, guiLeft + pos.x + pos.width - 3 - 18, guiTop + pos.y + 3);
			
		}
	}
	
	public void setSelected(boolean selected) {
		this.selected = selected;
	}

}
