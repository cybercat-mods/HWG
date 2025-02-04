package mod.azure.hwg.client.render;

import com.mojang.math.Axis;
import mod.azure.azurelib.rewrite.model.AzBone;
import mod.azure.azurelib.rewrite.render.AzRendererPipelineContext;
import mod.azure.azurelib.rewrite.render.entity.AzEntityRenderer;
import mod.azure.azurelib.rewrite.render.entity.AzEntityRendererConfig;
import mod.azure.azurelib.rewrite.render.layer.AzBlockAndItemLayer;
import mod.azure.hwg.CommonMod;
import mod.azure.hwg.entity.TechnodemonGreaterEntity;
import mod.azure.hwg.entity.animation.TechnodemonGreaterAnimator;
import mod.azure.hwg.util.registry.HWGItems;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;

public class TechnodemonGreaterRender<T extends TechnodemonGreaterEntity> extends AzEntityRenderer<TechnodemonGreaterEntity> {

    public TechnodemonGreaterRender(EntityRendererProvider.Context context) {
        super(
                AzEntityRendererConfig.<TechnodemonGreaterEntity>builder(
                                entity -> CommonMod.modResource("geo/entity/technodemon_greater_" + entity.getVariant() + ".geo.json"),
                                entity -> CommonMod.modResource("textures/entity/technodemon_greater_" + entity.getVariant() + ".png"))
                        .setAnimatorProvider(TechnodemonGreaterAnimator::new)
                        .addRenderLayer(new AzBlockAndItemLayer<TechnodemonGreaterEntity>() {

                            @Override
                            public ItemStack itemStackForBone(AzBone bone, TechnodemonGreaterEntity animatable) {
                                return switch (bone.getName()) {
                                    case "rArmRuff", "rightHand" -> animatable.getItemBySlot(EquipmentSlot.MAINHAND);
                                    default -> null;
                                };
                            }

                            @Override
                            protected ItemDisplayContext getTransformTypeForStack(AzBone bone, ItemStack stack, TechnodemonGreaterEntity animatable) {
                                return ItemDisplayContext.THIRD_PERSON_RIGHT_HAND;
                            }

                            @Override
                            protected void renderItemForBone(AzRendererPipelineContext<TechnodemonGreaterEntity> context, AzBone bone, ItemStack itemStack, TechnodemonGreaterEntity animatable) {
                                if (context.animatable().getMainHandItem().is(HWGItems.MINIGUN.get())) {
                                    context.poseStack().mulPose(Axis.XP.rotationDegrees(-15));
                                } else {
                                    context.poseStack().mulPose(Axis.XP.rotationDegrees(270));
                                }
                                if (context.animatable().getMainHandItem().is(HWGItems.MINIGUN.get())) {
                                    context.poseStack().mulPose(Axis.YP.rotationDegrees(-15));
                                } else {
                                    context.poseStack().mulPose(Axis.YP.rotationDegrees(0));
                                }
                                context.poseStack().mulPose(Axis.ZP.rotationDegrees(0f));
                                if (context.animatable().getMainHandItem().is(HWGItems.MINIGUN.get())) {
                                    context.poseStack().translate(0.1D, 0.15D, -0.5D);
                                } else {
                                    context.poseStack().translate(0.1D, 0.1D, -0.1D);
                                }
                                if (context.animatable().getMainHandItem().is(HWGItems.BRIMSTONE.get())) {
                                    context.poseStack().scale(1.1F, 1.1F, 1.1F);
                                }
                                super.renderItemForBone(context, bone, itemStack, animatable);
                            }
                        })
                        .build(),
                context
        );
        this.shadowRadius = 0.7F;
    }
}
