package cn.leolezury.eternalstarlight.common.mixins;

import cn.leolezury.eternalstarlight.common.registry.ESBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.FarmBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(FarmBlock.class)
public class FarmBlockMixin {
    @Inject(method = "getStateForPlacement", at = @At(value = "RETURN"), cancellable = true)
    private void es_getStateForPlacement(BlockPlaceContext blockPlaceContext, CallbackInfoReturnable<BlockState> cir) {
        if (((FarmBlock) (Object) this).defaultBlockState().is(ESBlocks.NIGHTFALL_FARMLAND.get()) && !((FarmBlock) (Object) this).defaultBlockState().canSurvive(blockPlaceContext.getLevel(), blockPlaceContext.getClickedPos())) {
            cir.setReturnValue(ESBlocks.NIGHTFALL_DIRT.get().defaultBlockState());
        }
    }
    
    @Inject(method = "turnToDirt", at = @At(value = "HEAD"), cancellable = true)
    private static void es_turnToDirt(Entity entity, BlockState blockState, Level level, BlockPos blockPos, CallbackInfo ci) {
        if (blockState.is(ESBlocks.NIGHTFALL_FARMLAND.get())) {
            ci.cancel();
            BlockState blockState2 = Block.pushEntitiesUp(blockState, ESBlocks.NIGHTFALL_DIRT.get().defaultBlockState(), level, blockPos);
            level.setBlockAndUpdate(blockPos, blockState2);
            level.gameEvent(GameEvent.BLOCK_CHANGE, blockPos, GameEvent.Context.of(entity, blockState2));
        }
    }
}