package nedelosk.modularmachines.client.techtree.gui.gadgets;

import org.lwjgl.opengl.GL11;

import nedelosk.modularmachines.client.techtree.gui.GuiTechTreeEditor;
import nedelosk.nedeloskcore.utils.RenderUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class GuiButtonSlot extends GuiButton {

	public ItemStack stack;
	private GuiTechTreeEditor parent;
	
	public GuiButtonSlot(int p_i1021_1_, int p_i1021_2_, int p_i1021_3_, GuiTechTreeEditor parent) {
		super(p_i1021_1_, p_i1021_2_, p_i1021_3_, 18, 18, null);
		this.parent = parent;
	}
	
	@Override
	public void drawButton(Minecraft mc, int mX, int mY) {
		if(visible){
			field_146123_n = mX >= this.xPosition && mY >= this.yPosition && mX < this.xPosition + this.width && mY < this.yPosition + this.height;
			RenderUtils.bindTexture(new ResourceLocation("modularmachines", "textures/gui/gui_techtree_editor.png"));
			GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
			GL11.glEnable(GL11.GL_BLEND);
            OpenGlHelper.glBlendFunc(770, 771, 1, 0);
            GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
			drawTexturedModalRect(xPosition, yPosition, 0, 166, width, height);
			parent.drawItemStack(stack, xPosition + 1, yPosition + 1);
			if(field_146123_n && stack != null)
				RenderUtils.renderTooltip(mX, mY, stack.getTooltip(mc.thePlayer, false));
		}
	}
	
	public void setItem(ItemStack stack){
		this.stack = stack;
	}

}
