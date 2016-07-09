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
			IModuleInventory inventory = (IModuleInventory) state.getContentHandler(IModuleInventory.class);
			IModuleTank tank = (IModuleTank) state.getContentHandler(IModuleTank.class);
			if(inventory != null){
				if(inventory.getStackInSlot(0) != null){
					ItemStack stack = inventory.getStackInSlot(0);
					IFluidHandler fludiHandler = stack.getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, null);
					ItemStack containerStack = FluidUtil.tryEmptyContainer(stack, tank.getTank(0), 1000, null, false);
					if(containerStack != null){
						if(inventory.extractItemInternal(0, 1, true) != null){
							if(inventory.insertItemInternal(1, containerStack, true) == null){
								inventory.insertItemInternal(1, FluidUtil.tryEmptyContainer(stack, tank.getTank(0), 1000, null, true), false);
								inventory.extractItemInternal(0, 1, false);
							}
						}
					}
				}
				if(inventory.getStackInSlot(2) != null){
					ItemStack stack = inventory.getStackInSlot(2);
					ItemStack containerStack = FluidUtil.tryFillContainer(stack, tank.getTank(1), 1000, null, false);
					if(containerStack != null){
						if(inventory.extractItemInternal(2, 1, true) != null){
							if(inventory.insertItemInternal(3, containerStack, true) == null){
								inventory.insertItemInternal(3, FluidUtil.tryFillContainer(stack, tank.getTank(1), 1000, null, true), false);
								inventory.extractItemInternal(2, 1, false);
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
		return state.getContentHandler(IModuleTank.class).getInputItems();
	}

	@Override
	public List<IModulePage> createPages(IModuleState state) {
		List<IModulePage> pages = super.createPages(state);
		pages.add(new BoilerPage("Basic", "boiler", state));
		return pages;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public IModuleModelHandler getInitModelHandler(IModuleContainer container) {
		return new ModelHandlerDefault(new ResourceLocation("modularmachines:module/boilers/" + container.getMaterial().getName()));
	}

	public class BoilerPage extends ModulePage<IModuleMachine> {

		public BoilerPage(String pageID, String title, IModuleState<IModuleMachine> moduleState) {
			super(pageID, title, moduleState);
		}

		@Override
		public void createInventory(IModuleInventoryBuilder invBuilder) {
			invBuilder.addInventorySlot(true, 15, 28, new ItemFluidFilter(true));
			invBuilder.addInventorySlot(false, 15, 48, new OutputAllFilter());

			invBuilder.addInventorySlot(true, 147, 28, new ItemFluidFilter(false));
			invBuilder.addInventorySlot(false, 147, 48, new OutputAllFilter());
		}

		@Override
		public void createTank(IModuleTankBuilder tankBuilder) {
			tankBuilder.addFluidTank(16000, true, 35, 15, new FluidFilterMachine());
			tankBuilder.addFluidTank(16000, false, 125, 15, new OutputAllFilter());
		}

		@Override
		public void createSlots(IContainerBase<IModularHandler> container, List<SlotModule> modularSlots) {
			modularSlots.add(new SlotModule(state, 0).setBackgroundTexture("liquid"));
			modularSlots.add(new SlotModule(state, 1).setBackgroundTexture("container"));

			modularSlots.add(new SlotModule(state, 2).setBackgroundTexture("container"));
			modularSlots.add(new SlotModule(state, 3).setBackgroundTexture("liquid"));
		}

		@SideOnly(Side.CLIENT)
		@Override
		public void addWidgets(List widgets) {
			widgets.add(new WidgetProgressBar(75, 35, state.getModule().getWorkTime(state), state.getModule().getWorkTimeTotal(state)));
			widgets.add(new WidgetFluidTank(state.getContentHandler(IModuleTank.class).getTank(0)));
			widgets.add(new WidgetFluidTank(state.getContentHandler(IModuleTank.class).getTank(1)));
		}
	}

	@Override
	public int getColor() {
		return 0x959595;
	}
}
