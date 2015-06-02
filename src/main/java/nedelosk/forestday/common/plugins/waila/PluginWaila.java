package nedelosk.forestday.common.plugins.waila;

import mcp.mobius.waila.api.IWailaDataProvider;
import mcp.mobius.waila.api.IWailaRegistrar;
import nedelosk.forestday.common.machines.brick.kiln.TileKiln;
import nedelosk.forestday.common.plugins.waila.provider.machines.ProviderTileKiln;
import nedelosk.forestday.common.plugins.waila.provider.structures.ProviderBusFluid;
import nedelosk.forestday.common.plugins.waila.provider.structures.ProviderTileCoil;
import nedelosk.forestday.common.plugins.waila.provider.structures.ProviderTileRegulatorGrinding;
import nedelosk.forestday.common.plugins.waila.provider.structures.ProviderTileRegulatorHeat;
import nedelosk.forestday.structure.base.blocks.tile.TileBusFluid;
import nedelosk.forestday.structure.base.blocks.tile.TileCoilHeat;
import nedelosk.forestday.structure.base.blocks.tile.TileRegulatorGrinding;
import nedelosk.forestday.structure.base.blocks.tile.TileRegulatorHeat;
import nedelosk.nedeloskcore.plugins.Plugin;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.Optional;
import cpw.mods.fml.common.event.FMLInterModComms;

public class PluginWaila extends Plugin {

	@Optional.Method(modid = "Waila")
	public static void register(IWailaRegistrar registrar){
		final IWailaDataProvider tileResin = new ProviderTileKiln();
		
		registrar.registerBodyProvider( tileResin, TileKiln.class);
		
		final IWailaDataProvider tileRegulatorHeat = new ProviderTileRegulatorHeat();
		
		registrar.registerBodyProvider( tileRegulatorHeat, TileRegulatorHeat.class);
		
		final IWailaDataProvider tileRegulatorGrinding = new ProviderTileRegulatorGrinding();
		
		registrar.registerBodyProvider( tileRegulatorGrinding, TileRegulatorGrinding.class);
		
		final IWailaDataProvider tileCoil = new ProviderTileCoil();
		
		registrar.registerBodyProvider( tileCoil, TileCoilHeat.class);
		
		final IWailaDataProvider tileBusFluid = new ProviderBusFluid();
		
		registrar.registerBodyProvider( tileBusFluid, TileBusFluid.class);

	}
	
	public void init(){
		if(Loader.isModLoaded("Waila"))
			FMLInterModComms.sendMessage( "Waila", "register", PluginWaila.class.getName() + ".register" );
	}
	
}
