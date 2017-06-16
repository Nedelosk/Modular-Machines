package modularmachines.common.tanks;

import com.google.common.collect.Lists;

import java.util.Collections;
import java.util.List;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.NonNullList;

import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.IFluidTankProperties;

import modularmachines.api.modules.Module;
import modularmachines.api.recipes.IRecipe;
import modularmachines.api.recipes.IRecipeConsumer;
import modularmachines.api.recipes.RecipeItem;

public class FluidTankHandler implements IFluidHandler, IRecipeConsumer {

	protected final Module module;
	protected final NonNullList<FluidTankModule> containers;

	public FluidTankHandler(Module module) {
		this.module = module;
		this.containers = NonNullList.create();
	}

	public FluidTankModule getContainer(int index) {
		return containers.get(index);
	}

	public List<FluidTankModule> getContainers() {
		return containers;
	}
	
	public FluidTankModule addTank(boolean isInput, int capacity){
		FluidTankModule container = new FluidTankModule(capacity, containers.size(), isInput, module);
		containers.add(container);
		return container;
	}

	@Override
	public RecipeItem[] getInputs() {
		int count = getInputCount();
		RecipeItem[] inputs = new RecipeItem[count];
		for (int index = 0; index < count; index++) {
			FluidStack input = getContainer(index).getFluid();
			if (input != null) {
				input = input.copy();
			}
			inputs[index] = new RecipeItem(input);
		}
		return inputs;
	}
	
	@Override
	public boolean extractInputs(float chance, IRecipe recipe, boolean simulate) {
		if (recipe == null) {
			return false;
		}
		for (RecipeItem recipeInput : recipe.getInputItems()) {
			if (recipeInput != null) {
				if (recipeInput.isFluid()) {
					FluidStack recipeFluid = recipeInput.fluid;
					FluidStack drainedFluid = drain(recipeFluid, !simulate);
					if(drainedFluid == null || drainedFluid.amount < recipeFluid.amount){
						return false;
					}
				}
			}
		}
		return true;
	}
	
	@Override
	public boolean insertOutputs(float chance, IRecipe recipe, boolean simulate) {
		if (recipe == null) {
			return false;
		}
		for (RecipeItem recipeOutput : recipe.getOutputItems()) {
			if (recipeOutput != null) {
				if (recipeOutput.isFluid() && recipeOutput.canUseItem(chance)) {
					FluidStack recipeFluid = recipeOutput.fluid;
					int drainedFluid = fill(recipeFluid, !simulate);
					if(drainedFluid < recipeFluid.amount){
						return false;
					}
				}
			}
		}
		return true;
	}

	@Override
	public IFluidTankProperties[] getTankProperties() {
		List<IFluidTankProperties> properties = Lists.newArrayList();
		for (FluidTankModule container : containers) {
			Collections.addAll(properties, container.getTankProperties());
		}
		return properties.toArray(new IFluidTankProperties[properties.size()]);
	}
	
	public int fillInternal(FluidStack resource, boolean doFill) {
		if (resource == null || resource.amount <= 0) {
			return 0;
		}
		resource = resource.copy();
		int totalFillAmount = 0;
		for (FluidTankModule container : containers) {
			int fillAmount = container.fillInternal(resource, doFill);
			totalFillAmount += fillAmount;
			resource.amount -= fillAmount;
			if (resource.amount <= 0) {
				break;
			}
		}
		return totalFillAmount;
	}

	public FluidStack drainInternal(FluidStack resource, boolean doDrain) {
		if (resource == null || resource.amount <= 0) {
			return null;
		}
		resource = resource.copy();
		FluidStack totalDrained = null;
		for (FluidTankModule container : containers) {
			FluidStack drain = container.drainInternal(resource, doDrain);
			if (drain != null) {
				if (totalDrained == null) {
					totalDrained = drain;
				} else {
					totalDrained.amount += drain.amount;
				}
				resource.amount -= drain.amount;
				if (resource.amount <= 0) {
					break;
				}
			}
		}
		return totalDrained;
	}

	public FluidStack drainInternal(int maxDrain, boolean doDrain) {
		if (maxDrain == 0) {
			return null;
		}
		FluidStack totalDrained = null;
		for (FluidTankModule container : containers) {
			if (totalDrained == null) {
				totalDrained = container.drainInternal(maxDrain, doDrain);
				if (totalDrained != null) {
					maxDrain -= totalDrained.amount;
				}
			} else {
				FluidStack copy = totalDrained.copy();
				copy.amount = maxDrain;
				FluidStack drain = container.drainInternal(copy, doDrain);
				if (drain != null) {
					totalDrained.amount += drain.amount;
					maxDrain -= drain.amount;
				}
			}
			if (maxDrain <= 0) {
				break;
			}
		}
		return totalDrained;
	}

	@Override
	public int fill(FluidStack resource, boolean doFill) {
		if (resource == null || resource.amount <= 0) {
			return 0;
		}
		resource = resource.copy();
		int totalFillAmount = 0;
		for (FluidTankModule container : containers) {
			int fillAmount = container.fill(resource, doFill);
			totalFillAmount += fillAmount;
			resource.amount -= fillAmount;
			if (resource.amount <= 0) {
				break;
			}
		}
		return totalFillAmount;
	}

