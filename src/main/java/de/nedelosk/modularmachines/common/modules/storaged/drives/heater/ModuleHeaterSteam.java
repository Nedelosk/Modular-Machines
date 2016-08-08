package de.nedelosk.modularmachines.common.modules.storaged.drives.heater;

import java.awt.Color;
import java.text.DecimalFormat;
import java.util.List;

import de.nedelosk.modularmachines.api.gui.IContainerBase;
import de.nedelosk.modularmachines.api.modular.IModular;
import de.nedelosk.modularmachines.api.modular.handlers.IModularHandler;
import de.nedelosk.modularmachines.api.modules.handlers.IModulePage;
import de.nedelosk.modularmachines.api.modules.handlers.inventory.IModuleInventory;
import de.nedelosk.modularmachines.api.modules.handlers.inventory.IModuleInventoryBuilder;
import de.nedelosk.modularmachines.api.modules.handlers.inventory.slots.SlotModule;
import de.nedelosk.modularmachines.api.modules.handlers.tank.IModuleTank;
import de.nedelosk.modularmachines.api.modules.handlers.tank.IModuleTankBuilder;
import de.nedelosk.modularmachines.api.modules.items.IModuleContainer;
import de.nedelosk.modularmachines.api.modules.models.IModelHandler;
import de.nedelosk.modularmachines.api.modules.state.IModuleState;
import de.nedelosk.modularmachines.api.modules.state.IModuleStateClient;
import de.nedelosk.modularmachines.api.modules.storaged.EnumModuleSize;
import de.nedelosk.modularmachines.api.modules.storaged.drives.heaters.IModuleHeaterBurning;
import de.nedelosk.modularmachines.api.property.PropertyInteger;
import de.nedelosk.modularmachines.client.gui.widgets.WidgetFluidTank;
import de.nedelosk.modularmachines.client.modules.ModelHandlerStatus;
import de.nedelosk.modularmachines.common.core.FluidManager;
import de.nedelosk.modularmachines.common.modules.handlers.FluidFilter;
import de.nedelosk.modularmachines.common.modules.handlers.ItemFluidFilter;
import de.nedelosk.modularmachines.common.modules.handlers.ModulePage;
import de.nedelosk.modularmachines.common.modules.handlers.OutputAllFilter;
import de.nedelosk.modularmachines.common.utils.Translator;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ModuleHeaterSteam extends ModuleHeater {

	public static final PropertyInteger BURNTIME = new PropertyInteger("burnTime", 0);
	public static final PropertyInteger BURNTIMETOTAL = new PropertyInteger("burnTimeTotal", 0);

	public ModuleHeaterSteam(int complexity, int maxHeat, int heatModifier, EnumModuleSize size) {
		super("heater.steam", complexity, maxHeat, heatModifier, size);
	}

	@Override
	public IModuleState createState(IModular modular, IModuleContainer container) {
		return super.createState(modular, container).register(BURNTIME).register(BURNTIMETOTAL);
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
		addBurnTime(state, -10);
	}

	@Override
	protected boolean updateFuel(IModuleState state) {
		IModuleTank tank = (IModuleTank) state.getContentHandler(IModuleTank.class);
		FluidStack input = tank.getTank(0).getFluid();
		if(input == null){
			if(tank.drainInternal(80, true) != null){
				setBurnTime(state, 50);
				state.set(BURNTIMETOTAL, 50);
				return true;
			}
		}
		return false;
	}

	@Override
	public void updateServer(IModuleState state, int tickCount) {
		if(state.getModular().updateOnInterval(20)){
			IModuleInventory inventory = (IModuleInventory) state.getContentHandler(IModuleInventory.class);
			IModuleTank tank = (IModuleTank) state.getContentHandler(IModuleTank.class);
			if(inventory != null){
				if(inventory.getStackInSlot(0) != null){
					ItemStack stack = inventory.getStackInSlot(0);
					IFluidHandler fludiHandler = stack.getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, null);
					ItemStack containerStack = FluidUtil.tryEmptyContainer(stack, tank.getTank(0), 1000, null, false);
					if(containerStack != null){
						if(inventory.extractItem(0, 1, true) != null){
							if(inventory.insertItem(1, containerStack, true) == null){
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
		if(handler instanceof ModelHandlerStatus){
			ModelHandlerStatus status = (ModelHandlerStatus) handler;
			if(getBurnTime(state) > 0){
				if(!status.status){
					status.status = true;
					return true;
				}
			}else{
				if(status.status){
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
		pages.add(new HeaterSteamPage("Basic", state));
		return pages;
	}

	@Override
	public int getColor() {
		return 0x6E593C;
	}

	public class HeaterSteamPage extends ModulePage<IModuleHeaterBurning>{

		public HeaterSteamPage(String pageID, IModuleState<IModuleHeaterBurning> heaterState) {
			super(pageID, "heater", heaterState);
		}

		@Override
		public void addWidgets() {
			gui.getWidgetManager().add(new WidgetFluidTank(state.getContentHandler(IModuleTank.class).getTank(0)));
		}

		@Override
		public void drawForeground(FontRenderer fontRenderer, int mouseX, int mouseY) {
			super.drawForeground(fontRenderer, mouseX, mouseY);
			DecimalFormat f = new DecimalFormat("#0.00"); 

			String heatName = Translator.translateToLocalFormatted("module.heater.heat", f.format(state.getModular().getHeatSource().getHeatStored()));
			fontRenderer.drawString(heatName, 135 - (fontRenderer.getStringWidth(heatName) / 2),45, Color.gray.getRGB());
		}

		@Override
		public void createInventory(IModuleInventoryBuilder invBuilder) {
			invBuilder.addInventorySlot(true, 15, 28, new ItemFluidFilter(true));
			invBuilder.addInventorySlot(false, 15, 48, new OutputAllFilter());
		}

		@Override
		public void createSlots(IContainerBase<IModularHandler> container, List<SlotModule> modularSlots) {
			modularSlots.add(new SlotModule(state, 0).setBackgroundTexture("liquid"));
			modularSlots.add(new SlotModule(state, 1).setBackgroundTexture("container"));
		}

		@Override
		public void createTank(IModuleTankBuilder tankBuilder) {
			tankBuilder.addFluidTank(16000, true, 80, 18, new FluidFilter(FluidManager.Steam));
		}
	}
}
