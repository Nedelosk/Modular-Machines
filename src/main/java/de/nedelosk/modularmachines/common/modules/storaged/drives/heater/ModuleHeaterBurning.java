package de.nedelosk.modularmachines.common.modules.storaged.drives.heater;

import java.awt.Color;
import java.text.DecimalFormat;
import java.util.List;

import de.nedelosk.modularmachines.api.gui.IContainerBase;
import de.nedelosk.modularmachines.api.gui.Widget;
import de.nedelosk.modularmachines.api.modular.IModular;
import de.nedelosk.modularmachines.api.modular.handlers.IModularHandler;
import de.nedelosk.modularmachines.api.modules.handlers.IModulePage;
import de.nedelosk.modularmachines.api.modules.handlers.inventory.IModuleInventory;
import de.nedelosk.modularmachines.api.modules.handlers.inventory.IModuleInventoryBuilder;
import de.nedelosk.modularmachines.api.modules.handlers.inventory.slots.SlotModule;
import de.nedelosk.modularmachines.api.modules.items.IModuleContainer;
import de.nedelosk.modularmachines.api.modules.models.IModelHandler;
import de.nedelosk.modularmachines.api.modules.models.ModelHandlerStatus;
import de.nedelosk.modularmachines.api.modules.state.IModuleState;
import de.nedelosk.modularmachines.api.modules.state.IModuleStateClient;
import de.nedelosk.modularmachines.api.modules.storaged.drives.heaters.IModuleHeaterBurning;
import de.nedelosk.modularmachines.api.property.PropertyInteger;
import de.nedelosk.modularmachines.client.gui.widgets.WidgetBurning;
import de.nedelosk.modularmachines.common.modules.handlers.ModulePage;
import de.nedelosk.modularmachines.common.utils.Translator;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ModuleHeaterBurning extends ModuleHeater implements IModuleHeaterBurning {

	public static final PropertyInteger BURNTIME = new PropertyInteger("burnTime", 0);
	public static final PropertyInteger BURNTIMETOTAL = new PropertyInteger("burnTimeTotal", 0);

	public ModuleHeaterBurning() {
		super("heater.burning");
	}

	@Override
	public IModuleState createState(IModular modular, IModuleContainer container) {
		return super.createState(modular, container).register(BURNTIME).register(BURNTIMETOTAL);
	}

	@SideOnly(Side.CLIENT)
	@Override
	public boolean needHandlerReload(IModuleStateClient state) {
		IModelHandler handler = state.getModelHandler();
		if(handler instanceof ModelHandlerStatus){
			ModelHandlerStatus status = (ModelHandlerStatus) handler;
			if(getBurnTime(state) > 0){
				if(!status.status){
					status.status = true;
					return true;
				}
			}else{
				if(status.status){
					status.status = false;
					return true;
				}
			}
		}
		return false;
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
	protected boolean canAddHeat(IModuleState state) {
		return getBurnTime(state) > 0;
	}

	@Override
	protected void afterAddHeat(IModuleState state) {
		addBurnTime(state, -25 * getSize(state.getContainer()).ordinal());
	}

	@Override
	protected boolean updateFuel(IModuleState state) {
		IModuleInventory inventory = (IModuleInventory) state.getContentHandler(IModuleInventory.class);
		ItemStack input = inventory.getStackInSlot(0);

		if(input != null){
			if(inventory.extractItemInternal(0, 1, false) != null){
				setBurnTime(state, TileEntityFurnace.getItemBurnTime(input));
				state.set(BURNTIMETOTAL, TileEntityFurnace.getItemBurnTime(input));
				return true;
			}
		}
		return false;
	}

	@Override
	public List<IModulePage> createPages(IModuleState state) {
		List<IModulePage> pages = super.createPages(state);
		pages.add(new HeaterBurningPage("Basic", state));
		return pages;
	}

	@Override
	public int getColor(IModuleContainer container) {
		return 0x615524;
	}

	@Override
	public boolean isWorking(IModuleState state) {
		return getBurnTime(state) > 0;
	}

	public class HeaterBurningPage extends ModulePage<IModuleHeaterBurning>{

		public HeaterBurningPage(String pageID, IModuleState<IModuleHeaterBurning> heaterState) {
			super(pageID, "heater", heaterState);
		}

		@Override
		public void addWidgets() {
			add(new WidgetBurning(80, 18, 0, 0));
		}

		@Override
		protected void onUpdateWidget(Widget widget) {
			super.onUpdateWidget(widget);
			if (widget instanceof WidgetBurning) {
				WidgetBurning widgetBurning = (WidgetBurning) widget;
				widgetBurning.burnTime = state.get(BURNTIME);
				widgetBurning.burnTimeTotal = state.get(BURNTIMETOTAL);
			}
		}

		@Override
		public void drawForeground(FontRenderer fontRenderer, int mouseX, int mouseY) {
			super.drawForeground(fontRenderer, mouseX, mouseY);
			DecimalFormat f = new DecimalFormat("#0.00"); 

			String heatName = Translator.translateToLocalFormatted("module.heater.heat", f.format(state.getModular().getHeatSource().getHeatStored()));
			fontRenderer.drawString(heatName, 90 - (fontRenderer.getStringWidth(heatName) / 2),55, Color.gray.getRGB());
		}

		@Override
		public void createInventory(IModuleInventoryBuilder invBuilder) {
			invBuilder.addInventorySlot(true, 80, 35, new ItemFliterFurnaceFuel());
		}

		@Override
		public void createSlots(IContainerBase<IModularHandler> container, List<SlotModule> modularSlots) {
			modularSlots.add(new SlotModule(state, 0));
		}
	}
}
