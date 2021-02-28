package mod.azure.hwg.client.models;

import mod.azure.hwg.HWGMod;
import mod.azure.hwg.entity.MercEntity;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.processor.IBone;
import software.bernie.geckolib3.model.AnimatedGeoModel;
import software.bernie.geckolib3.model.provider.data.EntityModelData;

public class MercModel extends AnimatedGeoModel<MercEntity> {

	public MercModel() {
	}

	@Override
	public Identifier getModelLocation(MercEntity object) {
		return new Identifier(HWGMod.MODID, "geo/merc_illager.geo.json");
	}

	@Override
	public Identifier getTextureLocation(MercEntity object) {
		return new Identifier(HWGMod.MODID, "textures/entity/merc_" + object.getVariant() + ".png");
	}

	@Override
	public Identifier getAnimationFileLocation(MercEntity object) {
		return new Identifier(HWGMod.MODID, "animations/merc_illager.animation.json");
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public void setLivingAnimations(MercEntity entity, Integer uniqueID, AnimationEvent customPredicate) {
		super.setLivingAnimations(entity, uniqueID, customPredicate);
		IBone head = this.getAnimationProcessor().getBone("head");

//        IBone Left = this.getAnimationProcessor().getBone("BipedLeftLeg");
//        IBone Right = this.getAnimationProcessor().getBone("BipedRightLeg");

		EntityModelData extraData = (EntityModelData) customPredicate.getExtraDataOfType(EntityModelData.class).get(0);
		if (head != null) {
			head.setRotationX(extraData.headPitch * ((float) Math.PI / 180F));
			head.setRotationY(extraData.netHeadYaw * ((float) Math.PI / 180F));
		}
//		if (Left != null) {
//			Left.setRotationX(MathHelper.cos(entity.limbAngle * 0.6662F + 3.1415927F) * 1.4F * entity.limbDistance * 0.5F);
//		}
//		if (Right != null) {
//			Right.setRotationX(MathHelper.cos(entity.limbAngle * 0.6662F) * 1.4F * entity.limbDistance * 0.5F);
//		}
	}
}