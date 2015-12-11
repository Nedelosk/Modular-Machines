package nedelosk.forestday.client.renderer.tile;

import org.lwjgl.opengl.GL11;

import nedelosk.forestcore.api.utils.RenderUtils;
import nedelosk.forestday.common.blocks.tiles.TileCharcoalKiln;
import nedelosk.forestday.common.core.managers.FBlockManager;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;

public class TileCharcoalKilnRenderer extends TileEntitySpecialRenderer {

	@Override
	public void renderTileEntityAt(TileEntity tile, double p_147500_2_, double p_147500_4_, double p_147500_6_,
			float p_147500_8_) {
		renderTileEntityAt((TileCharcoalKiln) tile, p_147500_2_, p_147500_4_, p_147500_6_, p_147500_8_);
	}

	public void renderTileEntityAt(TileCharcoalKiln kiln, double x, double y, double z, float p_147500_8_) {
		if (kiln.master != null && kiln.master.isMultiblock() || kiln.isMaster && kiln.isMultiblock) {

			GL11.glPushMatrix();
			GL11.glTranslated((float) x + 0.5F, (float) y + 0.5F, (float) z + 0.5F);
			Tessellator t = Tessellator.instance;
			GL11.glPushMatrix();

			RenderUtils.bindBlockTexture();

			IIcon loamIcon = FBlockManager.Gravel.block().getIcon(0, 0);
			IIcon woodIcon = Block.getBlockFromItem(kiln.type.wood.getItem()).getIcon(4,
					kiln.type.wood.getItemDamage());
			if (kiln.isMaster) {
				renderTop(loamIcon, woodIcon);
			} else if (kiln.master.getXCoord() == kiln.xCoord && kiln.master.getYCoord() == kiln.yCoord + 1
					&& kiln.master.getZCoord() - 1 == kiln.zCoord) {
				renderFront(loamIcon, woodIcon);
			} else if (kiln.master.getXCoord() == kiln.xCoord && kiln.master.getYCoord() == kiln.yCoord + 1
					&& kiln.master.getZCoord() + 1 == kiln.zCoord) {
				GL11.glRotated(180, 0F, 0F, 1F);
				GL11.glRotatef(180.0F, 1.0F, 0.0F, 0.0F);
				renderFront(loamIcon, woodIcon);
			} else if (kiln.master.getXCoord() - 1 == kiln.xCoord && kiln.master.getYCoord() == kiln.yCoord + 1
					&& kiln.master.getZCoord() == kiln.zCoord) {
				GL11.glRotatef(90.0F, 0.0F, 1.0F, 0.0F);
				renderFront(loamIcon, woodIcon);
			} else if (kiln.master.getXCoord() + 1 == kiln.xCoord && kiln.master.getYCoord() == kiln.yCoord + 1
					&& kiln.master.getZCoord() == kiln.zCoord) {
				GL11.glRotated(180, 0F, 1F, 0F);
				GL11.glRotatef(90.0F, 0.0F, 1.0F, 0.0F);
				renderFront(loamIcon, woodIcon);
			} else if (kiln.master.getXCoord() + 1 == kiln.xCoord && kiln.master.getYCoord() == kiln.yCoord + 1
					&& kiln.master.getZCoord() + 1 == kiln.zCoord) {
				GL11.glRotated(180, 0F, 1F, 0F);
				GL11.glRotatef(90.0F, 0.0F, 1.0F, 0.0F);
				renderCorner(loamIcon, woodIcon);

			} else if (kiln.master.getXCoord() - 1 == kiln.xCoord && kiln.master.getYCoord() == kiln.yCoord + 1
					&& kiln.master.getZCoord() + 1 == kiln.zCoord) {
				GL11.glRotated(90, 0F, 1F, 0F);
				GL11.glRotatef(90.0F, 0.0F, 1.0F, 0.0F);
				renderCorner(loamIcon, woodIcon);

			} else if (kiln.master.getXCoord() - 1 == kiln.xCoord && kiln.master.getYCoord() == kiln.yCoord + 1
					&& kiln.master.getZCoord() - 1 == kiln.zCoord) {
				GL11.glRotatef(90.0F, 0.0F, 1.0F, 0.0F);

				renderCorner(loamIcon, woodIcon);
			} else if (kiln.master.getXCoord() + 1 == kiln.xCoord && kiln.master.getYCoord() == kiln.yCoord + 1
					&& kiln.master.getZCoord() - 1 == kiln.zCoord) {
				GL11.glRotated(270, 0F, 1F, 0F);
				GL11.glRotatef(90.0F, 0.0F, 1.0F, 0.0F);
				renderCorner(loamIcon, woodIcon);

			} else if (kiln.master.getXCoord() + 1 == kiln.xCoord && kiln.master.getYCoord() + 1 == kiln.yCoord + 1
					&& kiln.master.getZCoord() - 1 == kiln.zCoord) {
				GL11.glRotated(270, 0F, 1F, 0F);
				GL11.glRotatef(90.0F, 0.0F, 1.0F, 0.0F);

				renderCornerTop(loamIcon, woodIcon);

			} else if (kiln.master.getXCoord() + 1 == kiln.xCoord && kiln.master.getYCoord() + 1 == kiln.yCoord + 1
					&& kiln.master.getZCoord() + 1 == kiln.zCoord) {
				GL11.glRotated(180, 0F, 1F, 0F);
				GL11.glRotatef(90.0F, 0.0F, 1.0F, 0.0F);

				renderCornerTop(loamIcon, woodIcon);

			} else if (kiln.master.getXCoord() - 1 == kiln.xCoord && kiln.master.getYCoord() + 1 == kiln.yCoord + 1
					&& kiln.master.getZCoord() - 1 == kiln.zCoord) {
				GL11.glRotatef(90.0F, 0.0F, 1.0F, 0.0F);

				renderCornerTop(loamIcon, woodIcon);

			} else if (kiln.master.getXCoord() - 1 == kiln.xCoord && kiln.master.getYCoord() + 1 == kiln.yCoord + 1
					&& kiln.master.getZCoord() + 1 == kiln.zCoord) {
				GL11.glRotated(90, 0F, 1F, 0F);
				GL11.glRotatef(90.0F, 0.0F, 1.0F, 0.0F);

				renderCornerTop(loamIcon, woodIcon);

			} else if (kiln.master.getXCoord() == kiln.xCoord && kiln.master.getYCoord() + 1 == kiln.yCoord + 1
					&& kiln.master.getZCoord() + 1 == kiln.zCoord) {
				GL11.glRotated(180, 0F, 0F, 1F);
				GL11.glRotatef(180.0F, 1.0F, 0.0F, 0.0F);

				renderFrontTop(loamIcon, woodIcon);
			} else if (kiln.master.getXCoord() == kiln.xCoord && kiln.master.getYCoord() + 1 == kiln.yCoord + 1
					&& kiln.master.getZCoord() - 1 == kiln.zCoord) {
				GL11.glRotated(270, 0F, 1F, 0F);
				GL11.glRotatef(90.0F, 0.0F, 1.0F, 0.0F);

				renderFrontTop(loamIcon, woodIcon);
			} else if (kiln.master.getXCoord() + 1 == kiln.xCoord && kiln.master.getYCoord() + 1 == kiln.yCoord + 1
					&& kiln.master.getZCoord() == kiln.zCoord) {
				GL11.glRotated(180, 0F, 1F, 0F);
				GL11.glRotatef(90.0F, 0.0F, 1.0F, 0.0F);

				renderFrontTop(loamIcon, woodIcon);
			} else if (kiln.master.getXCoord() - 1 == kiln.xCoord && kiln.master.getYCoord() + 1 == kiln.yCoord + 1
					&& kiln.master.getZCoord() == kiln.zCoord) {
				GL11.glRotatef(90.0F, 0.0F, 1.0F, 0.0F);

				renderFrontTop(loamIcon, woodIcon);
			} else if (kiln.master.getXCoord() == kiln.xCoord && kiln.master.getYCoord() == kiln.yCoord + 1
					&& kiln.master.getZCoord() == kiln.zCoord) {
				renderDown(loamIcon, woodIcon);

			}

			GL11.glPopMatrix();
			GL11.glPopMatrix();
		} else {
			renderBlock(kiln, x, y, z);
		}

	}

