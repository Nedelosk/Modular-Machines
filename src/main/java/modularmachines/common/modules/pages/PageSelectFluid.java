package modularmachines.common.modules.pages;

import net.minecraft.client.gui.inventory.GuiContainer;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import modularmachines.api.modules.pages.IModuleComponent;
import modularmachines.client.gui.widgets.Widget;
import modularmachines.client.gui.widgets.WidgetTextField;

public class PageSelectFluid extends PageWidget {
	
	@SideOnly(Side.CLIENT)
	private WidgetTextField field;
	
	public PageSelectFluid(IModuleComponent component, GuiContainer gui) {
		super(component, gui);
	}
	
	@SideOnly(Side.CLIENT)
	@Override
	public void addWidget(Widget widget) {
		super.addWidget(widget);
		addWidget(field = new WidgetTextField(20, 5, 75, 15));
	}
	
}
