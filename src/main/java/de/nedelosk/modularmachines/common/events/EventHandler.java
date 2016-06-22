package de.nedelosk.modularmachines.common.events;

import java.util.Random;

import de.nedelosk.modularmachines.api.modular.IModular;
import de.nedelosk.modularmachines.api.modules.IModule;
import de.nedelosk.modularmachines.api.modules.IModuleContainer;
import de.nedelosk.modularmachines.api.modules.IModuleController;
import de.nedelosk.modularmachines.api.modules.ModuleManager;
import de.nedelosk.modularmachines.api.modules.casing.IModuleCasing;
import de.nedelosk.modularmachines.common.blocks.BlockModularMachine;
import de.nedelosk.modularmachines.common.blocks.tile.TileModular;
import de.nedelosk.modularmachines.common.core.BlockManager;
import de.nedelosk.modularmachines.common.utils.Translator;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.RightClickBlock;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EventHandler {

	@SubscribeEvent
	public void stickDrop(BlockEvent.HarvestDropsEvent event) {
		Random r = new Random();
		if (event.getState().getBlock().isLeaves(event.getState(), event.getWorld(), event.getPos())) {
			if (r.nextInt(16) == 0) {
				event.getDrops().add(new ItemStack(Items.STICK));
			}
		}
	}

	@SideOnly(Side.CLIENT)
	@SubscribeEvent
	public void tooltipEvent(ItemTooltipEvent event) {
		IModuleContainer container = ModuleManager.getContainerFromItem(event.getItemStack());
		if (container != null) {
			event.getToolTip().add(Translator.translateToLocal("mm.module.tooltip.type") + ": " + container.getMaterial().getLocalizedName());
			event.getToolTip().add(Translator.translateToLocal("mm.module.tooltip.tier") + ": " + container.getMaterial().getTier());
			event.getToolTip().add(Translator.translateToLocal("mm.module.tooltip.name") + ": "
					+ Translator.translateToLocal(container.getUnlocalizedName()));
			container.addTooltip(event.getToolTip());
		}
	}
}
