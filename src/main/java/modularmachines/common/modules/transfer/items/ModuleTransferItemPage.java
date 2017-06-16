package modularmachines.common.modules.transfer.items;

import com.google.common.base.Predicate;
import com.google.common.primitives.Ints;

import java.awt.Color;
import java.util.List;

import net.minecraft.client.gui.FontRenderer;

import net.minecraftforge.items.IItemHandler;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import modularmachines.client.gui.widgets.WidgetTextField;
import modularmachines.client.gui.widgets.WidgetTransferHandler;
import modularmachines.common.modules.transfer.ITransferHandlerWrapper;
import modularmachines.common.modules.transfer.ModuleTransferPage;
import modularmachines.common.network.PacketHandler;
import modularmachines.common.network.packets.PacketAddCycle;
import modularmachines.common.utils.Translator;

public class ModuleTransferItemPage extends ModuleTransferPage<ModuleTransferItem, IItemHandler> {

	@SideOnly(Side.CLIENT)
	private WidgetTextField slots;
	@SideOnly(Side.CLIENT)
	private WidgetTextField insertSlots;
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
	
	public ModuleTransferItemPage(ModuleTransferItem parent, int index) {
		super(parent, index);
	}
	
	@SideOnly(Side.CLIENT)
	@Override
	public void initGui() {
		if(!parent.wasInited){
			for(ITransferHandlerWrapper wrapper : parent.getWrappers()){
				wrapper.init(parent.getLogic());
			}
			parent.wasInited = true;
		}
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void addCycleWidgets() {
		List<ITransferHandlerWrapper<IItemHandler>> wrappers = parent.getValidWrappers();
		if(!wrappers.isEmpty()){
			int xSize = gui.xSize;
			int cycleSize = 110;
			addWidget(startHandler = new WidgetTransferHandler(xSize + 4, 27, wrappers));
			addWidget(slots = new WidgetTextField(xSize + 28, 29, 75, 15).setTootltip("module.transfer.item.page.from.info"));
			//slots.setValidator(new NumberValidator(((IItemHandler)startHandler.getCurrentWrapper().getHandler()).getSlots()));
			addWidget(endHandler = new WidgetTransferHandler(176 + 4, 57, wrappers));
			addWidget(insertSlots = new WidgetTextField(xSize + 28, 59, 75, 15).setTootltip("module.transfer.item.page.to.info"));
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
	
	@SideOnly(Side.CLIENT)
	@Override
	public void drawForeground(FontRenderer fontRenderer, int mouseX, int mouseY) {
		super.drawForeground(fontRenderer, mouseX, mouseY);
		List<ITransferHandlerWrapper<IItemHandler>> wrappers = parent.getValidWrappers();
		if(doAdd && !wrappers.isEmpty()){
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
	
	@SideOnly(Side.CLIENT)
	@Override
	public void removeCycleWidgets() {
		List<ITransferHandlerWrapper<IItemHandler>> wrappers = parent.getValidWrappers();
		if(!wrappers.isEmpty()){
			widgetManager.remove(startHandler);
			widgetManager.remove(slots);
			widgetManager.remove(endHandler);
			widgetManager.remove(insertSlots);
			widgetManager.remove(time);
			widgetManager.remove(amount);
			widgetManager.remove(priority);
		}
	}
	
	public int[] parseIntArray(String text){
		if(text == null || text.isEmpty()){
			return new int[0];
		}
		String[] numberStrs = text.split(",");
		int[] numbers = new int[numberStrs.length];
		for(int i = 0;i < numberStrs.length;i++){
		   numbers[i] = Integer.parseInt(numberStrs[i]);
		}
		return numbers;
	}
	
	public int parseInt(String text){
		if(text == null){
			return -1;
		}
		return Ints.tryParse(text);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void addCycle(){
		if(doAdd){
			int time = parseInt(this.time.getText());
			int priority = parseInt(this.priority.getText());
			int amount = parseInt(this.amount.getText());
			int[] slots = parseIntArray(this.slots.getText());
			int[] insertSlots = parseIntArray(this.insertSlots.getText());
			if(time < 0 || amount < 0){
				return ;
			}
			
			PacketHandler.sendToServer(new PacketAddCycle(parent, new ItemTransferCycle(parent, startHandler.getCurrentWrapper(), slots, endHandler.getCurrentWrapper(), insertSlots, time, priority, amount)));
		}
	}

}
