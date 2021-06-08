package mod.azure.hwg.client.render.weapons;

import mod.azure.hwg.HWGMod;
import mod.azure.hwg.entity.projectiles.FuelTankEntity;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3f;

public class FuelTankRender extends EntityRenderer<FuelTankEntity> {

	protected static final Identifier TEXTURE = new Identifier(HWGMod.MODID, "textures/blocks/barrel_explode.png");

	public FuelTankRender(EntityRendererFactory.Context renderManagerIn) {
		super(renderManagerIn);
		this.shadowRadius = 0.5F;
	}

	public void render(FuelTankEntity entityIn, float entityYaw, float partialTicks, MatrixStack matrixStackIn,
			VertexConsumerProvider bufferIn, int packedLightIn) {
		matrixStackIn.push();
		matrixStackIn.translate(0.0D, 0.5D, 0.0D);

		matrixStackIn.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion(-90.0F));
		matrixStackIn.translate(-0.5D, -0.5D, 0.5D);
		matrixStackIn.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion(90.0F));
		matrixStackIn.pop();
		super.render(entityIn, entityYaw, partialTicks, matrixStackIn, bufferIn, packedLightIn);
	}

	@Override
	public Identifier getTexture(FuelTankEntity entity) {
		return TEXTURE;
	}
}