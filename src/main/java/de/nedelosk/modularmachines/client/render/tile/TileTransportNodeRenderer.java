package de.nedelosk.modularmachines.client.render.tile;

import org.lwjgl.opengl.GL11;

import de.nedelosk.modularmachines.api.transport.node.ITransportNode;
import de.nedelosk.modularmachines.client.model.ModelTransportNode;
import de.nedelosk.modularmachines.common.transport.node.TileEntityTransportNode;
import de.nedelosk.modularmachines.common.utils.RenderUtil;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;

public class TileTransportNodeRenderer extends TileEntitySpecialRenderer<TileEntityTransportNode> {

	public static final ResourceLocation textureModel = new ResourceLocation("modularmachines", "textures/models/transport_node.png");
	private ModelTransportNode model;

	public TileTransportNodeRenderer() {
		this.model = new ModelTransportNode();
	}

	@Override
	public void renderTileEntityAt(TileEntityTransportNode tNode, double x, double y, double z, float f, int destroyStage) {
		if(tNode != null){
			ITransportNode node = tNode.getPart();
			GL11.glPushMatrix();
			GL11.glTranslated((float) x + 0.5F, (float) y + 1.5F, (float) z + 0.5F);
			GL11.glRotated(180, 0F, 0F, 1F);
			GL11.glRotatef(180.0F, 0.0F, 1.0F, 0.0F);
			GL11.glPushMatrix();
			RenderUtil.bindTexture(textureModel);
			model.render();
			if (node.getSide(EnumFacing.SOUTH).isActive()) {
				model.renderLaser();
			} else if (node.getSide(EnumFacing.SOUTH).isConnected()) {
				model.renderPipe();
			}
			if (node.getSide(EnumFacing.WEST).isActive()) {
				GL11.glRotated(90, 0F, 1F, 0F);
				model.renderLaser();
				GL11.glRotated(-90, 0F, 1F, 0F);
			} else if (node.getSide(EnumFacing.WEST).isConnected()) {
				GL11.glRotated(90, 0F, 1F, 0F);
				model.renderPipe();
				GL11.glRotated(-90, 0F, 1F, 0F);
			}
			if (node.getSide(EnumFacing.NORTH).isActive()) {
				GL11.glRotated(180, 0F, 1F, 0F);
				model.renderLaser();
				GL11.glRotated(-180, 0F, 1F, 0F);
			} else if (node.getSide(EnumFacing.NORTH).isConnected()) {
				GL11.glRotated(180, 0F, 1F, 0F);
				model.renderPipe();
				GL11.glRotated(-180, 0F, 1F, 0F);
			}
			if (node.getSide(EnumFacing.EAST).isActive()) {
				GL11.glRotated(270, 0F, 1F, 0F);
				model.renderLaser();
				GL11.glRotated(-270, 0F, 1F, 0F);
			} else if (node.getSide(EnumFacing.EAST).isConnected()) {
				GL11.glRotated(270, 0F, 1F, 0F);
				model.renderPipe();
				GL11.glRotated(-270, 0F, 1F, 0F);
			}
			if (node.getSide(EnumFacing.UP).isActive()) {
				model.renderLaserTop();
			} else if (node.getSide(EnumFacing.UP).isConnected()) {
				model.renderPipeTop();
			}
			if (node.getSide(EnumFacing.DOWN).isActive()) {
				GL11.glRotated(180, 0F, 0F, 1F);
				GL11.glTranslated(0, -2F, 0);
				model.renderLaserTop();
				GL11.glRotated(-180, 0F, 0F, 1F);
				GL11.glTranslated(0, 2F, 0);
			} else if (node.getSide(EnumFacing.DOWN).isConnected()) {
				GL11.glRotated(180, 0F, 0F, 1F);
				GL11.glTranslated(0, -2F, 0);
				model.renderPipeTop();
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
