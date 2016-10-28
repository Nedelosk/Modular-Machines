package de.nedelosk.modularmachines.api.modular.handlers;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.mojang.authlib.GameProfile;

import de.nedelosk.modularmachines.api.gui.IGuiProvider;
import de.nedelosk.modularmachines.api.modular.IModular;
import de.nedelosk.modularmachines.api.modular.IModularAssembler;
import de.nedelosk.modularmachines.api.modules.position.StoragePositions;
import net.minecraft.nbt.NBTBase;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;

public interface IModularHandler<N extends NBTBase, K> extends IGuiProvider, ICapabilitySerializable<N> {

	void setWorld(World world);

	World getWorld();

	void setModular(@Nullable IModular modular);

	@Nullable
	IModular getModular();

	void setAssembler(@Nullable IModularAssembler assembler);

	@Nullable
	IModularAssembler getAssembler();

	void markDirty();

	void updateClient();

	void updateServer();

	GameProfile getOwner();

	void setOwner(GameProfile owner);

	boolean isAssembled();

	@Nonnull
	StoragePositions<K> getPositions();
}
