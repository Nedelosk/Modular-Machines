package nedelosk.forestday.common.registrys;

import java.util.LinkedList;

import nedelosk.forestday.api.Tabs;
import nedelosk.forestday.common.blocks.BlockForestday;
import nedelosk.forestday.common.blocks.BlockLogResin;
import nedelosk.forestday.common.blocks.BlockMetalBlocks;
import nedelosk.forestday.common.blocks.BlockOre;
import nedelosk.forestday.common.blocks.BlockTrunk;
import nedelosk.forestday.common.items.blocks.ItemBlockForestday;
import nedelosk.forestday.common.machines.brick.furnace.coke.TileCokeFurnace;
import nedelosk.forestday.common.machines.brick.furnace.fluidheater.TileFluidHeater;
import nedelosk.forestday.common.machines.brick.generator.heat.TileHeatGenerator;
import nedelosk.forestday.common.machines.brick.kiln.TileKiln;
import nedelosk.forestday.common.machines.iron.saw.TileSaw;
import nedelosk.forestday.common.machines.wood.workbench.TileWorkbench;
import nedelosk.forestday.machines.fan.block.BlockFan;
import nedelosk.forestday.machines.fan.tile.TileFan;
import nedelosk.forestday.machines.furnace.BlockFurnace;
import nedelosk.forestday.machines.generator.BlockGenerator;
import nedelosk.forestday.machines.kiln.block.BlockKiln;
import nedelosk.forestday.machines.saw.block.BlockSaw;
import nedelosk.forestday.module.lumberjack.blocks.BlockWorkbench;
import nedelosk.forestday.structure.airheater.blocks.BlockAirHeater;
import nedelosk.forestday.structure.airheater.blocks.tile.TileAirHeaterController;
import nedelosk.forestday.structure.airheater.blocks.tile.TileAirHeaterRegulator;
import nedelosk.forestday.structure.alloysmelter.blocks.BlockAlloySmelter;
import nedelosk.forestday.structure.alloysmelter.blocks.tile.TileAlloySmelterController;
import nedelosk.forestday.structure.alloysmelter.blocks.tile.TileAlloySmelterRegulator;
import nedelosk.forestday.structure.base.blocks.BlockBricks;
import nedelosk.forestday.structure.base.blocks.BlockBusFluid;
import nedelosk.forestday.structure.base.blocks.BlockBusItem;
import nedelosk.forestday.structure.base.blocks.BlockBusMetalFluid;
import nedelosk.forestday.structure.base.blocks.BlockBusMetalItem;
import nedelosk.forestday.structure.base.blocks.BlockCoilGrinding;
import nedelosk.forestday.structure.base.blocks.BlockCoilHeat;
import nedelosk.forestday.structure.base.blocks.BlockMetal;
import nedelosk.forestday.structure.base.blocks.tile.TileBusFluid;
import nedelosk.forestday.structure.base.blocks.tile.TileBusItem;
import nedelosk.forestday.structure.base.blocks.tile.TileCoilGrinding;
import nedelosk.forestday.structure.base.blocks.tile.TileCoilHeat;
import nedelosk.forestday.structure.base.blocks.tile.TileController;
import nedelosk.forestday.structure.base.blocks.tile.TileStructure;
import nedelosk.forestday.structure.base.blocks.tile.TileStructureBrick;
import nedelosk.forestday.structure.base.blocks.tile.TileStructureMetal;
import nedelosk.forestday.structure.base.items.blocks.ItemBlockCoilGrinding;
import nedelosk.forestday.structure.base.items.blocks.ItemBlockCoilHeat;
import nedelosk.forestday.structure.blastfurnace.blocks.BlockBlastFurncae;
import nedelosk.forestday.structure.blastfurnace.blocks.tile.TileBlastFurnaceController;
import nedelosk.forestday.structure.blastfurnace.blocks.tile.TileBlastFurnaceRegulator;
import nedelosk.forestday.structure.kiln.charcoal.blocks.BlockCharcoalKiln;
import nedelosk.forestday.structure.kiln.charcoal.blocks.tile.TileCharcoalKiln;
import nedelosk.forestday.structure.macerator.blocks.BlockMacerator;
import nedelosk.forestday.structure.macerator.blocks.tile.TileMaceratorController;
import nedelosk.forestday.structure.macerator.blocks.tile.TileMaceratorRegulator;
import net.minecraft.block.Block;
import net.minecraft.block.BlockLog;
import net.minecraft.block.material.Material;
import net.minecraft.client.particle.EffectRenderer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import cpw.mods.fml.common.registry.GameRegistry;

