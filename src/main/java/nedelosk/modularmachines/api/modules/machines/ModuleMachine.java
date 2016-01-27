package nedelosk.modularmachines.api.modules.machines;

import java.util.ArrayList;
import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import nedelosk.modularmachines.api.client.renderer.IModularRenderer;
import nedelosk.modularmachines.api.client.renderer.ModularMachineRenderer;
import nedelosk.modularmachines.api.modular.IModular;
import nedelosk.modularmachines.api.modular.integration.IWailaData;
import nedelosk.modularmachines.api.modular.tile.IModularTileEntity;
import nedelosk.modularmachines.api.modules.IModule;
import nedelosk.modularmachines.api.modules.IModuleGui;
import nedelosk.modularmachines.api.modules.Module;
import nedelosk.modularmachines.api.modules.ModuleDefaultGui;
import nedelosk.modularmachines.api.modules.fluids.IModuleWithFluid;
import nedelosk.modularmachines.api.modules.special.IProducerController;
import nedelosk.modularmachines.api.utils.ModuleRegistry;
import nedelosk.modularmachines.api.utils.ModuleStack;
import net.minecraft.item.ItemStack;

public abstract class ModuleMachine<S extends IModuleMachineSaver> extends Module<S> implements IModuleMachine<S>, IModuleWithFluid<S> {

	public ModuleMachine(String modifier) {
		super(modifier);
	}

	@SideOnly(Side.CLIENT)
	@Override
	public IModularRenderer getMachineRenderer(IModular modular, ModuleStack moduleStack, IModularTileEntity tile) {
		return new ModularMachineRenderer.MachineRenderer(moduleStack);
	}

	@SideOnly(Side.CLIENT)
	@Override
	public IModularRenderer getItemRenderer(IModular modular, ModuleStack moduleStack, ItemStack stack) {
		return new ModularMachineRenderer.MachineRenderer(moduleStack);
	}

	@SideOnly(Side.CLIENT)
	@Override
	public String getFilePath(ModuleStack stack) {
		return getModifier(stack);
	}

	@SideOnly(Side.CLIENT)
	@Override
	public IModuleGui getGui(ModuleStack stack) {
		return new ModuleDefaultGui(getCategoryUID(), getName(stack));
	}

	/* INVENTORY */
	@Override
	public boolean useFluids(ModuleStack stack) {
		return false;
	}

	@Override
	public int getFluidInputs(ModuleStack stack) {
		return 0;
	}

	@Override
	public int getFluidOutputs(ModuleStack stack) {
		return 0;
	}

	@Override
	public List<String> getRequiredModules() {
		ArrayList<String> modules = new ArrayList();
		modules.add("Battery");
		modules.add("Engine");
		modules.add("Casing");
		return modules;
	}

	@Override
	public boolean buildMachine(IModular modular, ItemStack[] stacks, ModuleStack<IProducerController> moduleStack) {
		ArrayList<ModuleStack> modules = new ArrayList();
		modules.add(moduleStack);
		for ( int i = 1; i < stacks.length; i++ ) {
			ItemStack stack = stacks[i];
			if (stack != null) {
				if (ModuleRegistry.getModuleFromItem(stack) != null && ModuleRegistry.getModuleFromItem(stack).moduleStack.getModule() != null) {
					modules.add(ModuleRegistry.getModuleFromItem(stack).moduleStack);
				}
			}
		}
		for ( ModuleStack<IModule> manager : modules ) {
			if (manager != null) {
				if (!modular.addModule(manager)) {
					return false;
				}
			}
		}
		return true;
	}

	/* WAILA */
	@Override
	public List<String> getWailaBody(ItemStack itemStack, List<String> currenttip, IWailaData data) {
		return currenttip;
	}

	@Override
	public List<String> getWailaHead(ItemStack itemStack, List<String> currenttip, IWailaData data) {
		return currenttip;
	}

	@Override
	public List<String> getWailaTail(ItemStack itemStack, List<String> currenttip, IWailaData data) {
		return currenttip;
	}
}