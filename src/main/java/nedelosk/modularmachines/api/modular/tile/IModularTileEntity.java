package nedelosk.modularmachines.api.modular.tile;

import com.mojang.authlib.GameProfile;

import cofh.api.energy.IEnergyHandler;
import nedelosk.forestcore.library.INBTTagable;
import nedelosk.forestcore.library.inventory.IGuiHandler;
import nedelosk.modularmachines.api.modular.IModular;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.world.World;
import net.minecraftforge.fluids.IFluidHandler;

public interface IModularTileEntity<M extends IModular> extends ISidedInventory, INBTTagable, IFluidHandler, IEnergyHandler, IGuiHandler {

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
