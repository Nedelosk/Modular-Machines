package nedelosk.modularmachines.common.modular.module.tool.producer.boiler;

import java.util.ArrayList;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import nedelosk.forestday.api.guis.IContainerBase;
import nedelosk.forestday.api.guis.IGuiBase;
import nedelosk.modularmachines.api.modular.machines.basic.IModular;
import nedelosk.modularmachines.api.modular.machines.basic.SlotModular;
import nedelosk.modularmachines.api.modular.machines.basic.SlotModularOutput;
import nedelosk.modularmachines.api.modular.utils.ModuleStack;
import nedelosk.modularmachines.api.recipes.IRecipeManager;
import nedelosk.modularmachines.api.recipes.NeiStack;
import nedelosk.modularmachines.api.recipes.RecipeInput;
import nedelosk.modularmachines.client.gui.widget.WidgetProgressBar;
import nedelosk.modularmachines.common.modular.module.tool.producer.machine.ProducerMachineRecipe;
import nedelosk.modularmachines.common.modular.utils.RecipeManager;
import net.minecraft.inventory.Slot;
import net.minecraft.nbt.NBTTagCompound;

public class ProducerBurningBoiler extends ProducerMachineRecipe {

	public ProducerBurningBoiler() {
		super("BoilerBurning", 1, 2, 125);
	}
	
	public ProducerBurningBoiler(String modifier, int speed) {
		super(modifier, 1, 2, speed);
	}
	
	public ProducerBurningBoiler(NBTTagCompound nbt, IModular modular, ModuleStack stack) {
		super(nbt, modular, stack);
	}

	@Override
	public String getRecipeName(ModuleStack stack) {
		return "BoilerBurning";
	}

	@Override
	public RecipeInput[] getInputs(IModular modular, ModuleStack stack) {
		RecipeInput[] fluids = getInputFluids(modular, stack);
		RecipeInput[] items = getInputItems(modular, stack);

		return new RecipeInput[] { fluids[0], items[1] };
	}
	
	@SideOnly(Side.CLIENT)
	@Override
	public void addWidgets(IGuiBase gui, IModular modular, ModuleStack stack) {
		gui.getWidgetManager().add(new WidgetProgressBar(82, 36, burnTime, burnTimeTotal));
	}

	@Override
	public ArrayList<NeiStack> addNEIStacks(ModuleStack stack) {
		ArrayList<NeiStack> slots = new ArrayList();
		return slots;
	}

	@Override
	public ArrayList<Slot> addSlots(IContainerBase container, IModular modular, ModuleStack stack) {
		ArrayList<Slot> slots = new ArrayList();
		slots.add(new SlotModular(modular.getMachine(), 0, 36, 35, stack));
		slots.add(new SlotModularOutput(modular.getMachine(), 2, 116, 35, stack));
		slots.add(new SlotModularOutput(modular.getMachine(), 3, 134, 35, stack));
		return slots;
	}

	@Override
	public int getSizeInventory(ModuleStack stack) {
		return 3;
	}

	@Override
	public int getSpeedModifier() {
		return 65;
	}

	@Override
	public ArrayList<String> getRequiredModules() {
		ArrayList<String> modules = new ArrayList();
		modules.add("Casing");
		modules.add("TankManager");
		return modules;
	}

	@Override
	public int getColor() {
		return 0x850715;
	}
	
	@Override
	public IRecipeManager creatRecipeManager(IModular modular, String recipeName, int energyModifier, RecipeInput[] inputs) {
		return new RecipeManager(modular, recipeName, inputs);
	}
	
	@Override
	public IRecipeManager creatRecipeManager() {
		return new RecipeManager();
	}

}
