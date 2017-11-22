package modularmachines.common.modules.container;

import javax.annotation.Nullable;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.items.ItemHandlerHelper;

import modularmachines.api.ILocatable;
import modularmachines.api.modules.IModuleHandler;
import modularmachines.api.modules.IModuleProvider;
import modularmachines.api.modules.ModuleManager;
import modularmachines.api.modules.components.IBoundingBoxComponent;
import modularmachines.api.modules.components.IInteractionComponent;
import modularmachines.api.modules.container.ContainerComponent;
import modularmachines.api.modules.container.IModuleContainer;
import modularmachines.api.modules.data.IModuleDataContainer;
import modularmachines.api.modules.positions.EnumCasingPositions;
import modularmachines.api.modules.positions.IModulePosition;
import modularmachines.common.modules.Module;
import modularmachines.common.modules.ModuleHandler;
import modularmachines.common.network.PacketHandler;
import modularmachines.common.network.packets.PacketSyncModule;
import modularmachines.common.network.packets.PacketSyncModuleContainer;
import modularmachines.common.utils.components.ComponentProvider;

public class ModuleContainer extends ComponentProvider<ContainerComponent> implements IModuleContainer {
	private final ILocatable locatable;
	private ModuleHandler moduleHandler;
	
	public ModuleContainer(ILocatable locatable) {
		this.locatable = locatable;
		this.moduleHandler = new ModuleHandler(this, EnumCasingPositions.CENTER);
	}
	
