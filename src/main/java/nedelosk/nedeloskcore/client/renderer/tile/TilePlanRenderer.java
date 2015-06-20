package nedelosk.nedeloskcore.client.renderer.tile;

import org.lwjgl.opengl.GL11;

import nedelosk.nedeloskcore.common.blocks.tile.TilePlan;
import nedelosk.nedeloskcore.common.core.registry.NRegistry;
import nedelosk.nedeloskcore.common.items.ItemPlan;
import nedelosk.nedeloskcore.utils.RenderUtils;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.util.ResourceLocation;

public class TilePlanRenderer extends TileEntitySpecialRenderer {

	@Override
	public void renderTileEntityAt(TileEntity tile, double x, double y, double z, float p_147500_8_) {
		TilePlan plan = (TilePlan) tile.getWorldObj().getTileEntity(tile.xCoord, tile.yCoord, tile.zCoord);
		GL11.glPushMatrix();
		GL11.glTranslated((float) x + 0.5F, (float) y - 0.5F, (float) z + 0.5F);
		Tessellator t = Tessellator.instance;
		GL11.glPushMatrix();
			this.bindTexture(new ResourceLocation("textures/blocks/log_oak.png"));
			t.startDrawingQuads();
			t.setNormal(0, 1, 0);
			t.addVertexWithUV(0.5, 0.5, 0.5, 1, 0);
			t.addVertexWithUV(0.5, 0.5, -0.5, 0, 0);
			t.addVertexWithUV(0.5, 0.7, -0.5, 0, 1);
			t.addVertexWithUV(0.5, 1.0, 0.5, 1, 1);
			t.draw();
			
			t.startDrawingQuads();
			t.setNormal(0, 1, 0);
			t.addVertexWithUV(-0.5, 0.5, -0.5, 1, 0);
			t.addVertexWithUV(-0.5, 0.5, 0.5, 0, 0);
			t.addVertexWithUV(-0.5, 1.0, 0.5, 0, 1);
			t.addVertexWithUV(-0.5, 0.7, -0.5, 1, 1);
			t.draw();
			
			t.startDrawingQuads();
			t.setNormal(0, 1, 0);
			t.addVertexWithUV(-0.5, 0.5, 0.5, 1, 0);
			t.addVertexWithUV(0.5, 0.5, 0.5, 0, 0);
			t.addVertexWithUV(0.5, 1.0, 0.5, 0, 1);
			t.addVertexWithUV(-0.5, 1.0, 0.5, 1, 1);
			t.draw();
			
			t.startDrawingQuads();
			t.setNormal(0, 1, 0);
			t.addVertexWithUV(0.5, 0.5, -0.5, 1, 0);
			t.addVertexWithUV(-0.5, 0.5,-0.5, 0, 0);
			t.addVertexWithUV(-0.5, 0.7, -0.5, 0, 1);
			t.addVertexWithUV(0.5, 0.7, -0.5, 1, 1);
			t.draw();
			
			//Top
			t.startDrawingQuads();
			t.setNormal(0, 1, 0);
			t.addVertexWithUV(-0.5, 1.0, 0.5, 1, 0);
			t.addVertexWithUV(0.5, 1.0, 0.5, 0, 0);
			t.addVertexWithUV(0.5, 0.7, -0.5, 0, 1);
			t.addVertexWithUV(-0.5, 0.7, -0.5, 1, 1);
			t.draw();
			
			//Down
			t.startDrawingQuads();
			t.setNormal(0, 1, 0);
			t.addVertexWithUV(-0.5, 0.5, -0.5, 1, 0);
			t.addVertexWithUV(0.5, 0.5, -0.5, 0, 0);
			t.addVertexWithUV(0.5, 0.5, 0.5, 0, 1);
			t.addVertexWithUV(-0.5, 0.5, 0.5, 1, 1);
			t.draw();
			
			this.bindTexture(new ResourceLocation("textures/blocks/planks_oak.png"));
			
			t.startDrawingQuads();
			t.setNormal(0, 1, 0);
			t.addVertexWithUV(0.4, 0.7, -0.45, 1, 0);
			t.addVertexWithUV(-0.4, 0.7,-0.45, 0, 0);
			t.addVertexWithUV(-0.4, 0.9, -0.45, 0, 0.2);
			t.addVertexWithUV(0.4, 0.9, -0.45, 1, 0.2);
			t.draw();
			
			t.startDrawingQuads();
			t.setNormal(0, 1, 0);
			t.addVertexWithUV(-0.4, 0.7, -0.4, 1, 0);
			t.addVertexWithUV(0.4, 0.7, -0.4, 0, 0);
			t.addVertexWithUV(0.4, 0.9, -0.4, 0, 0.2);
			t.addVertexWithUV(-0.4, 0.9, -0.4, 1, 0.2);
			t.draw();
			
			t.startDrawingQuads();
			t.setNormal(0, 1, 0);
			t.addVertexWithUV(-0.4, 0.9, -0.4, 1, 0);
			t.addVertexWithUV(0.4, 0.9, -0.4, 0, 0);
			t.addVertexWithUV(0.4, 0.9, -0.45, 0, 0.2);
			t.addVertexWithUV(-0.4, 0.9, -0.45, 1, 0.2);
			t.draw();
			
			t.startDrawingQuads();
			t.setNormal(0, 1, 0);
			t.addVertexWithUV(0.4, 0.5, -0.4, 1, 0);
			t.addVertexWithUV(0.4, 0.5, -0.45, 0, 0);
			t.addVertexWithUV(0.4, 0.9, -0.45, 0, 0.2);
			t.addVertexWithUV(0.4, 0.9, -0.4, 1, 0.2);
			t.draw();
			
			t.startDrawingQuads();
			t.setNormal(0, 1, 0);
			t.addVertexWithUV(-0.4, 0.5, -0.45, 1, 0);
			t.addVertexWithUV(-0.4, 0.5, -0.4, 0, 0);
			t.addVertexWithUV(-0.4, 0.9, -0.4, 0, 0.2);
			t.addVertexWithUV(-0.4, 0.9, -0.45, 1, 0.2);
			t.draw();
			
			if(plan.getPlan() != null)
			{
			RenderUtils.bindItemTexture();
			Item planItem = NRegistry.plan;
			IIcon iconPlan = planItem.getIcon(new ItemStack(NRegistry.plan, 1, 0), 0);
			t.startDrawingQuads();
			t.setNormal(0, 1, 0);
			t.addVertexWithUV(-0.431, 1.02, 0.431, iconPlan.getMaxU(), iconPlan.getMinV());
			t.addVertexWithUV(0.431, 1.02, 0.431, iconPlan.getMinU(), iconPlan.getMinV());
			t.addVertexWithUV(0.431, 0.72, -0.431, iconPlan.getMinU(), iconPlan.getMaxV());
			t.addVertexWithUV(-0.431, 0.72, -0.431, iconPlan.getMaxU(), iconPlan.getMaxV());
			t.draw();
			}
		GL11.glPopMatrix();
		GL11.glPopMatrix();
	}
	
