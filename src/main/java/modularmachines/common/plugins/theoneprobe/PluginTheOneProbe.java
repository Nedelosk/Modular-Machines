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
import net.minecraftforge.fml.common.event.FMLInterModComms;

public class PluginTheOneProbe extends APlugin implements Function<ITheOneProbe, Void>, IProbeInfoProvider, IProbeConfigProvider {

	public static ITheOneProbe probe;

	@Override
	public Void apply(ITheOneProbe theOneProbe) {
		probe = theOneProbe;
		probe.registerProvider(this);
		probe.registerProbeConfigProvider(this);
		return null;
	}

	@Override
	public String getRequiredMod() {
		return "theoneprobe";
	}

	@Override
	public void init() {
		FMLInterModComms.sendFunctionMessage("theoneprobe", "getTheOneProbe", "modularmachines.common.plugins.theoneprobe.PluginTheOneProbe");
	}

	@Override
	public boolean isActive() {
		return Config.pluginTheOneProbe;
	}

	@Override
	public String getID() {
		return Constants.MOD_ID + ":default";
	}

	@Override
	public void addProbeInfo(ProbeMode mode, IProbeInfo probeInfo, EntityPlayer player, World world, IBlockState blockState, IProbeHitData data) {
		if(probeInfo != null && world != null && blockState != null && data != null){
			TileEntity tileEntity = world.getTileEntity(data.getPos());
			if(tileEntity != null && tileEntity.hasCapability(ModularManager.MODULAR_HANDLER_CAPABILITY, data.getSideHit())){
				IModularHandler modularHandler = tileEntity.getCapability(ModularManager.MODULAR_HANDLER_CAPABILITY, data.getSideHit());
				if(modularHandler instanceof IModularHandlerTileEntity){
					IModularHandlerTileEntity tileHandler = (IModularHandlerTileEntity) modularHandler;
					IModular modular = tileHandler.getModular();
					if(modular != null){
						for(IModuleState state : modular.getModules()){
							ModuleBox box = new ModuleBox(probeInfo);
							mkRfLine(mode, box, state, data);
							mkKineticLine(mode, box, state, data);
							mkTankLine(mode, mcjty.theoneprobe.config.Config.getDefaultConfig(), box, state, data);
						}
					}
				}
			}
		}
	}

	private void mkRfLine(ProbeMode mode, ModuleBox box, IModuleState state, IProbeHitData data) {
		if (mode != ProbeMode.NORMAL || Config.topShowPowerByDefault) {
			if (state.getContentHandler(IEnergyBuffer.class) != null) {
				IEnergyBuffer buffer = state.getContentHandler(IEnergyBuffer.class);
				final IProbeInfo rfLine = box.get(state).horizontal(box.center()).item(new ItemStack(Items.REDSTONE));
				if (buffer.getEnergyStored() > 0) {
					rfLine.progress(buffer.getEnergyStored(), buffer.getCapacity(), box.getProbeinfo().defaultProgressStyle().suffix(" " + Translator.translateToLocal("mm.top.suffix.rf"))
							.filledColor(mcjty.theoneprobe.config.Config.rfbarFilledColor).alternateFilledColor(mcjty.theoneprobe.config.Config.rfbarAlternateFilledColor).borderColor(mcjty.theoneprobe.config.Config.rfbarBorderColor)
							.numberFormat(mcjty.theoneprobe.config.Config.rfFormat));
				} else {
					rfLine.text(TextFormatting.DARK_RED + Translator.translateToLocal("mm.top.outofpower"));
				}
			}
		}
	}

