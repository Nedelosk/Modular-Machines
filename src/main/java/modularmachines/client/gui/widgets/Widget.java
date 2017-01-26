package modularmachines.client.gui.widgets;

import java.awt.Rectangle;
import java.util.List;

import modularmachines.api.IGuiProvider;
import modularmachines.api.ILocatableSource;
import modularmachines.client.gui.GuiBase;
import modularmachines.client.gui.WidgetManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public abstract class Widget<S extends ILocatableSource> {
	
	protected static final ResourceLocation widgetTexture = new ResourceLocation("modularmachines", "textures/gui/widgets.png");
	
	protected final Rectangle pos;
	protected WidgetManager<IGuiProvider, S> manager;
	protected GuiBase<IGuiProvider, S> gui;
	protected S source;

	public Widget(int posX, int posY, int width, int height) {
		this.pos = new Rectangle(posX, posY, width, height);
		this.gui = manager.getGui();
		this.source = gui.getSource();
	}
	
	public void setManager(WidgetManager<IGuiProvider, S> manager) {
		this.manager = manager;
		this.source = manager.getGui().getSource();
	}
	
	public WidgetManager<IGuiProvider, S> getManager() {
		return manager;
	}

	public void draw(int guiLeft, int guiTop) {
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
		return x >= pos.x && y >= pos.y && x < pos.x + pos.width && y < pos.y + pos.height;
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
		return pos;
	}

	@Override
	public boolean equals(Object obj) {
		Widget w = (Widget) obj;
		if (w.pos.equals(pos)) {
			return true;
		}
		return false;
	}
}