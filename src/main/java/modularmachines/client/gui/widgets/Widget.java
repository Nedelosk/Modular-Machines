package modularmachines.client.gui.widgets;

import java.awt.Rectangle;
import java.util.Collections;
import java.util.List;

import net.minecraft.util.ResourceLocation;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import modularmachines.api.IGuiProvider;
import modularmachines.client.gui.GuiBase;
import modularmachines.client.gui.WidgetManager;

@SideOnly(Side.CLIENT)
public abstract class Widget<P extends IGuiProvider> {
	
	protected static final ResourceLocation widgetTexture = new ResourceLocation("modularmachines", "textures/gui/widgets.png");
	
	protected final Rectangle pos;
	protected WidgetManager<P> manager;
	protected GuiBase<P> gui;
	protected P provider;
	
	public Widget(int posX, int posY, int width, int height) {
		this.pos = new Rectangle(posX, posY, width, height);
	}
	
	public void setManager(WidgetManager<P> manager) {
		this.manager = manager;
		this.gui = manager.getGui();
		this.provider = gui.getProvider();
	}
	
	public WidgetManager<P> getManager() {
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
		return Collections.emptyList();
	}
	
	public boolean isMouseOver(int x, int y) {
		return x >= pos.x && y >= pos.y && x < pos.x + pos.width && y < pos.y + pos.height;
	}
	
	public void handleMouseClick(int mouseX, int mouseY, int mouseButton) {
	}
	
	public void onMouseClick(int mouseX, int mouseY, int mouseButton) {
		
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
		return w.pos.equals(pos);
	}
}