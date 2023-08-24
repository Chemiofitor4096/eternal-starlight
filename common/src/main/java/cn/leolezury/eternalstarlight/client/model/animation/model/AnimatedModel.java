package cn.leolezury.eternalstarlight.client.model.animation.model;

import cn.leolezury.eternalstarlight.client.model.animation.SLKeyframeAnimations;
import net.minecraft.client.animation.AnimationDefinition;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.world.entity.AnimationState;
import org.joml.Vector3f;

import java.util.Optional;

public interface AnimatedModel {
    Vector3f ANIMATION_VECTOR_CACHE = new Vector3f();

    default void animate(AnimationState state, AnimationDefinition definition, float tickCount) {
        this.animate(state, definition, tickCount, 1.0F);
    }

    default void animate(AnimationState state, AnimationDefinition definition, float tickCount, float speed) {
        state.updateTime(tickCount, speed);
        state.ifStarted((animState) -> SLKeyframeAnimations.animate(this, definition, animState.getAccumulatedTime(), 1.0F, ANIMATION_VECTOR_CACHE));
    }

    default void animateWalk(AnimationDefinition definition, float swing, float swingAmount, float speed, float scale) {
        long accumulatedTime = (long)(swing * 50.0F * speed);
        float interpolationScale = Math.min(swingAmount * scale, 1.0F);
        SLKeyframeAnimations.animate(this, definition, accumulatedTime, interpolationScale, ANIMATION_VECTOR_CACHE);
    }

    ModelPart root();
    default Optional<ModelPart> getAnyDescendantWithName(String name) {
        return name.equals("root") ? Optional.of(root()) : root().getAllParts().filter((part) -> part.hasChild(name)).findFirst().map((part) -> part.getChild(name));
    }
}