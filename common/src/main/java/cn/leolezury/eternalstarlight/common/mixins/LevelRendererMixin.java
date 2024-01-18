package cn.leolezury.eternalstarlight.common.mixins;

import cn.leolezury.eternalstarlight.common.client.model.animation.PlayerAnimator;
import com.google.common.collect.Lists;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Camera;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.renderer.entity.layers.ItemInHandLayer;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.entity.player.PlayerRenderer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.Vec3;
import org.joml.Matrix4f;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;
import java.util.Map;

@Mixin(LevelRenderer.class)
public abstract class LevelRendererMixin {
    @Final
    @Shadow
    private RenderBuffers renderBuffers;

    @Shadow @Final private EntityRenderDispatcher entityRenderDispatcher;

    @Shadow protected abstract void renderEntity(Entity entity, double d, double e, double f, float g, PoseStack poseStack, MultiBufferSource multiBufferSource);

    @Inject(method = "renderLevel", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/LevelRenderer;checkPoseStack(Lcom/mojang/blaze3d/vertex/PoseStack;)V", ordinal = 0))
    public void es_renderLevel(PoseStack stack, float tickDelta, long limitTime, boolean renderBlockOutline, Camera camera, GameRenderer gameRenderer, LightTexture lightmapTextureManager, Matrix4f matrix4f, CallbackInfo info) {
        if (!camera.isDetached() && camera.getEntity() instanceof LocalPlayer player && player.isUsingItem()) {
            boolean renderModel = false;

            for (Map.Entry<PlayerAnimator.AnimationTrigger, PlayerAnimator.AnimationStateFunction> entry : PlayerAnimator.ANIMATIONS.entrySet()) {
                if (entry.getKey().shouldPlay(player)) {
                    PlayerAnimator.PlayerAnimationState state = entry.getValue().get(player);
                    if (state.renderLeftArm() || state.renderRightArm()) {
                        renderModel = true;
                    }
                }
            }

            if (renderModel) {
                Vec3 cameraPos = camera.getPosition();
                MultiBufferSource.BufferSource bufferSource = this.renderBuffers.bufferSource();
                PlayerAnimator.renderingFirstPersonPlayer = true;

                // Very, very tricky way to filter model layers
                // Maybe the best way keep mod capabilities
                PlayerRenderer playerRenderer = (PlayerRenderer) entityRenderDispatcher.getRenderer(player);
                List<RenderLayer<AbstractClientPlayer, PlayerModel<AbstractClientPlayer>>> prevLayers = Lists.newArrayList();
                prevLayers.addAll(playerRenderer.layers);
                List<RenderLayer<AbstractClientPlayer, PlayerModel<AbstractClientPlayer>>> layers = Lists.newArrayList();
                for (RenderLayer<AbstractClientPlayer, PlayerModel<AbstractClientPlayer>> layer : playerRenderer.layers) {
                    if (layer instanceof ItemInHandLayer) {
                        layers.add(layer);
                    }
                }
                playerRenderer.layers.clear();
                playerRenderer.layers.addAll(layers);
                renderEntity(player, cameraPos.x(), cameraPos.y(), cameraPos.z(), tickDelta, stack, bufferSource);
                playerRenderer.layers.clear();
                playerRenderer.layers.addAll(prevLayers);

                PlayerAnimator.renderingFirstPersonPlayer = false;
            }
        }
    }
}
