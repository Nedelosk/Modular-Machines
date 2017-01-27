package modularmachines.common.core;

import javax.annotation.Nullable;

import modularmachines.api.modules.IModuleLogic;
import modularmachines.api.modules.assemblers.IAssembler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

public class GuiHandler implements IGuiHandler {
	
	@Nullable
	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		TileEntity tile = world.getTileEntity(new BlockPos(x, y, z));
		if(tile != null){
			switch (ID) {
				case 0: {
					if (tile.hasCapability(modularmachines.api.modules.ModuleManager.ASSEMBLER, null)) {
						IAssembler assembler = tile.getCapability(modularmachines.api.modules.ModuleManager.ASSEMBLER, null);
						return assembler.createContainer(player.inventory);
					}
				}
				case 1: {
					if (tile.hasCapability(modularmachines.api.modules.ModuleManager.MODULE_LOGIC, null)) {
						IModuleLogic logic = tile.getCapability(modularmachines.api.modules.ModuleManager.MODULE_LOGIC, null);
						return logic.createContainer(player.inventory);
					}
				}
				default:
					return null;
			}
		}
		return null;
	}

	@Nullable
	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		TileEntity tile = world.getTileEntity(new BlockPos(x, y, z));
		if(tile != null){
			switch (ID) {
				case 0: {
					if (tile.hasCapability(modularmachines.api.modules.ModuleManager.ASSEMBLER, null)) {
						IAssembler assembler = tile.getCapability(modularmachines.api.modules.ModuleManager.ASSEMBLER, null);
						return assembler.createGui(player.inventory);
					}
				}
				case 1: {
					if (tile.hasCapability(modularmachines.api.modules.ModuleManager.MODULE_LOGIC, null)) {
						IModuleLogic logic = tile.getCapability(modularmachines.api.modules.ModuleManager.MODULE_LOGIC, null);
						return logic.createGui(player.inventory);
					}
				}
				default:
					return null;
			}
		}
		return null;
	}

}
