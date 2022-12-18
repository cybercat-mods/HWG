package mod.azure.hwg.client.models;

import mod.azure.hwg.HWGMod;
import mod.azure.hwg.entity.MercEntity;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import software.bernie.geckolib.constant.DataTickets;
import software.bernie.geckolib.core.animatable.model.CoreGeoBone;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.model.data.EntityModelData;

public class MercModel extends GeoModel<MercEntity> {

	public MercModel() {
	}

	@Override
	public Identifier getModelResource(MercEntity object) {
		return new Identifier(HWGMod.MODID, "geo/merc_illager.geo.json");
	}

	@Override
	public Identifier getTextureResource(MercEntity object) {
		return new Identifier(HWGMod.MODID, "textures/entity/merc_" + object.getVariant() + ".png");
	}

	@Override
	public Identifier getAnimationResource(MercEntity object) {
		return new Identifier(HWGMod.MODID, "animations/merc_illager.animation.json");
	}
	
	@Override
	public RenderLayer getRenderType(MercEntity animatable, Identifier texture) {
		return RenderLayer.getEntityTranslucent(getTextureResource(animatable));
	}

	@Override
	public void setCustomAnimations(MercEntity animatable, long instanceId, AnimationState<MercEntity> animationState) {
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