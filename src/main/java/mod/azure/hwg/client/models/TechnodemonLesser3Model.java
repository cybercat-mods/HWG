package mod.azure.hwg.client.models;

import mod.azure.hwg.HWGMod;
import mod.azure.hwg.entity.Technodemon3Entity;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.processor.IBone;
import software.bernie.geckolib3.model.AnimatedGeoModel;
import software.bernie.geckolib3.model.provider.data.EntityModelData;

public class TechnodemonLesser3Model extends AnimatedGeoModel<Technodemon3Entity> {

	public TechnodemonLesser3Model() {
	}

	@Override
	public Identifier getModelLocation(Technodemon3Entity object) {
		return new Identifier(HWGMod.MODID, "geo/technodemon_lesser_3.geo.json");
	}

	@Override
	public Identifier getTextureLocation(Technodemon3Entity object) {
		return new Identifier(HWGMod.MODID, "textures/entity/technodemon_lesser_3.png");
	}

	@Override
	public Identifier getAnimationFileLocation(Technodemon3Entity object) {
		return new Identifier(HWGMod.MODID, "animations/technodemon_lesser_3.animation.json");
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public void setLivingAnimations(Technodemon3Entity entity, Integer uniqueID, AnimationEvent customPredicate) {
		super.setLivingAnimations(entity, uniqueID, customPredicate);
		IBone head = this.getAnimationProcessor().getBone("head");

		EntityModelData extraData = (EntityModelData) customPredicate.getExtraDataOfType(EntityModelData.class).get(0);
		if (head != null) {
			head.setRotationX(extraData.headPitch * ((float) Math.PI / 180F));
			head.setRotationY(extraData.netHeadYaw * ((float) Math.PI / 180F));
		}
	}
}