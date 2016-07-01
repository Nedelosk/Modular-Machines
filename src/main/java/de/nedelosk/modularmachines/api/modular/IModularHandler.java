package de.nedelosk.modularmachines.api.modular;

import com.mojang.authlib.GameProfile;

import cofh.api.energy.IEnergyProvider;
import cofh.api.energy.IEnergyReceiver;
import de.nedelosk.modularmachines.api.inventory.IGuiHandler;
import net.minecraft.nbt.NBTBase;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;

public interface IModularHandler<M extends IModular, N extends NBTBase> extends IEnergyReceiver, IEnergyProvider, IGuiHandler, ICapabilitySerializable<N> {

	void setWorld(World world);

	World getWorld();

	M getModular();

	void markDirty();

	void updateClient();

	void updateServer();

	void setModular(M modular);

	GameProfile getOwner();

	void setOwner(GameProfile owner);

}
