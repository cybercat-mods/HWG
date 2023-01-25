package mod.azure.hwg.client.models;

import mod.azure.hwg.HWGMod;
import mod.azure.hwg.entity.TechnodemonEntity;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import mod.azure.azurelib.constant.DataTickets;
import mod.azure.azurelib.core.animatable.model.CoreGeoBone;
import mod.azure.azurelib.core.animation.AnimationState;
import mod.azure.azurelib.model.GeoModel;
import mod.azure.azurelib.model.data.EntityModelData;

public class TechnodemonLesserModel extends GeoModel<TechnodemonEntity> {

	public TechnodemonLesserModel() {
	}

	@Override
	public Identifier getModelResource(TechnodemonEntity object) {
		return new Identifier(HWGMod.MODID, "geo/technodemon_lesser_" + object.getVariant() + ".geo.json");
	}

	@Override
	public Identifier getTextureResource(TechnodemonEntity object) {
		return new Identifier(HWGMod.MODID, "textures/entity/technodemon_lesser_" + object.getVariant() + ".png");
	}

	@Override
	public Identifier getAnimationResource(TechnodemonEntity object) {
		return new Identifier(HWGMod.MODID, "animations/technodemon_lesser_1.animation.json");
	}
	
	@Override
	public RenderLayer getRenderType(TechnodemonEntity animatable, Identifier texture) {
		return RenderLayer.getEntityTranslucent(getTextureResource(animatable));
	}
	
	@Override
	public void setCustomAnimations(TechnodemonEntity animatable, long instanceId, AnimationState<TechnodemonEntity> animationState) {
		super.setCustomAnimations(animatable, instanceId, animationState);

		CoreGeoBone head = getAnimationProcessor().getBone("head");

		if (head != null) {
			EntityModelData entityData = animationState.getData(DataTickets.ENTITY_MODEL_DATA);
			head.setRotX((entityData.headPitch() - 5) * MathHelper.RADIANS_PER_DEGREE);
			head.setRotY(entityData.netHeadYaw() * MathHelper.RADIANS_PER_DEGREE);
		}
	}
}