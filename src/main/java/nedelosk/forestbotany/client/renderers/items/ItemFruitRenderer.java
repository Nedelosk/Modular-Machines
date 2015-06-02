package nedelosk.forestbotany.client.renderers.items;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import nedelosk.forestbotany.common.genetics.templates.crop.CropDefinition.SeedFile;
import nedelosk.forestbotany.common.items.ItemPlant;
import nedelosk.forestbotany.common.items.ItemSeed;
import nedelosk.nedeloskcore.utils.RenderUtils;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraftforge.client.IItemRenderer;

public class ItemFruitRenderer implements IItemRenderer {

    @Override
    public boolean handleRenderType (ItemStack item, ItemRenderType type)
    {
        if (!item.hasTagCompound())
            return false;

        switch (type)
        {
        case ENTITY:
            return true;
        case EQUIPPED:
            GL11.glTranslatef(0.03f, 0F, -0.09375F);
        case EQUIPPED_FIRST_PERSON:
            return true;
        case INVENTORY:
            return true;
        default:
            return true;
        case FIRST_PERSON_MAP:
            return false;
        }
    }

    @Override
    public boolean shouldUseRenderHelper (ItemRenderType type, ItemStack item, ItemRendererHelper helper)
    {
        return handleRenderType(item, type) & helper.ordinal() < ItemRendererHelper.EQUIPPED_BLOCK.ordinal();
    }

