package de.nedelosk.forestmods.common.blocks;

import java.util.List;

import buildcraft.api.tools.IToolWrench;
import de.nedelosk.forestcore.blocks.BlockContainerForest;
import de.nedelosk.forestmods.api.ForestModsApi;
import de.nedelosk.forestmods.api.transport.IPartSide;
import de.nedelosk.forestmods.api.transport.TransportRegistry;
import de.nedelosk.forestmods.api.transport.node.EnumNodeMode;
import de.nedelosk.forestmods.api.transport.node.INodeSide;
import de.nedelosk.forestmods.api.transport.node.ITransportNode;
import de.nedelosk.forestmods.common.core.TabModularMachines;
import de.nedelosk.forestmods.common.transport.TileEntityTransport;
import de.nedelosk.forestmods.common.transport.node.TileEntityTransportNode;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class BlockTransport extends BlockContainerForest {

	public BlockTransport() {
		super(Material.iron, TabModularMachines.tabForestMods);
		setStepSound(soundTypeMetal);
		setBlockName("transport");
		setBlockTextureName("anvil_base");
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		if (meta == 1) {
			return new TileEntityTransportNode();
		}
		return new TileEntityTransport();
	}

	@Override
	public boolean isOpaqueCube() {
		return false;
	}

	@Override
	public int getRenderType() {
		return -1;
	}

	@Override
	public boolean renderAsNormalBlock() {
		return false;
	}

	@Override
	public void getSubBlocks(Item item, CreativeTabs tab, List subItems) {
		subItems.add(new ItemStack(item));
		for(int i = 0; i < ForestModsApi.getNodeTypes().size(); i++) {
			subItems.add(new ItemStack(item, 1, i + 1));
		}
	}

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float p_149727_7_, float p_149727_8_, float p_149727_9_) {
		TileEntity tile = world.getTileEntity(x, y, z);
		ItemStack playerStack = player.getCurrentEquippedItem();
		if (tile instanceof TileEntityTransportNode) {
			TileEntityTransportNode node = (TileEntityTransportNode) tile;
			if (playerStack != null && playerStack.getItem() instanceof IToolWrench) {
				INodeSide nodeSide = node.getPart().getSide(side);
				if (nodeSide.getNodeMode() == EnumNodeMode.CONNECTED) {
					nodeSide.setNodeMode(EnumNodeMode.NONE);
				} else if (nodeSide.getNodeMode() == EnumNodeMode.NONE) {
					nodeSide.setNodeMode(EnumNodeMode.LASER);
				} else if (nodeSide.getNodeMode() == EnumNodeMode.LASER) {
					if (nodeSide.canConnect()) {
						nodeSide.setNodeMode(EnumNodeMode.CONNECTED);
					} else {
						nodeSide.setNodeMode(EnumNodeMode.NONE);
					}
				}
				node.detachSelf(false);
				TransportRegistry.onPartAdded(world, node.getPart());
				return true;
			}
		} else if (tile instanceof TileEntityTransport) {
			TileEntityTransport transport = (TileEntityTransport) tile;
			if (playerStack != null && playerStack.getItem() instanceof IToolWrench) {
				IPartSide partSide = transport.getPart().getSide(side);
				if (partSide.isActive()) {
					partSide.setUnactive();
				} else {
					partSide.setActive();
				}
				transport.detachSelf(false);
				TransportRegistry.onPartAdded(world, transport.getPart());
				return true;
			}
		}
		return false;
	}

	@Override
	public void onNeighborBlockChange(World world, int x, int y, int z, Block block) {
		if (world.isRemote) {
			return;
		}
		TileEntity tile = world.getTileEntity(x, y, z);
		if (tile instanceof TileEntityTransportNode) {
			TileEntityTransportNode tileNode = (TileEntityTransportNode) tile;
			ITransportNode node = tileNode.getPart();
			for(ForgeDirection direction : ForgeDirection.VALID_DIRECTIONS) {
				INodeSide side = node.getSide(direction);
				if (!side.canConnect() && side.isConnected()) {
					side.setNodeMode(EnumNodeMode.NONE);
				} else if (!side.isConnected() && side.canConnect()) {
					side.setNodeMode(EnumNodeMode.CONNECTED);
				}
			}
			world.markBlockForUpdate(x, y, z);
		}
	}

	@Override
	public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int x, int y, int z) {
		int md = world.getBlockMetadata(x, y, z);
		TileEntity tile = world.getTileEntity(x, y, z);
		if (md == 0 && tile != null && tile instanceof TileEntityTransport) {
			TileEntityTransport port = (TileEntityTransport) tile;
			IPartSide[] sides = port.getPart().getSides();
			double minX;
			double minY;
			double minZ;
			double maxX;
			double maxY;
			double maxZ;
			if (sides[0].isActive()) {
				minY = 0.125D;
			} else {
				minY = 0.25D;
			}
			if (sides[1].isActive()) {
				maxY = 0.875D;
			} else {
				maxY = 0.75D;
			}
			if (sides[2].isActive()) {
				minZ = 0.125D;
			} else {
				minZ = 0.25D;
			}
			if (sides[3].isActive()) {
				maxZ = 0.875D;
			} else {
				maxZ = 0.75D;
			}
			if (sides[4].isActive()) {
				minX = 0.125D;
			} else {
				minX = 0.25D;
			}
			if (sides[5].isActive()) {
				maxX = 0.875D;
			} else {
				maxX = 0.75D;
			}
			return AxisAlignedBB.getBoundingBox(x + minX, y + minY, z + minZ, x + maxX, y + maxY, z + maxZ);
		} else if (md == 1 && tile != null && tile instanceof TileEntityTransportNode) {
			TileEntityTransportNode port = (TileEntityTransportNode) tile;
			INodeSide[] sides = port.getPart().getSides();
			double minX;
			double minY;
			double minZ;
			double maxX;
			double maxY;
			double maxZ;
			if (sides[0].isActive()) {
				minY = 0.1875D;
			} else if (sides[0].isConnected()) {
				minY = 0;
			} else {
				minY = 0.3125D;
			}
			if (sides[1].isActive()) {
				maxY = 0.8125D;
			} else if (sides[1].isConnected()) {
				maxY = 1;
			} else {
				maxY = 0.6875D;
			}
			if (sides[2].isActive()) {
				minZ = 0.1875D;
			} else if (sides[2].isConnected()) {
				minZ = 0;
			} else {
				minZ = 0.3125D;
			}
			if (sides[3].isActive()) {
				maxZ = 0.8125D;
			} else if (sides[3].isConnected()) {
				maxZ = 1;
			} else {
				maxZ = 0.6875D;
			}
			if (sides[4].isActive()) {
				minX = 0.1875D;
			} else if (sides[4].isConnected()) {
				minX = 0;
			} else {
				minX = 0.3125D;
			}
			if (sides[5].isActive()) {
				maxX = 0.8125D;
			} else if (sides[5].isConnected()) {
				maxX = 1;
			} else {
				maxX = 0.6875D;
			}
			return AxisAlignedBB.getBoundingBox(x + minX, y + minY, z + minZ, x + maxX, y + maxY, z + maxZ);
		} else {
			return AxisAlignedBB.getBoundingBox(x, y, z, x + 1D, y + 1D, z + 1D);
		}
	}

	@Override
	public AxisAlignedBB getSelectedBoundingBoxFromPool(World world, int x, int y, int z) {
		int md = world.getBlockMetadata(x, y, z);
		TileEntity tile = world.getTileEntity(x, y, z);
		if (md == 0 && tile != null && tile instanceof TileEntityTransport) {
			TileEntityTransport port = (TileEntityTransport) tile;
			IPartSide[] sides = port.getPart().getSides();
			double minX;
			double minY;
			double minZ;
			double maxX;
			double maxY;
			double maxZ;
			if (sides[0].isActive()) {
				minY = 0.125D;
			} else {
				minY = 0.25D;
			}
			if (sides[1].isActive()) {
				maxY = 0.875D;
			} else {
				maxY = 0.75D;
			}
			if (sides[2].isActive()) {
				minZ = 0.125D;
			} else {
				minZ = 0.25D;
			}
			if (sides[3].isActive()) {
				maxZ = 0.875D;
			} else {
				maxZ = 0.75D;
			}
			if (sides[4].isActive()) {
				minX = 0.125D;
			} else {
				minX = 0.25D;
			}
			if (sides[5].isActive()) {
				maxX = 0.875D;
			} else {
				maxX = 0.75D;
			}
			return AxisAlignedBB.getBoundingBox(x + minX, y + minY, z + minZ, x + maxX, y + maxY, z + maxZ);
		} else if (md == 1 && tile != null && tile instanceof TileEntityTransportNode) {
			TileEntityTransportNode port = (TileEntityTransportNode) tile;
			INodeSide[] sides = port.getPart().getSides();
			double minX;
			double minY;
			double minZ;
			double maxX;
			double maxY;
			double maxZ;
			if (sides[0].isActive()) {
				minY = 0.1875D;
			} else if (sides[0].isConnected()) {
				minY = 0;
			} else {
				minY = 0.3125D;
			}
			if (sides[1].isActive()) {
				maxY = 0.8125D;
			} else if (sides[1].isConnected()) {
				maxY = 1;
			} else {
				maxY = 0.6875D;
			}
			if (sides[2].isActive()) {
				minZ = 0.1875D;
			} else if (sides[2].isConnected()) {
				minZ = 0;
			} else {
				minZ = 0.3125D;
			}
			if (sides[3].isActive()) {
				maxZ = 0.8125D;
			} else if (sides[3].isConnected()) {
				maxZ = 1;
			} else {
				maxZ = 0.6875D;
			}
			if (sides[4].isActive()) {
				minX = 0.1875D;
			} else if (sides[4].isConnected()) {
				minX = 0;
			} else {
				minX = 0.3125D;
			}
			if (sides[5].isActive()) {
				maxX = 0.8125D;
			} else if (sides[5].isConnected()) {
				maxX = 1;
			} else {
				maxX = 0.6875D;
			}
			return AxisAlignedBB.getBoundingBox(x + minX, y + minY, z + minZ, x + maxX, y + maxY, z + maxZ);
		} else {
			return AxisAlignedBB.getBoundingBox(x, y, z, x + 1D, y + 1D, z + 1D);
		}
	}

	@Override
	public void setBlockBoundsBasedOnState(IBlockAccess iblockaccess, int x, int y, int z) {
		int md = iblockaccess.getBlockMetadata(x, y, z);
		TileEntity tile = iblockaccess.getTileEntity(x, y, z);
		if (md == 0 && tile != null && tile instanceof TileEntityTransport) {
			TileEntityTransport port = (TileEntityTransport) tile;
			IPartSide[] sides = port.getPart().getSides();
			float minX;
			float minY;
			float minZ;
			float maxX;
			float maxY;
			float maxZ;
			if (sides[0].isActive()) {
				minY = 0.125F;
			} else {
				minY = 0.25F;
			}
			if (sides[1].isActive()) {
				maxY = 0.875F;
			} else {
				maxY = 0.75F;
			}
			if (sides[2].isActive()) {
				minZ = 0.125F;
			} else {
				minZ = 0.25F;
			}
			if (sides[3].isActive()) {
				maxZ = 0.875F;
			} else {
				maxZ = 0.75F;
			}
			if (sides[4].isActive()) {
				minX = 0.125F;
			} else {
				minX = 0.25F;
			}
			if (sides[5].isActive()) {
				maxX = 0.875F;
			} else {
				maxX = 0.75F;
			}
			setBlockBounds(minX, minY, minZ, maxX, maxY, maxZ);
		} else if (md == 1 && tile != null && tile instanceof TileEntityTransportNode) {
			TileEntityTransportNode port = (TileEntityTransportNode) tile;
			INodeSide[] sides = port.getPart().getSides();
			float minX;
			float minY;
			float minZ;
			float maxX;
			float maxY;
			float maxZ;
			if (sides[0].isActive()) {
				minY = 0.1875F;
			} else if (sides[0].isConnected()) {
				minY = 0;
			} else {
				minY = 0.3125F;
			}
			if (sides[1].isActive()) {
				maxY = 0.8125F;
			} else if (sides[1].isConnected()) {
				maxY = 1;
			} else {
				maxY = 0.6875F;
			}
			if (sides[2].isActive()) {
				minZ = 0.1875F;
			} else if (sides[2].isConnected()) {
				minZ = 0;
			} else {
				minZ = 0.3125F;
			}
			if (sides[3].isActive()) {
				maxZ = 0.8125F;
			} else if (sides[3].isConnected()) {
				maxZ = 1;
			} else {
				maxZ = 0.6875F;
			}
			if (sides[4].isActive()) {
				minX = 0.1875F;
			} else if (sides[4].isConnected()) {
				minX = 0;
			} else {
				minX = 0.3125F;
			}
			if (sides[5].isActive()) {
				maxX = 0.8125F;
			} else if (sides[5].isConnected()) {
				maxX = 1;
			} else {
				maxX = 0.6875F;
			}
			setBlockBounds(minX, minY, minZ, maxX, maxY, maxZ);
		} else {
			setBlockBounds(0F, 0F, 0F, 1F, 1F, 1F);
		}
	}
}
