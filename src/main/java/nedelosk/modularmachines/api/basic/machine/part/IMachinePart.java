package nedelosk.modularmachines.api.basic.machine.part;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.IItemRenderer;

public interface IMachinePart {

	String getTagKey();
	
	ItemStack getMachine();
	
	String getPartName();
	
	@SideOnly(Side.CLIENT)
	IItemRenderer getPartRenderer();
}
