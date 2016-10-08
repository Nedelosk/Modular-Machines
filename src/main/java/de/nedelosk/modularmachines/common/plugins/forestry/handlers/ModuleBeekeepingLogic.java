package de.nedelosk.modularmachines.common.plugins.forestry.handlers;

import de.nedelosk.modularmachines.common.network.PacketHandler;
import de.nedelosk.modularmachines.common.plugins.forestry.network.PacketBeeLogicActiveModule;
import forestry.apiculture.BeekeepingLogic;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;

public class ModuleBeekeepingLogic extends BeekeepingLogic {

	private BeeHouseHandler housing;

	public ModuleBeekeepingLogic(BeeHouseHandler housing) {
		super(housing);
		this.housing = housing;
	}

	@Override
	public void syncToClient() {
		World world = housing.getWorldObj();
		if (world != null && !world.isRemote) {
			PacketHandler.sendToNetwork(new PacketBeeLogicActiveModule(housing.getModuleState(), housing), housing.getCoordinates(), (WorldServer) world);
		}
	}

	@Override
	public void syncToClient(EntityPlayerMP player) {
		World world = housing.getWorldObj();
		if (world != null && !world.isRemote) {
			PacketHandler.sendToPlayer(new PacketBeeLogicActiveModule(housing.getModuleState(), housing), player);
		}
	}

}
