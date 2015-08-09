package nedelosk.forestday.client.machines.multiblock.charcoalkiln;

import org.lwjgl.opengl.GL11;

import nedelosk.forestday.common.machines.mutiblock.charcoalkiln.TileCharcoalKiln;
import nedelosk.forestday.common.machines.mutiblock.charcoalkiln.WoodType;
import nedelosk.nedeloskcore.utils.RenderUtils;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;

public class TileCharcoalKilnRenderer extends TileEntitySpecialRenderer {

	@Override
	public void renderTileEntityAt(TileEntity tile, double p_147500_2_, double p_147500_4_, double p_147500_6_, float p_147500_8_) {
		renderTileEntityAt((TileCharcoalKiln)tile, p_147500_2_, p_147500_4_, p_147500_6_, p_147500_8_);
	}
	
	public void renderTileEntityAt(TileCharcoalKiln kiln, double x, double y, double z, float p_147500_8_) {
		if(kiln.master != null && kiln.master.isMultiblock || kiln.isMaster && kiln.isMultiblock)
		{
			
			GL11.glPushMatrix();
			GL11.glTranslated((float) x + 0.5F, (float) y + 0.5F, (float) z + 0.5F);
			Tessellator t = Tessellator.instance;
			GL11.glPushMatrix();
			
			GL11.glDisable(GL11.GL_LIGHTING);
			
			RenderUtils.bindBlockTexture();
			
			IIcon dirtIcon = Blocks.dirt.getIcon(0, 0);
			
			if(kiln.master.xCoord == kiln.xCoord && kiln.master.yCoord == kiln.yCoord && kiln.master.zCoord - 1 == kiln.zCoord)
			{
			//Down 0
			t.startDrawingQuads();
			t.setNormal(0, 1, 0);
			t.addVertexWithUV(-0.5, -0.5, -0.5, dirtIcon.getMinU(), dirtIcon.getMinV());
			t.addVertexWithUV(0.5, -0.5, -0.5, dirtIcon.getMaxU(), dirtIcon.getMinV());
			t.addVertexWithUV(0.5, -0.5, 0.5, dirtIcon.getMaxU(), dirtIcon.getMaxV());
			t.addVertexWithUV(-0.5, -0.5, 0.5, dirtIcon.getMinU(), dirtIcon.getMaxV());
			t.draw();
			
		    t.startDrawingQuads();
		    t.setNormal(0, 1, 0);
		    t.addVertexWithUV(-0.5, 0.5, 0.1, dirtIcon.getMinU(), dirtIcon.getMinV());
		    t.addVertexWithUV(-0.5, 0.5, 0.5, dirtIcon.getMaxU(), dirtIcon.getMinV());
		    t.addVertexWithUV(0.5, 0.5, 0.5, dirtIcon.getMaxU(), dirtIcon.getMaxV());
		    t.addVertexWithUV(0.5, 0.5, 0.1, dirtIcon.getMinU(), dirtIcon.getMaxV());
		    t.draw();
			
			t.startDrawingQuads();
			t.setNormal(0, 1, 0);
			t.addVertexWithUV(0.5, 0.5, 0.5, dirtIcon.getMinU(), dirtIcon.getMinV());
			t.addVertexWithUV(0.5, -0.5, 0.5, dirtIcon.getMaxU(), dirtIcon.getMinV());
			t.addVertexWithUV(0.5, -0.5, -0.5, dirtIcon.getMaxU(), dirtIcon.getMaxV());
			t.addVertexWithUV(0.5, 0.5, 0.1, dirtIcon.getMinU(), dirtIcon.getMaxV());
			t.draw();
			
			t.startDrawingQuads();
			t.setNormal(0, 1, 0);
			t.addVertexWithUV(-0.5, 0.5, 0.1, dirtIcon.getMinU(), dirtIcon.getMinV());
			t.addVertexWithUV(-0.5, -0.5, -0.5, dirtIcon.getMaxU(), dirtIcon.getMinV());
			t.addVertexWithUV(-0.5, -0.5, 0.5, dirtIcon.getMaxU(), dirtIcon.getMaxV());
			t.addVertexWithUV(-0.5, 0.5, 0.5, dirtIcon.getMinU(), dirtIcon.getMaxV());
			t.draw();
			
			t.startDrawingQuads();
			t.setNormal(0, 1, 0);
			t.addVertexWithUV(0.5, 0.5, 0.1, dirtIcon.getMinU(), dirtIcon.getMinV());
			t.addVertexWithUV(0.5, -0.5, -0.5, dirtIcon.getMaxU(), dirtIcon.getMinV());
			t.addVertexWithUV(-0.5, -0.5, -0.5, dirtIcon.getMaxU(), dirtIcon.getMaxV());
			t.addVertexWithUV(-0.5, 0.5, 0.1, dirtIcon.getMinU(), dirtIcon.getMaxV());
			t.draw();
			
			t.startDrawingQuads();
			t.setNormal(0, 1, 0);
			t.addVertexWithUV(-0.5, 0.5, 0.5, dirtIcon.getMinU(), dirtIcon.getMinV());
			t.addVertexWithUV(-0.5, -0.5, 0.5, dirtIcon.getMaxU(), dirtIcon.getMinV());
			t.addVertexWithUV(0.5, -0.5, 0.5, dirtIcon.getMaxU(), dirtIcon.getMaxV());
			t.addVertexWithUV(0.5, 0.5, 0.5, dirtIcon.getMinU(), dirtIcon.getMaxV());
			t.draw();
			}
			else if(kiln.master.xCoord == kiln.xCoord && kiln.master.yCoord == kiln.yCoord && kiln.master.zCoord + 1 == kiln.zCoord)
			{
				GL11.glRotated(180, 0F, 0F, 1F);
				GL11.glRotatef(180.0F, 1.0F, 0.0F, 0.0F);
			//Down 0
			t.startDrawingQuads();
			t.setNormal(0, 1, 0);
			t.addVertexWithUV(-0.5, -0.5, -0.5, dirtIcon.getMinU(), dirtIcon.getMinV());
			t.addVertexWithUV(0.5, -0.5, -0.5, dirtIcon.getMaxU(), dirtIcon.getMinV());
			t.addVertexWithUV(0.5, -0.5, 0.5, dirtIcon.getMaxU(), dirtIcon.getMaxV());
			t.addVertexWithUV(-0.5, -0.5, 0.5, dirtIcon.getMinU(), dirtIcon.getMaxV());
			t.draw();
			
		    t.startDrawingQuads();
		    t.setNormal(0, 1, 0);
		    t.addVertexWithUV(-0.5, 0.5, 0.1, dirtIcon.getMinU(), dirtIcon.getMinV());
		    t.addVertexWithUV(-0.5, 0.5, 0.5, dirtIcon.getMaxU(), dirtIcon.getMinV());
		    t.addVertexWithUV(0.5, 0.5, 0.5, dirtIcon.getMaxU(), dirtIcon.getMaxV());
		    t.addVertexWithUV(0.5, 0.5, 0.1, dirtIcon.getMinU(), dirtIcon.getMaxV());
		    t.draw();
			
			t.startDrawingQuads();
			t.setNormal(0, 1, 0);
			t.addVertexWithUV(0.5, 0.5, 0.5, dirtIcon.getMinU(), dirtIcon.getMinV());
			t.addVertexWithUV(0.5, -0.5, 0.5, dirtIcon.getMaxU(), dirtIcon.getMinV());
			t.addVertexWithUV(0.5, -0.5, -0.5, dirtIcon.getMaxU(), dirtIcon.getMaxV());
			t.addVertexWithUV(0.5, 0.5, 0.1, dirtIcon.getMinU(), dirtIcon.getMaxV());
			t.draw();
			
			t.startDrawingQuads();
			t.setNormal(0, 1, 0);
			t.addVertexWithUV(-0.5, 0.5, 0.1, dirtIcon.getMinU(), dirtIcon.getMinV());
			t.addVertexWithUV(-0.5, -0.5, -0.5, dirtIcon.getMaxU(), dirtIcon.getMinV());
			t.addVertexWithUV(-0.5, -0.5, 0.5, dirtIcon.getMaxU(), dirtIcon.getMaxV());
			t.addVertexWithUV(-0.5, 0.5, 0.5, dirtIcon.getMinU(), dirtIcon.getMaxV());
			t.draw();
			
			t.startDrawingQuads();
			t.setNormal(0, 1, 0);
			t.addVertexWithUV(0.5, 0.5, 0.1, dirtIcon.getMinU(), dirtIcon.getMinV());
			t.addVertexWithUV(0.5, -0.5, -0.5, dirtIcon.getMaxU(), dirtIcon.getMinV());
			t.addVertexWithUV(-0.5, -0.5, -0.5, dirtIcon.getMaxU(), dirtIcon.getMaxV());
			t.addVertexWithUV(-0.5, 0.5, 0.1, dirtIcon.getMinU(), dirtIcon.getMaxV());
			t.draw();
			
			t.startDrawingQuads();
			t.setNormal(0, 1, 0);
			t.addVertexWithUV(-0.5, 0.5, 0.5, dirtIcon.getMinU(), dirtIcon.getMinV());
			t.addVertexWithUV(-0.5, -0.5, 0.5, dirtIcon.getMaxU(), dirtIcon.getMinV());
			t.addVertexWithUV(0.5, -0.5, 0.5, dirtIcon.getMaxU(), dirtIcon.getMaxV());
			t.addVertexWithUV(0.5, 0.5, 0.5, dirtIcon.getMinU(), dirtIcon.getMaxV());
			t.draw();
			}
			else if(kiln.master.xCoord - 1 == kiln.xCoord && kiln.master.yCoord == kiln.yCoord && kiln.master.zCoord == kiln.zCoord)
			{
				GL11.glRotatef(90.0F, 0.0F, 1.0F, 0.0F);
			//Down 0
			t.startDrawingQuads();
			t.setNormal(0, 1, 0);
			t.addVertexWithUV(-0.5, -0.5, -0.5, dirtIcon.getMinU(), dirtIcon.getMinV());
			t.addVertexWithUV(0.5, -0.5, -0.5, dirtIcon.getMaxU(), dirtIcon.getMinV());
			t.addVertexWithUV(0.5, -0.5, 0.5, dirtIcon.getMaxU(), dirtIcon.getMaxV());
			t.addVertexWithUV(-0.5, -0.5, 0.5, dirtIcon.getMinU(), dirtIcon.getMaxV());
			t.draw();
			
		    t.startDrawingQuads();
		    t.setNormal(0, 1, 0);
		    t.addVertexWithUV(-0.5, 0.5, 0.1, dirtIcon.getMinU(), dirtIcon.getMinV());
		    t.addVertexWithUV(-0.5, 0.5, 0.5, dirtIcon.getMaxU(), dirtIcon.getMinV());
		    t.addVertexWithUV(0.5, 0.5, 0.5, dirtIcon.getMaxU(), dirtIcon.getMaxV());
		    t.addVertexWithUV(0.5, 0.5, 0.1, dirtIcon.getMinU(), dirtIcon.getMaxV());
		    t.draw();
			
			t.startDrawingQuads();
			t.setNormal(0, 1, 0);
			t.addVertexWithUV(0.5, 0.5, 0.5, dirtIcon.getMinU(), dirtIcon.getMinV());
			t.addVertexWithUV(0.5, -0.5, 0.5, dirtIcon.getMaxU(), dirtIcon.getMinV());
			t.addVertexWithUV(0.5, -0.5, -0.5, dirtIcon.getMaxU(), dirtIcon.getMaxV());
			t.addVertexWithUV(0.5, 0.5, 0.1, dirtIcon.getMinU(), dirtIcon.getMaxV());
			t.draw();
			
			t.startDrawingQuads();
			t.setNormal(0, 1, 0);
			t.addVertexWithUV(-0.5, 0.5, 0.1, dirtIcon.getMinU(), dirtIcon.getMinV());
			t.addVertexWithUV(-0.5, -0.5, -0.5, dirtIcon.getMaxU(), dirtIcon.getMinV());
			t.addVertexWithUV(-0.5, -0.5, 0.5, dirtIcon.getMaxU(), dirtIcon.getMaxV());
			t.addVertexWithUV(-0.5, 0.5, 0.5, dirtIcon.getMinU(), dirtIcon.getMaxV());
			t.draw();
			
			t.startDrawingQuads();
			t.setNormal(0, 1, 0);
			t.addVertexWithUV(0.5, 0.5, 0.1, dirtIcon.getMinU(), dirtIcon.getMinV());
			t.addVertexWithUV(0.5, -0.5, -0.5, dirtIcon.getMaxU(), dirtIcon.getMinV());
			t.addVertexWithUV(-0.5, -0.5, -0.5, dirtIcon.getMaxU(), dirtIcon.getMaxV());
			t.addVertexWithUV(-0.5, 0.5, 0.1, dirtIcon.getMinU(), dirtIcon.getMaxV());
			t.draw();
			
			t.startDrawingQuads();
			t.setNormal(0, 1, 0);
			t.addVertexWithUV(-0.5, 0.5, 0.5, dirtIcon.getMinU(), dirtIcon.getMinV());
			t.addVertexWithUV(-0.5, -0.5, 0.5, dirtIcon.getMaxU(), dirtIcon.getMinV());
			t.addVertexWithUV(0.5, -0.5, 0.5, dirtIcon.getMaxU(), dirtIcon.getMaxV());
			t.addVertexWithUV(0.5, 0.5, 0.5, dirtIcon.getMinU(), dirtIcon.getMaxV());
			t.draw();
			}
			else if(kiln.master.xCoord + 1 == kiln.xCoord && kiln.master.yCoord == kiln.yCoord && kiln.master.zCoord == kiln.zCoord)
			{
				GL11.glRotated(180, 0F, 1F, 0F);
				GL11.glRotatef(90.0F, 0.0F, 1.0F, 0.0F);
			//Down 0
			t.startDrawingQuads();
			t.setNormal(0, 1, 0);
			t.addVertexWithUV(-0.5, -0.5, -0.5, dirtIcon.getMinU(), dirtIcon.getMinV());
			t.addVertexWithUV(0.5, -0.5, -0.5, dirtIcon.getMaxU(), dirtIcon.getMinV());
			t.addVertexWithUV(0.5, -0.5, 0.5, dirtIcon.getMaxU(), dirtIcon.getMaxV());
			t.addVertexWithUV(-0.5, -0.5, 0.5, dirtIcon.getMinU(), dirtIcon.getMaxV());
			t.draw();
			
		    t.startDrawingQuads();
		    t.setNormal(0, 1, 0);
		    t.addVertexWithUV(-0.5, 0.5, 0.1, dirtIcon.getMinU(), dirtIcon.getMinV());
		    t.addVertexWithUV(-0.5, 0.5, 0.5, dirtIcon.getMaxU(), dirtIcon.getMinV());
		    t.addVertexWithUV(0.5, 0.5, 0.5, dirtIcon.getMaxU(), dirtIcon.getMaxV());
		    t.addVertexWithUV(0.5, 0.5, 0.1, dirtIcon.getMinU(), dirtIcon.getMaxV());
		    t.draw();
			
			t.startDrawingQuads();
			t.setNormal(0, 1, 0);
			t.addVertexWithUV(0.5, 0.5, 0.5, dirtIcon.getMinU(), dirtIcon.getMinV());
			t.addVertexWithUV(0.5, -0.5, 0.5, dirtIcon.getMaxU(), dirtIcon.getMinV());
			t.addVertexWithUV(0.5, -0.5, -0.5, dirtIcon.getMaxU(), dirtIcon.getMaxV());
			t.addVertexWithUV(0.5, 0.5, 0.1, dirtIcon.getMinU(), dirtIcon.getMaxV());
			t.draw();
			
			t.startDrawingQuads();
			t.setNormal(0, 1, 0);
			t.addVertexWithUV(-0.5, 0.5, 0.1, dirtIcon.getMinU(), dirtIcon.getMinV());
			t.addVertexWithUV(-0.5, -0.5, -0.5, dirtIcon.getMaxU(), dirtIcon.getMinV());
			t.addVertexWithUV(-0.5, -0.5, 0.5, dirtIcon.getMaxU(), dirtIcon.getMaxV());
			t.addVertexWithUV(-0.5, 0.5, 0.5, dirtIcon.getMinU(), dirtIcon.getMaxV());
			t.draw();
			
			t.startDrawingQuads();
			t.setNormal(0, 1, 0);
			t.addVertexWithUV(0.5, 0.5, 0.1, dirtIcon.getMinU(), dirtIcon.getMinV());
			t.addVertexWithUV(0.5, -0.5, -0.5, dirtIcon.getMaxU(), dirtIcon.getMinV());
			t.addVertexWithUV(-0.5, -0.5, -0.5, dirtIcon.getMaxU(), dirtIcon.getMaxV());
			t.addVertexWithUV(-0.5, 0.5, 0.1, dirtIcon.getMinU(), dirtIcon.getMaxV());
			t.draw();
			
			t.startDrawingQuads();
			t.setNormal(0, 1, 0);
			t.addVertexWithUV(-0.5, 0.5, 0.5, dirtIcon.getMinU(), dirtIcon.getMinV());
			t.addVertexWithUV(-0.5, -0.5, 0.5, dirtIcon.getMaxU(), dirtIcon.getMinV());
			t.addVertexWithUV(0.5, -0.5, 0.5, dirtIcon.getMaxU(), dirtIcon.getMaxV());
			t.addVertexWithUV(0.5, 0.5, 0.5, dirtIcon.getMinU(), dirtIcon.getMaxV());
			t.draw();
			}
			else if(kiln.master.xCoord + 1 == kiln.xCoord && kiln.master.yCoord == kiln.yCoord && kiln.master.zCoord + 1 == kiln.zCoord)
			{
				GL11.glRotated(180, 0F, 1F, 0F);
				GL11.glRotatef(90.0F, 0.0F, 1.0F, 0.0F);
			//Down 0
			t.startDrawingQuads();
			t.setNormal(0, 1, 0);
			t.addVertexWithUV(-0.5, -0.5, -0.5, dirtIcon.getMinU(), dirtIcon.getMinV());
			t.addVertexWithUV(0.5, -0.5, -0.5, dirtIcon.getMaxU(), dirtIcon.getMinV());
			t.addVertexWithUV(0.5, -0.5, 0.5, dirtIcon.getMaxU(), dirtIcon.getMaxV());
			t.addVertexWithUV(-0.5, -0.5, 0.5, dirtIcon.getMinU(), dirtIcon.getMaxV());
			t.draw();
			
		    t.startDrawingQuads();
		    t.setNormal(0, 1, 0);
		    t.addVertexWithUV(-0.5, 0.5, 0.1, dirtIcon.getMinU(), dirtIcon.getMinV());
		    t.addVertexWithUV(-0.5, 0.5, 0.5, dirtIcon.getMaxU(), dirtIcon.getMinV());
		    t.addVertexWithUV(-0.1, 0.5, 0.5, dirtIcon.getMaxU(), dirtIcon.getMaxV());
		    t.addVertexWithUV(-0.1, 0.5, 0.1, dirtIcon.getMinU(), dirtIcon.getMaxV());
		    t.draw();
			
			t.startDrawingQuads();
			t.setNormal(0, 1, 0);
			t.addVertexWithUV(-0.1, 0.5, 0.5, dirtIcon.getMinU(), dirtIcon.getMinV());
			t.addVertexWithUV(0.5, -0.5, 0.5, dirtIcon.getMaxU(), dirtIcon.getMinV());
			t.addVertexWithUV(0.5, -0.5, -0.5, dirtIcon.getMaxU(), dirtIcon.getMaxV());
			t.addVertexWithUV(-0.1, 0.5, 0.1, dirtIcon.getMinU(), dirtIcon.getMaxV());
			t.draw();
			
			t.startDrawingQuads();
			t.setNormal(0, 1, 0);
			t.addVertexWithUV(-0.5, 0.5, 0.1, dirtIcon.getMinU(), dirtIcon.getMinV());
			t.addVertexWithUV(-0.5, -0.5, -0.5, dirtIcon.getMaxU(), dirtIcon.getMinV());
			t.addVertexWithUV(-0.5, -0.5, 0.5, dirtIcon.getMaxU(), dirtIcon.getMaxV());
			t.addVertexWithUV(-0.5, 0.5, 0.5, dirtIcon.getMinU(), dirtIcon.getMaxV());
			t.draw();
			
			t.startDrawingQuads();
			t.setNormal(0, 1, 0);
			t.addVertexWithUV(-0.1, 0.5, 0.1, dirtIcon.getMinU(), dirtIcon.getMinV());
			t.addVertexWithUV(0.5, -0.5, -0.5, dirtIcon.getMaxU(), dirtIcon.getMinV());
			t.addVertexWithUV(-0.5, -0.5, -0.5, dirtIcon.getMaxU(), dirtIcon.getMaxV());
			t.addVertexWithUV(-0.5, 0.5, 0.1, dirtIcon.getMinU(), dirtIcon.getMaxV());
			t.draw();
			
			}
			else if(kiln.master.xCoord - 1 == kiln.xCoord && kiln.master.yCoord == kiln.yCoord && kiln.master.zCoord + 1 == kiln.zCoord)
			{
				GL11.glRotated(90, 0F, 1F, 0F);
				GL11.glRotatef(90.0F, 0.0F, 1.0F, 0.0F);
			//Down 0
			t.startDrawingQuads();
			t.setNormal(0, 1, 0);
			t.addVertexWithUV(-0.5, -0.5, -0.5, dirtIcon.getMinU(), dirtIcon.getMinV());
			t.addVertexWithUV(0.5, -0.5, -0.5, dirtIcon.getMaxU(), dirtIcon.getMinV());
			t.addVertexWithUV(0.5, -0.5, 0.5, dirtIcon.getMaxU(), dirtIcon.getMaxV());
			t.addVertexWithUV(-0.5, -0.5, 0.5, dirtIcon.getMinU(), dirtIcon.getMaxV());
			t.draw();
			
			t.startDrawingQuads();
			t.setNormal(0, 1, 0);
			t.addVertexWithUV(-0.1, 0.5, 0.5, dirtIcon.getMinU(), dirtIcon.getMinV());
			t.addVertexWithUV(0.5, -0.5, 0.5, dirtIcon.getMaxU(), dirtIcon.getMinV());
			t.addVertexWithUV(0.5, -0.5, -0.5, dirtIcon.getMaxU(), dirtIcon.getMaxV());
			t.addVertexWithUV(-0.1, 0.5, 0.1, dirtIcon.getMinU(), dirtIcon.getMaxV());
			t.draw();
			
			t.startDrawingQuads();
			t.setNormal(0, 1, 0);
			t.addVertexWithUV(-0.5, 0.5, 0.1, dirtIcon.getMinU(), dirtIcon.getMinV());
			t.addVertexWithUV(-0.5, -0.5, -0.5, dirtIcon.getMaxU(), dirtIcon.getMinV());
			t.addVertexWithUV(-0.5, -0.5, 0.5, dirtIcon.getMaxU(), dirtIcon.getMaxV());
			t.addVertexWithUV(-0.5, 0.5, 0.5, dirtIcon.getMinU(), dirtIcon.getMaxV());
			t.draw();
			
			t.startDrawingQuads();
			t.setNormal(0, 1, 0);
			t.addVertexWithUV(-0.1, 0.5, 0.1, dirtIcon.getMinU(), dirtIcon.getMinV());
			t.addVertexWithUV(0.5, -0.5, -0.5, dirtIcon.getMaxU(), dirtIcon.getMinV());
			t.addVertexWithUV(-0.5, -0.5, -0.5, dirtIcon.getMaxU(), dirtIcon.getMaxV());
			t.addVertexWithUV(-0.5, 0.5, 0.1, dirtIcon.getMinU(), dirtIcon.getMaxV());
			t.draw();
			
			}
			else if(kiln.master.xCoord - 1 == kiln.xCoord && kiln.master.yCoord == kiln.yCoord && kiln.master.zCoord - 1 == kiln.zCoord)
			{
				GL11.glRotatef(90.0F, 0.0F, 1.0F, 0.0F);
			//Down 0
			t.startDrawingQuads();
			t.setNormal(0, 1, 0);
			t.addVertexWithUV(-0.5, -0.5, -0.5, dirtIcon.getMinU(), dirtIcon.getMinV());
			t.addVertexWithUV(0.5, -0.5, -0.5, dirtIcon.getMaxU(), dirtIcon.getMinV());
			t.addVertexWithUV(0.5, -0.5, 0.5, dirtIcon.getMaxU(), dirtIcon.getMaxV());
			t.addVertexWithUV(-0.5, -0.5, 0.5, dirtIcon.getMinU(), dirtIcon.getMaxV());
			t.draw();
			
			t.startDrawingQuads();
			t.setNormal(0, 1, 0);
			t.addVertexWithUV(-0.1, 0.5, 0.5, dirtIcon.getMinU(), dirtIcon.getMinV());
			t.addVertexWithUV(0.5, -0.5, 0.5, dirtIcon.getMaxU(), dirtIcon.getMinV());
			t.addVertexWithUV(0.5, -0.5, -0.5, dirtIcon.getMaxU(), dirtIcon.getMaxV());
			t.addVertexWithUV(-0.1, 0.5, 0.1, dirtIcon.getMinU(), dirtIcon.getMaxV());
			t.draw();
			
			t.startDrawingQuads();
			t.setNormal(0, 1, 0);
			t.addVertexWithUV(-0.5, 0.5, 0.1, dirtIcon.getMinU(), dirtIcon.getMinV());
			t.addVertexWithUV(-0.5, -0.5, -0.5, dirtIcon.getMaxU(), dirtIcon.getMinV());
			t.addVertexWithUV(-0.5, -0.5, 0.5, dirtIcon.getMaxU(), dirtIcon.getMaxV());
			t.addVertexWithUV(-0.5, 0.5, 0.5, dirtIcon.getMinU(), dirtIcon.getMaxV());
			t.draw();
			
			t.startDrawingQuads();
			t.setNormal(0, 1, 0);
			t.addVertexWithUV(-0.1, 0.5, 0.1, dirtIcon.getMinU(), dirtIcon.getMinV());
			t.addVertexWithUV(0.5, -0.5, -0.5, dirtIcon.getMaxU(), dirtIcon.getMinV());
			t.addVertexWithUV(-0.5, -0.5, -0.5, dirtIcon.getMaxU(), dirtIcon.getMaxV());
			t.addVertexWithUV(-0.5, 0.5, 0.1, dirtIcon.getMinU(), dirtIcon.getMaxV());
			t.draw();
			
			}
			else if(kiln.master.xCoord + 1 == kiln.xCoord && kiln.master.yCoord == kiln.yCoord && kiln.master.zCoord - 1 == kiln.zCoord)
			{
				GL11.glRotated(270, 0F, 1F, 0F);
				GL11.glRotatef(90.0F, 0.0F, 1.0F, 0.0F);
			//Down 0
			t.startDrawingQuads();
			t.setNormal(0, 1, 0);
			t.addVertexWithUV(-0.5, -0.5, -0.5, dirtIcon.getMinU(), dirtIcon.getMinV());
			t.addVertexWithUV(0.5, -0.5, -0.5, dirtIcon.getMaxU(), dirtIcon.getMinV());
			t.addVertexWithUV(0.5, -0.5, 0.5, dirtIcon.getMaxU(), dirtIcon.getMaxV());
			t.addVertexWithUV(-0.5, -0.5, 0.5, dirtIcon.getMinU(), dirtIcon.getMaxV());
			t.draw();
			
			t.startDrawingQuads();
			t.setNormal(0, 1, 0);
			t.addVertexWithUV(-0.1, 0.5, 0.5, dirtIcon.getMinU(), dirtIcon.getMinV());
			t.addVertexWithUV(0.5, -0.5, 0.5, dirtIcon.getMaxU(), dirtIcon.getMinV());
			t.addVertexWithUV(0.5, -0.5, -0.5, dirtIcon.getMaxU(), dirtIcon.getMaxV());
			t.addVertexWithUV(-0.1, 0.5, 0.1, dirtIcon.getMinU(), dirtIcon.getMaxV());
			t.draw();
			
			t.startDrawingQuads();
			t.setNormal(0, 1, 0);
			t.addVertexWithUV(-0.5, 0.5, 0.1, dirtIcon.getMinU(), dirtIcon.getMinV());
			t.addVertexWithUV(-0.5, -0.5, -0.5, dirtIcon.getMaxU(), dirtIcon.getMinV());
			t.addVertexWithUV(-0.5, -0.5, 0.5, dirtIcon.getMaxU(), dirtIcon.getMaxV());
			t.addVertexWithUV(-0.5, 0.5, 0.5, dirtIcon.getMinU(), dirtIcon.getMaxV());
			t.draw();
			
			t.startDrawingQuads();
			t.setNormal(0, 1, 0);
			t.addVertexWithUV(-0.1, 0.5, 0.1, dirtIcon.getMinU(), dirtIcon.getMinV());
			t.addVertexWithUV(0.5, -0.5, -0.5, dirtIcon.getMaxU(), dirtIcon.getMinV());
			t.addVertexWithUV(-0.5, -0.5, -0.5, dirtIcon.getMaxU(), dirtIcon.getMaxV());
			t.addVertexWithUV(-0.5, 0.5, 0.1, dirtIcon.getMinU(), dirtIcon.getMaxV());
			t.draw();
			
			}
			else if(kiln.master.xCoord + 1 == kiln.xCoord && kiln.master.yCoord + 1 == kiln.yCoord && kiln.master.zCoord - 1 == kiln.zCoord)
			{
				GL11.glRotated(270, 0F, 1F, 0F);
				GL11.glRotatef(90.0F, 0.0F, 1.0F, 0.0F);
				
			//Down 0
			
			t.startDrawingQuads();
			t.setNormal(0, 1, 0);
			t.addVertexWithUV(-0.5, 0.1, 0.5, dirtIcon.getMinU(), dirtIcon.getMinV());
			t.addVertexWithUV(-0.1, -0.5, 0.5, dirtIcon.getMaxU(), dirtIcon.getMinV());
			t.addVertexWithUV(-0.1, -0.5, 0.1, dirtIcon.getMaxU(), dirtIcon.getMaxV());
			t.addVertexWithUV(-0.5, 0.1, 0.5, dirtIcon.getMinU(), dirtIcon.getMaxV());
			t.draw();
			
			t.startDrawingQuads();
			t.setNormal(0, 1, 0);
			t.addVertexWithUV(-0.5, 0.1, 0.5, dirtIcon.getMinU(), dirtIcon.getMinV());
			t.addVertexWithUV(-0.1, -0.5, 0.1, dirtIcon.getMaxU(), dirtIcon.getMinV());
			t.addVertexWithUV(-0.5, -0.5, 0.1, dirtIcon.getMaxU(), dirtIcon.getMaxV());
			t.addVertexWithUV(-0.5, 0.1, 0.5, dirtIcon.getMinU(), dirtIcon.getMaxV());
			t.draw();
			
			}
			else if(kiln.master.xCoord + 1 == kiln.xCoord && kiln.master.yCoord + 1 == kiln.yCoord && kiln.master.zCoord + 1 == kiln.zCoord)
			{
				GL11.glRotated(180, 0F, 1F, 0F);
				GL11.glRotatef(90.0F, 0.0F, 1.0F, 0.0F);
				
			//Down 0
			
			t.startDrawingQuads();
			t.setNormal(0, 1, 0);
			t.addVertexWithUV(-0.5, 0.1, 0.5, dirtIcon.getMinU(), dirtIcon.getMinV());
			t.addVertexWithUV(-0.1, -0.5, 0.5, dirtIcon.getMaxU(), dirtIcon.getMinV());
			t.addVertexWithUV(-0.1, -0.5, 0.1, dirtIcon.getMaxU(), dirtIcon.getMaxV());
			t.addVertexWithUV(-0.5, 0.1, 0.5, dirtIcon.getMinU(), dirtIcon.getMaxV());
			t.draw();
			
			t.startDrawingQuads();
			t.setNormal(0, 1, 0);
			t.addVertexWithUV(-0.5, 0.1, 0.5, dirtIcon.getMinU(), dirtIcon.getMinV());
			t.addVertexWithUV(-0.1, -0.5, 0.1, dirtIcon.getMaxU(), dirtIcon.getMinV());
			t.addVertexWithUV(-0.5, -0.5, 0.1, dirtIcon.getMaxU(), dirtIcon.getMaxV());
			t.addVertexWithUV(-0.5, 0.1, 0.5, dirtIcon.getMinU(), dirtIcon.getMaxV());
			t.draw();
			
			}
			else if(kiln.master.xCoord - 1 == kiln.xCoord && kiln.master.yCoord + 1 == kiln.yCoord && kiln.master.zCoord - 1 == kiln.zCoord)
			{
				GL11.glRotatef(90.0F, 0.0F, 1.0F, 0.0F);
				
			//Down 0
			
			t.startDrawingQuads();
			t.setNormal(0, 1, 0);
			t.addVertexWithUV(-0.5, 0.1, 0.5, dirtIcon.getMinU(), dirtIcon.getMinV());
			t.addVertexWithUV(-0.1, -0.5, 0.5, dirtIcon.getMaxU(), dirtIcon.getMinV());
			t.addVertexWithUV(-0.1, -0.5, 0.1, dirtIcon.getMaxU(), dirtIcon.getMaxV());
			t.addVertexWithUV(-0.5, 0.1, 0.5, dirtIcon.getMinU(), dirtIcon.getMaxV());
			t.draw();
			
			t.startDrawingQuads();
			t.setNormal(0, 1, 0);
			t.addVertexWithUV(-0.5, 0.1, 0.5, dirtIcon.getMinU(), dirtIcon.getMinV());
			t.addVertexWithUV(-0.1, -0.5, 0.1, dirtIcon.getMaxU(), dirtIcon.getMinV());
			t.addVertexWithUV(-0.5, -0.5, 0.1, dirtIcon.getMaxU(), dirtIcon.getMaxV());
			t.addVertexWithUV(-0.5, 0.1, 0.5, dirtIcon.getMinU(), dirtIcon.getMaxV());
			t.draw();
			
			}
			else if(kiln.master.xCoord - 1 == kiln.xCoord && kiln.master.yCoord + 1 == kiln.yCoord && kiln.master.zCoord + 1 == kiln.zCoord)
			{
				GL11.glRotated(90, 0F, 1F, 0F);
				GL11.glRotatef(90.0F, 0.0F, 1.0F, 0.0F);
				
			//Down 0
			
			t.startDrawingQuads();
			t.setNormal(0, 1, 0);
			t.addVertexWithUV(-0.5, 0.1, 0.5, dirtIcon.getMinU(), dirtIcon.getMinV());
			t.addVertexWithUV(-0.1, -0.5, 0.5, dirtIcon.getMaxU(), dirtIcon.getMinV());
			t.addVertexWithUV(-0.1, -0.5, 0.1, dirtIcon.getMaxU(), dirtIcon.getMaxV());
			t.addVertexWithUV(-0.5, 0.1, 0.5, dirtIcon.getMinU(), dirtIcon.getMaxV());
			t.draw();
			
			t.startDrawingQuads();
			t.setNormal(0, 1, 0);
			t.addVertexWithUV(-0.5, 0.1, 0.5, dirtIcon.getMinU(), dirtIcon.getMinV());
			t.addVertexWithUV(-0.1, -0.5, 0.1, dirtIcon.getMaxU(), dirtIcon.getMinV());
			t.addVertexWithUV(-0.5, -0.5, 0.1, dirtIcon.getMaxU(), dirtIcon.getMaxV());
			t.addVertexWithUV(-0.5, 0.1, 0.5, dirtIcon.getMinU(), dirtIcon.getMaxV());
			t.draw();
			
			}
			else if(kiln.master.xCoord == kiln.xCoord && kiln.master.yCoord + 1 == kiln.yCoord && kiln.master.zCoord + 1 == kiln.zCoord)
			{
				GL11.glRotated(180, 0F, 0F, 1F);
				GL11.glRotatef(180.0F, 1.0F, 0.0F, 0.0F);
			//Down 0
			
			t.startDrawingQuads();
			t.setNormal(0, 1, 0);
			t.addVertexWithUV(0.5, 0.1, 0.5, dirtIcon.getMinU(), dirtIcon.getMinV());
			t.addVertexWithUV(0.5, -0.5, 0.1, dirtIcon.getMaxU(), dirtIcon.getMinV());
			t.addVertexWithUV(-0.5, -0.5, 0.1, dirtIcon.getMaxU(), dirtIcon.getMaxV());
			t.addVertexWithUV(-0.5, 0.1, 0.5, dirtIcon.getMinU(), dirtIcon.getMaxV());
			t.draw();
			
			}
			else if(kiln.master.xCoord == kiln.xCoord && kiln.master.yCoord + 1 == kiln.yCoord && kiln.master.zCoord - 1 == kiln.zCoord)
			{
				GL11.glRotated(270, 0F, 1F, 0F);
				GL11.glRotatef(90.0F, 0.0F, 1.0F, 0.0F);
			//Down 0
			
			t.startDrawingQuads();
			t.setNormal(0, 1, 0);
			t.addVertexWithUV(0.5, 0.1, 0.5, dirtIcon.getMinU(), dirtIcon.getMinV());
			t.addVertexWithUV(0.5, -0.5, 0.1, dirtIcon.getMaxU(), dirtIcon.getMinV());
			t.addVertexWithUV(-0.5, -0.5, 0.1, dirtIcon.getMaxU(), dirtIcon.getMaxV());
			t.addVertexWithUV(-0.5, 0.1, 0.5, dirtIcon.getMinU(), dirtIcon.getMaxV());
			t.draw();
			
			}
			else if(kiln.master.xCoord + 1 == kiln.xCoord && kiln.master.yCoord + 1 == kiln.yCoord && kiln.master.zCoord == kiln.zCoord)
			{
				GL11.glRotated(180, 0F, 1F, 0F);
				GL11.glRotatef(90.0F, 0.0F, 1.0F, 0.0F);
			//Down 0
			
			t.startDrawingQuads();
			t.setNormal(0, 1, 0);
			t.addVertexWithUV(0.5, 0.1, 0.5, dirtIcon.getMinU(), dirtIcon.getMinV());
			t.addVertexWithUV(0.5, -0.5, 0.1, dirtIcon.getMaxU(), dirtIcon.getMinV());
			t.addVertexWithUV(-0.5, -0.5, 0.1, dirtIcon.getMaxU(), dirtIcon.getMaxV());
			t.addVertexWithUV(-0.5, 0.1, 0.5, dirtIcon.getMinU(), dirtIcon.getMaxV());
			t.draw();
			
			}
			else if(kiln.master.xCoord - 1 == kiln.xCoord && kiln.master.yCoord + 1 == kiln.yCoord && kiln.master.zCoord == kiln.zCoord)
			{
				GL11.glRotatef(90.0F, 0.0F, 1.0F, 0.0F);
			//Down 0
			
			t.startDrawingQuads();
			t.setNormal(0, 1, 0);
			t.addVertexWithUV(0.5, 0.1, 0.5, dirtIcon.getMinU(), dirtIcon.getMinV());
			t.addVertexWithUV(0.5, -0.5, 0.1, dirtIcon.getMaxU(), dirtIcon.getMinV());
			t.addVertexWithUV(-0.5, -0.5, 0.1, dirtIcon.getMaxU(), dirtIcon.getMaxV());
			t.addVertexWithUV(-0.5, 0.1, 0.5, dirtIcon.getMinU(), dirtIcon.getMaxV());
			t.draw();
			
			}
			else if(kiln.master.xCoord == kiln.xCoord && kiln.master.yCoord == kiln.yCoord && kiln.master.zCoord == kiln.zCoord)
			{
			//Down 0
			
				t.startDrawingQuads();
				t.setNormal(0, 1, 0);
				t.addVertexWithUV(-0.5, -0.5, -0.5, dirtIcon.getMinU(), dirtIcon.getMinV());
				t.addVertexWithUV(0.5, -0.5, -0.5, dirtIcon.getMaxU(), dirtIcon.getMinV());
				t.addVertexWithUV(0.5, -0.5, 0.5, dirtIcon.getMaxU(), dirtIcon.getMaxV());
				t.addVertexWithUV(-0.5, -0.5, 0.5, dirtIcon.getMinU(), dirtIcon.getMaxV());
				t.draw();
			
			}
			else if(kiln.master.xCoord == kiln.xCoord && kiln.master.yCoord + 1 == kiln.yCoord && kiln.master.zCoord == kiln.zCoord)
			{
			//Down 0
			
			    t.startDrawingQuads();
			    t.setNormal(0, 1, 0);
			    t.addVertexWithUV(-0.3, 0.5, -0.3, dirtIcon.getMinU(), dirtIcon.getMinV());
			    t.addVertexWithUV(-0.3, 0.5, 0.3, dirtIcon.getMaxU(), dirtIcon.getMinV());
			    t.addVertexWithUV(0.3, 0.5, 0.3, dirtIcon.getMaxU(), dirtIcon.getMaxV());
			    t.addVertexWithUV(0.3, 0.5, -0.3, dirtIcon.getMinU(), dirtIcon.getMaxV());
			    t.draw();
			    
				t.startDrawingQuads();
				t.setNormal(0, 1, 0);
				t.addVertexWithUV(0.3, 0.5, 0.3, dirtIcon.getMinU(), dirtIcon.getMinV());
				t.addVertexWithUV(0.5, 0.1, 0.5, dirtIcon.getMaxU(), dirtIcon.getMinV());
				t.addVertexWithUV(0.5, 0.1, -0.5, dirtIcon.getMaxU(), dirtIcon.getMaxV());
				t.addVertexWithUV(0.3, 0.5, -0.3, dirtIcon.getMinU(), dirtIcon.getMaxV());
				t.draw();
				
				t.startDrawingQuads();
				t.setNormal(0, 1, 0);
				t.addVertexWithUV(-0.3, 0.5, -0.3, dirtIcon.getMinU(), dirtIcon.getMinV());
				t.addVertexWithUV(-0.5, 0.1, -0.5, dirtIcon.getMaxU(), dirtIcon.getMinV());
				t.addVertexWithUV(-0.5, 0.1, 0.5, dirtIcon.getMaxU(), dirtIcon.getMaxV());
				t.addVertexWithUV(-0.3, 0.5, 0.3, dirtIcon.getMinU(), dirtIcon.getMaxV());
				t.draw();
				
				t.startDrawingQuads();
				t.setNormal(0, 1, 0);
				t.addVertexWithUV(0.3, 0.5, -0.3, dirtIcon.getMinU(), dirtIcon.getMinV());
				t.addVertexWithUV(0.5, 0.1, -0.5, dirtIcon.getMaxU(), dirtIcon.getMinV());
				t.addVertexWithUV(-0.5, 0.1, -0.5, dirtIcon.getMaxU(), dirtIcon.getMaxV());
				t.addVertexWithUV(-0.3, 0.5, -0.3, dirtIcon.getMinU(), dirtIcon.getMaxV());
				t.draw();
				
				t.startDrawingQuads();
				t.setNormal(0, 1, 0);
				t.addVertexWithUV(-0.3, 0.5, 0.3, dirtIcon.getMinU(), dirtIcon.getMinV());
				t.addVertexWithUV(-0.5, 0.1, 0.5, dirtIcon.getMaxU(), dirtIcon.getMinV());
				t.addVertexWithUV(0.5, 0.1, 0.5, dirtIcon.getMaxU(), dirtIcon.getMaxV());
				t.addVertexWithUV(0.3, 0.5, 0.3, dirtIcon.getMinU(), dirtIcon.getMaxV());
				t.draw();
			
			}
			
			GL11.glEnable(GL11.GL_LIGHTING);
			GL11.glPopMatrix();
			GL11.glPopMatrix();
		}
		else
		{
			if(kiln.type != null)
			{
			GL11.glPushMatrix();
			GL11.glTranslated((float) x + 0.5F, (float) y + 0.5F, (float) z + 0.5F);
			Tessellator t = Tessellator.instance;
			GL11.glPushMatrix();
			
			GL11.glDisable(GL11.GL_LIGHTING);
			
			RenderUtils.bindBlockTexture();
			
			IIcon woodIconTop = Block.getBlockFromItem(kiln.type.wood.getItem()).getIcon(0, kiln.type.wood.getItemDamage());
			IIcon woodIcon = Block.getBlockFromItem(kiln.type.wood.getItem()).getIcon(2, kiln.type.wood.getItemDamage());;
			
			t.startDrawingQuads();
			t.setNormal(0, 1, 0);
			t.addVertexWithUV(-0.5, -0.5, -0.5, woodIconTop.getMinU(), woodIconTop.getMinV());
			t.addVertexWithUV(0.5, -0.5, -0.5, woodIconTop.getMaxU(), woodIconTop.getMinV());
			t.addVertexWithUV(0.5, -0.5, 0.5, woodIconTop.getMaxU(), woodIconTop.getMaxV());
			t.addVertexWithUV(-0.5, -0.5, 0.5, woodIconTop.getMinU(), woodIconTop.getMaxV());
			t.draw();
			
		    t.startDrawingQuads();
		    t.setNormal(0, 1, 0);
		    t.addVertexWithUV(-0.5, 0.5, -0.5, woodIconTop.getMinU(), woodIconTop.getMinV());
		    t.addVertexWithUV(-0.5, 0.5, 0.5, woodIconTop.getMinU(), woodIconTop.getMaxV());
		    t.addVertexWithUV(0.5, 0.5, 0.5, woodIconTop.getMaxU(), woodIconTop.getMaxV());
		    t.addVertexWithUV(0.5, 0.5, -0.5, woodIconTop.getMaxU(), woodIconTop.getMinV());
		    t.draw();
			
			t.startDrawingQuads();
			t.setNormal(0, 1, 0);
			t.addVertexWithUV(0.5, 0.5, 0.5, woodIcon.getMinU(), woodIcon.getMinV());
			t.addVertexWithUV(0.5, -0.5, 0.5, woodIcon.getMinU(), woodIcon.getMaxV());
			t.addVertexWithUV(0.5, -0.5, -0.5, woodIcon.getMaxU(), woodIcon.getMaxV());
			t.addVertexWithUV(0.5, 0.5, -0.5, woodIcon.getMaxU(), woodIcon.getMinV());
			t.draw();
			
			t.startDrawingQuads();
			t.setNormal(0, 1, 0);
			t.addVertexWithUV(0.5, 0.5, -0.5, woodIcon.getMinU(), woodIcon.getMinV());
			t.addVertexWithUV(0.5, -0.5, -0.5, woodIcon.getMinU(), woodIcon.getMaxV());
			t.addVertexWithUV(-0.5, -0.5, -0.5, woodIcon.getMaxU(), woodIcon.getMaxV());
			t.addVertexWithUV(-0.5, 0.5, -0.5, woodIcon.getMaxU(), woodIcon.getMinV());
			t.draw();
			
			t.startDrawingQuads();
			t.setNormal(0, 1, 0);
			t.addVertexWithUV(-0.5, 0.5, 0.5, woodIcon.getMinU(), woodIcon.getMinV());
			t.addVertexWithUV(-0.5, -0.5, 0.5, woodIcon.getMinU(), woodIcon.getMaxV());
			t.addVertexWithUV(0.5, -0.5, 0.5, woodIcon.getMaxU(), woodIcon.getMaxV());
			t.addVertexWithUV(0.5, 0.5, 0.5, woodIcon.getMaxU(), woodIcon.getMinV());
			t.draw();
			
			t.startDrawingQuads();
			t.setNormal(0, 1, 0);
			t.addVertexWithUV(-0.5, 0.5, -0.5, woodIcon.getMinU(), woodIcon.getMinV());
			t.addVertexWithUV(-0.5, -0.5, -0.5, woodIcon.getMinU(), woodIcon.getMaxV());
			t.addVertexWithUV(-0.5, -0.5, 0.5, woodIcon.getMaxU(), woodIcon.getMaxV());
			t.addVertexWithUV(-0.5, 0.5, 0.5, woodIcon.getMaxU(), woodIcon.getMinV());
			t.draw();
			
			GL11.glEnable(GL11.GL_LIGHTING);
			GL11.glPopMatrix();
			GL11.glPopMatrix();
			}
		}
		
	}
	
