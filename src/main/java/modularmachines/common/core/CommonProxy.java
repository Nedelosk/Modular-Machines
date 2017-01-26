package modularmachines.common.core;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraftforge.fml.client.FMLClientHandler;

public class CommonProxy {

	public World getRenderWorld() {
		throw new IllegalStateException("Cannot get render world on server");
	}

	public Minecraft getClientInstance() {
		return FMLClientHandler.instance().getClient();
	}

	public EntityPlayer getPlayer() {
		throw new IllegalStateException("Can't call getPlayer on the server");
	}

	public void playButtonClick() {

	}
	
}
