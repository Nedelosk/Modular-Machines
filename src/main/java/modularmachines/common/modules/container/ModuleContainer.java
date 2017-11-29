package modularmachines.common.modules.container;

import javax.annotation.Nullable;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ITickable;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.items.ItemHandlerHelper;

import modularmachines.api.ILocatable;
import modularmachines.api.modules.IModule;
import modularmachines.api.modules.IModuleHandler;
import modularmachines.api.modules.IModuleProvider;
import modularmachines.api.modules.IModuleType;
import modularmachines.api.modules.ModuleManager;
import modularmachines.api.modules.components.IModuleComponent;
import modularmachines.api.modules.components.block.IBoundingBoxComponent;
import modularmachines.api.modules.components.block.IInteractionComponent;
import modularmachines.api.modules.container.ContainerComponent;
import modularmachines.api.modules.container.IModuleContainer;
import modularmachines.api.modules.container.IModuleListener;
import modularmachines.api.modules.events.Event;
import modularmachines.api.modules.events.Events;
import modularmachines.api.modules.events.IEventListener;
import modularmachines.api.modules.positions.EnumCasingPositions;
import modularmachines.api.modules.positions.IModulePosition;
import modularmachines.common.modules.CasingModuleHandler;
import modularmachines.common.modules.ModuleHandler;
import modularmachines.common.network.PacketHandler;
import modularmachines.common.network.packets.PacketUpdateModuleContainer;
import modularmachines.common.utils.ModuleUtil;
import modularmachines.common.utils.components.ComponentProvider;

public class ModuleContainer extends ComponentProvider<ContainerComponent> implements IModuleContainer {
	private final Map<Class<? extends Event>, Set<IEventListener>> eventListeners = new HashMap<>();
	private final ILocatable locatable;
	private ModuleHandler moduleHandler;
	private boolean markedForDeletion;
	@Nullable
	private Set<IModule> modules = null;
	@Nullable
	private Set<ITickable> tickables = null;
	@Nullable
	private AxisAlignedBB boundingBox = null;
	
	public ModuleContainer(ILocatable locatable) {
		this.locatable = locatable;
		this.moduleHandler = new CasingModuleHandler(this, EnumCasingPositions.CENTER);
	}
	