	public void renderBlock(TileCharcoalKiln kiln, double x, double y, double z) {
		if (kiln.type != null) {
			GL11.glPushMatrix();
			GL11.glTranslated((float) x + 0.5F, (float) y + 0.5F, (float) z + 0.5F);
			Tessellator t = Tessellator.instance;
			GL11.glPushMatrix();

			RenderUtils.bindBlockTexture();

			IIcon woodIconTop = Block.getBlockFromItem(kiln.type.wood.getItem()).getIcon(0,
					kiln.type.wood.getItemDamage());
			IIcon woodIcon = Block.getBlockFromItem(kiln.type.wood.getItem()).getIcon(2,
					kiln.type.wood.getItemDamage());
			;

			t.startDrawingQuads();
			t.setNormal(0, 1, 0);
			t.addVertexWithUV(-0.5, -0.5, -0.5, woodIconTop.getMinU(), woodIconTop.getMinV());
			t.addVertexWithUV(0.5, -0.5, -0.5, woodIconTop.getMaxU(), woodIconTop.getMinV());
			t.addVertexWithUV(0.5, -0.5, 0.5, woodIconTop.getMaxU(), woodIconTop.getMaxV());
			t.addVertexWithUV(-0.5, -0.5, 0.5, woodIconTop.getMinU(), woodIconTop.getMaxV());
			t.draw();

			t.startDrawingQuads();
			t.setNormal(0, 1, 0);
			t.addVertexWithUV(-0.5, 0.5, -0.5, woodIconTop.getMinU(), woodIconTop.getMinV());
			t.addVertexWithUV(-0.5, 0.5, 0.5, woodIconTop.getMinU(), woodIconTop.getMaxV());
			t.addVertexWithUV(0.5, 0.5, 0.5, woodIconTop.getMaxU(), woodIconTop.getMaxV());
			t.addVertexWithUV(0.5, 0.5, -0.5, woodIconTop.getMaxU(), woodIconTop.getMinV());
			t.draw();

			t.startDrawingQuads();
			t.setNormal(0, 1, 0);
			t.addVertexWithUV(0.5, 0.5, 0.5, woodIcon.getMinU(), woodIcon.getMinV());
			t.addVertexWithUV(0.5, -0.5, 0.5, woodIcon.getMinU(), woodIcon.getMaxV());
			t.addVertexWithUV(0.5, -0.5, -0.5, woodIcon.getMaxU(), woodIcon.getMaxV());
			t.addVertexWithUV(0.5, 0.5, -0.5, woodIcon.getMaxU(), woodIcon.getMinV());
			t.draw();

			t.startDrawingQuads();
			t.setNormal(0, 1, 0);
			t.addVertexWithUV(0.5, 0.5, -0.5, woodIcon.getMinU(), woodIcon.getMinV());
			t.addVertexWithUV(0.5, -0.5, -0.5, woodIcon.getMinU(), woodIcon.getMaxV());
			t.addVertexWithUV(-0.5, -0.5, -0.5, woodIcon.getMaxU(), woodIcon.getMaxV());
			t.addVertexWithUV(-0.5, 0.5, -0.5, woodIcon.getMaxU(), woodIcon.getMinV());
			t.draw();

			t.startDrawingQuads();
			t.setNormal(0, 1, 0);
			t.addVertexWithUV(-0.5, 0.5, 0.5, woodIcon.getMinU(), woodIcon.getMinV());
			t.addVertexWithUV(-0.5, -0.5, 0.5, woodIcon.getMinU(), woodIcon.getMaxV());
			t.addVertexWithUV(0.5, -0.5, 0.5, woodIcon.getMaxU(), woodIcon.getMaxV());
			t.addVertexWithUV(0.5, 0.5, 0.5, woodIcon.getMaxU(), woodIcon.getMinV());
			t.draw();

			t.startDrawingQuads();
			t.setNormal(0, 1, 0);
			t.addVertexWithUV(-0.5, 0.5, -0.5, woodIcon.getMinU(), woodIcon.getMinV());
			t.addVertexWithUV(-0.5, -0.5, -0.5, woodIcon.getMinU(), woodIcon.getMaxV());
			t.addVertexWithUV(-0.5, -0.5, 0.5, woodIcon.getMaxU(), woodIcon.getMaxV());
			t.addVertexWithUV(-0.5, 0.5, 0.5, woodIcon.getMaxU(), woodIcon.getMinV());
			t.draw();
			GL11.glPopMatrix();
			GL11.glPopMatrix();
		}
	}

