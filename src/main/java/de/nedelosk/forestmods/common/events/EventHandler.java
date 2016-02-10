package de.nedelosk.forestmods.common.events;

import java.util.Random;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import de.nedelosk.forestcore.utils.BlockPos;
import de.nedelosk.forestmods.api.modules.IModuleAddable;
import de.nedelosk.forestmods.api.modules.IModuleSaver;
import de.nedelosk.forestmods.api.modules.basic.IModuleCasing;
import de.nedelosk.forestmods.api.utils.ModuleRegistry;
import de.nedelosk.forestmods.api.utils.ModuleRegistry.ModuleItem;
import de.nedelosk.forestmods.api.utils.ModuleStack;
import de.nedelosk.forestmods.common.blocks.tile.TileCharcoalKiln;
import de.nedelosk.forestmods.common.blocks.tile.TileModularMachine;
import de.nedelosk.forestmods.common.core.BlockManager;
import de.nedelosk.forestmods.common.modular.ModularMachine;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.Action;
import net.minecraftforge.event.world.BlockEvent;

public class EventHandler {

	@SubscribeEvent
	public void onBlockActivated(PlayerInteractEvent event) {
		EntityPlayer player = event.entityPlayer;
		World world = event.world;
		if (event.action == Action.RIGHT_CLICK_BLOCK && player.getCurrentEquippedItem() != null) {
			Block block = world.getBlock(event.x, event.y, event.z);
			int meta = world.getBlockMetadata(event.x, event.y, event.z);
			TileEntity tileOld = world.getTileEntity(event.x, event.y, event.z);
			ItemStack blockStack = new ItemStack(block, 1, meta);
			if (ModuleRegistry.getModuleFromItem(blockStack) != null
					&& ModuleRegistry.getModuleFromItem(blockStack).moduleStack.getModule() instanceof IModuleCasing) {
				ModuleStack<IModuleCasing, IModuleSaver> casingStack = ModuleRegistry.getModuleFromItem(blockStack).moduleStack;
				if (ModuleRegistry.getModuleFromItem(player.getCurrentEquippedItem()) != null
						&& ModuleRegistry.getModuleFromItem(player.getCurrentEquippedItem()).moduleStack.getModule() instanceof IModuleAddable
						&& !(ModuleRegistry.getModuleFromItem(player.getCurrentEquippedItem()).moduleStack.getModule() instanceof IModuleCasing)) {
					if (!world.isRemote) {
						ModuleStack stack = ModuleRegistry.getModuleFromItem(player.getCurrentEquippedItem()).moduleStack;
						world.setBlock(event.x, event.y, event.z, BlockManager.blockModularMachines);
						TileEntity tile = world.getTileEntity(event.x, event.y, event.z);
						if (!(tile instanceof TileModularMachine)) {
							event.setCanceled(true);
							return;
						}
						TileModularMachine modularTile = (TileModularMachine) tile;
						ModularMachine machine = new ModularMachine();
						modularTile.setModular(machine);
						stack.setItemStack(player.getCurrentEquippedItem());
						machine.getModuleManager().addModule(casingStack);
						machine.getModuleManager().addModule(stack);
						if (casingStack.getModule() instanceof IModuleAddable) {
							try {
								((IModuleAddable) casingStack.getModule()).onAddInModular(machine, casingStack);
							} catch (Exception e) {
								world.setBlock(event.x, event.y, event.z, block, meta, 3);
								world.setTileEntity(event.x, event.y, event.z, tileOld);
								return;
							}
						}
						if (stack.getModule() instanceof IModuleAddable) {
							try {
								((IModuleAddable) stack.getModule()).onAddInModular(machine, stack);
							} catch (Exception e) {
								world.setBlock(event.x, event.y, event.z, block, meta, 3);
								world.setTileEntity(event.x, event.y, event.z, tileOld);
								return;
							}
						}
						if (!player.capabilities.isCreativeMode) {
							ItemStack currentItem = player.getCurrentEquippedItem();
							if (currentItem.stackSize < 2) {
								currentItem = null;
							} else {
								currentItem.stackSize--;
							}
							player.setCurrentItemOrArmor(0, currentItem);
						}
						ItemStack stackModular = new ItemStack(BlockManager.blockModularMachines);
						NBTTagCompound nbt = new NBTTagCompound();
						stackModular.writeToNBT(nbt);
						stackModular.setTagCompound(nbt);
						BlockManager.blockModularMachines.onBlockPlacedBy(world, event.x, event.y, event.z, player, stackModular);
						event.setCanceled(true);
						if (world.isRemote) {
							return;
						}
						world.markBlockForUpdate(event.x, event.y, event.z);
					}
				}
			}
		}
	}

	@SubscribeEvent
	public void onPlayerUse(PlayerInteractEvent event) {
		if (!event.world.isRemote && event.action == Action.RIGHT_CLICK_BLOCK) {
			EntityPlayer player = event.entityPlayer;
			World world = event.world;
			int x = event.x;
			int y = event.y;
			int z = event.z;
			if (!(world.getTileEntity(x, y, z) instanceof TileCharcoalKiln)) {
				return;
			}
			ItemStack stack = player.getCurrentEquippedItem();
			if (stack != null && (stack.getItem() == Items.flint_and_steel || stack.getItem() == Item.getItemFromBlock(Blocks.torch))) {
				TileCharcoalKiln kiln = (TileCharcoalKiln) world.getTileEntity(x, y, z);
				if (kiln.isConnected() && kiln.getController().isAssembled() && !kiln.getController().isActive()) {
					kiln.getController().setIsActive();
					if (stack.getItem() == Items.flint_and_steel) {
						stack.damageItem(1, player);
					}
					BlockPos min = kiln.getController().getMinimumCoord();
					BlockPos max = kiln.getController().getMaximumCoord();
					for ( int xPos = min.x; xPos < max.x; xPos++ ) {
						for ( int zPos = min.z; zPos < max.z; zPos++ ) {
							for ( int yPos = min.y; yPos < max.y; yPos++ ) {
								world.markBlockForUpdate(xPos, yPos, zPos);
							}
						}
					}
				}
			}
		}
	}

	@SubscribeEvent
	public void stickDrop(BlockEvent.HarvestDropsEvent event) {
		Random r = new Random();
		if (event.block.isLeaves(event.world, event.x, event.y, event.z)) {
			if (r.nextInt(31) == 0) {
				event.drops.add(new ItemStack(Items.stick));
			}
		}
	}

	@SideOnly(Side.CLIENT)
	@SubscribeEvent
	public void tooltipEvent(ItemTooltipEvent event) {
		ModuleItem moduleItem = ModuleRegistry.getModuleFromItem(event.itemStack);
		if (moduleItem != null) {
			event.toolTip.add(StatCollector.translateToLocal("mm.module.tooltip.type") + ": " + moduleItem.material.getLocalName());
			event.toolTip.add(StatCollector.translateToLocal("mm.module.tooltip.tier") + ": " + moduleItem.material.getTier());
			event.toolTip.add(StatCollector.translateToLocal("mm.module.tooltip.name") + ": "
					+ StatCollector.translateToLocal(moduleItem.moduleStack.getModule().getUnlocalizedName(moduleItem.moduleStack)));
		}
	}
}
