package nedelosk.forestday.client.machines.base.renderer.tile;

import org.lwjgl.opengl.GL11;

import nedelosk.forestday.client.machines.base.renderer.model.ModelSaw;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class TileSawRenderer extends TileEntitySpecialRenderer {
	
	public static final ResourceLocation textureModel = new ResourceLocation("forestday", "textures/model/file_auto.png");
	public static final ResourceLocation textureModelActive = new ResourceLocation("forestday", "textures/model/file_auto_active.png");
	
	private ModelSaw file;
	
	public TileSawRenderer()
	{
		this.file = new ModelSaw();
	}
	
	@Override
	public void renderTileEntityAt(TileEntity entity, double x, double y, double z, float f) {
		GL11.glPushMatrix();
		GL11.glTranslated((float) x + 0.5F, (float) y + 1.5F, (float) z + 0.5F);
		GL11.glRotated(180, 0F, 0F, 1F);
		this.bindTexture(textureModel);
		GL11.glPushMatrix();
		file.renderModel(0.0625F);
		GL11.glPopMatrix();
		GL11.glPopMatrix();
	}

}
