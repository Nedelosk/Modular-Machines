package nedelosk.modularmachines.api.producers.machines;

import java.util.ArrayList;
import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import nedelosk.forestcore.library.gui.IGuiBase;
import nedelosk.forestcore.library.gui.Widget;
import nedelosk.modularmachines.api.client.renderer.IModularRenderer;
import nedelosk.modularmachines.api.client.renderer.ModularMachineRenderer;
import nedelosk.modularmachines.api.modular.IModular;
import nedelosk.modularmachines.api.modular.integration.IWailaData;
import nedelosk.modularmachines.api.modular.tile.IModularTileEntity;
import nedelosk.modularmachines.api.modules.IModule;
import nedelosk.modularmachines.api.producers.IProducer;
import nedelosk.modularmachines.api.producers.fluids.IProducerWithFluid;
import nedelosk.modularmachines.api.producers.inventory.ProducerInventory;
import nedelosk.modularmachines.api.producers.special.IProducerController;
import nedelosk.modularmachines.api.recipes.IRecipe;
import nedelosk.modularmachines.api.recipes.NeiStack;
import nedelosk.modularmachines.api.utils.ModuleRegistry;
import nedelosk.modularmachines.api.utils.ModuleStack;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public abstract class ProducerMachine extends ProducerInventory implements IProducerMachine, IProducerWithFluid {

	protected int timer, timerTotal;

	public ProducerMachine(String modifier) {
		super(modifier);
	}

	public ProducerMachine(NBTTagCompound nbt, IModular modular, ModuleStack stack) {
		super(nbt, modular, stack);
	}

	/* NBT */
	@Override
	public void writeToNBT(NBTTagCompound nbt, IModular modular, ModuleStack stack) throws Exception {
		super.writeToNBT(nbt, modular, stack);
		nbt.setInteger("timer", timer);
		nbt.setInteger("timerTotal", timerTotal);
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt, IModular modular, ModuleStack stack) throws Exception {
		super.readFromNBT(nbt, modular, stack);
		timer = nbt.getInteger("timer");
		timerTotal = nbt.getInteger("timerTotal");
	}

	/* GUI */
	@SideOnly(Side.CLIENT)
	@Override
	public void addWidgets(IGuiBase gui, IModular modular, ModuleStack stack) {
	}

	@SideOnly(Side.CLIENT)
	@Override
	public List<Widget> addNEIWidgets(IGuiBase gui, ModuleStack stack, IRecipe recipe) {
		return new ArrayList();
	}

	@SideOnly(Side.CLIENT)
	@Override
	public List<NeiStack> addNEIStacks(ModuleStack stack, IRecipe recipe) {
		return null;
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

	/* INVENTORY */
	@Override
	public boolean hasCustomInventoryName(ModuleStack stack) {
		return true;
	}

	@Override
	public boolean useFluids(ModuleStack stack) {
		return false;
	}

	@Override
	public int getFluidInputs(ModuleStack<IModule, IProducer> stack) {
		return 0;
	}

	@Override
	public int getFluidOutputs(ModuleStack<IModule, IProducer> stack) {
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
	public boolean buildMachine(IModular modular, ItemStack[] stacks, ModuleStack<IModule, IProducerController> moduleStack) {
		ArrayList<ModuleStack> modules = new ArrayList();
		modules.add(moduleStack);
		for ( int i = 1; i < stacks.length; i++ ) {
			ItemStack stack = stacks[i];
			if (stack != null) {
				if (ModuleRegistry.getProducer(stack) != null && ModuleRegistry.getProducer(stack).getModule() != null) {
					modules.add(ModuleRegistry.getProducer(stack));
				}
			}
		}
		for ( ModuleStack<IModule, IProducer> manager : modules ) {
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