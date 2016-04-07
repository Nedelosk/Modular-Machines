package de.nedelosk.forestmods.client.render.tile;

import static de.nedelosk.forestmods.common.multiblocks.charcoal.CharcoalKilnPosition.BACK;
import static de.nedelosk.forestmods.common.multiblocks.charcoal.CharcoalKilnPosition.BACK_LEFT;
import static de.nedelosk.forestmods.common.multiblocks.charcoal.CharcoalKilnPosition.BACK_RIGHT;
import static de.nedelosk.forestmods.common.multiblocks.charcoal.CharcoalKilnPosition.FRONT;
import static de.nedelosk.forestmods.common.multiblocks.charcoal.CharcoalKilnPosition.FRONT_LEFT;
import static de.nedelosk.forestmods.common.multiblocks.charcoal.CharcoalKilnPosition.FRONT_RIGHT;
import static de.nedelosk.forestmods.common.multiblocks.charcoal.CharcoalKilnPosition.LEFT;
import static de.nedelosk.forestmods.common.multiblocks.charcoal.CharcoalKilnPosition.RIGHT;

import org.lwjgl.opengl.GL11;

import de.nedelosk.forestcore.utils.RenderUtil;
import de.nedelosk.forestmods.client.render.CharcoalKilnAccessWrapper;
import de.nedelosk.forestmods.common.blocks.tile.TileCharcoalKiln;
import de.nedelosk.forestmods.common.core.BlockManager;
import de.nedelosk.forestmods.common.multiblocks.charcoal.CharcoalKilnPosition;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;

public class TileCharcoalKilnRenderer extends TileEntitySpecialRenderer {

	@Override
	public void renderTileEntityAt(TileEntity tile, double p_147500_2_, double p_147500_4_, double p_147500_6_, float p_147500_8_) {
		renderTileEntityAt((TileCharcoalKiln) tile, p_147500_2_, p_147500_4_, p_147500_6_, p_147500_8_);
	}

	public void renderTileEntityAt(TileCharcoalKiln kiln, double x, double y, double z, float p_147500_8_) {
		if (kiln.getWoodStack() == null) {
			return;
		}
		boolean withWood = false;
		if (kiln.isConnected() && kiln.getController().isAssembled()) {
			int brightness = kiln.getBlockType().getMixedBrightnessForBlock(kiln.getWorldObj(), kiln.xCoord, kiln.yCoord, kiln.zCoord);
			GL11.glPushMatrix();
			GL11.glTranslated((float) x + 0.5F, (float) y + 0.5F, (float) z + 0.5F);
			Tessellator t = Tessellator.instance;
			GL11.glPushMatrix();
			GL11.glDisable(GL11.GL_LIGHTING);
			RenderUtil.bindBlockTexture();
			boolean isTop = false;
			if (kiln.getController().getMaximumCoord().y == kiln.yCoord) {
				isTop = true;
			}
			IBlockAccess wrapper = new CharcoalKilnAccessWrapper(kiln.getWorldObj(), kiln.getWoodStack());
			CharcoalKilnPosition pos = kiln.getKilnPosition();
			IIcon loamIcon = BlockManager.blockGravel.getIcon(0, 0);
			IIcon woodIcon = Block.getBlockFromItem(kiln.getWoodStack().getItem()).getIcon(wrapper, kiln.xCoord, kiln.yCoord, kiln.zCoord, 4);
			IIcon woodTop = Block.getBlockFromItem(kiln.getWoodStack().getItem()).getIcon(wrapper, kiln.xCoord, kiln.yCoord, kiln.zCoord, 1);
			if (pos == BACK) {
				renderFront(loamIcon, woodIcon, woodTop, brightness);
			} else if (pos == FRONT) {
				GL11.glRotated(180, 0F, 0F, 1F);
				GL11.glRotatef(180.0F, 1.0F, 0.0F, 0.0F);
				renderFront(loamIcon, woodIcon, woodTop, brightness);
			} else if (pos == LEFT) {
				GL11.glRotatef(90.0F, 0.0F, 1.0F, 0.0F);
				renderFront(loamIcon, woodIcon, woodTop, brightness);
			} else if (pos == RIGHT) {
				GL11.glRotated(180, 0F, 1F, 0F);
				GL11.glRotatef(90.0F, 0.0F, 1.0F, 0.0F);
				renderFront(loamIcon, woodIcon, woodTop, brightness);
			} else if (pos == BACK_RIGHT) {
				GL11.glRotated(180, 0F, 1F, 0F);
				GL11.glRotatef(90.0F, 0.0F, 1.0F, 0.0F);
				renderCorner(loamIcon, woodIcon, woodTop, brightness, isTop);
			} else if (pos == BACK_LEFT) {
				GL11.glRotated(90, 0F, 1F, 0F);
				GL11.glRotatef(90.0F, 0.0F, 1.0F, 0.0F);
				renderCorner(loamIcon, woodIcon, woodTop, brightness, isTop);
			} else if (pos == FRONT_LEFT) {
				GL11.glRotatef(90.0F, 0.0F, 1.0F, 0.0F);
				renderCorner(loamIcon, woodIcon, woodTop, brightness, isTop);
			} else if (pos == FRONT_RIGHT) {
				GL11.glRotated(270, 0F, 1F, 0F);
				GL11.glRotatef(90.0F, 0.0F, 1.0F, 0.0F);
				renderCorner(loamIcon, woodIcon, woodTop, brightness, isTop);
			}
			GL11.glPopMatrix();
			GL11.glPopMatrix();
		}
		GL11.glEnable(GL11.GL_LIGHTING);
	}

