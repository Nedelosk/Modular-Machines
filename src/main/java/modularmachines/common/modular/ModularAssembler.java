package modularmachines.common.modular;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.ClickType;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.play.server.SPacketSetSlot;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.WorldServer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

import modularmachines.api.modular.AssemblerException;
import modularmachines.api.modular.AssemblerItemHandler;
import modularmachines.api.modular.IModular;
import modularmachines.api.modular.IModularAssembler;
import modularmachines.api.modular.handlers.IModularHandler;
import modularmachines.api.modular.handlers.IModularHandlerTileEntity;
import modularmachines.api.modules.IModule;
import modularmachines.api.modules.IModuleCasing;
import modularmachines.api.modules.IModuleProperties;
import modularmachines.api.modules.ModuleManager;
import modularmachines.api.modules.containers.IModuleContainer;
import modularmachines.api.modules.containers.IModuleItemContainer;
import modularmachines.api.modules.controller.IModuleController;
import modularmachines.api.modules.position.IStoragePosition;
import modularmachines.api.modules.state.IModuleState;
import modularmachines.api.modules.storage.IItemHandlerStorage;
import modularmachines.api.modules.storage.IStorage;
import modularmachines.api.modules.storage.IStorageModule;
import modularmachines.api.modules.storage.IStoragePage;
import modularmachines.api.modules.storage.module.IModuleModuleStorage;
import modularmachines.api.modules.storage.module.IModuleStorage;
import modularmachines.client.gui.GuiAssembler;
import modularmachines.common.config.Config;
import modularmachines.common.core.ModularMachines;
import modularmachines.common.inventory.ContainerAssembler;
import modularmachines.common.network.PacketHandler;
import modularmachines.common.network.packets.PacketSyncHandlerState;
import modularmachines.common.utils.Translator;

public class ModularAssembler implements IModularAssembler {

	protected final AssemblerItemHandler itemHandler;
	protected final Map<IStoragePosition, IStoragePage> pages;
	protected final List<IStoragePosition> indexes;
	protected final IModularHandler modularHandler;
	protected boolean hasChange = false;
	protected IStoragePosition selectedPosition;

	public ModularAssembler(IModularHandler modularHandler, ItemStack[] stacks, Map<IStoragePosition, IStoragePage> pages) {
		this(modularHandler, new AssemblerItemHandler(stacks, null, null), pages);
		this.itemHandler.setAssembler(this);
	}

	public ModularAssembler(IModularHandler modularHandler, Map<IStoragePosition, IStoragePage> pages) {
		this(modularHandler, new AssemblerItemHandler(pages.size(), null), pages);
		this.itemHandler.setAssembler(this);
	}

	public ModularAssembler(IModularHandler modularHandler) {
		this(modularHandler, createEmptyPages(modularHandler));
	}

	public ModularAssembler(IModularHandler modularHandler, NBTTagCompound nbtTag) {
		this(modularHandler, createEmptyPages(modularHandler));
		deserializeNBT(nbtTag);
	}

	public ModularAssembler(IModularHandler modularHandler, AssemblerItemHandler itemHandler, Map<IStoragePosition, IStoragePage> pages) {
		this.modularHandler = modularHandler;
		this.pages = pages;
		this.itemHandler = itemHandler;
		this.indexes = modularHandler.getPositions().asList();
		this.selectedPosition = indexes.get(0);
		updatePages(null);
	}

	private static Map<IStoragePosition, IStoragePage> createEmptyPages(IModularHandler modularHandler) {
		Map<IStoragePosition, IStoragePage> pages = new HashMap<>();
		for (IStoragePosition position : (List<IStoragePosition>) modularHandler.getPositions().asList()) {
			pages.put(position, null);
		}
		return pages;
	}

	@Override
	public NBTTagCompound serializeNBT() {
		NBTTagCompound tagCompound = new NBTTagCompound();
		if (itemHandler != null) {
			tagCompound.setTag("itemHandler", CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.getStorage().writeNBT(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, itemHandler, null));
		}
		NBTTagList list = new NBTTagList();
		for (Entry<IStoragePosition, IStoragePage> entry : pages.entrySet()) {
			if (entry.getValue() != null) {
				NBTTagCompound nbtTag = new NBTTagCompound();
				nbtTag.setInteger("Index", getIndex(entry.getKey()));
				nbtTag.setTag("Page", entry.getValue().serializeNBT());
				list.appendTag(nbtTag);
			}
		}
		tagCompound.setTag("Pages", list);
		return tagCompound;
	}

