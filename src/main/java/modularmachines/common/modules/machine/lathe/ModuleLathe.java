package modularmachines.common.modules.machine.lathe;

import net.minecraft.nbt.NBTTagCompound;

import modularmachines.api.recipes.IMode;
import modularmachines.api.recipes.IRecipeConsumer;
import modularmachines.api.recipes.IRecipeMode;
import modularmachines.api.recipes.RecipeItem;
import modularmachines.common.inventory.ItemHandlerModule;
import modularmachines.common.modules.IModuleJei;
import modularmachines.common.modules.IModuleMode;
import modularmachines.common.modules.filters.FilterMachine;
import modularmachines.common.modules.filters.OutputFilter;
import modularmachines.common.modules.machine.MachineCategorys;
import modularmachines.common.modules.machine.ModuleKineticMachine;

public class ModuleLathe extends ModuleKineticMachine<IRecipeMode> implements IModuleJei, IModuleMode {
	
	public final ItemHandlerModule itemHandler;
	public final IMode defaultMode;
	public IMode mode;
	
	public ModuleLathe(int workTimeModifier, double maxSpeed) {
		super(workTimeModifier, maxSpeed);
		this.defaultMode = LatheMode.ROD;
		this.mode = defaultMode;
		
		itemHandler = new ItemHandlerModule(this);
		itemHandler.addSlot(true).addFilter(FilterMachine.INSTANCE);
		itemHandler.addSlot(false).addFilter(OutputFilter.INSTANCE);
		itemHandler.addSlot(false).addFilter(OutputFilter.INSTANCE);
	}
	
	@Override
	public void readFromNBT(NBTTagCompound compound) {
		super.readFromNBT(compound);
		itemHandler.readFromNBT(compound);
		setCurrentMode(compound.getInteger("Mode"));
	}
	
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		super.writeToNBT(compound);
		itemHandler.writeToNBT(compound);
		setCurrentMode(compound.getInteger("Mode"));
		return compound;
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
	public ItemHandlerModule getItemHandler() {
		return itemHandler;
	}
	
	@Override
	public String[] getJeiRecipeCategorys() {
		return new String[]{MachineCategorys.LATHE};
	}
	
	@Override
	public String getRecipeCategory() {
		return MachineCategorys.LATHE;
	}
	
	@Override
	protected void createComponents() {
		super.createComponents();
		addComponent(new ModuleComponentLathe(this));
	}
	
	@Override
	protected boolean isRecipeValid(IRecipeMode recipe) {
		if (super.isRecipeValid(recipe)) {
			if (recipe.getMode() == getCurrentMode()) {
				return true;
			}
		}
		return false;
	}
	
	@Override
	public IMode getCurrentMode() {
		return mode;
	}
	
	@Override
	public void setCurrentMode(int ordinal) {
		mode = mode.getMode(ordinal);
	}
	
	@Override
	public IMode getDefaultMode() {
		return defaultMode;
	}
}
