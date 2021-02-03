package mod.azure.hwg.client.models;

import mod.azure.hwg.HWGMod;
import mod.azure.hwg.entity.Technodemon4Entity;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.processor.IBone;
import software.bernie.geckolib3.model.AnimatedGeoModel;
import software.bernie.geckolib3.model.provider.data.EntityModelData;

public class TechnodemonLesser4Model extends AnimatedGeoModel<Technodemon4Entity> {

	public TechnodemonLesser4Model() {
	}

	@Override
	public Identifier getModelLocation(Technodemon4Entity object) {
		return new Identifier(HWGMod.MODID, "geo/technodemon_lesser_4.geo.json");
	}

	@Override
	public Identifier getTextureLocation(Technodemon4Entity object) {
		return new Identifier(HWGMod.MODID, "textures/entity/technodemon_lesser_4.png");
	}

	@Override
	public Identifier getAnimationFileLocation(Technodemon4Entity object) {
		return new Identifier(HWGMod.MODID, "animations/technodemon_lesser_4.animation.json");
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public void setLivingAnimations(Technodemon4Entity entity, Integer uniqueID, AnimationEvent customPredicate) {
		super.setLivingAnimations(entity, uniqueID, customPredicate);
		IBone head = this.getAnimationProcessor().getBone("head");

		EntityModelData extraData = (EntityModelData) customPredicate.getExtraDataOfType(EntityModelData.class).get(0);
		if (head != null) {
			head.setRotationX(extraData.headPitch * ((float) Math.PI / 180F));
			head.setRotationY(extraData.netHeadYaw * ((float) Math.PI / 180F));
		}
	}
}