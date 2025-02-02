package mod.azure.hwg.client.render;

import com.mojang.blaze3d.vertex.PoseStack;
import mod.azure.azurelib.rewrite.render.item.AzItemRenderer;
import mod.azure.azurelib.rewrite.render.item.AzItemRendererConfig;
import mod.azure.azurelib.rewrite.render.layer.AzAutoGlowingLayer;
import mod.azure.hwg.CommonMod;
import mod.azure.hwg.item.enums.GunTypeEnum;
import mod.azure.hwg.item.weapons.animations.GunAnimator;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public class GunRender extends AzItemRenderer {
    private final GunTypeEnum gunTypeEnum;

    private static ResourceLocation HELL_MODEL = CommonMod.modResource("geo/item/hellhorse_revolver/hellhorse_revolver.geo.json");

    private static ResourceLocation PISTOL_MODEL = CommonMod.modResource("geo/item/pistol/pistol.geo.json");

    private static ResourceLocation PISTOL_TEXTURE = CommonMod.modResource("textures/item/pistol/pistol.png");

    private static ResourceLocation MEANIE_TEXTURE = CommonMod.modResource("textures/item/meanie_gun_1/meanie_gun.png");

    public GunRender(String id, GunTypeEnum gunTypeEnum) {
        super(
                AzItemRendererConfig.builder(itemStack -> {
                            if (gunTypeEnum == GunTypeEnum.SILVER_HELL) {
                                return HELL_MODEL;
                            }
                            if (gunTypeEnum == GunTypeEnum.SILVER_PISTOL) {
                                return PISTOL_MODEL;
                            }
                            return CommonMod.modResource("geo/item/"+ id +"/"+ id + ".geo.json");
                        }, itemStack -> {
                            if (gunTypeEnum == GunTypeEnum.SIL_PISTOL) {
                                return PISTOL_TEXTURE;
                            }
                            if (gunTypeEnum == GunTypeEnum.MEANIE) {
                                return MEANIE_TEXTURE;
                            }
                            return CommonMod.modResource("textures/item/"+ id + "/" + id + ".png");
                        })
                        .setAnimatorProvider(GunAnimator::new)
                        .build()
        );
        this.gunTypeEnum = gunTypeEnum;
    }

    @Override
    public void renderByItem(ItemStack stack, @NotNull PoseStack poseStack, @NotNull MultiBufferSource source, int packedLight) {
        super.renderByItem(stack, poseStack, source, packedLight);
        var model = rendererPipeline.context().bakedModel();
        if (gunTypeEnum == GunTypeEnum.ROCKETLAUNCHER)
            model.getBone("rocket").get().setHidden(stack.getDamageValue() == (stack.getMaxDamage() - 1));
    }
}