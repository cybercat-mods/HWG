package mod.azure.hwg.client.render;

import mod.azure.hwg.client.models.TechnodemonGreaterModel;
import mod.azure.hwg.entity.TechnodemonGreaterEntity;
import mod.azure.hwg.item.weapons.Minigun;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.model.json.ModelTransformation.Mode;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3f;
import software.bernie.geckolib3.geo.render.built.GeoBone;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class TechnodemonGreaterRender extends GeoEntityRenderer<TechnodemonGreaterEntity> {

	public TechnodemonGreaterRender(EntityRendererFactory.Context renderManagerIn) {
		super(renderManagerIn, new TechnodemonGreaterModel());
		this.shadowRadius = 0.7F;
	}

	@Override
	public RenderLayer getRenderType(TechnodemonGreaterEntity animatable, float partialTicks, MatrixStack stack,
			VertexConsumerProvider renderTypeBuffer, VertexConsumer vertexBuilder, int packedLightIn,
			Identifier textureLocation) {
		return RenderLayer.getEntityTranslucent(getTextureLocation(animatable));
	}

	@Override
	public void renderRecursively(GeoBone bone, MatrixStack stack, VertexConsumer bufferIn, int packedLightIn,
			int packedOverlayIn, float red, float green, float blue, float alpha) {
		if (bone.getName().equals("rightHand")) {
			stack.push();
			stack.multiply(Vec3f.POSITIVE_X.getDegreesQuaternion(mainHand.getItem() instanceof Minigun ? -15 : -90));
			stack.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion(mainHand.getItem() instanceof Minigun ? -15 : 0));
			stack.multiply(Vec3f.POSITIVE_Z.getDegreesQuaternion(0));
			stack.translate(mainHand.getItem() instanceof Minigun ? 0.50D : 0.42D,
					mainHand.getItem() instanceof Minigun ? 1.79D : 0.12D,
					mainHand.getItem() instanceof Minigun ? -0.3D : 1.5D);
			stack.scale(1.0f, 1.0f, 1.0f);
			MinecraftClient.getInstance().getItemRenderer().renderItem(mainHand, Mode.THIRD_PERSON_RIGHT_HAND,
					packedLightIn, packedOverlayIn, stack, this.rtb, 0);
			stack.pop();
			bufferIn = rtb.getBuffer(RenderLayer.getEntityTranslucent(whTexture));
		}
		super.renderRecursively(bone, stack, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
	}

}