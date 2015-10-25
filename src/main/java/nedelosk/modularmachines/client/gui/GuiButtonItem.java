package nedelosk.modularmachines.client.gui;

import org.lwjgl.opengl.GL11;

import nedelosk.forestday.api.guis.Button;
import nedelosk.modularmachines.client.gui.assembler.element.GuiElement;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class GuiButtonItem<T> extends Button {
  // Positions from generic.png
  protected static final GuiElement GUI_Button_pressed = new GuiElement(144, 216, 18, 18, 256, 256);
  protected static final GuiElement GUI_Button_normal = new GuiElement(144 + 18 * 2, 216, 18, 18, 256, 256);
  protected static final GuiElement GUI_Button_hover = new GuiElement(144 + 18 * 4, 216, 18, 18, 256, 256);

  private final ItemStack icon;
  public final T data;
  public boolean pressed;

  private GuiElement guiPressed = GUI_Button_pressed;
  private GuiElement guiNormal = GUI_Button_normal;
  private GuiElement guiHover = GUI_Button_hover;
  protected static RenderItem itemRender = new RenderItem();
  public static final ResourceLocation locBackground = new ResourceLocation("modularmachines", "textures/gui/icons.png");
  public ResourceLocation background = locBackground;

  public GuiButtonItem(int buttonId, int x, int y, String displayName, T data) {
    super(buttonId, x, y, 18, 18, displayName);

    this.icon = null;
    this.data = data;
  }

  public GuiButtonItem(int buttonId, int x, int y, ItemStack icon, T data) {
    super(buttonId, x, y, 18, 18, icon.getDisplayName());

    this.icon = icon;
    this.data = data;
  }

  public GuiButtonItem setGraphics(GuiElement normal, GuiElement hover, GuiElement pressed, ResourceLocation background) {
	this.guiPressed = pressed;
    this.guiNormal = normal;
    this.guiHover = hover;
    this.background = background;

    return this;
  }

  @Override
  public void drawButton(Minecraft mc, int mouseX, int mouseY) {
	GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
    mc.getTextureManager().bindTexture(background);

    if(this.visible) {
      this.field_146123_n = mouseX >= this.xPosition && mouseY >= this.yPosition &&
                     mouseX < this.xPosition + this.width && mouseY < this.yPosition + this.height;

      if(pressed) {
        guiPressed.draw(xPosition, yPosition);
      }
      else if(field_146123_n) {
        guiHover.draw(xPosition, yPosition);
      }
      else {
        guiNormal.draw(xPosition, yPosition);
      }

      drawIcon(mc);
    }
  }

  protected void drawIcon(Minecraft mc) {
	  GL11.glPushMatrix();
	  RenderHelper.enableStandardItemLighting();
	  itemRender.renderItemIntoGUI(mc.fontRenderer, mc.renderEngine, icon, xPosition+1, yPosition+1);
	  RenderHelper.disableStandardItemLighting();
	  GL11.glPopMatrix();
  }
}
