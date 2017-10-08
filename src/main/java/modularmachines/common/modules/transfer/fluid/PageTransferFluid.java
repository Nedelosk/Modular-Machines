package modularmachines.common.modules.transfer.fluid;

import com.google.common.base.Predicate;
import com.google.common.primitives.Ints;

import java.awt.Color;
import java.util.List;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.inventory.GuiContainer;

import net.minecraftforge.fluids.capability.IFluidHandler;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import modularmachines.api.modules.pages.IModuleComponent;
import modularmachines.client.gui.widgets.WidgetTextField;
import modularmachines.client.gui.widgets.WidgetTransferHandler;
import modularmachines.common.modules.transfer.ITransferHandlerWrapper;
import modularmachines.common.modules.transfer.PageTransfer;
import modularmachines.common.network.PacketHandler;
import modularmachines.common.network.packets.PacketAddCycle;
import modularmachines.common.utils.RenderUtil;
import modularmachines.common.utils.Translator;

public class PageTransferFluid extends PageTransfer<ModuleTransferFluid, IFluidHandler> {

	@SideOnly(Side.CLIENT)
	private WidgetTextField amount;
	@SideOnly(Side.CLIENT)
	private WidgetTextField time;
	@SideOnly(Side.CLIENT)
	private WidgetTextField priority;
	@SideOnly(Side.CLIENT)
	private WidgetTransferHandler startHandler;
	@SideOnly(Side.CLIENT)
	private WidgetTransferHandler endHandler;
	
	
	public static class NumberValidator implements Predicate<String> {

		private final int slots;
		
		private NumberValidator(int slots) {
			this.slots = slots;
		}

		@Override
		public boolean apply(String text) {
			Integer i = Ints.tryParse(text);
			if(i == null || i > slots){
				return false;
			}
			return text.isEmpty() || i != null && i.intValue() >= 0;
		}
	}
	
