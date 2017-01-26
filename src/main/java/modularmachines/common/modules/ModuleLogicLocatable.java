package modularmachines.common.modules;

import modularmachines.api.ILocatable;
import modularmachines.api.modules.ModuleHelper;
import modularmachines.api.modules.assemblers.IAssembler;
import modularmachines.common.network.PacketHandler;
import modularmachines.common.network.packets.PacketSyncHandlerState;
import modularmachines.common.utils.ContainerUtil;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;

public class ModuleLogicLocatable extends ModuleLogic implements ILocatable {

	public World world;
	public BlockPos pos;
	
	@Override
	public void assemble(IAssembler assembler, EntityPlayer player) {
		super.assemble(assembler, player);
		if (world.isRemote) {
			world.markBlockRangeForRenderUpdate(pos, pos);
		} else {
			if(world instanceof WorldServer){
				WorldServer server = (WorldServer) world;
				PacketHandler.sendToNetwork(new PacketSyncHandlerState(this, true), pos, server);
				IBlockState blockState = server.getBlockState(pos);
				server.notifyBlockUpdate(pos, blockState, blockState, 3);
				ContainerUtil.openOrCloseGuiSave(this, !ModuleHelper.getPageModules(this).isEmpty());
			}
		}
	}

	@Override
	public void updateRendering() {
	}
	
	@Override
	public ILocatable getLocatable() {
		return this;
	}
	
	@Override
	public BlockPos getCoordinates() {
		return pos;
	}
	
	@Override
	public World getWorldObj() {
		return world;
	}

}
