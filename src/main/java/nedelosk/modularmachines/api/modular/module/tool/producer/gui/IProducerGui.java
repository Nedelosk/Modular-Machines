package nedelosk.modularmachines.api.modular.module.tool.producer.gui;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import nedelosk.forestday.api.guis.IGuiBase;
import nedelosk.forestday.api.guis.Widget;
import nedelosk.modularmachines.api.modular.machines.basic.IModular;
import nedelosk.modularmachines.api.modular.machines.basic.IModularTileEntity;
import nedelosk.modularmachines.api.modular.module.tool.producer.IProducer;
import nedelosk.modularmachines.api.modular.utils.ModuleStack;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.util.ResourceLocation;

public interface IProducerGui extends IProducer {

	@Override
	@SideOnly(Side.CLIENT)
	void updateServer(IModular modular, ModuleStack stack);
	
	@SideOnly(Side.CLIENT)
	int getGuiTop(IModular modular, ModuleStack stack);
	
	@SideOnly(Side.CLIENT)
	ResourceLocation getCustomGui(IModular modular, ModuleStack stack);
	
	@SideOnly(Side.CLIENT)
	void renderString(FontRenderer fontRenderer, int x, int y, int xM, int yM, ModuleStack stack);
	
	@SideOnly(Side.CLIENT)
	void updateGui(IGuiBase base, int x, int y, IModular modular, ModuleStack stack);
	
	@SideOnly(Side.CLIENT)
	void handleMouseClicked(IModularTileEntity tile, Widget widget, int mouseX, int mouseY, int mouseButton, ModuleStack stack);
	
	@SideOnly(Side.CLIENT)
	boolean hasCustomInventoryName(ModuleStack stack);
	
	@SideOnly(Side.CLIENT)
	String getInventoryName(ModuleStack stack);
	
}
