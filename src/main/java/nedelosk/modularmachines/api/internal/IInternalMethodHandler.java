package nedelosk.modularmachines.api.internal;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public interface IInternalMethodHandler {

	void openGui(EntityPlayer entityPlayer, int modGuiId, World world, int x, int y, int z);
}
