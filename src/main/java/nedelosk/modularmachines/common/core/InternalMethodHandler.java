package nedelosk.modularmachines.common.core;

import cpw.mods.fml.common.network.internal.FMLNetworkHandler;
import nedelosk.modularmachines.api.internal.IInternalMethodHandler;
import nedelosk.modularmachines.common.ModularMachines;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public class InternalMethodHandler implements IInternalMethodHandler {

	@Override
	public void openGui(EntityPlayer entityPlayer, int modGuiId, World world, int x, int y, int z) {
		 FMLNetworkHandler.openGui(entityPlayer, ModularMachines.instance, modGuiId, world, x, y, z);
	}

}
