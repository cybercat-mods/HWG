package mod.azure.hwg.client.models;

import mod.azure.hwg.HWGMod;
import mod.azure.hwg.entity.TechnodemonEntity;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import mod.azure.azurelib.constant.DataTickets;
import mod.azure.azurelib.core.animatable.model.CoreGeoBone;
import mod.azure.azurelib.core.animation.AnimationState;
import mod.azure.azurelib.model.GeoModel;
import mod.azure.azurelib.model.data.EntityModelData;

public class TechnodemonLesserModel extends GeoModel<TechnodemonEntity> {

	public TechnodemonLesserModel() {
	}

	@Override
	public ResourceLocation getModelResource(TechnodemonEntity object) {
		return new ResourceLocation(HWGMod.MODID, "geo/technodemon_lesser_" + object.getVariant() + ".geo.json");
	}

	@Override
	public ResourceLocation getTextureResource(TechnodemonEntity object) {
		return new ResourceLocation(HWGMod.MODID, "textures/entity/technodemon_lesser_" + object.getVariant() + ".png");
	}

	@Override
	public ResourceLocation getAnimationResource(TechnodemonEntity object) {
		return new ResourceLocation(HWGMod.MODID, "animations/technodemon_lesser_1.animation.json");
	}
	
	@Override
	public RenderType getRenderType(TechnodemonEntity animatable, ResourceLocation texture) {
		return RenderType.entityTranslucent(getTextureResource(animatable));
	}
	
	@Override
	public void setCustomAnimations(TechnodemonEntity animatable, long instanceId, AnimationState<TechnodemonEntity> animationState) {
		super.setCustomAnimations(animatable, instanceId, animationState);

		CoreGeoBone head = getAnimationProcessor().getBone("head");

		if (head != null) {
			EntityModelData entityData = animationState.getData(DataTickets.ENTITY_MODEL_DATA);
			head.setRotX((entityData.headPitch() - 5) * Mth.DEG_TO_RAD);
			head.setRotY(entityData.netHeadYaw() * Mth.DEG_TO_RAD);
		}
	}
}