	public void renderTop(IIcon dirtIcon, IIcon woodIcon) {
		Tessellator t = Tessellator.instance;
		t.startDrawingQuads();
		t.setNormal(0, 1, 0);
		t.addVertexWithUV(-0.5, 0.3, -0.5, dirtIcon.getMinU(), dirtIcon.getMinV());
		t.addVertexWithUV(-0.5, 0.3, 0.5, dirtIcon.getMaxU(), dirtIcon.getMinV());
		t.addVertexWithUV(0.5, 0.3, 0.5, dirtIcon.getMaxU(), dirtIcon.getMaxV());
		t.addVertexWithUV(0.5, 0.3, -0.5, dirtIcon.getMinU(), dirtIcon.getMaxV());
		t.draw();
	}

	public void renderDown(IIcon dirtIcon, IIcon woodIcon) {
		Tessellator t = Tessellator.instance;
		t.startDrawingQuads();
		t.setNormal(0, 1, 0);
		t.addVertexWithUV(-0.5, -0.5, -0.5, dirtIcon.getMinU(), dirtIcon.getMinV());
		t.addVertexWithUV(0.5, -0.5, -0.5, dirtIcon.getMaxU(), dirtIcon.getMinV());
		t.addVertexWithUV(0.5, -0.5, 0.5, dirtIcon.getMaxU(), dirtIcon.getMaxV());
		t.addVertexWithUV(-0.5, -0.5, 0.5, dirtIcon.getMinU(), dirtIcon.getMaxV());
		t.draw();
	}

