package nedelosk.nedeloskcore.client.gui.button;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.opengl.GL11;

import nedelosk.nedeloskcore.client.gui.GuiBlueprint;
import nedelosk.nedeloskcore.common.plan.PlanRecipe;
import nedelosk.nedeloskcore.utils.RenderUtils;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;

public class GuiButtonBlueprint extends GuiButton {

	public GuiBlueprint blueprint;
	public Object object;
	public RenderItem itemRender = RenderItem.getInstance();
	
	public GuiButtonBlueprint(int id, int x, int y, GuiBlueprint blueprint) {
		super(id, x, y, 16, 16, "");
		this.blueprint = blueprint;
	}
	
	@Override
	public void drawButton(Minecraft mc, int x, int y) {
		if(object != null)
		{
		field_146123_n = x >= xPosition && y >= yPosition && x < xPosition + width && y < yPosition + height;
		if(object instanceof PlanRecipe)
		{
		drawItemStack(((PlanRecipe)object).outputBlock, xPosition, yPosition);
		List<String> tooltip = new ArrayList<String>();
		tooltip.add(((PlanRecipe)object).outputBlock.getDisplayName());
		tooltip.add(EnumChatFormatting.GRAY + (EnumChatFormatting.ITALIC + "Update Block : " + ((((PlanRecipe)object).updateBlock != null) ? (((PlanRecipe)object).updateBlock).getDisplayName() : "null")));
		int tooltipY = (tooltip.size() - 1) * 10;
		if(field_146123_n)
			RenderUtils.renderTooltip(x, y + tooltipY, tooltip);
		}
		else if(object instanceof Block)
		{
			drawItemStack(new ItemStack((Block) object), xPosition, yPosition);
			List<String> tooltip = new ArrayList<String>();
			tooltip.add("Updates / Block " + new ItemStack((Block) object).getDisplayName());
			int tooltipY = (tooltip.size() - 1) * 10;
			if(field_146123_n)
				RenderUtils.renderTooltip(x, y + tooltipY, tooltip);
		}
		}
	}
	
    private void drawItemStack(ItemStack stack, int x, int y)
    {
        GL11.glTranslatef(0.0F, 0.0F, 32.0F);
        this.zLevel = 200.0F;
        itemRender.zLevel = 200.0F;
        FontRenderer font = null;
        if (stack != null) font = stack.getItem().getFontRenderer(stack);
        if (font == null) font = Minecraft.getMinecraft().fontRenderer;
        itemRender.renderItemAndEffectIntoGUI(font, Minecraft.getMinecraft().getTextureManager(), stack, x, y);
        this.zLevel = 0.0F;
        itemRender.zLevel = 0.0F;
    }
	

}
