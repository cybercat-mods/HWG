package mod.azure.hwg.entity;

import java.util.UUID;

import org.jetbrains.annotations.Nullable;

import mod.azure.hwg.item.weapons.Minigun;
import mod.azure.hwg.util.packet.EntityPacket;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityData;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.goal.LookAroundGoal;
import net.minecraft.entity.ai.goal.LookAtEntityGoal;
import net.minecraft.entity.ai.goal.RevengeGoal;
import net.minecraft.entity.ai.goal.SwimGoal;
import net.minecraft.entity.ai.goal.TargetGoal;
import net.minecraft.entity.ai.goal.WanderAroundFarGoal;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.Angerable;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.Monster;
import net.minecraft.entity.passive.MerchantEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.Packet;
import net.minecraft.util.TimeHelper;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.intprovider.UniformIntProvider;
import net.minecraft.util.random.RandomGenerator;
import net.minecraft.world.Difficulty;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.IAnimationTickable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

public abstract class HWGEntity extends HostileEntity implements IAnimatable, Angerable, Monster, IAnimationTickable {

	private static final TrackedData<Integer> ANGER_TIME = DataTracker.registerData(HWGEntity.class,
			TrackedDataHandlerRegistry.INTEGER);
	private static final UniformIntProvider ANGER_TIME_RANGE = TimeHelper.betweenSeconds(20, 39);
	public static final TrackedData<Integer> VARIANT = DataTracker.registerData(HWGEntity.class,
			TrackedDataHandlerRegistry.INTEGER);
	public static final TrackedData<Integer> STATE = DataTracker.registerData(HWGEntity.class,
			TrackedDataHandlerRegistry.INTEGER);
	private UUID targetUuid;
	private AnimationFactory factory = new AnimationFactory(this);

	protected HWGEntity(EntityType<? extends HostileEntity> type, World worldIn) {
		super(type, worldIn);
		this.getNavigation().setCanSwim(true);
		this.ignoreCameraFrustum = true;
	}

	@Override
	public AnimationFactory getFactory() {
		return this.factory;
	}

	@Override
	public void registerControllers(AnimationData data) {
		data.addAnimationController(new AnimationController<HWGEntity>(this, "controller1", 0, this::predicate1));
	}

	private <E extends IAnimatable> PlayState predicate1(AnimationEvent<E> event) {
		if (this.dataTracker.get(STATE) == 1 && !(this.dead || this.getHealth() < 0.01 || this.isDead())
				&& !(this.getEquippedStack(EquipmentSlot.MAINHAND).getItem() instanceof Minigun)) {
			event.getController().setAnimation(new AnimationBuilder().addAnimation("attacking", true));
			return PlayState.CONTINUE;
		}
		return PlayState.STOP;
	}

	@Override
	public int tickTimer() {
		return age;
	}

	@Override
	protected void initGoals() {
		this.goalSelector.add(8, new LookAtEntityGoal(this, PlayerEntity.class, 8.0F));
		this.goalSelector.add(8, new LookAroundGoal(this));
		this.goalSelector.add(9, new SwimGoal(this));
		this.goalSelector.add(5, new WanderAroundFarGoal(this, 0.8D));
		this.targetSelector.add(2, new RevengeGoal(this).setGroupRevenge());
		this.targetSelector.add(2, new TargetGoal<>(this, PlayerEntity.class, true));
		this.targetSelector.add(2, new TargetGoal<>(this, MerchantEntity.class, true));
	}

	public static boolean canNetherSpawn(EntityType<? extends HWGEntity> type, WorldAccess serverWorldAccess,
			SpawnReason spawnReason, BlockPos pos, RandomGenerator random) {
		if (serverWorldAccess.getDifficulty() == Difficulty.PEACEFUL)
			return false;
		if ((spawnReason != SpawnReason.CHUNK_GENERATION && spawnReason != SpawnReason.NATURAL))
			return !serverWorldAccess.getBlockState(pos.down()).isOf(Blocks.NETHER_WART_BLOCK);
		return !serverWorldAccess.getBlockState(pos.down()).isOf(Blocks.NETHER_WART_BLOCK);
	}

	@Override
	public Packet<?> createSpawnPacket() {
		return EntityPacket.createPacket(this);
	}

	@Override
	protected boolean isDisallowedInPeaceful() {
		return true;
	}

	@Override
	public void checkDespawn() {
		super.checkDespawn();
	}

	@Override
	public int getLimitPerChunk() {
		return 1;
	}

	protected boolean shouldDrown() {
		return false;
	}

	protected boolean shouldBurnInDay() {
		return false;
	}

	@Override
	@Environment(EnvType.CLIENT)
	public boolean shouldRender(double distance) {
		return true;
	}

	public void setShooting(boolean attacking) {

	}

	@Override
	protected void initDataTracker() {
		super.initDataTracker();
		this.dataTracker.startTracking(ANGER_TIME, 0);
		this.dataTracker.startTracking(STATE, 0);
	}

	@Override
	public EntityData initialize(ServerWorldAccess world, LocalDifficulty difficulty, SpawnReason spawnReason,
			EntityData entityData, NbtCompound entityTag) {
		return super.initialize(world, difficulty, spawnReason, entityData, entityTag);
	}

	@Override
	public int getAngerTime() {
		return this.dataTracker.get(ANGER_TIME);
	}

	@Override
	public void setAngerTime(int ticks) {
		this.dataTracker.set(ANGER_TIME, ticks);
	}

	public int getAttckingState() {
		return this.dataTracker.get(STATE);
	}

	public void setAttackingState(int time) {
		this.dataTracker.set(STATE, time);
	}

	@Override
	public UUID getAngryAt() {
		return this.targetUuid;
	}

	@Override
	public void setAngryAt(@Nullable UUID uuid) {
		this.targetUuid = uuid;
	}

	@Override
	public void chooseRandomAngerTime() {
		this.setAngerTime(ANGER_TIME_RANGE.get(this.random));
	}

	public abstract int getVariants();

}