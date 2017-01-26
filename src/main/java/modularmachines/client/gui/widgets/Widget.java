package modularmachines.client.gui.widgets;

import java.awt.Rectangle;
import java.util.List;

import modularmachines.api.IGuiProvider;
import modularmachines.api.ILocatableSource;
import modularmachines.client.gui.WidgetManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public abstract class Widget<S extends ILocatableSource> {
	
	protected static final ResourceLocation widgetTexture = new ResourceLocation("modularmachines", "textures/gui/widgets.png");
	
	protected final Rectangle positon;
	protected WidgetManager<IGuiProvider, S> manager;
	protected S source;

	public Widget(int posX, int posY, int width, int height) {
		this.positon = new Rectangle(posX, posY, width, height);
		this.source = manager.getGui().getSource();
	}
	
	public void setManager(WidgetManager<IGuiProvider, S> manager) {
		this.manager = manager;
		this.source = manager.getGui().getSource();
	}
	
	public WidgetManager<IGuiProvider, S> getManager() {
		return manager;
	}

	public void draw() {
	}

	public boolean keyTyped(char keyChar, int keyCode) {
		return false;
	}

	public void drawStrings() {
	}

	public List<String> getTooltip() {
		return null;
	}

	public boolean isMouseOver(int x, int y) {
		return x >= positon.x && y >= positon.y && x < positon.x + positon.width && y < positon.y + positon.height;
	}

	public void handleMouseClick(int mouseX, int mouseY, int mouseButton) {
	}

	public boolean isFocused() {
		return false;
	}

	public void setFocused(boolean focused) {
	}

	public String getText() {
		return null;
	}
	
	public final Rectangle getPos() {
		return positon;
	}

	@Override
	public boolean equals(Object obj) {
		Widget w = (Widget) obj;
		if (w.positon.equals(positon)) {
			return true;
		}
		return false;
	}
}