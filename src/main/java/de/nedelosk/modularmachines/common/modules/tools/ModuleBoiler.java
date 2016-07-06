package de.nedelosk.modularmachines.common.modules.tools;

import java.util.List;

import de.nedelosk.modularmachines.api.inventory.IContainerBase;
import de.nedelosk.modularmachines.api.modular.IModularHandler;
import de.nedelosk.modularmachines.api.modules.IModuleColored;
import de.nedelosk.modularmachines.api.modules.IModuleContainer;
import de.nedelosk.modularmachines.api.modules.IModuleModelHandler;
import de.nedelosk.modularmachines.api.modules.handlers.IModulePage;
import de.nedelosk.modularmachines.api.modules.handlers.inventory.IModuleInventory;
import de.nedelosk.modularmachines.api.modules.handlers.inventory.IModuleInventoryBuilder;
import de.nedelosk.modularmachines.api.modules.handlers.inventory.slots.SlotModule;
import de.nedelosk.modularmachines.api.modules.handlers.tank.EnumTankMode;
import de.nedelosk.modularmachines.api.modules.handlers.tank.IModuleTank;
import de.nedelosk.modularmachines.api.modules.handlers.tank.IModuleTankBuilder;
import de.nedelosk.modularmachines.api.modules.state.IModuleState;
import de.nedelosk.modularmachines.api.modules.tool.IModuleMachine;
import de.nedelosk.modularmachines.api.recipes.RecipeItem;
import de.nedelosk.modularmachines.client.gui.widgets.WidgetFluidTank;
import de.nedelosk.modularmachines.client.gui.widgets.WidgetProgressBar;
import de.nedelosk.modularmachines.client.modules.ModelHandlerDefault;
import de.nedelosk.modularmachines.common.modules.ModuleMachineHeat;
import de.nedelosk.modularmachines.common.modules.handlers.FluidFilterMachine;
import de.nedelosk.modularmachines.common.modules.handlers.ItemFluidFilter;
import de.nedelosk.modularmachines.common.modules.handlers.ModulePage;
import de.nedelosk.modularmachines.common.modules.handlers.OutputAllFilter;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ModuleBoiler extends ModuleMachineHeat implements IModuleColored {

	public ModuleBoiler(int speed, int size) {
		super(speed, size);
	}

	@Override
	public boolean renderWall() {
		return true;
	}

	@Override
	public void updateServer(IModuleState state, int tickCount) {
		super.updateServer(state, tickCount);

		if(state.getModular().updateOnInterval(20)){
			IModuleInventory inventory = (IModuleInventory) state.getContentHandler(ItemStack.class);
			IModuleTank tank = (IModuleTank) state.getContentHandler(FluidStack.class);
			if(inventory != null){
				BoilerPage page = (BoilerPage) state.getPage("Basic");

				if(inventory.getStackInSlot(page.fluidInputInput) != null){
					ItemStack stack = inventory.getStackInSlot(page.fluidInputInput);
					IFluidHandler fludiHandler = stack.getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, null);
					ItemStack containerStack = FluidUtil.tryEmptyContainer(stack, tank.getTank(page.tankInput), 1000, null, false);
					if(containerStack != null){
						if(inventory.extractItem(page.fluidInputInput, 1, true) != null){
							if(inventory.insertItem(page.fluidInputOutput, containerStack, true) == null){
								inventory.insertItem(page.fluidInputOutput, FluidUtil.tryEmptyContainer(stack, tank.getTank(page.tankInput), 1000, null, true), false);
								inventory.extractItem(page.fluidInputInput, 1, false);
							}
						}
					}
				}
				if(inventory.getStackInSlot(page.fluidOutputInput) != null){
					ItemStack stack = inventory.getStackInSlot(page.fluidOutputInput);
					ItemStack containerStack = FluidUtil.tryFillContainer(stack, tank.getTank(page.tankOutput), 1000, null, false);
					if(containerStack != null){
						if(inventory.extractItem(page.fluidOutputInput, 1, true) != null){
							if(inventory.insertItem(page.fluidOutputOutput, containerStack, true) == null){
								inventory.insertItem(page.fluidOutputOutput, FluidUtil.tryFillContainer(stack, tank.getTank(page.tankOutput), 1000, null, true), false);
								inventory.extractItem(page.fluidOutputInput, 1, false);
							}
						}
					}
				}
			}
		}
	}

	@Override
	protected int getConsumeHeat(IModuleState state) {
		return 5;
	}

	@Override
	public String getRecipeCategory(IModuleState state) {
		return "Boiler";
	}

	@Override
	public RecipeItem[] getInputs(IModuleState state) {
		return ((IModuleTank)state.getContentHandler(FluidStack.class)).getInputItems();
	}

	@Override
	public List<IModulePage> createPages(IModuleState state) {
		List<IModulePage> pages = super.createPages(state);
		pages.add(new BoilerPage("Basic", state));
		return pages;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public IModuleModelHandler getInitModelHandler(IModuleContainer container) {
		return new ModelHandlerDefault(new ResourceLocation("modularmachines:module/boilers/" + container.getMaterial().getName()));
	}

	/*@SideOnly(Side.CLIENT)
	@Override
	public IJEIPage createNEIPage(IModuleJEI stack) {
		return new BoilerNEIPage(stack);
	}*/

	public class BoilerPage extends ModulePage<IModuleMachine> {

		public int tankInput;
		public int tankOutput;

		public int fluidInputInput;
		public int fluidInputOutput;

		public int fluidOutputInput;
		public int fluidOutputOutput;

		public BoilerPage(String pageID, IModuleState<IModuleMachine> moduleState) {
			super(pageID, moduleState);
		}

		@Override
		public void createInventory(IModuleInventoryBuilder invBuilder) {
			invBuilder.setInventoryName("module.inventory.boiler.name");

			fluidInputInput = invBuilder.addInventorySlot(true, new ItemFluidFilter());
			fluidInputOutput = invBuilder.addInventorySlot(false, new OutputAllFilter());

			fluidOutputInput = invBuilder.addInventorySlot(true, new ItemFluidFilter());
			fluidOutputOutput = invBuilder.addInventorySlot(false, new OutputAllFilter());
		}

		@Override
		public void createTank(IModuleTankBuilder tankBuilder) {
			tankInput = tankBuilder.addFluidTank(16000, EnumTankMode.INPUT, new FluidFilterMachine());
			tankOutput = tankBuilder.addFluidTank(16000, EnumTankMode.OUTPUT, new OutputAllFilter());
		}

		@Override
		public void createSlots(IContainerBase<IModularHandler> container, List<SlotModule> modularSlots) {
			modularSlots.add(new SlotModule(state, fluidInputInput, 15, 28).setBackgroundTexture("liquid"));
			modularSlots.add(new SlotModule(state, fluidInputOutput, 15, 48).setBackgroundTexture("container"));

			modularSlots.add(new SlotModule(state, fluidOutputInput, 147, 28).setBackgroundTexture("container"));
			modularSlots.add(new SlotModule(state, fluidOutputOutput, 147, 48).setBackgroundTexture("liquid"));
		}

		@SideOnly(Side.CLIENT)
		@Override
		public void addWidgets(List widgets) {
			widgets.add(new WidgetProgressBar(75, 35, state.getModule().getWorkTime(state), state.getModule().getWorkTimeTotal(state)));
			widgets.add(new WidgetFluidTank(((IModuleTank)state.getContentHandler(FluidStack.class)).getTank(tankInput), 35, 15));
			widgets.add(new WidgetFluidTank(((IModuleTank)state.getContentHandler(FluidStack.class)).getTank(tankOutput), 125, 15));
		}
	}

	@Override
	public int getColor() {
		return 0xB22222;
	}
}
