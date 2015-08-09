package nedelosk.forestday.client.machines.base.renderer.tile;

import nedelosk.forestday.client.machines.base.renderer.model.ModelKiln;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

public class TileKilnRenderer extends TileEntitySpecialRenderer {
	
	public static final ResourceLocation textureModel = new ResourceLocation("forestday", "textures/models/kiln_resin.png");
	
	private ModelKiln kiln;
	
	public TileKilnRenderer()
	{
		this.kiln = new ModelKiln();
	}
	
	@Override
	public void renderTileEntityAt(TileEntity entity, double x, double y, double z, float f) {
		GL11.glPushMatrix();
		GL11.glTranslated((float) x + 0.5F, (float) y + 1.5F, (float) z + 0.5F);
		GL11.glRotated(180, 0F, 0F, 1F);
		this.bindTexture(textureModel);
		GL11.glPushMatrix();
		kiln.renderModel(0.0625F);
		GL11.glPopMatrix();
		GL11.glPopMatrix();
	}
	
	public void renderItem() {
		GL11.glPushMatrix();
		GL11.glTranslated(0.5F, 1.5F, 0.5F);
		GL11.glRotated(180, 0F, 0F, 1F);
		this.bindTexture(textureModel);
		GL11.glPushMatrix();
		kiln.renderModel(0.0625F);
		GL11.glPopMatrix();
		GL11.glPopMatrix();
	}

}
