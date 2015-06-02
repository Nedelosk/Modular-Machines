package nedelosk.forestday.structure.base.render;

import nedelosk.forestday.api.structure.tile.ITileStructure;
import nedelosk.forestday.structure.base.blocks.tile.TileCoilGrinding;
import nedelosk.nedeloskcore.utils.RenderUtils;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

public class RendererCoil extends TileEntitySpecialRenderer {

public static ResourceLocation[] coilsResourceHolder = new ResourceLocation[] { RenderUtils.getResourceLocatioStructure("wood", "coils", "holder"), RenderUtils.getResourceLocatioStructure("stone", "coils", "holder"), RenderUtils.getResourceLocatioStructure("iron", "coils", "holder"), RenderUtils.getResourceLocatioStructure("steel", "coils", "holder"), RenderUtils.getResourceLocatioStructure("steel_dark", "coils", "holder"), RenderUtils.getResourceLocatioStructure("enderium", "coils", "holder")};
	
	public static ResourceLocation[] coilsResource = new ResourceLocation[] { RenderUtils.getResourceLocatioStructure("stone", "coils"), RenderUtils.getResourceLocatioStructure("iron", "coils"), RenderUtils.getResourceLocatioStructure("brown_alloy", "coils"), RenderUtils.getResourceLocatioStructure("steel", "coils"), RenderUtils.getResourceLocatioStructure("steel_dark", "coils"), RenderUtils.getResourceLocatioStructure("diamond", "coils")};
	
