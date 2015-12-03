package nedelosk.forestday.client.renderer.tile;

import org.lwjgl.opengl.GL11;

import nedelosk.forestday.api.utils.RenderUtils;
import nedelosk.forestday.common.blocks.tiles.TileCharcoalAsh;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;

public class TileCharcoalAshRenderer extends TileEntitySpecialRenderer {

	@Override
	public void renderTileEntityAt(TileEntity tile, double p_147500_2_, double p_147500_4_, double p_147500_6_,
			float p_147500_8_) {
		renderTileEntityAt((TileCharcoalAsh) tile, p_147500_2_, p_147500_4_, p_147500_6_, p_147500_8_);
	}

	public void renderTileEntityAt(TileCharcoalAsh ash, double x, double y, double z, float p_147500_8_) {

		GL11.glPushMatrix();
		GL11.glTranslated((float) x + 0.5F, (float) y + 0.5F, (float) z + 0.5F);
		Tessellator t = Tessellator.instance;
		GL11.glPushMatrix();

		GL11.glDisable(GL11.GL_LIGHTING);

		RenderUtils.bindBlockTexture();

		IIcon dirtIcon = /*
							 * FBlocks.Multiblock_Charcoal_Kiln.block().getIcon(0,
							 * 0);
							 */ Blocks.dirt.getIcon(0, 0);

		t.startDrawingQuads();
		t.setNormal(0, 1, 0);
		t.addVertexWithUV(-0.3, 0.1, -0.3, dirtIcon.getMinU(), dirtIcon.getMinV());
		t.addVertexWithUV(-0.3, 0.1, 0.3, dirtIcon.getMaxU(), dirtIcon.getMinV());
		t.addVertexWithUV(0.3, 0.1, 0.3, dirtIcon.getMaxU(), dirtIcon.getMaxV());
		t.addVertexWithUV(0.3, 0.1, -0.3, dirtIcon.getMinU(), dirtIcon.getMaxV());
		t.draw();

		t.startDrawingQuads();
		t.setNormal(0, 1, 0);
		t.addVertexWithUV(-0.5, -0.5, -0.5, dirtIcon.getMinU(), dirtIcon.getMinV());
		t.addVertexWithUV(0.5, -0.5, -0.5, dirtIcon.getMaxU(), dirtIcon.getMinV());
		t.addVertexWithUV(0.5, -0.5, 0.5, dirtIcon.getMaxU(), dirtIcon.getMaxV());
		t.addVertexWithUV(-0.5, -0.5, 0.5, dirtIcon.getMinU(), dirtIcon.getMaxV());
		t.draw();

		t.startDrawingQuads();
		t.setNormal(0, 1, 0);
		t.addVertexWithUV(0.3, 0.1, 0.3, dirtIcon.getMinU(), dirtIcon.getMinV());
		t.addVertexWithUV(0.5, -0.5, 0.5, dirtIcon.getMaxU(), dirtIcon.getMinV());
		t.addVertexWithUV(0.5, -0.5, -0.5, dirtIcon.getMaxU(), dirtIcon.getMaxV());
		t.addVertexWithUV(0.3, 0.1, -0.3, dirtIcon.getMinU(), dirtIcon.getMaxV());
		t.draw();

		t.startDrawingQuads();
		t.setNormal(0, 1, 0);
		t.addVertexWithUV(-0.3, 0.1, -0.3, dirtIcon.getMinU(), dirtIcon.getMinV());
		t.addVertexWithUV(-0.5, -0.5, -0.5, dirtIcon.getMaxU(), dirtIcon.getMinV());
		t.addVertexWithUV(-0.5, -0.5, 0.5, dirtIcon.getMaxU(), dirtIcon.getMaxV());
		t.addVertexWithUV(-0.3, 0.1, 0.3, dirtIcon.getMinU(), dirtIcon.getMaxV());
		t.draw();

		t.startDrawingQuads();
		t.setNormal(0, 1, 0);
		t.addVertexWithUV(0.3, 0.1, -0.3, dirtIcon.getMinU(), dirtIcon.getMinV());
		t.addVertexWithUV(0.5, -0.5, -0.5, dirtIcon.getMaxU(), dirtIcon.getMinV());
		t.addVertexWithUV(-0.5, -0.5, -0.5, dirtIcon.getMaxU(), dirtIcon.getMaxV());
		t.addVertexWithUV(-0.3, 0.1, -0.3, dirtIcon.getMinU(), dirtIcon.getMaxV());
		t.draw();

		t.startDrawingQuads();
		t.setNormal(0, 1, 0);
		t.addVertexWithUV(-0.3, 0.1, 0.3, dirtIcon.getMinU(), dirtIcon.getMinV());
		t.addVertexWithUV(-0.5, -0.5, 0.5, dirtIcon.getMaxU(), dirtIcon.getMinV());
		t.addVertexWithUV(0.5, -0.5, 0.5, dirtIcon.getMaxU(), dirtIcon.getMaxV());
		t.addVertexWithUV(0.3, 0.1, 0.3, dirtIcon.getMinU(), dirtIcon.getMaxV());
		t.draw();

		GL11.glEnable(GL11.GL_LIGHTING);
		GL11.glPopMatrix();
		GL11.glPopMatrix();
	}

}
