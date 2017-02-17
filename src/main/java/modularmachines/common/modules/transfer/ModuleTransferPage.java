package modularmachines.common.modules.transfer;

import java.util.List;

import javax.annotation.Nullable;

import modularmachines.client.gui.widgets.Widget;
import modularmachines.client.gui.widgets.WidgetTransfer;
import modularmachines.client.gui.widgets.WidgetTransferButton;
import modularmachines.client.gui.widgets.WidgetTransferCycle;
import modularmachines.common.modules.pages.ModulePageWidget;
import modularmachines.common.utils.RenderUtil;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public abstract class ModuleTransferPage<M extends ModuleTransfer<H>, H> extends ModulePageWidget<M> {

	protected static final ResourceLocation modularWdgets = new ResourceLocation("modularmachines", "textures/gui/modular_widgets.png");
	
	@SideOnly(Side.CLIENT)
	@Nullable
	public WidgetTransfer selectedWidget = null;
	public boolean doAdd;
	public final int index;
	
	public ModuleTransferPage(M parent, int index) {
		super(parent);
		this.index = index;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void drawBackground(int mouseX, int mouseY){
		super.drawBackground(mouseX, mouseY);
		if(doAdd){
			RenderUtil.texture(modularWdgets);
			gui.drawTexturedModalRect(gui.getGuiLeft() + 176, gui.getGuiTop() + 13, 146, 114, 110, 141);
		}
	}
	
	@Override
	protected ResourceLocation getGuiTexture() {
		return new ResourceLocation("modularmachines:textures/gui/module_transfer.png");
	}
	
	@Override
	public int getPlayerInvPosition() {
		return -1;
	}
	
	public void setAdd(boolean doAdd){
		this.doAdd = doAdd;
		if(doAdd){
			addCycleWidgets();
		}else{
			removeCycleWidgets();
		}
	}
	
	@SideOnly(Side.CLIENT)
	@Override
	public void addWidgets() {
		super.addWidgets();
		addWidget(new WidgetTransferButton(151, 62, true, this));
		addWidget(new WidgetTransferButton(151, 86, false, this));
		addWidget(new WidgetTransferCycle(153, 42, this));
		for(int i = 0;i < 6;i++){
			List<ITransferCycle<H>> cycles = parent.getTransferCycles();
			ITransferCycle<H> cycle = null;
			if(index + i < cycles.size()){
				cycle = cycles.get(index + i);
			}
			if(cycle != null){
				addWidget(new WidgetTransfer(12, 15 + i * 23, cycle));
			}
		}
		if(doAdd){
			addCycleWidgets();
		}
	}
	
	@SideOnly(Side.CLIENT)
	@Override
	public void handleMouseClicked(int mouseX, int mouseY, int mouseButton){
		int posX = mouseX - gui.getGuiLeft();
		int posY = mouseY - gui.getGuiTop();
		Widget widget = widgetManager.getWidgetAtMouse(posX, posY);
		if(widget != null && widget instanceof WidgetTransfer && widget != selectedWidget){
			if(selectedWidget != null){
				selectedWidget.setSelected(false);
			}
			selectedWidget = (WidgetTransfer) widget;
			selectedWidget.setSelected(true);
		}
	}
	
	@SideOnly(Side.CLIENT)
	public abstract void addCycleWidgets();
	
	@SideOnly(Side.CLIENT)
	public abstract void removeCycleWidgets();
	
	@SideOnly(Side.CLIENT)
	public abstract void addCycle();

}