	@Override
	public void renderTileEntityAt(TileEntity tile, double x, double y, double z, float f) {
		
		TileCoilGrinding coil =  tile instanceof TileCoilGrinding ? (TileCoilGrinding) tile : null;
		if(coil != null)
		{
		int meta = coil.getBlockMetadata();
		GL11.glPushMatrix();
		GL11.glTranslated((float) x + 0.5F, (float) y + 1.5F, (float) z + 0.5F);
		if(tile.getWorldObj().getTileEntity(tile.xCoord, tile.yCoord, tile.zCoord + 1) instanceof ITileStructure && tile.getWorldObj().getTileEntity(tile.xCoord, tile.yCoord, tile.zCoord - 1) instanceof ITileStructure)
		{
			GL11.glRotated(90, 0F, 1F, 0F);
		}
		Tessellator t = Tessellator.instance;
	    this.bindTexture(coilsResourceHolder[meta]);
		GL11.glPushMatrix();
		//Down
	    t.startDrawingQuads();
	    t.setNormal(0, 1, 0);
	    t.addVertexWithUV(-0.5, -1.2, -0.2, 0, 0);
	    t.addVertexWithUV(0.5, -1.2, -0.2, 1, 0);
	    t.addVertexWithUV(0.5, -1.2, 0.2, 1, 1);
	    t.addVertexWithUV(-0.5, -1.2, 0.2, 0, 1);
	    t.draw();
	    //Top
	    t.startDrawingQuads();
	    t.setNormal(0, 1, 0);
	    t.addVertexWithUV(-0.5, -0.8, -0.2, 0, 0);
	    t.addVertexWithUV(-0.5, -0.8, 0.2, 1, 0);
	    t.addVertexWithUV(0.5, -0.8, 0.2, 1, 1);
	    t.addVertexWithUV(0.5, -0.8, -0.2, 0, 1);
	    t.draw();
	    //Front
	    t.startDrawingQuads();
	    t.setNormal(0, 1, 0);
	    t.addVertexWithUV(0.5, -0.8, 0.2, 0, 0);
	    t.addVertexWithUV(0.5, -1.2, 0.2, 1, 0);
	    t.addVertexWithUV(0.5, -1.2, -0.2, 1, 1);
	    t.addVertexWithUV(0.5, -0.8, -0.2, 0, 1);
	    t.draw();
	    //Back
	    t.startDrawingQuads();
	    t.setNormal(0, 1, 0);
	    t.addVertexWithUV(-0.5, -0.8, -0.2, 0, 0);
	    t.addVertexWithUV(-0.5, -1.2, -0.2, 1, 0);
	    t.addVertexWithUV(-0.5, -1.2, 0.2, 1, 1);
	    t.addVertexWithUV(-0.5, -0.8, 0.2, 0, 1);
	    t.draw();
	    
	    //Side
	    t.startDrawingQuads();
	    t.setNormal(0, 1, 0);
	    t.addVertexWithUV(0.5, -0.8, -0.2, 0, 0);
	    t.addVertexWithUV(0.5, -1.2, -0.2, 1, 0);
	    t.addVertexWithUV(-0.5, -1.2, -0.2, 1, 1);
	    t.addVertexWithUV(-0.5, -0.8, -0.2, 0, 1);
	    t.draw();
	    t.startDrawingQuads();
	    t.setNormal(0, 1, 0);
	    t.addVertexWithUV(-0.5, -0.8, 0.2, 0, 0);
	    t.addVertexWithUV(-0.5, -1.2, 0.2, 1, 0);
	    t.addVertexWithUV(0.5, -1.2, 0.2, 1, 1);
	    t.addVertexWithUV(0.5, -0.8, 0.2, 0, 1);
	    t.draw();
	    
	    //Coil 1
	    if(coil.isStackInSlotCoil(2))
	    {
	    this.bindTexture(coilsResource[coil.getCoilMeta(2)]);
	    
	    t.startDrawingQuads();
	    t.setNormal(0, 1, 0);
	    t.addVertexWithUV(0.44444444, -0.5, 0.5, 0, 0);
	    t.addVertexWithUV(0.44444444, -1.5, 0.5, 1, 0);
	    t.addVertexWithUV(0.44444444, -1.5, -0.5, 1, 1);
	    t.addVertexWithUV(0.44444444, -0.5, -0.5, 0, 1);
	    t.draw();
	    
	    t.startDrawingQuads();
	    t.setNormal(0, 1, 0);
	    t.addVertexWithUV(0.25, -0.5, -0.5, 0, 0);
	    t.addVertexWithUV(0.25, -1.5, -0.5, 1, 0);
	    t.addVertexWithUV(0.25, -1.5, 0.5, 1, 1);
	    t.addVertexWithUV(0.25, -0.5, 0.5, 0, 1);
	    t.draw();
	    
	    t.startDrawingQuads();
	    t.setNormal(0, 1, 0);
	    t.addVertexWithUV(0.25, -0.5, -0.5, 0, 0);
	    t.addVertexWithUV(0.25, -0.5, 0.5, 1, 0);
	    t.addVertexWithUV(0.44444444, -0.5, 0.5, 1, 1);
	    t.addVertexWithUV(0.44444444, -0.5, -0.5, 0, 1);
	    t.draw();
	    
	    t.startDrawingQuads();
	    t.setNormal(0, 1, 0);
	    t.addVertexWithUV(0.44444444, -1.5, -0.5, 0, 0);
	    t.addVertexWithUV(0.44444444, -1.5, 0.5, 1, 0);
	    t.addVertexWithUV(0.25, -1.5, 0.5, 1, 1);
	    t.addVertexWithUV(0.25, -1.5, -0.5, 0, 1);
	    t.draw();
	    
	    t.startDrawingQuads();
	    t.setNormal(0, 1, 0);
	    t.addVertexWithUV(0.44444444, -0.5, -0.5, 0, 0);
	    t.addVertexWithUV(0.44444444, -1.5, -0.5, 1, 0);
	    t.addVertexWithUV(0.25, -1.5, -0.5, 1, 1);
	    t.addVertexWithUV(0.25, -0.5, -0.5, 0, 1);
	    t.draw();
	    
	    t.startDrawingQuads();
	    t.setNormal(0, 1, 0);
	    t.addVertexWithUV(0.25, -0.5, 0.5, 0, 0);
	    t.addVertexWithUV(0.25, -1.5, 0.5, 1, 0);
	    t.addVertexWithUV(0.44444444, -1.5, 0.5, 1, 1);
	    t.addVertexWithUV(0.44444444, -0.5, 0.5, 0, 1);
	    t.draw();
	    }
	    
	  //Coil 2
	    if(coil.isStackInSlotCoil(3))
	    {
	    this.bindTexture(coilsResource[coil.getCoilMeta(3)]);
	    
	    t.startDrawingQuads();
	    t.setNormal(0, 1, 0);
	    t.addVertexWithUV(0.25, -0.5, 0.5, 0, 0);
	    t.addVertexWithUV(0.25, -1.5, 0.5, 1, 0);
	    t.addVertexWithUV(0.25, -1.5, -0.5, 1, 1);
	    t.addVertexWithUV(0.25, -0.5, -0.5, 0, 1);
	    t.draw();
	    
	    t.startDrawingQuads();
	    t.setNormal(0, 1, 0);
	    t.addVertexWithUV(0, -0.5, -0.5, 0, 0);
	    t.addVertexWithUV(0, -1.5, -0.5, 1, 0);
	    t.addVertexWithUV(0, -1.5, 0.5, 1, 1);
	    t.addVertexWithUV(0, -0.5, 0.5, 0, 1);
	    t.draw();
	    
	    t.startDrawingQuads();
	    t.setNormal(0, 1, 0);
	    t.addVertexWithUV(0, -0.5, -0.5, 0, 0);
	    t.addVertexWithUV(0, -0.5, 0.5, 1, 0);
	    t.addVertexWithUV(0.25, -0.5, 0.5, 1, 1);
	    t.addVertexWithUV(0.25, -0.5, -0.5, 0, 1);
	    t.draw();
	    
	    t.startDrawingQuads();
	    t.setNormal(0, 1, 0);
	    t.addVertexWithUV(0.25, -1.5, -0.5, 0, 0);
	    t.addVertexWithUV(0.25, -1.5, 0.5, 1, 0);
	    t.addVertexWithUV(0, -1.5, 0.5, 1, 1);
	    t.addVertexWithUV(0, -1.5, -0.5, 0, 1);
	    t.draw();
	    
	    t.startDrawingQuads();
	    t.setNormal(0, 1, 0);
	    t.addVertexWithUV(0.25, -0.5, -0.5, 0, 0);
	    t.addVertexWithUV(0.25, -1.5, -0.5, 1, 0);
	    t.addVertexWithUV(0, -1.5, -0.5, 1, 1);
	    t.addVertexWithUV(0, -0.5, -0.5, 0, 1);
	    t.draw();
	    
	    t.startDrawingQuads();
	    t.setNormal(0, 1, 0);
	    t.addVertexWithUV(0, -0.5, 0.5, 0, 0);
	    t.addVertexWithUV(0, -1.5, 0.5, 1, 0);
	    t.addVertexWithUV(0.25, -1.5, 0.5, 1, 1);
	    t.addVertexWithUV(0.25, -0.5, 0.5, 0, 1);
	    t.draw();
	    }
	    
	    if(coil.isStackInSlotCoil(4))
	    {
	    this.bindTexture(coilsResource[coil.getCoilMeta(4)]);
	    
	    t.startDrawingQuads();
	    t.setNormal(0, 1, 0);
	    t.addVertexWithUV(0, -0.5, 0.5, 0, 0);
	    t.addVertexWithUV(0, -1.5, 0.5, 1, 0);
	    t.addVertexWithUV(0, -1.5, -0.5, 1, 1);
	    t.addVertexWithUV(0, -0.5, -0.5, 0, 1);
	    t.draw();
	    
	    t.startDrawingQuads();
	    t.setNormal(0, 1, 0);
	    t.addVertexWithUV(-0.25, -0.5, -0.5, 0, 0);
	    t.addVertexWithUV(-0.25, -1.5, -0.5, 1, 0);
	    t.addVertexWithUV(-0.25, -1.5, 0.5, 1, 1);
	    t.addVertexWithUV(-0.25, -0.5, 0.5, 0, 1);
	    t.draw();
	    
	    t.startDrawingQuads();
	    t.setNormal(0, 1, 0);
	    t.addVertexWithUV(-0.25, -0.5, -0.5, 0, 0);
	    t.addVertexWithUV(-0.25, -0.5, 0.5, 1, 0);
	    t.addVertexWithUV(0, -0.5, 0.5, 1, 1);
	    t.addVertexWithUV(0, -0.5, -0.5, 0, 1);
	    t.draw();
	    
	    t.startDrawingQuads();
	    t.setNormal(0, 1, 0);
	    t.addVertexWithUV(0, -1.5, -0.5, 0, 0);
	    t.addVertexWithUV(0, -1.5, 0.5, 1, 0);
	    t.addVertexWithUV(-0.25, -1.5, 0.5, 1, 1);
	    t.addVertexWithUV(-0.25, -1.5, -0.5, 0, 1);
	    t.draw();
	    
	    t.startDrawingQuads();
	    t.setNormal(0, 1, 0);
	    t.addVertexWithUV(0, -0.5, -0.5, 0, 0);
	    t.addVertexWithUV(0, -1.5, -0.5, 1, 0);
	    t.addVertexWithUV(-0.25, -1.5, -0.5, 1, 1);
	    t.addVertexWithUV(-0.25, -0.5, -0.5, 0, 1);
	    t.draw();
	    
	    t.startDrawingQuads();
	    t.setNormal(0, 1, 0);
	    t.addVertexWithUV(-0.25, -0.5, 0.5, 0, 0);
	    t.addVertexWithUV(-0.25, -1.5, 0.5, 1, 0);
	    t.addVertexWithUV(0, -1.5, 0.5, 1, 1);
	    t.addVertexWithUV(0, -0.5, 0.5, 0, 1);
	    t.draw();
	    }
	    
	    if(coil.isStackInSlotCoil(5))
	    {
	    this.bindTexture(coilsResource[coil.getCoilMeta(5)]);
	    
	    t.startDrawingQuads();
	    t.setNormal(0, 1, 0);
	    t.addVertexWithUV(-0.25, -0.5, 0.5, 0, 0);
	    t.addVertexWithUV(-0.25, -1.5, 0.5, 1, 0);
	    t.addVertexWithUV(-0.25, -1.5, -0.5, 1, 1);
	    t.addVertexWithUV(-0.25, -0.5, -0.5, 0, 1);
	    t.draw();
	    
	    t.startDrawingQuads();
	    t.setNormal(0, 1, 0);
	    t.addVertexWithUV(-0.44444444, -0.5, -0.5, 0, 0);
	    t.addVertexWithUV(-0.44444444, -1.5, -0.5, 1, 0);
	    t.addVertexWithUV(-0.44444444, -1.5, 0.5, 1, 1);
	    t.addVertexWithUV(-0.44444444, -0.5, 0.5, 0, 1);
	    t.draw();
	    
	    t.startDrawingQuads();
	    t.setNormal(0, 1, 0);
	    t.addVertexWithUV(-0.44444444, -0.5, -0.5, 0, 0);
	    t.addVertexWithUV(-0.44444444, -0.5, 0.5, 1, 0);
	    t.addVertexWithUV(-0.25, -0.5, 0.5, 1, 1);
	    t.addVertexWithUV(-0.25, -0.5, -0.5, 0, 1);
	    t.draw();
	    
	    t.startDrawingQuads();
	    t.setNormal(0, 1, 0);
	    t.addVertexWithUV(-0.25, -1.5, -0.5, 0, 0);
	    t.addVertexWithUV(-0.25, -1.5, 0.5, 1, 0);
	    t.addVertexWithUV(-0.44444444, -1.5, 0.5, 1, 1);
	    t.addVertexWithUV(-0.44444444, -1.5, -0.5, 0, 1);
	    t.draw();
	    
	    t.startDrawingQuads();
	    t.setNormal(0, 1, 0);
	    t.addVertexWithUV(-0.25, -0.5, -0.5, 0, 0);
	    t.addVertexWithUV(-0.25, -1.5, -0.5, 1, 0);
	    t.addVertexWithUV(-0.44444444, -1.5, -0.5, 1, 1);
	    t.addVertexWithUV(-0.44444444, -0.5, -0.5, 0, 1);
	    t.draw();
	    
	    t.startDrawingQuads();
	    t.setNormal(0, 1, 0);
	    t.addVertexWithUV(-0.44444444, -0.5, 0.5, 0, 0);
	    t.addVertexWithUV(-0.44444444, -1.5, 0.5, 1, 0);
	    t.addVertexWithUV(-0.25, -1.5, 0.5, 1, 1);
	    t.addVertexWithUV(-0.25, -0.5, 0.5, 0, 1);
	    t.draw();
	    }

		GL11.glPopMatrix();
		GL11.glPopMatrix();
	    }
	}
	
