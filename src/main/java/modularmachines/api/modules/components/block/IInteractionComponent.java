package modularmachines.api.modules.components.block;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.RayTraceResult;

import modularmachines.api.modules.components.IModuleComponent;

public interface IInteractionComponent extends IModuleComponent {
	default boolean onActivated(EntityPlayer player, EnumHand hand, RayTraceResult hit) {
		return false;
	}
	
	default void onClick(EntityPlayer player, RayTraceResult hit) {
	}
}
