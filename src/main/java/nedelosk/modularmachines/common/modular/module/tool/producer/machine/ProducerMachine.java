package nedelosk.modularmachines.common.modular.module.tool.producer.machine;

import java.util.ArrayList;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import nedelosk.forestday.api.guis.IGuiBase;
import nedelosk.modularmachines.api.modular.machines.basic.IModular;
import nedelosk.modularmachines.api.modular.machines.basic.IModularRenderer;
import nedelosk.modularmachines.api.modular.machines.basic.IModularTileEntity;
import nedelosk.modularmachines.api.modular.machines.basic.ModularMachineRenderer;
import nedelosk.modularmachines.api.modular.module.basic.IModule;
import nedelosk.modularmachines.api.modular.module.tool.producer.IProducer;
import nedelosk.modularmachines.api.modular.module.tool.producer.basic.IProducerController;
import nedelosk.modularmachines.api.modular.module.tool.producer.inventory.ProducerInventory;
import nedelosk.modularmachines.api.modular.module.tool.producer.machine.IProducerMachine;
import nedelosk.modularmachines.api.modular.utils.ModuleRegistry;
import nedelosk.modularmachines.api.modular.utils.ModuleStack;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public abstract class ProducerMachine extends ProducerInventory implements IProducerMachine {

	protected int timer, timerTotal;

	public ProducerMachine(String modifier) {
		super(modifier);
	}

	public ProducerMachine(NBTTagCompound nbt, IModular modular, ModuleStack stack) {
		super(nbt, modular, stack);
	}

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

	@Override
	public boolean hasCustomInventoryName(ModuleStack stack) {
		return true;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void addWidgets(IGuiBase gui, IModular modular, ModuleStack stack) {

	}

	@SideOnly(Side.CLIENT)
	@Override
	public IModularRenderer getMachineRenderer(IModular modular, ModuleStack moduleStack, IModularTileEntity tile) {
		return new ModularMachineRenderer.MachineRenderer(moduleStack.getModule());
	}

	@SideOnly(Side.CLIENT)
	@Override
	public IModularRenderer getItemRenderer(IModular modular, ModuleStack moduleStack, ItemStack stack) {
		return new ModularMachineRenderer.MachineRenderer(moduleStack.getModule());
	}
	
	@Override
	public ArrayList<String> getRequiredModules() {
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
		for(int i = 1;i < stacks.length;i++){
			ItemStack stack = stacks[i];
			if(stack != null)
				if(ModuleRegistry.getModuleItem(stack) != null && ModuleRegistry.getModuleItem(stack).getModule() != null)
					modules.add(ModuleRegistry.getModuleItem(stack));
		}
		for (ModuleStack<IModule, IProducer> manager : modules)
			if (manager != null)
				if (!modular.addModule(manager))
					return false;
		return true;
	}

}