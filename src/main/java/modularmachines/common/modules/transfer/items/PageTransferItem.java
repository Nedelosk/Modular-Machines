package modularmachines.common.modules.transfer.items;

import com.google.common.base.Predicate;
import com.google.common.primitives.Ints;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.MathHelper;

import net.minecraftforge.items.IItemHandler;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import modularmachines.api.modules.pages.IModuleComponent;
import modularmachines.client.gui.widgets.WidgetButtonLabeled;
import modularmachines.client.gui.widgets.WidgetFilterSlot;
import modularmachines.client.gui.widgets.WidgetTextField;
import modularmachines.client.gui.widgets.WidgetTransferHandler;
import modularmachines.common.modules.transfer.ITransferHandlerWrapper;
import modularmachines.common.modules.transfer.PageTransfer;
import modularmachines.common.network.PacketHandler;
import modularmachines.common.network.packets.PacketAddCycle;
import modularmachines.common.utils.Translator;

public class PageTransferItem extends PageTransfer<ModuleTransferItem, IItemHandler> {
	
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
	@SideOnly(Side.CLIENT)
	private WidgetButtonLabeled metaButton;
	@SideOnly(Side.CLIENT)
	private WidgetButtonLabeled oredictButton;
	@SideOnly(Side.CLIENT)
	private WidgetButtonLabeled blacklistButton;
	@SideOnly(Side.CLIENT)
	private WidgetButtonLabeled nbtButton;
	@SideOnly(Side.CLIENT)
	private List<WidgetFilterSlot> filterSlots = new ArrayList<>();
	
	public static class NumberValidator implements Predicate<String> {
		
		private final int slots;
		
		private NumberValidator(int slots) {
			this.slots = slots;
		}
		
		@Override
		public boolean apply(String text) {
			Integer i = Ints.tryParse(text);
			if (i == null || i > slots) {
				return false;
			}
			return text.isEmpty() || i != null && i.intValue() >= 0;
		}
	}
	
	public PageTransferItem(IModuleComponent<ModuleTransferItem> module, GuiContainer gui) {
		super(module, gui);
	}
	
	@SideOnly(Side.CLIENT)
	@Override
	public void initGui() {
		if (!module.wasInited) {
			for (ITransferHandlerWrapper wrapper : module.getWrappers()) {
				wrapper.init(module.getContainer());
			}
			module.wasInited = true;
		}
	}
	
	@Override
	public void addWidgets() {
		super.addWidgets();
		List<ITransferHandlerWrapper<IItemHandler>> wrappers = module.getValidWrappers();
		if (!wrappers.isEmpty()) {
			int xSize = gui.xSize;
			int cycleSize = 110;
			addWidget(startHandler = new WidgetTransferHandler(xSize + 4, 22, wrappers));
			addWidget(slots = new WidgetTextField(xSize + 28, 24, 75, 15).setTootltip("module.transfer.item.page.from.info"));
			slots.setCharFilter(WidgetTextField.FILTER_NUMERIC_ARRAY);
			//slots.setValidator(new NumberValidator(((IItemHandler)startHandler.getCurrentWrapper().getHandler()).getCollection()));
			addWidget(endHandler = new WidgetTransferHandler(176 + 4, 52, wrappers));
			addWidget(insertSlots = new WidgetTextField(xSize + 28, 54, 75, 15).setTootltip("module.transfer.item.page.to.info"));
			insertSlots.setCharFilter(WidgetTextField.FILTER_NUMERIC_ARRAY);
			addWidget(amount = new WidgetTextField(xSize + cycleSize / 4 - 25 / 2, 84, 25, 15).setTootltip("module.transfer.item.page.amount.info"));
			amount.setMaxStringLength(2);
			amount.setCharFilter(WidgetTextField.FILTER_NUMERIC);
			//amount.setValidator(new NumberValidator(64));
			addWidget(priority = new WidgetTextField(xSize + cycleSize / 2 + 25 / 2, 84, 25, 15).setTootltip("module.transfer.item.page.priority.info"));
			priority.setMaxStringLength(3);
			priority.setCharFilter(WidgetTextField.FILTER_NUMERIC);
			addWidget(time = new WidgetTextField(xSize + cycleSize / 2 - 35 / 2, 114, 35, 15).setTootltip("module.transfer.item.page.time.info"));
			time.setMaxStringLength(3);
			time.setCharFilter(WidgetTextField.FILTER_NUMERIC);
			addWidget(metaButton = new WidgetButtonLabeled(xSize + cycleSize / 2 - 8 - 36 - 4, 135, "META"));
			addWidget(oredictButton = new WidgetButtonLabeled(xSize + cycleSize / 2 - 18 - 8 + 4, 135, "ORE"));
			addWidget(blacklistButton = new WidgetButtonLabeled(xSize + cycleSize / 2 + 18 - 8 - 4, 135, "BL"));
			addWidget(nbtButton = new WidgetButtonLabeled(xSize + cycleSize / 2 - 8 + 36 + 4, 135, "NBT"));
			for (int x = 0; x < 5; x++) {
				for (int y = 0; y < 4; y++) {
					WidgetFilterSlot slot = new WidgetFilterSlot(xSize + 8 + x * 18 + 1, 157 + y * 18 + 1);
					filterSlots.add(slot);
					addWidget(slot);
				}
			}
		}
	}
	
