package nedelosk.modularmachines.client.proxy;

import cpw.mods.fml.client.registry.ClientRegistry;
import nedelosk.modularmachines.api.modular.parts.IMachinePart;
import nedelosk.modularmachines.client.MMClientRegistry;
import nedelosk.modularmachines.client.renderers.item.ItemMetal;
import nedelosk.modularmachines.client.renderers.item.ItemModularMachinesRenderer;
import nedelosk.modularmachines.client.renderers.item.ItemPartRenderer;
import nedelosk.modularmachines.client.renderers.tile.TileModularAssemblerRenderer;
import nedelosk.modularmachines.client.renderers.tile.TileModularWorkbenchRenderer;
import nedelosk.modularmachines.common.blocks.tile.TileModularAssembler;
import nedelosk.modularmachines.common.blocks.tile.TileModularWorkbench;
import nedelosk.modularmachines.common.core.MMBlocks;
import nedelosk.modularmachines.common.core.MMItems;
import nedelosk.modularmachines.common.machines.assembler.AssemblerMachineInfo;
import nedelosk.modularmachines.common.machines.utils.MachineBuilder.BuildMode;
import nedelosk.modularmachines.common.proxy.CommonProxy;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.MinecraftForgeClient;

public class ClientProxy extends CommonProxy {
	
	@Override
	public void registerRenderer() {
		ClientRegistry.bindTileEntitySpecialRenderer(TileModularAssembler.class, new TileModularAssemblerRenderer());
		ClientRegistry.bindTileEntitySpecialRenderer(TileModularWorkbench.class, new TileModularWorkbenchRenderer());
		MinecraftForgeClient.registerItemRenderer(MMBlocks.Modular_Assembler.item(), new ItemModularMachinesRenderer());
		MinecraftForgeClient.registerItemRenderer(MMBlocks.Modular_Workbench.item(), new ItemModularMachinesRenderer());
		MinecraftForgeClient.registerItemRenderer(MMBlocks.Metal_Blocks.item(), new ItemMetal());
		ItemPartRenderer.preInit();
	}
	
	public static TileEntitySpecialRenderer getRenderer(Class tileEntityClass){
		return (TileEntitySpecialRenderer) TileEntityRendererDispatcher.instance.mapSpecialRenderers.get(tileEntityClass);
	}
	
	@Override
	public void init()
	{
    	
    	AssemblerMachineInfo infoBattery = new AssemblerMachineInfo(new ItemStack(MMItems.Part_Battery.item()), BuildMode.PART);
    	infoBattery.addSlotPosition(33-20, 42-20);
        infoBattery.addSlotPosition(33-20, 42);
        infoBattery.addSlotPosition(33, 42);
        infoBattery.addSlotPosition(33+20, 42);
        infoBattery.addSlotPosition(33+20, 42-20);
    	MMClientRegistry.addAssemblerInfo((IMachinePart) MMItems.Part_Battery.item(), infoBattery);
    	
    	AssemblerMachineInfo infoEngine = new AssemblerMachineInfo(new ItemStack(MMItems.Part_Engine.item()), BuildMode.PART);
        infoEngine.addSlotPosition(33+20, 42-20);
        infoEngine.addSlotPosition(33, 42);
        infoEngine.addSlotPosition(33-20, 42+20);
        MMClientRegistry.addAssemblerInfo((IMachinePart) MMItems.Part_Engine.item(), infoEngine);
	}
}
