package nedelosk.modularmachines.client.gui.assembler;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.opengl.GL11;

import nedelosk.modularmachines.api.RendererSides;
import nedelosk.modularmachines.api.modular.module.ModuleEntry;
import nedelosk.modularmachines.common.blocks.tile.TileModularAssenbler;
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
	protected ResourceLocation guiTextureOverlay2 = RenderUtils.getResourceLocation("modularmachines", "modular_assembler_overlay_2", "gui");
	
	public RenderItem itemRender = RenderItem.getInstance();
	public boolean active;
	public GuiModularAssembler assembler;
	public int entryID;
	public int parentID;
	public String parentPage;
	public boolean hasParent;
	public RendererSides[] rendererSides;
	public int chainTile;
	public boolean isChain;
	public String page;
	
	public GuiButtonModularAssemblerSlot(int p_i1021_1_, int p_i1021_2_, int p_i1021_3_, int entryID, GuiModularAssembler assembler) {
		super(p_i1021_1_, p_i1021_2_, p_i1021_3_,18 , 18, null);
		this.entryID = entryID;
		this.assembler = assembler;
	}
	
	public GuiButtonModularAssemblerSlot(int p_i1021_1_, int p_i1021_2_, int p_i1021_3_, int entryID, String page, GuiModularAssembler assembler, int parentID, String parentPage, boolean hasParent, RendererSides[] rendererSides) {
		super(p_i1021_1_, p_i1021_2_, p_i1021_3_,18 , 18, null);
		this.entryID = entryID;
		this.parentID = parentID;
		this.assembler = assembler;
		this.hasParent = hasParent;
		this.rendererSides = rendererSides;
		this.parentPage = parentPage;
		this.page = page;
	}
	
	public GuiButtonModularAssemblerSlot(int p_i1021_1_, int p_i1021_2_, int p_i1021_3_, int entryID, String page, GuiModularAssembler assembler, int parentID, String parentPage, boolean hasParent, RendererSides[] rendererSides, boolean isChain, int chainTile) {
		super(p_i1021_1_, p_i1021_2_, p_i1021_3_,18 , 18, null);
		this.entryID = entryID;
		this.parentID = parentID;
		this.assembler = assembler;
		this.hasParent = hasParent;
		this.rendererSides = rendererSides;
		this.isChain = isChain;
		this.chainTile = chainTile;
		this.parentPage = parentPage;
		this.page = page;
	}
	
	@Override
	public void drawButton(Minecraft p_146112_1_, int mx, int my) {
		boolean inside = mx >= xPosition && my >= yPosition && mx < xPosition + width && my < yPosition + height;
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		RenderUtils.bindTexture(guiTextureOverlay2);
		if(rendererSides != null)
			if(isChain)
			{
				if(rendererSides[0] == RendererSides.EAST)
				{
					if(chainTile == 0)
					{
						RenderUtils.drawTexturedModalRect(xPosition + 18, yPosition + 5, 1,!active ? 18 : 72 ,54 ,18 , 8);
						RenderUtils.drawTexturedModalRect(xPosition + 18 + 5, yPosition + 13, 1,!active ? 23 : 59 ,62 ,8 , 5);
					}
					else if(chainTile == 1)
					{
						RenderUtils.drawTexturedModalRect(xPosition + 18 + 5, yPosition, 1,!active ? 23 : 77 ,68 ,8 , 18);
						RenderUtils.drawTexturedModalRect(xPosition + 18, yPosition + 4, 1,!active ? 18 : 72 ,72 ,5 , 8);
					}
					else if(chainTile == 2)
					{
						RenderUtils.drawTexturedModalRect(xPosition + 18 + 5, yPosition, 1,!active ? 23 : 77 ,85 ,8 , 14);
						RenderUtils.drawTexturedModalRect(xPosition + 18, yPosition + 6, 1,!active ? 18 : 72 ,91 ,5 , 8);
					}
				}
				else if(rendererSides[0] == RendererSides.WEST)
					if(chainTile == 0)
					{
						RenderUtils.drawTexturedModalRect(xPosition - 18, yPosition + 5, 1,!active ? 0 : 54 ,54 ,18 , 8);
						RenderUtils.drawTexturedModalRect(xPosition - 13, yPosition + 13, 1,!active ? 5 : 59 ,62 ,8 , 5);
					}
					else if(chainTile == 1)
					{
						RenderUtils.drawTexturedModalRect(xPosition - 13, yPosition, 1,!active ? 5 : 59 ,68 ,8 , 18);
						RenderUtils.drawTexturedModalRect(xPosition - 5, yPosition + 4, 1,!active ? 13 : 67 ,72 ,5 , 8);
					}
					else if(chainTile == 2)
					{
						RenderUtils.drawTexturedModalRect(xPosition - 13, yPosition, 1,!active ? 5 : 59 ,85 ,8 , 14);
						RenderUtils.drawTexturedModalRect(xPosition - 5, yPosition + 6, 1,!active ? 13 : 67 ,91 ,5 , 8);
					}
						
			}
			else
			{
				for(RendererSides side : rendererSides)
				{
					if(side == RendererSides.EAST)
						RenderUtils.drawTexturedModalRect(xPosition + 18, yPosition + 5, 1, !active ? 36 : 90 , 23 ,18 , 8);
					if(side == RendererSides.WEST)
						RenderUtils.drawTexturedModalRect(xPosition - 18, yPosition + 5, 1,!active ? 0 : 54 , 23 ,18 , 8);
					if(side == RendererSides.NORTH)
						RenderUtils.drawTexturedModalRect(xPosition + 5, yPosition - 18, 1,!active ? 23 : 77 , 0 ,8 , 18);
					if(side == RendererSides.SOUTH)
						RenderUtils.drawTexturedModalRect(xPosition + 5, yPosition + 18, 1,!active ? 23 : 77 , 36 ,8 , 18);
						
				}
			}
		RenderUtils.bindTexture(guiTextureOverlay);
		if(active)
		{
		RenderUtils.drawTexturedModalRect(xPosition, yPosition, 1,238 ,238 ,18 , 18);
		if(((TileModularAssenbler)this.assembler.getTile()).getStackInSlot(page, entryID) != null)
		{
			RenderHelper.enableGUIStandardItemLighting();
			drawItemStack(((TileModularAssenbler)this.assembler.getTile()).getStackInSlot(page, entryID), xPosition + 1, yPosition + 1);
			RenderHelper.disableStandardItemLighting();
			if(inside)
				renderToolTip(((TileModularAssenbler)this.assembler.getTile()).getStackInSlot(page, entryID), my, my);
		}
		else
		{
			if(inside)
			{
				ArrayList<String> list = new ArrayList<String>();
				list.add(((TileModularAssenbler)this.assembler.getTile()).moduleEntrys.get(page).get(entryID).moduleName);
				if(((TileModularAssenbler)this.assembler.getTile()).moduleEntrys.get(page).get(entryID).moduleName2 != null)
				{
					String moduleName = list.get(0);
					StringBuilder builder = new StringBuilder();
					builder.append(moduleName);
					builder.append(',');
					list.remove(0);
					list.add(builder.toString());
					list.add(((TileModularAssenbler)this.assembler.getTile()).moduleEntrys.get(page).get(entryID).moduleName2);
				}
		        RenderUtils.renderTooltip(my, my, list);
			}
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
