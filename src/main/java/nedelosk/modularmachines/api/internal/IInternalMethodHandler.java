package nedelosk.modularmachines.api.internal;

import nedelosk.modularmachines.api.modular.material.Materials.Material;
import nedelosk.modularmachines.api.modules.special.IModuleWithItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public interface IInternalMethodHandler {

	void openGui(EntityPlayer entityPlayer, int modGuiId, World world, int x, int y, int z);

	/**
	 * Add a module to the module item
	 */
	void addModuleToModuelItem(IModuleWithItem module, Material material);
}
