package nedelosk.modularmachines.common.modular.module.tool.producer.machine.pulverizer;

import java.util.ArrayList;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import nedelosk.modularmachines.api.modular.machines.basic.IModular;
import nedelosk.modularmachines.api.modular.machines.basic.SlotModular;
import nedelosk.modularmachines.api.modular.utils.ModuleStack;
import nedelosk.modularmachines.api.recipes.NeiStack;
import nedelosk.modularmachines.api.recipes.RecipeInput;
import nedelosk.modularmachines.client.gui.widget.WidgetProgressBar;
import nedelosk.modularmachines.common.modular.module.tool.producer.machine.ProducerMachineRecipe;
import nedelosk.nedeloskcore.api.machines.IContainerBase;
import nedelosk.nedeloskcore.api.machines.IGuiBase;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class ProducerPulverizer extends ProducerMachineRecipe {
	
	public ProducerPulverizer(String modifier, int speedModifier) {
		super("Pulverizer" + modifier, 1, 2, speedModifier);
	}
	
	public ProducerPulverizer(NBTTagCompound nbt, IModular modular, ModuleStack stack) {
		super(nbt, modular, stack);
	}

	@Override
	public ArrayList<Slot> addSlots(IContainerBase container, IModular modular, ModuleStack stack) {
		ArrayList<Slot> list = new ArrayList<Slot>();
		list.add(new SlotModular(modular.getMachine(), 0, 56, 35, stack));
		list.add(new SlotModular(modular.getMachine(), 1, 116, 35, stack){
			@Override
			public boolean isItemValid(ItemStack stack) {
				return false;
			}
		});
		list.add(new SlotModular(modular.getMachine(), 2, 134, 35, stack){
			@Override
			public boolean isItemValid(ItemStack stack) {
				return false;
			}
		});
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
		return "Pulverizer";
	}

	@Override
	public int getSizeInventory(ModuleStack stack) {
		return 3;
	}
	
	@SideOnly(Side.CLIENT)
	@Override
	public void addWidgets(IGuiBase gui, IModular modular, ModuleStack stack) {
		gui.getWidgetManager().add(new WidgetProgressBar(82, 36, burnTime, burnTimeTotal));
	}
	
	@Override
	public int getSpeedModifier() {
		return 105;
	}

}
