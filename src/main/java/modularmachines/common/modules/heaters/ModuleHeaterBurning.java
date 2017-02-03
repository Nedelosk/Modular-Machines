package modularmachines.common.modules.heaters;

import java.util.List;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import scala.reflect.internal.Types.Type.FilterMapForeach;
import modularmachines.api.modules.IModuleStorage;
import modularmachines.api.modules.containers.IModuleContainer;
import modularmachines.common.inventory.ItemHandlerModule;
import modularmachines.common.modules.IModuleBurning;
import modularmachines.common.modules.filters.FilterMachine;
import modularmachines.common.modules.filters.ItemFliterFurnaceFuel;

public class ModuleHeaterBurning extends ModuleHeater {

	public final ItemHandlerModule itemHandler;
	
	public ModuleHeaterBurning(IModuleStorage storage, double heatOnCycle, int heatModifier) {
		super(storage, heatOnCycle, heatModifier);
		itemHandler = new ItemHandlerModule(this);
		itemHandler.addSlot(true).addFilter(ItemFliterFurnaceFuel.INSTANCE);
	}
	
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		super.writeToNBT(compound);
		itemHandler.writeToNBT(compound);
		return compound;
	}
	
	@Override
	public void readFromNBT(NBTTagCompound compound) {
		super.readFromNBT(compound);
		itemHandler.readFromNBT(compound);
	}
	
	public ItemHandlerModule getItemHandler() {
		return itemHandler;
	}

	@Override
	protected boolean canAddHeat() {
		return fuel > 0;
	}

	@Override
	protected void afterAddHeat() {
		fuel-=25 * getData().getSize().ordinal();
	}

	@Override
	protected boolean updateFuel() {
		ItemStack input = itemHandler.getStackInSlot(0);
		if (!input.isEmpty()) {
			if (!itemHandler.extractItemInternal(0, 1, false).isEmpty()) {
				fuel = TileEntityFurnace.getItemBurnTime(input);
				fuelTotal = fuel;
				return true;
			}
		}
		return false;
	}
	
	@Override
	protected void initPages() {
		super.initPages();
		addPage(new PageBurningHeater(this));
	}
	
	//TODO: rendering
	/*@SideOnly(Side.CLIENT)
	@Override
	public boolean needHandlerReload(IModuleStateClient state) {
		IModelHandler handler = state.getModelHandler();
		if (handler instanceof ModelHandlerStatus) {
			ModelHandlerStatus status = (ModelHandlerStatus) handler;
			if (getBurnTime(state) > 0) {
				if (!status.status) {
					status.status = true;
					return true;
				}
			} else {
				if (status.status) {
					status.status = false;
					return true;
				}
			}
		}
		return false;
	}*/
}
