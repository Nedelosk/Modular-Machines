package de.nedelosk.modularmachines.common.blocks;

import java.util.List;

import de.nedelosk.modularmachines.api.ModularMachinesApi;
import de.nedelosk.modularmachines.api.transport.IPartSide;
import de.nedelosk.modularmachines.api.transport.node.EnumNodeMode;
import de.nedelosk.modularmachines.api.transport.node.INodeSide;
import de.nedelosk.modularmachines.api.transport.node.ITransportNode;
import de.nedelosk.modularmachines.common.core.TabModularMachines;
import de.nedelosk.modularmachines.common.transport.TileEntityTransport;
import de.nedelosk.modularmachines.common.transport.TransportRegistry;
import de.nedelosk.modularmachines.common.transport.node.TileEntityTransportNode;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockTransport extends BlockContainerForest {

	public BlockTransport() {
		super(Material.IRON, TabModularMachines.tabForestMods);
		setSoundType(SoundType.METAL);
		
		setUnlocalizedName("transport");
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		if (meta == 1) {
			return new TileEntityTransportNode();
		}
		return new TileEntityTransport();
	}

	@Override
	public boolean isOpaqueCube(IBlockState state) {
		return false;
	}
	
	@Override
	public EnumBlockRenderType getRenderType(IBlockState state) {
		return EnumBlockRenderType.INVISIBLE;
	}

	@Override
	public void getSubBlocks(Item item, CreativeTabs tab, List subItems) {
		subItems.add(new ItemStack(item));
		for(int i = 1; i < ModularMachinesApi.getNodeTypes().size(); i++) {
			subItems.add(new ItemStack(item, 1, i));
		}
	}
	
	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, ItemStack heldItem, EnumFacing side, float hitX, float hitY, float hitZ) {
		TileEntity tile = world.getTileEntity(pos);
		if (tile instanceof TileEntityTransportNode) {
			TileEntityTransportNode node = (TileEntityTransportNode) tile;
			if (heldItem != null && heldItem.getItem() instanceof IToolWrench) {
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
			if (heldItem != null && heldItem.getItem() instanceof IToolWrench) {
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
	public void onNeighborBlockChange(World world, BlockPos pos, IBlockState state, Block neighborBlock) {
		if (world.isRemote) {
			return;
		}
		TileEntity tile = world.getTileEntity(pos);
		if (tile instanceof TileEntityTransportNode) {
			TileEntityTransportNode tileNode = (TileEntityTransportNode) tile;
			ITransportNode node = tileNode.getPart();
			for(EnumFacing facing : EnumFacing.VALUES) {
				INodeSide side = node.getSide(facing);
				if (!side.canConnect() && side.isConnected()) {
					side.setNodeMode(EnumNodeMode.NONE);
				} else if (!side.isConnected() && side.canConnect()) {
					side.setNodeMode(EnumNodeMode.CONNECTED);
				}
			}
			tile.markDirty();
		}
	}
	
	@Override
	public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, World world, BlockPos pos) {
		int md = getMetaFromState(blockState);
		int x = pos.getX();
		int y = pos.getY();
		int z = pos.getZ();
		TileEntity tile = world.getTileEntity(pos);
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
			return new AxisAlignedBB(x + minX, y + minY, z + minZ, x + maxX, y + maxY, z + maxZ);
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
			return new AxisAlignedBB(x + minX, y + minY, z + minZ, x + maxX, y + maxY, z + maxZ);
		} else {
			return new AxisAlignedBB(x, y, z, x + 1D, y + 1D, z + 1D);
		}
	}
	
	@Override
	public AxisAlignedBB getSelectedBoundingBox(IBlockState state, World world, BlockPos pos) {
		int x = pos.getX();
		int y = pos.getY();
		int z = pos.getZ();
		int md = getMetaFromState(state);
		TileEntity tile = world.getTileEntity(pos);
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
			return new AxisAlignedBB(x + minX, y + minY, z + minZ, x + maxX, y + maxY, z + maxZ);
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
			return new AxisAlignedBB(x + minX, y + minY, z + minZ, x + maxX, y + maxY, z + maxZ);
		} else {
			return new AxisAlignedBB(x, y, z, x + 1D, y + 1D, z + 1D);
		}
	}
}
