package cn.leolezury.eternalstarlight.common.mixins;

import cn.leolezury.eternalstarlight.common.client.model.animation.PlayerAnimator;
import com.mojang.blaze3d.vertex.PoseStack;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.layers.ItemInHandLayer;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Environment(EnvType.CLIENT)
@Mixin(ItemInHandLayer.class)
public abstract class ItemInHandLayerMixin {
    @Inject(method = "renderArmWithItem", at = @At("HEAD"), cancellable = true)
    private void es_renderArmWithItem(LivingEntity livingEntity, ItemStack itemStack, ItemDisplayContext itemDisplayContext, HumanoidArm humanoidArm, PoseStack poseStack, MultiBufferSource multiBufferSource, int i, CallbackInfo ci) {
        if (PlayerAnimator.renderingFirstPersonPlayer) {
            if (!((ItemInHandLayer<?, ? extends PlayerModel>) (Object) this).getParentModel().leftArm.visible && humanoidArm == HumanoidArm.LEFT) {
                ci.cancel();
            }
            if (!((ItemInHandLayer<?, ? extends PlayerModel>) (Object) this).getParentModel().rightArm.visible && humanoidArm == HumanoidArm.RIGHT) {
                ci.cancel();
            }
        }
    }
}
