package de.nedelosk.forestmods.api.modular;

import com.mojang.authlib.GameProfile;

import cofh.api.energy.IEnergyProvider;
import cofh.api.energy.IEnergyReceiver;
import de.nedelosk.forestcore.inventory.IGuiHandler;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.IFluidHandler;

public interface IModularTileEntity<M extends IModular> extends ISidedInventory, IFluidHandler, IEnergyReceiver, IEnergyProvider, IGuiHandler {

	World getWorld();

	int getXCoord();

	int getYCoord();

	int getZCoord();

	M getModular();

	void setModular(M modular);

	ForgeDirection getFacing();

	void setFacing(ForgeDirection facing);

	GameProfile getOwner();

	void setOwner(GameProfile owner);

	void readFromNBT(NBTTagCompound nbt);

	void writeToNBT(NBTTagCompound nbt);

	void assembleModular();
}
