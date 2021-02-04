package mod.azure.hwg.client.models;

import mod.azure.hwg.HWGMod;
import mod.azure.hwg.entity.SpyEntity;
import net.minecraft.util.Identifier;
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

	@Override
	public void setLivingAnimations(SpyEntity entity, Integer uniqueID, AnimationEvent customPredicate) {
		super.setLivingAnimations(entity, uniqueID, customPredicate);
		IBone head = this.getAnimationProcessor().getBone("head");

		EntityModelData extraData = (EntityModelData) customPredicate.getExtraDataOfType(EntityModelData.class).get(0);
		if (head != null) {
			head.setRotationX(extraData.headPitch * ((float) Math.PI / 180F));
			head.setRotationY(extraData.netHeadYaw * ((float) Math.PI / 180F));
		}
	}
}