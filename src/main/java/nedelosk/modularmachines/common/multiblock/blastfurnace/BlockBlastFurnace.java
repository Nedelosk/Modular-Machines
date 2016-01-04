package nedelosk.modularmachines.common.multiblock.blastfurnace;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import nedelosk.forestcore.library.CoordTriplet;
import nedelosk.forestcore.library.multiblock.IMultiblockPart;
import nedelosk.forestcore.library.multiblock.MultiblockControllerBase;
import nedelosk.forestcore.library.multiblock.rectangular.PartPosition;
import nedelosk.modularmachines.common.ModularMachines;
import nedelosk.modularmachines.common.core.TabModularMachines;

public class BlockBlastFurnace extends BlockContainer {

	public static final int METADATA_CASING = 0;
	public static final int METADATA_BRICK = 1;
	public static final int METADATA_ACCESSPORT = 2;
	public static final int METADATA_FLUIDPORT = 3;

	private static final int PORT_AIR_HOT = 0;
	private static final int PORT_SLAG = 1;
	private static final int PORT_OUTPUT = 2;
	private static final int PORT_GAS_BLAST_FURNACE = 3;

	private static String[] subBlocks = new String[] { "casing", "brick", "accessPort", "fluidPort" };

	private static String[][] states = new String[][] {
			{ "default", "face", "corner", "eastwest", "northsouth", "vertical" }, // Casing
			{ "default" }, // Brick
			{ "default" }, // Access Port
			{ "air_hot", "slag", "output", "gas_blast_furnace" }, // Fluid Port
	};
	private IIcon[][] icons = new IIcon[states.length][];

	public static boolean isCasing(int metadata) {
		return metadata == METADATA_CASING;
	}

	public static boolean isBrick(int metadata) {
		return metadata == METADATA_BRICK;
	}

	public static boolean isAccessPort(int metadata) {
		return metadata == METADATA_ACCESSPORT;
	}

	public static boolean isFluidPort(int metadata) {
		return metadata == METADATA_FLUIDPORT;
	}

	public BlockBlastFurnace(Material material) {
		super(material);

		setStepSound(soundTypeMetal);
		setHardness(2.0f);
		setBlockName("blockReactorPart");
		this.setBlockTextureName("modularmachines:blockReactorPart");
		setCreativeTab(TabModularMachines.core);
	}

	@Override
	public IIcon getIcon(IBlockAccess blockAccess, int x, int y, int z, int side) {
		IIcon icon = null;
		int metadata = blockAccess.getBlockMetadata(x, y, z);

		switch (metadata) {
		case METADATA_CASING:
			icon = getCasingIcon(blockAccess, x, y, z, side);
			break;
		case METADATA_BRICK:
			icon = getBrickIcon(blockAccess, x, y, z, side);
			break;
		case METADATA_ACCESSPORT:
			icon = getAccessPortIcon(blockAccess, x, y, z, side);
			break;
		case METADATA_FLUIDPORT:
			icon = getFluidPortIcon(blockAccess, x, y, z, side);
			break;
		}

		return icon != null ? icon : getIcon(side, metadata);
	}

	@Override
	public IIcon getIcon(int side, int metadata) {
		if (side > 1 && (metadata >= 0 && metadata < icons.length)) {
			return icons[metadata][0];
		}
		return blockIcon;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister par1IconRegister) {
		String prefix = "modularmachines:" + getUnlocalizedName() + ".";

		for (int metadata = 0; metadata < states.length; ++metadata) {
			String[] blockStates = states[metadata];
			icons[metadata] = new IIcon[blockStates.length];

			for (int state = 0; state < blockStates.length; state++) {
				icons[metadata][state] = par1IconRegister
						.registerIcon(prefix + subBlocks[metadata] + "." + blockStates[state]);
			}
		}

		this.blockIcon = par1IconRegister.registerIcon("modularmachines:" + getUnlocalizedName());
	}

