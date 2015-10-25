package nedelosk.modularmachines.client.renderers.tile;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import nedelosk.forestday.common.multiblocks.TileMultiblockBase;
import nedelosk.forestday.utils.RenderUtils;
import nedelosk.modularmachines.client.renderers.model.ModelFermenter;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;

public class TileRendererMultiblockFermenter extends TileEntitySpecialRenderer {
	public static final ResourceLocation textureModel = new ResourceLocation("modularmachines", "textures/models/multiblocks/fermenter.png");
	
	private ModelFermenter fermenter;
	
	public TileRendererMultiblockFermenter()
	{
		this.fermenter = new ModelFermenter();
	}
	
	@Override
	public void renderTileEntityAt(TileEntity tile, double x, double y, double z, float f) {
		if(tile instanceof TileMultiblockBase && ((TileMultiblockBase)tile).getPatternMarker() == 'J')
		{
		    GL11.glPushMatrix();
		    GL11.glTranslated((float) x + 0.5F, (float) y + 1.5F, (float) z + 0.5F);
		    GL11.glRotated(180, 0F, 0F, 1F);
		    if(((TileMultiblockBase)tile).pattern.tier == 1)
		    	GL11.glRotated(180, 0F, 1F, 0F);
		    if(((TileMultiblockBase)tile).pattern.tier == 2)
		    	GL11.glRotated(-90, 0F, 1F, 0F);
		    if(((TileMultiblockBase)tile).pattern.tier == 3)
		    	GL11.glRotated(90, 0F, 1F, 0F);
		    GL11.glEnable(GL12.GL_RESCALE_NORMAL);
		    RenderUtils.bindTexture(textureModel);
		    GL11.glPushMatrix();
		    fermenter.render(null, 0, 0, 0, 0, 0, 0.0625F);
		    GL11.glPopMatrix();
		    GL11.glDisable(GL12.GL_RESCALE_NORMAL);
		    GL11.glPopMatrix();
		}
	}
}