	@SideOnly(Side.CLIENT)
	@Override
	public void drawForeground(FontRenderer fontRenderer, int mouseX, int mouseY) {
		super.drawForeground(fontRenderer, mouseX, mouseY);
		List<ITransferHandlerWrapper<IItemHandler>> wrappers = module.getValidWrappers();
		if (!wrappers.isEmpty()) {
			String from = Translator.translateToLocal("module.transfer.item.page.from");
			fontRenderer.drawString(from, gui.xSize + 110 / 2 - fontRenderer.getStringWidth(from) / 2, 13, Color.GRAY.getRGB());
			String to = Translator.translateToLocal("module.transfer.item.page.to");
			fontRenderer.drawString(to, gui.xSize + 110 / 2 - fontRenderer.getStringWidth(to) / 2, 43, Color.GRAY.getRGB());
			String amount = Translator.translateToLocal("module.transfer.item.page.amount");
			fontRenderer.drawString(amount, gui.xSize + 110 / 4 - fontRenderer.getStringWidth(amount) / 2, 73, Color.GRAY.getRGB());
			String priority = Translator.translateToLocal("module.transfer.item.page.priority");
			fontRenderer.drawString(priority, gui.xSize + 110 / 2 + 25 - fontRenderer.getStringWidth(priority) / 2, 73, Color.GRAY.getRGB());
			String time = Translator.translateToLocal("module.transfer.item.page.time");
			fontRenderer.drawString(time, gui.xSize + 110 / 2 - fontRenderer.getStringWidth(time) / 2, 103, Color.GRAY.getRGB());
			//fontRenderer.drawStringWithShadow(Translator.translateToLocal("module.transfer.item.page.from"), gui.guiLeft + 176 , y, Color.GRAY)
		}
	}
	
	/*@SideOnly(Side.CLIENT)
	@Override
	public void removeCycleWidgets() {
		List<ITransferHandlerWrapper<IItemHandler>> wrappers = module.getValidWrappers();
		if(!wrappers.isEmpty()){
			widgetManager.remove(startHandler);
			widgetManager.remove(slots);
			widgetManager.remove(endHandler);
			widgetManager.remove(insertSlots);
			widgetManager.remove(time);
			widgetManager.remove(amount);
			widgetManager.remove(priority);
			widgetManager.remove(metaButton);
			widgetManager.remove(oredictButton);
			widgetManager.remove(blacklistButton);
			widgetManager.remove(nbtButton);
			for(WidgetFilterSlot slot : filterSlots){
				widgetManager.remove(slot);
			}
		}
	}*/
	
	public Set<Integer> parseIntArray(String text) {
		if (text == null || text.isEmpty()) {
			return Collections.emptySet();
		}
		String[] numberStrs = text.split(",");
		Set<Integer> numbers = new HashSet<>(numberStrs.length);
		for (int i = 0; i < numberStrs.length; i++) {
			Integer integer = Ints.tryParse(numberStrs[i]);
			if (integer == null) {
				continue;
			}
			numbers.add(integer);
		}
		return numbers;
	}
	
	public int parseInt(String text) {
		if (text == null || text.isEmpty()) {
			return -1;
		}
		return Ints.tryParse(text);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void addCycle() {
		int time = MathHelper.clamp(parseInt(this.time.getText()), 10, 512);
		int priority = parseInt(this.priority.getText());
		int amount = MathHelper.clamp(parseInt(this.amount.getText()), 1, 64);
		int[] slots = toIntArray(module.getHandler(startHandler.getCurrentWrapper()), parseIntArray(this.slots.getText()));
		int[] insertSlots = toIntArray(module.getHandler(endHandler.getCurrentWrapper()), parseIntArray(this.insertSlots.getText()));
		NonNullList<ItemStack> filterItems = NonNullList.create();
		if (time < 0 || amount < 0) {
			return;
		}
		for (WidgetFilterSlot slot : filterSlots) {
			ItemStack stack = slot.getStack();
			if (!stack.isEmpty()) {
				filterItems.add(stack);
			}
		}
		PacketHandler.sendToServer(new PacketAddCycle(module, new ItemTransferCycle(module, startHandler.getCurrentWrapper(), slots, endHandler.getCurrentWrapper(), insertSlots, time, priority, amount, NonNullList.create(), metaButton.isActive(), oredictButton.isActive(), blacklistButton.isActive(), nbtButton.isActive())));
	}
	
	private int[] toIntArray(IItemHandler itemHandler, Set<Integer> slots) {
		if (itemHandler == null) {
			return new int[0];
		}
		Iterator<Integer> iterator = slots.iterator();
		int slotCount = itemHandler.getSlots();
		while (iterator.hasNext()) {
			Integer slot = iterator.next();
			if (slot >= slotCount) {
				iterator.remove();
			}
		}
		Integer[] array = slots.toArray(new Integer[slots.size()]);
		return Arrays.stream(array).mapToInt(Integer::intValue).toArray();
	}
	
}
