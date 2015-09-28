package nedelosk.modularmachines.common.items;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import nedelosk.modularmachines.api.basic.machine.part.IMachinePart;
import nedelosk.modularmachines.api.basic.machine.part.MachineMaterial;
import nedelosk.modularmachines.api.basic.machine.part.MaterialType;
import nedelosk.modularmachines.common.core.tabs.TabModularMachinesModules;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraftforge.client.IItemRenderer;

public abstract class ItemMachinePart extends Item implements IMachinePart{

	public String name;
	public IIcon[] icons;
	public int renderPasses;
	
	public ItemMachinePart(String name, int renderPasses) {
        this.setHasSubtypes(true);
		this.name = name;
		this.renderPasses = renderPasses;
		this.setUnlocalizedName("part" + name);
		this.setCreativeTab(TabModularMachinesModules.instance);
	}
	
	@SideOnly(Side.CLIENT)
	public IItemRenderer getPartRenderer(){
		return null;
	}
	
	@Override
	public boolean requiresMultipleRenderPasses() {
		return true;
	}
	
	@Override
	public int getRenderPasses(int metadata) {
		return renderPasses;
	}
	
	@Override
	public void registerIcons(IIconRegister IIconRegister) {
		icons = new IIcon[renderPasses];
		for(int i = 0;i < renderPasses;i++)
			icons[i] = IIconRegister.registerIcon("modularmachines:machine_parts/" + name + "." + i);
	}
	
	@Override
	public IIcon getIcon(ItemStack stack, int pass) {
		return icons[pass];
	}
	
	@Override
	public String getTagKey() {
		return "machine";
	}

	@Override
	public ItemStack getMachine() {
		return null;
	}
	
	@Override
	public String getPartName() {
		return name;
	}

}
