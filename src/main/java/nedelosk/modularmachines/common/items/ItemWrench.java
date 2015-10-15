package nedelosk.modularmachines.common.items;

import buildcraft.api.tools.IToolWrench;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;

public class ItemWrench extends Item implements IToolWrench {
	
	@Override
	public boolean canWrench(EntityPlayer player, int x, int y, int z) {
		return false;
	}

	@Override
	public void wrenchUsed(EntityPlayer player, int x, int y, int z) {
		
	}

}
