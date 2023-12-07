package mod.azure.hwg.client.render;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import mod.azure.azurelib.animatable.GeoEntity;
import mod.azure.azurelib.cache.object.GeoBone;
import mod.azure.azurelib.renderer.GeoEntityRenderer;
import mod.azure.azurelib.renderer.layer.BlockAndItemGeoLayer;
import mod.azure.hwg.client.models.HWGEntityModel;
import mod.azure.hwg.entity.*;
import mod.azure.hwg.entity.enums.EntityEnum;
import mod.azure.hwg.util.registry.HWGItems;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

public class HWGMobRender<T extends HWGEntity & GeoEntity> extends GeoEntityRenderer<T> {
    public HWGMobRender(EntityRendererProvider.Context renderManager, EntityEnum entityType) {
        super(renderManager, new HWGEntityModel<>(entityType));
        addRenderLayer(new BlockAndItemGeoLayer<>(this) {
            @Nullable
            @Override
            protected ItemStack getStackForBone(GeoBone bone, T animatable) {
                return switch (bone.getName()) {
                    case "rArmRuff", "rightHand" -> animatable.getItemBySlot(EquipmentSlot.MAINHAND);
                    default -> null;
                };
            }

            @Override
            protected ItemDisplayContext getTransformTypeForStack(GeoBone bone, ItemStack stack, T animatable) {
                return ItemDisplayContext.THIRD_PERSON_RIGHT_HAND;
            }

            @Override
            protected void renderStackForBone(PoseStack poseStack, GeoBone bone, ItemStack stack, T animatable, MultiBufferSource bufferSource, float partialTick, int packedLight, int packedOverlay) {
                if (animatable.getMainHandItem().is(HWGItems.MINIGUN)) {
                    poseStack.mulPose(Axis.XP.rotationDegrees(-15));
                } else {
                    poseStack.mulPose(Axis.XP.rotationDegrees(270));
                }
                if (animatable.getMainHandItem().is(HWGItems.MINIGUN)) {
                    poseStack.mulPose(Axis.YP.rotationDegrees(-15));
                } else if (animatable instanceof TechnodemonEntity) {
                    poseStack.mulPose(Axis.YP.rotationDegrees(animatable.getMainHandItem().is(HWGItems.BRIMSTONE) ? 0 : 16));
                } else {
                    poseStack.mulPose(Axis.YP.rotationDegrees(0));
                }
                poseStack.mulPose(Axis.ZP.rotationDegrees(0f));
                if (animatable instanceof SpyEntity || animatable instanceof MercEntity) {
                    if (animatable.getMainHandItem().is(HWGItems.MINIGUN)) poseStack.translate(0.0D, 0.15D, -0.1D);
                    else poseStack.translate(0.0D, 0.1D, -0.1D);
                } else if (animatable instanceof TechnodemonEntity) {
                    if (animatable.getMainHandItem().is(HWGItems.BRIMSTONE)) poseStack.translate(0.0D, 0.15D, -0.7D);
                    else poseStack.translate(0.2D, 0.15D, -0.65D);
                } else if (animatable instanceof TechnodemonGreaterEntity) {
                    if (animatable.getMainHandItem().is(HWGItems.MINIGUN)) poseStack.translate(0.1D, 0.15D, -0.5D);
                    else poseStack.translate(0.1D, 0.1D, -0.1D);
                    if (animatable.getMainHandItem().is(HWGItems.BRIMSTONE)) poseStack.scale(1.1F, 1.1F, 1.1F);
                }
                super.renderStackForBone(poseStack, bone, stack, animatable, bufferSource, partialTick, packedLight, packedOverlay);
            }
        });
        this.shadowRadius = 0.7F;
    }
}