public class ForestdayBlockRegistry {
	
	public static void preInit()
	{
		
		//Blocks
		
		oreBlock = new BlockOre();
		GameRegistry.registerBlock(oreBlock, ItemBlockForestday.class, "ore");
		
		metalBlock = new BlockMetalBlocks();
		GameRegistry.registerBlock(metalBlock , ItemBlockForestday.class, "metalBlock");
		
		loam = new BlockForestday("loam", Material.sand, Tabs.tabForestdayBlocks).setBlockTextureName("forestday:loam").setStepSound(Block.soundTypeGravel).setHardness(2.0F);
		GameRegistry.registerBlock(loam, "loam");
		
		logNature = new BlockLogResin();
		GameRegistry.registerBlock(logNature, "log.nature");
		
		//Trunk
		trunkBig = new BlockTrunk("big");
		GameRegistry.registerBlock(trunkBig, "trunk.big");
		trunkMedium = new BlockTrunk("medium");
		GameRegistry.registerBlock(trunkMedium, "trunk.medium");
		trunkSmall = new BlockTrunk("small");
		GameRegistry.registerBlock(trunkSmall, "trunk.small");
		trunkTiny = new BlockTrunk("tiny");
		GameRegistry.registerBlock(trunkTiny, "trunk.tiny");
		
		//Machines
		machineKiln = new BlockKiln();
		GameRegistry.registerBlock(machineKiln, ItemBlockForestday.class, "machine.kiln");
		
		machineFurnace = new BlockFurnace();
		GameRegistry.registerBlock(machineFurnace, ItemBlockForestday.class, "machine.furnace");

		machineGenerator = new BlockGenerator();
		GameRegistry.registerBlock(machineGenerator, ItemBlockForestday.class, "machine.generator");
		
		machineFile = new BlockSaw();
		GameRegistry.registerBlock(machineFile, "machine.file");
		
		machineFan = new BlockFan();
		GameRegistry.registerBlock(machineFan, "machine.fan");
		
		heatCoil = new BlockCoilHeat(); 
		GameRegistry.registerBlock(heatCoil, ItemBlockCoilHeat.class, "structure.coil");
		grindingCoil = new BlockCoilGrinding(); 
		GameRegistry.registerBlock(grindingCoil, ItemBlockCoilGrinding.class, "structure.coil.grinding");
		
		//Metal
		metal = new BlockMetal();
		GameRegistry.registerBlock(metal, ItemBlockForestday.class, "structure.metal");
		busMetal = new BlockBusMetalItem(); 
		GameRegistry.registerBlock(busMetal, ItemBlockForestday.class, "structure.metal.bus");
		busMetalFluid = new BlockBusMetalFluid(); 
		GameRegistry.registerBlock(busMetalFluid, ItemBlockForestday.class, "structure.metal.bus.fluid");
		
		//Bricks
		bricks = new BlockBricks();
		GameRegistry.registerBlock(bricks, ItemBlockForestday.class, "structure.brick");
		busBrick = new BlockBusItem(); 
		GameRegistry.registerBlock(busBrick, ItemBlockForestday.class, "structure.brick.bus");
		busBrickFluid = new BlockBusFluid(); 
		GameRegistry.registerBlock(busBrickFluid, ItemBlockForestday.class, "structure.brick.bus.fluid");
		
		airHeater = new BlockAirHeater();
		GameRegistry.registerBlock(airHeater, ItemBlockForestday.class, "machine.air.heater");
		
		alloySmelter = new BlockAlloySmelter();
		GameRegistry.registerBlock(alloySmelter, ItemBlockForestday.class, "machine.furnace.alloy");
		
		macerator = new BlockMacerator();
		GameRegistry.registerBlock(macerator, ItemBlockForestday.class, "machine.macerator");
		
		blastFurnace = new BlockBlastFurncae();
		GameRegistry.registerBlock(blastFurnace, ItemBlockForestday.class, "machine.furnace.blast");
		
		//kilnCharcoal = new BlockCharcoalKiln();
		//GameRegistry.registerBlock(kilnCharcoal, ItemBlockForestday.class, "machine.kiln.charcoal");
		
		workbench = new BlockWorkbench();
		GameRegistry.registerBlock(workbench, ItemBlockForestday.class, "machine.workbanch");
		registerTile();
		
	}
	