	public void renderItem(int meta){
		
		GL11.glPushMatrix();
		GL11.glTranslated(0.5F, 1.5F, 0.5F);
		//GL11.glRotated(90, 0F, 1F, 0F);
		Tessellator t = Tessellator.instance;
	    this.bindTexture(coilsResourceHolder[meta]);
		GL11.glPushMatrix();
		//Down
	    t.startDrawingQuads();
	    t.setNormal(0, 1, 0);
	    t.addVertexWithUV(-0.5, -1.2, -0.2, 0, 0);
	    t.addVertexWithUV(0.5, -1.2, -0.2, 1, 0);
	    t.addVertexWithUV(0.5, -1.2, 0.2, 1, 1);
	    t.addVertexWithUV(-0.5, -1.2, 0.2, 0, 1);
	    t.draw();
	    //Top
	    t.startDrawingQuads();
	    t.setNormal(0, 1, 0);
	    t.addVertexWithUV(-0.5, -0.8, -0.2, 0, 0);
	    t.addVertexWithUV(-0.5, -0.8, 0.2, 1, 0);
	    t.addVertexWithUV(0.5, -0.8, 0.2, 1, 1);
	    t.addVertexWithUV(0.5, -0.8, -0.2, 0, 1);
	    t.draw();
	    //Front
	    t.startDrawingQuads();
	    t.setNormal(0, 1, 0);
	    t.addVertexWithUV(0.5, -0.8, 0.2, 0, 0);
	    t.addVertexWithUV(0.5, -1.2, 0.2, 1, 0);
	    t.addVertexWithUV(0.5, -1.2, -0.2, 1, 1);
	    t.addVertexWithUV(0.5, -0.8, -0.2, 0, 1);
	    t.draw();
	    //Back
	    t.startDrawingQuads();
	    t.setNormal(0, 1, 0);
	    t.addVertexWithUV(-0.5, -0.8, -0.2, 0, 0);
	    t.addVertexWithUV(-0.5, -1.2, -0.2, 1, 0);
	    t.addVertexWithUV(-0.5, -1.2, 0.2, 1, 1);
	    t.addVertexWithUV(-0.5, -0.8, 0.2, 0, 1);
	    t.draw();
	    
	    //Side
	    t.startDrawingQuads();
	    t.setNormal(0, 1, 0);
	    t.addVertexWithUV(0.5, -0.8, -0.2, 0, 0);
	    t.addVertexWithUV(0.5, -1.2, -0.2, 1, 0);
	    t.addVertexWithUV(-0.5, -1.2, -0.2, 1, 1);
	    t.addVertexWithUV(-0.5, -0.8, -0.2, 0, 1);
	    t.draw();
	    t.startDrawingQuads();
	    t.setNormal(0, 1, 0);
	    t.addVertexWithUV(-0.5, -0.8, 0.2, 0, 0);
	    t.addVertexWithUV(-0.5, -1.2, 0.2, 1, 0);
	    t.addVertexWithUV(0.5, -1.2, 0.2, 1, 1);
	    t.addVertexWithUV(0.5, -0.8, 0.2, 0, 1);
	    t.draw();

		GL11.glPopMatrix();
		GL11.glPopMatrix();
	}


}
