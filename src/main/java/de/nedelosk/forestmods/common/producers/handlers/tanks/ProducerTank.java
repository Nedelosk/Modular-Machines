package de.nedelosk.forestmods.common.producers.handlers.tanks;

import java.util.ArrayList;

import de.nedelosk.forestmods.api.modular.IModular;
import de.nedelosk.forestmods.api.producers.handlers.IContentFilter;
import de.nedelosk.forestmods.api.producers.handlers.tank.EnumTankMode;
import de.nedelosk.forestmods.api.producers.handlers.tank.IModuleTank;
import de.nedelosk.forestmods.api.producers.handlers.tank.ITankData;
import de.nedelosk.forestmods.api.producers.handlers.tank.TankData;
import de.nedelosk.forestmods.api.recipes.RecipeItem;
import de.nedelosk.forestmods.api.utils.ModuleStack;
import de.nedelosk.forestmods.common.producers.handlers.FilterWrapper;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTankInfo;

public class ProducerTank implements IModuleTank {

	protected final TankData[] tanks;
	protected final IModular modular;
	protected final ModuleStack moduleStack;
	private final FilterWrapper insertFilter;
	private final FilterWrapper extractFilter;

	public ProducerTank(TankData[] tanks, IModular modular, ModuleStack moduleStack, FilterWrapper insertFilter, FilterWrapper extractFilter) {
		this.tanks = tanks;
		this.modular = modular;
		this.moduleStack = moduleStack;
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
		for ( int index = 0; index < getInputs(); index++ ) {
			inputs[index] = new RecipeItem(index, getTank(index).getTank().getFluid().copy());
		}
		return inputs;
	}

	@Override
	public boolean canRemoveRecipeInputs(RecipeItem[] inputs) {
		if (inputs != null) {
			for ( RecipeItem recipeInput : inputs ) {
				if (recipeInput != null) {
					if (recipeInput.isFluid()) {
						FluidStack test = drain(ForgeDirection.UNKNOWN, recipeInput.fluid, false, true);
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
	public boolean canAddRecipeOutputs(RecipeItem[] outputs) {
		if (outputs != null) {
			for ( RecipeItem output : outputs ) {
				if (output != null) {
					if (output.isFluid()) {
						int test = fill(ForgeDirection.UNKNOWN, output.fluid, false, true);
						if (test != output.fluid.amount) {
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
	public void removeRecipeInputs(RecipeItem[] inputs) {
		if (inputs != null) {
			for ( RecipeItem recipeInput : inputs ) {
				if (recipeInput != null) {
					if (recipeInput.isFluid()) {
						drain(ForgeDirection.UNKNOWN, recipeInput.fluid, false, true);
					}
				}
			}
		}
	}

	@Override
	public void addRecipeOutputs(RecipeItem[] outputs) {
		if (outputs != null) {
			for ( RecipeItem item : outputs ) {
				if (item != null && item.isFluid()) {
					fill(ForgeDirection.UNKNOWN, item.fluid.copy(), true, true);
				}
			}
		}
	}

	@Override
	public int fill(ForgeDirection from, FluidStack resource, boolean doFill, boolean canFillOutput) {
		if (resource == null) {
			return 0;
		}
		for ( int i = 0; i < tanks.length; i++ ) {
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
				if (from == data.getDirection() || from == ForgeDirection.UNKNOWN || data.getDirection() == ForgeDirection.UNKNOWN) {
					if (insertFilter.isValid(i, resource, moduleStack, from)) {
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
	public FluidStack drain(ForgeDirection from, FluidStack resource, boolean doDrain, boolean canDrainInput) {
		if (resource == null) {
			return null;
		}
		for ( int i = 0; i < tanks.length; i++ ) {
			TankData data = tanks[i];
			if (data == null || data.getTank().isEmpty()) {
				continue;
			}
			if (resource.getFluid() != data.getTank().getFluid().getFluid()) {
				continue;
			}
			if (data.getMode() == EnumTankMode.OUTPUT || data.getMode() == EnumTankMode.INPUT && canDrainInput) {
				if (from == data.getDirection() || from == ForgeDirection.UNKNOWN || data.getDirection() == ForgeDirection.UNKNOWN) {
					if (extractFilter.isValid(i, resource, moduleStack, from)) {
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
	public FluidStack drain(ForgeDirection from, int maxDrain, boolean doDrain, boolean canDrainInput) {
		if (maxDrain < 0) {
			return null;
		}
		for ( int i = 0; i < tanks.length; i++ ) {
			TankData data = tanks[i];
			if (data == null || data.getTank().isEmpty()) {
				continue;
			}
			if (data.getTank().getFluid().amount < 0) {
				continue;
			}
			if (data.getMode() == EnumTankMode.OUTPUT || data.getMode() == EnumTankMode.INPUT && canDrainInput) {
				if (from == data.getDirection() || from == ForgeDirection.UNKNOWN || data.getDirection() == ForgeDirection.UNKNOWN) {
					FluidStack resource = new FluidStack(data.getTank().getFluid().getFluid(), maxDrain);
					if (extractFilter.isValid(i, resource, moduleStack, from)) {
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
	public boolean canFill(ForgeDirection from, Fluid fluid) {
		return true;
	}

	@Override
	public boolean canDrain(ForgeDirection from, Fluid fluid) {
		return true;
	}

	@Override
	public FluidTankInfo[] getTankInfo(ForgeDirection from) {
		ArrayList<FluidTankInfo> infos = new ArrayList<>();
		for ( TankData data : tanks ) {
			if (data != null) {
				infos.add(data.getTank().getInfo());
			}
		}
		return infos.toArray(new FluidTankInfo[infos.size()]);
	}

	@Override
	public IContentFilter<FluidStack> getInsertFilter() {
		return null;
	}

	@Override
	public IContentFilter<FluidStack> getExtractFilter() {
		return null;
	}

	@Override
	public int getInputs() {
		int inputs = 0;
		for ( ITankData tank : tanks ) {
			if (tank.getMode() == EnumTankMode.INPUT) {
				inputs++;
			}
		}
		return inputs;
	}

	@Override
	public int getOutputs() {
		int outputs = 0;
		for ( ITankData tank : tanks ) {
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
	public ModuleStack getModuleStack() {
		return moduleStack;
	}
}
