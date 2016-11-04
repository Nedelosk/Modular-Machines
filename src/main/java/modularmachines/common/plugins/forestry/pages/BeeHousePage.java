package modularmachines.common.plugins.forestry.pages;

import java.io.IOException;
import java.util.List;

import forestry.core.config.Constants;
import forestry.core.render.EnumTankLevel;
import modularmachines.api.modules.handlers.inventory.IModuleInventoryBuilder;
import modularmachines.api.modules.network.DataInputStreamMM;
import modularmachines.api.modules.network.DataOutputStreamMM;
import modularmachines.api.modules.network.IStreamable;
import modularmachines.api.modules.state.IModuleState;
import modularmachines.common.modules.pages.MainPage;
import modularmachines.common.network.PacketHandler;
import modularmachines.common.network.packets.IPacketClient;
import modularmachines.common.network.packets.PacketUpdateModule;
import modularmachines.common.plugins.forestry.ModuleBeeHouse;
import modularmachines.common.plugins.forestry.handlers.BeeHouseHandler;
import modularmachines.common.plugins.forestry.handlers.ItemFilterBee;
import modularmachines.common.utils.Log;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IContainerListener;
import net.minecraft.inventory.Slot;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BeeHousePage extends MainPage<ModuleBeeHouse> implements IStreamable {

	private int previousBreedingProgressPercent = 0;

	public BeeHousePage(String title, IModuleState<ModuleBeeHouse> moduleState) {
		super(title, moduleState);
	}

	@Override
	public int getYSize() {
		return 190;
	}

	@Override
	public int getPlayerInvPosition() {
		return 107;
	}

	@Override
	protected void createInventory(IModuleInventoryBuilder invBuilder) {
		// Queen/Princess
		invBuilder.addInventorySlot(true, 29, 39, new ItemFilterBee(false));
		// Drone
		invBuilder.addInventorySlot(true, 29, 65, new ItemFilterBee(true));
		// Product Inventory
		invBuilder.addInventorySlot(false, 116, 52);
		invBuilder.addInventorySlot(false, 137, 39);
		invBuilder.addInventorySlot(false, 137, 65);
		invBuilder.addInventorySlot(false, 116, 78);
		invBuilder.addInventorySlot(false, 95, 65);
		invBuilder.addInventorySlot(false, 95, 39);
		invBuilder.addInventorySlot(false, 116, 26);
	}

	@Override
	public void writeData(DataOutputStreamMM data) throws IOException {
		BeeHouseHandler housing = moduleState.getContentHandler(BeeHouseHandler.class);
		data.writeInt(housing.getBeekeepingLogic().getBeeProgressPercent());
	}

	@Override
	public void readData(DataInputStreamMM data) throws IOException {
		BeeHouseHandler housing = moduleState.getContentHandler(BeeHouseHandler.class);
		housing.setBreedingProgressPercent(data.readInt());
	}

	@Override
	public void detectAndSendChanges() {
		BeeHouseHandler housing = moduleState.getContentHandler(BeeHouseHandler.class);
		if (housing != null) {
			int breedingProgressPercen = housing.getBeekeepingLogic().getBeeProgressPercent();
			if (previousBreedingProgressPercent != breedingProgressPercen) {
				previousBreedingProgressPercent = breedingProgressPercen;
				sendPacketToListeners(new PacketUpdateModule(moduleState));
			}
		}
	}

	protected final void sendPacketToListeners(IPacketClient packet) {
		for(IContainerListener listener : (List<IContainerListener>) container.getListeners()) {
			if (listener instanceof EntityPlayer) {
				PacketHandler.sendToPlayer(packet, (EntityPlayer) listener);
			} else {
				Log.err("Unknown listener type: {}", listener);
			}
		}
	}

	@Override
	public void drawBackground(int mouseX, int mouseY) {
		super.drawBackground(mouseX, mouseY);
		BeeHouseHandler housing = moduleState.getContentHandler(BeeHouseHandler.class);
		drawHealthMeter(gui.getGuiLeft() + 20, gui.getGuiTop() + 37, housing.getHealthScaled(46), EnumTankLevel.rateTankLevel(housing.getHealthScaled(100)));
	}

	private void drawHealthMeter(int x, int y, int height, EnumTankLevel rated) {
		int i = 176 + rated.getLevelScaled(16);
		int k = 0;
		gui.getGui().drawTexturedModalRect(x, y + 46 - height, i, k + 46 - height, 4, height);
	}

	@Override
	protected void drawSlot(Slot slot) {
	}

	@Override
	protected ResourceLocation getInventoryTexture() {
		return null;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public ResourceLocation getGuiTexture() {
		return new ResourceLocation(Constants.MOD_ID, Constants.TEXTURE_PATH_GUI + "/alveary.png");
	}
}
