package mod.azure.hwg.client.models;

import mod.azure.hwg.HWGMod;
import mod.azure.hwg.entity.SpyEntity;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import mod.azure.azurelib.constant.DataTickets;
import mod.azure.azurelib.core.animatable.model.CoreGeoBone;
import mod.azure.azurelib.core.animation.AnimationState;
import mod.azure.azurelib.model.GeoModel;
import mod.azure.azurelib.model.data.EntityModelData;

public class SpyModel extends GeoModel<SpyEntity> {

	public SpyModel() {
	}

	@Override
	public Identifier getModelResource(SpyEntity object) {
		return new Identifier(HWGMod.MODID, "geo/merc_illager.geo.json");
	}

	@Override
	public Identifier getTextureResource(SpyEntity object) {
		return new Identifier(HWGMod.MODID, "textures/entity/spy_0" + object.getVariant() + ".png");
	}

	@Override
	public Identifier getAnimationResource(SpyEntity object) {
		return new Identifier(HWGMod.MODID, "animations/merc_illager.animation.json");
	}
	
	@Override
	public RenderLayer getRenderType(SpyEntity animatable, Identifier texture) {
		return RenderLayer.getEntityTranslucent(getTextureResource(animatable));
	}

	@Override
	public void setCustomAnimations(SpyEntity animatable, long instanceId, AnimationState<SpyEntity> animationState) {
		super.setCustomAnimations(animatable, instanceId, animationState);

		CoreGeoBone head = getAnimationProcessor().getBone("head");
		CoreGeoBone left_arm = getAnimationProcessor().getBone("BipedLeftArm");
		CoreGeoBone right_arm = getAnimationProcessor().getBone("BipedRightArm");
		CoreGeoBone left_leg = getAnimationProcessor().getBone("BipedLeftLeg");
		CoreGeoBone right_leg = getAnimationProcessor().getBone("BipedRightLeg");
		EntityModelData entityData = animationState.getData(DataTickets.ENTITY_MODEL_DATA);

		if (head != null) {
			head.setRotX(entityData.headPitch() * MathHelper.RADIANS_PER_DEGREE);
			head.setRotY(entityData.netHeadYaw() * MathHelper.RADIANS_PER_DEGREE);
		}
		if (left_arm != null) {
			left_arm.setRotX(MathHelper.cos(animatable.limbAngle * 0.6662F) * 2.0F * animatable.limbDistance * 0.5F);
		}
		if (right_arm != null && animatable.getAttckingState() == 0) {
			right_arm.setRotX(MathHelper.cos(animatable.limbAngle * 0.6662F + 3.1415927F) * 2.0F
					* animatable.limbDistance * 0.5F);
		}
		if (left_leg != null) {
			left_leg.setRotX(MathHelper.cos(animatable.limbAngle * 0.6662F + 3.1415927F) * 1.4F
					* animatable.limbDistance * 0.5F);
		}
		if (right_leg != null) {
			right_leg.setRotX(MathHelper.cos(animatable.limbAngle * 0.6662F) * 1.4F * animatable.limbDistance * 0.5F);
		}
	}
}