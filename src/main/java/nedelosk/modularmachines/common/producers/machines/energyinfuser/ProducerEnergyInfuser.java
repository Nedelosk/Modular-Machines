package nedelosk.modularmachines.common.producers.machines.energyinfuser;

import java.util.ArrayList;
import java.util.List;

import crazypants.enderio.machine.recipe.RecipeInput;
import nedelosk.forestcore.library.inventory.IContainerBase;
import nedelosk.modularmachines.api.inventory.slots.SlotModular;
import nedelosk.modularmachines.api.inventory.slots.SlotModularOutput;
import nedelosk.modularmachines.api.modular.IModular;
import nedelosk.modularmachines.api.modules.machines.recipe.ModuleMachineRecipe;
import nedelosk.modularmachines.api.recipes.IRecipe;
import nedelosk.modularmachines.api.recipes.NeiStack;
import nedelosk.modularmachines.api.utils.ModuleStack;
import net.minecraft.inventory.Slot;
import net.minecraft.nbt.NBTTagCompound;

public class ProducerEnergyInfuser extends ModuleMachineRecipe {

	public ProducerEnergyInfuser() {
		this(180);
	}

	public ProducerEnergyInfuser(int speedModifier) {
		super("EnergyInfuser", 1, 1, speedModifier);
	}

	public ProducerEnergyInfuser(NBTTagCompound nbt, IModular modular, ModuleStack stack) {
		super(nbt, modular, stack);
	}

	@Override
	public List<Slot> addSlots(IContainerBase container, IModular modular, ModuleStack stack) {
		ArrayList<Slot> list = new ArrayList<Slot>();
		list.add(new SlotModular(modular.getMachine(), 0, 56, 35, stack));
		list.add(new SlotModular(modular.getMachine(), 1, 74, 35, stack));
		list.add(new SlotModularOutput(modular.getMachine(), 2, 116, 35, stack));
		list.add(new SlotModularOutput(modular.getMachine(), 3, 134, 35, stack));
		return list;
	}

	@Override
	public List<NeiStack> addNEIStacks(ModuleStack stack, IRecipe recipe) {
		ArrayList<NeiStack> list = new ArrayList<NeiStack>();
		list.add(new NeiStack(56, 24, true));
		list.add(new NeiStack(74, 24, true));
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
		return "EnergyInfuser";
	}

	@Override
	public int getColor() {
		return 0xABA8A8;
	}
}
