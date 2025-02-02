package mod.azure.hwg.client.render.projectiles;

import com.mojang.blaze3d.vertex.PoseStack;
import mod.azure.azurelib.common.api.client.helper.ClientUtils;
import mod.azure.azurelib.rewrite.render.entity.AzEntityRenderer;
import mod.azure.azurelib.rewrite.render.entity.AzEntityRendererConfig;
import mod.azure.hwg.CommonMod;
import mod.azure.hwg.entity.animation.GrenadeAnimator;
import mod.azure.hwg.entity.projectiles.GrenadeEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public class GrenadeRender extends AzEntityRenderer<GrenadeEntity> {

    private static final ResourceLocation MODEL = CommonMod.modResource("geo/grenade.geo.json");

    public GrenadeRender(EntityRendererProvider.Context context) {
        super(AzEntityRendererConfig.<GrenadeEntity>builder(
                $ -> MODEL, entity -> CommonMod.modResource("textures/item/projectiles/grenade_" +
                        (entity.getVariant() == 2 ? "frag" :
                                entity.getVariant() == 3 ? "napalm" :
                                        entity.getVariant() == 4 ? "smoke" :
                                                entity.getVariant() == 5 ? "stun" : "emp") + ".png"))
                .setAnimatorProvider(GrenadeAnimator::new).build(), context);
    }

    @Override
    public void render(@NotNull GrenadeEntity entity, float entityYaw, float partialTick, @NotNull PoseStack poseStack, @NotNull MultiBufferSource bufferSource, int packedLight) {
        ClientUtils.faceRotation(poseStack, entity, partialTick);
        super.render(entity, entityYaw, partialTick, poseStack, bufferSource, packedLight);
    }

}