	public void renderFront(IIcon dirtIcon, IIcon woodIcon) {
		Tessellator t = Tessellator.instance;

		t.startDrawingQuads();
		t.setNormal(0, 1, 0);
		t.addVertexWithUV(-0.5, -0.5, -0.5, dirtIcon.getMinU(), dirtIcon.getMinV());
		t.addVertexWithUV(0.5, -0.5, -0.5, dirtIcon.getMaxU(), dirtIcon.getMinV());
		t.addVertexWithUV(0.5, -0.5, 0.5, dirtIcon.getMaxU(), dirtIcon.getMaxV());
		t.addVertexWithUV(-0.5, -0.5, 0.5, dirtIcon.getMinU(), dirtIcon.getMaxV());
		t.draw();

		t.startDrawingQuads();
		t.setNormal(0, 1, 0);
		t.addVertexWithUV(-0.5, 0.5, 0.2, dirtIcon.getMinU(), dirtIcon.getMinV());
		t.addVertexWithUV(-0.5, 0.5, 0.5, dirtIcon.getMaxU(), dirtIcon.getMinV());
		t.addVertexWithUV(0.5, 0.5, 0.5, dirtIcon.getMaxU(), dirtIcon.getMaxV());
		t.addVertexWithUV(0.5, 0.5, 0.2, dirtIcon.getMinU(), dirtIcon.getMaxV());
		t.draw();

		t.startDrawingQuads();
		t.setNormal(0, 1, 0);
		t.addVertexWithUV(0.5, 0.5, 0.5, dirtIcon.getMinU(), dirtIcon.getMinV());
		t.addVertexWithUV(0.5, -0.5, 0.5, dirtIcon.getMaxU(), dirtIcon.getMinV());
		t.addVertexWithUV(0.5, -0.5, -0.3, dirtIcon.getMaxU(), dirtIcon.getMaxV());
		t.addVertexWithUV(0.5, 0.5, 0.2, dirtIcon.getMinU(), dirtIcon.getMaxV());
		t.draw();

		t.startDrawingQuads();
		t.setNormal(0, 1, 0);
		t.addVertexWithUV(-0.5, 0.5, 0.2, dirtIcon.getMinU(), dirtIcon.getMinV());
		t.addVertexWithUV(-0.5, -0.5, -0.3, dirtIcon.getMaxU(), dirtIcon.getMinV());
		t.addVertexWithUV(-0.5, -0.5, 0.5, dirtIcon.getMaxU(), dirtIcon.getMaxV());
		t.addVertexWithUV(-0.5, 0.5, 0.5, dirtIcon.getMinU(), dirtIcon.getMaxV());
		t.draw();

		// Front
		t.startDrawingQuads();
		t.setNormal(0, 1, 0);
		t.addVertexWithUV(0.5, 0.5, 0.2, dirtIcon.getMinU(), dirtIcon.getMinV());
		t.addVertexWithUV(0.5, -0.5, -0.3, dirtIcon.getMaxU(), dirtIcon.getMinV());
		t.addVertexWithUV(-0.5, -0.5, -0.3, dirtIcon.getMaxU(), dirtIcon.getMaxV());
		t.addVertexWithUV(-0.5, 0.5, 0.2, dirtIcon.getMinU(), dirtIcon.getMaxV());
		t.draw();

		// Back
		t.startDrawingQuads();
		t.setNormal(0, 1, 0);
		t.addVertexWithUV(-0.5, 0.5, 0.5, dirtIcon.getMinU(), dirtIcon.getMinV());
		t.addVertexWithUV(-0.5, -0.5, 0.5, dirtIcon.getMaxU(), dirtIcon.getMinV());
		t.addVertexWithUV(0.5, -0.5, 0.5, dirtIcon.getMaxU(), dirtIcon.getMaxV());
		t.addVertexWithUV(0.5, 0.5, 0.5, dirtIcon.getMinU(), dirtIcon.getMaxV());
		t.draw();

		// Wood

		// 0

		// Front
		t.startDrawingQuads();
		t.setNormal(0, 1, 0);
		t.addVertexWithUV(0.1, 0.3, 0.0, woodIcon.getMaxU(), woodIcon.getMaxV());
		t.addVertexWithUV(0.1, -0.5, -0.5, woodIcon.getMaxU(), woodIcon.getMinV());
		t.addVertexWithUV(-0.1, -0.5, -0.5, woodIcon.getMinU(), woodIcon.getMinV());
		t.addVertexWithUV(-0.1, 0.3, 0.0, woodIcon.getMinU(), woodIcon.getMaxV());
		t.draw();

		t.startDrawingQuads();
		t.setNormal(0, 1, 0);
		t.addVertexWithUV(-0.1, 0.3, 0.0, woodIcon.getMaxU(), woodIcon.getMaxV());
		t.addVertexWithUV(-0.1, -0.5, -0.5, woodIcon.getMaxU(), woodIcon.getMinV());
		t.addVertexWithUV(-0.1, -0.5, -0.3, woodIcon.getMinU(), woodIcon.getMinV());
		t.addVertexWithUV(-0.1, 0.3, 0.2, woodIcon.getMinU(), woodIcon.getMaxV());
		t.draw();

		t.startDrawingQuads();
		t.setNormal(0, 1, 0);
		t.addVertexWithUV(0.1, 0.3, 0.2, woodIcon.getMaxU(), woodIcon.getMaxV());
		t.addVertexWithUV(0.1, -0.5, -0.3, woodIcon.getMaxU(), woodIcon.getMinV());
		t.addVertexWithUV(0.1, -0.5, -0.5, woodIcon.getMinU(), woodIcon.getMinV());
		t.addVertexWithUV(0.1, 0.3, 0.0, woodIcon.getMinU(), woodIcon.getMaxV());
		t.draw();

		// 1

		// Front
		t.startDrawingQuads();
		t.setNormal(0, 1, 0);
		t.addVertexWithUV(0.5, 0.3, 0.0, woodIcon.getMaxU(), woodIcon.getMaxV());
		t.addVertexWithUV(0.5, -0.5, -0.5, woodIcon.getMaxU(), woodIcon.getMinV());
		t.addVertexWithUV(0.3, -0.5, -0.5, woodIcon.getMinU(), woodIcon.getMinV());
		t.addVertexWithUV(0.3, 0.3, 0.0, woodIcon.getMinU(), woodIcon.getMaxV());
		t.draw();

		t.startDrawingQuads();
		t.setNormal(0, 1, 0);
		t.addVertexWithUV(0.3, 0.3, 0.0, woodIcon.getMaxU(), woodIcon.getMaxV());
		t.addVertexWithUV(0.3, -0.5, -0.5, woodIcon.getMaxU(), woodIcon.getMinV());
		t.addVertexWithUV(0.3, -0.5, -0.3, woodIcon.getMinU(), woodIcon.getMinV());
		t.addVertexWithUV(0.3, 0.3, 0.2, woodIcon.getMinU(), woodIcon.getMaxV());
		t.draw();

		t.startDrawingQuads();
		t.setNormal(0, 1, 0);
		t.addVertexWithUV(0.5, 0.3, 0.2, woodIcon.getMaxU(), woodIcon.getMaxV());
		t.addVertexWithUV(0.5, -0.5, -0.3, woodIcon.getMaxU(), woodIcon.getMinV());
		t.addVertexWithUV(0.5, -0.5, -0.5, woodIcon.getMinU(), woodIcon.getMinV());
		t.addVertexWithUV(0.5, 0.3, 0.0, woodIcon.getMinU(), woodIcon.getMaxV());
		t.draw();

		// 2

		// Front
		t.startDrawingQuads();
		t.setNormal(0, 1, 0);
		t.addVertexWithUV(-0.3, 0.3, 0.0, woodIcon.getMaxU(), woodIcon.getMaxV());
		t.addVertexWithUV(-0.3, -0.5, -0.5, woodIcon.getMaxU(), woodIcon.getMinV());
		t.addVertexWithUV(-0.5, -0.5, -0.5, woodIcon.getMinU(), woodIcon.getMinV());
		t.addVertexWithUV(-0.5, 0.3, 0.0, woodIcon.getMinU(), woodIcon.getMaxV());
		t.draw();

		t.startDrawingQuads();
		t.setNormal(0, 1, 0);
		t.addVertexWithUV(-0.5, 0.3, 0.0, woodIcon.getMaxU(), woodIcon.getMaxV());
		t.addVertexWithUV(-0.5, -0.5, -0.5, woodIcon.getMaxU(), woodIcon.getMinV());
		t.addVertexWithUV(-0.5, -0.5, -0.3, woodIcon.getMinU(), woodIcon.getMinV());
		t.addVertexWithUV(-0.5, 0.3, 0.2, woodIcon.getMinU(), woodIcon.getMaxV());
		t.draw();

		t.startDrawingQuads();
		t.setNormal(0, 1, 0);
		t.addVertexWithUV(-0.3, 0.3, 0.2, woodIcon.getMaxU(), woodIcon.getMaxV());
		t.addVertexWithUV(-0.3, -0.5, -0.3, woodIcon.getMaxU(), woodIcon.getMinV());
		t.addVertexWithUV(-0.3, -0.5, -0.5, woodIcon.getMinU(), woodIcon.getMinV());
		t.addVertexWithUV(-0.3, 0.3, 0.0, woodIcon.getMinU(), woodIcon.getMaxV());
		t.draw();

		// 3

		t.startDrawingQuads();
		t.setNormal(0, 1, 0);
		t.addVertexWithUV(-0.8, 0.3, 0.2, woodIcon.getMinU(), woodIcon.getMinV());
		t.addVertexWithUV(-1, 0.3, -0, woodIcon.getMaxU(), woodIcon.getMinV());
		t.addVertexWithUV(1, 0.3, -0, woodIcon.getMaxU(), woodIcon.getMaxV());
		t.addVertexWithUV(0.8, 0.3, 0.2, woodIcon.getMinU(), woodIcon.getMaxV());
		t.draw();

		t.startDrawingQuads();
		t.setNormal(0, 1, 0);
		t.addVertexWithUV(-0.8, 0.5, 0.2, woodIcon.getMinU(), woodIcon.getMinV());
		t.addVertexWithUV(0.8, 0.5, 0.2, woodIcon.getMaxU(), woodIcon.getMinV());
		t.addVertexWithUV(1, 0.5, 0, woodIcon.getMaxU(), woodIcon.getMaxV());
		t.addVertexWithUV(-1, 0.5, 0, woodIcon.getMinU(), woodIcon.getMaxV());
		t.draw();

		t.startDrawingQuads();
		t.setNormal(0, 1, 0);
		t.addVertexWithUV(1, 0.5, 0.0, woodIcon.getMinU(), woodIcon.getMinV());
		t.addVertexWithUV(1, 0.3, 0.0, woodIcon.getMaxU(), woodIcon.getMinV());
		t.addVertexWithUV(-1, 0.3, 0.0, woodIcon.getMaxU(), woodIcon.getMaxV());
		t.addVertexWithUV(-1, 0.5, 0.0, woodIcon.getMinU(), woodIcon.getMaxV());
		t.draw();
	}

