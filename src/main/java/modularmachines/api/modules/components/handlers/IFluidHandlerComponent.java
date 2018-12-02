package modularmachines.api.modules.components.handlers;

import javax.annotation.Nullable;
import java.util.function.Predicate;

import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.IFluidTank;
import net.minecraftforge.fluids.capability.IFluidHandler;

import modularmachines.api.components.INetworkComponent;
import modularmachines.api.ingredients.IIngredientConsumer;
import modularmachines.api.ingredients.IIngredientHolder;
import modularmachines.api.ingredients.IIngredientProducer;
import modularmachines.api.modules.IModule;

/**
 * This component can be used to add a fluid handler to the module
 * <p>
 * {@link modularmachines.api.modules.components.IModuleComponentFactory#addFluidHandler(IModule)} can be
 * used to add this component to a module.
 */
public interface IFluidHandlerComponent extends IHandlerComponent, IFluidHandler,
		INetworkComponent, IIngredientConsumer<FluidStack>, IIngredientProducer<FluidStack>, IIngredientHolder<FluidStack> {
	
	default ITank addTank(int capacity) {
		return addTank(capacity, false);
	}
	
	ITank addTank(int capacity, boolean isOutput);
	
	int fillInternal(FluidStack resource, boolean doFill);
	
	@Nullable
	FluidStack drainInternal(FluidStack resource, boolean doDrain);
	
	@Nullable
	FluidStack drainInternal(int maxDrain, boolean doDrain);
	
	@Nullable
	ITank getTank(int index);
	
	@Override
	ISaveHandler<IFluidHandlerComponent> getSaveHandler();
	
	interface ITank extends IFluidTank {
		int getIndex();
		
		void setFluid(@Nullable FluidStack fluidStack);
		
		void setFilter(Predicate<FluidStack> filter);
		
		Predicate<FluidStack> getFilter();
		
		int fillInternal(FluidStack resource, boolean doFill);
		
		@Nullable
		FluidStack drainInternal(int maxDrain, boolean doDrain);
		
		@Nullable
		FluidStack drainInternal(FluidStack resource, boolean doDrain);
	}
}