	public void renderItem()
	{
		GL11.glPushMatrix();
		GL11.glTranslated((float) 0.5F, (float) -0.5F, (float) 0.5F);
		GL11.glRotatef(180.0F, 0.0F, 1F, 0.0F);
		Tessellator t = Tessellator.instance;
		GL11.glPushMatrix();
		RenderUtils.bindTexture(new ResourceLocation("textures/blocks/log_oak.png"));
		t.startDrawingQuads();
		t.setNormal(0, 1, 0);
		t.addVertexWithUV(0.5, 0.5, 0.5, 1, 0);
		t.addVertexWithUV(0.5, 0.5, -0.5, 0, 0);
		t.addVertexWithUV(0.5, 0.7, -0.5, 0, 1);
		t.addVertexWithUV(0.5, 1.0, 0.5, 1, 1);
		t.draw();
		
		t.startDrawingQuads();
		t.setNormal(0, 1, 0);
		t.addVertexWithUV(-0.5, 0.5, -0.5, 1, 0);
		t.addVertexWithUV(-0.5, 0.5, 0.5, 0, 0);
		t.addVertexWithUV(-0.5, 1.0, 0.5, 0, 1);
		t.addVertexWithUV(-0.5, 0.7, -0.5, 1, 1);
		t.draw();
		
		t.startDrawingQuads();
		t.setNormal(0, 1, 0);
		t.addVertexWithUV(-0.5, 0.5, 0.5, 1, 0);
		t.addVertexWithUV(0.5, 0.5, 0.5, 0, 0);
		t.addVertexWithUV(0.5, 1.0, 0.5, 0, 1);
		t.addVertexWithUV(-0.5, 1.0, 0.5, 1, 1);
		t.draw();
		
		t.startDrawingQuads();
		t.setNormal(0, 1, 0);
		t.addVertexWithUV(0.5, 0.5, -0.5, 1, 0);
		t.addVertexWithUV(-0.5, 0.5,-0.5, 0, 0);
		t.addVertexWithUV(-0.5, 0.7, -0.5, 0, 1);
		t.addVertexWithUV(0.5, 0.7, -0.5, 1, 1);
		t.draw();
		
		//Top
		t.startDrawingQuads();
		t.setNormal(0, 1, 0);
		t.addVertexWithUV(-0.5, 1.0, 0.5, 1, 0);
		t.addVertexWithUV(0.5, 1.0, 0.5, 0, 0);
		t.addVertexWithUV(0.5, 0.7, -0.5, 0, 1);
		t.addVertexWithUV(-0.5, 0.7, -0.5, 1, 1);
		t.draw();
		
		//Down
		t.startDrawingQuads();
		t.setNormal(0, 1, 0);
		t.addVertexWithUV(-0.5, 0.5, -0.5, 1, 0);
		t.addVertexWithUV(0.5, 0.5, -0.5, 0, 0);
		t.addVertexWithUV(0.5, 0.5, 0.5, 0, 1);
		t.addVertexWithUV(-0.5, 0.5, 0.5, 1, 1);
		t.draw();
		
		RenderUtils.bindTexture(new ResourceLocation("textures/blocks/planks_oak.png"));
		
		t.startDrawingQuads();
		t.setNormal(0, 1, 0);
		t.addVertexWithUV(0.4, 0.7, -0.45, 1, 0);
		t.addVertexWithUV(-0.4, 0.7,-0.45, 0, 0);
		t.addVertexWithUV(-0.4, 0.9, -0.45, 0, 0.2);
		t.addVertexWithUV(0.4, 0.9, -0.45, 1, 0.2);
		t.draw();
		
		t.startDrawingQuads();
		t.setNormal(0, 1, 0);
		t.addVertexWithUV(-0.4, 0.7, -0.4, 1, 0);
		t.addVertexWithUV(0.4, 0.7, -0.4, 0, 0);
		t.addVertexWithUV(0.4, 0.9, -0.4, 0, 0.2);
		t.addVertexWithUV(-0.4, 0.9, -0.4, 1, 0.2);
		t.draw();
		
		t.startDrawingQuads();
		t.setNormal(0, 1, 0);
		t.addVertexWithUV(-0.4, 0.9, -0.4, 1, 0);
		t.addVertexWithUV(0.4, 0.9, -0.4, 0, 0);
		t.addVertexWithUV(0.4, 0.9, -0.45, 0, 0.2);
		t.addVertexWithUV(-0.4, 0.9, -0.45, 1, 0.2);
		t.draw();
		
		t.startDrawingQuads();
		t.setNormal(0, 1, 0);
		t.addVertexWithUV(0.4, 0.5, -0.4, 1, 0);
		t.addVertexWithUV(0.4, 0.5, -0.45, 0, 0);
		t.addVertexWithUV(0.4, 0.9, -0.45, 0, 0.2);
		t.addVertexWithUV(0.4, 0.9, -0.4, 1, 0.2);
		t.draw();
		
		t.startDrawingQuads();
		t.setNormal(0, 1, 0);
		t.addVertexWithUV(-0.4, 0.5, -0.45, 1, 0);
		t.addVertexWithUV(-0.4, 0.5, -0.4, 0, 0);
		t.addVertexWithUV(-0.4, 0.9, -0.4, 0, 0.2);
		t.addVertexWithUV(-0.4, 0.9, -0.45, 1, 0.2);
		t.draw();
		
		RenderUtils.bindItemTexture();
		Item planItem = NRegistry.plan;
		IIcon iconPlan = planItem.getIcon(new ItemStack(NRegistry.plan), 0);
		t.startDrawingQuads();
		t.setNormal(0, 1, 0);
		t.addVertexWithUV(-0.431, 1.02, 0.431, iconPlan.getMaxU(), iconPlan.getMinV());
		t.addVertexWithUV(0.431, 1.02, 0.431, iconPlan.getMinU(), iconPlan.getMinV());
		t.addVertexWithUV(0.431, 0.72, -0.431, iconPlan.getMinU(), iconPlan.getMaxV());
		t.addVertexWithUV(-0.431, 0.72, -0.431, iconPlan.getMaxU(), iconPlan.getMaxV());
		t.draw();
		
		GL11.glPopMatrix();
		GL11.glPopMatrix();
	}

}
