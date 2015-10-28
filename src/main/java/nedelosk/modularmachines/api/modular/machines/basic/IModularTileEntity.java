package nedelosk.modularmachines.api.modular.machines.basic;

import com.mojang.authlib.GameProfile;

import cofh.api.energy.IEnergyHandler;
import nedelosk.forestday.api.INBTTagable;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.world.World;
import net.minecraftforge.fluids.IFluidHandler;

public interface IModularTileEntity<M extends IModular>
		extends ISidedInventory, INBTTagable, IFluidHandler, IEnergyHandler {

	World getWorldObj();

	int getXCoord();

	int getYCoord();

	int getZCoord();

	M getModular();

	short getFacing();

	GameProfile getOwner();

	void setFacing(short facing);

	void setOwner(GameProfile owner);

}