	public void renderCorner(IIcon dirtIcon, IIcon woodIcon) {
		Tessellator t = Tessellator.instance;

		t.startDrawingQuads();
		t.setNormal(0, 1, 0);
		t.addVertexWithUV(-0.5, -0.5, -0.3, dirtIcon.getMinU(), dirtIcon.getMinV());
		t.addVertexWithUV(0.3, -0.5, -0.3, dirtIcon.getMaxU(), dirtIcon.getMinV());
		t.addVertexWithUV(0.3, -0.5, 0.5, dirtIcon.getMaxU(), dirtIcon.getMaxV());
		t.addVertexWithUV(-0.5, -0.5, 0.5, dirtIcon.getMinU(), dirtIcon.getMaxV());
		t.draw();

		t.startDrawingQuads();
		t.setNormal(0, 1, 0);
		t.addVertexWithUV(-0.2, 0.5, 0.5, dirtIcon.getMinU(), dirtIcon.getMinV());
		t.addVertexWithUV(0.3, -0.5, 0.5, dirtIcon.getMaxU(), dirtIcon.getMinV());
		t.addVertexWithUV(0.3, -0.5, -0.3, dirtIcon.getMaxU(), dirtIcon.getMaxV());
		t.addVertexWithUV(-0.2, 0.5, 0.2, dirtIcon.getMinU(), dirtIcon.getMaxV());
		t.draw();

		t.startDrawingQuads();
		t.setNormal(0, 1, 0);
		t.addVertexWithUV(-0.2, 0.5, 0.2, dirtIcon.getMinU(), dirtIcon.getMinV());
		t.addVertexWithUV(0.3, -0.5, -0.3, dirtIcon.getMaxU(), dirtIcon.getMinV());
		t.addVertexWithUV(-0.5, -0.5, -0.3, dirtIcon.getMaxU(), dirtIcon.getMaxV());
		t.addVertexWithUV(-0.5, 0.5, 0.2, dirtIcon.getMinU(), dirtIcon.getMaxV());
		t.draw();

		// Front
		t.startDrawingQuads();
		t.setNormal(0, 1, 0);
		t.addVertexWithUV(-0.2, 0.3, 0.0, woodIcon.getMaxU(), woodIcon.getMaxV());
		t.addVertexWithUV(0.3, -0.5, -0.5, woodIcon.getMaxU(), woodIcon.getMinV());
		t.addVertexWithUV(0.1, -0.5, -0.5, woodIcon.getMinU(), woodIcon.getMinV());
		t.addVertexWithUV(-0.4, 0.3, 0.0, woodIcon.getMinU(), woodIcon.getMaxV());
		t.draw();

		t.startDrawingQuads();
		t.setNormal(0, 1, 0);
		t.addVertexWithUV(-0.4, 0.3, 0.0, woodIcon.getMaxU(), woodIcon.getMaxV());
		t.addVertexWithUV(0.1, -0.5, -0.5, woodIcon.getMaxU(), woodIcon.getMinV());
		t.addVertexWithUV(0.1, -0.5, -0.3, woodIcon.getMinU(), woodIcon.getMinV());
		t.addVertexWithUV(-0.4, 0.3, 0.2, woodIcon.getMinU(), woodIcon.getMaxV());
		t.draw();

		t.startDrawingQuads();
		t.setNormal(0, 1, 0);
		t.addVertexWithUV(-0.2, 0.3, 0.2, woodIcon.getMaxU(), woodIcon.getMaxV());
		t.addVertexWithUV(0.3, -0.5, -0.3, woodIcon.getMaxU(), woodIcon.getMinV());
		t.addVertexWithUV(0.3, -0.5, -0.5, woodIcon.getMinU(), woodIcon.getMinV());
		t.addVertexWithUV(-0.2, 0.3, 0.0, woodIcon.getMinU(), woodIcon.getMaxV());
		t.draw();

		GL11.glRotatef(270.0F, 0.0F, 1.0F, 0.0F);

		// Front
		t.startDrawingQuads();
		t.setNormal(0, 1, 0);
		t.addVertexWithUV(0.4, 0.3, 0.0, woodIcon.getMaxU(), woodIcon.getMaxV());
		t.addVertexWithUV(-0.1, -0.5, -0.5, woodIcon.getMaxU(), woodIcon.getMinV());
		t.addVertexWithUV(-0.3, -0.5, -0.5, woodIcon.getMinU(), woodIcon.getMinV());
		t.addVertexWithUV(0.2, 0.3, 0.0, woodIcon.getMinU(), woodIcon.getMaxV());
		t.draw();

		t.startDrawingQuads();
		t.setNormal(0, 1, 0);
		t.addVertexWithUV(0.2, 0.3, 0.0, woodIcon.getMaxU(), woodIcon.getMaxV());
		t.addVertexWithUV(-0.3, -0.5, -0.5, woodIcon.getMaxU(), woodIcon.getMinV());
		t.addVertexWithUV(-0.3, -0.5, -0.3, woodIcon.getMinU(), woodIcon.getMinV());
		t.addVertexWithUV(0.2, 0.3, 0.2, woodIcon.getMinU(), woodIcon.getMaxV());
		t.draw();

		t.startDrawingQuads();
		t.setNormal(0, 1, 0);
		t.addVertexWithUV(0.4, 0.3, 0.2, woodIcon.getMaxU(), woodIcon.getMaxV());
		t.addVertexWithUV(-0.1, -0.5, -0.3, woodIcon.getMaxU(), woodIcon.getMinV());
		t.addVertexWithUV(-0.1, -0.5, -0.5, woodIcon.getMinU(), woodIcon.getMinV());
		t.addVertexWithUV(0.4, 0.3, 0.0, woodIcon.getMinU(), woodIcon.getMaxV());
		t.draw();

		GL11.glRotatef(90.0F, 0.0F, 1.0F, 0.0F);
	}

