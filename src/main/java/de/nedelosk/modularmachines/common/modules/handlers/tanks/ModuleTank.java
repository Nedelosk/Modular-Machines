package de.nedelosk.modularmachines.common.modules.handlers.tanks;

import java.util.ArrayList;

import de.nedelosk.modularmachines.api.modular.IModular;
import de.nedelosk.modularmachines.api.modules.IModule;
import de.nedelosk.modularmachines.api.modules.handlers.IContentFilter;
import de.nedelosk.modularmachines.api.modules.handlers.tank.EnumTankMode;
import de.nedelosk.modularmachines.api.modules.handlers.tank.IModuleTank;
import de.nedelosk.modularmachines.api.modules.handlers.tank.ITankData;
import de.nedelosk.modularmachines.api.modules.handlers.tank.TankData;
import de.nedelosk.modularmachines.api.modules.state.IModuleState;
import de.nedelosk.modularmachines.api.recipes.RecipeItem;
import de.nedelosk.modularmachines.common.modules.handlers.FilterWrapper;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTankInfo;

public class ModuleTank<M extends IModule> implements IModuleTank<M> {

	protected final TankData[] tanks;
	protected final IModular modular;
	protected final IModuleState<M> state;
	private final FilterWrapper insertFilter;
	private final FilterWrapper extractFilter;

	public ModuleTank(TankData[] tanks, IModular modular, IModuleState<M> state, FilterWrapper insertFilter, FilterWrapper extractFilter) {
		this.tanks = tanks;
		this.modular = modular;
		this.state = state;
		this.insertFilter = insertFilter;
		this.extractFilter = extractFilter;
	}

	@Override
	public IModular getModular() {
		return modular;
	}

	@Override
	public ITankData getTank(int index) {
		return tanks[index];
	}

	@Override
	public ITankData[] getTanks() {
		return tanks;
	}

	@Override
	public RecipeItem[] getInputItems() {
		RecipeItem[] inputs = new RecipeItem[getInputs()];
		for(int index = 0; index < getInputs(); index++) {
			FluidStack input = getTank(index).getTank().getFluid();
			if (input != null) {
				input = input.copy();
			}
			inputs[index] = new RecipeItem(index, input);
		}
		return inputs;
	}

	@Override
	public boolean canRemoveRecipeInputs(int chance, RecipeItem[] inputs) {
		if (inputs != null) {
			for(RecipeItem recipeInput : inputs) {
				if (recipeInput != null) {
					if (recipeInput.isFluid()) {
						FluidStack test = drain(null, recipeInput.fluid, false, true);
						if (test == null || test.amount != recipeInput.fluid.amount) {
							return false;
						}
					}
					continue;
				} else {
					return false;
				}
			}
			return true;
		}
		return false;
	}

	@Override
	public boolean canAddRecipeOutputs(int chance, RecipeItem[] outputs) {
		if (outputs != null) {
			for(RecipeItem output : outputs) {
				if (output != null) {
					if (output.isFluid()) {
						if(output.chance == -1 || chance <= output.chance){
							int test = fill(null, output.fluid, false, true);
							if (test != output.fluid.amount) {
								return false;
							}
						}
					}
					continue;
				} else {
					return false;
				}
			}
			return true;
		}
		return false;
	}

	@Override
	public void removeRecipeInputs(int chance, RecipeItem[] inputs) {
		if (inputs != null) {
			for(RecipeItem recipeInput : inputs) {
				if (recipeInput != null) {
					if (recipeInput.isFluid()) {
						drain(null, recipeInput.fluid, false, true);
					}
				}
			}
		}
	}

	@Override
	public void addRecipeOutputs(int chance, RecipeItem[] outputs) {
		if (outputs != null) {
			for(RecipeItem item : outputs) {
				if (item != null && item.isFluid()) {
					if(item.chance == -1 || chance <= item.chance){
						fill(null, item.fluid.copy(), true, true);
					}
				}
			}
		}
	}

