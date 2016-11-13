package modularmachines.api.modules.transport;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import modularmachines.api.gui.Page;
import modularmachines.api.modular.handlers.IModularHandler;

public abstract class TransportCyclePage<H, T extends ITransportCycle<H>> extends Page<IModularHandler> implements ITransportCyclePage<H, T> {

	public TransportCyclePage(String title) {
		super(title);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public ResourceLocation getGuiTexture() {
		return new ResourceLocation("modularmachines:textures/gui/modular_machine.png");
	}
}
