package mod.azure.hwg.client.models;

import mod.azure.hwg.HWGMod;
import mod.azure.hwg.entity.TechnodemonGreater2Entity;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.processor.IBone;
import software.bernie.geckolib3.model.AnimatedGeoModel;
import software.bernie.geckolib3.model.provider.data.EntityModelData;

public class TechnodemonGreater2Model extends AnimatedGeoModel<TechnodemonGreater2Entity> {

	public TechnodemonGreater2Model() {
	}

	@Override
	public Identifier getModelLocation(TechnodemonGreater2Entity object) {
		return new Identifier(HWGMod.MODID, "geo/technodemon_greater_2.geo.json");
	}

	@Override
	public Identifier getTextureLocation(TechnodemonGreater2Entity object) {
		return new Identifier(HWGMod.MODID, "textures/entity/technodemon_greater_2.png");
	}

	@Override
	public Identifier getAnimationFileLocation(TechnodemonGreater2Entity object) {
		return new Identifier(HWGMod.MODID, "animations/technodemon_greater_2.animation.json");
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public void setLivingAnimations(TechnodemonGreater2Entity entity, Integer uniqueID, AnimationEvent customPredicate) {
		super.setLivingAnimations(entity, uniqueID, customPredicate);
		IBone head = this.getAnimationProcessor().getBone("head");

		EntityModelData extraData = (EntityModelData) customPredicate.getExtraDataOfType(EntityModelData.class).get(0);
		if (head != null) {
			head.setRotationX(extraData.headPitch * ((float) Math.PI / 180F));
			head.setRotationY(extraData.netHeadYaw * ((float) Math.PI / 180F));
		}
	}
}