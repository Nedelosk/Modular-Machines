package modularmachines.client.core;

import modularmachines.common.core.CommonProxy;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.audio.SoundHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.world.World;

public class ClientProxy extends CommonProxy {

	@Override
	public EntityPlayer getPlayer() {
		return Minecraft.getMinecraft().player;
	}

	@Override
	public void playButtonClick() {
		Minecraft minecraft = getClientInstance();
		SoundHandler soundHandler = minecraft.getSoundHandler();
		soundHandler.playSound(PositionedSoundRecord.getMasterRecord(SoundEvents.UI_BUTTON_CLICK, 1.0F));
	}
	
	@Override
	public World getRenderWorld() {
		return getClientInstance().world;
	}
	
}