	@Override
	public int fill(EnumFacing from, FluidStack resource, boolean doFill, boolean canFillOutput) {
		if (resource == null) {
			return 0;
		}
		for(int i = 0; i < tanks.length; i++) {
			TankData data = tanks[i];
			if (data == null || data.getTank().isFull()) {
				continue;
			}
			if (!data.getTank().isEmpty()) {
				if (resource.getFluid() != data.getTank().getFluid().getFluid()) {
					continue;
				}
			}
			if (data.getMode() == EnumTankMode.OUTPUT && canFillOutput || data.getMode() == EnumTankMode.INPUT) {
				if (from == data.getFacing() || from == null || data.getFacing() == null) {
					if (insertFilter.isValid(i, resource, state)) {
						return data.getTank().fill(resource, doFill);
					}
				} else {
					continue;
				}
			} else {
				continue;
			}
		}
		return 0;
	}

	@Override
	public FluidStack drain(EnumFacing from, FluidStack resource, boolean doDrain, boolean canDrainInput) {
		if (resource == null) {
			return null;
		}
		for(int i = 0; i < tanks.length; i++) {
			TankData data = tanks[i];
			if (data == null || data.getTank().isEmpty()) {
				continue;
			}
			if (resource.getFluid() != data.getTank().getFluid().getFluid()) {
				continue;
			}
			if (data.getMode() == EnumTankMode.OUTPUT || data.getMode() == EnumTankMode.INPUT && canDrainInput) {
				if (from == data.getFacing() || from == null || data.getFacing() == null) {
					if (extractFilter.isValid(i, resource, state)) {
						return data.getTank().drain(resource, doDrain);
					}
				} else {
					continue;
				}
			} else {
				continue;
			}
		}
		return null;
	}

	@Override
	public FluidStack drain(EnumFacing from, int maxDrain, boolean doDrain, boolean canDrainInput) {
		if (maxDrain < 0) {
			return null;
		}
		for(int i = 0; i < tanks.length; i++) {
			TankData data = tanks[i];
			if (data == null || data.getTank().isEmpty()) {
				continue;
			}
			if (data.getTank().getFluid().amount < 0) {
				continue;
			}
			if (data.getMode() == EnumTankMode.OUTPUT || data.getMode() == EnumTankMode.INPUT && canDrainInput) {
				if (from == data.getFacing() || from == null || data.getFacing() == null) {
					FluidStack resource = new FluidStack(data.getTank().getFluid().getFluid(), maxDrain);
					if (extractFilter.isValid(i, resource, state)) {
						return data.getTank().drain(maxDrain, doDrain);
					}
				} else {
					continue;
				}
			} else {
				continue;
			}
		}
		return null;
	}

	@Override
	public boolean canFill(EnumFacing from, Fluid fluid) {
		return true;
	}

	@Override
	public boolean canDrain(EnumFacing from, Fluid fluid) {
		return true;
	}

	@Override
	public FluidTankInfo[] getTankInfo(EnumFacing from) {
		ArrayList<FluidTankInfo> infos = new ArrayList<>();
		for(TankData data : tanks) {
			if (data != null) {
				infos.add(data.getTank().getInfo());
			}
		}
		return infos.toArray(new FluidTankInfo[infos.size()]);
	}

	@Override
	public IContentFilter<FluidStack, M> getInsertFilter() {
		return insertFilter;
	}

	@Override
	public IContentFilter<FluidStack, M> getExtractFilter() {
		return extractFilter;
	}

	@Override
	public int getInputs() {
		int inputs = 0;
		for(ITankData tank : tanks) {
			if (tank.getMode() == EnumTankMode.INPUT) {
				inputs++;
			}
		}
		return inputs;
	}

	@Override
	public int getOutputs() {
		int outputs = 0;
		for(ITankData tank : tanks) {
			if (tank.getMode() == EnumTankMode.OUTPUT) {
				outputs++;
			}
		}
		return outputs;
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt) {
	}

	@Override
	public IModuleState<M> getModuleState() {
		return state;
	}

	@Override
	public Class<FluidStack> getContentClass() {
		return FluidStack.class;
	}
}
