package modularmachines.api.gui;

import java.util.List;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.IFluidTank;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import modularmachines.api.energy.IEnergyBuffer;
import modularmachines.api.modular.IModularAssembler;
import modularmachines.api.modules.IModuleBurning;
import modularmachines.api.modules.IModulePage;
import modularmachines.api.modules.IModuleWorkerTime;
import modularmachines.api.modules.controller.IModuleControlled;
import modularmachines.api.modules.position.IStoragePosition;
import modularmachines.api.modules.state.IModuleState;
import modularmachines.api.modules.tools.IModuleModeMachine;

@SideOnly(Side.CLIENT)
public interface IGuiHelper {

	Widget<IFluidTank> createFluidTank(int xPosition, int yPosition, IFluidTank provider);

	Widget<IModuleState<IModuleWorkerTime>> createProgressBar(int xPosition, IModuleState<IModuleWorkerTime> provider);

	Widget<IEnergyBuffer> createEnergyField(int xPosition, int yPosition, IEnergyBuffer provider);

	Widget<IEnergyBuffer> createEnergyBar(int xPosition, int yPosition, IEnergyBuffer provider);

	Widget<IModuleState<IModuleBurning>> createBurning(int xPosition, int yPosition, IModuleState<IModuleBurning> provider);

	Widget<IModuleState<IModuleControlled>> createRedstoneMode(int xPosition, int yPosition, IModuleState<IModuleControlled> provider);

	Widget<IModuleState<IModuleControlled>> createController(int xPosition, int yPosition, IModuleState<IModuleControlled> provider, IModuleState state);

	Widget<IModuleState<IModuleModeMachine>> createMode(int xPosition, int yPosition, IModuleState<IModuleModeMachine> provider);

	/* MODULAR */
	Widget<IModuleState> createModuleTab(int xPosition, int yPosition, IModuleState provider, List<IModuleState> modulesWithPages);

	Widget<IModulePage> createModulePageTab(int xPosition, int yPosition, IModulePage provider);

	/* ASSEMBLER */
	Widget<IModularAssembler> createAssemblerTab(int xPosition, int yPosition, IModularAssembler provider, IStoragePosition position, boolean right);

	Widget<ItemStack> createAssembleTab(int xPosition, int yPosition, boolean isRight);
}
