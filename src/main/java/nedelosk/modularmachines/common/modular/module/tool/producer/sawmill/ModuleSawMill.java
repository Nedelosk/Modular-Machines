package nedelosk.modularmachines.common.modular.module.tool.producer.sawmill;

import nedelosk.modularmachines.api.modular.IModular;
import nedelosk.modularmachines.api.modular.module.recipes.RecipeInput;
import nedelosk.modularmachines.common.inventory.slots.SlotModuleMachine;
import nedelosk.modularmachines.common.modular.module.tool.producer.ModuleProducerRecipe;
import nedelosk.nedeloskcore.api.machines.IContainerBase;
import nedelosk.nedeloskcore.api.machines.IGuiBase;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class ModuleSawMill extends ModuleProducerRecipe {

	public ModuleSawMill() {
		super("SawMill", 1, 2);
	}

	@Override
	public void addSlots(IContainerBase container, IModular modular) {
		container.addSlot(new SlotModuleMachine(modular.getMachine(), 0, 56, 35, this.getName()));
		container.addSlot(new SlotModuleMachine(modular.getMachine(), 1, 116, 35, this.getName()){
			@Override
			public boolean isItemValid(ItemStack stack) {
				return false;
			}
		});
		container.addSlot(new SlotModuleMachine(modular.getMachine(), 2, 134, 35, this.getName()){
			@Override
			public boolean isItemValid(ItemStack stack) {
				return false;
			}
		});
	}

	@Override
	public void addButtons(IGuiBase gui, IModular modular) {
		
	}

	@Override
	public void addWidgets(IGuiBase gui, IModular modular) {
		
	}

	@Override
	public RecipeInput[] getInputs(IModular modular) {
		return getInputItems(modular);
	}

	@Override
	public String getRecipeName() {
		return "SawMill";
	}

	@Override
	public int getSpeedModifier() {
		return 20;
	}

}
