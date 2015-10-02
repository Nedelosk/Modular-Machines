package nedelosk.modularmachines.client.renderers.tile;

import org.lwjgl.opengl.GL11;

import nedelosk.modularmachines.client.renderers.model.ModelModularTable;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;

public class TileModularWorkbenchRenderer extends TileEntitySpecialRenderer {
	
	public static final ResourceLocation textureModel = new ResourceLocation("modularmachines", "textures/models/modular_workbench.png");
	
	private ModelModularTable workbench;
	
	public TileModularWorkbenchRenderer()
	{
		this.workbench = new ModelModularTable();
	}
	
	@Override
	public void renderTileEntityAt(TileEntity entity, double x, double y, double z, float f) {
		GL11.glPushMatrix();
		GL11.glTranslated((float) x + 0.5F, (float) y + 1.5F, (float) z + 0.5F);
		GL11.glRotated(180, 0F, 0F, 1F);
		this.bindTexture(textureModel);
		GL11.glPushMatrix();
		workbench.render();
		GL11.glPopMatrix();
		GL11.glPopMatrix();
	}
	
	public void renderItem() {
		GL11.glPushMatrix();
		GL11.glTranslated(0.5F, 1.5F, 0.5F);
		GL11.glRotated(180, 0F, 0F, 1F);
		this.bindTexture(textureModel);
		GL11.glPushMatrix();
		workbench.render();
		GL11.glPopMatrix();
		GL11.glPopMatrix();
	}

}
