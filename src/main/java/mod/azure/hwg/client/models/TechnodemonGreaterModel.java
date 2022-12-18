package mod.azure.hwg.client.models;

import mod.azure.hwg.HWGMod;
import mod.azure.hwg.entity.TechnodemonGreaterEntity;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import software.bernie.geckolib.constant.DataTickets;
import software.bernie.geckolib.core.animatable.model.CoreGeoBone;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.model.data.EntityModelData;

public class TechnodemonGreaterModel extends GeoModel<TechnodemonGreaterEntity> {

	public TechnodemonGreaterModel() {
	}

	@Override
	public Identifier getModelResource(TechnodemonGreaterEntity object) {
		return new Identifier(HWGMod.MODID, "geo/technodemon_greater_" + object.getVariant() + ".geo.json");
	}

	@Override
	public Identifier getTextureResource(TechnodemonGreaterEntity object) {
		return new Identifier(HWGMod.MODID, "textures/entity/technodemon_greater_" + object.getVariant() + ".png");
	}

	@Override
	public Identifier getAnimationResource(TechnodemonGreaterEntity object) {
		return new Identifier(HWGMod.MODID,
				"animations/technodemon_greater_" + object.getVariant() + ".animation.json");
	}
	
	@Override
	public RenderLayer getRenderType(TechnodemonGreaterEntity animatable, Identifier texture) {
		return RenderLayer.getEntityTranslucent(getTextureResource(animatable));
	}
	
	@Override
	public void setCustomAnimations(TechnodemonGreaterEntity animatable, long instanceId, AnimationState<TechnodemonGreaterEntity> animationState) {
		super.setCustomAnimations(animatable, instanceId, animationState);

		CoreGeoBone head = getAnimationProcessor().getBone("head");

		if (head != null) {
			EntityModelData entityData = animationState.getData(DataTickets.ENTITY_MODEL_DATA);
			head.setRotX((entityData.headPitch() - 5) * MathHelper.RADIANS_PER_DEGREE);
			head.setRotY(entityData.netHeadYaw() * MathHelper.RADIANS_PER_DEGREE);
		}
	}
}