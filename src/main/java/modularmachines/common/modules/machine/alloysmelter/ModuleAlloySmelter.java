package modularmachines.common.modules.machine.alloysmelter;

import net.minecraft.nbt.NBTTagCompound;

import modularmachines.api.recipes.IRecipeConsumer;
import modularmachines.api.recipes.RecipeItem;
import modularmachines.common.inventory.ItemHandlerModule;
import modularmachines.common.modules.filters.FilterMachine;
import modularmachines.common.modules.filters.OutputFilter;
import modularmachines.common.modules.machine.MachineCategorys;
import modularmachines.common.modules.machine.ModuleHeatMachine;

public class ModuleAlloySmelter extends ModuleHeatMachine {
	
	public final ItemHandlerModule itemHandler;
	
	public ModuleAlloySmelter(int workTimeModifier) {
		super(workTimeModifier);
		itemHandler = new ItemHandlerModule(this);
		itemHandler.addSlot(true).addFilter(FilterMachine.INSTANCE);
		itemHandler.addSlot(true).addFilter(FilterMachine.INSTANCE);
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
	public void createComponents() {
		super.createComponents();
		addComponent(new ModuleComponentAlloySmelter(this));
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
		return MachineCategorys.ALLOY_SMELTER;
	}
	
	@Override
	public ItemHandlerModule getItemHandler() {
		return itemHandler;
	}
	
}