	@Override
	public void deserializeNBT(NBTTagCompound nbt) {
		if (nbt.hasKey("itemHandler") && itemHandler != null) {
			CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.getStorage().readNBT(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, itemHandler, null, nbt.getTag("itemHandler"));
		}
		updatePages(null);
		NBTTagList list = nbt.getTagList("Pages", 10);
		for (int i = 0; i < list.tagCount(); i++) {
			NBTTagCompound nbtTag = list.getCompoundTagAt(i);
			IStoragePosition position = indexes.get(nbtTag.getInteger("Index"));
			pages.get(position).deserializeNBT(nbtTag.getCompoundTag("Page"));
		}
	}

	@Override
	public IModularHandler getHandler() {
		return modularHandler;
	}

	@Override
	public void setSelectedPosition(IStoragePosition position) {
		this.selectedPosition = position;
	}

	@Override
	public IStoragePosition getSelectedPosition() {
		return selectedPosition;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public GuiContainer createGui(InventoryPlayer inventory) {
		return new GuiAssembler(modularHandler, inventory);
	}

	@Override
	public Container createContainer(InventoryPlayer inventory) {
		return new ContainerAssembler(modularHandler, inventory);
	}

	protected void testComplexity() throws AssemblerException {
		int complexity = getComplexity(true, null);
		int allowedComplexity = getAllowedComplexity(null);
		if (complexity > allowedComplexity) {
			if (allowedComplexity == Config.defaultAllowedComplexity) {
				throw new AssemblerException(Translator.translateToLocalFormatted("modular.assembler.error.no.controller"));
			}
			throw new AssemblerException(Translator.translateToLocalFormatted("modular.assembler.error.complexity"));
		}
	}

	@Override
	public int getComplexity(boolean withStorage, IStoragePosition position) {
		int complexity = 0;
		if (position == null) {
			for (IStoragePosition otherPosition : indexes) {
				ItemStack storageStack = itemHandler.getStackInSlot(getIndex(otherPosition));
				if (storageStack != null) {
					IModuleItemContainer itemContainer = ModuleManager.getContainerFromItem(storageStack);
					if (itemContainer != null) {
						for (IModuleContainer container : itemContainer.getContainers()) {
							IModule module = container.getModule();
							if (withStorage || !(module instanceof IStorageModule)) {
								complexity += module.getComplexity(container);
							}
						}
					}
				}
				IStoragePage page = pages.get(otherPosition);
				if (page != null) {
					IItemHandler assemblerHandler = page.getItemHandler();
					for (int index = 0; index < assemblerHandler.getSlots(); index++) {
						ItemStack stack = assemblerHandler.getStackInSlot(index);
						if (stack != null) {
							IModuleItemContainer itemContainer = ModuleManager.getContainerFromItem(stack);
							if (itemContainer != null) {
								for (IModuleContainer container : itemContainer.getContainers()) {
									IModule module = container.getModule();
									if (module instanceof IModuleModuleStorage && !withStorage) {
										continue;
									}
									complexity += module.getComplexity(container);
								}
							}
						}
					}
				}
			}
		} else {
			ItemStack storageStack = itemHandler.getStackInSlot(getIndex(position));
			if (storageStack != null) {
				IModuleItemContainer itemContainer = ModuleManager.getContainerFromItem(storageStack);
				if (itemContainer != null) {
					for (IModuleContainer container : itemContainer.getContainers()) {
						IModule module = container.getModule();
						if (withStorage || !(module instanceof IStorageModule)) {
							complexity += module.getComplexity(container);
						}
					}
				}
			}
			if (pages.get(position) != null) {
				IItemHandler assemblerHandler = pages.get(position).getItemHandler();
				for (int index = 0; index < assemblerHandler.getSlots(); index++) {
					ItemStack slotStack = assemblerHandler.getStackInSlot(index);
					if (slotStack != null) {
						IModuleItemContainer itemContainer = ModuleManager.getContainerFromItem(slotStack);
						if (itemContainer != null) {
							for (IModuleContainer container : itemContainer.getContainers()) {
								IModule module = container.getModule();
								if (module instanceof IModuleModuleStorage && !withStorage) {
									continue;
								}
								complexity += module.getComplexity(container);
							}
						}
					}
				}
			}
		}
		return complexity;
	}

	@Override
	public int getAllowedComplexity(IStoragePosition position) {
		if (position == null) {
			for (IStoragePage page : pages.values()) {
				if (page != null) {
					IItemHandler assemblerHandler = page.getItemHandler();
					for (int index = 0; index < assemblerHandler.getSlots(); index++) {
						ItemStack stack = assemblerHandler.getStackInSlot(index);
						if (stack != null) {
							IModuleItemContainer itemContainer = ModuleManager.getContainerFromItem(stack);
							if (itemContainer != null) {
								for (IModuleContainer container : itemContainer.getContainers()) {
									IModule module = container.getModule();
									if (module instanceof IModuleController) {
										return ((IModuleController) module).getAllowedComplexity(container);
									}
								}
							}
						}
					}
				}
			}
			return Config.defaultAllowedComplexity;
		} else {
			ItemStack storageStack = itemHandler.getStackInSlot(getIndex(position));
			if (storageStack != null) {
				IModuleItemContainer itemContainer = ModuleManager.getContainerFromItem(storageStack);
				if (itemContainer != null) {
					for (IModuleContainer container : itemContainer.getContainers()) {
						IModule module = container.getModule();
						if (module instanceof IModuleModuleStorage) {
							return ((IModuleModuleStorage) module).getAllowedComplexity(container);
						}
					}
				}
			}
			if (pages.get(position) != null) {
				IItemHandler assemblerHandler = pages.get(position).getItemHandler();
				for (int index = 0; index < assemblerHandler.getSlots(); index++) {
					ItemStack slotStack = assemblerHandler.getStackInSlot(index);
					if (slotStack != null) {
						IModuleItemContainer itemContainer = ModuleManager.getContainerFromItem(slotStack);
						if (itemContainer != null) {
							for (IModuleContainer container : itemContainer.getContainers()) {
								IModule module = container.getModule();
								if (module instanceof IModuleModuleStorage) {
									return ((IModuleModuleStorage) module).getAllowedComplexity(container);
								}
							}
						}
					}
				}
			}
			return Config.defaultAllowedStorageComplexity;
		}
	}

	@Override
	public IItemHandlerStorage getItemHandler() {
		return itemHandler;
	}

	@Override
	public Collection<IStoragePage> getStoragePages() {
		return pages.values();
	}

	@Override
	public List<IStoragePosition> getStoragePositions() {
		return indexes;
	}

	@Override
	public IStoragePage getStoragePage(IStoragePosition position) {
		return pages.get(position);
	}

	@Override
	public void assemble(EntityPlayer player) {
		if (modularHandler != null) {
			try {
				modularHandler.setModular(createModular());
			} catch (Exception e) {
				e.printStackTrace();
			}
			if (modularHandler.getWorld().isRemote) {
				if (modularHandler instanceof IModularHandlerTileEntity) {
					IModularHandlerTileEntity tileEntity = (IModularHandlerTileEntity) modularHandler;
					BlockPos pos = tileEntity.getPos();
					modularHandler.getWorld().markBlockRangeForRenderUpdate(pos, pos);
				}
			} else {
				modularHandler.markDirty();
				if (modularHandler instanceof IModularHandlerTileEntity) {
					IModularHandlerTileEntity tileEntity = (IModularHandlerTileEntity) modularHandler;
					BlockPos pos = tileEntity.getPos();
					WorldServer server = (WorldServer) modularHandler.getWorld();
					PacketHandler.sendToNetwork(new PacketSyncHandlerState(tileEntity, true), pos, server);
					IBlockState blockState = server.getBlockState(pos);
					server.notifyBlockUpdate(pos, blockState, blockState, 3);
					boolean canOpenGui = modularHandler.getModular() != null && (modularHandler.getModular().getCurrentModule() == null || modularHandler.getModular().getCurrentPage() == null);
					for (EntityPlayer otherPlayer : server.playerEntities) {
						if (otherPlayer.openContainer instanceof ContainerAssembler) {
							ContainerAssembler assembler = (ContainerAssembler) otherPlayer.openContainer;
							if (modularHandler == assembler.getHandler()) {
								if (canOpenGui) {
									otherPlayer.closeScreen();
								} else {
									ItemStack heldStack = null;
									if (otherPlayer.inventory.getItemStack() != null) {
										heldStack = otherPlayer.inventory.getItemStack();
										otherPlayer.inventory.setItemStack(null);
									}
									otherPlayer.openGui(ModularMachines.instance, 0, otherPlayer.worldObj, pos.getX(), pos.getY(), pos.getZ());
									if (heldStack != null) {
										otherPlayer.inventory.setItemStack(heldStack);
										((EntityPlayerMP) otherPlayer).connection.sendPacket(new SPacketSetSlot(-1, -1, heldStack));
									}
								}
							}
						}
					}
				}
			}
		}
	}

	@Override
	public IModular createModular() throws AssemblerException {
		IModular modular = new Modular(modularHandler);
		for (IStoragePage page : pages.values()) {
			if (page != null) {
				IStorage storage = page.assemble(modular);
				if (storage != null) {
					for (IModuleState state : storage.getProvider().getModuleStates()) {
						state.setIndex(modular.getNextIndex());
					}
					modular.addStorage(storage);
				}
			}
		}
		for (IStorage storage : modular.getStorages().values()) {
			if (storage != null) {
				for (IModuleState state : storage.getProvider().getModuleStates()) {
					state.getModule().assembleModule(this, modular, storage, state);
				}
				if (storage instanceof IModuleStorage) {
					for (IModuleState state : ((IModuleStorage) storage).getModules()) {
						state.getModule().assembleModule(this, modular, storage, state);
					}
				}
			}
		}
		if (modular.getModules(IModuleCasing.class).isEmpty()) {
			throw new AssemblerException(Translator.translateToLocal("modular.assembler.error.no.casing"));
		}
		for (IStoragePage page : pages.values()) {
			if (page != null) {
				page.canAssemble(modular);
			}
		}
		testComplexity();
		modular.onModularAssembled();
		return modular;
	}

	@Override
	public int getIndex(IStoragePosition position) {
		return indexes.indexOf(position);
	}

	@Override
	public IModularAssembler copy(IModularHandler handler) {
		return new ModularAssembler(handler, itemHandler.getStacks(), new HashMap(pages));
	}

	@Override
	public void beforeSlotClick(int slotId, int dragType, ClickType clickTypeIn, EntityPlayer player) {
	}

	@Override
	public void afterSlotClick(int slotId, int dragType, ClickType clickTypeIn, EntityPlayer player) {
		if (hasChange) {
			BlockPos pos = ((IModularHandlerTileEntity) modularHandler).getPos();
			WorldServer server = (WorldServer) modularHandler.getWorld();
			for (EntityPlayer otherPlayer : server.playerEntities) {
				if (otherPlayer.openContainer instanceof ContainerAssembler) {
					ContainerAssembler assembler = (ContainerAssembler) otherPlayer.openContainer;
					if (modularHandler == assembler.getHandler()) {
						ItemStack heldStack = null;
						if (otherPlayer.inventory.getItemStack() != null) {
							heldStack = otherPlayer.inventory.getItemStack();
							otherPlayer.inventory.setItemStack(null);
						}
						otherPlayer.openGui(ModularMachines.instance, 0, otherPlayer.worldObj, pos.getX(), pos.getY(), pos.getZ());
						if (heldStack != null && heldStack.stackSize > 0) {
							otherPlayer.inventory.setItemStack(heldStack);
							((EntityPlayerMP) otherPlayer).connection.sendPacket(new SPacketSetSlot(-1, -1, heldStack));
						}
					}
				}
			}
			hasChange = false;
		}
	}

	@Override
	public void updatePages(IStoragePosition position) {
		if (position == null) {
			for (IStoragePosition pos : indexes) {
				IStoragePage page = pages.get(pos);
				ItemStack stack = itemHandler.getStackInSlot(getIndex(pos));
				if (page == null) {
					if (stack != null) {
						IModuleContainer<IStorageModule, IModuleProperties> moduleContainer = ModuleManager.getStorageContainer(ModuleManager.getContainerFromItem(stack, null), pos);
						if (moduleContainer != null) {
							pages.put(pos, page = moduleContainer.getModule().createPage(this, null, null, pos));
							IStoragePosition secondPos = moduleContainer.getModule().getSecondPosition(moduleContainer, pos);
							if (secondPos != null && secondPos != pos) {
								IStoragePage secondPage = moduleContainer.getModule().createSecondPage(secondPos);
								page.addChild(secondPage);
								pages.put(secondPos, secondPage);
							}
						}
					}
				} else {
					if (stack == null && (page.getParent() == null || page.getParent().getStorageStack() == null)) {
						pages.put(pos, null);
					} else {
						page.setAssembler(this);
					}
				}
			}
		}
		modularHandler.markDirty();
	}

	@Override
	public void onStorageSlotChange() {
		if (!modularHandler.getWorld().isRemote && modularHandler instanceof IModularHandlerTileEntity) {
			hasChange = true;
		}
	}
}