	public static void registerTile()
	{
		GameRegistry.registerTileEntity(TileKiln.class, "machine.kiln.resin");
		GameRegistry.registerTileEntity(TileSaw.class, "machine.file");
		GameRegistry.registerTileEntity(TileCokeFurnace.class, "machine.furnace.coke");
		GameRegistry.registerTileEntity(TileFluidHeater.class, "machine.furnace.fluidheater");
		GameRegistry.registerTileEntity(TileHeatGenerator.class, "machine.generator.burning");
		GameRegistry.registerTileEntity(TileFan.class, "machine.fan");
		
		GameRegistry.registerTileEntity(TileStructure.class, "structure");
		GameRegistry.registerTileEntity(TileBusItem.class, "structure.bus.item");
		GameRegistry.registerTileEntity(TileBusFluid.class, "structure.bus.fluid");
		GameRegistry.registerTileEntity(TileCoilHeat.class, "structure.coil.heat");
		GameRegistry.registerTileEntity(TileCoilGrinding.class, "structure.coil.grinding");
		GameRegistry.registerTileEntity(TileController.class, "structure.controller");
		GameRegistry.registerTileEntity(TileStructureBrick.class, "structure.brick");
		GameRegistry.registerTileEntity(TileStructureMetal.class, "structure.emtal");
		
		GameRegistry.registerTileEntity(TileAirHeaterController.class, "airheater.controller");
		GameRegistry.registerTileEntity(TileAirHeaterRegulator.class, "airheater.regulator");
		
		GameRegistry.registerTileEntity(TileAlloySmelterController.class, "alloysmelter.controller");
		GameRegistry.registerTileEntity(TileAlloySmelterRegulator.class, "alloysmelter.regulator");
		
		GameRegistry.registerTileEntity(TileMaceratorController.class, "macerator.controller");
		GameRegistry.registerTileEntity(TileMaceratorRegulator.class, "macerator.regulator");
		
		GameRegistry.registerTileEntity(TileBlastFurnaceController.class, "blastfurnace.controller");
		GameRegistry.registerTileEntity(TileBlastFurnaceRegulator.class, "blastfurnace.regulator");
		
		GameRegistry.registerTileEntity(TileCharcoalKiln.class, "kiln.charcoal");
		
		GameRegistry.registerTileEntity(TileWorkbench.class, "workbench");
		
	}
	
	public static Block oreBlock;
	public static Block metalBlock;
	public static Block loam;
	public static Block logNature;
	
	public static Block machineKiln;
	public static Block machineFan;
	public static Block machineFile;
	public static Block machineGenerator;
	public static Block machineFurnace;
	
	public static Block airHeater;
	public static Block alloySmelter;
	public static Block macerator;
	public static Block blastFurnace;
	public static Block kilnCharcoal;
	public static Block workbench;
	
	public static Block heatCoil;
	public static Block grindingCoil;
	public static Block bricks;
	public static Block metal;
	public static Block busBrick;
	public static Block busMetal;
	public static Block busMetalFluid;
	public static Block busBrickFluid;
	
	public static Block controller;
	
	public static Block trunkBig;
	public static Block trunkMedium;
	public static Block trunkSmall;
	public static Block trunkTiny;
	
}
