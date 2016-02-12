package de.nedelosk.forestmods.client.render.tile;

import org.lwjgl.opengl.GL11;

import de.nedelosk.forestcore.utils.RenderUtil;
import de.nedelosk.forestmods.api.transport.node.ITransportNode;
import de.nedelosk.forestmods.client.render.models.ModelTransportNode;
import de.nedelosk.forestmods.common.transport.node.TileEntityTransportNode;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.util.ForgeDirection;

public class TileTransportNodeRenderer extends TileEntitySpecialRenderer {

	public static final ResourceLocation textureModel = new ResourceLocation("forestmods", "textures/models/transport_node.png");
	private ModelTransportNode model;

	public TileTransportNodeRenderer() {
		this.model = new ModelTransportNode();
	}

	@Override
	public void renderTileEntityAt(TileEntity entity, double x, double y, double z, float f) {
		TileEntityTransportNode tNode = (TileEntityTransportNode) entity;
		ITransportNode node = tNode.getPart();
		GL11.glPushMatrix();
		GL11.glTranslated((float) x + 0.5F, (float) y + 1.5F, (float) z + 0.5F);
		GL11.glRotated(180, 0F, 0F, 1F);
		GL11.glRotatef(180.0F, 0.0F, 1.0F, 0.0F);
		GL11.glPushMatrix();
		RenderUtil.bindTexture(textureModel);
		model.render();
		if (node.getSide(ForgeDirection.SOUTH).isActive()) {
			model.renderLaser();
		} else if (node.getSide(ForgeDirection.SOUTH).isConnected()) {
			model.renderPipe();
		}
		if (node.getSide(ForgeDirection.WEST).isActive()) {
			GL11.glRotated(90, 0F, 1F, 0F);
			model.renderLaser();
			GL11.glRotated(-90, 0F, 1F, 0F);
		} else if (node.getSide(ForgeDirection.WEST).isConnected()) {
			GL11.glRotated(90, 0F, 1F, 0F);
			model.renderPipe();
			GL11.glRotated(-90, 0F, 1F, 0F);
		}
		if (node.getSide(ForgeDirection.NORTH).isActive()) {
			GL11.glRotated(180, 0F, 1F, 0F);
			model.renderLaser();
			GL11.glRotated(-180, 0F, 1F, 0F);
		} else if (node.getSide(ForgeDirection.NORTH).isConnected()) {
			GL11.glRotated(180, 0F, 1F, 0F);
			model.renderPipe();
			GL11.glRotated(-180, 0F, 1F, 0F);
		}
		if (node.getSide(ForgeDirection.EAST).isActive()) {
			GL11.glRotated(270, 0F, 1F, 0F);
			model.renderLaser();
			GL11.glRotated(-270, 0F, 1F, 0F);
		} else if (node.getSide(ForgeDirection.EAST).isConnected()) {
			GL11.glRotated(270, 0F, 1F, 0F);
			model.renderPipe();
			GL11.glRotated(-270, 0F, 1F, 0F);
		}
		if (node.getSide(ForgeDirection.UP).isActive()) {
			model.renderLaserTop();
		} else if (node.getSide(ForgeDirection.UP).isConnected()) {
			model.renderPipeTop();
		}
		if (node.getSide(ForgeDirection.DOWN).isActive()) {
			GL11.glRotated(180, 0F, 0F, 1F);
			GL11.glTranslated(0, -2F, 0);
			model.renderLaserTop();
			GL11.glRotated(-180, 0F, 0F, 1F);
			GL11.glTranslated(0, 2F, 0);
		} else if (node.getSide(ForgeDirection.DOWN).isConnected()) {
			GL11.glRotated(180, 0F, 0F, 1F);
			GL11.glTranslated(0, -2F, 0);
			model.renderPipeTop();
			GL11.glRotated(-180, 0F, 0F, 1F);
			GL11.glTranslated(0, 2F, 0);
		}
		GL11.glPopMatrix();
		GL11.glPopMatrix();
	}

	public void renderItem() {
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
