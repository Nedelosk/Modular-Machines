package de.nedelosk.forestmods.client.render.blocks;

import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import de.nedelosk.forestmods.client.core.ClientProxy;
import de.nedelosk.forestmods.client.render.CharcoalKilnBlockAccessWrapper;
import de.nedelosk.forestmods.common.blocks.tile.TileCharcoalKiln;
import de.nedelosk.forestmods.common.core.BlockManager;
import de.nedelosk.forestmods.common.multiblocks.charcoal.CharcoalKilnPosition;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockAccess;

public class BlockCharcoalKilnRenderer implements ISimpleBlockRenderingHandler {

	@Override
	public void renderInventoryBlock(Block block, int metadata, int modelId, RenderBlocks renderer) {
	}

	@Override
	public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId, RenderBlocks renderer) {
		TileEntity tile = world.getTileEntity(x, y, z);
		if (tile instanceof TileCharcoalKiln) {
			TileCharcoalKiln kiln = (TileCharcoalKiln) tile;
			if (kiln.isConnected() && kiln.getController().isAssembled()) {
				if (kiln.getKilnPosition() == CharcoalKilnPosition.INTERIOR) {
					renderer.renderBlockByRenderType(BlockManager.blockGravel, x, y, z);
				}
				return true;
			} else if (kiln.isAsh()) {
				CharcoalKilnBlockAccessWrapper wrapper = new CharcoalKilnBlockAccessWrapper(renderer.blockAccess);
				renderer.blockAccess = wrapper;
				renderer.renderBlockByRenderType(BlockManager.blockGravel, x, y, z);
				renderer.blockAccess = wrapper.wrapped;
			} else {
				CharcoalKilnBlockAccessWrapper wrapper = new CharcoalKilnBlockAccessWrapper(renderer.blockAccess, kiln.getWoodStack());
				renderer.blockAccess = wrapper;
				renderer.renderBlockByRenderType(Block.getBlockFromItem(kiln.getWoodStack().getItem()), x, y, z);
				renderer.blockAccess = wrapper.wrapped;
			}
		}
		return true;
	}

	@Override
	public boolean shouldRender3DInInventory(int modelId) {
		return false;
	}

	@Override
	public int getRenderId() {
		return ClientProxy.charcoalKilnRenderID;
	}
}
