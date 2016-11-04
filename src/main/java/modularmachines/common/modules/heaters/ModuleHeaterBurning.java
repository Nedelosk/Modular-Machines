package modularmachines.common.modules.heaters;

import java.util.List;

import modularmachines.api.modules.IModulePage;
import modularmachines.api.modules.containers.IModuleContainer;
import modularmachines.api.modules.containers.IModuleProvider;
import modularmachines.api.modules.handlers.inventory.IModuleInventory;
import modularmachines.api.modules.heaters.IModuleHeaterBurning;
import modularmachines.api.modules.models.IModelHandler;
import modularmachines.api.modules.models.ModelHandlerStatus;
import modularmachines.api.modules.state.IModuleState;
import modularmachines.api.modules.state.IModuleStateClient;
import modularmachines.api.property.PropertyInteger;
import modularmachines.common.modules.pages.BurningHeaterPage;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ModuleHeaterBurning extends ModuleHeater implements IModuleHeaterBurning {

	public static final PropertyInteger BURNTIME = new PropertyInteger("burnTime", 0);
	public static final PropertyInteger BURNTIMETOTAL = new PropertyInteger("burnTimeTotal", 0);

	public ModuleHeaterBurning() {
		super("burning");
	}

	@Override
	public IModuleState createState(IModuleProvider provider, IModuleContainer container) {
		return super.createState(provider, container).register(BURNTIME).register(BURNTIMETOTAL);
	}

	@SideOnly(Side.CLIENT)
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
	}

	@Override
	public int getBurnTime(IModuleState state) {
		return state.get(BURNTIME);
	}

	@Override
	public int getBurnTimeTotal(IModuleState state) {
		return state.get(BURNTIMETOTAL);
	}

	@Override
	public void setBurnTime(IModuleState state, int burnTime) {
		state.set(BURNTIME, burnTime);
	}

	@Override
	public void addBurnTime(IModuleState state, int burnTime) {
		state.set(BURNTIME, state.get(BURNTIME) + burnTime);
	}

	@Override
	protected boolean canAddHeat(IModuleState state) {
		return getBurnTime(state) > 0;
	}

	@Override
	protected void afterAddHeat(IModuleState state) {
		addBurnTime(state, -25 * state.getContainer().getItemContainer().getSize().ordinal());
	}

	@Override
	protected boolean updateFuel(IModuleState state) {
		IModuleInventory inventory = state.getPage(BurningHeaterPage.class).getInventory();
		ItemStack input = inventory.getStackInSlot(0);
		if (input != null) {
			if (inventory.extractItemInternal(0, 1, false) != null) {
				setBurnTime(state, TileEntityFurnace.getItemBurnTime(input));
				state.set(BURNTIMETOTAL, TileEntityFurnace.getItemBurnTime(input));
				return true;
			}
		}
		return false;
	}

	@Override
	public List<IModulePage> createPages(IModuleState state) {
		List<IModulePage> pages = super.createPages(state);
		pages.add(new BurningHeaterPage(state));
		return pages;
	}

	@Override
	public int getColor(IModuleContainer container) {
		return 0x615524;
	}

	@Override
	public boolean isWorking(IModuleState state) {
		return getBurnTime(state) > 0;
	}
}