	public void renderItem(ItemStack stack)
	{
		if(WoodType.loadFromNBT(stack.getTagCompound()) != null)
		{
		GL11.glPushMatrix();
		GL11.glTranslated(0.5F, 0.4F, 0.5F);
		Tessellator t = Tessellator.instance;
		GL11.glPushMatrix();
		
		RenderUtils.bindBlockTexture();
		
		IIcon woodIconTop = Block.getBlockFromItem(WoodType.loadFromNBT(stack.getTagCompound()).wood.getItem()).getIcon(0, WoodType.loadFromNBT(stack.getTagCompound()).wood.getItemDamage());
		IIcon woodIcon = Block.getBlockFromItem(WoodType.loadFromNBT(stack.getTagCompound()).wood.getItem()).getIcon(2, WoodType.loadFromNBT(stack.getTagCompound()).wood.getItemDamage());;
		
		t.startDrawingQuads();
		t.setNormal(0, 1, 0);
		t.addVertexWithUV(-0.5, -0.5, -0.5, woodIconTop.getMinU(), woodIconTop.getMinV());
		t.addVertexWithUV(0.5, -0.5, -0.5, woodIconTop.getMaxU(), woodIconTop.getMinV());
		t.addVertexWithUV(0.5, -0.5, 0.5, woodIconTop.getMaxU(), woodIconTop.getMaxV());
		t.addVertexWithUV(-0.5, -0.5, 0.5, woodIconTop.getMinU(), woodIconTop.getMaxV());
		t.draw();
		
	    t.startDrawingQuads();
	    t.setNormal(0, 1, 0);
	    t.addVertexWithUV(-0.5, 0.5, -0.5, woodIconTop.getMinU(), woodIconTop.getMinV());
	    t.addVertexWithUV(-0.5, 0.5, 0.5, woodIconTop.getMinU(), woodIconTop.getMaxV());
	    t.addVertexWithUV(0.5, 0.5, 0.5, woodIconTop.getMaxU(), woodIconTop.getMaxV());
	    t.addVertexWithUV(0.5, 0.5, -0.5, woodIconTop.getMaxU(), woodIconTop.getMinV());
	    t.draw();
		
		t.startDrawingQuads();
		t.setNormal(0, 1, 0);
		t.addVertexWithUV(0.5, 0.5, 0.5, woodIcon.getMinU(), woodIcon.getMinV());
		t.addVertexWithUV(0.5, -0.5, 0.5, woodIcon.getMinU(), woodIcon.getMaxV());
		t.addVertexWithUV(0.5, -0.5, -0.5, woodIcon.getMaxU(), woodIcon.getMaxV());
		t.addVertexWithUV(0.5, 0.5, -0.5, woodIcon.getMaxU(), woodIcon.getMinV());
		t.draw();
		
		t.startDrawingQuads();
		t.setNormal(0, 1, 0);
		t.addVertexWithUV(0.5, 0.5, -0.5, woodIcon.getMinU(), woodIcon.getMinV());
		t.addVertexWithUV(0.5, -0.5, -0.5, woodIcon.getMinU(), woodIcon.getMaxV());
		t.addVertexWithUV(-0.5, -0.5, -0.5, woodIcon.getMaxU(), woodIcon.getMaxV());
		t.addVertexWithUV(-0.5, 0.5, -0.5, woodIcon.getMaxU(), woodIcon.getMinV());
		t.draw();
		
		t.startDrawingQuads();
		t.setNormal(0, 1, 0);
		t.addVertexWithUV(-0.5, 0.5, 0.5, woodIcon.getMinU(), woodIcon.getMinV());
		t.addVertexWithUV(-0.5, -0.5, 0.5, woodIcon.getMinU(), woodIcon.getMaxV());
		t.addVertexWithUV(0.5, -0.5, 0.5, woodIcon.getMaxU(), woodIcon.getMaxV());
		t.addVertexWithUV(0.5, 0.5, 0.5, woodIcon.getMaxU(), woodIcon.getMinV());
		t.draw();
		
		t.startDrawingQuads();
		t.setNormal(0, 1, 0);
		t.addVertexWithUV(-0.5, 0.5, -0.5, woodIcon.getMinU(), woodIcon.getMinV());
		t.addVertexWithUV(-0.5, -0.5, -0.5, woodIcon.getMinU(), woodIcon.getMaxV());
		t.addVertexWithUV(-0.5, -0.5, 0.5, woodIcon.getMaxU(), woodIcon.getMaxV());
		t.addVertexWithUV(-0.5, 0.5, 0.5, woodIcon.getMaxU(), woodIcon.getMinV());
		t.draw();
		
		GL11.glPopMatrix();
		GL11.glPopMatrix();
		}
	}

}
