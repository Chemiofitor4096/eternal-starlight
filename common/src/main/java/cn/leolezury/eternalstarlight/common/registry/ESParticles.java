package cn.leolezury.eternalstarlight.common.registry;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.particle.ESExplosionParticleOptions;
import cn.leolezury.eternalstarlight.common.particle.ESSmokeParticleOptions;
import cn.leolezury.eternalstarlight.common.particle.LightningParticleOptions;
import cn.leolezury.eternalstarlight.common.platform.registry.RegistrationProvider;
import cn.leolezury.eternalstarlight.common.platform.registry.RegistryObject;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;

public class ESParticles {
    public static final RegistrationProvider<ParticleType<?>> PARTICLE_TYPES = RegistrationProvider.get(Registries.PARTICLE_TYPE, EternalStarlight.MOD_ID);
    public static final RegistryObject<ParticleType<?>, SimpleParticleType> STARLIGHT = PARTICLE_TYPES.register("starlight", () -> new SimpleParticleType(false));
    public static final RegistryObject<ParticleType<?>, SimpleParticleType> SCARLET_LEAVES = PARTICLE_TYPES.register("scarlet_leaves", () -> new SimpleParticleType(false));
    public static final RegistryObject<ParticleType<?>, SimpleParticleType> ENERGY = PARTICLE_TYPES.register("energy", () -> new SimpleParticleType(false));
    public static final RegistryObject<ParticleType<?>, SimpleParticleType> POISON = PARTICLE_TYPES.register("poison", () -> new SimpleParticleType(false));
    public static final RegistryObject<ParticleType<?>, ParticleType<LightningParticleOptions>> LIGHTNING = PARTICLE_TYPES.register("lightning", () -> new ParticleType<>(false) {
        @Override
        public MapCodec<LightningParticleOptions> codec() {
            return LightningParticleOptions.CODEC;
        }

        @Override
        public StreamCodec<? super RegistryFriendlyByteBuf, LightningParticleOptions> streamCodec() {
            return LightningParticleOptions.STREAM_CODEC;
        }
    });
    public static final RegistryObject<ParticleType<?>, SimpleParticleType> BLADE_SHOCKWAVE = PARTICLE_TYPES.register("blade_shockwave", () -> new SimpleParticleType(false));
    public static final RegistryObject<ParticleType<?>, SimpleParticleType> CRYSTALLIZED_MOTH_SONAR = PARTICLE_TYPES.register("crystallized_moth_sonar", () -> new SimpleParticleType(false));
    public static final RegistryObject<ParticleType<?>, SimpleParticleType> AMARAMBER_FLAME = PARTICLE_TYPES.register("amaramber_flame", () -> new SimpleParticleType(false));
    public static final RegistryObject<ParticleType<?>, ParticleType<ESExplosionParticleOptions>> EXPLOSION = PARTICLE_TYPES.register("explosion", () -> new ParticleType<>(false) {
        @Override
        public MapCodec<ESExplosionParticleOptions> codec() {
            return ESExplosionParticleOptions.CODEC;
        }

        @Override
        public StreamCodec<? super RegistryFriendlyByteBuf, ESExplosionParticleOptions> streamCodec() {
            return ESExplosionParticleOptions.STREAM_CODEC;
        }
    });
    public static final RegistryObject<ParticleType<?>, ParticleType<ESSmokeParticleOptions>> SMOKE = PARTICLE_TYPES.register("smoke", () -> new ParticleType<>(false) {
        @Override
        public MapCodec<ESSmokeParticleOptions> codec() {
            return ESSmokeParticleOptions.CODEC;
        }

        @Override
        public StreamCodec<? super RegistryFriendlyByteBuf, ESSmokeParticleOptions> streamCodec() {
            return ESSmokeParticleOptions.STREAM_CODEC;
        }
    });
    public static final RegistryObject<ParticleType<?>, SimpleParticleType> AETHERSENT_SMOKE = PARTICLE_TYPES.register("aethersent_smoke", () -> new SimpleParticleType(false));
    public static final RegistryObject<ParticleType<?>, SimpleParticleType> SMOKE_TRAIL = PARTICLE_TYPES.register("smoke_trail", () -> new SimpleParticleType(false));
    public static final RegistryObject<ParticleType<?>, SimpleParticleType> AETHERSENT_EXPLOSION = PARTICLE_TYPES.register("aethersent_explosion", () -> new SimpleParticleType(false));

    public static void loadClass() {}
}
