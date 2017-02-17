package modularmachines.common.modules.transfer;

import modularmachines.api.modules.logic.IModuleLogic;
import modularmachines.common.core.ModularMachines;
import modularmachines.common.utils.WorldUtil;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

public class TransferWrapperTileEntity<H> implements ITransferHandlerWrapper<H> {
	
	protected TileEntity tileEntity;
	protected final EnumFacing facing;
	protected final ModuleTransfer<H> moduleTransfer;
	
	public TransferWrapperTileEntity(ModuleTransfer<H> moduleTransfer, EnumFacing facing) {
		this.moduleTransfer = moduleTransfer;
		this.facing = facing;
		this.tileEntity = null;
	}

	@Override
	public String getTabTooltip() {
		return null;
	}
	
	@Override
	public void init(IModuleLogic logic) {
		if(tileEntity == null){
			tileEntity = WorldUtil.getTile(logic.getLocatable(), facing);
		}
	}

	@Override
	public boolean isValid() {
		return tileEntity != null;
	}
	
	public TileEntity getTileEntity() {
		return tileEntity;
	}
	
	public EnumFacing getFacing() {
		return facing;
	}

	@Override
	public ItemStack getTabItem() {
		if(tileEntity == null){
			return ItemStack.EMPTY;
		}
		World world = tileEntity.getWorld();
		BlockPos pos = tileEntity.getPos();
		Minecraft minecraft = ModularMachines.proxy.getClientInstance();
		EntityPlayer player = minecraft.player;
		RayTraceResult rayTrace = minecraft.objectMouseOver;
		IBlockState state = world.getBlockState(pos);
		return state.getBlock().getPickBlock(state, rayTrace, world, pos, player);
	}
	
	@Override
	public ModuleTransfer<H> getTransferModule() {
		return moduleTransfer;
	}
	
}
