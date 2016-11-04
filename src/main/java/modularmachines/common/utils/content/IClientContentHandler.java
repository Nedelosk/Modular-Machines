package modularmachines.common.utils.content;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public interface IClientContentHandler {

	@SideOnly(Side.CLIENT)
	void handleClientContent();
}
