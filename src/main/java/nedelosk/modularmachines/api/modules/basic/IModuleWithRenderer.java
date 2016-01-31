package nedelosk.modularmachines.api.modules.basic;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import nedelosk.modularmachines.api.client.renderer.IModularRenderer;
import nedelosk.modularmachines.api.modular.IModular;
import nedelosk.modularmachines.api.modular.tile.IModularTileEntity;
import nedelosk.modularmachines.api.modules.IModule;
import nedelosk.modularmachines.api.utils.ModuleStack;
import net.minecraft.item.ItemStack;

public interface IModuleWithRenderer extends IModule {

	@SideOnly(Side.CLIENT)
	IModularRenderer getItemRenderer(IModular modular, ModuleStack moduleStack, ItemStack stack);

	@SideOnly(Side.CLIENT)
	IModularRenderer getMachineRenderer(IModular modular, ModuleStack moduleStack, IModularTileEntity tile);
}
