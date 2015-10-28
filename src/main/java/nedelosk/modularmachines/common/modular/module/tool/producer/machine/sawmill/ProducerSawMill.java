package nedelosk.modularmachines.common.modular.module.tool.producer.machine.sawmill;

import java.util.ArrayList;

import nedelosk.forestday.api.guis.IContainerBase;
import nedelosk.modularmachines.api.modular.machines.basic.IModular;
import nedelosk.modularmachines.api.modular.machines.basic.SlotModular;
import nedelosk.modularmachines.api.modular.utils.ModuleStack;
import nedelosk.modularmachines.api.recipes.NeiStack;
import nedelosk.modularmachines.api.recipes.RecipeInput;
import nedelosk.modularmachines.common.modular.module.tool.producer.machine.ProducerMachineRecipe;
import net.minecraft.inventory.Slot;
import net.minecraft.nbt.NBTTagCompound;

public class ProducerSawMill extends ProducerMachineRecipe {

	public ProducerSawMill(String modifier, int speedModifier) {
		super("SawMill" + modifier, 1, 2, speedModifier);
	}

	public ProducerSawMill() {
		super("SawMill", 1, 2, 60);
	}

	public ProducerSawMill(NBTTagCompound nbt, IModular modular, ModuleStack stack) {
		super(nbt, modular, stack);
	}

	@Override
	public ArrayList<Slot> addSlots(IContainerBase container, IModular modular, ModuleStack stack) {
		ArrayList<Slot> list = new ArrayList<Slot>();
		list.add(new SlotModular(modular.getMachine(), 0, 56, 35, stack));
		list.add(new SlotModular(modular.getMachine(), 1, 116, 35, stack));
		list.add(new SlotModular(modular.getMachine(), 2, 134, 35, stack));
		return list;
	}

	@Override
	public ArrayList<NeiStack> addNEIStacks(ModuleStack stack) {
		ArrayList<NeiStack> list = new ArrayList<NeiStack>();
		list.add(new NeiStack(56, 24, true));
		list.add(new NeiStack(116, 24, false));
		list.add(new NeiStack(134, 24, false));
		return list;
	}

	@Override
	public RecipeInput[] getInputs(IModular modular, ModuleStack stack) {
		return getInputItems(modular, stack);
	}

	@Override
	public String getRecipeName(ModuleStack stack) {
		return "SawMill";
	}

	@Override
	public int getSizeInventory(ModuleStack stack) {
		return 3;
	}

	@Override
	public int getSpeedModifier() {
		return 85;
	}

	@Override
	public int getColor() {
		return 0xA65005;
	}

}
