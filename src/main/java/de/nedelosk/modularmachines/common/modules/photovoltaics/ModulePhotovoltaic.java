package de.nedelosk.modularmachines.common.modules.photovoltaics;

import java.util.ArrayList;
import java.util.List;

import de.nedelosk.modularmachines.api.energy.IEnergyBuffer;
import de.nedelosk.modularmachines.api.modular.AssemblerException;
import de.nedelosk.modularmachines.api.modular.IModular;
import de.nedelosk.modularmachines.api.modular.IModularAssembler;
import de.nedelosk.modularmachines.api.modular.handlers.IModularHandler;
import de.nedelosk.modularmachines.api.modular.handlers.IModularHandlerTileEntity;
import de.nedelosk.modularmachines.api.modules.EnumWallType;
import de.nedelosk.modularmachines.api.modules.IModule;
import de.nedelosk.modularmachines.api.modules.containers.IModuleContainer;
import de.nedelosk.modularmachines.api.modules.controller.ModuleControlled;
import de.nedelosk.modularmachines.api.modules.handlers.IModulePage;
import de.nedelosk.modularmachines.api.modules.photovoltaics.IModulePhotovoltaic;
import de.nedelosk.modularmachines.api.modules.position.EnumModulePositions;
import de.nedelosk.modularmachines.api.modules.position.IModulePositioned;
import de.nedelosk.modularmachines.api.modules.position.IModulePostion;
import de.nedelosk.modularmachines.api.modules.state.IModuleState;
import de.nedelosk.modularmachines.api.modules.storage.IStorage;
import de.nedelosk.modularmachines.api.modules.storage.energy.IModuleBattery;
import de.nedelosk.modularmachines.common.modules.pages.ControllerPage;
import de.nedelosk.modularmachines.common.network.PacketHandler;
import de.nedelosk.modularmachines.common.network.packets.PacketSyncModule;
import de.nedelosk.modularmachines.common.utils.Translator;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.EnumSkyBlock;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ModulePhotovoltaic extends ModuleControlled implements IModulePhotovoltaic, IModulePositioned{

	protected final int rfOutput;

	public ModulePhotovoltaic(int rfOutput) {
		super("photovoltaic");
		this.rfOutput = rfOutput;
	}

	@Override
	public void assembleModule(IModularAssembler assembler, IModular modular, IStorage storage, IModuleState state) throws AssemblerException {
		if(modular.getModules(IModuleBattery.class).isEmpty()){
			throw new AssemblerException(Translator.translateToLocal("modular.assembler.error.no.battery"));
		}
	}

	@Override
	public void sendModuleUpdate(IModuleState state){
		IModularHandler handler = state.getModular().getHandler();
		if(handler instanceof IModularHandlerTileEntity){
			PacketHandler.sendToNetwork(new PacketSyncModule(state), ((IModularHandlerTileEntity)handler).getPos(), (WorldServer) handler.getWorld());
		}
	}

	@Override
	public void updateServer(IModuleState<IModule> state, int tickCount) {
		if(state.getModular().getHandler() instanceof IModularHandlerTileEntity){
			IModularHandlerTileEntity handler = (IModularHandlerTileEntity) state.getModular().getHandler();
			IEnergyBuffer energyBuffer = state.getModular().getEnergyBuffer();

			if(energyBuffer.getCapacity() > energyBuffer.getEnergyStored()){
				World world = handler.getWorld();
				BlockPos pos = handler.getPos();
				float lightRatio = calculateLightRatio(world);
				if(world.canSeeSky(pos.up())){
					energyBuffer.receiveEnergy(state, null, Float.valueOf(rfOutput * lightRatio).longValue(), false);
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

	@SideOnly(Side.CLIENT)
	@Override
	public EnumWallType getWallType(IModuleState state) {
		return EnumWallType.NONE;
	}

	@Override
	public IModulePostion[] getValidPositions(IModuleContainer container) {
		return new IModulePostion[]{EnumModulePositions.TOP};
	}

	@Override
	public List<IModuleState> getUsedModules(IModuleState state) {
		return new ArrayList(state.getModular().getModules(IModuleBattery.class));
	}

	@Override
	protected IModulePage getControllerPage(IModuleState state) {
		return new ControllerPage(state);
	}
}
