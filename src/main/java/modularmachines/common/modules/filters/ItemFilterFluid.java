package modularmachines.common.modules.filters;

import java.util.HashMap;
import java.util.Map;

import net.minecraft.item.ItemStack;

import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.IFluidTankProperties;

import modularmachines.api.modules.Module;
import modularmachines.common.inventory.IContentFilter;

public class ItemFilterFluid implements IContentFilter<ItemStack, Module> {
	
	public static final ItemFilterFluid INSTANCE = new ItemFilterFluid(true);
	private static final Map<Fluid, ItemFilterFluid> FILTERS = new HashMap<>();
	
	public static ItemFilterFluid get(Fluid fluidFilter) {
		if (!FILTERS.containsKey(fluidFilter)) {
			FILTERS.put(fluidFilter, new ItemFilterFluid(fluidFilter));
			return FILTERS.get(fluidFilter);
		}
		return FILTERS.get(fluidFilter);
	}
	
	private boolean empty;
	private Fluid fluidFilter;
	
	private ItemFilterFluid(boolean empty) {
		this(empty, null);
	}
	
	private ItemFilterFluid(Fluid fluidFilter) {
		this(false, fluidFilter);
	}
	
	private ItemFilterFluid(boolean empty, Fluid fluidFilter) {
		this.empty = empty;
		this.fluidFilter = fluidFilter;
	}
	
	@Override
	public boolean isValid(int index, ItemStack content, Module module) {
		if (content == null) {
			return false;
		}
		if (content.hasCapability(CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY, null)) {
			IFluidHandler handler = content.getCapability(CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY, null);
			if (handler.getTankProperties() == null || handler.getTankProperties().length <= 0) {
				return empty;
			}
			for (IFluidTankProperties property : handler.getTankProperties()) {
				if (property != null) {
					FluidStack tankContent = property.getContents();
					if (tankContent != null) {
						if ((fluidFilter == null || tankContent.getFluid() != null && tankContent.getFluid() == fluidFilter)) {
							continue;
						} else {
							return false;
						}
					}
				}
			}
			return true;
		}
		return false;
	}
}