	@Override
	public void renderItem(ItemRenderType type, ItemStack item, Object... data) {
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        Tessellator tess = Tessellator.instance;
        	
        int icons = 4;
        IIcon[] parts = new IIcon[icons];
        int[] color = new int[icons];
        for(int i = 0;i < icons;i++)
        {
        parts[i] = ((ItemPlant)item.getItem()).getPlantIcon(item, i);
        if(parts[i] == null)
        	parts[i] = Items.wheat_seeds.getIcon(item, 0);
        }
        
        float[] xMax = new float[icons];
        float[] yMin = new float[icons];
        float[] xMin = new float[icons];
        float[] yMax = new float[icons];
        float depth = 1f / 16f;

        float[] width = new float[icons];
        float[] height = new float[icons];
        float[] xDiff = new float[icons];
        float[] yDiff = new float[icons];
        float[] xSub = new float[icons];
        float[] ySub = new float[icons];
        for (int i = 0; i < icons; ++i)
        {
            IIcon icon = parts[i];
            xMin[i] = icon.getMinU();
            xMax[i] = icon.getMaxU();
            yMin[i] = icon.getMinV();
            yMax[i] = icon.getMaxV();
            width[i] = icon.getIconWidth();
            height[i] = icon.getIconHeight();
            xDiff[i] = xMin[i] - xMax[i];
            yDiff[i] = yMin[i] - yMax[i];
            xSub[i] = 0.5f * (xMax[i] - xMin[i]) / width[i];
            ySub[i] = 0.5f * (yMax[i] - yMin[i]) / height[i];
        }
        GL11.glPushMatrix();
        
        for(int i = 0; i < icons; i++)
            color[i] = item.getItem().getColorFromItemStack(item, i);
        
        GL11.glEnable(GL12.GL_RESCALE_NORMAL);
        
		if(type == ItemRenderType.INVENTORY)
		{
	        GL11.glDisable(GL11.GL_LIGHTING);
	        GL11.glEnable(GL11.GL_ALPHA_TEST);
	        GL11.glDisable(GL11.GL_BLEND);
	        tess.startDrawingQuads();
	        for (int i = 0; i < icons; ++i)
	        {
	            tess.setColorOpaque_I(color[i]);
	            tess.addVertexWithUV(0, 16, 0, xMin[i], yMax[i]);
	            tess.addVertexWithUV(16, 16, 0, xMax[i], yMax[i]);
	            tess.addVertexWithUV(16, 0, 0, xMax[i], yMin[i]);
	            tess.addVertexWithUV(0, 0, 0, xMin[i], yMin[i]);
	        }
	        tess.draw();
	        GL11.glEnable(GL11.GL_LIGHTING);
	        GL11.glDisable(GL11.GL_ALPHA_TEST);
	        GL11.glEnable(GL11.GL_BLEND);
		}
		else
        {
            switch (type)
            {
            case EQUIPPED_FIRST_PERSON:
                break;
            case EQUIPPED:
                GL11.glTranslatef(0, -4 / 16f, 0);
                break;
            default:
            }

            // one side
            tess.startDrawingQuads();
            tess.setNormal(0, 0, 1);
            for (int i = 0; i < icons; ++i)
            {
                tess.setColorOpaque_I(color[i]);
                tess.addVertexWithUV(0, 0, 0, xMax[i], yMax[i]);
                tess.addVertexWithUV(1, 0, 0, xMin[i], yMax[i]);
                tess.addVertexWithUV(1, 1, 0, xMin[i], yMin[i]);
                tess.addVertexWithUV(0, 1, 0, xMax[i], yMin[i]);
            }
            tess.draw();

            // other side
            tess.startDrawingQuads();
            tess.setNormal(0, 0, -1);
            for (int i = 0; i < icons; ++i)
            {
                tess.setColorOpaque_I(color[i]);
                tess.addVertexWithUV(0, 1, -depth, xMax[i], yMin[i]);
                tess.addVertexWithUV(1, 1, -depth, xMin[i], yMin[i]);
                tess.addVertexWithUV(1, 0, -depth, xMin[i], yMax[i]);
                tess.addVertexWithUV(0, 0, -depth, xMax[i], yMax[i]);
            }
            tess.draw();

            // make it have "depth"
            tess.startDrawingQuads();
            tess.setNormal(-1, 0, 0);
            float pos;
            float iconPos;

            for (int i = 0; i < icons; ++i)
            {
                tess.setColorOpaque_I(color[i]);
                float w = width[i], m = xMax[i], d = xDiff[i], s = xSub[i];
                for (int k = 0, e = (int) w; k < e; ++k)
                {
                    pos = k / w;
                    iconPos = m + d * pos - s;
                    tess.addVertexWithUV(pos, 0, -depth, iconPos, yMax[i]);
                    tess.addVertexWithUV(pos, 0, 0, iconPos, yMax[i]);
                    tess.addVertexWithUV(pos, 1, 0, iconPos, yMin[i]);
                    tess.addVertexWithUV(pos, 1, -depth, iconPos, yMin[i]);
                }
            }

            tess.draw();
            tess.startDrawingQuads();
            tess.setNormal(1, 0, 0);
            float posEnd;

            for (int i = 0; i < icons; ++i)
            {
                tess.setColorOpaque_I(color[i]);
                float w = width[i], m = xMax[i], d = xDiff[i], s = xSub[i];
                float d2 = 1f / w;
                for (int k = 0, e = (int) w; k < e; ++k)
                {
                    pos = k / w;
                    iconPos = m + d * pos - s;
                    posEnd = pos + d2;
                    tess.addVertexWithUV(posEnd, 1, -depth, iconPos, yMin[i]);
                    tess.addVertexWithUV(posEnd, 1, 0, iconPos, yMin[i]);
                    tess.addVertexWithUV(posEnd, 0, 0, iconPos, yMax[i]);
                    tess.addVertexWithUV(posEnd, 0, -depth, iconPos, yMax[i]);
                }
            }

            tess.draw();
            tess.startDrawingQuads();
            tess.setNormal(0, 1, 0);

            for (int i = 0; i < icons; ++i)
            {
                tess.setColorOpaque_I(color[i]);
                float h = height[i], m = yMax[i], d = yDiff[i], s = ySub[i];
                float d2 = 1f / h;
                for (int k = 0, e = (int) h; k < e; ++k)
                {
                    pos = k / h;
                    iconPos = m + d * pos - s;
                    posEnd = pos + d2;
                    tess.addVertexWithUV(0, posEnd, 0, xMax[i], iconPos);
                    tess.addVertexWithUV(1, posEnd, 0, xMin[i], iconPos);
                    tess.addVertexWithUV(1, posEnd, -depth, xMin[i], iconPos);
                    tess.addVertexWithUV(0, posEnd, -depth, xMax[i], iconPos);
                }
            }

            tess.draw();
            tess.startDrawingQuads();
            tess.setNormal(0, -1, 0);

            for (int i = 0; i < icons; ++i)
            {
                tess.setColorOpaque_I(color[i]);
                float h = height[i], m = yMax[i], d = yDiff[i], s = ySub[i];
                for (int k = 0, e = (int) h; k < e; ++k)
                {
                    pos = k / h;
                    iconPos = m + d * pos - s;
                    tess.addVertexWithUV(1, pos, 0, xMin[i], iconPos);
                    tess.addVertexWithUV(0, pos, 0, xMax[i], iconPos);
                    tess.addVertexWithUV(0, pos, -depth, xMax[i], iconPos);
                    tess.addVertexWithUV(1, pos, -depth, xMin[i], iconPos);
                }
            }

            tess.draw();
            GL11.glDisable(GL12.GL_RESCALE_NORMAL);
        }

        GL11.glPopMatrix();
        }

}
