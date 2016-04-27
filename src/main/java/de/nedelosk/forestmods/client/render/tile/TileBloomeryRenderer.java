package de.nedelosk.forestmods.client.render.tile;

import org.lwjgl.opengl.GL11;

import de.nedelosk.forestmods.client.render.models.ModelBloomery;
import de.nedelosk.forestmods.library.utils.RenderUtil;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;

public class TileBloomeryRenderer extends TileEntitySpecialRenderer {

	private ModelBloomery model;

	public TileBloomeryRenderer() {
		this.model = new ModelBloomery();
	}

	@Override
	public void renderTileEntityAt(TileEntity entity, double x, double y, double z, float f) {
		GL11.glPushMatrix();
		GL11.glTranslated((float) x + 0.5F, (float) y + 1.5F, (float) z + 0.5F);
		GL11.glRotated(180, 0F, 0F, 1F);
		GL11.glRotatef(180.0F, 0.0F, 1.0F, 0.0F);
		GL11.glPushMatrix();
		RenderUtil.bindTexture(new ResourceLocation("forestmods", "textures/models/bloomery.png"));
		model.render();
		GL11.glPopMatrix();
		GL11.glPopMatrix();
	}
}
