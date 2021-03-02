package mod.azure.hwg.client.models;

import mod.azure.hwg.HWGMod;
import mod.azure.hwg.entity.TechnodemonEntity;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.processor.IBone;
import software.bernie.geckolib3.model.AnimatedGeoModel;
import software.bernie.geckolib3.model.provider.data.EntityModelData;

public class TechnodemonLesserModel extends AnimatedGeoModel<TechnodemonEntity> {

	public TechnodemonLesserModel() {
	}

	@Override
	public Identifier getModelLocation(TechnodemonEntity object) {
		return new Identifier(HWGMod.MODID, "geo/technodemon_lesser_" + object.getVariant() + ".geo.json");
	}

	@Override
	public Identifier getTextureLocation(TechnodemonEntity object) {
		return new Identifier(HWGMod.MODID, "textures/entity/technodemon_lesser_" + object.getVariant() + ".png");
	}

	@Override
	public Identifier getAnimationFileLocation(TechnodemonEntity object) {
		return new Identifier(HWGMod.MODID, "animations/technodemon_lesser_1.animation.json");
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public void setLivingAnimations(TechnodemonEntity entity, Integer uniqueID, AnimationEvent customPredicate) {
		super.setLivingAnimations(entity, uniqueID, customPredicate);
		IBone head = this.getAnimationProcessor().getBone("head");

		EntityModelData extraData = (EntityModelData) customPredicate.getExtraDataOfType(EntityModelData.class).get(0);
		if (head != null) {
			head.setRotationX((extraData.headPitch - 5) * ((float) Math.PI / 180F));
			head.setRotationY(extraData.netHeadYaw * ((float) Math.PI / 340F));
		}
	}
}