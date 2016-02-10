package de.nedelosk.forestmods.common.items.block;

import de.nedelosk.forestcore.items.ItemBlockForest;
import de.nedelosk.forestmods.api.ForestModsApi;
import de.nedelosk.forestmods.common.transport.TileEntityTransport;
import de.nedelosk.forestmods.common.transport.node.TileEntityTransportNode;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class ItemBlockTransport extends ItemBlockForest {

	public ItemBlockTransport(Block block) {
		super(block);
	}

	@Override
	public boolean placeBlockAt(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ,
			int metadata) {
		super.placeBlockAt(stack, player, world, x, y, z, side, hitX, hitY, hitZ, metadata);
		TileEntity tile = world.getTileEntity(x, y, z);
		if (!(tile instanceof TileEntityTransport)) {
			world.setBlockToAir(x, y, z);
			return false;
		}
		if (tile instanceof TileEntityTransportNode) {
			((TileEntityTransportNode) tile).getTransportPart().setType(ForestModsApi.getNodeType(metadata - 1));
		}
		return true;
	}
}