	public PageTransferFluid(IModuleComponent<ModuleTransferFluid> module, GuiContainer gui) {
		super(module, gui);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void drawBackground(int mouseX, int mouseY){
		super.drawBackground(mouseX, mouseY);
		RenderUtil.texture(getGuiTexture());
		gui.drawTexturedModalRect(gui.getGuiLeft() + 286, gui.getGuiTop() + 30, 176, 0, 29, 104);
	}
	
	@SideOnly(Side.CLIENT)
	@Override
	public void initGui() {
		if(!module.wasInited){
			for(ITransferHandlerWrapper wrapper : module.getWrappers()){
				wrapper.init(module.getLogic());
			}
			module.wasInited = true;
		}
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void addWidgets() {
		super.addWidgets();
		List<ITransferHandlerWrapper<IFluidHandler>> wrappers = module.getValidWrappers();
		if(!wrappers.isEmpty()){
			int xSize = gui.xSize;
			int cycleSize = 110;
			addWidget(startHandler = new WidgetTransferHandler(xSize + 4, 27, wrappers));
			//slots.setValidator(new NumberValidator(((IItemHandler)startHandler.getCurrentWrapper().getHandler()).getCollection()));
			addWidget(endHandler = new WidgetTransferHandler(176 + 4, 57, wrappers));
			addWidget(amount = new WidgetTextField(xSize + cycleSize / 4 - 25 / 2, 89, 25, 15).setTootltip("module.transfer.item.page.amount.info"));
			amount.setMaxStringLength(2);
			amount.setValidator(new NumberValidator(64));
			addWidget(priority = new WidgetTextField(xSize + cycleSize / 2 + 25 / 2, 89, 25, 15).setTootltip("module.transfer.item.page.priority.info"));
			priority.setMaxStringLength(3);
			priority.setCharFilter(WidgetTextField.FILTER_NUMERIC);
			addWidget(time = new WidgetTextField(xSize + cycleSize / 2 - 35 / 2, 119, 35, 15).setTootltip("module.transfer.item.page.time.info"));
			time.setMaxStringLength(3);
			time.setCharFilter(WidgetTextField.FILTER_NUMERIC);
		}
	}
	/*@SideOnly(Side.CLIENT)
	@Override
	public void addCycleWidgets() {
		List<ITransferHandlerWrapper<IFluidHandler>> wrappers = module.getValidWrappers();
		if(!wrappers.isEmpty()){
			int xSize = gui.xSize;
			int cycleSize = 110;
			addWidget(startHandler = new WidgetTransferHandler(xSize + 4, 27, wrappers));
			//slots.setValidator(new NumberValidator(((IItemHandler)startHandler.getCurrentWrapper().getHandler()).getCollection()));
			addWidget(endHandler = new WidgetTransferHandler(176 + 4, 57, wrappers));
			addWidget(amount = new WidgetTextField(xSize + cycleSize / 4 - 25 / 2, 89, 25, 15).setTootltip("module.transfer.item.page.amount.info"));
			amount.setMaxStringLength(2);
			amount.setValidator(new NumberValidator(64));
			addWidget(priority = new WidgetTextField(xSize + cycleSize / 2 + 25 / 2, 89, 25, 15).setTootltip("module.transfer.item.page.priority.info"));
			priority.setMaxStringLength(3);
			priority.setCharFilter(WidgetTextField.FILTER_NUMERIC);
			addWidget(time = new WidgetTextField(xSize + cycleSize / 2 - 35 / 2, 119, 35, 15).setTootltip("module.transfer.item.page.time.info"));
			time.setMaxStringLength(3);
			time.setCharFilter(WidgetTextField.FILTER_NUMERIC);
		}
	}*/
	
	@SideOnly(Side.CLIENT)
	@Override
	public void drawForeground(FontRenderer fontRenderer, int mouseX, int mouseY) {
		super.drawForeground(fontRenderer, mouseX, mouseY);
		List<ITransferHandlerWrapper<IFluidHandler>> wrappers = module.getValidWrappers();
		if(!wrappers.isEmpty()){
			String from = Translator.translateToLocal("module.transfer.item.page.from");
			fontRenderer.drawString(from, gui.xSize + 110 / 2 - fontRenderer.getStringWidth(from) / 2, 18, Color.GRAY.getRGB());
			String to = Translator.translateToLocal("module.transfer.item.page.to");
			fontRenderer.drawString(to, gui.xSize + 110 / 2 - fontRenderer.getStringWidth(to) / 2, 48, Color.GRAY.getRGB());
			String amount = Translator.translateToLocal("module.transfer.item.page.amount");
			fontRenderer.drawString(amount, gui.xSize + 110 / 4 - fontRenderer.getStringWidth(amount) / 2, 78, Color.GRAY.getRGB());
			String priority = Translator.translateToLocal("module.transfer.item.page.priority");
			fontRenderer.drawString(priority, gui.xSize + 110 / 2 + 25  - fontRenderer.getStringWidth(priority) / 2, 78, Color.GRAY.getRGB());
			String time = Translator.translateToLocal("module.transfer.item.page.time");
			fontRenderer.drawString(time, gui.xSize + 110 / 2 - fontRenderer.getStringWidth(time) / 2, 108, Color.GRAY.getRGB());
			//fontRenderer.drawStringWithShadow(Translator.translateToLocal("module.transfer.item.page.from"), gui.guiLeft + 176 , y, Color.GRAY)
		}
	}
	
	/*@SideOnly(Side.CLIENT)
	@Override
	public void removeCycleWidgets() {
		List<ITransferHandlerWrapper<IFluidHandler>> wrappers = module.getValidWrappers();
		if(!wrappers.isEmpty()){
			widgetManager.remove(startHandler);
			widgetManager.remove(endHandler);
			widgetManager.remove(time);
			widgetManager.remove(amount);
			widgetManager.remove(priority);
		}
	}*/
	
	public int parseInt(String text){
		if(text == null){
			return -1;
		}
		return Ints.tryParse(text);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void addCycle(){
			int time = parseInt(this.time.getText());
			int priority = parseInt(this.priority.getText());
			int amount = parseInt(this.amount.getText());
			if(time < 0 || amount < 0){
				return ;
			}
			
			PacketHandler.sendToServer(new PacketAddCycle(module, new FluidTransferCycle(module, startHandler.getCurrentWrapper(), endHandler.getCurrentWrapper(), time, priority, amount)));
	}

}