	@Override
	public TileEntity createNewTileEntity(World world, int metadata) {
		switch (metadata) {
		case METADATA_ACCESSPORT:
			return new TileBlastFurnaceAccessPort();
		case METADATA_FLUIDPORT:
			return new TileBlastFurnaceFluidPort();
		default:
			return new TileBlastFurnaceBase();
		}
	}

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int par6, float par7,
			float par8, float par9) {
		if (player.isSneaking()) {
			return false;
		}

		int metadata = world.getBlockMetadata(x, y, z);
		TileEntity te = world.getTileEntity(x, y, z);
		IMultiblockPart part = null;
		MultiblockControllerBase controller = null;

		if (te instanceof IMultiblockPart) {
			part = (IMultiblockPart) te;
			controller = part.getMultiblockController();
		}

		if (isCasing(metadata)) {
			// If the player's hands are empty and they rightclick on a
			// multiblock, they get a
			// multiblock-debugging message if the machine is not assembled.
			if (player.getCurrentEquippedItem() == null) {
				if (controller != null) {
					Exception e = controller.getLastValidationException();
					if (e != null) {
						player.addChatMessage(new ChatComponentText(e.getMessage()));
						return true;
					}
				} else {
					player.addChatMessage(new ChatComponentText(
							"Block is not connected to a reactor. This could be due to lag, or a bug. If the problem persists, try breaking and re-placing the block.")); // TODO
																																											// Localize
					return true;
				}
			}

			// If nonempty, or there was no error, just fall through
			return false;
		}

		// Don't open the controller GUI if the reactor isn't assembled
		if (isFluidPort(metadata) && (controller == null || !controller.isAssembled())) {
			return false;
		}

		if (!world.isRemote) {
			player.openGui(ModularMachines.instance, 0, world, x, y, z);
		}
		return true;
	}

	@Override
	public boolean isOpaqueCube() {
		return true;
	}

	@Override
	public boolean renderAsNormalBlock() {
		return true;
	}

	@Override
	public int damageDropped(int metadata) {
		return metadata;
	}

	public ItemStack getBlastFurnaceCasingItemStack() {
		return new ItemStack(this, 1, METADATA_CASING);
	}

	public ItemStack getBlastFurnaceBrickItemStack() {
		return new ItemStack(this, 1, METADATA_BRICK);
	}

	public ItemStack getAccessPortItemStack() {
		return new ItemStack(this, 1, METADATA_ACCESSPORT);
	}

	public ItemStack getCoolantPortItemStack() {
		return new ItemStack(this, 1, METADATA_FLUIDPORT);
	}

	@Override
	public void getSubBlocks(Item par1, CreativeTabs par2CreativeTabs, List par3List) {
		for (int metadata = 0; metadata < subBlocks.length; metadata++) {
			par3List.add(new ItemStack(this, 1, metadata));
		}
	}

	@Override
	public void breakBlock(World world, int x, int y, int z, Block block, int meta) {
		// Drop everything inside inventory blocks
		TileEntity te = world.getTileEntity(x, y, z);
		if (te instanceof IInventory) {
			IInventory inventory = ((IInventory) te);
			inv: for (int i = 0; i < inventory.getSizeInventory(); i++) {
				ItemStack itemstack = inventory.getStackInSlot(i);
				if (itemstack == null) {
					continue;
				}
				float xOffset = world.rand.nextFloat() * 0.8F + 0.1F;
				float yOffset = world.rand.nextFloat() * 0.8F + 0.1F;
				float zOffset = world.rand.nextFloat() * 0.8F + 0.1F;
				do {
					if (itemstack.stackSize <= 0) {
						continue inv;
					}
					int amountToDrop = world.rand.nextInt(21) + 10;
					if (amountToDrop > itemstack.stackSize) {
						amountToDrop = itemstack.stackSize;
					}
					itemstack.stackSize -= amountToDrop;
					EntityItem entityitem = new EntityItem(world, x + xOffset, y + yOffset, z + zOffset,
							new ItemStack(itemstack.getItem(), amountToDrop, itemstack.getItemDamage()));
					if (itemstack.getTagCompound() != null) {
						entityitem.getEntityItem().setTagCompound(itemstack.getTagCompound());
					}
					float motionMultiplier = 0.05F;
					entityitem.motionX = (float) world.rand.nextGaussian() * motionMultiplier;
					entityitem.motionY = (float) world.rand.nextGaussian() * motionMultiplier + 0.2F;
					entityitem.motionZ = (float) world.rand.nextGaussian() * motionMultiplier;
					world.spawnEntityInWorld(entityitem);
				} while (true);
			}
		}

		super.breakBlock(world, x, y, z, block, meta);
	}

	@Override
	public boolean canCreatureSpawn(EnumCreatureType type, IBlockAccess world, int x, int y, int z) {
		return false;
	}

	//// UGLY UI CODE HERE ////
	private IIcon getFluidPortIcon(IBlockAccess blockAccess, int x, int y, int z, int side) {
		TileEntity te = blockAccess.getTileEntity(x, y, z);
		if (te instanceof TileBlastFurnaceFluidPort) {
			TileBlastFurnaceFluidPort port = (TileBlastFurnaceFluidPort) te;

			if (!isReactorAssembled(port) || isOutwardsSide(port, side)) {
				switch (port.getType()) {
				case INPUT_AIR_HOT:
					return icons[METADATA_FLUIDPORT][PORT_AIR_HOT];
				case OUTPUT:
					return icons[METADATA_FLUIDPORT][PORT_OUTPUT];
				case SLAG:
					return icons[METADATA_FLUIDPORT][PORT_SLAG];
				case Gas_Blast_Furnace:
					return icons[METADATA_FLUIDPORT][PORT_GAS_BLAST_FURNACE];
				}
			}
		}
		return blockIcon;
	}

	private IIcon getAccessPortIcon(IBlockAccess blockAccess, int x, int y, int z, int side) {
		TileEntity te = blockAccess.getTileEntity(x, y, z);
		if (te instanceof TileBlastFurnaceAccessPort) {
			TileBlastFurnaceAccessPort port = (TileBlastFurnaceAccessPort) te;

			if (!isReactorAssembled(port) || isOutwardsSide(port, side)) {
				return icons[METADATA_ACCESSPORT][0];
			}
		}
		return blockIcon;
	}

	private IIcon getBrickIcon(IBlockAccess blockAccess, int x, int y, int z, int side) {
		TileEntity te = blockAccess.getTileEntity(x, y, z);
		if (te instanceof TileBlastFurnacePart) {
			return icons[METADATA_BRICK][0];
		}
		return blockIcon;
	}

	private static final int DEFAULT = 0;
	private static final int FACE = 1;
	private static final int CORNER = 2;
	private static final int EASTWEST = 3;
	private static final int NORTHSOUTH = 4;
	private static final int VERTICAL = 5;

	private IIcon getCasingIcon(IBlockAccess blockAccess, int x, int y, int z, int side) {
		TileEntity te = blockAccess.getTileEntity(x, y, z);
		if (te instanceof TileBlastFurnacePart) {
			TileBlastFurnacePart part = (TileBlastFurnacePart) te;
			PartPosition position = part.getPartPosition();
			BlastFurnaceController reactor = part.getBlastFurnaceController();
			if (reactor == null || !reactor.isAssembled()) {
				return icons[METADATA_CASING][DEFAULT];
			}

			switch (position) {
			case BottomFace:
			case TopFace:
			case EastFace:
			case WestFace:
			case NorthFace:
			case SouthFace:
				return icons[METADATA_CASING][FACE];
			case FrameCorner:
				return icons[METADATA_CASING][CORNER];
			case Frame:
				return getCasingEdgeIcon(part, reactor, side);
			case Interior:
			case Unknown:
			default:
				return icons[METADATA_CASING][DEFAULT];
			}
		}
		return icons[METADATA_CASING][DEFAULT];
	}

	private IIcon getCasingEdgeIcon(TileBlastFurnacePart part, BlastFurnaceController reactor, int side) {
		if (reactor == null || !reactor.isAssembled()) {
			return icons[METADATA_CASING][DEFAULT];
		}

		CoordTriplet minCoord = reactor.getMinimumCoord();
		CoordTriplet maxCoord = reactor.getMaximumCoord();

		boolean xExtreme, yExtreme, zExtreme;
		xExtreme = yExtreme = zExtreme = false;

		if (part.xCoord == minCoord.x || part.xCoord == maxCoord.x) {
			xExtreme = true;
		}
		if (part.yCoord == minCoord.y || part.yCoord == maxCoord.y) {
			yExtreme = true;
		}
		if (part.zCoord == minCoord.z || part.zCoord == maxCoord.z) {
			zExtreme = true;
		}

		int idx = DEFAULT;
		if (!xExtreme) {
			if (side < 4) {
				idx = EASTWEST;
			}
		} else if (!yExtreme) {
			if (side > 1) {
				idx = VERTICAL;
			}
		} else { // !zExtreme
			if (side < 2) {
				idx = NORTHSOUTH;
			} else if (side > 3) {
				idx = EASTWEST;
			}
		}
		return icons[METADATA_CASING][idx];
	}

	private IIcon getFaceOrBlockIcon(IBlockAccess blockAccess, int x, int y, int z, int side, int metadata) {
		TileEntity te = blockAccess.getTileEntity(x, y, z);
		if (te instanceof TileBlastFurnacePart) {
			TileBlastFurnacePart part = (TileBlastFurnacePart) te;
			if (!isReactorAssembled(part) || isOutwardsSide(part, side)) {
				return icons[metadata][0];
			}
		}
		return this.blockIcon;
	}

	/**
	 * @param part
	 *            The part whose sides we're checking
	 * @param side
	 *            The side to compare to the part
	 * @return True if `side` is the outwards-facing face of `part`
	 */
	private boolean isOutwardsSide(TileBlastFurnacePart part, int side) {
		ForgeDirection outDir = part.getOutwardsDir();
		return outDir.ordinal() == side;
	}

	private boolean isReactorAssembled(TileBlastFurnacePart part) {
		BlastFurnaceController reactor = part.getBlastFurnaceController();
		return reactor != null && reactor.isAssembled();
	}
}