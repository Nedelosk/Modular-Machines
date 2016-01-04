package nedelosk.modularmachines.api.producers.client;

import java.util.Locale;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import nedelosk.forestcore.library.gui.IGuiBase;
import nedelosk.forestcore.library.gui.Widget;
import nedelosk.modularmachines.api.modular.IModular;
import nedelosk.modularmachines.api.modular.tile.IModularTileEntity;
import nedelosk.modularmachines.api.producers.Producer;
import nedelosk.modularmachines.api.utils.ModuleStack;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;

public abstract class ProducerGui extends Producer implements IProducerGui {

	public ProducerGui(String modifier) {
		super(modifier);
	}

	public ProducerGui(NBTTagCompound nbt, IModular modular, ModuleStack stack) {
		super(nbt, modular, stack);
	}

	/* GUI */
	@SideOnly(Side.CLIENT)
	@Override
	public int getGuiTop(IModular modular, ModuleStack stack) {
		return 166;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public ResourceLocation getCustomGui(IModular modular, ModuleStack stack) {
		return null;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void renderString(FontRenderer fontRenderer, int x, int y, int xM, int yM, ModuleStack stack) {
		if (hasCustomInventoryName(stack))
			fontRenderer.drawString(getInventoryName(stack),
					90 - (fontRenderer.getStringWidth(getInventoryName(stack)) / 2), 6, 4210752);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public abstract boolean hasCustomInventoryName(ModuleStack stack);

	@Override
	@SideOnly(Side.CLIENT)
	public String getInventoryName(ModuleStack stack) {
		return StatCollector.translateToLocal("mm.modularmachine.bookmark."
				+ stack.getModule().getModuleName().toLowerCase(Locale.ENGLISH) + ".name");
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void updateGui(IGuiBase base, int x, int y, IModular modular, ModuleStack stack) {
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void handleMouseClicked(IModularTileEntity tile, Widget widget, int mouseX, int mouseY, int mouseButton,
			ModuleStack stack) {
	}

}
