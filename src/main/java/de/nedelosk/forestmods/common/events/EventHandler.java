package de.nedelosk.forestmods.common.events;

import java.util.Random;

import cpw.mods.fml.common.eventhandler.EventPriority;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import de.nedelosk.forestmods.common.blocks.BlockModularMachine;
import de.nedelosk.forestmods.common.blocks.tile.TileModular;
import de.nedelosk.forestmods.common.core.BlockManager;
import de.nedelosk.forestmods.library.modular.IModular;
import de.nedelosk.forestmods.library.modules.IModule;
import de.nedelosk.forestmods.library.modules.IModuleContainer;
import de.nedelosk.forestmods.library.modules.IModuleController;
import de.nedelosk.forestmods.library.modules.ModuleManager;
import de.nedelosk.forestmods.library.modules.casing.IModuleCasing;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
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

	@SubscribeEvent(priority = EventPriority.HIGHEST)
	public void onBlockActivated(PlayerInteractEvent event) {
		EntityPlayer player = event.entityPlayer;
		World world = event.world;
		Block block = world.getBlock(event.x, event.y, event.z);
		int metadata = world.getBlockMetadata(event.x, event.y, event.z);
		ItemStack currentItem = player.getHeldItem();
		IModuleContainer currentContainer = ModuleManager.moduleRegistry.getModuleFromItem(currentItem);
		if (event.action == Action.RIGHT_CLICK_BLOCK && currentItem != null && currentItem.stackSize > 0) {
			if (currentContainer != null) {
				if (block instanceof BlockModularMachine) {
					TileEntity tile = world.getTileEntity(event.x, event.y, event.z);
					if (tile instanceof TileModular) {
						IModule currentModule = ModuleManager.moduleRegistry.createModule(((TileModular) tile).getModular(), currentContainer);
						if (currentContainer != null && !(currentModule instanceof IModuleController)) {
							if (!world.isRemote) {
								TileModular modularTile = (TileModular) tile;
								if (modularTile.getModular() != null) {
									IModular modular = modularTile.getModular();
									ItemStack moduleItem = new ItemStack(currentItem.getItem(), 1, currentItem.getItemDamage());
									if (currentItem.hasTagCompound()) {
										moduleItem.setTagCompound((NBTTagCompound) currentItem.getTagCompound().copy());
									}
									if (modular.addModule(moduleItem.copy(), currentModule)) {
										if (!player.capabilities.isCreativeMode) {
											currentItem.stackSize--;
											if (currentItem.stackSize == 0) {
												currentItem = null;
											}
											player.setCurrentItemOrArmor(0, currentItem);
										}
										event.setCanceled(true);
										world.markBlockForUpdate(event.x, event.y, event.z);
									}
								}
							}
						}
					}
				} else {
					ItemStack casingStack = new ItemStack(block, 1, metadata);

					if (currentContainer != null) {
						IModuleContainer casingContainer = ModuleManager.moduleRegistry.getModuleFromItem(casingStack);
						if (casingContainer != null && IModuleCasing.class.isAssignableFrom(casingContainer.getModuleClass())
								&& currentContainer != null && IModuleController.class.isAssignableFrom(currentContainer.getModuleClass())) {
							world.setBlock(event.x, event.y, event.z, BlockManager.blockModular);
							BlockManager.blockModular.onBlockPlacedBy(world, event.x, event.y, event.z, event.entityPlayer, null);
							TileEntity tile = world.getTileEntity(event.x, event.y, event.z);
							if (tile instanceof TileModular) {
								if (!world.isRemote) {
									IModule currentModule = ModuleManager.moduleRegistry.createModule(((TileModular) tile).getModular(), currentContainer);
									IModule casingModule = ModuleManager.moduleRegistry.createModule(((TileModular) tile).getModular(), casingContainer);
									TileModular modularTile = (TileModular) tile;
									if (modularTile.getModular() != null) {
										IModular modular = modularTile.getModular();
										ItemStack moduleItem = new ItemStack(currentItem.getItem(), 1, currentItem.getItemDamage());
										if (currentItem.hasTagCompound()) {
											moduleItem.setTagCompound((NBTTagCompound) currentItem.getTagCompound().copy());
										}
										if (modular.addModule(moduleItem.copy(), currentModule)) {
											if (modular.addModule(casingStack.copy(), casingModule)) {
												if (!player.capabilities.isCreativeMode) {
													currentItem.stackSize--;
													if (currentItem.stackSize == 0) {
														currentItem = null;
													}
													player.setCurrentItemOrArmor(0, currentItem);
												}
												event.setCanceled(true);
												world.markBlockForUpdate(event.x, event.y, event.z);
											}
										}
									}
								}
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
			if (r.nextInt(16) == 0) {
				event.drops.add(new ItemStack(Items.stick));
			}
		}
	}

	@SideOnly(Side.CLIENT)
	@SubscribeEvent
	public void tooltipEvent(ItemTooltipEvent event) {
		IModule module = ModuleManager.moduleRegistry.createFakeModule(ModuleManager.moduleRegistry.getModuleFromItem(event.itemStack));
		if (module != null) {
			event.toolTip.add(StatCollector.translateToLocal("mm.module.tooltip.type") + ": " + module.getModuleContainer().getMaterial().getLocalizedName());
			event.toolTip.add(StatCollector.translateToLocal("mm.module.tooltip.tier") + ": " + module.getModuleContainer().getMaterial().getTier());
			event.toolTip.add(StatCollector.translateToLocal("mm.module.tooltip.name") + ": "
					+ StatCollector.translateToLocal(module.getUnlocalizedName()));
			module.addTooltip(event.toolTip);
		}
	}
}
