package nedelosk.modularmachines.common.modular.module.tool.producer.boiler;

import java.util.ArrayList;

import nedelosk.modularmachines.api.modular.machines.basic.IModular;
import nedelosk.modularmachines.api.modular.utils.ModuleStack;
import nedelosk.modularmachines.api.recipes.NeiStack;
import nedelosk.modularmachines.api.recipes.RecipeInput;
import nedelosk.nedeloskcore.api.machines.IContainerBase;
import net.minecraft.inventory.Slot;
import net.minecraft.nbt.NBTTagCompound;

public class ProducerBurningBoiler extends ProducerBoiler {

	public ProducerBurningBoiler(NBTTagCompound nbt, IModular modular, ModuleStack stack) {
		super(nbt, modular, stack);
	}
	
	public ProducerBurningBoiler(String modifier, int inputs, int outputs) {
		super(modifier, inputs, outputs);
	}
	
	public ProducerBurningBoiler(String modifier, int inputs, int outputs, int speed) {
		super(modifier, inputs, outputs, speed);
	}

	@Override
	public String getRecipeName(ModuleStack stack) {
		return "BoilerBurning";
	}

	@Override
	public RecipeInput[] getInputs(IModular modular, ModuleStack stack) {
		return null;
	}

	@Override
	public ArrayList<NeiStack> addNEIStacks(ModuleStack stack) {
		ArrayList<NeiStack> slots = new ArrayList();
		return slots;
	}

	@Override
	public ArrayList<Slot> addSlots(IContainerBase container, IModular modular, ModuleStack stack) {
		ArrayList<Slot> slots = new ArrayList();
		return slots;
	}

	@Override
	public int getSizeInventory(ModuleStack stack) {
		return 5;
	}

	@Override
	public int getSpeedModifier() {
		return 15;
	}
	
	@Override
	public ArrayList<String> getRequiredModules() {
		ArrayList<String> modules = new ArrayList<>();
		modules.add("TankManager");
		return modules;
	}
	
}