	/* SAVE & LOAD */
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		moduleHandler.writeToNBT(compound);
		return compound;
	}
	
	public void readFromNBT(NBTTagCompound compound) {
		moduleHandler.readFromNBT(compound);
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
	public Module getModule(int index) {
		return getModules().stream().filter(m -> m.getIndex() == index).findAny().orElse(null);
	}
	
	@Override
	public Collection<Module> getModules() {
		Set<Module> modules = new HashSet<>();
		moduleHandler.getModules().forEach(m -> addToList(modules, m));
		return modules;
	}
	
	private void addToList(Set<Module> modules, Module module) {
		modules.add(module);
		if (!(module instanceof IModuleProvider)) {
			return;
		}
		IModuleProvider provider = (IModuleProvider) module;
		IModuleHandler moduleHandler = provider.getHandler();
		moduleHandler.getModules().forEach(m -> addToList(modules, m));
	}
	
	@Override
	public AxisAlignedBB getBoundingBox() {
		AxisAlignedBB boundingBox = null;
		for (Module module : getModules()) {
			for (IBoundingBoxComponent component : module.getInterfaces(IBoundingBoxComponent.class)) {
				AxisAlignedBB alignedBB = component.getCollisionBox();
				if (alignedBB == null) {
					continue;
				}
				if (boundingBox == null) {
					boundingBox = alignedBB;
					continue;
				}
				boundingBox = boundingBox.union(alignedBB);
			}
		}
		return boundingBox == null ? Block.FULL_BLOCK_AABB : boundingBox;
	}
	
	public boolean onActivated(EntityPlayer player, EnumHand hand, RayTraceResult hit) {
		World world = player.world;
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
		if (insertModule(player.getHeldItem(hand), hit, world.isRemote)) {
			ItemStack itemStack = player.getHeldItem(hand);
			if (!player.capabilities.isCreativeMode) {
				itemStack.shrink(1);
				if (itemStack.isEmpty()) {
					player.setHeldItem(hand, ItemStack.EMPTY);
				}
			}
			if (world.isRemote) {
				world.playSound(player, player.posX, player.posY, player.posZ,
						SoundEvents.ENTITY_ITEMFRAME_PLACE, SoundCategory.PLAYERS, 0.6F, ((world.rand.nextFloat() - world.rand.nextFloat()) * 0.7F + 1.0F) * 2.0F);
			}
			return true;
		}
		Module module = getModule(hit.subHit);
		return module != null && module.getInterfaces(IInteractionComponent.class).stream().anyMatch(c -> c.onActivated(player, hand, hit));
	}
	
	public void onClick(EntityPlayer player, RayTraceResult hit) {
		Module module = getModule(hit.subHit);
		if (module == null) {
			return;
		}
		module.getInterfaces(IInteractionComponent.class).forEach(c -> c.onClick(player, hit));
	}
	
	@Override
	public RayTraceResult collisionRayTrace(BlockPos pos, Vec3d start, Vec3d end) {
		Vec3d startVec = start.subtract((double) pos.getX(), (double) pos.getY(), (double) pos.getZ());
		Vec3d endVec = end.subtract((double) pos.getX(), (double) pos.getY(), (double) pos.getZ());
		Collection<Module> modules = moduleHandler.getModules();
		if (modules.isEmpty()) {
			RayTraceResult old = Block.FULL_BLOCK_AABB.calculateIntercept(startVec, endVec);
			if (old == null) {
				return null;
			}
			return new RayTraceResult(old.hitVec.addVector((double) pos.getX(), (double) pos.getY(), (double) pos.getZ()), old.sideHit, pos);
		}
		return modules
				.stream()
				.map(m -> m.getInterfaces(IBoundingBoxComponent.class))
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
	
	private int createPositionIndex(Module module) {
		//List<Module> modules = new LinkedList<>();
		//addPosition(module, modules);
		int value = 0;
		Object provider = module;
		do {
			Module otherModule = (Module) provider;
			IModuleHandler moduleHandler = otherModule.getParent();
			IModulePosition position = otherModule.getPosition();
			int positionIndex = moduleHandler.getPositionIndex(position);
			value = value << 5;
			value = value | (positionIndex & 0xF);
			if (provider != module) {
				value = value | 1 << 4;
			}
			provider = otherModule.getParent().getProvider();
		} while (provider == module || provider instanceof Module);
		/*for(int i = 0;i < modules.size();i++){
			Module otherModule = modules.get(i);
			IModuleHandler moduleHandler = otherModule.getParent();
			IModulePosition position = otherModule.getPosition();
			int positionIndex = moduleHandler.getPositionIndex(position);
			value = value << 5;
			value = value | (positionIndex & 0xF);
			if(i != 0) {
				value = value | 1 << 4;
			}
		}*/
		return value;
	}
	
	private void addPosition(Module module, List<Module> modules) {
		modules.add(module);
		IModuleProvider provider = module.getParent().getProvider();
		if (provider instanceof Module) {
			addPosition(((Module) provider), modules);
		}
	}
	
	private List<ItemStack> extractModule(RayTraceResult rayTraceResult, boolean simulate) {
		if (rayTraceResult.subHit == -1) {
			return moduleHandler.extractModule(EnumCasingPositions.CENTER, simulate);
		}
		Module module = getModule(rayTraceResult.subHit);
		if (module == null) {
			return Collections.emptyList();
		}
		IModulePosition position = module.getPosition();
		IModuleHandler parent = module.getParent();
		return parent.extractModule(position, simulate);
	}
	
	private boolean insertModule(ItemStack itemStack, RayTraceResult rayTraceResult, boolean simulate) {
		if (itemStack.isEmpty()) {
			return false;
		}
		IModuleDataContainer dataContainer = ModuleManager.helper.getContainerFromItem(itemStack);
		if (dataContainer == null) {
			return false;
		}
		if (rayTraceResult.subHit == -1) {
			return insertModule(this, itemStack.copy(), dataContainer, rayTraceResult, simulate);
		}
		Module module = getModule(rayTraceResult.subHit);
		if (!(module instanceof IModuleProvider)) {
			return false;
		}
		IModuleProvider provider = (IModuleProvider) module;
		return insertModule(provider, itemStack, dataContainer, rayTraceResult, simulate);
	}
	
	private boolean insertModule(IModuleProvider provider, ItemStack itemStack, IModuleDataContainer dataContainer, RayTraceResult rayTraceResult, boolean simulate) {
		IModulePosition position = provider.getPosition(rayTraceResult);
		if (position == null) {
			return false;
		}
		return provider.getHandler().insertModule(position, dataContainer, itemStack, simulate);
	}
	
	@Override
	public IModulePosition getPosition(RayTraceResult hit) {
		return EnumCasingPositions.CENTER;
	}
	
	@Override
	public void onModuleAdded(Module module) {
		int index = createPositionIndex(module);
		module.setIndex(index);
	}
	
	@Override
	public boolean hasCapability(Capability<?> capability, @Nullable EnumFacing facing) {
		for (Module module : getModules()) {
			if (module.canHandle(facing) && module.hasCapability(capability, facing)) {
				return true;
			}
		}
		return false;
	}
	
	@Nullable
	@Override
	public <T> T getCapability(Capability<T> capability, @Nullable EnumFacing facing) {
		for (Module module : getModules()) {
			if (module.canHandle(facing) && module.hasCapability(capability, facing)) {
				return module.getCapability(capability, facing);
			}
		}
		return null;
	}
	
	@Override
	public void sendModuleToClient(Module module) {
		PacketHandler.sendToNetwork(new PacketSyncModule(module), locatable.getCoordinates(), locatable.getWorldObj());
	}
	
	@Override
	public void sendToClient() {
		PacketHandler.sendToNetwork(new PacketSyncModuleContainer(this), locatable.getCoordinates(), locatable.getWorldObj());
	}
}
