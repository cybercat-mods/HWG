package mod.azure.hwg.client.render;

import com.mojang.math.Axis;
import mod.azure.azurelib.common.internal.client.util.RenderUtils;
import mod.azure.azurelib.rewrite.model.AzBone;
import mod.azure.azurelib.rewrite.render.AzRendererPipelineContext;
import mod.azure.azurelib.rewrite.render.entity.AzEntityRenderer;
import mod.azure.azurelib.rewrite.render.entity.AzEntityRendererConfig;
import mod.azure.azurelib.rewrite.render.layer.AzBlockAndItemLayer;
import mod.azure.hwg.CommonMod;
import mod.azure.hwg.entity.*;
import mod.azure.hwg.entity.animation.SpyAnimator;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;

public class SpyRender<T extends SpyEntity> extends AzEntityRenderer<SpyEntity> {

    private static final ResourceLocation MODEL = CommonMod.modResource("geo/entity/merc_illager.geo.json");

    public SpyRender(EntityRendererProvider.Context context) {
        super(
                AzEntityRendererConfig.<SpyEntity>builder(
                        $ -> MODEL,
                                entity -> CommonMod.modResource("textures/entity/spy_" + entity.getVariant() + ".png"))
                        .setAnimatorProvider(SpyAnimator::new)
                        .addRenderLayer(new AzBlockAndItemLayer<SpyEntity>() {

                            public ItemStack itemStackForBoneWithEntity(AzBone bone, T animatable) {
                                return switch (bone.getName()) {
                                    case "rArmRuff", "rightHand" -> animatable.getItemBySlot(EquipmentSlot.MAINHAND);
                                    default -> null;
                                };
                            }

                            @Override
                            public void renderForBone(AzRendererPipelineContext<SpyEntity> context, AzBone bone) {
                                var stack = itemStackForBoneWithEntity(bone, (T) context.animatable());

                                if (stack == null)
                                    return;

                                context.poseStack().pushPose();
                                RenderUtils.translateAndRotateMatrixForBone(context.poseStack(), bone);

                                renderItemForBone(context, bone, stack);

                                context.poseStack().popPose();
                            }

                            @Override
                            protected ItemDisplayContext getTransformTypeForStack(AzBone bone, ItemStack stack) {
                                return ItemDisplayContext.THIRD_PERSON_RIGHT_HAND;
                            }

                            @Override
                            protected void renderItemForBone(AzRendererPipelineContext<SpyEntity> context, AzBone bone, ItemStack itemStack) {
                                context.poseStack().mulPose(Axis.XP.rotationDegrees(270));
                                context.poseStack().mulPose(Axis.YP.rotationDegrees(0));
                                context.poseStack().mulPose(Axis.ZP.rotationDegrees(0f));
                                context.poseStack().translate(0.0D, 0.1D, -0.1D);
                                super.renderItemForBone(context, bone, itemStack);
                            }
                        })
                        .build(),
                context
        );
        this.shadowRadius = 0.7F;
    }
}
