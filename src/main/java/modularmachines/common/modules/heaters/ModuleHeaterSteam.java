package modularmachines.common.modules.heaters;

import java.util.List;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import modularmachines.api.modules.IModulePage;
import modularmachines.api.modules.containers.IModuleContainer;
import modularmachines.api.modules.containers.IModuleProvider;
import modularmachines.api.modules.handlers.inventory.IModuleInventory;
import modularmachines.api.modules.handlers.tank.IModuleTank;
import modularmachines.api.modules.models.IModelHandler;
import modularmachines.api.modules.models.ModelHandlerStatus;
import modularmachines.api.modules.state.IModuleState;
import modularmachines.api.modules.state.IModuleStateClient;
import modularmachines.api.property.PropertyInteger;
import modularmachines.common.modules.pages.SteamHeaterPage;

public class ModuleHeaterSteam extends ModuleHeater {

	public static final PropertyInteger BURNTIME = new PropertyInteger("burnTime", 0);
	public static final PropertyInteger BURNTIMETOTAL = new PropertyInteger("burnTimeTotal", 0);

	public ModuleHeaterSteam() {
		super("steam");
	}

	@Override
	public IModuleState createState(IModuleProvider provider, IModuleContainer container) {
		return super.createState(provider, container).register(BURNTIME).register(BURNTIMETOTAL);
	}

	@Override
	public boolean isWorking(IModuleState state) {
		return getBurnTime(state) > 0;
	}

	public int getBurnTime(IModuleState state) {
		return state.get(BURNTIME);
	}

	public void setBurnTime(IModuleState state, int burnTime) {
		state.set(BURNTIME, burnTime);
	}

	public void addBurnTime(IModuleState state, int burnTime) {
		state.set(BURNTIME, state.get(BURNTIME) + burnTime);
	}

	@Override
	protected boolean canAddHeat(IModuleState state) {
		return getBurnTime(state) > 0;
	}

	@Override
	protected void afterAddHeat(IModuleState state) {
		addBurnTime(state, -10 * state.getContainer().getItemContainer().getSize().ordinal());
	}

	@Override
	protected boolean updateFuel(IModuleState state) {
		IModuleTank tank = state.getPage(SteamHeaterPage.class).getTank();
		FluidStack input = tank.getTank(0).getFluid();
		if (input == null) {
			if (tank.drainInternal(80, true) != null) {
				setBurnTime(state, 50);
				state.set(BURNTIMETOTAL, 50);
				return true;
			}
		}
		return false;
	}

	@Override
	public void updateServer(IModuleState state, int tickCount) {
		if (state.getModular().updateOnInterval(20)) {
			IModulePage page = state.getPage(SteamHeaterPage.class);
			IModuleInventory inventory = page.getInventory();
			IModuleTank tank = page.getTank();
			if (inventory != null) {
				if (inventory.getStackInSlot(0) != null) {
					ItemStack stack = inventory.getStackInSlot(0);
					IFluidHandler fludiHandler = stack.getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, null);
					ItemStack containerStack = FluidUtil.tryEmptyContainer(stack, tank.getTank(0), 1000, null, false);
					if (containerStack != null) {
						if (inventory.extractItem(0, 1, true) != null) {
							if (inventory.insertItem(1, containerStack, true) == null) {
								inventory.insertItem(1, FluidUtil.tryEmptyContainer(stack, tank.getTank(0), 1000, null, true), false);
								inventory.extractItem(0, 1, false);
							}
						}
					}
				}
			}
		}
		super.updateServer(state, tickCount);
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
	public List<IModulePage> createPages(IModuleState state) {
		List<IModulePage> pages = super.createPages(state);
		pages.add(new SteamHeaterPage(state));
		return pages;
	}

	@Override
	public int getColor(IModuleContainer container) {
		return 0x6E593C;
	}
}
