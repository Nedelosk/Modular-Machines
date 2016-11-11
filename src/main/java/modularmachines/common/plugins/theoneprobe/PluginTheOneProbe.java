package modularmachines.common.plugins.theoneprobe;

import com.google.common.base.Function;

import mcjty.theoneprobe.api.ElementAlignment;
import mcjty.theoneprobe.api.ILayoutStyle;
import mcjty.theoneprobe.api.IProbeConfig;
import mcjty.theoneprobe.api.IProbeConfigProvider;
import mcjty.theoneprobe.api.IProbeHitData;
import mcjty.theoneprobe.api.IProbeHitEntityData;
import mcjty.theoneprobe.api.IProbeInfo;
import mcjty.theoneprobe.api.IProbeInfoProvider;
import mcjty.theoneprobe.api.ITheOneProbe;
import mcjty.theoneprobe.api.NumberFormat;
import mcjty.theoneprobe.api.ProbeMode;
import mcjty.theoneprobe.apiimpl.ProbeConfig;
import mcjty.theoneprobe.apiimpl.elements.ElementProgress;
import modularmachines.api.energy.IEnergyBuffer;
import modularmachines.api.energy.IKineticSource;
import modularmachines.api.modular.IModular;
import modularmachines.api.modular.ModularManager;
import modularmachines.api.modular.handlers.IModularHandler;
import modularmachines.api.modular.handlers.IModularHandlerTileEntity;
import modularmachines.api.modules.state.IModuleState;
import modularmachines.common.config.Config;
import modularmachines.common.core.Constants;
import modularmachines.common.core.managers.ItemManager;
import modularmachines.common.plugins.APlugin;
import modularmachines.common.utils.Translator;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.IFluidTankProperties;
import net.minecraftforge.fml.common.Optional;
import net.minecraftforge.fml.common.event.FMLInterModComms;

public class PluginTheOneProbe extends APlugin {

	@Override
	public String getRequiredMod() {
		return "theoneprobe";
	}

	@Override
	public void init() {
		FMLInterModComms.sendFunctionMessage("theoneprobe", "getTheOneProbe", "modularmachines.common.plugins.theoneprobe.DefaultInfoProvider");
	}

	@Override
	public boolean isActive() {
		return Config.pluginTheOneProbe;
	}
}
