package de.nedelosk.forestmods.common.modules.machines;

import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import de.nedelosk.forestmods.client.gui.widgets.WidgetProgressBar;
import de.nedelosk.forestmods.client.render.modules.MachineRenderer;
import de.nedelosk.forestmods.common.modules.ModuleMachine;
import de.nedelosk.forestmods.common.modules.handlers.FluidFilterMachine;
import de.nedelosk.forestmods.common.modules.handlers.ModulePage;
import de.nedelosk.forestmods.common.modules.handlers.NEIPage;
import de.nedelosk.forestmods.library.inventory.IContainerBase;
import de.nedelosk.forestmods.library.modular.IModular;
import de.nedelosk.forestmods.library.modular.IModularTileEntity;
import de.nedelosk.forestmods.library.modular.renderer.IRenderState;
import de.nedelosk.forestmods.library.modular.renderer.ISimpleRenderer;
import de.nedelosk.forestmods.library.modules.IModuleColored;
import de.nedelosk.forestmods.library.modules.IModuleContainer;
import de.nedelosk.forestmods.library.modules.IModuleMachine;
import de.nedelosk.forestmods.library.modules.handlers.IModulePage;
import de.nedelosk.forestmods.library.modules.handlers.inventory.IModuleInventoryBuilder;
import de.nedelosk.forestmods.library.modules.handlers.inventory.slots.SlotModule;
import de.nedelosk.forestmods.library.modules.handlers.tank.EnumTankMode;
import de.nedelosk.forestmods.library.modules.handlers.tank.IModuleTankBuilder;
import de.nedelosk.forestmods.library.modules.integration.IModuleNEI;
import de.nedelosk.forestmods.library.modules.integration.INEIPage;
import de.nedelosk.forestmods.library.modules.integration.SlotNEI;
import de.nedelosk.forestmods.library.recipes.RecipeItem;
import net.minecraftforge.common.util.ForgeDirection;

public class ModuleBoiler extends ModuleMachine implements IModuleColored{

	public ModuleBoiler(IModular modular, IModuleContainer container, int speed) {
		super(modular, container, speed);
	}

	@Override
	public String getRecipeCategory() {
		return "Boiler";
	}

	@SideOnly(Side.CLIENT)
	@Override
	public ISimpleRenderer getRenderer(IRenderState state) {
		return new MachineRenderer(container);
	}

	@Override
	public RecipeItem[] getInputs() {
		if (inventory == null) {
			return null;
		}
		return inventory.getInputItems();
	}

	@Override
	protected IModulePage[] createPages() {
		IModulePage[] pages = new IModulePage[] { new BoilerPage(0, modular, this) };
		return pages;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public INEIPage createNEIPage(IModuleNEI stack) {
		return new BoilerNEIPage(stack);
	}

	public static class BoilerPage extends ModulePage<IModuleMachine> {

		public BoilerPage(int pageID, IModular modular, IModuleMachine module) {
			super(pageID, modular, module);
		}

		@Override
		public void createHandlers(IModuleInventoryBuilder invBuilder, IModuleTankBuilder tankBuilder) {
			invBuilder.setInventoryName("module.inventory.boiler.name");
			tankBuilder.initTank(0, 16000, ForgeDirection.EAST, EnumTankMode.INPUT, new FluidFilterMachine());
			tankBuilder.initTank(1, 16000, ForgeDirection.WEST, EnumTankMode.OUTPUT, new FluidFilterMachine());
		}

		@Override
		public void createSlots(IContainerBase<IModularTileEntity> container, List<SlotModule> modularSlots) {
		}

		@SideOnly(Side.CLIENT)
		@Override
		public void addWidgets(List widgets) {
			super.addWidgets(widgets);
			widgets.add(new WidgetProgressBar(82, 35, module.getBurnTime(), module.getBurnTimeTotal()));
		}
	}

	@SideOnly(Side.CLIENT)
	public static class BoilerNEIPage extends NEIPage {

		public BoilerNEIPage(IModuleNEI module) {
			super(module);
		}

		@Override
		public void createSlots(List<SlotNEI> modularSlots) {
		}

		@Override
		public void addWidgets(List widgets) {
			widgets.add(new WidgetProgressBar(82, 24, 0, 0).setShowTooltip(false));
		}
	}

	@Override
	public int getColor() {
		return 0xB22222;
	}
}