	public void renderFront(IIcon dirtIcon, IIcon woodIcon, IIcon woodTop, int brightness) {
		Tessellator t = Tessellator.instance;
		t.startDrawingQuads();
		t.setBrightness(brightness);
		t.setColorRGBA_F(1, 1, 1, 1);
		t.setNormal(0, 1, 0);
		t.addVertexWithUV(-0.5, -0.5, -0.5, dirtIcon.getMinU(), dirtIcon.getMinV());
		t.addVertexWithUV(0.5, -0.5, -0.5, dirtIcon.getMaxU(), dirtIcon.getMinV());
		t.addVertexWithUV(0.5, -0.5, 0.5, dirtIcon.getMaxU(), dirtIcon.getMaxV());
		t.addVertexWithUV(-0.5, -0.5, 0.5, dirtIcon.getMinU(), dirtIcon.getMaxV());
		// Front
		t.addVertexWithUV(0.5, -0.5, -0.5, dirtIcon.getMinU(), dirtIcon.getMaxV());
		t.addVertexWithUV(-0.5, -0.5, -0.5, dirtIcon.getMaxU(), dirtIcon.getMaxV());
		t.addVertexWithUV(-0.5, 0.5, 0.5, dirtIcon.getMaxU(), dirtIcon.getMinV());
		t.addVertexWithUV(0.5, 0.5, 0.5, dirtIcon.getMinU(), dirtIcon.getMinV());
		// Back
		t.addVertexWithUV(-0.5, 0.5, 0.5, dirtIcon.getMinU(), dirtIcon.getMinV());
		t.addVertexWithUV(-0.5, -0.5, 0.5, dirtIcon.getMaxU(), dirtIcon.getMinV());
		t.addVertexWithUV(0.5, -0.5, 0.5, dirtIcon.getMaxU(), dirtIcon.getMaxV());
		t.addVertexWithUV(0.5, 0.5, 0.5, dirtIcon.getMinU(), dirtIcon.getMaxV());
		// Wood
		// 0
		// Front
		t.addVertexWithUV(0.1, 0.3, 0.3, woodIcon.getMaxU(), woodIcon.getMaxV());
		t.addVertexWithUV(0.1, -0.5, -0.5, woodIcon.getMaxU(), woodIcon.getMinV());
		t.addVertexWithUV(0.1, -0.5, -0.7, woodIcon.getMinU(), woodIcon.getMinV());
		t.addVertexWithUV(0.1, 0.5, 0.3, woodIcon.getMinU(), woodIcon.getMaxV());
		t.addVertexWithUV(0.1, 0.5, 0.3, woodIcon.getMaxU(), woodIcon.getMaxV());
		t.addVertexWithUV(0.1, -0.5, -0.7, woodIcon.getMaxU(), woodIcon.getMinV());
		t.addVertexWithUV(-0.1, -0.5, -0.7, woodIcon.getMinU(), woodIcon.getMinV());
		t.addVertexWithUV(-0.1, 0.5, 0.3, woodIcon.getMinU(), woodIcon.getMaxV());
		t.addVertexWithUV(-0.1, 0.5, 0.3, woodIcon.getMaxU(), woodIcon.getMaxV());
		t.addVertexWithUV(-0.1, -0.5, -0.7, woodIcon.getMaxU(), woodIcon.getMinV());
		t.addVertexWithUV(-0.1, -0.5, -0.5, woodIcon.getMinU(), woodIcon.getMinV());
		t.addVertexWithUV(-0.1, 0.3, 0.3, woodIcon.getMinU(), woodIcon.getMaxV());
		t.addVertexWithUV(-0.1, -0.5, -0.7, woodTop.getMinU(), woodTop.getMinV());
		t.addVertexWithUV(0.1, -0.5, -0.7, woodTop.getMaxU(), woodTop.getMinV());
		t.addVertexWithUV(0.1, -0.5, -0.5, woodTop.getMaxU(), woodTop.getMaxV());
		t.addVertexWithUV(-0.1, -0.5, -0.5, woodTop.getMinU(), woodTop.getMaxV());
		// 1
		// Front
		// 3
		t.addVertexWithUV(-0.5, 0.3, 0.5, woodIcon.getMinU(), woodIcon.getMinV());
		t.addVertexWithUV(-0.5, 0.3, 0.3, woodIcon.getMaxU(), woodIcon.getMinV());
		t.addVertexWithUV(0.5, 0.3, 0.3, woodIcon.getMaxU(), woodIcon.getMaxV());
		t.addVertexWithUV(0.5, 0.3, 0.5, woodIcon.getMinU(), woodIcon.getMaxV());
		t.addVertexWithUV(-0.5, 0.5, 0.5, woodIcon.getMinU(), woodIcon.getMinV());
		t.addVertexWithUV(0.5, 0.5, 0.5, woodIcon.getMaxU(), woodIcon.getMinV());
		t.addVertexWithUV(0.5, 0.5, 0.3, woodIcon.getMaxU(), woodIcon.getMaxV());
		t.addVertexWithUV(-0.5, 0.5, 0.3, woodIcon.getMinU(), woodIcon.getMaxV());
		t.addVertexWithUV(0.5, 0.5, 0.3, woodIcon.getMinU(), woodIcon.getMinV());
		t.addVertexWithUV(0.5, 0.3, 0.3, woodIcon.getMaxU(), woodIcon.getMinV());
		t.addVertexWithUV(-0.5, 0.3, 0.3, woodIcon.getMaxU(), woodIcon.getMaxV());
		t.addVertexWithUV(-0.5, 0.5, 0.3, woodIcon.getMinU(), woodIcon.getMaxV());
		t.draw();
	}

