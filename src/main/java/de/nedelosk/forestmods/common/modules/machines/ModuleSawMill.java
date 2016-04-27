package de.nedelosk.forestmods.common.modules.machines;

import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import de.nedelosk.forestmods.client.gui.widgets.WidgetProgressBar;
import de.nedelosk.forestmods.client.render.modules.MachineRenderer;
import de.nedelosk.forestmods.common.modules.ModuleMachine;
import de.nedelosk.forestmods.common.modules.handlers.ItemFilterMachine;
import de.nedelosk.forestmods.common.modules.handlers.ModulePage;
import de.nedelosk.forestmods.common.modules.handlers.NEIPage;
import de.nedelosk.forestmods.common.modules.handlers.OutputAllFilter;
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
import de.nedelosk.forestmods.library.modules.handlers.tank.IModuleTankBuilder;
import de.nedelosk.forestmods.library.modules.integration.IModuleNEI;
import de.nedelosk.forestmods.library.modules.integration.INEIPage;
import de.nedelosk.forestmods.library.modules.integration.SlotNEI;
import de.nedelosk.forestmods.library.recipes.RecipeItem;

public class ModuleSawMill extends ModuleMachine implements IModuleColored{

	public ModuleSawMill(IModular modular, IModuleContainer container, int speed) {
		super(modular, container, speed);
	}

	@Override
	public RecipeItem[] getInputs() {
		return getInventory().getInputItems();
	}

	@Override
	public String getRecipeCategory() {
		return "SawMill";
	}

	@Override
	public int getColor() {
		return 0xA65005;
	}

	@Override
	protected IModulePage[] createPages() {
		return new IModulePage[]{new SawMillPage(0, modular, this)};
	}

	@SideOnly(Side.CLIENT)
	@Override
	public INEIPage createNEIPage(IModuleNEI module) {
		return new SawMillNEIPage(module);
	}

	public static class SawMillPage extends ModulePage<IModuleMachine> {

		public SawMillPage(int pageID, IModular modular, IModuleMachine module) {
			super(pageID, modular, module);
		}

		@Override
		public void createHandlers(IModuleInventoryBuilder invBuilder, IModuleTankBuilder tankBuilder) {
			invBuilder.setInventoryName("module.inventory.sawmill.name");
			invBuilder.initSlot(0, true, new ItemFilterMachine());
			invBuilder.initSlot(1, false, new OutputAllFilter());
			invBuilder.initSlot(2, false, new OutputAllFilter());
		}

		@Override
		public void createSlots(IContainerBase<IModularTileEntity> container, List<SlotModule> modularSlots) {
			modularSlots.add(new SlotModule(module, 0, 56, 35));
			modularSlots.add(new SlotModule(module, 1, 116, 35));
			modularSlots.add(new SlotModule(module, 2, 134, 35));
		}

		@SideOnly(Side.CLIENT)
		@Override
		public void addWidgets(List widgets) {
			super.addWidgets(widgets);
			widgets.add(new WidgetProgressBar(82, 36, module.getBurnTime(), module.getBurnTimeTotal()));
		}
	}

	@SideOnly(Side.CLIENT)
	@Override
	public ISimpleRenderer getRenderer(IRenderState state) {
		return new MachineRenderer(container);
	}

	@SideOnly(Side.CLIENT)
	public static class SawMillNEIPage extends NEIPage {

		public SawMillNEIPage(IModuleNEI module) {
			super(module);
		}

		@Override
		public void createSlots(List<SlotNEI> modularSlots) {
			modularSlots.add(new SlotNEI(56, 24, true));
			modularSlots.add(new SlotNEI(116, 24, false));
			modularSlots.add(new SlotNEI(134, 24, false));
		}

		@Override
		public void addWidgets(List widgets) {
			widgets.add(new WidgetProgressBar(82, 24, 0, 0).setShowTooltip(false));
		}
	}
}
