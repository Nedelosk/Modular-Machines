package nedelosk.modularmachines.common.inventory.assembler;

import java.util.ArrayList;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import nedelosk.forestcore.api.inventory.ContainerBase;
import nedelosk.modularmachines.api.modular.assembler.AssemblerMachineInfo;
import nedelosk.modularmachines.api.modular.basic.IModularItem;
import nedelosk.modularmachines.api.packets.PacketHandler;
import nedelosk.modularmachines.client.gui.assembler.GuiButtonAssembler;
import nedelosk.modularmachines.client.gui.assembler.GuiModularAssembler;
import nedelosk.modularmachines.common.blocks.tile.TileModularAssembler;
import nedelosk.modularmachines.common.inventory.slots.SlotAssemblerIn;
import nedelosk.modularmachines.common.inventory.slots.SlotAssemblerOut;
import nedelosk.modularmachines.common.modular.utils.MachineBuilder;
import nedelosk.modularmachines.common.network.packets.PacketAssembler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.world.WorldServer;

public class ContainerModularAssembler extends ContainerBase<TileModularAssembler> {

	public SlotAssemblerOut out;
	public IInventory inventory;
	protected AssemblerMachineInfo info;
	protected int activeSlots;

	public ContainerModularAssembler(TileModularAssembler tile, InventoryPlayer inventory) {
		super(tile, inventory);
		this.inventory = inventory;
		this.info = GuiButtonAssembler.info;
	}

	public void syncOnOpen(EntityPlayerMP playerOpened) {
		WorldServer server = playerOpened.getServerForPlayer();
		for (EntityPlayer player : (ArrayList<EntityPlayer>) server.playerEntities) {
			if (player == playerOpened)
				continue;
			if (player.openContainer instanceof ContainerModularAssembler) {
				if (sameGui((ContainerBase<TileModularAssembler>) player.openContainer)) {
					syncWithOtherContainer((ContainerBase<TileModularAssembler>) player.openContainer, playerOpened);
					return;
				}
			}
		}
		syncNewContainer(playerOpened);
	}

	@Override
	protected void addSlots(InventoryPlayer inventory) {
		int i;
		for (i = 0; i < inventoryBase.getSizeInventory() - 1; i++) {
			addSlotToContainer(new SlotAssemblerIn(inventoryBase, i, 0, 0, this));
		}

		// output slot
		out = new SlotAssemblerOut(i, 234, 38, this);
		addSlotToContainer(out);
		onCraftMatrixChanged(null);
	}

	protected void syncNewContainer(EntityPlayerMP player) {
		this.activeSlots = inventoryBase.getSizeInventory() - 1;
		PacketHandler.INSTANCE.sendTo(new PacketAssembler(inventoryBase, GuiButtonAssembler.info, inventoryBase.getSizeInventory() - 1), player);
		onCraftMatrixChanged(null);
	}

	protected void syncWithOtherContainer(ContainerBase<TileModularAssembler> otherContainer, EntityPlayerMP player) {
		this.syncWithOtherContainer((ContainerModularAssembler) otherContainer, player);
	}

	protected void syncWithOtherContainer(ContainerModularAssembler otherContainer, EntityPlayerMP player) {
		this.setToolSelection(otherContainer.info, otherContainer.activeSlots);
		PacketHandler.INSTANCE.sendTo(new PacketAssembler(inventoryBase, otherContainer.info, otherContainer.activeSlots), player);
	}

	public void setToolSelection(AssemblerMachineInfo info, int activeSlots) {
		if (activeSlots > inventoryBase.getSizeInventory() - 1)
			activeSlots = inventoryBase.getSizeInventory() - 1;

		this.activeSlots = activeSlots;
		this.info = info;

		for (int i = 0; i < inventoryBase.getSizeInventory() - 1; i++) {
			Slot slot = (Slot) inventorySlots.get(i + 36);
			if (slot instanceof SlotAssemblerIn) {
				SlotAssemblerIn slotToolPart = (SlotAssemblerIn) slot;

				if (i >= activeSlots) {
					slotToolPart.deactivate();
				} else {
					slotToolPart.activate();
				}
			}
		}
		onCraftMatrixChanged(null);
	}

	@Override
	public void onCraftMatrixChanged(IInventory inventoryIn) {
		updateGUI();
		out.inventory.setInventorySlotContents(inventoryBase.getSizeInventory() - 1, buildMachine());
	}

	public void updateGUI() {
		if (inventoryBase.getWorldObj().isRemote) {
			ContainerModularAssembler.clientGuiUpdate();
		}
	}

	@SideOnly(Side.CLIENT)
	private static void clientGuiUpdate() {
		GuiScreen screen = Minecraft.getMinecraft().currentScreen;
		if (screen instanceof GuiModularAssembler) {
			((GuiModularAssembler) screen).updateDisplay();
		}
	}

	public void onResultTaken(EntityPlayer playerIn, ItemStack stack) {
		for (int i = 0; i < inventoryBase.getSizeInventory() - 1; i++) {
			inventoryBase.decrStackSize(i, 1);
		}
		onCraftMatrixChanged(null);
	}

	private ItemStack buildMachine() {
		ItemStack[] input = new ItemStack[activeSlots];
		for (int i = 0; i < activeSlots; i++) {
			input[i] = inventoryBase.getStackInSlot(i);
		}
		if (info == null)
			return null;
		if (info.machine == null)
			return null;
		return MachineBuilder.buildMachineItem(input, ((IModularItem) info.machine.getItem()).getMachineName(), inventoryBase.tier, info.machine);
	}

	@Override
	protected void addInventory(InventoryPlayer inventory) {
		for (int i1 = 0; i1 < 3; i1++) {
			for (int l1 = 0; l1 < 9; l1++) {
				addSlotToContainer(new Slot(inventory, l1 + i1 * 9 + 9, 8 + l1 * 18 + 110, 92 + i1 * 18));
			}
		}

		for (int j1 = 0; j1 < 9; j1++) {
			addSlotToContainer(new Slot(inventory, j1, 8 + j1 * 18 + 110, 150));
		}
	}

}
