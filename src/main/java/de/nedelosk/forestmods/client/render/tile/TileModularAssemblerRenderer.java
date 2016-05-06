package de.nedelosk.forestmods.client.render.tile;

import org.lwjgl.opengl.GL11;

import de.nedelosk.forestmods.client.render.models.ModelModularAssembler;
import de.nedelosk.forestmods.common.blocks.tile.TileModularAssembler;
import de.nedelosk.forestmods.library.utils.RenderUtil;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;

public class TileModularAssemblerRenderer extends TileEntitySpecialRenderer {

	public static final ResourceLocation textureModelOFF = new ResourceLocation("forestmods", "textures/models/modular_assembler_off.png");
	public static final ResourceLocation textureModelON = new ResourceLocation("forestmods", "textures/models/modular_assembler_on.png");
	private ModelModularAssembler model;

	public TileModularAssemblerRenderer() {
		this.model = new ModelModularAssembler();
	}

	@Override
	public void renderTileEntityAt(TileEntity entity, double x, double y, double z, float f) {
		GL11.glPushMatrix();
		GL11.glTranslated((float) x + 0.5F, (float) y + 1.5F, (float) z + 0.5F);
		GL11.glRotated(180, 0F, 0F, 1F);
		GL11.glRotatef(180.0F, 0.0F, 1.0F, 0.0F);
		GL11.glPushMatrix();
		if (((TileModularAssembler) entity).isWorking) {
			RenderUtil.bindTexture(textureModelON);
		} else {
			RenderUtil.bindTexture(textureModelOFF);
		}
		model.render();
		GL11.glPopMatrix();
		GL11.glPopMatrix();
	}

	public void renderItem() {
		GL11.glPushMatrix();
		GL11.glTranslated(0.5F, 1.5F, 0.5F);
		GL11.glRotated(180, 0F, 0F, 1F);
		GL11.glRotatef(180.0F, 0.0F, 1.0F, 0.0F);
		RenderUtil.bindTexture(textureModelOFF);
		model.render();
		GL11.glPopMatrix();
	}
}