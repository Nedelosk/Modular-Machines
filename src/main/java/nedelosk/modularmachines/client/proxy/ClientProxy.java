package nedelosk.modularmachines.client.proxy;

import java.util.Map;

import org.lwjgl.input.Keyboard;

import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import nedelosk.modularmachines.api.basic.techtree.TechTreeCategories;
import nedelosk.modularmachines.api.basic.techtree.TechTreeCategoryList;
import nedelosk.modularmachines.client.renderers.ItemModularMachinesRenderer;
import nedelosk.modularmachines.client.renderers.TileModularAssemblerRenderer;
import nedelosk.modularmachines.client.renderers.TileModularWorkbenchRenderer;
import nedelosk.modularmachines.client.techtree.gui.GuiTechPoint;
import nedelosk.modularmachines.client.techtree.pages.PageData;
import nedelosk.modularmachines.common.blocks.tile.TileModularAssembler;
import nedelosk.modularmachines.common.blocks.tile.TileModularWorkbench;
import nedelosk.modularmachines.common.config.ModularConfig;
import nedelosk.modularmachines.common.core.MMBlocks;
import nedelosk.modularmachines.common.proxy.CommonProxy;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.client.MinecraftForgeClient;

public class ClientProxy extends CommonProxy {

	@SideOnly(Side.CLIENT)
	public static KeyBinding techTree = new KeyBinding("forest.mm.techtree.key", Keyboard.KEY_U, "forest.mm.techtree");
	public static final GuiTechPoint techPointGui = new GuiTechPoint();
	
	@Override
	public void registerRenderer() {
		ClientRegistry.bindTileEntitySpecialRenderer(TileModularAssembler.class, new TileModularAssemblerRenderer());
		ClientRegistry.bindTileEntitySpecialRenderer(TileModularWorkbench.class, new TileModularWorkbenchRenderer());
		MinecraftForgeClient.registerItemRenderer(MMBlocks.Modular_Assembler.item(), new ItemModularMachinesRenderer());
		MinecraftForgeClient.registerItemRenderer(MMBlocks.Modular_Workbench.item(), new ItemModularMachinesRenderer());
	}
	
	public static TileEntitySpecialRenderer getRenderer(Class tileEntityClass){
		return (TileEntitySpecialRenderer) TileEntityRendererDispatcher.instance.mapSpecialRenderers.get(tileEntityClass);
	}
	
	@Override
	public void postInit()
	{
    	ClientRegistry.registerKeyBinding(techTree);
    	for(Map.Entry<String, Boolean> entry : ModularConfig.activeLanguages.entrySet())
    		if(entry.getValue())
    			for(Map.Entry<String, TechTreeCategoryList> entryCategory : TechTreeCategories.entryCategories.entrySet())
    				PageData.readDocument(entryCategory.getKey(), entry.getKey());
	}
}
