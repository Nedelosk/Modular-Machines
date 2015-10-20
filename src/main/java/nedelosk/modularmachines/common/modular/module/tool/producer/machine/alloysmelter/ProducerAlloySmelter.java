package nedelosk.modularmachines.common.modular.module.tool.producer.machine.alloysmelter;

import java.util.ArrayList;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import nedelosk.modularmachines.api.modular.machines.basic.IModular;
import nedelosk.modularmachines.api.modular.machines.basic.IModularRenderer;
import nedelosk.modularmachines.api.modular.machines.basic.IModularTileEntity;
import nedelosk.modularmachines.api.modular.machines.basic.ModularMachineRenderer;
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

public class ProducerAlloySmelter extends ProducerMachineRecipe {
	
	public ProducerAlloySmelter(String modifier, int speedModifier) {
		super("AlloySmelter" + modifier, 2, 2, speedModifier);
	}
	
	public ProducerAlloySmelter(NBTTagCompound nbt, IModular modular, ModuleStack stack) {
		super(nbt, modular, stack);
	}

	//Inventory
	@Override
	public ArrayList<Slot> addSlots(IContainerBase container, IModular modular, ModuleStack stack) {
		ArrayList<Slot> list = new ArrayList<Slot>();
		list.add(new SlotModular(modular.getMachine(), 0, 36, 35, stack));
		list.add(new SlotModular(modular.getMachine(), 1, 54, 35, stack));
		list.add(new SlotModular(modular.getMachine(), 2, 116, 35, stack){
			@Override
			public boolean isItemValid(ItemStack stack) {
				return false;
			}
		});
		list.add(new SlotModular(modular.getMachine(), 3, 134, 35, stack){
			@Override
			public boolean isItemValid(ItemStack stack) {
				return false;
			}
		});
		return list;
	}
	
	@Override
	public int getSizeInventory(ModuleStack stack) {
		return 4;
	}
	
	//Gui
	@SideOnly(Side.CLIENT)
	@Override
	public void addWidgets(IGuiBase gui, IModular modular, ModuleStack stack) {
		gui.getWidgetManager().add(new WidgetProgressBar(82, 36, burnTime, burnTimeTotal));
	}
	
	//NEI
	@Override
	public ArrayList<NeiStack> addNEIStacks(ModuleStack stack) {
		ArrayList<NeiStack> list = new ArrayList<NeiStack>();
		list.add(new NeiStack(56, 24, true));
		list.add(new NeiStack(74, 24, true));
		list.add(new NeiStack(116, 24, false));
		list.add(new NeiStack(134, 24, false));
		return list;
	}

	//Recipe
	@Override
	public RecipeInput[] getInputs(IModular modular, ModuleStack stack) {
		return getInputItems(modular, stack);
	}

	@Override
	public String getRecipeName(ModuleStack stack) {
		return "AlloySmelter";
	}
	
	@Override
	public int getSpeedModifier() {
		return 95;
	}
	
	@Override
	public IModularRenderer getMachineRenderer(IModular modular, ModuleStack moduleStack, IModularTileEntity tile) {
		return new ModularMachineRenderer.AlloySmelterRenderer(moduleStack.getModule());
	}

}
