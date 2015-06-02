package nedelosk.forestday.structure.kiln.charcoal.render;

import nedelosk.forestday.structure.kiln.charcoal.blocks.tile.TileCharcoalKiln;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

public class RendererCharcoalKiln extends TileEntitySpecialRenderer {

	public void renderTileEntityAt(TileCharcoalKiln tile, double x, double y, double z, float f) 
	{

		TileCharcoalKiln kiln =  tile instanceof TileCharcoalKiln ? (TileCharcoalKiln) tile : null;
		if(kiln != null)
		{
		int meta = kiln.getBlockMetadata();
		GL11.glPushMatrix();
		GL11.glTranslated((float) x + 0.5F, (float) y + 1.5F, (float) z + 0.5F);
		Tessellator t = Tessellator.instance;
		this.bindTexture(new ResourceLocation("minecraft", "textures/blocks/dirt.png"));
		GL11.glPushMatrix();
		
		//Down
	    //t.startDrawingQuads();
	    //t.setNormal(0, 1, 0);
	    //t.addVertexWithUV(-0.5, -1.2, -0.2, 0, 0);
	    //t.addVertexWithUV(0.5, -1.2, -0.2, 1, 0);
	    //t.addVertexWithUV(0.5, -1.2, 0.2, 1, 1);
	    //t.addVertexWithUV(-0.5, -1.2, 0.2, 0, 1);
	    //t.draw();
		
		if(tile.isMaster)
		{
		    t.startDrawingQuads();
		    t.setNormal(0, 1, 0);
		    t.addVertexWithUV(-1.5, -1.5, -1.5, 0, 0);
		    t.addVertexWithUV(1.5, -1.5, -1.5, 4, 0);
		    t.addVertexWithUV(1.5, -1.5, 1.5, 4, 4);
		    t.addVertexWithUV(-1.5, -1.5, 1.5, 0, 4);
		    t.draw();
		    
		    t.startDrawingQuads();
		    t.setNormal(0, 1, 0);
		    t.addVertexWithUV(1.5, -1.49, -1.5, 0, 0);
		    t.addVertexWithUV(-1.5, -1.49, -1.5, 4, 0);
		    t.addVertexWithUV(-1.5, -1.49, 1.5, 4, 4);
		    t.addVertexWithUV(1.5, -1.49, 1.5, 0, 4);
		    t.draw();
		}
		else if(tile.master == null)
		{
			
		    /*t.startDrawingQuads();
		    t.setNormal(0, 1, 0);
		    t.addVertexWithUV(-1.5, -1.5, -1.5, 0, 0);
		    t.addVertexWithUV(1.5, -1.5, -1.5, 4, 0);
		    t.addVertexWithUV(1.5, -1.5, 1.5, 4, 4);
		    t.addVertexWithUV(-1.5, -1.5, 1.5, 0, 4);
		    t.draw();
		    
		    t.startDrawingQuads();
		    t.setNormal(0, 1, 0);
		    t.addVertexWithUV(1.5, -1.49, -1.5, 0, 0);
		    t.addVertexWithUV(-1.5, -1.49, -1.5, 4, 0);
		    t.addVertexWithUV(-1.5, -1.49, 1.5, 4, 4);
		    t.addVertexWithUV(1.5, -1.49, 1.5, 0, 4);
		    t.draw();*/
		}
		else if(tile.master.xCoord == tile.xCoord)
		{
			if(tile.master.yCoord == tile.yCoord + 1)
			{
			    t.startDrawingQuads();
			    t.setNormal(0, 1, 0);
			    t.addVertexWithUV(-0.5, -2.5, -0.5, 0, 0);
			    t.addVertexWithUV(0.5, -2.5, -0.5, 1, 0);
			    t.addVertexWithUV(0.5, -2.5, 0.5, 1, 1);
			    t.addVertexWithUV(-0.5, -2.5, 0.5, 0, 1);
			    t.draw();
			}
		}
	    
		GL11.glPopMatrix();
		GL11.glPopMatrix();
		}
	    
	}
	
	@Override
	public void renderTileEntityAt(TileEntity tile, double x, double y, double z, float f) 
	{
		renderTileEntityAt((TileCharcoalKiln)tile, x, y, z, f);
	}

}
