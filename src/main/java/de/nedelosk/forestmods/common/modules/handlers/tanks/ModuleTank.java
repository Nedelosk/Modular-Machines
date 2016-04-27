package de.nedelosk.forestmods.common.modules.handlers.tanks;

import java.util.ArrayList;

import de.nedelosk.forestmods.common.modules.handlers.FilterWrapper;
import de.nedelosk.forestmods.library.modular.IModular;
import de.nedelosk.forestmods.library.modules.IModule;
import de.nedelosk.forestmods.library.modules.ModuleManager;
import de.nedelosk.forestmods.library.modules.handlers.IContentFilter;
import de.nedelosk.forestmods.library.modules.handlers.tank.EnumTankMode;
import de.nedelosk.forestmods.library.modules.handlers.tank.IModuleTank;
import de.nedelosk.forestmods.library.modules.handlers.tank.ITankData;
import de.nedelosk.forestmods.library.modules.handlers.tank.TankData;
import de.nedelosk.forestmods.library.recipes.RecipeItem;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTankInfo;

public class ModuleTank<M extends IModule> implements IModuleTank<M> {

	protected final TankData[] tanks;
	protected final IModular modular;
	protected final M module;
	private final FilterWrapper insertFilter;
	private final FilterWrapper extractFilter;

	public ModuleTank(TankData[] tanks, IModular modular, M module, FilterWrapper insertFilter, FilterWrapper extractFilter) {
		this.tanks = tanks;
		this.modular = modular;
		this.module = module;
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
	public boolean canAddRecipeOutputs(int chance, RecipeItem[] outputs) {
		if (outputs != null) {
			for(RecipeItem output : outputs) {
				if (output != null) {
					if (output.isFluid()) {
						if(output.chance == -1 || chance <= output.chance){
							int test = fill(ForgeDirection.UNKNOWN, output.fluid, false, true);
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
						drain(ForgeDirection.UNKNOWN, recipeInput.fluid, false, true);
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
						fill(ForgeDirection.UNKNOWN, item.fluid.copy(), true, true);
					}
				}
			}
		}
	}

	@Override
	public int fill(ForgeDirection from, FluidStack resource, boolean doFill, boolean canFillOutput) {
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
				if (from == data.getDirection() || from == ForgeDirection.UNKNOWN || data.getDirection() == ForgeDirection.UNKNOWN) {
					if (insertFilter.isValid(i, resource, module, from)) {
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
		for(int i = 0; i < tanks.length; i++) {
			TankData data = tanks[i];
			if (data == null || data.getTank().isEmpty()) {
				continue;
			}
			if (resource.getFluid() != data.getTank().getFluid().getFluid()) {
				continue;
			}
			if (data.getMode() == EnumTankMode.OUTPUT || data.getMode() == EnumTankMode.INPUT && canDrainInput) {
				if (from == data.getDirection() || from == ForgeDirection.UNKNOWN || data.getDirection() == ForgeDirection.UNKNOWN) {
					if (extractFilter.isValid(i, resource, module, from)) {
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
		for(int i = 0; i < tanks.length; i++) {
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
					if (extractFilter.isValid(i, resource, module, from)) {
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
	public IModule getModule() {
		return module;
	}

	@Override
	public String getType() {
		return ModuleManager.tankType;
	}
}
