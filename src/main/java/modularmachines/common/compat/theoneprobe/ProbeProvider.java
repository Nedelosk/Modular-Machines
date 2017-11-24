package modularmachines.common.compat.theoneprobe;

import javax.annotation.Nullable;
import java.util.function.Function;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

import net.minecraftforge.fml.common.Optional;

import modularmachines.common.core.Constants;

import mcjty.theoneprobe.api.IProbeHitData;
import mcjty.theoneprobe.api.IProbeInfo;
import mcjty.theoneprobe.api.IProbeInfoProvider;
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
	}
}
