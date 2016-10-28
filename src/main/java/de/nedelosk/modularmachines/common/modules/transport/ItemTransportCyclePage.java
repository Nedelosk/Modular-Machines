package de.nedelosk.modularmachines.common.modules.transport;


import java.awt.FlowLayout;

import com.google.common.base.Predicate;
import com.google.common.primitives.Floats;
import com.google.common.primitives.Ints;

import de.nedelosk.modularmachines.api.modules.ModulePage;
import de.nedelosk.modularmachines.api.modules.transport.ITransportCycle;
import de.nedelosk.modularmachines.api.modules.transport.ITransportHandler;
import de.nedelosk.modularmachines.api.modules.transport.ITransportHandlerWrapper;
import de.nedelosk.modularmachines.api.modules.transport.TransportCyclePage;
import de.nedelosk.modularmachines.client.gui.widgets.WidgetTextField;
import de.nedelosk.modularmachines.client.gui.widgets.WidgetTransportHandler;
import de.nedelosk.modularmachines.client.gui.widgets.WidgetTransportHandlerWrapper;
import net.minecraft.block.state.IBlockState;
import net.minecraft.world.chunk.BlockStateContainer;
import net.minecraftforge.fml.client.config.GuiSlider;
import net.minecraftforge.items.IItemHandler;

public class ItemTransportCyclePage extends TransportCyclePage<IItemHandler, ItemTransportCycle> {

	private WidgetTextField inputSlots;
	private WidgetTextField outputSlots;
	private WidgetTextField amountPerWork;
	
	public static class NumberValidator implements Predicate<String>{
		public static final NumberValidator INSTANCE = new NumberValidator();
		
		private NumberValidator() {
		}
		
		@Override
		public boolean apply(String text) {
            Integer i = Ints.tryParse(text);
            return text.isEmpty() || i != null  && i.floatValue() >= 0;
		}
		
	}
	
	public ItemTransportCyclePage(String title) {
		super(title);
	}
	
	@Override
	public void addWidgets() {
		add(new WidgetTextField(5, 16, 8, 8).setValidator(NumberValidator.INSTANCE));
		add(new WidgetTextField(5, 16, 8, 8).setValidator(NumberValidator.INSTANCE));
	}
	
	@Override
	public void handleMouseClicked(int mouseX, int mouseY, int mouseButton) {
		super.handleMouseClicked(mouseX, mouseY, mouseButton);
	}

	//IItemHandler startHandler, int[] startSlots, IItemHandler endHandler, int[] endSlots, int time, int property, int amount
	
	@Override
	public ItemTransportCycle createCycle() {
		return null;
	}
}
