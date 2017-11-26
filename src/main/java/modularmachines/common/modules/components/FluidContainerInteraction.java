package modularmachines.common.modules.components;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.RayTraceResult;

import net.minecraftforge.fluids.FluidUtil;

import modularmachines.api.modules.components.IFluidHandlerComponent;
import modularmachines.api.modules.components.IInteractionComponent;

public class FluidContainerInteraction extends ModuleComponent implements IInteractionComponent {
	@Override
	public boolean onActivated(EntityPlayer player, EnumHand hand, RayTraceResult hit) {
		IFluidHandlerComponent component = provider.getComponent(IFluidHandlerComponent.class);
		return !player.isSneaking() && component != null && FluidUtil.interactWithFluidHandler(player, hand, component);
	}
}
