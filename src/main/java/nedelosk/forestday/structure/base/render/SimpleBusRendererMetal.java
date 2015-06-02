package nedelosk.forestday.structure.base.render;

import nedelosk.forestday.common.core.Defaults;
import nedelosk.forestday.structure.base.blocks.BlockBusItem;
import nedelosk.forestday.structure.base.blocks.BlockMetal;
import nedelosk.forestday.structure.base.blocks.tile.TileBus;
import nedelosk.forestday.structure.base.blocks.tile.TileBus.Mode;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;

public class SimpleBusRendererMetal implements ISimpleBlockRenderingHandler {
	
	@Override
	public void renderInventoryBlock(Block block, int metadata, int modelId,
			RenderBlocks renderer) {
		IIcon icon;
		icon = BlockMetal.icons[metadata];
		IIcon iconBus = BlockBusItem.iconBus;
		String name = block.getUnlocalizedName();
		Tessellator tessellator = Tessellator.instance;
		
		GL11.glTranslatef( -0.5F, -0.5F, -0.5F );

		tessellator.startDrawingQuads();
		
		tessellator.setNormal( -1.0F, 0.0F, 0.0F );
		renderer.renderFaceXNeg(block, 0.0D, 0.0D, 0.0D, icon);
		tessellator.setNormal( 1.0F, 0.0F, 0.0F );
		renderer.renderFaceXPos(block, 0.0D, 0.0D, 0.0D, icon);
		tessellator.setNormal( 0.0F, -1.0F, 0.0F );
		renderer.renderFaceYNeg(block, 0.0D, 0.0D, 0.0D, icon);
		tessellator.setNormal( 0.0F, 1.0F, 0.0F );
		renderer.renderFaceYPos(block, 0.0D, 0.0D, 0.0D, icon);
		tessellator.setNormal( 0.0F, 0.0F, -1.0F );
		renderer.renderFaceZNeg(block, 0.0D, 0.0D, 0.0D, icon);
		tessellator.setNormal( 0.0F, 0.0F, 1.0F );
		renderer.renderFaceZPos(block, 0.0D, 0.0D, 0.0D, icon);
		tessellator.setNormal( -1.0F, 0.0F, 0.0F );
		
		renderer.renderFaceXNeg(block, 0.0D, 0.0D, 0.0D, iconBus);
		tessellator.setNormal( 1.0F, 0.0F, 0.0F );
		renderer.renderFaceXPos(block, 0.0D, 0.0D, 0.0D, iconBus);
		tessellator.setNormal( 0.0F, 0.0F, -1.0F );
		renderer.renderFaceZNeg(block, 0.0D, 0.0D, 0.0D, iconBus);
		tessellator.setNormal( 0.0F, 0.0F, 1.0F );
		renderer.renderFaceZPos(block, 0.0D, 0.0D, 0.0D, iconBus);
		
		tessellator.draw();

		GL11.glTranslatef( 0.5F, 0.5F, 0.5F );
	}

	@Override
	public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z,
			Block block, int modelId, RenderBlocks renderer) 
	{
		TileBus tile = (TileBus) world.getTileEntity(x, y, z);
		IIcon icon = tile.getOverlayTexture();
		Mode mode = tile.getMode();
		IIcon iconBus = BlockBusItem.iconBus;
		Tessellator tessellator = Tessellator.instance;
		renderer.renderFaceXNeg(block, (double)x, (double)y, (double)z, icon);
		renderer.renderFaceXPos(block, (double)x, (double)y, (double)z, icon);
		renderer.renderFaceYNeg(block, (double)x, (double)y, (double)z, icon);
		renderer.renderFaceYPos(block, (double)x, (double)y, (double)z, icon);
		renderer.renderFaceZNeg(block, (double)x, (double)y, (double)z, icon);
		renderer.renderFaceZPos(block, (double)x, (double)y, (double)z, icon);
		
		renderer.renderFaceXNeg(block, (double)x, (double)y, (double)z, iconBus);
		renderer.renderFaceXPos(block, (double)x, (double)y, (double)z, iconBus);
		renderer.renderFaceZNeg(block, (double)x, (double)y, (double)z, iconBus);
		renderer.renderFaceZPos(block, (double)x, (double)y, (double)z, iconBus);
		renderer.renderFaceYNeg(block, (double)x, (double)y, (double)z, iconBus);
		renderer.renderFaceYPos(block, (double)x, (double)y, (double)z, iconBus);
		return true;
	}

	@Override
	public boolean shouldRender3DInInventory(int modelId) {
		return true;
	}

	@Override
	public int getRenderId() {
		return Defaults.BUSRENDERER_METAL_ID;
	}
}
