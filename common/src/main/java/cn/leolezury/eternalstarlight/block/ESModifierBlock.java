package cn.leolezury.eternalstarlight.block;

import cn.leolezury.eternalstarlight.block.modifier.ModifiedBlock;
import cn.leolezury.eternalstarlight.block.modifier.ModifierContainer;
import net.minecraft.world.level.block.Block;

import java.util.function.Consumer;

public class ESModifierBlock extends Block implements ModifiedBlock {
    private ModifierContainer modifierContainer;
    public ESModifierBlock(Properties properties) {
        super(properties);
    }

    @Override
    public ESModifierBlock modifiers(Consumer... modifiers) {
        modifierContainer = new ModifierContainer<>(this, modifiers);
        return this;
    }

    @Override
    public ModifierContainer<Block> getModifierContainer() {
        return modifierContainer;
    }
}