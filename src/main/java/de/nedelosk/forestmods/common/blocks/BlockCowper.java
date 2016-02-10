package de.nedelosk.forestmods.common.blocks;

import java.util.List;

import de.nedelosk.forestcore.multiblock.BlockMultiblockBasic;
import de.nedelosk.forestmods.api.Tabs;
import de.nedelosk.forestmods.common.blocks.tile.TileCowperAccessPort;
import de.nedelosk.forestmods.common.blocks.tile.TileCowperBase;
import de.nedelosk.forestmods.common.blocks.tile.TileCowperFluidPort;
import de.nedelosk.forestmods.common.blocks.tile.TileCowperFluidPort.PortType;
import de.nedelosk.forestmods.common.core.ForestMods;
import de.nedelosk.forestmods.common.multiblocks.cowper.MultiblockCowper;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockCowper extends BlockMultiblockBasic<MultiblockCowper> {

	public static final int METADATA_ACCESSPORT = 2;
	public static final int METADATA_FLUIDPORT_INPUT = 3;
	public static final int METADATA_FLUIDPORT_FUEL = 4;
	public static final int METADATA_FLUIDPORT_OUTPUT = 5;
	public static final int METADATA_FLUIDPORT_STEAM = 6;
	public static final int METADATA_MUFFLER = 7;
	private static final int ICON_ID_FLUIDPORT_INPUT = 0;
	private static final int ICON_ID_FLUIDPORT_FUEL = 1;
	private static final int ICON_ID_FLUIDPORT_OUTPUT = 2;
	private static final int ICON_ID_FLUIDPORT_STEAM = 3;
	private static final int ICON_ID_FLUIDPORT = 3;
	private static final int CONTROLLER_OFF = 0;
	private static final int CONTROLLER_IDLE = 1;
	private static final int CONTROLLER_ACTIVE = 2;
	private static String[] subBlocks = new String[] { "casing", "controller", "accessPort", "fluidPort", "muffler" };
	private static String[][] states = new String[][] { { "default", "face", "corner", "eastwest", "northsouth", "vertical" }, // Casing
			{ "off", "idle", "active" }, // Controller
			{ "default" }, // Access
							// Port
			{ "input", "fuel", "output", "steam" }, // Fluid
													// Port
			{ "default" } }; // Muffler

	public static boolean isAccessPort(int metadata) {
		return metadata == METADATA_ACCESSPORT;
	}

	public static boolean isFluidPort(int metadata) {
		return metadata == METADATA_FLUIDPORT_INPUT || metadata == METADATA_FLUIDPORT_OUTPUT || metadata == METADATA_FLUIDPORT_FUEL
				|| metadata == METADATA_FLUIDPORT_STEAM;
	}

	public static boolean isMuffler(int metadata) {
		return metadata == METADATA_MUFFLER;
	}

	public BlockCowper() {
		super(Material.ground, MultiblockCowper.class, subBlocks, states);
		setBlockName("cowper");
		setCreativeTab(Tabs.tabForestMods);
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
			case METADATA_FLUIDPORT_INPUT:
			case METADATA_FLUIDPORT_FUEL:
			case METADATA_FLUIDPORT_OUTPUT:
			case METADATA_FLUIDPORT_STEAM:
				icon = getFluidPortIcon(blockAccess, x, y, z, side);
				break;
			case METADATA_MUFFLER:
				icon = getMufflerIcon(blockAccess, x, y, z, side);
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
		return "cowper";
	}

	@Override
	public TileEntity createNewTileEntity(World world, int metadata) {
		switch (metadata) {
			case METADATA_ACCESSPORT:
				return new TileCowperAccessPort();
			case METADATA_FLUIDPORT_INPUT:
				return new TileCowperFluidPort(PortType.INPUT);
			case METADATA_FLUIDPORT_FUEL:
				return new TileCowperFluidPort(PortType.FUEL);
			case METADATA_FLUIDPORT_OUTPUT:
				return new TileCowperFluidPort(PortType.OUTPUT);
			default:
				return new TileCowperBase();
		}
	}

	@Override
	public boolean whenMultiblockNotAssembled(int metadata) {
		return super.whenMultiblockNotAssembled(metadata) || isFluidPort(metadata);
	}

	@Override
	public void getSubBlocks(Item par1, CreativeTabs par2CreativeTabs, List par3List) {
		for ( int metadata = 0; metadata < 6; metadata++ ) {
			par3List.add(new ItemStack(this, 1, metadata));
		}
	}

	//// UGLY UI CODE HERE ////
	private IIcon getFluidPortIcon(IBlockAccess blockAccess, int x, int y, int z, int side) {
		TileEntity te = blockAccess.getTileEntity(x, y, z);
		if (te instanceof TileCowperFluidPort) {
			TileCowperFluidPort port = (TileCowperFluidPort) te;
			if (!isMultiblockAssembled(port) || isOutwardsSide(port, side)) {
				switch (port.getType()) {
					case INPUT:
						return icons[ICON_ID_FLUIDPORT][ICON_ID_FLUIDPORT_INPUT];
					case FUEL:
						return icons[ICON_ID_FLUIDPORT][ICON_ID_FLUIDPORT_FUEL];
					case OUTPUT:
						return icons[ICON_ID_FLUIDPORT][ICON_ID_FLUIDPORT_OUTPUT];
					case STEAM:
						return icons[ICON_ID_FLUIDPORT][ICON_ID_FLUIDPORT_STEAM];
				}
			}
		}
		return blockIcon;
	}

	private IIcon getMufflerIcon(IBlockAccess blockAccess, int x, int y, int z, int side) {
		return icons[METADATA_MUFFLER][0];
	}

	private IIcon getAccessPortIcon(IBlockAccess blockAccess, int x, int y, int z, int side) {
		TileEntity te = blockAccess.getTileEntity(x, y, z);
		if (te instanceof TileCowperAccessPort) {
			TileCowperAccessPort port = (TileCowperAccessPort) te;
			if (!isMultiblockAssembled(port) || isOutwardsSide(port, side)) {
				return icons[METADATA_ACCESSPORT][0];
			}
		}
		return blockIcon;
	}

	@Override
	public void openGui(EntityPlayer player, World world, int x, int y, int z) {
		player.openGui(ForestMods.instance, 0, world, x, y, z);
	}
}