	@Override
	public FluidStack drain(FluidStack resource, boolean doDrain) {
		if (resource == null || resource.amount <= 0) {
			return null;
		}
		resource = resource.copy();
		FluidStack totalDrained = null;
		for (FluidTankModule container : containers) {
			FluidStack drain = container.drain(resource, doDrain);
			if (drain != null) {
				if (totalDrained == null) {
					totalDrained = drain;
				} else {
					totalDrained.amount += drain.amount;
				}
				resource.amount -= drain.amount;
				if (resource.amount <= 0) {
					break;
				}
			}
		}
		return totalDrained;
	}

	@Override
	public FluidStack drain(int maxDrain, boolean doDrain) {
		if (maxDrain == 0) {
			return null;
		}
		FluidStack totalDrained = null;
		for (FluidTankModule container : containers) {
			if (totalDrained == null) {
				totalDrained = container.drain(maxDrain, doDrain);
				if (totalDrained != null) {
					maxDrain -= totalDrained.amount;
				}
			} else {
				FluidStack copy = totalDrained.copy();
				copy.amount = maxDrain;
				FluidStack drain = container.drain(copy, doDrain);
				if (drain != null) {
					totalDrained.amount += drain.amount;
					maxDrain -= drain.amount;
				}
			}
			if (maxDrain <= 0) {
				break;
			}
		}
		return totalDrained;
	}

	@Override
	public int getInputCount() {
		int inputs = 0;
		for (FluidTankModule container : containers) {
			if (container.isInput()) {
				inputs++;
			}
		}
		return inputs;
	}

	@Override
	public int getOutputCount() {
		int outputs = 0;
		for (FluidTankModule container : containers) {
			if (!container.isInput()) {
				outputs++;
			}
		}
		return outputs;
	}

	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		NBTTagList nbtTagTankList = new NBTTagList();
		for (FluidTankModule container : containers) {
			NBTTagCompound tagCompound = new NBTTagCompound();
			tagCompound.setInteger("Index", container.index);
			container.writeToNBT(tagCompound);
			nbtTagTankList.appendTag(tagCompound);
		}
		compound.setTag("Tanks", nbtTagTankList);
		return compound;
	}

	public void readFromNBT(NBTTagCompound compound) {
		NBTTagList tankList = compound.getTagList("Tanks", 10);
		for (int i = 0; i < tankList.tagCount(); i++) {
			NBTTagCompound tagCompound = tankList.getCompoundTagAt(i);
			int index = tagCompound.getInteger("Index");
			containers.get(index).readFromNBT(tagCompound);
		}
		onLoad();
	}
	
	protected void onLoad() {
	}

	/*@Override
	public void addToolTip(List<String> tooltip, ItemStack stack, IModuleState<M> state) {
		tooltip.add(I18n.translateToLocal("mm.tooltip.handler.tanks"));
		for (FluidTankModule tank : tanks) {
			FluidStack fluidStack = tank.getFluid();
			if (fluidStack != null) {
				tooltip.add(" " + TextFormatting.ITALIC + I18n.translateToLocal("mm.tooltip.handler.tank") + " " + tank.index);
				tooltip.add(" - " + I18n.translateToLocal("mm.tooltip.handler.tank.fluid") + fluidStack.getLocalizedName() + ", " + I18n.translateToLocal("mm.tooltip.handler.tank.amount") + fluidStack.amount);
			}
		}
	}*/

	public boolean isInput(int index) {
		if (containers.size() <= index) {
			return false;
		}
		return containers.get(index).isInput;
	}

	/*@Override
	public void cleanHandler(IModuleState state) {
		IModularHandler handler = state.getModular().getHandler();
		if (handler instanceof IModularHandlerTileEntity) {
			IModularHandlerTileEntity tileHandler = (IModularHandlerTileEntity) handler;
			List<IFluidHandler> handlers = new ArrayList<>();
			for (IModuleState moduleState : state.getModuleHandler().getModules()) {
				if (moduleState.getContentHandler(IFluidHandler.class) != null) {
					handlers.add(moduleState.getContentHandler(IFluidHandler.class));
				}
				for (IModulePage page : (List<IModulePage>) moduleState.getPages()) {
					if (page.getContentHandler(IFluidHandler.class) != null) {
						handlers.add(page.getContentHandler(IFluidHandler.class));
					}
				}
			}
			for (EnumFacing facing : EnumFacing.VALUES) {
				TileEntity tile = tileHandler.getWorld().getTileEntity(tileHandler.getPos().offset(facing));
				if (tile != null && tile.hasCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, facing.getOpposite())) {
					handlers.add(tile.getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, facing.getOpposite()));
				}
			}
			for (IFluidHandler fluidHandler : handlers) {
				if (!isEmpty()) {
					for (FluidTankModule tank : getContainers()) {
						tank.drainInternal(fluidHandler.fill(tank.getFluid(), true), true);
					}
				}
			}
		}
	}

	@Override
	public boolean isCleanable() {
		return true;
	}

	@Override
	public boolean isEmpty() {
		for (FluidTankModule tank : tanks) {
			if (tank != null && !tank.isEmpty()) {
				return false;
			}
		}
		return true;
	}*/
}
