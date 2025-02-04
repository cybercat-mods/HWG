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
import mod.azure.hwg.entity.animation.MercAnimator;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;

public class MercRender<T extends MercEntity> extends AzEntityRenderer<MercEntity> {

    private static final ResourceLocation MODEL = CommonMod.modResource("geo/entity/merc_illager.geo.json");

    public MercRender(EntityRendererProvider.Context context) {
        super(
                AzEntityRendererConfig.<MercEntity>builder(
                        $ -> MODEL,
                                entity -> CommonMod.modResource("textures/entity/merc_" + entity.getVariant() + ".png"))
                        .setAnimatorProvider(MercAnimator::new)
                        .addRenderLayer(new AzBlockAndItemLayer<MercEntity>() {

                            @Override
                            public ItemStack itemStackForBone(AzBone bone, MercEntity animatable) {
                                return switch (bone.getName()) {
                                    case "rArmRuff", "rightHand" -> animatable.getItemBySlot(EquipmentSlot.MAINHAND);
                                    default -> null;
                                };
                            }

                            @Override
                            protected ItemDisplayContext getTransformTypeForStack(AzBone bone, ItemStack stack, MercEntity animatable) {
                                return ItemDisplayContext.THIRD_PERSON_RIGHT_HAND;
                            }

                            @Override
                            protected void renderItemForBone(AzRendererPipelineContext<MercEntity> context, AzBone bone, ItemStack itemStack, MercEntity animatable) {
                                context.poseStack().mulPose(Axis.XP.rotationDegrees(270));
                                context.poseStack().mulPose(Axis.YP.rotationDegrees(0));
                                context.poseStack().mulPose(Axis.ZP.rotationDegrees(0f));
                                context.poseStack().translate(0.0D, 0.1D, -0.1D);
                                super.renderItemForBone(context, bone, itemStack, animatable);
                            }
                        })
                        .build(),
                context
        );
        this.shadowRadius = 0.7F;
    }
}
