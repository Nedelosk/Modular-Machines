package nedelosk.modularmachines.common.producers.machines.assembler;

import java.util.ArrayList;
import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import nedelosk.forestcore.library.gui.IGuiBase;
import nedelosk.forestcore.library.gui.Widget;
import nedelosk.forestcore.library.gui.WidgetProgressBar;
import nedelosk.forestcore.library.inventory.IContainerBase;
import nedelosk.modularmachines.api.modular.IModular;
import nedelosk.modularmachines.api.modular.inventory.SlotModular;
import nedelosk.modularmachines.api.modular.inventory.SlotModularOutput;
import nedelosk.modularmachines.api.modules.IModule;
import nedelosk.modularmachines.api.producers.engine.IProducerEngine;
import nedelosk.modularmachines.api.producers.machines.recipe.ProducerMachineRecipe;
import nedelosk.modularmachines.api.recipes.IRecipe;
import nedelosk.modularmachines.api.recipes.NeiStack;
import nedelosk.modularmachines.api.recipes.RecipeInput;
import nedelosk.modularmachines.api.utils.ModuleStack;
import nedelosk.modularmachines.api.utils.ModuleUtils;
import net.minecraft.inventory.Slot;
import net.minecraft.nbt.NBTTagCompound;

public class ProducerModuleAssembler extends ProducerMachineRecipe {

	public ProducerModuleAssembler() {
		this(75);
	}

	public ProducerModuleAssembler(int speedModifier) {
		super("AssemblerModule", 9, 2, speedModifier);
	}

	public ProducerModuleAssembler(NBTTagCompound nbt, IModular modular, ModuleStack stack) {
		super(nbt, modular, stack);
	}

	// Inventory
	@Override
	public List<Slot> addSlots(IContainerBase container, IModular modular, ModuleStack stack) {
		ArrayList<Slot> list = new ArrayList<Slot>();
		list.add(new SlotModular(modular.getMachine(), 0, 17, 16, stack));
		list.add(new SlotModular(modular.getMachine(), 1, 35, 16, stack));
		list.add(new SlotModular(modular.getMachine(), 2, 53, 16, stack));
		list.add(new SlotModular(modular.getMachine(), 3, 17, 34, stack));
		list.add(new SlotModular(modular.getMachine(), 4, 35, 34, stack));
		list.add(new SlotModular(modular.getMachine(), 5, 53, 34, stack));
		list.add(new SlotModular(modular.getMachine(), 6, 17, 52, stack));
		list.add(new SlotModular(modular.getMachine(), 7, 35, 52, stack));
		list.add(new SlotModular(modular.getMachine(), 8, 53, 52, stack));
		list.add(new SlotModularOutput(modular.getMachine(), 9, 125, 34, stack));
		list.add(new SlotModularOutput(modular.getMachine(), 10, 143, 34, stack));
		return list;
	}

	// Gui
	@SideOnly(Side.CLIENT)
	@Override
	public void addWidgets(IGuiBase gui, IModular modular, ModuleStack stack) {
		ModuleStack<IModule, IProducerEngine> engine = ModuleUtils.getModuleStackEngine(modular);
		int burnTime = 0;
		int burnTimeTotal = 0;
		if (engine != null) {
			burnTime = engine.getProducer().getBurnTime(engine);
			burnTimeTotal = engine.getProducer().getBurnTimeTotal(engine);
		}
		gui.getWidgetManager().add(new WidgetProgressBar(82, 46, burnTime, burnTimeTotal));
	}

	@SideOnly(Side.CLIENT)
	@Override
	public List<Widget> addNEIWidgets(IGuiBase gui, ModuleStack stack, IRecipe recipe) {
		gui.getWidgetManager().add(new WidgetProgressBar(87, 35, 0, 0));
		return gui.getWidgetManager().getWidgets();
	}

	// NEI
	@SideOnly(Side.CLIENT)
	@Override
	public List<NeiStack> addNEIStacks(ModuleStack stack, IRecipe recipe) {
		ArrayList<NeiStack> list = new ArrayList<NeiStack>();
		list.add(new NeiStack(17, 16, true));
		list.add(new NeiStack(35, 16, true));
		list.add(new NeiStack(53, 16, true));
		list.add(new NeiStack(17, 34, true));
		list.add(new NeiStack(35, 34, true));
		list.add(new NeiStack(53, 34, true));
		list.add(new NeiStack(17, 52, true));
		list.add(new NeiStack(35, 52, true));
		list.add(new NeiStack(53, 52, true));
		list.add(new NeiStack(125, 34, false));
		list.add(new NeiStack(143, 34, false));
		return list;
	}

	// Recipe
	@Override
	public RecipeInput[] getInputs(IModular modular, ModuleStack stack) {
		return getInputItems(modular, stack);
	}

	@Override
	public String getRecipeName(ModuleStack stack) {
		return "AssemblerModule";
	}

	@Override
	public int getColor() {
		return 0x601C93;
	}
}
