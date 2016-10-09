package de.nedelosk.modularmachines.api.modular;

import java.util.List;

import com.google.common.collect.Lists;

import de.nedelosk.modularmachines.api.modular.handlers.IModularHandler;
import de.nedelosk.modularmachines.api.modular.handlers.IModularHandlerItem;
import de.nedelosk.modularmachines.api.modules.position.EnumStoragePositions;
import de.nedelosk.modularmachines.api.modules.position.IStoragePosition;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;

public class ModularManager {

	@CapabilityInject(IModularHandler.class)
	public static Capability<IModularHandler> MODULAR_HANDLER_CAPABILITY;

	/* A list of all storage positions that a default modular assembler have.*/
	public static final List<IStoragePosition> DEFAULT_STORAGE_POSITIONS = Lists.newArrayList(EnumStoragePositions.CASING, EnumStoragePositions.LEFT, EnumStoragePositions.RIGHT, EnumStoragePositions.TOP, EnumStoragePositions.BACK);

	/* A helper to create modulars, modular assemblers and modular handlers*/
	public static IModularHelper helper;

	/**
	 * Write a modular to a item stack.
	 */
	public static ItemStack saveModularToItem(ItemStack modularItem, IModular modular, EntityPlayer player){
		IModularHandlerItem<NBTTagCompound> itemHandler = (IModularHandlerItem<NBTTagCompound>) modularItem.getCapability(MODULAR_HANDLER_CAPABILITY, null);
		modularItem = modularItem.copy();
		itemHandler.setModular(modular);
		itemHandler.setWorld(player.getEntityWorld());
		itemHandler.setOwner(player.getGameProfile());
		itemHandler.setUID();
		modularItem.setTagCompound(itemHandler.serializeNBT());
		return modularItem;
	}

	public static ItemStack saveModularToItem(ItemStack modularItem, IModularHandler modularHandler, EntityPlayer player){
		IModularHandlerItem<NBTTagCompound> itemHandler = (IModularHandlerItem<NBTTagCompound>) modularItem.getCapability(MODULAR_HANDLER_CAPABILITY, null);
		modularItem = modularItem.copy();
		if(modularHandler.isAssembled() && modularHandler.getModular() != null){
			itemHandler.setModular(modularHandler.getModular().copy(itemHandler));
		}else if(!modularHandler.isAssembled() && modularHandler.getAssembler() != null){
			itemHandler.setAssembler(modularHandler.getAssembler().copy(itemHandler));
		}
		itemHandler.setWorld(player.getEntityWorld());
		itemHandler.setOwner(player.getGameProfile());
		itemHandler.setUID();
		modularItem.setTagCompound(itemHandler.serializeNBT());
		return modularItem;
	}
}
