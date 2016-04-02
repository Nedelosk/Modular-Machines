package de.nedelosk.forestmods.common.blocks;

import java.util.ArrayList;
import java.util.List;

import com.google.common.collect.Lists;

import buildcraft.api.tools.IToolWrench;
import de.nedelosk.forestcore.blocks.BlockContainerForest;
import de.nedelosk.forestcore.utils.WorldUtil;
import de.nedelosk.forestmods.api.modular.IModularTileEntity;
import de.nedelosk.forestmods.api.modular.managers.IModularModuleManager;
import de.nedelosk.forestmods.api.utils.ModuleStack;
import de.nedelosk.forestmods.common.blocks.tile.TileMachineBase;
import de.nedelosk.forestmods.common.blocks.tile.TileModularAssembler;
import de.nedelosk.forestmods.common.blocks.tile.TileModularMachine;
import de.nedelosk.forestmods.common.core.ForestMods;
import de.nedelosk.forestmods.common.core.TabModularMachines;
import de.nedelosk.forestmods.common.modular.ModularMachine;
import de.nedelosk.forestmods.common.modules.ModuleRegistry;
import de.nedelosk.forestmods.common.network.PacketHandler;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class BlockModularMachine extends BlockContainerForest {

	public BlockModularMachine() {
		super(Material.iron);
		setStepSound(soundTypeMetal);
		setHardness(2.0F);
		setHarvestLevel("pickaxe", 1);
		setBlockName("modular");
		setBlockTextureName("anvil_base");
		setCreativeTab(TabModularMachines.tabModules);
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
	public TileEntity createNewTileEntity(World world, int meta) {
		if (meta == 1) {
			return new TileModularAssembler();
		} else {
			return new TileModularMachine();
		}
	}
	
	@Override
	public void onBlockAdded(World p_149726_1_, int p_149726_2_, int p_149726_3_, int p_149726_4_) {
		super.onBlockAdded(p_149726_1_, p_149726_2_, p_149726_3_, p_149726_4_);
	}

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float par7, float par8, float par9) {
		if (!world.isRemote) {
			TileEntity tile = world.getTileEntity(x, y, z);
			if (tile instanceof TileModularMachine) {
				TileModularMachine modularMachine = (TileModularMachine) tile;
				if (modularMachine.getModular() == null) {
					return false;
				}
				if (modularMachine.getModular() != null && modularMachine.getModular().isAssembled()) {
					player.openGui(ForestMods.instance, 0, player.worldObj, x, y, z);
					return true;
				}
				if (player.getCurrentEquippedItem() != null) {
					if (player.getCurrentEquippedItem().getItem() instanceof IToolWrench) {
						modularMachine.assembleModular();
						Exception e = modularMachine.getModular().getLastException();
						if (e != null) {
							player.addChatMessage(new ChatComponentText(e.getMessage()));
						} else {
							modularMachine.getModular().getInventoryManager().addInventorys();
							PacketHandler.INSTANCE.sendTo(new PacketSyncManagers(modularMachine), (EntityPlayerMP) player);
						}
						return true;
					} else if (ModuleRegistry.getModuleFromItem(player.getCurrentEquippedItem()) != null) {
						ModuleItem item = ModuleRegistry.getModuleFromItem(player.getCurrentEquippedItem());
						item.moduleStack.setItemStack(player.getCurrentEquippedItem());
						boolean addModule = modularMachine.getModular().getModuleManager().addModule(item.moduleStack);
						if (addModule) {
							if (!player.capabilities.isCreativeMode) {
								ItemStack currentItem = player.getCurrentEquippedItem();
								if (currentItem.stackSize < 2) {
									currentItem = null;
								} else {
									currentItem.stackSize--;
								}
								player.setCurrentItemOrArmor(0, currentItem);
							}
							modularMachine.getModular().getInventoryManager().addInventorys();
							world.markBlockForUpdate(x, y, z);
						}
						return addModule;
					}
				} else {
					Exception e = modularMachine.getModular().getLastException();
					if (e != null) {
						player.addChatMessage(new ChatComponentText(e.getMessage()));
						return true;
					}
				}
			} else {
				player.openGui(ForestMods.instance, 0, player.worldObj, x, y, z);
				return true;
			}
		}
		return false;
	}

	@Override
	public void getSubBlocks(Item item, CreativeTabs ptab, List list) {
		list.add(new ItemStack(item, 1, 1));
	}

	@Override
	public void breakBlock(World world, int x, int y, int z, Block block, int meta) {
		TileEntity tile = world.getTileEntity(x, y, z);
		if (tile instanceof IModularTileEntity) {
			IModularTileEntity modular = (IModularTileEntity) tile;
			if (modular.getModular() != null && modular.getModular().getManager(IModularModuleManager.class) != null) {
				List<ItemStack> drops = Lists.newArrayList();
				for ( ModuleStack stack : (List<ModuleStack>) modular.getModular().getManager(IModularModuleManager.class).getModuleStacks() ) {
					if (stack != null) {
						drops.add(stack.getModule().getDropItem(stack, modular.getModular()).copy());
					}
				}
				WorldUtil.dropItem(world, x, y, z, drops);
			}
		}
		super.breakBlock(world, x, y, z, block, meta);
	}

	@Override
	public ItemStack getPickBlock(MovingObjectPosition target, World world, int x, int y, int z) {
		ItemStack stack = new ItemStack(this);
		TileEntity tile = world.getTileEntity(x, y, z);
		if (tile instanceof IModularState) {
			IModularState modular = (IModularState) tile;
			NBTTagCompound nbtTag = new NBTTagCompound();
			modular.writeToNBT(nbtTag);
			stack.setTagCompound(nbtTag);
		}
		return stack;
	}

	@Override
	public ArrayList<ItemStack> getDrops(World world, int x, int y, int z, int metadata, int fortune) {
		return new ArrayList<ItemStack>();
	}

	@Override
	public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int x, int y, int z) {
		int md = world.getBlockMetadata(x, y, z);
		if (md == 1) {
			return AxisAlignedBB.getBoundingBox(x, y, z, (double) x + 1, y + 0.8125D, (double) z + 1);
		} else {
			return AxisAlignedBB.getBoundingBox(x, y, z, x + 1D, y + 1D, z + 1D);
		}
	}

	@Override
	public AxisAlignedBB getSelectedBoundingBoxFromPool(World world, int x, int y, int z) {
		int md = world.getBlockMetadata(x, y, z);
		if (md == 1) {
			return AxisAlignedBB.getBoundingBox(x, y, z, (double) x + 1, y + 0.8125D, (double) z + 1);
		} else {
			return AxisAlignedBB.getBoundingBox(x, y, z, x + 1D, y + 1D, z + 1D);
		}
	}

	@Override
	public void setBlockBoundsBasedOnState(IBlockAccess iblockaccess, int x, int y, int z) {
		int md = iblockaccess.getBlockMetadata(x, y, z);
		if (md == 1) {
			setBlockBounds(0F, 0F, 0F, 1F, 0.8125F, 1F);
		} else {
			setBlockBounds(0F, 0F, 0F, 1F, 1F, 1F);
		}
	}

	@Override
	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase entity, ItemStack stack) {
		super.onBlockPlacedBy(world, x, y, z, entity, stack);
		if (world.isRemote) {
			return;
		}
		TileEntity tile = world.getTileEntity(x, y, z);
		if(tile instanceof TileModularMachine){
			TileModularMachine modular = (TileModularMachine) tile;
			modular.setModular(new ModularMachine());
			int heading = MathHelper.floor_double(entity.rotationYaw * 4.0F / 360.0F + 0.5D) & 3;
			modular.setFacing(getFacingForHeading(heading));
			if (entity instanceof EntityPlayer) {
				EntityPlayer player = (EntityPlayer) entity;
				modular.setOwner(player.getGameProfile());
			}
			world.markBlockForUpdate(x, y, z);
		}
	}

	protected ForgeDirection getFacingForHeading(int heading) {
		switch (heading) {
			case 0:
				return ForgeDirection.NORTH;
			case 1:
				return ForgeDirection.EAST;
			case 2:
				return ForgeDirection.SOUTH;
			case 3:
			default:
				return ForgeDirection.WEST;
		}
	}
}
