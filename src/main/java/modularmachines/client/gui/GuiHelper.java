package modularmachines.client.gui;

import java.util.List;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.IFluidTank;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import modularmachines.api.energy.IEnergyBuffer;
import modularmachines.api.gui.IGuiHelper;
import modularmachines.api.gui.Widget;
import modularmachines.api.modular.IModularAssembler;
import modularmachines.api.modules.IModuleBurning;
import modularmachines.api.modules.IModulePage;
import modularmachines.api.modules.IModuleWorkerTime;
import modularmachines.api.modules.controller.IModuleControlled;
import modularmachines.api.modules.position.IStoragePosition;
import modularmachines.api.modules.state.IModuleState;
import modularmachines.api.modules.tools.IModuleModeMachine;
import modularmachines.client.gui.widgets.WidgetAssembleTab;
import modularmachines.client.gui.widgets.WidgetAssemblerTab;
import modularmachines.client.gui.widgets.WidgetBurning;
import modularmachines.client.gui.widgets.WidgetController;
import modularmachines.client.gui.widgets.WidgetEnergyBar;
import modularmachines.client.gui.widgets.WidgetEnergyField;
import modularmachines.client.gui.widgets.WidgetFluidTank;
import modularmachines.client.gui.widgets.WidgetMode;
import modularmachines.client.gui.widgets.WidgetModulePageTab;
import modularmachines.client.gui.widgets.WidgetModuleTab;
import modularmachines.client.gui.widgets.WidgetProgressBar;
import modularmachines.client.gui.widgets.WidgetRedstoneMode;

@SideOnly(Side.CLIENT)
public class GuiHelper implements IGuiHelper {

	@Override
	public Widget<IFluidTank> createFluidTank(int xPosition, int yPosition, IFluidTank provider) {
		return new WidgetFluidTank(xPosition, yPosition, provider);
	}

	@Override
	public Widget<IModuleState<IModuleWorkerTime>> createProgressBar(int xPosition, IModuleState<IModuleWorkerTime> provider) {
		return new WidgetProgressBar(xPosition, xPosition, provider);
	}

	@Override
	public Widget<IEnergyBuffer> createEnergyField(int xPosition, int yPosition, IEnergyBuffer provider) {
		return new WidgetEnergyField(xPosition, yPosition, provider);
	}

	@Override
	public Widget<IEnergyBuffer> createEnergyBar(int xPosition, int yPosition, IEnergyBuffer provider) {
		return new WidgetEnergyBar(xPosition, yPosition, provider);
	}

	@Override
	public Widget<IModuleState<IModuleBurning>> createBurning(int xPosition, int yPosition, IModuleState<IModuleBurning> provider) {
		return new WidgetBurning(xPosition, yPosition, provider);
	}

	@Override
	public Widget<IModuleState<IModuleControlled>> createRedstoneMode(int xPosition, int yPosition, IModuleState<IModuleControlled> provider) {
		return new WidgetRedstoneMode(xPosition, yPosition, provider);
	}

	@Override
	public Widget<IModuleState<IModuleControlled>> createController(int xPosition, int yPosition, IModuleState<IModuleControlled> provider, IModuleState state) {
		return new WidgetController(xPosition, yPosition, provider, state);
	}

	@Override
	public Widget<IModuleState<IModuleModeMachine>> createMode(int xPosition, int yPosition, IModuleState<IModuleModeMachine> provider) {
		return new WidgetMode(xPosition, yPosition, provider);
	}

	@Override
	public Widget<IModuleState> createModuleTab(int xPosition, int yPosition, IModuleState provider, List<IModuleState> modulesWithPages) {
		return new WidgetModuleTab(xPosition, yPosition, provider, modulesWithPages);
	}

	@Override
	public Widget<IModulePage> createModulePageTab(int xPosition, int yPosition, IModulePage provider) {
		return new WidgetModulePageTab(xPosition, yPosition, provider);
	}

	@Override
	public Widget<IModularAssembler> createAssemblerTab(int xPosition, int yPosition, IModularAssembler provider, IStoragePosition position, boolean right) {
		return new WidgetAssemblerTab(xPosition, yPosition, provider, position, right);
	}

	@Override
	public Widget<ItemStack> createAssembleTab(int xPosition, int yPosition, boolean isRight) {
		return new WidgetAssembleTab(xPosition, yPosition, isRight);
	}
}