	public void renderFrontTop(IIcon dirtIcon, IIcon woodIcon) {
		Tessellator t = Tessellator.instance;

		t.startDrawingQuads();
		t.setNormal(0, 1, 0);
		t.addVertexWithUV(0.5, 0.1, 0.5, dirtIcon.getMinU(), dirtIcon.getMinV());
		t.addVertexWithUV(0.5, -0.5, 0.2, dirtIcon.getMaxU(), dirtIcon.getMinV());
		t.addVertexWithUV(-0.5, -0.5, 0.2, dirtIcon.getMaxU(), dirtIcon.getMaxV());
		t.addVertexWithUV(-0.5, 0.1, 0.5, dirtIcon.getMinU(), dirtIcon.getMaxV());
		t.draw();

		// Wood 0

		t.startDrawingQuads();
		t.setNormal(0, 1, 0);
		t.addVertexWithUV(-0.3, 0.1, 0.3, woodIcon.getMaxU(), woodIcon.getMaxV());
		t.addVertexWithUV(-0.3, -0.5, -0.0, woodIcon.getMaxU(), woodIcon.getMinV());
		t.addVertexWithUV(-0.5, -0.5, -0.0, woodIcon.getMinU(), woodIcon.getMinV());
		t.addVertexWithUV(-0.5, 0.1, 0.3, woodIcon.getMinU(), woodIcon.getMaxV());
		t.draw();

		t.startDrawingQuads();
		t.setNormal(0, 1, 0);
		t.addVertexWithUV(-0.5, 0.1, 0.3, woodIcon.getMaxU(), woodIcon.getMaxV());
		t.addVertexWithUV(-0.5, -0.5, 0.0, woodIcon.getMaxU(), woodIcon.getMinV());
		t.addVertexWithUV(-0.5, -0.5, 0.2, woodIcon.getMinU(), woodIcon.getMinV());
		t.addVertexWithUV(-0.5, 0.1, 0.5, woodIcon.getMinU(), woodIcon.getMaxV());
		t.draw();

		t.startDrawingQuads();
		t.setNormal(0, 1, 0);
		t.addVertexWithUV(-0.3, 0.1, 0.5, woodIcon.getMaxU(), woodIcon.getMaxV());
		t.addVertexWithUV(-0.3, -0.5, 0.2, woodIcon.getMaxU(), woodIcon.getMinV());
		t.addVertexWithUV(-0.3, -0.5, 0.0, woodIcon.getMinU(), woodIcon.getMinV());
		t.addVertexWithUV(-0.3, 0.1, 0.3, woodIcon.getMinU(), woodIcon.getMaxV());
		t.draw();

		// Wood 1

		t.startDrawingQuads();
		t.setNormal(0, 1, 0);
		t.addVertexWithUV(0.1, 0.1, 0.3, woodIcon.getMaxU(), woodIcon.getMaxV());
		t.addVertexWithUV(0.1, -0.5, -0.0, woodIcon.getMaxU(), woodIcon.getMinV());
		t.addVertexWithUV(-0.1, -0.5, -0.0, woodIcon.getMinU(), woodIcon.getMinV());
		t.addVertexWithUV(-0.1, 0.1, 0.3, woodIcon.getMinU(), woodIcon.getMaxV());
		t.draw();

		t.startDrawingQuads();
		t.setNormal(0, 1, 0);
		t.addVertexWithUV(-0.1, 0.1, 0.3, woodIcon.getMaxU(), woodIcon.getMaxV());
		t.addVertexWithUV(-0.1, -0.5, 0.0, woodIcon.getMaxU(), woodIcon.getMinV());
		t.addVertexWithUV(-0.1, -0.5, 0.2, woodIcon.getMinU(), woodIcon.getMinV());
		t.addVertexWithUV(-0.1, 0.1, 0.5, woodIcon.getMinU(), woodIcon.getMaxV());
		t.draw();

		t.startDrawingQuads();
		t.setNormal(0, 1, 0);
		t.addVertexWithUV(0.1, 0.1, 0.5, woodIcon.getMaxU(), woodIcon.getMaxV());
		t.addVertexWithUV(0.1, -0.5, 0.2, woodIcon.getMaxU(), woodIcon.getMinV());
		t.addVertexWithUV(0.1, -0.5, 0.0, woodIcon.getMinU(), woodIcon.getMinV());
		t.addVertexWithUV(0.1, 0.1, 0.3, woodIcon.getMinU(), woodIcon.getMaxV());
		t.draw();

		// Wood 2

		t.startDrawingQuads();
		t.setNormal(0, 1, 0);
		t.addVertexWithUV(0.5, 0.1, 0.3, woodIcon.getMaxU(), woodIcon.getMaxV());
		t.addVertexWithUV(0.5, -0.5, -0.0, woodIcon.getMaxU(), woodIcon.getMinV());
		t.addVertexWithUV(0.3, -0.5, -0.0, woodIcon.getMinU(), woodIcon.getMinV());
		t.addVertexWithUV(0.3, 0.1, 0.3, woodIcon.getMinU(), woodIcon.getMaxV());
		t.draw();

		t.startDrawingQuads();
		t.setNormal(0, 1, 0);
		t.addVertexWithUV(0.3, 0.1, 0.3, woodIcon.getMaxU(), woodIcon.getMaxV());
		t.addVertexWithUV(0.3, -0.5, 0.0, woodIcon.getMaxU(), woodIcon.getMinV());
		t.addVertexWithUV(0.3, -0.5, 0.2, woodIcon.getMinU(), woodIcon.getMinV());
		t.addVertexWithUV(0.3, 0.1, 0.5, woodIcon.getMinU(), woodIcon.getMaxV());
		t.draw();

		t.startDrawingQuads();
		t.setNormal(0, 1, 0);
		t.addVertexWithUV(0.5, 0.1, 0.5, woodIcon.getMaxU(), woodIcon.getMaxV());
		t.addVertexWithUV(0.5, -0.5, 0.2, woodIcon.getMaxU(), woodIcon.getMinV());
		t.addVertexWithUV(0.5, -0.5, 0.0, woodIcon.getMinU(), woodIcon.getMinV());
		t.addVertexWithUV(0.5, 0.1, 0.3, woodIcon.getMinU(), woodIcon.getMaxV());
		t.draw();

		// Wood 3

		t.startDrawingQuads();
		t.setNormal(0, 1, 0);
		t.addVertexWithUV(-0.7, 0.3, 0.3, woodIcon.getMinU(), woodIcon.getMinV());
		t.addVertexWithUV(-0.5, 0.3, 0.5, woodIcon.getMaxU(), woodIcon.getMinV());
		t.addVertexWithUV(0.5, 0.3, 0.5, woodIcon.getMaxU(), woodIcon.getMaxV());
		t.addVertexWithUV(0.7, 0.3, 0.3, woodIcon.getMinU(), woodIcon.getMaxV());
		t.draw();

		t.startDrawingQuads();
		t.setNormal(0, 1, 0);
		t.addVertexWithUV(-0.7, 0.1, 0.3, woodIcon.getMinU(), woodIcon.getMinV());
		t.addVertexWithUV(0.7, 0.1, 0.3, woodIcon.getMaxU(), woodIcon.getMinV());
		t.addVertexWithUV(0.5, 0.1, 0.5, woodIcon.getMaxU(), woodIcon.getMaxV());
		t.addVertexWithUV(-0.5, 0.1, 0.5, woodIcon.getMinU(), woodIcon.getMaxV());
		t.draw();

		t.startDrawingQuads();
		t.setNormal(0, 1, 0);
		t.addVertexWithUV(0.7, 0.3, 0.3, woodIcon.getMinU(), woodIcon.getMinV());
		t.addVertexWithUV(0.7, 0.1, 0.3, woodIcon.getMaxU(), woodIcon.getMinV());
		t.addVertexWithUV(-0.7, 0.1, 0.3, woodIcon.getMaxU(), woodIcon.getMaxV());
		t.addVertexWithUV(-0.7, 0.3, 0.3, woodIcon.getMinU(), woodIcon.getMaxV());
		t.draw();
	}

