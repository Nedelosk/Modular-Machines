package de.nedelosk.modularmachines.api.modular.handlers;

import javax.annotation.Nullable;

import com.mojang.authlib.GameProfile;

import de.nedelosk.modularmachines.api.gui.IGuiHandler;
import de.nedelosk.modularmachines.api.modular.IModular;
import de.nedelosk.modularmachines.api.modular.IModularAssembler;
import net.minecraft.nbt.NBTBase;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;

public interface IModularHandler<M extends IModular, A extends IModularAssembler, N extends NBTBase> extends IGuiHandler, ICapabilitySerializable<N> {

	void setWorld(World world);

	World getWorld();

	void setModular(@Nullable M modular);

	@Nullable
	M getModular();

	void setAssembler(@Nullable A assembler);

	@Nullable
	A getAssembler();

	void markDirty();

	void updateClient();

	void updateServer();

	GameProfile getOwner();

	void setOwner(GameProfile owner);

	void setAssembled(boolean isAssembled);

	boolean isAssembled();

}
