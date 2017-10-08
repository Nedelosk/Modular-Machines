/*
 * Copyright (c) 2017 Nedelosk
 *
 * This work (the MOD) is licensed under the "MIT" License, see LICENSE for details.
 */
package modularmachines.common.items.blocks;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;

import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;

import modularmachines.api.modules.ModuleRegistry;
import modularmachines.api.modules.logic.IModuleLogic;
import modularmachines.api.modules.storages.EnumStoragePosition;
import modularmachines.common.modules.logic.ModuleLogic;

public class ItemMachineCapabilityProvider implements ICapabilitySerializable<NBTTagCompound> {
	protected final ItemStack parent;
	protected final IModuleLogic logic;
	
	public ItemMachineCapabilityProvider(ItemStack parent) {
		this.parent = parent;
		this.logic = new ModuleLogic(null, EnumStoragePosition.getValidPositions());
	}
	
	@Override
	public boolean hasCapability(@Nonnull Capability<?> capability, @Nullable EnumFacing facing) {
		return capability == ModuleRegistry.MODULE_LOGIC;
	}
	
	@Nullable
	@Override
	public <T> T getCapability(@Nonnull Capability<T> capability, @Nullable EnumFacing facing) {
		if(capability == ModuleRegistry.MODULE_LOGIC){
			return ModuleRegistry.MODULE_LOGIC.cast(logic);
		}
		return null;
	}
	
	@Override
	public NBTTagCompound serializeNBT() {
		return logic.writeToNBT(new NBTTagCompound());
	}
	
	@Override
	public void deserializeNBT(NBTTagCompound nbt) {
		logic.readFromNBT(nbt);
	}
}
