package de.nedelosk.forestmods.client.render.tile;

import static de.nedelosk.forestmods.common.multiblocks.charcoal.CharcoalKilnPosition.BACK;
import static de.nedelosk.forestmods.common.multiblocks.charcoal.CharcoalKilnPosition.BACK_LEFT;
import static de.nedelosk.forestmods.common.multiblocks.charcoal.CharcoalKilnPosition.BACK_RIGHT;
import static de.nedelosk.forestmods.common.multiblocks.charcoal.CharcoalKilnPosition.FRONT;
import static de.nedelosk.forestmods.common.multiblocks.charcoal.CharcoalKilnPosition.FRONT_LEFT;
import static de.nedelosk.forestmods.common.multiblocks.charcoal.CharcoalKilnPosition.FRONT_RIGHT;
import static de.nedelosk.forestmods.common.multiblocks.charcoal.CharcoalKilnPosition.INTERIOR;
import static de.nedelosk.forestmods.common.multiblocks.charcoal.CharcoalKilnPosition.LEFT;
import static de.nedelosk.forestmods.common.multiblocks.charcoal.CharcoalKilnPosition.RIGHT;

import org.lwjgl.opengl.GL11;

import de.nedelosk.forestcore.utils.RenderUtil;
import de.nedelosk.forestmods.common.blocks.tile.TileCharcoalKiln;
import de.nedelosk.forestmods.common.core.BlockManager;
import de.nedelosk.forestmods.common.multiblocks.charcoal.CharcoalKilnPosition;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;

public class TileCharcoalKilnRenderer extends TileEntitySpecialRenderer {

	@Override
	public void renderTileEntityAt(TileEntity tile, double p_147500_2_, double p_147500_4_, double p_147500_6_, float p_147500_8_) {
		renderTileEntityAt((TileCharcoalKiln) tile, p_147500_2_, p_147500_4_, p_147500_6_, p_147500_8_);
	}

	public void renderTileEntityAt(TileCharcoalKiln kiln, double x, double y, double z, float p_147500_8_) {
		if (kiln.getWoodType() == null) {
			return;
		}
		boolean withWood = false;
		if (kiln.isConnected() && kiln.getController().isAssembled()) {
			GL11.glPushMatrix();
			GL11.glTranslated((float) x + 0.5F, (float) y + 0.5F, (float) z + 0.5F);
			Tessellator t = Tessellator.instance;
			GL11.glPushMatrix();
			GL11.glDisable(GL11.GL_LIGHTING);
			RenderUtil.bindBlockTexture();
			CharcoalKilnPosition pos = kiln.getKilnPosition();
			int level = kiln.getLevel();
			IIcon loamIcon = BlockManager.blockGravel.getIcon(0, 0);
			IIcon woodIcon = Block.getBlockFromItem(kiln.getWoodType().getWood().getItem()).getIcon(4, kiln.getWoodType().getWood().getItemDamage());
			if (pos == BACK && level == 0) {
				renderFront(loamIcon, woodIcon, withWood);
			} else if (pos == FRONT && level == 0) {
				GL11.glRotated(180, 0F, 0F, 1F);
				GL11.glRotatef(180.0F, 1.0F, 0.0F, 0.0F);
				renderFront(loamIcon, woodIcon, withWood);
			} else if (pos == LEFT && level == 0) {
				GL11.glRotatef(90.0F, 0.0F, 1.0F, 0.0F);
				renderFront(loamIcon, woodIcon, withWood);
			} else if (pos == RIGHT && level == 0) {
				GL11.glRotated(180, 0F, 1F, 0F);
				GL11.glRotatef(90.0F, 0.0F, 1.0F, 0.0F);
				renderFront(loamIcon, woodIcon, withWood);
			} else if (pos == BACK_RIGHT && level == 0) {
				GL11.glRotated(180, 0F, 1F, 0F);
				GL11.glRotatef(90.0F, 0.0F, 1.0F, 0.0F);
				renderCorner(loamIcon, woodIcon, withWood);
			} else if (pos == BACK_LEFT && level == 0) {
				GL11.glRotated(90, 0F, 1F, 0F);
				GL11.glRotatef(90.0F, 0.0F, 1.0F, 0.0F);
				renderCorner(loamIcon, woodIcon, withWood);
			} else if (pos == FRONT_LEFT && level == 0) {
				GL11.glRotatef(90.0F, 0.0F, 1.0F, 0.0F);
				renderCorner(loamIcon, woodIcon, withWood);
			} else if (pos == FRONT_RIGHT && level == 0) {
				GL11.glRotated(270, 0F, 1F, 0F);
				GL11.glRotatef(90.0F, 0.0F, 1.0F, 0.0F);
				renderCorner(loamIcon, woodIcon, withWood);
			} else if (pos == INTERIOR && level == 0) {
				renderDown(loamIcon, (int) x, (int) y, (int) z);
			} else if (pos == FRONT_RIGHT && level == 1) {
				GL11.glRotated(270, 0F, 1F, 0F);
				GL11.glRotatef(90.0F, 0.0F, 1.0F, 0.0F);
				renderCornerTop(loamIcon, woodIcon, withWood);
			} else if (pos == BACK_RIGHT && level == 1) {
				GL11.glRotated(180, 0F, 1F, 0F);
				GL11.glRotatef(90.0F, 0.0F, 1.0F, 0.0F);
				renderCornerTop(loamIcon, woodIcon, withWood);
			} else if (pos == FRONT_LEFT && level == 1) {
				GL11.glRotatef(90.0F, 0.0F, 1.0F, 0.0F);
				renderCornerTop(loamIcon, woodIcon, withWood);
			} else if (pos == BACK_LEFT && level == 1) {
				GL11.glRotated(90, 0F, 1F, 0F);
				GL11.glRotatef(90.0F, 0.0F, 1.0F, 0.0F);
				renderCornerTop(loamIcon, woodIcon, withWood);
			} else if (pos == FRONT && level == 1) {
				GL11.glRotated(180, 0F, 0F, 1F);
				GL11.glRotatef(180.0F, 1.0F, 0.0F, 0.0F);
				renderFrontTop(loamIcon, woodIcon, withWood);
			} else if (pos == BACK && level == 1) {
				GL11.glRotated(270, 0F, 1F, 0F);
				GL11.glRotatef(90.0F, 0.0F, 1.0F, 0.0F);
				renderFrontTop(loamIcon, woodIcon, withWood);
			} else if (pos == RIGHT && level == 1) {
				GL11.glRotated(180, 0F, 1F, 0F);
				GL11.glRotatef(90.0F, 0.0F, 1.0F, 0.0F);
				renderFrontTop(loamIcon, woodIcon, withWood);
			} else if (pos == LEFT && level == 1) {
				GL11.glRotatef(90.0F, 0.0F, 1.0F, 0.0F);
				renderFrontTop(loamIcon, woodIcon, withWood);
			} else if (pos == INTERIOR && level == 1) {
				renderTop(loamIcon, withWood);
			}
			GL11.glPopMatrix();
			GL11.glPopMatrix();
		} else {
			renderBlock(kiln, x, y, z);
		}
		GL11.glEnable(GL11.GL_LIGHTING);
	}

