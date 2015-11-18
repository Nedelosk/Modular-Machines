package nedelosk.modularmachines.common.items;

import java.util.Set;

import buildcraft.api.tools.IToolWrench;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemTool;

public class ItemWrench extends ItemTool implements IToolWrench {

	protected ItemWrench(float p_i45333_1_, ToolMaterial p_i45333_2_, Set p_i45333_3_) {
		super(p_i45333_1_, p_i45333_2_, p_i45333_3_);
	}

	@Override
	public boolean canWrench(EntityPlayer player, int x, int y, int z) {
		return true;
	}

	@Override
	public void wrenchUsed(EntityPlayer player, int x, int y, int z) {
		
	}

}
