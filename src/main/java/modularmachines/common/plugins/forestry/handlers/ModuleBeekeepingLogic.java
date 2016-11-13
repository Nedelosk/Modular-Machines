package modularmachines.common.plugins.forestry.handlers;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;

import forestry.apiculture.BeekeepingLogic;
import modularmachines.common.network.PacketHandler;
import modularmachines.common.plugins.forestry.network.PacketBeeLogicActiveModule;

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
