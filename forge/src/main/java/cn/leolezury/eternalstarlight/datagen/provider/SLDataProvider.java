package cn.leolezury.eternalstarlight.datagen.provider;

import cn.leolezury.eternalstarlight.EternalStarlight;
import cn.leolezury.eternalstarlight.datagen.ConfiguredFeatureInit;
import cn.leolezury.eternalstarlight.datagen.DamageTypeInit;
import cn.leolezury.eternalstarlight.datagen.PlacedFeatureInit;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.DatapackBuiltinEntriesProvider;

import java.util.Set;
import java.util.concurrent.CompletableFuture;

public class SLDataProvider extends DatapackBuiltinEntriesProvider {
    public static final RegistrySetBuilder BUILDER = new RegistrySetBuilder()
            .add(Registries.CONFIGURED_FEATURE, ConfiguredFeatureInit::bootstrap)
            .add(Registries.PLACED_FEATURE, PlacedFeatureInit::bootstrap)
            .add(Registries.DAMAGE_TYPE, DamageTypeInit::bootstrap);

    public SLDataProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries, BUILDER, Set.of(EternalStarlight.MOD_ID));
    }
}