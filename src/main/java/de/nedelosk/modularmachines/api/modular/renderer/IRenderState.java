package de.nedelosk.modularmachines.api.modular.renderer;

import de.nedelosk.modularmachines.api.modular.IModular;
import de.nedelosk.modularmachines.api.modules.IModule;
import de.nedelosk.modularmachines.api.modules.state.IModuleState;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public interface IRenderState<M extends IModular> {

	ItemStack getItem();

	double getX();

	double getY();

	double getZ();

	IModuleState getModuleState();

	void setCurrentModule(IModule module);

	M getModular();
}
