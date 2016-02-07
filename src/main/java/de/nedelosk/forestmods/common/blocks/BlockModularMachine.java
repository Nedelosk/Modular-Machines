package de.nedelosk.forestmods.common.blocks;

import java.util.ArrayList;
import java.util.List;

import com.google.common.collect.Lists;

import buildcraft.api.tools.IToolWrench;
import de.nedelosk.forestcore.utils.WorldUtil;
import de.nedelosk.forestmods.api.modular.tile.IModularTileEntity;
import de.nedelosk.forestmods.api.modules.IModuleDropped;
import de.nedelosk.forestmods.api.utils.ModuleRegistry;
import de.nedelosk.forestmods.api.utils.ModuleRegistry.ModuleItem;
import de.nedelosk.forestmods.api.utils.ModuleStack;
import de.nedelosk.forestmods.common.blocks.tile.TileModularMachine;
import de.nedelosk.forestmods.common.core.ModularMachines;
import de.nedelosk.forestmods.common.network.PacketHandler;
import de.nedelosk.forestmods.common.network.packets.PacketSyncManagers;
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
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

public class BlockModularMachine extends BlockModular {

	public BlockModularMachine() {
		super(Material.iron);
		setStepSound(soundTypeMetal);
		setHardness(2.0F);
		setHarvestLevel("pickaxe", 1);
		setBlockName("modular");
		setBlockTextureName("iron_block");
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
		return new TileModularMachine();
	}

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float par7, float par8, float par9) {
		if (!world.isRemote) {
			TileEntity tile = world.getTileEntity(x, y, z);
			if (tile instanceof TileModularMachine) {
				TileModularMachine modularMachine = (TileModularMachine) tile;
				if (modularMachine.getModular() != null && modularMachine.getModular().isAssembled()) {
					player.openGui(ModularMachines.instance, 0, player.worldObj, x, y, z);
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
			}
		}
		return false;
	}

	@Override
	public void getSubBlocks(Item item, CreativeTabs ptab, List list) {
	}

	@Override
	public void breakBlock(World world, int x, int y, int z, Block block, int meta) {
		TileEntity tile = world.getTileEntity(x, y, z);
		if (tile instanceof IModularTileEntity) {
			IModularTileEntity modular = (IModularTileEntity) tile;
			if (modular.getModular() != null && modular.getModular().getModuleManager() != null) {
				List<ItemStack> drops = Lists.newArrayList();
				for ( ModuleStack stack : (List<ModuleStack>) modular.getModular().getModuleManager().getModuleStacks() ) {
					if (stack != null) {
						if (stack.getModule() instanceof IModuleDropped) {
							drops.add(((IModuleDropped) stack.getModule()).onDropItem(stack, modular.getModular()).copy());
						} else {
							drops.add(stack.getItemStack().copy());
						}
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
		if (tile instanceof IModularTileEntity) {
			IModularTileEntity modular = (IModularTileEntity) tile;
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
	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase entity, ItemStack stack) {
		super.onBlockPlacedBy(world, x, y, z, entity, stack);
		if (world.isRemote) {
			return;
		}
		int heading = MathHelper.floor_double(entity.rotationYaw * 4.0F / 360.0F + 0.5D) & 3;
		IModularTileEntity tile = (IModularTileEntity) world.getTileEntity(x, y, z);
		tile.setFacing(getFacingForHeading(heading));
		if (entity instanceof EntityPlayer) {
			EntityPlayer player = (EntityPlayer) entity;
			tile.setOwner(player.getGameProfile());
		}
		world.markBlockForUpdate(x, y, z);
	}

	protected short getFacingForHeading(int heading) {
		switch (heading) {
			case 0:
				return 2;
			case 1:
				return 5;
			case 2:
				return 3;
			case 3:
			default:
				return 4;
		}
	}
}
