package de.nedelosk.modularmachines.api.modular;

import com.mojang.authlib.GameProfile;

import cofh.api.energy.IEnergyProvider;
import cofh.api.energy.IEnergyReceiver;
import de.nedelosk.modularmachines.api.inventory.IGuiHandler;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.IItemHandlerModifiable;

public interface IModularHandler<M extends IModular, N extends NBTBase> extends IEnergyReceiver, IEnergyProvider, IGuiHandler, ICapabilitySerializable<N> {
	
	World getWorld();

	M getModular();
	
	void markDirty();
	
	void updateClient();

	void updateServer();

	void setModular(M modular);

	GameProfile getOwner();

	void setOwner(GameProfile owner);

}
