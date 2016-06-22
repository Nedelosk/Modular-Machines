package de.nedelosk.modularmachines.api.modular;

import com.mojang.authlib.GameProfile;

import cofh.api.energy.IEnergyProvider;
import cofh.api.energy.IEnergyReceiver;
import de.nedelosk.modularmachines.api.inventory.IGuiHandler;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fluids.IFluidHandler;

public interface IModularTileEntity<M extends IModular> extends ISidedInventory, IFluidHandler, IEnergyReceiver, IEnergyProvider, IGuiHandler {

	World getWorld();
	
	BlockPos getPos();

	M getModular();

	void setModular(M modular);

	EnumFacing getFacing();

	void setFacing(EnumFacing facing);

	GameProfile getOwner();

	void setOwner(GameProfile owner);

	void readFromNBT(NBTTagCompound nbt);

	void writeToNBT(NBTTagCompound nbt);
}
