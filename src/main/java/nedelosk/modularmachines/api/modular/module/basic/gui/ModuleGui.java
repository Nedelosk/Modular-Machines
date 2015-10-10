package nedelosk.modularmachines.api.modular.module.basic.gui;

import nedelosk.modularmachines.api.modular.machines.basic.IModular;
import nedelosk.modularmachines.api.modular.machines.basic.IModularTileEntity;
import nedelosk.modularmachines.api.modular.module.basic.basic.Module;
import nedelosk.nedeloskcore.api.machines.IGuiBase;
import nedelosk.nedeloskcore.api.machines.Widget;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;

public abstract class ModuleGui extends Module implements IModuleGui {

	public ModuleGui() {
		super();
	}
	
	public ModuleGui(String modifier) {
		super(modifier);
	}
	
	public ModuleGui(NBTTagCompound nbt) {
		super(nbt);
	}
	
	@Override
	public int getGuiTop(IModular modular) {
		return 166;
	}

	@Override
	public ResourceLocation getCustomGui(IModular modular) {
		return null;
	}
	
	@Override
	public void renderString(FontRenderer fontRenderer, int x, int y, int xM, int yM) {
		if(hasCustomInventoryName())
			fontRenderer.drawString(getInventoryName(), 90 - (fontRenderer.getStringWidth(getInventoryName()) / 2), 6, 4210752);
	}
	
	public abstract boolean hasCustomInventoryName();
	
	public String getInventoryName(){
		return StatCollector.translateToLocal(getName());
	}
	
	@Override
	public void updateGui(IGuiBase base, int x, int y) {
	}
	
	@Override
	public void handleMouseClicked(IModularTileEntity tile, Widget widget, int mouseX, int mouseY, int mouseButton) {
		
	}
	
}