	public void renderCornerTop(IIcon dirtIcon, IIcon woodIcon) {
		Tessellator t = Tessellator.instance;

		t.startDrawingQuads();
		t.setNormal(0, 1, 0);
		t.addVertexWithUV(-0.5, 0.1, 0.5, dirtIcon.getMinU(), dirtIcon.getMinV());
		t.addVertexWithUV(-0.2, -0.5, 0.5, dirtIcon.getMaxU(), dirtIcon.getMinV());
		t.addVertexWithUV(-0.2, -0.5, 0.2, dirtIcon.getMaxU(), dirtIcon.getMaxV());
		t.addVertexWithUV(-0.5, 0.1, 0.5, dirtIcon.getMinU(), dirtIcon.getMaxV());
		t.draw();

		t.startDrawingQuads();
		t.setNormal(0, 1, 0);
		t.addVertexWithUV(-0.5, 0.1, 0.5, dirtIcon.getMinU(), dirtIcon.getMinV());
		t.addVertexWithUV(-0.2, -0.5, 0.2, dirtIcon.getMaxU(), dirtIcon.getMinV());
		t.addVertexWithUV(-0.5, -0.5, 0.2, dirtIcon.getMaxU(), dirtIcon.getMaxV());
		t.addVertexWithUV(-0.5, 0.1, 0.5, dirtIcon.getMinU(), dirtIcon.getMaxV());
		t.draw();

		// Front
		/*
		 * t.startDrawingQuads(); t.setNormal(0, 1, 0); t.addVertexWithUV(-0.7,
		 * 0.5, 0.5, woodIcon.getMaxU(), woodIcon.getMaxV());
		 * t.addVertexWithUV(-0.2, -0.5, 0.0, woodIcon.getMaxU(),
		 * woodIcon.getMinV()); t.addVertexWithUV(-0.4, -0.5, 0.0,
		 * woodIcon.getMinU(), woodIcon.getMinV()); t.addVertexWithUV(-0.9, 0.5,
		 * 0.5, woodIcon.getMinU(), woodIcon.getMaxV()); t.draw();
		 * 
		 * t.startDrawingQuads(); t.setNormal(0, 1, 0); t.addVertexWithUV(-0.9,
		 * 0.5, 0.5, woodIcon.getMaxU(), woodIcon.getMaxV());
		 * t.addVertexWithUV(-0.4, -0.5, 0.0, woodIcon.getMaxU(),
		 * woodIcon.getMinV()); t.addVertexWithUV(-0.4, -0.5, 0.2,
		 * woodIcon.getMinU(), woodIcon.getMinV()); t.addVertexWithUV(-0.9, 0.5,
		 * 0.7, woodIcon.getMinU(), woodIcon.getMaxV()); t.draw();
		 * 
		 * t.startDrawingQuads(); t.setNormal(0, 1, 0); t.addVertexWithUV(-0.7,
		 * 0.5, 0.7, woodIcon.getMaxU(), woodIcon.getMaxV());
		 * t.addVertexWithUV(-0.2, -0.5, 0.2, woodIcon.getMaxU(),
		 * woodIcon.getMinV()); t.addVertexWithUV(-0.2, -0.5, 0.0,
		 * woodIcon.getMinU(), woodIcon.getMinV()); t.addVertexWithUV(-0.7, 0.5,
		 * 0.5, woodIcon.getMinU(), woodIcon.getMaxV()); t.draw();
		 * 
		 * GL11.glRotatef(270.0F, 0.0F, 1.0F, 0.0F);
		 * 
		 * //Front t.startDrawingQuads(); t.setNormal(0, 1, 0);
		 * t.addVertexWithUV(0.9, -0.1, 0.5, woodIcon.getMaxU(),
		 * woodIcon.getMaxV()); t.addVertexWithUV(0.4, -0.5, 0.0,
		 * woodIcon.getMaxU(), woodIcon.getMinV()); t.addVertexWithUV(0.2, -0.5,
		 * 0.0, woodIcon.getMinU(), woodIcon.getMinV()); t.addVertexWithUV(0.7,
		 * 0.1, 0.5, woodIcon.getMinU(), woodIcon.getMaxV()); t.draw();
		 * 
		 * t.startDrawingQuads(); t.setNormal(0, 1, 0); t.addVertexWithUV(0.7,
		 * 0.5, 0.5, woodIcon.getMaxU(), woodIcon.getMaxV());
		 * t.addVertexWithUV(0.2, -0.5, 0.0, woodIcon.getMaxU(),
		 * woodIcon.getMinV()); t.addVertexWithUV(0.2, -0.5, 0.2,
		 * woodIcon.getMinU(), woodIcon.getMinV()); t.addVertexWithUV(0.7, 0.5,
		 * 0.7, woodIcon.getMinU(), woodIcon.getMaxV()); t.draw();
		 * 
		 * t.startDrawingQuads(); t.setNormal(0, 1, 0); t.addVertexWithUV(0.9,
		 * 0.5, 0.7, woodIcon.getMaxU(), woodIcon.getMaxV());
		 * t.addVertexWithUV(0.4, -0.5, 0.2, woodIcon.getMaxU(),
		 * woodIcon.getMinV()); t.addVertexWithUV(0.4, -0.5, 0.0,
		 * woodIcon.getMinU(), woodIcon.getMinV()); t.addVertexWithUV(0.9, 0.5,
		 * 0.5, woodIcon.getMinU(), woodIcon.getMaxV()); t.draw();
		 * 
		 * GL11.glRotatef(90.0F, 0.0F, 1.0F, 0.0F);
		 */
	}

}
