package nedelosk.modularmachines.common.multiblock.blastfurnace;

import java.util.List;

import nedelosk.forestcore.library.multiblock.BlockMultiblockBasic;
import nedelosk.modularmachines.common.multiblock.blastfurnace.TileBlastFurnaceFluidPort.PortType;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockBlastFurnace extends BlockMultiblockBasic<MultiblockBlastFurnace> {

	public static final int METADATA_ACCESSPORT = 2;
	public static final int METADATA_FLUIDPORT_AIR = 3;
	public static final int METADATA_FLUIDPORT_SLAG = 4;
	public static final int METADATA_FLUIDPORT_OUTPUT = 5;
	public static final int METADATA_FLUIDPORT_GAS = 6;
	private static final int ICON_ID_FLUIDPORT_AIR = 0;
	private static final int ICON_ID_FLUIDPORT_SLAG = 1;
	private static final int ICON_ID_FLUIDPORT_OUTPUT = 2;
	private static final int ICON_ID_FLUIDPORT_GAS = 3;
	private static final int ICON_ID_FLUIDPORT = 3;
	private static String[] subBlocks = new String[] { "casing", "controller", "accessPort", "fluidPort" };
	private static String[][] states = new String[][] { { "default", "face", "corner", "eastwest", "northsouth", "vertical" }, // Casing
			{ "off", "idle", "active" }, // Controller
			{ "default" }, // Access
							// Port
			{ "air", "slag", "output", "gas" }, // Fluid
												// Port
	};

	public static boolean isAccessPort(int metadata) {
		return metadata == METADATA_ACCESSPORT;
	}

	public static boolean isFluidPort(int metadata) {
		return metadata == METADATA_FLUIDPORT_AIR || metadata == METADATA_FLUIDPORT_OUTPUT || metadata == METADATA_FLUIDPORT_SLAG
				|| metadata == METADATA_FLUIDPORT_GAS;
	}

	public BlockBlastFurnace() {
		super(Material.iron, MultiblockBlastFurnace.class, subBlocks, states);
		setBlockName("blast_furnace");
	}

	@Override
	public IIcon getIcon(IBlockAccess blockAccess, int x, int y, int z, int side) {
		IIcon icon = null;
		int metadata = blockAccess.getBlockMetadata(x, y, z);
		switch (metadata) {
			case METADATA_CASING:
				icon = getCasingIcon(blockAccess, x, y, z, side);
				break;
			case METADATA_CONTROLLER:
				icon = getControllerIcon(blockAccess, x, y, z, side);
				break;
			case METADATA_ACCESSPORT:
				icon = getAccessPortIcon(blockAccess, x, y, z, side);
				break;
			case METADATA_FLUIDPORT_AIR:
			case METADATA_FLUIDPORT_OUTPUT:
			case METADATA_FLUIDPORT_GAS:
			case METADATA_FLUIDPORT_SLAG:
				icon = getFluidPortIcon(blockAccess, x, y, z, side);
				break;
		}
		return icon != null ? icon : getIcon(side, metadata);
	}

	@Override
	public IIcon getIcon(int side, int metadata) {
		if (isFluidPort(metadata)) {
			if (side > 1 && metadata >= 0) {
				return icons[ICON_ID_FLUIDPORT][metadata - 3];
			}
		} else if (side > 1 && (metadata >= 0 && metadata < icons.length)) {
			return icons[metadata][0];
		}
		return blockIcon;
	}

	@Override
	public String getMultiblockName() {
		return "blastfurnace";
	}

	@Override
	public TileEntity createNewTileEntity(World world, int metadata) {
		switch (metadata) {
			case METADATA_ACCESSPORT:
				return new TileBlastFurnaceAccessPort();
			case METADATA_FLUIDPORT_SLAG:
				return new TileBlastFurnaceFluidPort(PortType.SLAG);
			case METADATA_FLUIDPORT_OUTPUT:
				return new TileBlastFurnaceFluidPort(PortType.OUTPUT);
			case METADATA_FLUIDPORT_GAS:
				return new TileBlastFurnaceFluidPort(PortType.GAS);
			case METADATA_FLUIDPORT_AIR:
				return new TileBlastFurnaceFluidPort(PortType.AIR);
			default:
				return new TileBlastFurnaceBase();
		}
	}

	@Override
	public boolean whenMultiblockNotAssembled(int metadata) {
		return super.whenMultiblockNotAssembled(metadata) || isFluidPort(metadata);
	}

	@Override
	public void getSubBlocks(Item par1, CreativeTabs par2CreativeTabs, List par3List) {
		for ( int metadata = 0; metadata < 7; metadata++ ) {
			par3List.add(new ItemStack(this, 1, metadata));
		}
	}

	//// UGLY UI CODE HERE ////
	private IIcon getFluidPortIcon(IBlockAccess blockAccess, int x, int y, int z, int side) {
		TileEntity te = blockAccess.getTileEntity(x, y, z);
		if (te instanceof TileBlastFurnaceFluidPort) {
			TileBlastFurnaceFluidPort port = (TileBlastFurnaceFluidPort) te;
			if (!isMultiblockAssembled(port) || isOutwardsSide(port, side)) {
				switch (port.getType()) {
					case AIR:
						return icons[ICON_ID_FLUIDPORT][ICON_ID_FLUIDPORT_AIR];
					case OUTPUT:
						return icons[ICON_ID_FLUIDPORT][ICON_ID_FLUIDPORT_OUTPUT];
					case SLAG:
						return icons[ICON_ID_FLUIDPORT][ICON_ID_FLUIDPORT_SLAG];
					case GAS:
						return icons[ICON_ID_FLUIDPORT][ICON_ID_FLUIDPORT_GAS];
				}
			}
		}
		return blockIcon;
	}

	private IIcon getAccessPortIcon(IBlockAccess blockAccess, int x, int y, int z, int side) {
		TileEntity te = blockAccess.getTileEntity(x, y, z);
		if (te instanceof TileBlastFurnaceAccessPort) {
			TileBlastFurnaceAccessPort port = (TileBlastFurnaceAccessPort) te;
			if (!isMultiblockAssembled(port) || isOutwardsSide(port, side)) {
				return icons[METADATA_ACCESSPORT][0];
			}
		}
		return blockIcon;
	}
}