	public void renderBlock(TileCharcoalKiln kiln, double x, double y, double z) {
		if (kiln.getWoodType() != null) {
			GL11.glPushMatrix();
			GL11.glTranslated((float) x + 0.5F, (float) y + 0.5F, (float) z + 0.5F);
			Tessellator t = Tessellator.instance;
			GL11.glPushMatrix();
			RenderUtil.bindBlockTexture();
			IIcon woodIconTop = Block.getBlockFromItem(kiln.getWoodType().getWood().getItem()).getIcon(0, kiln.getWoodType().getWood().getItemDamage());
			IIcon woodIcon = Block.getBlockFromItem(kiln.getWoodType().getWood().getItem()).getIcon(2, kiln.getWoodType().getWood().getItemDamage());
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

	public void renderTop(IIcon dirtIcon, boolean withWood) {
		Tessellator t = Tessellator.instance;
		t.startDrawingQuads();
		t.setNormal(0, 1, 0);
		if (withWood) {
			t.addVertexWithUV(-0.5, 0.1, -0.5, dirtIcon.getMinU(), dirtIcon.getMinV());
			t.addVertexWithUV(-0.5, 0.1, 0.5, dirtIcon.getMaxU(), dirtIcon.getMinV());
			t.addVertexWithUV(0.5, 0.1, 0.5, dirtIcon.getMaxU(), dirtIcon.getMaxV());
			t.addVertexWithUV(0.5, 0.1, -0.5, dirtIcon.getMinU(), dirtIcon.getMaxV());
		} else {
			t.addVertexWithUV(-0.5, 0.3, -0.5, dirtIcon.getMinU(), dirtIcon.getMinV());
			t.addVertexWithUV(-0.5, 0.3, 0.5, dirtIcon.getMaxU(), dirtIcon.getMinV());
			t.addVertexWithUV(0.5, 0.3, 0.5, dirtIcon.getMaxU(), dirtIcon.getMaxV());
			t.addVertexWithUV(0.5, 0.3, -0.5, dirtIcon.getMinU(), dirtIcon.getMaxV());
		}
		t.draw();
	}

	public void renderDown(IIcon dirtIcon, int x, int y, int z) {
		Tessellator t = Tessellator.instance;
		t.startDrawingQuads();
		t.setNormal(0, 1, 0);
		t.addVertexWithUV(-0.5, -0.5, -0.5, dirtIcon.getMinU(), dirtIcon.getMinV());
		t.addVertexWithUV(0.5, -0.5, -0.5, dirtIcon.getMaxU(), dirtIcon.getMinV());
		t.addVertexWithUV(0.5, -0.5, 0.5, dirtIcon.getMaxU(), dirtIcon.getMaxV());
		t.addVertexWithUV(-0.5, -0.5, 0.5, dirtIcon.getMinU(), dirtIcon.getMaxV());
		t.draw();
	}

	public void renderFront(IIcon dirtIcon, IIcon woodIcon, boolean withWood) {
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
		if (!withWood) {
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
	}

	public void renderCorner(IIcon dirtIcon, IIcon woodIcon, boolean withWood) {
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
		if (!withWood) {
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
		}
		GL11.glRotatef(90.0F, 0.0F, 1.0F, 0.0F);
	}

	public void renderFrontTop(IIcon dirtIcon, IIcon woodIcon, boolean withWood) {
		Tessellator t = Tessellator.instance;
		t.startDrawingQuads();
		t.setNormal(0, 1, 0);
		t.addVertexWithUV(0.5, 0.1, 0.5, dirtIcon.getMinU(), dirtIcon.getMinV());
		t.addVertexWithUV(0.5, -0.5, 0.2, dirtIcon.getMaxU(), dirtIcon.getMinV());
		t.addVertexWithUV(-0.5, -0.5, 0.2, dirtIcon.getMaxU(), dirtIcon.getMaxV());
		t.addVertexWithUV(-0.5, 0.1, 0.5, dirtIcon.getMinU(), dirtIcon.getMaxV());
		t.draw();
		if (!withWood) {
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
	}

	public void renderCornerTop(IIcon dirtIcon, IIcon woodIcon, boolean withWood) {
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
	}
}
