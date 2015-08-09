package nedelosk.modularmachines.client.gui.assembler.button;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.opengl.GL11;

import nedelosk.modularmachines.api.IModularAssembler;
import nedelosk.modularmachines.api.modular.module.ModuleEntry;
import nedelosk.nedeloskcore.utils.RenderUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.ResourceLocation;

public class GuiButtonModularAssemblerSlot extends GuiButton {

	protected ResourceLocation guiTextureOverlay = RenderUtils.getResourceLocation("modularmachines", "modular_assembler_overlay", "gui");
	
	public RenderItem itemRender = RenderItem.getInstance();
	public ModuleEntry entry;
	public IModularAssembler assembler;
	
	public GuiButtonModularAssemblerSlot(int p_i1021_1_, int p_i1021_2_, int p_i1021_3_, ModuleEntry entry, IModularAssembler assembler) {
		super(p_i1021_1_, p_i1021_2_, p_i1021_3_,18 , 18, null);
		this.entry = entry;
		this.assembler = assembler;
	}
	
	public void renderTooltip(int mx, int my)
	{
		boolean inside = mx >= xPosition && my >= yPosition && mx < xPosition + width && my < yPosition + height;
		if(entry.isActivate)
		{
			if(inside)
				if(assembler.getStackInSlot(entry.page, entry.ID) != null)
					renderToolTip(assembler.getStackInSlot(entry.page, entry.ID), mx, my);
				else
				{
					ArrayList<String> list = new ArrayList<String>();
					for(int i = 0;i < entry.moduleNames.length;i++)
					{
						if(entry.activatedModuleNames[i])
						{
							list.add(entry.moduleNames[i] + ((entry.moduleNames.length - 1 > i) ? "," : ""));
						}
					}
					/*list.add(entry.moduleNames[0]);
					if(entry.moduleNames.length > 1)
					{
						for(int i = 1;i < entry.moduleNames.length;i++)
						{
							String moduleName = list.get(i - 1);
							StringBuilder builder = new StringBuilder();
							builder.append(moduleName);
							builder.append(',');
							list.remove(i - 1);
							list.add(builder.toString());
							list.add(entry.moduleNames[i]);
						}
					}*/
			        RenderUtils.renderTooltip(mx, my, list);
				}
		}
	}
	
	@Override
	public void drawButton(Minecraft p_146112_1_, int mx, int my) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		RenderUtils.bindTexture(guiTextureOverlay);
		if(entry.isActivate)
		{
		RenderUtils.drawTexturedModalRect(xPosition, yPosition, 1,238 ,238 ,18 , 18);
		if(assembler.getStackInSlot(entry.page, entry.ID) != null)
		{
			RenderHelper.enableGUIStandardItemLighting();
			drawItemStack(assembler.getStackInSlot(entry.page, entry.ID), xPosition + 1, yPosition + 1);
			RenderHelper.disableStandardItemLighting();
		}
		}
		else
		{
			RenderUtils.drawTexturedModalRect(xPosition, yPosition, 1,220 ,238 ,18 , 18);
		}
	}
	
    protected void renderToolTip(ItemStack p_146285_1_, int x, int y)
    {
        List list = p_146285_1_.getTooltip(Minecraft.getMinecraft().thePlayer, Minecraft.getMinecraft().gameSettings.advancedItemTooltips);

        for (int k = 0; k < list.size(); ++k)
        {
            if (k == 0)
            {
                list.set(k, p_146285_1_.getRarity().rarityColor + (String)list.get(k));
            }
            else
            {
                list.set(k, EnumChatFormatting.GRAY + (String)list.get(k));
            }
        }
        RenderUtils.renderTooltip(x, y, list);
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
