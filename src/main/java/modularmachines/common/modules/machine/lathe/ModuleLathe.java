package modularmachines.common.modules.machine.lathe;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.WorldServer;
import modularmachines.api.ILocatable;
import modularmachines.api.modules.IModuleStorage;
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
import modularmachines.common.network.PacketHandler;
import modularmachines.common.network.packets.PacketSyncModule;

public class ModuleLathe extends ModuleKineticMachine<IRecipeMode> implements IModuleJei, IModuleMode {

	public final ItemHandlerModule itemHandler;
	public final IMode defaultMode;
	public IMode mode;
	
	public ModuleLathe(IModuleStorage storage, int workTimeModifier, double maxSpeed) {
		super(storage, workTimeModifier, maxSpeed);
		itemHandler = new ItemHandlerModule(this);
		itemHandler.addSlot(true).addFilter(FilterMachine.INSTANCE);
		itemHandler.addSlot(false).addFilter(OutputFilter.INSTANCE);
		itemHandler.addSlot(false).addFilter(OutputFilter.INSTANCE);
		this.defaultMode = LatheMode.ROD;
		this.mode = defaultMode;
	}
	
	@Override
	public void readFromNBT(NBTTagCompound compound) {
		super.readFromNBT(compound);
		itemHandler.readFromNBT(compound);
		setCurrentMode(compound.getInteger("Mode"));
	}
	
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
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
	
	public ItemHandlerModule getItemHandler() {
		return itemHandler;
	}

	@Override
	public String[] getJEIRecipeCategorys() {
		return new String[] { MachineCategorys.LATHE };
	}

	@Override
	public void sendModuleUpdate() {
		ILocatable locatable = logic.getLocatable();
		if (locatable != null) {
			PacketHandler.sendToNetwork(new PacketSyncModule(this),locatable.getCoordinates(), (WorldServer) locatable.getWorldObj());
		}
	}

	@Override
	public String getRecipeCategory() {
		return MachineCategorys.LATHE;
	}
	
	@Override
	protected void initPages() {
		super.initPages();
		addPage(new PageLathe(this));
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
