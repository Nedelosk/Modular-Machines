package nedelosk.forestday.client.gui;

import java.util.ArrayList;

import nedelosk.forestday.api.guis.IGuiBase;
import nedelosk.forestday.api.guis.IWidgetManager;
import nedelosk.forestday.api.guis.Widget;
import nedelosk.forestday.utils.RenderUtils;
import net.minecraft.client.Minecraft;

import org.lwjgl.opengl.GL11;

public class WidgetManager<G extends IGuiBase> implements IWidgetManager<G> {

	public final G gui;
	public final Minecraft minecraft;
	protected final ArrayList<Widget> widgets = new ArrayList<Widget>();

	public WidgetManager(G gui) {
		this.gui = gui;
		this.minecraft = Minecraft.getMinecraft();
	}

	@Override
	public void add(Widget slot) {
		if(!widgets.contains(slot))
			this.widgets.add(slot);
	}

	@Override
	public void remove(Widget slot) {
		this.widgets.remove(slot);
	}

	public void clear() {
		this.widgets.clear();
	}

	protected Widget getAtPosition(int mX, int mY) {
		for (Widget slot : widgets) {
			if (slot.isMouseOver(mX, mY)) {
				return slot;
			}
		}

		return null;
	}

	public void drawWidgets() {
		gui.setZLevel(100.0F);
		GuiBase.getItemRenderer().zLevel = 100.0F;
		for (Widget slot : widgets) {
			GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
			slot.draw(gui);
		}
		gui.setZLevel(0.0F);
		GuiBase.getItemRenderer().zLevel = 0.0F;

	}
	
	public void drawTooltip(int mX, int mY)
	{
		for(Widget slot : widgets)
		{
			if(slot.isMouseOver(mX - gui.getGuiLeft(), mY - gui.getGuiTop()))
				RenderUtils.renderTooltip(mX - gui.getGuiLeft(), mY - gui.getGuiTop(), slot.getTooltip());
		}
	}

	public void handleMouseClicked(int mouseX, int mouseY, int mouseButton) {
		Widget slot = getAtPosition(mouseX - gui.getGuiLeft(), mouseY - gui.getGuiTop());
		if (slot != null) {
			slot.handleMouseClick(mouseX, mouseY, mouseButton, gui);
		}
	}

	@Override
	public ArrayList<Widget> getWidgets() {
		return widgets;
	}

	@Override
	public G getGui() {
		return gui;
	}
}
