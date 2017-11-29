package modularmachines.common.compat.theoneprobe;

import javax.annotation.Nullable;
import java.util.function.Function;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import net.minecraftforge.fml.common.Optional;

import modularmachines.api.modules.container.IHeatSource;
import modularmachines.api.modules.container.IModuleContainer;
import modularmachines.common.core.Constants;
import modularmachines.common.energy.Heat;
import modularmachines.common.modules.ModuleCapabilities;

import mcjty.theoneprobe.api.IProbeHitData;
import mcjty.theoneprobe.api.IProbeInfo;
import mcjty.theoneprobe.api.IProbeInfoProvider;
import mcjty.theoneprobe.api.IProgressStyle;
import mcjty.theoneprobe.api.ITheOneProbe;
import mcjty.theoneprobe.api.ProbeMode;


@Optional.InterfaceList({@Optional.Interface(iface = "mcjty.theoneprobe.api.IProbeInfoProvide", modid = "theoneprobe"), @Optional.Interface(iface = "mcjty.theoneprobe.api.IProbeConfigProvider", modid = "theoneprobe")})
public class ProbeProvider implements IProbeInfoProvider, Function<ITheOneProbe, Void> {
	@Nullable
	private ITheOneProbe probe;
	
	@Override
	public Void apply(ITheOneProbe probe) {
		this.probe = probe;
		IProbeInfoProvider provider = new ProbeProvider();
		probe.registerProvider(provider);
		return null;
	}
	
	@Override
	public String getID() {
		return Constants.MOD_ID + ":modules";
	}
	
	@Override
	public void addProbeInfo(ProbeMode mode, IProbeInfo probeInfo, EntityPlayer player, World world, IBlockState blockState, IProbeHitData data) {
		EnumFacing facing = data.getSideHit().getOpposite();
		BlockPos pos = data.getPos();
		TileEntity tileEntity = world.getTileEntity(pos);
		if (tileEntity == null || !tileEntity.hasCapability(ModuleCapabilities.MODULE_CONTAINER, facing)) {
			return;
		}
		IModuleContainer container = tileEntity.getCapability(ModuleCapabilities.MODULE_CONTAINER, facing);
		if (container == null) {
			return;
		}
		IProbeInfo containerInfo = probeInfo.vertical();
		IHeatSource heatSource = container.getComponent(IHeatSource.class);
		if (heatSource != null && heatSource.getHeat() != Heat.COLD_TEMP) {
			IProgressStyle style = containerInfo.defaultProgressStyle().filledColor(0xff990000).alternateFilledColor(0xff550000).borderColor(0).prefix("Heat ");
			containerInfo.progress((int) heatSource.getHeat(), (int) heatSource.getMaxHeat(), style);
		}
	}
}
