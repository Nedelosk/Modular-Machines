package de.nedelosk.modularmachines.common.modules.heater;

import java.awt.Color;
import java.util.List;

import de.nedelosk.modularmachines.api.inventory.IContainerBase;
import de.nedelosk.modularmachines.api.modular.IModular;
import de.nedelosk.modularmachines.api.modular.IModularHandler;
import de.nedelosk.modularmachines.api.modular.ModularHelper;
import de.nedelosk.modularmachines.api.modules.IModuleCasing;
import de.nedelosk.modularmachines.api.modules.IModuleContainer;
import de.nedelosk.modularmachines.api.modules.handlers.IModulePage;
import de.nedelosk.modularmachines.api.modules.handlers.inventory.IModuleInventory;
import de.nedelosk.modularmachines.api.modules.handlers.inventory.IModuleInventoryBuilder;
import de.nedelosk.modularmachines.api.modules.handlers.inventory.slots.SlotModule;
import de.nedelosk.modularmachines.api.modules.heater.IModuleHeaterBurning;
import de.nedelosk.modularmachines.api.modules.state.IModuleState;
import de.nedelosk.modularmachines.api.property.PropertyInteger;
import de.nedelosk.modularmachines.client.gui.Widget;
import de.nedelosk.modularmachines.client.gui.widgets.WidgetBurning;
import de.nedelosk.modularmachines.common.modules.handlers.ModulePage;
import de.nedelosk.modularmachines.common.network.PacketHandler;
import de.nedelosk.modularmachines.common.network.packets.PacketModule;
import de.nedelosk.modularmachines.common.utils.Translator;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityFurnace;

public class ModuleHeaterBurning extends ModuleHeater implements IModuleHeaterBurning {

	public static final PropertyInteger BURNTIME = new PropertyInteger("burnTime", 0);
	public static final PropertyInteger BURNTIMETOTAL = new PropertyInteger("burnTimeTotal", 0);

	public ModuleHeaterBurning(int maxHeat, int size) {
		super(maxHeat, size);
	}

	@Override
	public IModuleState createState(IModular modular, IModuleContainer container) {
		return super.createState(modular, container).register(BURNTIME).register(BURNTIMETOTAL);
	}

	@Override
	public int getBurnTime(IModuleState state) {
		return state.get(BURNTIME);
	}

	@Override
	public void setBurnTime(IModuleState state, int burnTime) {
		state.set(BURNTIME, burnTime);
	}

	@Override
	public void addBurnTime(IModuleState state, int burnTime) {
		state.set(BURNTIME, state.get(BURNTIME) + burnTime);
	}

	@Override
	public void updateServer(IModuleState moduleState, int tickCount) {
		if(moduleState.getModular().updateOnInterval(20)){
			boolean needUpdate = false;
			IModuleState<IModuleCasing> casingState = ModularHelper.getCasing(moduleState.getModular());
			if (getBurnTime(moduleState) > 0) {
				if(casingState.getModule().getHeat(casingState) < maxHeat){
					casingState.getModule().addHeat(casingState, 5);
				}
				addBurnTime(moduleState, -25);
				needUpdate = true;
			} else {
				List<IModulePage> pages = moduleState.getPages();
				IModuleInventory inventory = (IModuleInventory) moduleState.getContentHandler(IModuleInventory.class);
				ItemStack input = inventory.getStackInSlot(((HeaterBurningPage)pages.get(0)).BURNSLOT);
				if(input == null){
					if(casingState.getModule().getHeat(casingState) > 0){
						casingState.getModule().addHeat(casingState, -1);
						needUpdate = true;
					}
				}else if (input != null) {
					if(inventory.extractItem(((HeaterBurningPage)pages.get(0)).BURNSLOT, 1, false) != null){
						setBurnTime(moduleState, TileEntityFurnace.getItemBurnTime(input));
						moduleState.set(BURNTIMETOTAL, TileEntityFurnace.getItemBurnTime(input));
						needUpdate = true;
					}
				}
			}
			if(needUpdate){
				PacketHandler.INSTANCE.sendToAll(new PacketModule(moduleState.getModular().getHandler(), moduleState));
			}
		}
	}

	@Override
	public List<IModulePage> createPages(IModuleState state) {
		List<IModulePage> pages = super.createPages(state);
		pages.add(new HeaterBurningPage("Basic", state));
		return pages;
	}
	
	@Override
	public int getColor() {
		return 0x6E593C;
	}

	public class HeaterBurningPage extends ModulePage<IModuleHeaterBurning>{

		public int BURNSLOT;

		public HeaterBurningPage(String pageID, IModuleState<IModuleHeaterBurning> heaterState) {
			super(pageID, "heater", heaterState);
		}

		@Override
		public void addWidgets(List widgets) {
			widgets.add(new WidgetBurning(80, 18, 0, 0));
		}

		@Override
		public void updateGui(int x, int y) {
			List<Widget> widgets = gui.getWidgetManager().getWidgets();
			for(Widget widget : widgets) {
				if (widget instanceof WidgetBurning) {
					WidgetBurning widgetBurning = (WidgetBurning) widget;
					widgetBurning.burnTime = state.get(BURNTIME);
					widgetBurning.burnTimeTotal = state.get(BURNTIMETOTAL);
				}
			}
		}
		
		@Override
		public void drawForeground(FontRenderer fontRenderer, int mouseX, int mouseY) {
			super.drawForeground(fontRenderer, mouseX, mouseY);
			
			IModuleState<IModuleCasing> casingState = state.getModular().getModules(IModuleCasing.class).get(0);
			String heatName = Translator.translateToLocalFormatted("module.heater.heat", casingState.getModule().getHeat(casingState));
			fontRenderer.drawString(heatName, 90 - (fontRenderer.getStringWidth(heatName) / 2),55, Color.gray.getRGB());
		}

		@Override
		public void createInventory(IModuleInventoryBuilder invBuilder) {
			BURNSLOT = invBuilder.addInventorySlot(true, 80, 35, new ItemFliterFurnaceFuel());
		}

		@Override
		public void createSlots(IContainerBase<IModularHandler> container, List<SlotModule> modularSlots) {
			modularSlots.add(new SlotModule(state, BURNSLOT));
		}

	}
}
