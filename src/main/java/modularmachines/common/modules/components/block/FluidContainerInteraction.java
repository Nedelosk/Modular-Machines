package modularmachines.common.modules.components.block;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.RayTraceResult;

import net.minecraftforge.fluids.FluidUtil;

import modularmachines.api.modules.components.block.IInteractionComponent;
import modularmachines.api.modules.components.handlers.IFluidHandlerComponent;
import modularmachines.common.modules.components.ModuleComponent;

public class FluidContainerInteraction extends ModuleComponent implements IInteractionComponent {
	@Override
	public boolean onActivated(EntityPlayer player, EnumHand hand, RayTraceResult hit) {
		IFluidHandlerComponent component = provider.getComponent(IFluidHandlerComponent.class);
		return !player.isSneaking() && component != null && FluidUtil.interactWithFluidHandler(player, hand, component);
	}
}
