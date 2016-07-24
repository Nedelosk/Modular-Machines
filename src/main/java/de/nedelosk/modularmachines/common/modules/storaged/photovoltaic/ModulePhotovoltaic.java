package de.nedelosk.modularmachines.common.modules.storaged.photovoltaic;

import de.nedelosk.modularmachines.api.Translator;
import de.nedelosk.modularmachines.api.energy.EnergyRegistry;
import de.nedelosk.modularmachines.api.energy.IEnergyInterface;
import de.nedelosk.modularmachines.api.energy.IEnergyType;
import de.nedelosk.modularmachines.api.modular.AssemblerException;
import de.nedelosk.modularmachines.api.modular.IModular;
import de.nedelosk.modularmachines.api.modular.IModularAssembler;
import de.nedelosk.modularmachines.api.modular.handlers.IModularHandlerTileEntity;
import de.nedelosk.modularmachines.api.modules.IModule;
import de.nedelosk.modularmachines.api.modules.IModuleContainer;
import de.nedelosk.modularmachines.api.modules.state.IModuleState;
import de.nedelosk.modularmachines.api.modules.storage.IPositionedModuleStorage;
import de.nedelosk.modularmachines.api.modules.storaged.EnumModuleSize;
import de.nedelosk.modularmachines.api.modules.storaged.EnumPosition;
import de.nedelosk.modularmachines.api.modules.storaged.EnumWallType;
import de.nedelosk.modularmachines.api.modules.storaged.photovoltaic.IModulePhotovoltaic;
import de.nedelosk.modularmachines.api.modules.storaged.storage.IModuleBattery;
import de.nedelosk.modularmachines.common.modules.Module;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.EnumSkyBlock;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ModulePhotovoltaic extends Module implements IModulePhotovoltaic{

	protected final int rfOutput;

	public ModulePhotovoltaic(int complexity, int rfOutput) {
		super("photovoltaic", complexity);
		this.rfOutput = rfOutput;
	}

	@Override
	public void assembleModule(IModularAssembler assembler, IModular modular, IPositionedModuleStorage storage, IModuleState state) throws AssemblerException {
		if(modular.getModules(IModuleBattery.class).isEmpty()){
			throw new AssemblerException(Translator.translateToLocal("modular.assembler.error.no.battery"));
		}
	}

	@Override
	public void updateServer(IModuleState<IModule> state, int tickCount) {
		if(state.getModular().getHandler() instanceof IModularHandlerTileEntity){
			IModularHandlerTileEntity handler = (IModularHandlerTileEntity) state.getModular().getHandler();
			IEnergyInterface energyInterface = state.getModular().getEnergyInterface();
			IEnergyType type = EnergyRegistry.redstoneFlux;

			if(energyInterface.getCapacity(type) > energyInterface.getEnergyStored(type)){
				World world = handler.getWorld();
				BlockPos pos = handler.getPos();
				float lightRatio = calculateLightRatio(world);
				if(world.canSeeSky(pos.up())){
					energyInterface.receiveEnergy(type, Float.valueOf(rfOutput * lightRatio).intValue(), false);
				}
			}
		}
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void updateClient(IModuleState<IModule> state, int tickCount) {
	}

	private float calculateLightRatio(World world) {
		int lightValue = EnumSkyBlock.SKY.defaultLightValue - world.getSkylightSubtracted();
		float sunAngle = world.getCelestialAngleRadians(1.0F);

		if (sunAngle < (float) Math.PI) {
			sunAngle += (0.0F - sunAngle) * 0.2F;
		} else {
			sunAngle += (((float) Math.PI * 2F) - sunAngle) * 0.2F;
		}

		lightValue = Math.round(lightValue * MathHelper.cos(sunAngle));

		lightValue = MathHelper.clamp_int(lightValue, 0, 15);
		return lightValue / 15f;
	}

	@Override
	public EnumModuleSize getSize() {
		return EnumModuleSize.LARGE;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public EnumWallType getWallType(IModuleState state) {
		return EnumWallType.NONE;
	}

	@Override
	public EnumPosition getPosition(IModuleContainer container) {
		return EnumPosition.TOP;
	}
}
