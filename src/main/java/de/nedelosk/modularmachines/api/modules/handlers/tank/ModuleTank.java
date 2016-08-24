package de.nedelosk.modularmachines.api.modules.handlers.tank;

import java.util.Collections;
import java.util.EnumMap;
import java.util.List;
import java.util.Map.Entry;

import com.google.common.collect.Lists;

import de.nedelosk.modularmachines.api.modular.handlers.IModularHandler;
import de.nedelosk.modularmachines.api.modular.handlers.IModularHandlerTileEntity;
import de.nedelosk.modularmachines.api.modules.IModule;
import de.nedelosk.modularmachines.api.modules.handlers.ContentInfo;
import de.nedelosk.modularmachines.api.modules.handlers.FilterWrapper;
import de.nedelosk.modularmachines.api.modules.handlers.IContentFilter;
import de.nedelosk.modularmachines.api.modules.state.IModuleState;
import de.nedelosk.modularmachines.api.recipes.RecipeItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.translation.I18n;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.IFluidTankProperties;

public class ModuleTank<M extends IModule> implements IModuleTank<M> {

	protected final FluidTankAdvanced[] tanks;
	protected final ContentInfo[] contentInfos;
	protected final EnumMap<EnumFacing, boolean[]> configurations = new EnumMap(EnumFacing.class);
	protected final IModuleState<M> state;
	private final FilterWrapper insertFilter;
	private final FilterWrapper extractFilter;

	public ModuleTank(FluidTankAdvanced[] tanks, ContentInfo[] contentInfos, IModuleState<M> state, FilterWrapper insertFilter, FilterWrapper extractFilter) {
		this.tanks = tanks;
		this.contentInfos = contentInfos;
		this.state = state;
		this.insertFilter = insertFilter;
		this.extractFilter = extractFilter;

		for(FluidTankAdvanced tank : tanks){
			tank.moduleTank = this;
		}

		for(EnumFacing facing : EnumFacing.values()){
			configurations.put(facing, new boolean[tanks.length]);
		}
	}

	@Override
	public FluidTankAdvanced getTank(int index) {
		return tanks[index];
	}

	@Override
	public FluidTankAdvanced[] getTanks() {
		return tanks;
	}

