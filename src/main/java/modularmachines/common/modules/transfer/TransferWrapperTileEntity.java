package modularmachines.common.modules.transfer;

import javax.annotation.Nullable;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

import modularmachines.api.modules.container.IModuleContainer;
import modularmachines.common.ModularMachines;
import modularmachines.common.utils.WorldUtil;

public class TransferWrapperTileEntity<H> implements ITransferHandlerWrapper<H> {
	
	protected final EnumFacing facing;
	protected final ModuleTransfer<H> moduleTransfer;
	@Nullable
	protected TileEntity tileEntity;
	
	public TransferWrapperTileEntity(ModuleTransfer<H> moduleTransfer, EnumFacing facing) {
		this.moduleTransfer = moduleTransfer;
		this.facing = facing;
		this.tileEntity = null;
	}
	
	@Override
	public String getTabTooltip() {
		ItemStack tabItem = getTabItem();
		if (tabItem == null || tabItem.isEmpty()) {
			return "UNKNOWN";
		}
		return tabItem.getDisplayName();
	}
	
	@Override
	public void init(IModuleContainer provider) {
		if (tileEntity == null) {
			tileEntity = WorldUtil.getTile(provider.getLocatable(), facing);
		}
	}
	
	@Override
	public boolean isValid() {
		return tileEntity != null && moduleTransfer.isValid(this);
	}
	
	public TileEntity getTileEntity() {
		return tileEntity;
	}
	
	public EnumFacing getFacing() {
		return facing;
	}
	
	@Override
	public ItemStack getTabItem() {
		if (tileEntity == null) {
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