	public void renderCorner(IIcon dirtIcon, IIcon woodIcon, IIcon woodTop, int brightness, boolean isTop) {
		Tessellator t = Tessellator.instance;
		t.startDrawingQuads();
		t.setNormal(0, 1, 0);
		t.setBrightness(brightness);
		t.setColorRGBA_F(1, 1, 1, 1);
		t.addVertexWithUV(-0.5, -0.5, -0.5, dirtIcon.getMinU(), dirtIcon.getMinV());
		t.addVertexWithUV(0.5, -0.5, -0.5, dirtIcon.getMaxU(), dirtIcon.getMinV());
		t.addVertexWithUV(0.5, -0.5, 0.5, dirtIcon.getMaxU(), dirtIcon.getMaxV());
		t.addVertexWithUV(-0.5, -0.5, 0.5, dirtIcon.getMinU(), dirtIcon.getMaxV());
		t.addVertexWithUV(0.5, -0.5, 0.5, dirtIcon.getMinU(), dirtIcon.getMaxV());
		t.addVertexWithUV(0.5, -0.5, -0.5, dirtIcon.getMaxU(), dirtIcon.getMaxV());
		t.addVertexWithUV(-0.5, 0.5, 0.5, dirtIcon.getMaxU(), dirtIcon.getMinV());
		t.addVertexWithUV(-0.5, 0.5, 0.5, dirtIcon.getMinU(), dirtIcon.getMinV());
		t.addVertexWithUV(-0.5, 0.5, 0.5, dirtIcon.getMaxU(), dirtIcon.getMinV());
		t.addVertexWithUV(0.5, -0.5, -0.5, dirtIcon.getMaxU(), dirtIcon.getMaxV());
		t.addVertexWithUV(-0.5, -0.5, -0.5, dirtIcon.getMinU(), dirtIcon.getMaxV());
		t.addVertexWithUV(-0.5, 0.5, 0.5, dirtIcon.getMinU(), dirtIcon.getMinV());
		// Front
		if (isTop) {
			t.addVertexWithUV(-0.3, 0.3, 0.3, woodIcon.getMaxU(), woodIcon.getMaxV());
			t.addVertexWithUV(-0.3, -0.5, -0.5, woodIcon.getMaxU(), woodIcon.getMinV());
			t.addVertexWithUV(-0.3, -0.5, -0.7, woodIcon.getMinU(), woodIcon.getMinV());
			t.addVertexWithUV(-0.3, 0.5, 0.3, woodIcon.getMinU(), woodIcon.getMaxV());
			// Front
			double woodU = (woodIcon.getMaxU() - woodIcon.getMinU()) / 16;
			double woodV = (woodIcon.getMaxV() - woodIcon.getMinV()) / 16;
			t.addVertexWithUV(-0.3, 0.5, 0.3, woodIcon.getMinU(), woodIcon.getMinV());
			t.addVertexWithUV(-0.3, -0.5, -0.7, woodIcon.getMinU(), woodIcon.getMaxV());
			t.addVertexWithUV(-0.5, -0.5, -0.7, woodIcon.getMaxU(), woodIcon.getMaxV());
			t.addVertexWithUV(-0.5, 0.5, 0.3, woodIcon.getMaxU(), woodIcon.getMinV());
			t.addVertexWithUV(-0.5, 0.5, 0.3, woodIcon.getMaxU(), woodIcon.getMaxV());
			t.addVertexWithUV(-0.5, -0.5, -0.7, woodIcon.getMaxU(), woodIcon.getMinV());
			t.addVertexWithUV(-0.5, -0.5, -0.5, woodIcon.getMinU(), woodIcon.getMinV());
			t.addVertexWithUV(-0.5, 0.3, 0.3, woodIcon.getMinU(), woodIcon.getMaxV());
			t.addVertexWithUV(-0.5, -0.5, -0.7, woodTop.getMinU(), woodTop.getMinV());
			t.addVertexWithUV(-0.3, -0.5, -0.7, woodTop.getMaxU(), woodTop.getMinV());
			t.addVertexWithUV(-0.3, -0.5, -0.5, woodTop.getMaxU(), woodTop.getMaxV());
			t.addVertexWithUV(-0.5, -0.5, -0.5, woodTop.getMinU(), woodTop.getMaxV());
		}
		t.draw();
		GL11.glRotatef(270.0F, 0.0F, 1.0F, 0.0F);
		t.startDrawingQuads();
		t.setNormal(0, 1, 0);
		t.setBrightness(brightness);
		t.setColorRGBA_F(1, 1, 1, 1);
		if (isTop) {
			t.addVertexWithUV(0.5, 0.3, 0.3, woodIcon.getMaxU(), woodIcon.getMaxV());
			t.addVertexWithUV(0.5, -0.5, -0.5, woodIcon.getMaxU(), woodIcon.getMinV());
			t.addVertexWithUV(0.5, -0.5, -0.7, woodIcon.getMinU(), woodIcon.getMinV());
			t.addVertexWithUV(0.5, 0.5, 0.3, woodIcon.getMinU(), woodIcon.getMaxV());
			// Front
			t.addVertexWithUV(0.5, 0.5, 0.3, woodIcon.getMaxU(), woodIcon.getMaxV());
			t.addVertexWithUV(0.5, -0.5, -0.7, woodIcon.getMaxU(), woodIcon.getMinV());
			t.addVertexWithUV(0.3, -0.5, -0.7, woodIcon.getMinU(), woodIcon.getMinV());
			t.addVertexWithUV(0.3, 0.5, 0.3, woodIcon.getMinU(), woodIcon.getMaxV());
			t.addVertexWithUV(0.3, 0.5, 0.3, woodIcon.getMaxU(), woodIcon.getMaxV());
			t.addVertexWithUV(0.3, -0.5, -0.7, woodIcon.getMaxU(), woodIcon.getMinV());
			t.addVertexWithUV(0.3, -0.5, -0.5, woodIcon.getMinU(), woodIcon.getMinV());
			t.addVertexWithUV(0.3, 0.3, 0.3, woodIcon.getMinU(), woodIcon.getMaxV());
			t.addVertexWithUV(0.3, -0.5, -0.7, woodTop.getMinU(), woodTop.getMinV());
			t.addVertexWithUV(0.5, -0.5, -0.7, woodTop.getMaxU(), woodTop.getMinV());
			t.addVertexWithUV(0.5, -0.5, -0.5, woodTop.getMaxU(), woodTop.getMaxV());
			t.addVertexWithUV(0.3, -0.5, -0.5, woodTop.getMinU(), woodTop.getMaxV());
		}
		t.addVertexWithUV(0.3, 0.5, 0.5, woodIcon.getMaxU(), woodIcon.getMinV());
		t.addVertexWithUV(0.5, 0.5, 0.5, woodIcon.getMinU(), woodIcon.getMinV());
		t.addVertexWithUV(0.5, 0.5, 0.3, woodIcon.getMinU(), woodIcon.getMaxV());
		t.addVertexWithUV(0.3, 0.5, 0.3, woodIcon.getMaxU(), woodIcon.getMaxV());
		t.addVertexWithUV(0.3, 0.3, 0.5, woodIcon.getMaxU(), woodIcon.getMaxV());
		t.addVertexWithUV(0.3, 0.3, 0.3, woodIcon.getMaxU(), woodIcon.getMinV());
		t.addVertexWithUV(0.5, 0.3, 0.3, woodIcon.getMinU(), woodIcon.getMinV());
		t.addVertexWithUV(0.5, 0.3, 0.5, woodIcon.getMinU(), woodIcon.getMaxV());
		// Wood End
		t.addVertexWithUV(0.5, 0.5, 0.3, woodTop.getMinU(), woodTop.getMinV());
		t.addVertexWithUV(0.5, 0.3, 0.3, woodTop.getMinU(), woodTop.getMaxV());
		t.addVertexWithUV(0.3, 0.3, 0.3, woodTop.getMaxU(), woodTop.getMaxV());
		t.addVertexWithUV(0.3, 0.5, 0.3, woodTop.getMaxU(), woodTop.getMinV());
		t.addVertexWithUV(0.3, 0.5, 0.3, woodTop.getMinU(), woodTop.getMinV());
		t.addVertexWithUV(0.3, 0.3, 0.3, woodTop.getMinU(), woodTop.getMaxV());
		t.addVertexWithUV(0.3, 0.3, 0.5, woodTop.getMaxU(), woodTop.getMaxV());
		t.addVertexWithUV(0.3, 0.5, 0.5, woodTop.getMaxU(), woodTop.getMinV());
		t.draw();
		GL11.glRotatef(90.0F, 0.0F, 1.0F, 0.0F);
	}
}
