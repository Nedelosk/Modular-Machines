package de.nedelosk.modularmachines.common.modules.heater;

import java.awt.Color;
import java.util.List;

import de.nedelosk.modularmachines.api.Translator;
import de.nedelosk.modularmachines.api.gui.IContainerBase;
import de.nedelosk.modularmachines.api.modular.IModular;
import de.nedelosk.modularmachines.api.modular.IModularHandler;
import de.nedelosk.modularmachines.api.modular.ModularHelper;
import de.nedelosk.modularmachines.api.modules.EnumModuleSize;
import de.nedelosk.modularmachines.api.modules.IModuleCasing;
import de.nedelosk.modularmachines.api.modules.IModuleContainer;
import de.nedelosk.modularmachines.api.modules.IModuleModelHandler;
import de.nedelosk.modularmachines.api.modules.IModuleState;
import de.nedelosk.modularmachines.api.modules.handlers.IModulePage;
import de.nedelosk.modularmachines.api.modules.handlers.inventory.IModuleInventory;
import de.nedelosk.modularmachines.api.modules.handlers.inventory.IModuleInventoryBuilder;
import de.nedelosk.modularmachines.api.modules.handlers.inventory.slots.SlotModule;
import de.nedelosk.modularmachines.api.modules.handlers.tank.IModuleTank;
import de.nedelosk.modularmachines.api.modules.handlers.tank.IModuleTankBuilder;
import de.nedelosk.modularmachines.api.modules.heater.IModuleHeaterBurning;
import de.nedelosk.modularmachines.api.property.PropertyInteger;
import de.nedelosk.modularmachines.client.gui.widgets.WidgetFluidTank;
import de.nedelosk.modularmachines.client.modules.ModelHandlerDefault;
import de.nedelosk.modularmachines.common.modules.handlers.FluidFilterSteam;
import de.nedelosk.modularmachines.common.modules.handlers.ItemFluidFilter;
import de.nedelosk.modularmachines.common.modules.handlers.ModulePage;
import de.nedelosk.modularmachines.common.modules.handlers.OutputAllFilter;
import de.nedelosk.modularmachines.common.network.PacketHandler;
import de.nedelosk.modularmachines.common.network.packets.PacketModule;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ModuleHeaterSteam extends ModuleHeater {

	public static final PropertyInteger BURNTIME = new PropertyInteger("burnTime", 0);
	public static final PropertyInteger BURNTIMETOTAL = new PropertyInteger("burnTimeTotal", 0);

	public ModuleHeaterSteam(int complexity, int maxHeat, EnumModuleSize size) {
		super("heater.steam", complexity, maxHeat, size);
	}

	@Override
	public IModuleState createState(IModular modular, IModuleContainer container) {
		return super.createState(modular, container).register(BURNTIME).register(BURNTIMETOTAL);
	}

	@SideOnly(Side.CLIENT)
	@Override
	public IModuleModelHandler getModelHandler(IModuleState state) {
		return new ModelHandlerDefault(new ResourceLocation("modularmachines:module/heaters/" + state.getContainer().getMaterial().getName() + "_" + size.getName() + (getBurnTime(state) > 0 ? "_on" : "_off")));
	}

	public int getBurnTime(IModuleState state) {
		return state.get(BURNTIME);
	}

	public void setBurnTime(IModuleState state, int burnTime) {
		state.set(BURNTIME, burnTime);
	}

	public void addBurnTime(IModuleState state, int burnTime) {
		state.set(BURNTIME, state.get(BURNTIME) + burnTime);
	}

	@Override
	public void updateServer(IModuleState state, int tickCount) {
		if(state.getModular().updateOnInterval(20)){
			IModuleInventory inventory = (IModuleInventory) state.getContentHandler(IModuleInventory.class);
			IModuleTank tank = (IModuleTank) state.getContentHandler(IModuleTank.class);
			if(inventory != null){
				if(inventory.getStackInSlot(0) != null){
					ItemStack stack = inventory.getStackInSlot(0);
					IFluidHandler fludiHandler = stack.getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, null);
					ItemStack containerStack = FluidUtil.tryEmptyContainer(stack, tank.getTank(0), 1000, null, false);
					if(containerStack != null){
						if(inventory.extractItem(0, 1, true) != null){
							if(inventory.insertItem(1, containerStack, true) == null){
								inventory.insertItem(1, FluidUtil.tryEmptyContainer(stack, tank.getTank(0), 1000, null, true), false);
								inventory.extractItem(0, 1, false);
							}
						}
					}
				}
			}

			boolean needUpdate = false;
			IModuleState<IModuleCasing> casingState = ModularHelper.getCasing(state.getModular());
			if (getBurnTime(state) > 0) {
				if(casingState.getModule().getHeat(casingState) < maxHeat){
					casingState.getModule().addHeat(casingState, 5);
				}
				addBurnTime(state, -10);
				needUpdate = true;
			} else {
				List<IModulePage> pages = state.getPages();
				FluidStack input = tank.getTank(0).getFluid();
				if(input == null){
					if(casingState.getModule().getHeat(casingState) > 0){
						casingState.getModule().addHeat(casingState, -1);
						needUpdate = true;
					}
				}else{
					if(tank.drainInternal(80, true) != null){
						setBurnTime(state, 50);
						state.set(BURNTIMETOTAL, 50);
						needUpdate = true;
					}
				}
			}
			if(needUpdate){
				IModularHandler handler = state.getModular().getHandler();
				PacketHandler.INSTANCE.sendToAll(new PacketModule(handler, state));
				PacketHandler.INSTANCE.sendToAll(new PacketModule(handler, casingState));
			}
		}
	}

	@Override
	public List<IModulePage> createPages(IModuleState state) {
		List<IModulePage> pages = super.createPages(state);
		pages.add(new HeaterSteamPage("Basic", state));
		return pages;
	}

	@Override
	public int getColor() {
		return 0x6E593C;
	}

	public class HeaterSteamPage extends ModulePage<IModuleHeaterBurning>{

		public HeaterSteamPage(String pageID, IModuleState<IModuleHeaterBurning> heaterState) {
			super(pageID, "heater", heaterState);
		}

		@Override
		public void addWidgets(List widgets) {
			widgets.add(new WidgetFluidTank(state.getContentHandler(IModuleTank.class).getTank(0)));
		}

		@Override
		public void drawForeground(FontRenderer fontRenderer, int mouseX, int mouseY) {
			super.drawForeground(fontRenderer, mouseX, mouseY);

			IModuleState<IModuleCasing> casingState = state.getModular().getModules(IModuleCasing.class).get(0);
			String heatName = Translator.translateToLocalFormatted("module.heater.heat", casingState.getModule().getHeat(casingState));
			fontRenderer.drawString(heatName, 135 - (fontRenderer.getStringWidth(heatName) / 2),45, Color.gray.getRGB());
		}

		@Override
		public void createInventory(IModuleInventoryBuilder invBuilder) {
			invBuilder.addInventorySlot(true, 15, 28, new ItemFluidFilter(true));
			invBuilder.addInventorySlot(false, 15, 48, new OutputAllFilter());
		}

		@Override
		public void createSlots(IContainerBase<IModularHandler> container, List<SlotModule> modularSlots) {
			modularSlots.add(new SlotModule(state, 0).setBackgroundTexture("liquid"));
			modularSlots.add(new SlotModule(state, 1).setBackgroundTexture("container"));
		}

		@Override
		public void createTank(IModuleTankBuilder tankBuilder) {
			tankBuilder.addFluidTank(16000, true, 80, 18, new FluidFilterSteam());
		}
	}
}