package nedelosk.forestcore.library.multiblock;

import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.IIcon;
import net.minecraft.util.StatCollector;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import nedelosk.forestcore.library.CoordTriplet;
import nedelosk.forestcore.library.multiblock.rectangular.PartPosition;
import nedelosk.forestcore.library.multiblock.rectangular.RectangularMultiblockControllerBase;
import nedelosk.modularmachines.common.ModularMachines;
import nedelosk.modularmachines.common.core.TabModularMachines;

public abstract class BlockMultiblockBasic<M extends RectangularMultiblockControllerBase> extends BlockContainer {

	public static final int METADATA_CASING = 0;
	public static final int METADATA_CONTROLLER = 1;
	
	private static final int CONTROLLER_OFF = 0;
	private static final int CONTROLLER_IDLE = 1;
	private static final int CONTROLLER_ACTIVE = 2;

	protected String[] subBlocks;

	protected String[][] states;

	protected IIcon[][] icons = new IIcon[states.length][];

	public static boolean isCasing(int metadata) {
		return metadata == METADATA_CASING;
	}

	public static boolean isController(int metadata){
		return metadata == METADATA_CONTROLLER;
	}
	
	protected Class<? extends M> multiblockClass;

	public BlockMultiblockBasic(Material material, Class<? extends M> multiblockClass, String[] subBlocks, String[][] states) {
		super(material);

		setStepSound(soundTypeMetal);
		setHardness(2.0f);
		setCreativeTab(TabModularMachines.core);
		
		this.multiblockClass = multiblockClass;
		this.subBlocks = subBlocks;
		this.states = states;
	}
	
	public abstract String getMultiblockName();

	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister par1IconRegister) {
		String prefix = "modularmachines:" + getMultiblockName() + "/";

		for (int metadata = 0; metadata < states.length; ++metadata) {
			String[] blockStates = states[metadata];
			icons[metadata] = new IIcon[blockStates.length];

			for (int state = 0; state < blockStates.length; state++) {
				icons[metadata][state] = par1IconRegister
						.registerIcon(prefix + subBlocks[metadata] + "/" + blockStates[state]);
			}
		}

		this.blockIcon = par1IconRegister.registerIcon("modularmachines:" + getMultiblockName() + "/default");
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
					player.addChatMessage(new ChatComponentText(StatCollector.translateToLocalFormatted("multiblock.error.notConnected", getMultiblockName())));
					return true;
				}
			}

			// If nonempty, or there was no error, just fall through
			return false;
		}

		if ((whenMultiblockNotAssembled(metadata)) && (controller == null || !controller.isAssembled())) {
			return false;
		}

		if (!world.isRemote) {
			player.openGui(ModularMachines.instance, 0, world, x, y, z);
		}
		return true;
	}
	
	public boolean whenMultiblockNotAssembled(int metadata){
		return isController(metadata);
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

	protected IIcon getControllerIcon(IBlockAccess blockAccess, int x, int y, int z, int side) {
		TileEntity te = blockAccess.getTileEntity(x, y, z);
		if (te instanceof TileMultiblockBase) {
			if(((TileMultiblockBase) te).getMultiblockControllerType().equals(multiblockClass)){
				TileMultiblockBase<M> part = (TileMultiblockBase<M>) te;
				M m = part.getController();
	
				if (m == null || !m.isAssembled()) {
					return icons[METADATA_CONTROLLER][CONTROLLER_OFF];
				} else if (!isOutwardsSide(part, side)) {
					return blockIcon;
				} else if (m.isActive()) {
					return icons[METADATA_CONTROLLER][CONTROLLER_ACTIVE];
				} else {
					return icons[METADATA_CONTROLLER][CONTROLLER_IDLE];
				}
			}
		}
		return blockIcon;
	}
	
	private static final int DEFAULT = 0;
	private static final int FACE = 1;
	private static final int CORNER = 2;
	private static final int EASTWEST = 3;
	private static final int NORTHSOUTH = 4;
	private static final int VERTICAL = 5;
	
	protected IIcon getCasingIcon(IBlockAccess blockAccess, int x, int y, int z, int side) {
		TileEntity te = blockAccess.getTileEntity(x, y, z);
		if (te instanceof TileMultiblockBase) {
			if(((TileMultiblockBase) te).getMultiblockControllerType().equals(multiblockClass)){
				TileMultiblockBase<M> part = (TileMultiblockBase<M>) te;
				PartPosition position = part.getPartPosition();
				M m = part.getController();
				if (m == null || !m.isAssembled()) {
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
					return getCasingEdgeIcon(part, m, side);
				case Interior:
				case Unknown:
				default:
					return icons[METADATA_CASING][DEFAULT];
				}
			}
		}
		return icons[METADATA_CASING][DEFAULT];
	}

	protected IIcon getCasingEdgeIcon(TileMultiblockBase<M> part, M m, int side) {
		if (m == null || !m.isAssembled()) {
			return icons[METADATA_CASING][DEFAULT];
		}

		CoordTriplet minCoord = m.getMinimumCoord();
		CoordTriplet maxCoord = m.getMaximumCoord();

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

	/**
	 * @param part
	 *            The part whose sides we're checking
	 * @param side
	 *            The side to compare to the part
	 * @return True if `side` is the outwards-facing face of `part`
	 */
	protected boolean isOutwardsSide(TileMultiblockBase<M> part, int side) {
		ForgeDirection outDir = part.getOutwardsDir();
		return outDir.ordinal() == side;
	}

	protected boolean isMultiblockAssembled(TileMultiblockBase<M> part) {
		M m = part.getController();
		return m != null && m.isAssembled();
	}
}