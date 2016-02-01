package nedelosk.modularmachines.common.events;

import cpw.mods.fml.common.eventhandler.Event.Result;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import nedelosk.modularmachines.api.modules.IModuleAddable;
import nedelosk.modularmachines.api.modules.IModuleSaver;
import nedelosk.modularmachines.api.modules.basic.IModuleCasing;
import nedelosk.modularmachines.api.utils.ModuleRegistry;
import nedelosk.modularmachines.api.utils.ModuleRegistry.ModuleItem;
import nedelosk.modularmachines.api.utils.ModuleStack;
import nedelosk.modularmachines.common.blocks.tile.TileModularMachine;
import nedelosk.modularmachines.common.modular.ModularMachine;
import nedelosk.modularmachines.modules.ModuleModular;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.Action;

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
						&& !(ModuleRegistry.getModuleFromItem(player.getCurrentEquippedItem()).moduleStack.getModule() instanceof IModuleCasing)){
					if(!world.isRemote){
						ModuleStack stack = ModuleRegistry.getModuleFromItem(player.getCurrentEquippedItem()).moduleStack;
						world.setBlock(event.x, event.y, event.z, ModuleModular.BlockManager.Modular_Machine.block());
						TileEntity tile = world.getTileEntity(event.x, event.y, event.z);
						if (!(tile instanceof TileModularMachine)) {
							event.setCanceled(true);
							return;
						}
						TileModularMachine modularTile = (TileModularMachine) tile;
						ModularMachine machine = new ModularMachine();
						stack.setItemStack(player.getCurrentEquippedItem());
						casingStack.setItemStack(player.getCurrentEquippedItem());
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
						modularTile.setModular(machine);
						if (!player.capabilities.isCreativeMode) {
							ItemStack currentItem = player.getCurrentEquippedItem();
							if (currentItem.stackSize < 2) {
								currentItem = null;
							} else {
								currentItem.stackSize--;
							}
							player.setCurrentItemOrArmor(0, currentItem);
						}
					}
				}
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
