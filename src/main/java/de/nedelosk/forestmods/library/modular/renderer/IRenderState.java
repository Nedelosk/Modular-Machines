package de.nedelosk.forestmods.library.modular.renderer;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import de.nedelosk.forestmods.library.modular.IModular;
import de.nedelosk.forestmods.library.modules.IModule;
import net.minecraft.item.ItemStack;

@SideOnly(Side.CLIENT)
public interface IRenderState<M extends IModular> {

	ItemStack getItem();

	double getX();

	double getY();

	double getZ();
	
	IModule getCurrentModule();
	
	void setCurrentModule(IModule module);

	M getModular();
}
