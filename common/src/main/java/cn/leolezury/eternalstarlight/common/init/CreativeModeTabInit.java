package cn.leolezury.eternalstarlight.common.init;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.platform.ESPlatform;
import cn.leolezury.eternalstarlight.common.util.register.RegistrationProvider;
import cn.leolezury.eternalstarlight.common.util.register.RegistryObject;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.CreativeModeTab;

public class CreativeModeTabInit {
    public static final RegistrationProvider<CreativeModeTab> TABS = RegistrationProvider.get(Registries.CREATIVE_MODE_TAB, EternalStarlight.MOD_ID);
    public static final RegistryObject<CreativeModeTab> ETERNAL_STARLIGHT = TABS.register("eternal_starlight", ESPlatform.INSTANCE::getESTab);
    public static void postRegistry() {}
}