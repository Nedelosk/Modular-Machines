package modularmachines.common.modules.machine.pulverizer;

import net.minecraft.nbt.NBTTagCompound;

import modularmachines.api.modules.IModuleStorage;
import modularmachines.api.recipes.IRecipeConsumer;
import modularmachines.api.recipes.RecipeItem;
import modularmachines.common.inventory.ItemHandlerModule;
import modularmachines.common.modules.filters.FilterMachine;
import modularmachines.common.modules.filters.OutputFilter;
import modularmachines.common.modules.machine.MachineCategorys;
import modularmachines.common.modules.machine.ModuleKineticMachine;

public class ModulePulverizer extends ModuleKineticMachine {
	public final ItemHandlerModule itemHandler;
	
	public ModulePulverizer(IModuleStorage storage, int workTimeModifier, double maxSpeed) {
		super(storage, workTimeModifier, maxSpeed);
		itemHandler = new ItemHandlerModule(this);
		itemHandler.addSlot(true).addFilter(FilterMachine.INSTANCE);
		itemHandler.addSlot(false).addFilter(OutputFilter.INSTANCE);
		itemHandler.addSlot(false).addFilter(OutputFilter.INSTANCE);
		itemHandler.addSlot(false).addFilter(OutputFilter.INSTANCE);
		itemHandler.addSlot(false).addFilter(OutputFilter.INSTANCE);
	}
	
	@Override
	public void readFromNBT(NBTTagCompound compound) {
		super.readFromNBT(compound);
		itemHandler.readFromNBT(compound);
	}
	
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		super.writeToNBT(compound);
		itemHandler.writeToNBT(compound);
		return compound;
	}
	
	@Override
	protected void initPages() {
		super.initPages();
		addPage(new PagePulverizer(this));
	}

	@Override
	protected IRecipeConsumer[] getConsumers() {
		return new IRecipeConsumer[]{itemHandler};
	}

	@Override
	public RecipeItem[] getInputs() {
		return itemHandler.getInputs();
	}

	@Override
	protected String getRecipeCategory() {
		return MachineCategorys.PULVERIZER;
	}
	
	@Override
	public ItemHandlerModule getItemHandler() {
		return itemHandler;
	}
}