	/* SAVE & LOAD */
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		super.writeToNBT(compound);
		moduleHandler.writeToNBT(compound);
		return compound;
	}
	
	public void readFromNBT(NBTTagCompound compound) {
		super.readFromNBT(compound);
		moduleHandler.readFromNBT(compound);
		receiveEvent(new Events.ContainerLoadEvent(this));
	}
	
	@Override
	public ILocatable getLocatable() {
		return locatable;
	}
	
	/* MODULE STORAGE */
	@Override
	public IModuleHandler getHandler() {
		return moduleHandler;
	}
	
	@Override
	public IModule getModule(int index) {
		return getModules().stream().filter(m -> m.getIndex() == index).findAny().orElse(null);
	}
	
	@Override
	public Collection<IModule> getModules() {
		if (modules == null) {
			modules = new HashSet<>();
			moduleHandler.getModules().forEach(m -> addToList(modules, m));
		}
		return modules;
	}
	
	private Set<ITickable> getTickables() {
		if (tickables == null) {
			tickables = getModules().stream().map(m -> m.getComponents(ITickable.class)).flatMap(Collection::stream).collect(Collectors.toSet());
		}
		return tickables;
	}
	
	private void addToList(Set<IModule> modules, IModule module) {
		modules.add(module);
		IModuleProvider moduleProvider = module.getComponent(IModuleProvider.class);
		if (moduleProvider == null) {
			return;
		}
		IModuleHandler moduleHandler = moduleProvider.getHandler();
		moduleHandler.getModules().forEach(m -> addToList(modules, m));
	}
	
	@Override
	public AxisAlignedBB getBoundingBox() {
		if (boundingBox == null) {
			for (IModule module : getModules()) {
				for (IBoundingBoxComponent component : module.getComponents(IBoundingBoxComponent.class)) {
					AxisAlignedBB alignedBB = component.getCollisionBox();
					if (boundingBox == null) {
						boundingBox = alignedBB;
						continue;
					}
					boundingBox = boundingBox.union(alignedBB);
				}
			}
			if (boundingBox == null) {
				boundingBox = Block.FULL_BLOCK_AABB;
			}
		}
		return boundingBox;
	}
	
	public boolean onActivated(EntityPlayer player, EnumHand hand, RayTraceResult hit) {
		if (markedForDeletion) {
			return false;
		}
		World world = player.world;
		ItemStack heldItem = player.getHeldItem(hand);
		if (heldItem.isEmpty()) {
			if (player.isSneaking()) {
				List<ItemStack> itemStacks = extractModule(hit, world.isRemote);
				if (itemStacks.isEmpty()) {
					return false;
				}
				for (ItemStack itemStack : itemStacks) {
					ItemHandlerHelper.giveItemToPlayer(player, itemStack);
				}
				return true;
			}
		} else {
			if (insertModule(heldItem, hit, world.isRemote)) {
				if (!player.capabilities.isCreativeMode) {
					heldItem.shrink(1);
					if (heldItem.isEmpty()) {
						player.setHeldItem(hand, ItemStack.EMPTY);
					}
				}
				if (world.isRemote) {
					world.playSound(player, player.posX, player.posY, player.posZ,
							SoundEvents.ENTITY_ITEMFRAME_PLACE, SoundCategory.PLAYERS, 0.6F, ((world.rand.nextFloat() - world.rand.nextFloat()) * 0.7F + 1.0F) * 2.0F);
				}
				return true;
			}
		}
		IModule module = getModule(hit.subHit);
		return module != null && module.getComponents(IInteractionComponent.class).stream().anyMatch(c -> c.onActivated(player, hand, hit));
	}
	
	public void onClick(EntityPlayer player, RayTraceResult hit) {
		if (markedForDeletion) {
			return;
		}
		IModule module = getModule(hit.subHit);
		if (module == null) {
			return;
		}
		module.getComponents(IInteractionComponent.class).forEach(c -> c.onClick(player, hit));
	}
	
	@Override
	public RayTraceResult collisionRayTrace(BlockPos pos, Vec3d start, Vec3d end) {
		Vec3d startVec = start.subtract((double) pos.getX(), (double) pos.getY(), (double) pos.getZ());
		Vec3d endVec = end.subtract((double) pos.getX(), (double) pos.getY(), (double) pos.getZ());
		Collection<IModule> modules = moduleHandler.getModules();
		return modules
				.stream()
				.map(m -> m.getComponents(IBoundingBoxComponent.class))
				.flatMap(Collection::stream)
				.map(c -> c.collisionRayTrace(startVec, endVec))
				.filter(Objects::nonNull)
				.min(Comparator.comparingDouble(hit -> hit.hitVec.squareDistanceTo(startVec)))
				.map(r -> {
					RayTraceResult result = new RayTraceResult(r.hitVec.addVector((double) pos.getX(), (double) pos.getY(), (double) pos.getZ()), r.sideHit, pos);
					result.subHit = r.subHit;
					result.hitInfo = r.hitInfo;
					return result;
				})
				.orElse(null);
	}
	
	public List<ItemStack> extractModule(RayTraceResult rayTraceResult, boolean simulate) {
		if (markedForDeletion) {
			return Collections.emptyList();
		}
		IModule module = getModule(rayTraceResult.subHit);
		if (module == null) {
			return Collections.emptyList();
		}
		IModulePosition position = module.getPosition();
		IModuleHandler parent = module.getHandler();
		return parent.extractModule(position, simulate);
	}
	
	private boolean insertModule(ItemStack itemStack, RayTraceResult rayTraceResult, boolean simulate) {
		if (itemStack.isEmpty()) {
			return false;
		}
		IModuleType type = ModuleManager.registry.getTypeFromItem(itemStack);
		if (type == null) {
			return false;
		}
		IModule module = getModule(rayTraceResult.subHit);
		IModuleProvider provider = ModuleUtil.getComponent(module, IModuleProvider.class);
		return provider != null && insertModule(provider, itemStack, type, rayTraceResult, simulate);
	}
	
	private boolean insertModule(IModuleProvider provider, ItemStack itemStack, IModuleType dataContainer, RayTraceResult rayTraceResult, boolean simulate) {
		IModulePosition position = provider.getPosition(rayTraceResult);
		return position != null && provider.getHandler().insertModule(position, dataContainer, itemStack, simulate);
	}
	
	@Override
	public IModulePosition getPosition(RayTraceResult hit) {
		return EnumCasingPositions.CENTER;
	}
	
	@Override
	public int generateIndex(IModule module) {
		int value = 0;
		IModule currentModule = module;
		do {
			IModuleHandler moduleHandler = currentModule.getHandler();
			IModulePosition position = currentModule.getPosition();
			int positionIndex = moduleHandler.getPositionIndex(position);
			value = value << 5;
			value = value | (positionIndex & 0xF);
			if (currentModule != module) {
				value = value | 1 << 4;
			}
			IModuleProvider provider = currentModule.getHandler().getProvider();
			if (provider instanceof IModuleComponent) {
				currentModule = ((IModuleComponent) provider).getProvider();
			} else {
				break;
			}
		} while (true);
		return value;
	}
	
	@Override
	public boolean hasCapability(Capability<?> capability, @Nullable EnumFacing facing) {
		if (super.hasCapability(capability, facing)) {
			return true;
		}
		/*for (IModule module : getModules()) {
			if (module.hasCapability(capability, facing)) {
				return true;
			}
		}*/
		return false;
	}
	
	@Nullable
	@Override
	public <T> T getCapability(Capability<T> capability, @Nullable EnumFacing facing) {
		T t = super.getCapability(capability, facing);
		if (t != null) {
			return t;
		}
		/*for (IModule module : getModules()) {
			if (module.hasCapability(capability, facing)) {
				return module.getCapability(capability, facing);
			}
		}*/
		return null;
	}
	
	@Override
	public void update() {
		if (isMarkedForDeletion()) {
			locatable.getWorldObj().setBlockToAir(locatable.getCoordinates());
		}
		getComponents(ITickable.class).forEach(ITickable::update);
		getTickables().forEach(ITickable::update);
	}
	
	@Override
	public void sendToClient() {
		if (markedForDeletion) {
			return;
		}
		PacketHandler.sendToNetwork(new PacketUpdateModuleContainer(this), locatable.getCoordinates(), locatable.getWorldObj());
	}
	
	@Override
	public void markForDeletion() {
		markedForDeletion = true;
	}
	
	@Override
	public boolean isMarkedForDeletion() {
		return markedForDeletion;
	}
	
	@Override
	public void onModuleRemoved(IModule module) {
		for (IModuleListener listener : getComponents(IModuleListener.class)) {
			listener.onModuleRemoved(module);
		}
		//reset module list
		modules = null;
		tickables = null;
		boundingBox = null;
	}
	
	@Override
	public void onModuleAdded(IModule module) {
		for (IModuleListener listener : getComponents(IModuleListener.class)) {
			listener.onModuleAdded(module);
		}
		//reset module list
		modules = null;
		tickables = null;
		boundingBox = null;
	}
	
	@Override
	public <E extends Event> void registerListener(Class<? extends E> eventClass, IEventListener<E> listener) {
		eventListeners.computeIfAbsent(eventClass, k -> new HashSet<>()).add(listener);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void receiveEvent(Event event) {
		Set<IEventListener> listeners = eventListeners.get(event.getClass());
		if (listeners == null) {
			return;
		}
		for (IEventListener listener : listeners) {
			listener.onEvent(event);
		}
	}
}
