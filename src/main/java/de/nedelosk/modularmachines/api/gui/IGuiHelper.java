package de.nedelosk.modularmachines.api.gui;

import java.util.List;

import de.nedelosk.modularmachines.api.energy.IEnergyBuffer;
import de.nedelosk.modularmachines.api.modular.IModularAssembler;
import de.nedelosk.modularmachines.api.modules.IModuleBurning;
import de.nedelosk.modularmachines.api.modules.IModuleWorking;
import de.nedelosk.modularmachines.api.modules.controller.IModuleControlled;
import de.nedelosk.modularmachines.api.modules.handlers.IModulePage;
import de.nedelosk.modularmachines.api.modules.position.IStoragePosition;
import de.nedelosk.modularmachines.api.modules.state.IModuleState;
import de.nedelosk.modularmachines.api.modules.tools.IModuleModeMachine;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.IFluidTank;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public interface IGuiHelper {

	Widget<IFluidTank> createFluidTank(int xPosition, int yPosition, IFluidTank provider);

	Widget<IModuleState<IModuleWorking>> createProgressBar(int xPosition, IModuleState<IModuleWorking> provider);

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

	Widget<ItemStack> createAssembleTab(int xPosition, int yPosition);
}
