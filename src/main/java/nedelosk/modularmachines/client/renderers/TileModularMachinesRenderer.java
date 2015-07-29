package nedelosk.modularmachines.client.renderers;

import org.lwjgl.opengl.GL11;

import nedelosk.modularmachines.client.renderers.model.ModelModularAssembler;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;

public class TileModularMachinesRenderer extends TileEntitySpecialRenderer {
	
	public static final ResourceLocation textureModel = new ResourceLocation("modularmachines", "textures/models/modular_assembler.png");
	
	private ModelModularAssembler model;
	
	public TileModularMachinesRenderer()
	{
		this.model = new ModelModularAssembler();
	}
	
	@Override
	public void renderTileEntityAt(TileEntity entity, double x, double y, double z, float f) {
		GL11.glPushMatrix();
		GL11.glTranslated((float) x + 0.5F, (float) y + 1.5F, (float) z + 0.5F);
		GL11.glRotated(180, 0F, 0F, 1F);
		this.bindTexture(textureModel);
		GL11.glPushMatrix();
		model.render();
		GL11.glPopMatrix();
		GL11.glPopMatrix();
	}
	
	public void renderItem() {
		GL11.glPushMatrix();
		GL11.glTranslated((float)0.5F, (float) 1.5F, (float) 0.5F);
		GL11.glRotated(180, 0F, 0F, 1F);
		this.bindTexture(textureModel);
		GL11.glPushMatrix();
		model.render();
		GL11.glPopMatrix();
		GL11.glPopMatrix();
	}

}
