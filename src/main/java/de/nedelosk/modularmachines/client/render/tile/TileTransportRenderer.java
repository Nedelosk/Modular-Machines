package de.nedelosk.modularmachines.client.render.tile;

import org.lwjgl.opengl.GL11;

import de.nedelosk.modularmachines.api.transport.ITransportPart;
import de.nedelosk.modularmachines.client.model.ModelTransport;
import de.nedelosk.modularmachines.common.transport.TileEntityTransport;
import de.nedelosk.modularmachines.common.utils.RenderUtil;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;

public class TileTransportRenderer extends TileEntitySpecialRenderer<TileEntityTransport> {

	public static final ResourceLocation textureModel = new ResourceLocation("modularmachines", "textures/models/transport_base.png");
	private ModelTransport model;

	public TileTransportRenderer() {
		this.model = new ModelTransport();
	}

	@Override
	public void renderTileEntityAt(TileEntityTransport tPort, double x, double y, double z, float f, int destroyStage) {
		if(tPort != null){
			ITransportPart part = tPort.getPart();
			GL11.glPushMatrix();
			GL11.glTranslated((float) x + 0.5F, (float) y + 1.5F, (float) z + 0.5F);
			GL11.glRotated(180, 0F, 0F, 1F);
			GL11.glRotatef(180.0F, 0.0F, 1.0F, 0.0F);
			GL11.glPushMatrix();
			RenderUtil.bindTexture(textureModel);
			model.render();
			if (part.getSide(EnumFacing.SOUTH).isActive()) {
				model.renderLaser();
			}
			if (part.getSide(EnumFacing.WEST).isActive()) {
				GL11.glRotated(90, 0F, 1F, 0F);
				model.renderLaser();
				GL11.glRotated(-90, 0F, 1F, 0F);
			}
			if (part.getSide(EnumFacing.NORTH).isActive()) {
				GL11.glRotated(180, 0F, 1F, 0F);
				model.renderLaser();
				GL11.glRotated(-180, 0F, 1F, 0F);
			}
			if (part.getSide(EnumFacing.EAST).isActive()) {
				GL11.glRotated(270, 0F, 1F, 0F);
				model.renderLaser();
				GL11.glRotated(-270, 0F, 1F, 0F);
			}
			if (part.getSide(EnumFacing.UP).isActive()) {
				model.renderLaserTop();
			}
			if (part.getSide(EnumFacing.DOWN).isActive()) {
				GL11.glRotated(180, 0F, 0F, 1F);
				GL11.glTranslated(0, -2F, 0);
				model.renderLaserTop();
				GL11.glRotated(-180, 0F, 0F, 1F);
				GL11.glTranslated(0, 2F, 0);
			}
			GL11.glPopMatrix();
			GL11.glPopMatrix();
		}else{
			GL11.glPushMatrix();
			GL11.glTranslated(0.5F, 2F, 0.5F);
			GL11.glRotated(180, 0F, 0F, 1F);
			GL11.glRotatef(180.0F, 0.0F, 1.0F, 0.0F);
			GL11.glScaled(1.5F, 1.5F, 1.5F);
			GL11.glPushMatrix();
			RenderUtil.bindTexture(textureModel);
			model.render();
			model.renderLaser();
			GL11.glRotated(90, 0F, 1F, 0F);
			model.renderLaser();
			GL11.glRotated(90, 0F, 1F, 0F);
			model.renderLaser();
			GL11.glRotated(90, 0F, 1F, 0F);
			model.renderLaser();
			GL11.glRotated(90, 0F, 1F, 0F);
			model.renderLaserTop();
			GL11.glRotated(180, 0F, 0F, 1F);
			GL11.glTranslated(0, -2F, 0);
			model.renderLaserTop();
			GL11.glRotated(-180, 0F, 0F, 1F);
			GL11.glTranslated(0, 2F, 0);
			GL11.glPopMatrix();
			GL11.glPopMatrix();
		}
	}
}
