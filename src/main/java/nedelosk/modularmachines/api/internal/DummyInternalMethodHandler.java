package nedelosk.modularmachines.api.internal;

import nedelosk.modularmachines.api.modular.material.Materials.Material;
import nedelosk.modularmachines.api.modules.special.IModuleWithItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public class DummyInternalMethodHandler implements IInternalMethodHandler {

	@Override
	public void openGui(EntityPlayer entityPlayer, int modGuiId, World world, int x, int y, int z) {
	}

	@Override
	public void addModuleToModuelItem(IModuleWithItem module, Material material) {
	}
}
