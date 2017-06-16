package modularmachines.common.modules.pages;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import modularmachines.api.modules.Module;
import modularmachines.client.gui.widgets.Widget;
import modularmachines.client.gui.widgets.WidgetTextField;

public class PageSelectFluid extends ModulePageWidget {

	@SideOnly(Side.CLIENT)
	private WidgetTextField field;
	
	public PageSelectFluid(Module parent) {
		super(parent);
	}
	
	@SideOnly(Side.CLIENT)
	@Override
	public void addWidget(Widget widget) {
		super.addWidget(widget);
		addWidget(field = new WidgetTextField(20, 5, 75, 15));
	}
	
	@Override
	public int getPlayerInvPosition() {
		return -1;
	}

}
