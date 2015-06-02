package nedelosk.forestday.client.renderers.tile;

import nedelosk.forestday.common.machines.wood.workbench.TileWorkbench;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

public class TileCampfireRenderer extends TileEntitySpecialRenderer {

    private static RenderBlocks renderBlocks = new RenderBlocks();
	
	@Override
	public void renderTileEntityAt(TileEntity p_147500_1_, double p_147500_2_, double p_147500_4_, double p_147500_6_, float p_147500_8_) {
		renderTileEntityAt((TileWorkbench)p_147500_1_, p_147500_2_, p_147500_4_, p_147500_6_, p_147500_8_);
	}
	
	public void renderTileEntityAt(TileWorkbench tile, double x, double y, double z, float p_147500_8_) {
			GL11.glPushMatrix();
			GL11.glTranslated((float) x + 0.5F, (float) y + 0.5F, (float) z + 0.5F);
			Tessellator t = Tessellator.instance;
			this.bindTexture(new ResourceLocation("minecraft", "textures/blocks/dirt.png"));
			GL11.glPushMatrix();
			
			GL11.glPopMatrix();
			GL11.glPopMatrix();
	}
	
	public void renderItem(ItemStack stack)
	{
	}

}
