package mod.azure.hwg.client.models;

import mod.azure.hwg.HWGMod;
import mod.azure.hwg.entity.SpyEntity;
import net.minecraft.client.util.math.Vector3f;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.processor.IBone;
import software.bernie.geckolib3.model.AnimatedGeoModel;
import software.bernie.geckolib3.model.provider.data.EntityModelData;

public class SpyModel extends AnimatedGeoModel<SpyEntity> {

	public SpyModel() {
	}

	@Override
	public Identifier getModelLocation(SpyEntity object) {
		return new Identifier(HWGMod.MODID, "geo/merc_illager.geo.json");
	}

	@Override
	public Identifier getTextureLocation(SpyEntity object) {
		return new Identifier(HWGMod.MODID, "textures/entity/spy_0" + object.getVariant() + ".png");
	}

	@Override
	public Identifier getAnimationFileLocation(SpyEntity object) {
		return new Identifier(HWGMod.MODID, "animations/merc_illager.animation.json");
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public void setLivingAnimations(SpyEntity entity, Integer uniqueID, AnimationEvent customPredicate) {
		super.setLivingAnimations(entity, uniqueID, customPredicate);
		IBone head = this.getAnimationProcessor().getBone("head");

		IBone Left_arm = this.getAnimationProcessor().getBone("BipedLeftArm");
		IBone Right_arm = this.getAnimationProcessor().getBone("BipedRightArm");
		IBone Left_leg = this.getAnimationProcessor().getBone("BipedLeftLeg");
		IBone Right_leg = this.getAnimationProcessor().getBone("BipedRightLeg");

		EntityModelData extraData = (EntityModelData) customPredicate.getExtraDataOfType(EntityModelData.class).get(0);
		if (head != null) {
			head.setRotationX(
					Vector3f.POSITIVE_X.getRadialQuaternion(extraData.headPitch * ((float) Math.PI / 180F)).getX());
			head.setRotationY(
					Vector3f.POSITIVE_Y.getRadialQuaternion(extraData.netHeadYaw * ((float) Math.PI / 180F)).getY());
		}
		if (Left_arm != null) {
			Left_arm.setRotationX(Vector3f.POSITIVE_X
					.getRadialQuaternion(MathHelper.cos(entity.limbAngle * 0.6662F) * 2.0F * entity.limbDistance * 0.5F)
					.getX());
		}
		if (Right_arm != null) {
			Right_arm.setRotationX(Vector3f.POSITIVE_X
					.getRadialQuaternion(
							MathHelper.cos(entity.limbAngle * 0.6662F + 3.1415927F) * 2.0F * entity.limbDistance * 0.5F)
					.getX());
		}
		if (Left_leg != null) {
			Left_leg.setRotationX(Vector3f.POSITIVE_X
					.getRadialQuaternion(
							MathHelper.cos(entity.limbAngle * 0.6662F + 3.1415927F) * 1.4F * entity.limbDistance * 0.5F)
					.getX());
		}
		if (Right_leg != null) {
			Right_leg.setRotationX(Vector3f.POSITIVE_X
					.getRadialQuaternion(MathHelper.cos(entity.limbAngle * 0.6662F) * 1.4F * entity.limbDistance * 0.5F)
					.getX());
		}
	}
}