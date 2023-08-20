package cn.leolezury.eternalstarlight.init;

import cn.leolezury.eternalstarlight.EternalStarlight;
import cn.leolezury.eternalstarlight.util.register.RegistrationProvider;
import cn.leolezury.eternalstarlight.util.register.RegistryObject;
import cn.leolezury.eternalstarlight.world.feature.tree.TrunkBerriesDecorator;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecoratorType;

public class TreeDecoratorInit {
    public static final RegistrationProvider<TreeDecoratorType<?>> TREE_DECORATORS = RegistrationProvider.get(Registries.TREE_DECORATOR_TYPE, EternalStarlight.MOD_ID);
    public static final RegistryObject<TreeDecoratorType<TrunkBerriesDecorator>> TRUNK_BERRIES = TREE_DECORATORS.register("trunk_berries", () -> new TreeDecoratorType<>(TrunkBerriesDecorator.CODEC));
}