	private void mkKineticLine(ProbeMode mode, ModuleBox box, IModuleState state, IProbeHitData data) {
		if (mode != ProbeMode.NORMAL || Config.topShowKineticByDefault) {
			if (state.getContentHandler(IKineticSource.class) != null) {
				IKineticSource buffer = state.getContentHandler(IKineticSource.class);
				final IProbeInfo kLine = box.get(state).horizontal(box.center()).item(new ItemStack(ItemManager.itemEngineSteam));
				if (buffer.getStored() > 0) {
					kLine.progress((long)buffer.getStored(), (long)buffer.getCapacity(), box.getProbeinfo().defaultProgressStyle().suffix(" " + Translator.translateToLocal("mm.top.suffix.kenetic")).numberFormat(NumberFormat.COMMAS)
							.filledColor(0xff128bb8).alternateFilledColor(0xff128bb8).lifeBar(false));
				} else {
					kLine.text(TextFormatting.DARK_RED + Translator.translateToLocal("mm.top.outofpower"));
				}
			}
		}
	}

	private void mkTankLine(ProbeMode mode, ProbeConfig config, ModuleBox box, IModuleState state, IProbeHitData data) {
		if (mode != ProbeMode.NORMAL || Config.topShowTanksByDefault) {
			if (state.getContentHandler(IFluidHandler.class) != null) {
				IFluidHandler fluidHandler = state.getContentHandler(IFluidHandler.class);
				if (fluidHandler.getTankProperties().length > 0) {
					IFluidTankProperties[] properties = fluidHandler.getTankProperties();
					if (properties != null) {
						for (IFluidTankProperties property : properties) {
							if (property != null) {
								FluidStack fluidStack = property.getContents();
								int maxContents = property.getCapacity();
								addFluidInfo(box.get(state), config, fluidStack, maxContents);
							}
						}
					}
				}
			}
		}
	}

	private void addFluidInfo(IProbeInfo probeInfo, ProbeConfig config, FluidStack fluidStack, int maxContents) {
		int contents = fluidStack == null ? 0 : fluidStack.amount;
		if (fluidStack != null) {
			probeInfo.text("Liquid: " + fluidStack.getLocalizedName());
		}
		if (config.getTankMode() == 1) {
			probeInfo.progress(contents, maxContents,
					probeInfo.defaultProgressStyle()
					.suffix("mB")
					.filledColor(mcjty.theoneprobe.config.Config.tankbarFilledColor)
					.alternateFilledColor(mcjty.theoneprobe.config.Config.tankbarAlternateFilledColor)
					.borderColor(mcjty.theoneprobe.config.Config.tankbarBorderColor)
					.numberFormat(mcjty.theoneprobe.config.Config.tankFormat));
		} else {
			probeInfo.text(TextFormatting.GREEN + ElementProgress.format(contents, mcjty.theoneprobe.config.Config.tankFormat, "mB"));
		}
	}

	private static class ModuleBox {
		private final IProbeInfo probeinfo;
		private IProbeInfo box;

		public ModuleBox(IProbeInfo probeinfo) {
			this.probeinfo = probeinfo;
		}

		public IProbeInfo getProbeinfo() {
			return probeinfo;
		}

		public IProbeInfo get(IModuleState state) {
			if (box == null) {

				box = probeinfo.vertical(probeinfo.defaultLayoutStyle().borderColor(0xffff0000));
				box.horizontal().item(state.getProvider().getItemStack()).text(state.getDisplayName());
			}
			return box;
		}

		public ILayoutStyle center() {
			return probeinfo.defaultLayoutStyle().alignment(ElementAlignment.ALIGN_CENTER);
		}
	}

	@Override
	public void getProbeConfig(IProbeConfig config, EntityPlayer player, World world, Entity entity, IProbeHitEntityData data) {
	}

	@Override
	public void getProbeConfig(IProbeConfig config, EntityPlayer player, World world, IBlockState blockState, IProbeHitData data) {
		if(config != null && world != null && blockState != null && data != null){
			TileEntity tileEntity = world.getTileEntity(data.getPos());
			if(tileEntity != null && tileEntity.hasCapability(ModularManager.MODULAR_HANDLER_CAPABILITY, data.getSideHit())){
				config.setRFMode(0);
				config.setTankMode(0);
			}
		}
	}
}
