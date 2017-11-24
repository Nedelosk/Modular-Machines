package modularmachines.common.modules.components;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;

import modularmachines.api.IGuiProvider;
import modularmachines.api.ILocatable;
import modularmachines.api.modules.components.IInteractionComponent;
import modularmachines.common.ModularMachines;

public abstract class GuiComponent extends ModuleComponent implements IInteractionComponent, IGuiProvider {
	@Override
	public boolean onActivated(EntityPlayer player, EnumHand hand, RayTraceResult hit) {
		if (player.world.isRemote) {
			return true;
		}
		BlockPos pos = provider.getContainer().getLocatable().getCoordinates();
		player.openGui(ModularMachines.instance, provider.getIndex(), player.world, pos.getX(), pos.getY(), pos.getZ());
		return false;
	}
	
	@Override
	public ILocatable getLocatable() {
		return provider.getContainer().getLocatable();
	}
}
