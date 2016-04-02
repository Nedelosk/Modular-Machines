package de.nedelosk.forestmods.common.events;

import java.util.Random;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import de.nedelosk.forestcore.utils.BlockPos;
import de.nedelosk.forestmods.api.modular.IModular;
import de.nedelosk.forestmods.api.modular.managers.IModularModuleManager;
import de.nedelosk.forestmods.api.utils.ModuleManager;
import de.nedelosk.forestmods.api.utils.ModuleStack;
import de.nedelosk.forestmods.common.blocks.tile.TileCharcoalKiln;
import de.nedelosk.forestmods.common.blocks.tile.TileModularMachine;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
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
		ItemStack currentItem = player.getHeldItem();
		if (event.action == Action.RIGHT_CLICK_BLOCK && currentItem != null && currentItem.stackSize > 0) {
			TileEntity tile = world.getTileEntity(event.x, event.y, event.z);
			if (tile instanceof TileModularMachine) {
				ModuleStack currentModule = ModuleManager.moduleRegistry.getModuleFromItem(currentItem).copy();
				if (currentModule != null) {
					if (!world.isRemote) {
						TileModularMachine modularTile = (TileModularMachine) tile;
						if (modularTile.getModular() != null) {
							IModular modular = modularTile.getModular();
							modular.getManager(IModularModuleManager.class).addModule(currentItem.copy(), currentModule);
							try {
								currentModule.getModule().onAddInModular(modular, currentModule);
							} catch (Exception e) {
								e.printStackTrace();
								return;
							}
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
			if (r.nextInt(16) == 0) {
				event.drops.add(new ItemStack(Items.stick));
			}
		}
	}

	@SideOnly(Side.CLIENT)
	@SubscribeEvent
	public void tooltipEvent(ItemTooltipEvent event) {
		ModuleStack stack = ModuleManager.moduleRegistry.getModuleFromItem(event.itemStack);
		if (stack != null) {
			event.toolTip.add(StatCollector.translateToLocal("mm.module.tooltip.type") + ": " + stack.getMaterial().getLocalizedName());
			event.toolTip.add(StatCollector.translateToLocal("mm.module.tooltip.tier") + ": " + stack.getMaterial().getTier());
			event.toolTip.add(StatCollector.translateToLocal("mm.module.tooltip.name") + ": "
					+ StatCollector.translateToLocal(stack.getModule().getUnlocalizedName(stack)));
			stack.getModule().addTooltip(stack, event.toolTip);
		}
	}
}
