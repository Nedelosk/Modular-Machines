package de.nedelosk.forestmods.api.modular.tile;

import com.mojang.authlib.GameProfile;

import cofh.api.energy.IEnergyHandler;
import de.nedelosk.forestcore.inventory.IGuiHandler;
import de.nedelosk.forestmods.api.modular.IModular;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.fluids.IFluidHandler;

public interface IModularTileEntity<M extends IModular> extends ISidedInventory, IFluidHandler, IEnergyHandler, IGuiHandler {

	World getWorldObj();

	int getXCoord();

	int getYCoord();

	int getZCoord();

	M getModular();

	void setModular(M modular);

	short getFacing();

	GameProfile getOwner();

	void setFacing(short facing);

	void setOwner(GameProfile owner);

	void assembleModular();

	void readFromNBT(NBTTagCompound nbt);

	void writeToNBT(NBTTagCompound nbt);
}
