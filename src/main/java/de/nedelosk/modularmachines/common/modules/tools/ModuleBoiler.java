package de.nedelosk.modularmachines.common.modules.tools;

import java.util.List;

import de.nedelosk.modularmachines.api.inventory.IContainerBase;
import de.nedelosk.modularmachines.api.modular.IModularTileEntity;
import de.nedelosk.modularmachines.api.modular.renderer.IRenderState;
import de.nedelosk.modularmachines.api.modular.renderer.ISimpleRenderer;
import de.nedelosk.modularmachines.api.modules.IModuleColored;
import de.nedelosk.modularmachines.api.modules.casing.IModuleCasing;
import de.nedelosk.modularmachines.api.modules.handlers.IModulePage;
import de.nedelosk.modularmachines.api.modules.handlers.inventory.IModuleInventory;
import de.nedelosk.modularmachines.api.modules.handlers.inventory.IModuleInventoryBuilder;
import de.nedelosk.modularmachines.api.modules.handlers.inventory.slots.SlotModule;
import de.nedelosk.modularmachines.api.modules.handlers.tank.EnumTankMode;
import de.nedelosk.modularmachines.api.modules.handlers.tank.IModuleTank;
import de.nedelosk.modularmachines.api.modules.handlers.tank.IModuleTankBuilder;
import de.nedelosk.modularmachines.api.modules.state.IModuleState;
import de.nedelosk.modularmachines.api.modules.tool.IModuleTool;
import de.nedelosk.modularmachines.api.recipes.RecipeItem;
import de.nedelosk.modularmachines.client.gui.widgets.WidgetFluidTank;
import de.nedelosk.modularmachines.client.gui.widgets.WidgetProgressBar;
import de.nedelosk.modularmachines.client.render.modules.MachineRenderer;
import de.nedelosk.modularmachines.common.modules.ModuleToolHeat;
import de.nedelosk.modularmachines.common.modules.handlers.FluidFilterMachine;
import de.nedelosk.modularmachines.common.modules.handlers.ModulePage;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ModuleBoiler extends ModuleToolHeat implements IModuleColored {

	public ModuleBoiler(int speed, int size) {
		super(speed, size);
	}

	@Override
	public Object[] getRecipeModifiers(IModuleState state) {
		IModuleState<IModuleCasing> casingState = state.getModular().getModules(IModuleCasing.class).get(0);
		return new Object[]{casingState.getModule().getHeat(casingState)};
	}

	@Override
	protected int getConsumeHeat(IModuleState state) {
		return 10;
	}

	@Override
	public String getRecipeCategory(IModuleState state) {
		return "Boiler";
	}

	@SideOnly(Side.CLIENT)
	@Override
	public ISimpleRenderer getRenderer(IRenderState state) {
		return new MachineRenderer(state.getModuleState().getContainer());
	}

	@Override
	public RecipeItem[] getInputs(IModuleState state) {
		return ((IModuleInventory)state.getContentHandler(ItemStack.class)).getInputItems();
	}

	@Override
	public IModulePage[] createPages(IModuleState state) {
		IModulePage[] pages = new IModulePage[] { new BoilerPage(0, state) };
		return pages;
	}

	/*@SideOnly(Side.CLIENT)
	@Override
	public IJEIPage createNEIPage(IModuleJEI stack) {
		return new BoilerNEIPage(stack);
	}*/

	public static class BoilerPage extends ModulePage<IModuleTool> {

		public static int TANKINPUT;
		public static int TANKOUTPUT;

		public BoilerPage(int pageID, IModuleState<IModuleTool> moduleState) {
			super(pageID, moduleState);
		}

		@Override
		public void createInventory(IModuleInventoryBuilder invBuilder) {
			invBuilder.setInventoryName("module.inventory.boiler.name");
		}

		@Override
		public void createTank(IModuleTankBuilder tankBuilder) {
			TANKINPUT = tankBuilder.initTank(16000, EnumFacing.EAST, EnumTankMode.INPUT, new FluidFilterMachine());
			TANKOUTPUT = tankBuilder.initTank(16000, EnumFacing.WEST, EnumTankMode.OUTPUT, new FluidFilterMachine());
		}

		@Override
		public void createSlots(IContainerBase<IModularTileEntity> container, List<SlotModule> modularSlots) {
		}

		@SideOnly(Side.CLIENT)
		@Override
		public void addWidgets(List widgets) {
			widgets.add(new WidgetProgressBar(82, 35, state.getModule().getWorkTime(state), state.getModule().getWorkTimeTotal(state)));
			widgets.add(new WidgetFluidTank(((IModuleTank)state.getContentHandler(IModuleTank.class)).getTank(TANKINPUT).getTank(), 55, 25));
			widgets.add(new WidgetFluidTank(((IModuleTank)state.getContentHandler(IModuleTank.class)).getTank(TANKOUTPUT).getTank(), 55, 25));
		}
	}

	/*@SideOnly(Side.CLIENT)
	public static class BoilerNEIPage extends NEIPage {

		public BoilerNEIPage(IModuleJEI module) {
			super(module);
		}

		@Override
		public void createSlots(List<SlotJEI> modularSlots) {
		}

		@Override
		public void addWidgets(List widgets) {
			widgets.add(new WidgetProgressBar(82, 24, 0, 0).setShowTooltip(false));
		}
	}*/

	@Override
	public int getColor() {
		return 0xB22222;
	}
}
