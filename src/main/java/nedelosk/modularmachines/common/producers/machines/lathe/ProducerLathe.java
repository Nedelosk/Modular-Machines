package nedelosk.modularmachines.common.producers.machines.lathe;

import java.util.ArrayList;
import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import nedelosk.forestday.api.guis.IContainerBase;
import nedelosk.forestday.api.guis.IGuiBase;
import nedelosk.forestday.api.guis.Widget;
import nedelosk.forestday.api.guis.WidgetProgressBar;
import nedelosk.modularmachines.api.client.widget.WidgetButtonMode;
import nedelosk.modularmachines.api.modular.IModular;
import nedelosk.modularmachines.api.modular.inventory.SlotModular;
import nedelosk.modularmachines.api.modular.inventory.SlotModularOutput;
import nedelosk.modularmachines.api.modules.IModule;
import nedelosk.modularmachines.api.producers.engine.IProducerEngine;
import nedelosk.modularmachines.api.producers.machines.recipe.ProducerMachineRecipeMode;
import nedelosk.modularmachines.api.producers.machines.recipes.RecipeLathe.LatheModes;
import nedelosk.modularmachines.api.recipes.IMachineMode;
import nedelosk.modularmachines.api.recipes.IRecipe;
import nedelosk.modularmachines.api.recipes.NeiStack;
import nedelosk.modularmachines.api.recipes.RecipeInput;
import nedelosk.modularmachines.api.utils.ModuleStack;
import nedelosk.modularmachines.api.utils.ModuleUtils;
import net.minecraft.inventory.Slot;
import net.minecraft.nbt.NBTTagCompound;

public class ProducerLathe extends ProducerMachineRecipeMode {
	
	public ProducerLathe() {
		this(60);
	}

	public ProducerLathe(int speedModifier) {
		super("Lathe", 1, 2, speedModifier, LatheModes.ROD);
	}

	public ProducerLathe(NBTTagCompound nbt, IModular modular, ModuleStack stack) {
		super(nbt, modular, stack);
	}

	// Inventory
	@Override
	public List<Slot> addSlots(IContainerBase container, IModular modular, ModuleStack stack) {
		ArrayList<Slot> list = new ArrayList<Slot>();
		list.add(new SlotModular(modular.getMachine(), 0, 54, 35, stack));
		list.add(new SlotModularOutput(modular.getMachine(), 1, 116, 35, stack));
		list.add(new SlotModularOutput(modular.getMachine(), 2, 134, 35, stack));
		return list;
	}

	@Override
	public int getSizeInventory(ModuleStack stack) {
		return 3;
	}

	// Gui
	@SideOnly(Side.CLIENT)
	@Override
	public void addWidgets(IGuiBase gui, IModular modular, ModuleStack stack) {
		ModuleStack<IModule, IProducerEngine> engine = ModuleUtils.getModuleStackEngine(modular);
		int burnTime = 0;
		int burnTimeTotal = 0;
		if(engine != null){
			burnTime = engine.getProducer().getBurnTime(engine);
			burnTimeTotal = engine.getProducer().getBurnTimeTotal(engine);
		}
		gui.getWidgetManager().add(new WidgetProgressBar(82, 36, burnTime, burnTimeTotal));
		gui.getWidgetManager().add(new WidgetButtonMode(86, 13, getMode()));
	}
	
	@Override
	public List<Widget> addNEIWidgets(IGuiBase gui, ModuleStack stack, IRecipe recipe) {
		gui.getWidgetManager().add(new WidgetProgressBar(82, 25, 0, 0));
		gui.getWidgetManager().add(new WidgetButtonMode(86, 0, (IMachineMode)recipe.getModifiers()[0]));
		return gui.getWidgetManager().getWidgets();
	}

	// NEI
	@Override
	public List<NeiStack> addNEIStacks(ModuleStack stack, IRecipe recipe) {
		ArrayList<NeiStack> list = new ArrayList<NeiStack>();
		list.add(new NeiStack(54, 24, true));
		list.add(new NeiStack(116, 24, false));
		list.add(new NeiStack(134, 24, false));
		return list;
	}

	// Recipe
	@Override
	public RecipeInput[] getInputs(IModular modular, ModuleStack stack) {
		return getInputItems(modular, stack);
	}

	@Override
	public String getRecipeName(ModuleStack stack) {
		return "Lathe";
	}

	@Override
	public int getSpeedModifier() {
		return 95;
	}

	@Override
	public int getColor() {
		return 0x49D18B;
	}

	@Override
	public Class<? extends IMachineMode> getModeClass() {
		return LatheModes.class;
	}

}
