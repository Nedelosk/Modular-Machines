package modularmachines.client.gui.widgets;

import javax.annotation.Nullable;
import java.awt.Color;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import modularmachines.common.modules.transfer.ITransferCycle;
import modularmachines.common.utils.RenderUtil;

public class WidgetTransfer extends Widget {

	public static final ResourceLocation TEXTURE = new ResourceLocation("modularmachines:textures/gui/transfer_widgets.png");
	
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
		gui.drawTexturedModalRect(guiLeft + pos.x, guiTop + pos.y, 110, selected ? 22 : 0, pos.width, pos.height);
		if(cycle != null){
			ItemStack startItem = cycle.getStartHandler().getTabItem();
			ItemStack endItem = cycle.getEndHandler().getTabItem();
			gui.drawItemStack(startItem, guiLeft + pos.x + 3, guiTop + pos.y + 3);
			FontRenderer fontRenderer = gui.getFontRenderer();
			String time = /*Translator.translateToLocal("module.transfer.item.page.time") + */"T: " + cycle.getTime();
			fontRenderer.drawString(time, guiLeft + pos.x + 20, guiTop + pos.y + 3, Color.WHITE.getRGB());
			String amount = /*Translator.translateToLocal("module.transfer.item.page.amount")+ */"A: " + cycle.getAmount();
			fontRenderer.drawString(amount, guiLeft + pos.x + 55/*65*/, guiTop + pos.y + 3, Color.WHITE.getRGB());
			String priority =/* Translator.translateToLocal("module.transfer.item.page.priority") + */"P: " + cycle.getPriority();
			fontRenderer.drawString(priority, guiLeft + pos.x + 90/*40*/, guiTop + pos.y + 3/*12*/, Color.WHITE.getRGB());
			gui.drawItemStack(endItem, guiLeft + pos.x + pos.width - 3 - 18, guiTop + pos.y + 3);
			
		}
	}
	
	public void setSelected(boolean selected) {
		this.selected = selected;
	}

}
