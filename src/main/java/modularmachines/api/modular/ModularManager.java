package modularmachines.api.modular;

import com.google.common.collect.ImmutableMap;

import modularmachines.api.modular.handlers.IModularHandler;
import modularmachines.api.modular.handlers.IModularHandlerItem;
import modularmachines.api.modules.position.IStoragePosition;
import modularmachines.api.modules.position.StoragePositions;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;

public class ModularManager {

	@CapabilityInject(IModularHandler.class)
	public static Capability<IModularHandler> MODULAR_HANDLER_CAPABILITY;
	public static final StoragePositions<EnumFacing> DEFAULT_STORAGE_POSITIONS;
	/* A helper to create modulars, modular assemblers and modular handlers */
	public static IModularHelper helper;
	static {
		ImmutableMap.Builder<EnumFacing, IStoragePosition> builder = new ImmutableMap.Builder<>();
		builder.put(EnumFacing.NORTH, ExpandedStoragePositions.CASING);
		builder.put(EnumFacing.SOUTH, ExpandedStoragePositions.BACK);
		builder.put(EnumFacing.EAST, ExpandedStoragePositions.LEFT);
		builder.put(EnumFacing.WEST, ExpandedStoragePositions.RIGHT);
		builder.put(EnumFacing.UP, ExpandedStoragePositions.TOP);
		DEFAULT_STORAGE_POSITIONS = new StoragePositions(builder.build());
	}

	/**
	 * Write a modular to a item stack.
	 */
	public static ItemStack saveModularToItem(ItemStack modularItem, IModular modular, EntityPlayer player) {
		IModularHandlerItem<NBTTagCompound, Object> itemHandler = (IModularHandlerItem<NBTTagCompound, Object>) modularItem.getCapability(MODULAR_HANDLER_CAPABILITY, null);
		modularItem = modularItem.copy();
		itemHandler.setModular(modular);
		itemHandler.setWorld(player.getEntityWorld());
		itemHandler.setOwner(player.getGameProfile());
		itemHandler.setUID();
		modularItem.setTagCompound(itemHandler.serializeNBT());
		return modularItem;
	}

	public static ItemStack saveModularToItem(ItemStack modularItem, IModularHandler modularHandler, EntityPlayer player) {
		IModularHandlerItem<NBTTagCompound, Object> itemHandler = (IModularHandlerItem<NBTTagCompound, Object>) modularItem.getCapability(MODULAR_HANDLER_CAPABILITY, null);
		modularItem = modularItem.copy();
		if (modularHandler.isAssembled() && modularHandler.getModular() != null) {
			itemHandler.setModular(modularHandler.getModular().copy(itemHandler));
		} else if (!modularHandler.isAssembled() && modularHandler.getAssembler() != null) {
			itemHandler.setAssembler(modularHandler.getAssembler().copy(itemHandler));
		}
		itemHandler.setWorld(player.getEntityWorld());
		itemHandler.setOwner(player.getGameProfile());
		itemHandler.setUID();
		modularItem.setTagCompound(itemHandler.serializeNBT());
		return modularItem;
	}
}
