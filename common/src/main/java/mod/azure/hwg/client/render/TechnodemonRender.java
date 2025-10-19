package mod.azure.hwg.client.render;

import com.mojang.math.Axis;
import mod.azure.azurelib.common.model.AzBone;
import mod.azure.azurelib.common.render.AzRendererPipelineContext;
import mod.azure.azurelib.common.render.entity.AzEntityRenderer;
import mod.azure.azurelib.common.render.entity.AzEntityRendererConfig;
import mod.azure.azurelib.common.render.layer.AzBlockAndItemLayer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;

import java.util.UUID;

import mod.azure.hwg.CommonMod;
import mod.azure.hwg.entity.TechnodemonEntity;
import mod.azure.hwg.entity.animation.TechnodemonAnimator;
import mod.azure.hwg.util.registry.HWGItems;

public class TechnodemonRender<T extends TechnodemonEntity> extends AzEntityRenderer<TechnodemonEntity> {

    public TechnodemonRender(EntityRendererProvider.Context context) {
        super(
            AzEntityRendererConfig.<TechnodemonEntity>builder(
                entity -> CommonMod.modResource("geo/entity/technodemon_lesser_" + entity.getVariant() + ".geo.json"),
                entity -> CommonMod.modResource("textures/entity/technodemon_lesser_" + entity.getVariant() + ".png")
            )
                .setAnimatorProvider(TechnodemonAnimator::new)
                .addRenderLayer(new AzBlockAndItemLayer<>() {

                    @Override
                    public ItemStack itemStackForBone(AzBone bone, TechnodemonEntity animatable) {
                        return switch (bone.getName()) {
                            case "rArmRuff", "rightHand" -> animatable.getItemBySlot(EquipmentSlot.MAINHAND);
                            default -> null;
                        };
                    }

                    @Override
                    protected ItemDisplayContext getTransformTypeForStack(
                        AzBone bone,
                        ItemStack stack,
                        TechnodemonEntity animatable
                    ) {
                        return ItemDisplayContext.THIRD_PERSON_RIGHT_HAND;
                    }

                    @Override
                    protected void renderItemForBone(
                        AzRendererPipelineContext<UUID, TechnodemonEntity> context,
                        AzBone bone,
                        ItemStack itemStack,
                        TechnodemonEntity animatable
                    ) {
                        if (context.animatable().getMainHandItem().is(HWGItems.MINIGUN.get())) {
                            context.poseStack().mulPose(Axis.XP.rotationDegrees(-15));
                        } else {
                            context.poseStack().mulPose(Axis.XP.rotationDegrees(270));
                        }
                        if (context.animatable().getMainHandItem().is(HWGItems.MINIGUN.get())) {
                            context.poseStack().mulPose(Axis.YP.rotationDegrees(-15));
                        } else {
                            context.poseStack()
                                .mulPose(
                                    Axis.YP.rotationDegrees(
                                        context.animatable().getMainHandItem().is(HWGItems.BRIMSTONE.get()) ? 0 : 16
                                    )
                                );
                        }
                        context.poseStack().mulPose(Axis.ZP.rotationDegrees(0f));
                        if (context.animatable().getMainHandItem().is(HWGItems.BRIMSTONE.get())) {
                            context.poseStack().translate(0.0D, 0.15D, -0.7D);
                        } else {
                            context.poseStack().translate(0.2D, 0.15D, -0.65D);
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
