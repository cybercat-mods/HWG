package mod.azure.hwg.client.models;

import mod.azure.azurelib.constant.DataTickets;
import mod.azure.azurelib.core.animation.AnimationState;
import mod.azure.azurelib.model.GeoModel;
import mod.azure.hwg.HWGMod;
import mod.azure.hwg.entity.SpyEntity;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

public class SpyModel extends GeoModel<SpyEntity> {

	public SpyModel() {
	}

	@Override
	public ResourceLocation getModelResource(SpyEntity object) {
		return new ResourceLocation(HWGMod.MODID, "geo/merc_illager.geo.json");
	}

	@Override
	public ResourceLocation getTextureResource(SpyEntity object) {
		return new ResourceLocation(HWGMod.MODID, "textures/entity/spy_0" + object.getVariant() + ".png");
	}

	@Override
	public ResourceLocation getAnimationResource(SpyEntity object) {
		return new ResourceLocation(HWGMod.MODID, "animations/merc_illager.animation.json");
	}

	@Override
	public RenderType getRenderType(SpyEntity animatable, ResourceLocation texture) {
		return RenderType.entityTranslucent(getTextureResource(animatable));
	}

	@Override
	public void setCustomAnimations(SpyEntity animatable, long instanceId, AnimationState<SpyEntity> animationState) {
		super.setCustomAnimations(animatable, instanceId, animationState);

		var head = getAnimationProcessor().getBone("head");
		var body = getAnimationProcessor().getBone("body");
		var left_arm = getAnimationProcessor().getBone("BipedLeftArm");
		var right_arm = getAnimationProcessor().getBone("BipedRightArm");
		var left_leg = getAnimationProcessor().getBone("BipedLeftLeg");
		var right_leg = getAnimationProcessor().getBone("BipedRightLeg");
		var entityData = animationState.getData(DataTickets.ENTITY_MODEL_DATA);

		if (head != null) {
			head.setRotX(entityData.headPitch() * Mth.DEG_TO_RAD);
			head.setRotY(entityData.netHeadYaw() * Mth.DEG_TO_RAD);
		}
		if (body != null) 
			body.setRotY(entityData.netHeadYaw() * Mth.DEG_TO_RAD);
		if (left_arm != null)
			left_arm.setRotX(Mth.cos(animatable.walkAnimation.position() * 0.6662F) * 2.0F * animatable.walkAnimation.speed() * 0.5F);
		if (right_arm != null && !animatable.isAggressive()) 
			right_arm.setRotX(Mth.cos(animatable.walkAnimation.position() * 0.6662F + 3.1415927F) * 2.0F * animatable.walkAnimation.speed() * 0.5F);
		if (left_leg != null)
			left_leg.setRotX(Mth.cos(animatable.walkAnimation.position() * 0.6662F + 3.1415927F) * 1.4F * animatable.walkAnimation.speed() * 0.5F);
		if (right_leg != null)
			right_leg.setRotX(Mth.cos(animatable.walkAnimation.position() * 0.6662F) * 1.4F * animatable.walkAnimation.speed() * 0.5F);
	}
}