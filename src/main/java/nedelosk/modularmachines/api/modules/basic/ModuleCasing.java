package nedelosk.modularmachines.api.modules.basic;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import nedelosk.modularmachines.api.client.renderer.IModularRenderer;
import nedelosk.modularmachines.api.client.renderer.ModularMachineRenderer;
import nedelosk.modularmachines.api.modular.IModular;
import nedelosk.modularmachines.api.modular.tile.IModularTileEntity;
import nedelosk.modularmachines.api.modules.Module;
import nedelosk.modularmachines.api.utils.ModuleCategoryUIDs;
import nedelosk.modularmachines.api.utils.ModuleStack;
import net.minecraft.item.ItemStack;

public class ModuleCasing extends Module implements IModuleCasing {

	public ModuleCasing(String moduleUID) {
		super(ModuleCategoryUIDs.CASING, moduleUID);
	}

	@SideOnly(Side.CLIENT)
	@Override
	public IModularRenderer getItemRenderer(IModular modular, ModuleStack moduleStack, ItemStack stack) {
		return new ModularMachineRenderer.CasingRenderer(moduleStack);
	}

	@SideOnly(Side.CLIENT)
	@Override
	public IModularRenderer getMachineRenderer(IModular modular, ModuleStack moduleStack, IModularTileEntity tile) {
		return new ModularMachineRenderer.CasingRenderer(moduleStack);
	}

	@Override
	public int getMaxHeat() {
		return 0;
	}

	@Override
	public int getResistance() {
		return 0;
	}
}