	@Override
	public RecipeItem[] getInputItems() {
		RecipeItem[] inputs = new RecipeItem[getInputs()];
		for(int index = 0; index < getInputs(); index++) {
			FluidStack input = getTank(index).getFluid();
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
						FluidStack test = drainInternal(recipeInput.fluid, false);
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
							int test = fillInternal(output.fluid, false);
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
						drainInternal(recipeInput.fluid, true);
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
						fillInternal(item.fluid.copy(), true);
					}
				}
			}
		}
	}

	@Override
	public int fillInternal(FluidStack resource, boolean doFill){
		if (resource == null || resource.amount <= 0) {
			return 0;
		}

		resource = resource.copy();

		int totalFillAmount = 0;
		for (FluidTankAdvanced handler : tanks){
			if(isInput(handler.index)){
				continue;
			}
			int fillAmount = handler.fillInternal(resource, doFill);
			totalFillAmount += fillAmount;
			resource.amount -= fillAmount;
			if (resource.amount <= 0) {
				break;
			}
		}
		return totalFillAmount;
	}

	@Override
	public FluidStack drainInternal(FluidStack resource, boolean doDrain){
		if (resource == null || resource.amount <= 0) {
			return null;
		}

		resource = resource.copy();

		FluidStack totalDrained = null;
		for (FluidTankAdvanced handler : tanks){
			if(!isInput(handler.index)){
				continue;
			}
			FluidStack drain = handler.drainInternal(resource, doDrain);
			if (drain != null){
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
	public FluidStack drainInternal(int maxDrain, boolean doDrain){
		if (maxDrain == 0) {
			return null;
		}
		FluidStack totalDrained = null;
		for (FluidTankAdvanced handler : tanks){
			if(!isInput(handler.index)){
				continue;
			}
			if (totalDrained == null){
				totalDrained = handler.drainInternal(maxDrain, doDrain);
				if (totalDrained != null){
					maxDrain -= totalDrained.amount;
				}
			}else{
				FluidStack copy = totalDrained.copy();
				copy.amount = maxDrain;
				FluidStack drain = handler.drainInternal(copy, doDrain);
				if (drain != null){
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
	public IFluidTankProperties[] getTankProperties()
	{
		List<IFluidTankProperties> properties = Lists.newArrayList();
		for (IFluidHandler handler : tanks){
			Collections.addAll(properties, handler.getTankProperties());
		}
		return properties.toArray(new IFluidTankProperties[properties.size()]);
	}

	@Override
	public int fill(FluidStack resource, boolean doFill){
		if (resource == null || resource.amount <= 0) {
			return 0;
		}

		resource = resource.copy();

		int totalFillAmount = 0;
		for (IFluidHandler handler : tanks){
			int fillAmount = handler.fill(resource, doFill);
			totalFillAmount += fillAmount;
			resource.amount -= fillAmount;
			if (resource.amount <= 0) {
				break;
			}
		}
		return totalFillAmount;
	}

	@Override
	public FluidStack drain(FluidStack resource, boolean doDrain){
		if (resource == null || resource.amount <= 0) {
			return null;
		}

		resource = resource.copy();

		FluidStack totalDrained = null;
		for (IFluidHandler handler : tanks){
			FluidStack drain = handler.drain(resource, doDrain);
			if (drain != null){
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
	public FluidStack drain(int maxDrain, boolean doDrain){
		if (maxDrain == 0) {
			return null;
		}
		FluidStack totalDrained = null;
		for (IFluidHandler handler : tanks){
			if (totalDrained == null){
				totalDrained = handler.drain(maxDrain, doDrain);
				if (totalDrained != null){
					maxDrain -= totalDrained.amount;
				}
			}else{
				FluidStack copy = totalDrained.copy();
				copy.amount = maxDrain;
				FluidStack drain = handler.drain(copy, doDrain);
				if (drain != null){
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
		for(FluidTankAdvanced tank : tanks) {
			if (contentInfos[tank.index].isInput) {
				inputs++;
			}
		}
		return inputs;
	}

	@Override
	public int getOutputs() {
		int outputs = 0;
		for(FluidTankAdvanced tank : tanks) {
			if (!contentInfos[tank.index].isInput) {
				outputs++;
			}
		}
		return outputs;
	}

	@Override
	public void deserializeNBT(NBTTagCompound nbt) {
		NBTTagList nbtTagTankList = nbt.getTagList("Tanks", 10);
		for(int i = 0;i < nbtTagTankList.tagCount();i++){
			NBTTagCompound tankTag = nbtTagTankList.getCompoundTagAt(i);
			int capacity = tankTag.getInteger("Capacity");
			int index = tankTag.getInteger("Index");
			tanks[index] = new FluidTankAdvanced(capacity, this, index, tankTag);
		}
		NBTTagList nbtTagConfigurationList = nbt.getTagList("Configurations", 10);
		for(int index = 0;index < nbtTagConfigurationList.tagCount();index++){
			NBTTagCompound entryTag = nbtTagConfigurationList.getCompoundTagAt(index);
			EnumFacing facing = EnumFacing.VALUES[index];
			boolean[] configurations = new boolean[this.configurations.get(facing).length];
			byte[] tankConfiguration = entryTag.getByteArray("Configurations");
			for(int i = 0;i < this.configurations.get(facing).length;i++){
				configurations[i] = tankConfiguration[i] == 1;
			}
			this.configurations.put(facing, configurations);
		}
	}

	@Override
	public NBTTagCompound serializeNBT() {
		NBTTagCompound nbt = new NBTTagCompound();
		NBTTagList nbtTagTankList = new NBTTagList();

		for(FluidTankAdvanced tank : tanks){
			NBTTagCompound tankTag = new NBTTagCompound();
			tank.writeToNBT(tankTag);
			tankTag.setInteger("Index", tank.index);
			tankTag.setInteger("Capacity", tank.getCapacity());;
			nbtTagTankList.appendTag(tankTag);
		}
		nbt.setTag("Tanks", nbtTagTankList);
		NBTTagList nbtTagConfigurationList = new NBTTagList();
		for(Entry<EnumFacing, boolean[]> entry : configurations.entrySet()){
			NBTTagCompound entryTag = new NBTTagCompound();
			entryTag.setInteger("Face", entry.getKey().ordinal());
			byte[] configurations = new byte[entry.getValue().length];
			for(int i = 0;i < entry.getValue().length;i++){
				configurations[i] = (byte) (entry.getValue()[i] ? 1 : 0);
			}
			entryTag.setByteArray("Configurations", configurations);
			nbtTagConfigurationList.appendTag(entryTag);
		}
		nbt.setTag("Configurations", nbtTagConfigurationList);
		return nbt;
	}

	@Override
	public void addToolTip(List<String> tooltip, ItemStack stack, IModuleState state) {
		tooltip.add(I18n.translateToLocal("mm.tooltip.handler.tanks"));
		for(FluidTankAdvanced tank : tanks){
			FluidStack fluidStack = tank.getFluid();
			tooltip.add(" " + TextFormatting.ITALIC + I18n.translateToLocal("mm.tooltip.handler.tank") + " " + tank.index);
			if(fluidStack != null){
				tooltip.add(" - " + I18n.translateToLocal("mm.tooltip.handler.tank.fluid") + fluidStack.getLocalizedName() + ", " + I18n.translateToLocal("mm.tooltip.handler.tank.amount") + fluidStack.amount);
			}else{
				tooltip.add(" - " + I18n.translateToLocal("mm.tooltip.handler.tank.empty"));
			}
		}
	}

	@Override
	public String getUID() {
		return "Tanks";
	}

	@Override
	public IModuleState<M> getModuleState() {
		return state;
	}

	@Override
	public void onChange() {
		state.getModule().sendModuleUpdate(state);
	}

	@Override
	public EnumMap<EnumFacing, boolean[]> getConfigurations() {
		return configurations;
	}

	@Override
	public ContentInfo[] getContentInfos() {
		return contentInfos;
	}

	@Override
	public ContentInfo getInfo(int index) {
		if(contentInfos.length <= index){
			return null;
		}
		return contentInfos[index];
	}

	@Override
	public List<ItemStack> getDrops() {
		return Collections.emptyList();
	}

	@Override
	public boolean isInput(int index) {
		if(contentInfos.length <= index){
			return false;
		}
		return contentInfos[index].isInput;
	}

	@Override
	public void cleanHandler(IModuleState state) {
		IModularHandler handler = state.getModular().getHandler();
		if(handler instanceof IModularHandlerTileEntity){
			IModularHandlerTileEntity tileHandler = (IModularHandlerTileEntity) handler;
			for(EnumFacing facing : EnumFacing.VALUES){
				TileEntity tile = tileHandler.getWorld().getTileEntity(tileHandler.getPos().offset(facing));
				if(tile != null && tile.hasCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, facing.getOpposite())){
					IFluidHandler fluidHandler = tile.getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, facing.getOpposite());
					for(FluidTankAdvanced tank : getTanks()){
						tank.drainInternal(fluidHandler.fill(tank.getFluid(), true), true);
					}
				}
			}
		}
	}

	@Override
	public boolean isEmpty() {
		for(FluidTankAdvanced tank : tanks){
			if(tank != null && !tank.isEmpty()){
				return false;
			}
		}
		return true;
	}
}
