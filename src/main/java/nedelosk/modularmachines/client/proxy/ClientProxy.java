package nedelosk.modularmachines.client.proxy;

import cpw.mods.fml.client.registry.ClientRegistry;
import nedelosk.modularmachines.api.parts.IMachinePart;
import nedelosk.modularmachines.client.MMClientRegistry;
import nedelosk.modularmachines.client.renderers.item.ItemRendererMetal;
import nedelosk.modularmachines.client.renderers.item.ItemRendererModular;
import nedelosk.modularmachines.client.renderers.item.ItemRendererModularAssembler;
import nedelosk.modularmachines.client.renderers.item.ItemPartRenderer;
import nedelosk.modularmachines.client.renderers.tile.TileRendererModular;
import nedelosk.modularmachines.client.renderers.tile.TileRendererModularAssembler;
import nedelosk.modularmachines.common.blocks.tile.TileModular;
import nedelosk.modularmachines.common.blocks.tile.TileModularAssembler;
import nedelosk.modularmachines.common.core.MMBlocks;
import nedelosk.modularmachines.common.core.MMItems;
import nedelosk.modularmachines.common.modular.machines.assembler.AssemblerMachineInfo;
import nedelosk.modularmachines.common.modular.utils.MachineBuilder.BuildMode;
import nedelosk.modularmachines.common.proxy.CommonProxy;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.MinecraftForgeClient;

public class ClientProxy extends CommonProxy {
	
	public void registerRenderer() {
		ClientRegistry.bindTileEntitySpecialRenderer(TileModular.class, new TileRendererModular());
		ClientRegistry.bindTileEntitySpecialRenderer(TileModularAssembler.class, new TileRendererModularAssembler());
		MinecraftForgeClient.registerItemRenderer(MMBlocks.Modular_Machine.item(), new ItemRendererModular());
		MinecraftForgeClient.registerItemRenderer(MMBlocks.Modular_Assembler.item(), new ItemRendererModularAssembler());
		MinecraftForgeClient.registerItemRenderer(MMBlocks.Metal_Blocks.item(), new ItemRendererMetal());
		ItemPartRenderer.init();
	}
	
	public static TileEntitySpecialRenderer getRenderer(Class tileEntityClass){
		return (TileEntitySpecialRenderer) TileEntityRendererDispatcher.instance.mapSpecialRenderers.get(tileEntityClass);
	}
	
	@Override
	public void init()
	{
    	registerRenderer();
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
        
    	AssemblerMachineInfo infoModule = new AssemblerMachineInfo(new ItemStack(MMItems.Part_Module.item()), BuildMode.PART);
    	infoModule.addSlotPosition(33, 42-20);
        infoModule.addSlotPosition(33-20, 42);
        infoModule.addSlotPosition(33, 42);
        infoModule.addSlotPosition(33+20, 42);
        infoModule.addSlotPosition(33, 42+20);
    	MMClientRegistry.addAssemblerInfo((IMachinePart) MMItems.Part_Module.item(), infoModule);
    	
    	AssemblerMachineInfo infoBurningChamber = new AssemblerMachineInfo(new ItemStack(MMItems.Part_Burning_Chamber.item()), BuildMode.PART);
    	infoBurningChamber.addSlotPosition(33-20, 42-20);
        infoBurningChamber.addSlotPosition(33, 	  42-20);
        infoBurningChamber.addSlotPosition(33+20, 42-20);
        infoBurningChamber.addSlotPosition(33-20, 42);
        infoBurningChamber.addSlotPosition(33+20, 42);
        infoBurningChamber.addSlotPosition(33-20, 42+20);
        infoBurningChamber.addSlotPosition(33, 	  42+20);
    	infoBurningChamber.addSlotPosition(33+20, 42+20);
    	MMClientRegistry.addAssemblerInfo((IMachinePart) MMItems.Part_Burning_Chamber.item(), infoBurningChamber);
    
    	AssemblerMachineInfo infoGrindingWhell = new AssemblerMachineInfo(new ItemStack(MMItems.Part_Grinding_Wheel.item()), BuildMode.PART);
    	infoGrindingWhell.addSlotPosition(33, 42-20);
        infoGrindingWhell.addSlotPosition(33-20, 42);
        infoGrindingWhell.addSlotPosition(33, 42);
        infoGrindingWhell.addSlotPosition(33+20, 42);
        infoGrindingWhell.addSlotPosition(33, 42+20);
    	MMClientRegistry.addAssemblerInfo((IMachinePart) MMItems.Part_Grinding_Wheel.item(), infoGrindingWhell);
	}
}
