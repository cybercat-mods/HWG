package mod.azure.hwg.client.render;

import com.mojang.blaze3d.vertex.PoseStack;
import mod.azure.azurelib.common.render.item.AzItemRenderer;
import mod.azure.azurelib.common.render.item.AzItemRendererConfig;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

import mod.azure.hwg.CommonMod;
import mod.azure.hwg.item.enums.GunTypeEnum;
import mod.azure.hwg.item.weapons.animations.GunAnimator;

public class GunRender extends AzItemRenderer {

    private final GunTypeEnum gunTypeEnum;

    private static final ResourceLocation HELL_MODEL = CommonMod.modResource(
        "geo/item/hellhorse_revolver/hellhorse_revolver.geo.json"
    );

    private static final ResourceLocation PISTOL_MODEL = CommonMod.modResource("geo/item/pistol/pistol.geo.json");

    private static final ResourceLocation PISTOL_TEXTURE = CommonMod.modResource("textures/item/pistol/pistol.png");

    private static final ResourceLocation MEANIE_TEXTURE = CommonMod.modResource(
        "textures/item/meanie_gun_1/meanie_gun.png"
    );

    public GunRender(String id, GunTypeEnum gunTypeEnum) {
        super(
            AzItemRendererConfig.builder(itemStack -> {
                if (gunTypeEnum == GunTypeEnum.SILVER_HELL) {
                    return HELL_MODEL;
                }
                if (gunTypeEnum == GunTypeEnum.SILVER_PISTOL) {
                    return PISTOL_MODEL;
                }
                return CommonMod.modResource("geo/item/" + id + "/" + id + ".geo.json");
            }, itemStack -> {
                if (gunTypeEnum == GunTypeEnum.SIL_PISTOL) {
                    return PISTOL_TEXTURE;
                }
                if (gunTypeEnum == GunTypeEnum.MEANIE) {
                    return MEANIE_TEXTURE;
                }
                return CommonMod.modResource("textures/item/" + id + "/" + id + ".png");
            })
                .setAnimatorProvider(GunAnimator::new)
                .enableAnimationOnlyInContexts(
                    ItemDisplayContext.FIRST_PERSON_LEFT_HAND,
                    ItemDisplayContext.FIRST_PERSON_RIGHT_HAND,
                    ItemDisplayContext.THIRD_PERSON_LEFT_HAND,
                    ItemDisplayContext.THIRD_PERSON_RIGHT_HAND
                )
                .build()
        );
        this.gunTypeEnum = gunTypeEnum;
    }

    @Override
    public void renderByItem(
        ItemStack stack,
        ItemDisplayContext transformType,
        @NotNull PoseStack poseStack,
        @NotNull MultiBufferSource source,
        int packedLight
    ) {
        super.renderByItem(stack, transformType, poseStack, source, packedLight);
        var model = rendererPipeline.context().bakedModel();
        if (gunTypeEnum == GunTypeEnum.ROCKETLAUNCHER) {
            model.getBone("rocket")
                .ifPresent(
                    bone -> bone.setHidden(stack.getDamageValue() == (stack.getMaxDamage() - 1))
                );
        }
    }
}
