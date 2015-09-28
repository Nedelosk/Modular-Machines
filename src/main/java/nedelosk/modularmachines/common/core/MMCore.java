package nedelosk.modularmachines.common.core;

import static net.minecraft.util.EnumChatFormatting.AQUA;
import static net.minecraft.util.EnumChatFormatting.DARK_AQUA;
import static net.minecraft.util.EnumChatFormatting.DARK_GRAY;
import static net.minecraft.util.EnumChatFormatting.DARK_GREEN;
import static net.minecraft.util.EnumChatFormatting.DARK_PURPLE;
import static net.minecraft.util.EnumChatFormatting.DARK_RED;
import static net.minecraft.util.EnumChatFormatting.GOLD;
import static net.minecraft.util.EnumChatFormatting.GRAY;
import static net.minecraft.util.EnumChatFormatting.GREEN;
import static net.minecraft.util.EnumChatFormatting.LIGHT_PURPLE;
import static net.minecraft.util.EnumChatFormatting.RED;
import static net.minecraft.util.EnumChatFormatting.WHITE;
import static net.minecraft.util.EnumChatFormatting.YELLOW;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import nedelosk.modularmachines.api.basic.machine.part.MaterialType;
import nedelosk.modularmachines.common.config.ModularConfig;
import nedelosk.modularmachines.common.core.manager.ModularRecipeManager;
import nedelosk.modularmachines.common.core.manager.OreDictManager;
import nedelosk.modularmachines.common.core.registry.BlockRegistry;
import nedelosk.modularmachines.common.core.registry.ItemRegistry;
import nedelosk.modularmachines.common.core.registry.ModularRegistry;
import nedelosk.modularmachines.common.core.registry.TechTreeRegistry;
import nedelosk.modularmachines.common.events.EventHandler;
import nedelosk.modularmachines.common.events.EventHandlerNetwork;
import nedelosk.modularmachines.common.events.KeyHandler;
import nedelosk.modularmachines.common.multiblocks.MultiblockAirHeatingPlant;
import nedelosk.modularmachines.common.multiblocks.MultiblockBlastFurnace;
import nedelosk.modularmachines.common.multiblocks.MultiblockCokeOven;
import nedelosk.modularmachines.common.multiblocks.MultiblockFermenter;
import nedelosk.modularmachines.common.network.packets.PacketHandler;
import nedelosk.modularmachines.common.world.WorldGeneratorModularMachines;
import nedelosk.modularmachines.plugins.PluginManager;
import nedelosk.nedeloskcore.common.core.registry.NRegistry;
import net.minecraft.block.material.Material;
import net.minecraftforge.common.MinecraftForge;
import tconstruct.library.TConstructRegistry;

public class MMCore {

	PluginManager pluginManager = new PluginManager();
	
	public void preInit()
	{
    	registerFluids();
    	registerMaterials();
    	pluginManager.registerPlugins();
		pluginManager.preInit();
    	ModularConfig.preInit();
    	BlockRegistry.preInit();
    	ItemRegistry.preInit();
    	ModularRegistry.preInit();
    	TechTreeRegistry.preInit();
    	PacketHandler.preInit();
    	ModularRecipeManager.preInit();
    	MinecraftForge.EVENT_BUS.register(new EventHandler());
    	if(FMLCommonHandler.instance().getEffectiveSide() == Side.CLIENT)
    		FMLCommonHandler.instance().bus().register(new KeyHandler());
		FMLCommonHandler.instance().bus().register(new EventHandlerNetwork());
		GameRegistry.registerWorldGenerator(new WorldGeneratorModularMachines(), 0);
		new MultiblockAirHeatingPlant();
		new MultiblockBlastFurnace();
		new MultiblockCokeOven();
		new MultiblockFermenter();
	}
	
	public void init()
	{
		OreDictManager.init();
		pluginManager.init();
	}
	
	public void postInit()
	{
		pluginManager.postInit();
	}
	
	public static void registerFluids()
	{
		NRegistry.registerFluid("pig.iron", 1500, Material.lava, true);
		NRegistry.registerFluid("slag", 100, Material.lava, true);
		NRegistry.registerFluid("gas.blastfurnace", 200, Material.water, true);
		NRegistry.registerFluid("air.hot", 1250, Material.lava, true);
	}
	
	public static void registerMaterials()
	{
		MMRegistry.addMachineMaterial(MaterialType.WOOD, "Wood", 1, 0, YELLOW.toString(), 0x755821);
		MMRegistry.addMachineMaterial(MaterialType.STONE, "Stone", 1, 0, GRAY.toString(), 0x7F7F7F);
		MMRegistry.addMachineMaterial(MaterialType.METAL, "Iron", 2, 1, WHITE.toString(), 0xDADADA);
		MMRegistry.addMachineMaterial(MaterialType.CUSTOM, "Flint", 1, 0, DARK_GRAY.toString(), 0x484848);
		MMRegistry.addMachineMaterial(MaterialType.CUSTOM, "Bone", 1, 0, YELLOW.toString(), 0xEDEBCA);
		MMRegistry.addMachineMaterial(MaterialType.STONE, "Netherrack", 2, 0, DARK_RED.toString(), 0x833238);
        MMRegistry.addMachineMaterial(MaterialType.METAL, "Tin", 1, 0, RED.toString(), 0xCC6410);
        MMRegistry.addMachineMaterial(MaterialType.METAL, "Copper", 1, 0, RED.toString(), 0xCC6410);
        MMRegistry.addMachineMaterial(MaterialType.METAL, "Bronze", 2, 1, GOLD.toString(), 0xCA9956);
        MMRegistry.addMachineMaterial(MaterialType.METAL, "Steel", 4, 2, GRAY.toString(), 0xA0A0A0);
        MMRegistry.addMachineMaterial(MaterialType.METAL, "Gray Pig Iron", 4, 1, DARK_GRAY.toString(), 0xA0A0A0);
        MMRegistry.addMachineMaterial(MaterialType.METAL, "White Pig Iron", 4, 1, WHITE.toString(), 0xA0A0A0);
	}
}
