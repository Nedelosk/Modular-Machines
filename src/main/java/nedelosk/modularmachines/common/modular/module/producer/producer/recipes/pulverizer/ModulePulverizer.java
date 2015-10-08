package nedelosk.modularmachines.common.modular.module.producer.producer.recipes.pulverizer;

import java.util.ArrayList;

import nedelosk.modularmachines.api.modular.machines.basic.IModular;
import nedelosk.modularmachines.api.modular.module.basic.IModule;
import nedelosk.modularmachines.api.parts.PartType;
import nedelosk.modularmachines.api.parts.PartType.MachinePartType;
import nedelosk.modularmachines.api.recipes.NeiStack;
import nedelosk.modularmachines.api.recipes.RecipeInput;
import nedelosk.modularmachines.common.core.registry.ItemRegistry;
import nedelosk.modularmachines.common.inventory.slots.SlotModuleMachine;
import nedelosk.modularmachines.common.modular.module.producer.producer.recipes.ModuleProducerRecipe;
import nedelosk.nedeloskcore.api.machines.IContainerBase;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ModulePulverizer extends ModuleProducerRecipe {

	public ModulePulverizer() {
		super("Pulverizer", 1, 2);
	}
	
	public ModulePulverizer(int speedModifier) {
		super("Pulverizer", 1, 2, speedModifier);
	}

	@Override
	public ArrayList<Slot> addSlots(IContainerBase container, IModular modular) {
		ArrayList<Slot> list = new ArrayList<Slot>();
		list.add(new SlotModuleMachine(modular.getMachine(), 0, 56, 35, this.getName()));
		list.add(new SlotModuleMachine(modular.getMachine(), 1, 116, 35, this.getName()){
			@Override
			public boolean isItemValid(ItemStack stack) {
				return false;
			}
		});
		list.add(new SlotModuleMachine(modular.getMachine(), 2, 134, 35, this.getName()){
			@Override
			public boolean isItemValid(ItemStack stack) {
				return false;
			}
		});
		return list;
	}
	
	@Override
	public ArrayList<NeiStack> addNEIStacks() {
		ArrayList<NeiStack> list = new ArrayList<NeiStack>();
		list.add(new NeiStack(56, 24, true));
		list.add(new NeiStack(116, 24, false));
		list.add(new NeiStack(134, 24, false));
		return list;
	}

	@Override
	public RecipeInput[] getInputs(IModular modular) {
		return getInputItems(modular);
	}

	@Override
	public String getRecipeName() {
		return "Pulverizer";
	}

	@Override
	public int getSizeInventory() {
		return 3;
	}

	@Override
	public PartType[] getRequiredComponents() {
		return new PartType[]{new MachinePartType(ItemRegistry.Grinding_Wheel),
				  			  new MachinePartType(ItemRegistry.Module),
				  			new MachinePartType(ItemRegistry.Grinding_Wheel)};
	}
	
	@Override
	public int getColor() {
		return 0x515151;
	}
	
	@Override
	public int getSpeedModifier() {
		return 105;
	}
	
	@Override
	public IModule getModule(int speedModifier) {
		return new ModulePulverizer(speedModifier);
	}

}
