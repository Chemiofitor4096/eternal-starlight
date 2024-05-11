package cn.leolezury.eternalstarlight.common;

import cn.leolezury.eternalstarlight.common.block.flammable.ESFlammabilityRegistry;
import cn.leolezury.eternalstarlight.common.client.helper.ClientHelper;
import cn.leolezury.eternalstarlight.common.client.helper.ClientSideHelper;
import cn.leolezury.eternalstarlight.common.client.helper.EmptyClientHelper;
import cn.leolezury.eternalstarlight.common.data.ESRegistries;
import cn.leolezury.eternalstarlight.common.platform.ESPlatform;
import cn.leolezury.eternalstarlight.common.registry.*;
import com.mojang.logging.LogUtils;
import net.minecraft.resources.ResourceLocation;
import org.slf4j.Logger;

public class EternalStarlight {
    public static final String MOD_ID = "eternal_starlight";
    public static final Logger LOGGER = LogUtils.getLogger();

    public static void init() {
        ESFluids.loadClass();
        ESBlocks.loadClass();
        ESDataComponents.loadClass();
        ESArmorMaterials.loadClass();
        ESItems.loadClass();
        ESCreativeModeTabs.loadClass();
        ESCriteriaTriggers.loadClass();
        ESEntities.loadClass();
        ESSensorTypes.loadClass();
        ESMobEffects.loadClass();
        ESBlockEntities.loadClass();
        ESEnchantments.loadClass();
        ESWorldCarvers.loadClass();
        ESFeatures.loadClass();
        ESPlacers.loadClass();
        ESTreeDecorators.loadClass();
        ESStructurePlacementTypes.loadClass();
        ESStructurePoolElementTypes.loadClass();
        ESParticles.loadClass();
        ESSoundEvents.loadClass();
        ESRecipeSerializers.loadClass();
        ESRecipes.loadClass();
        ESDataTransformerTypes.loadClass();
        ESSpells.loadClass();
        ESWeathers.loadClass();
        ESBoarwarfProfessions.loadClass();
        ESRegistries.loadClass();
        ESFlammabilityRegistry.registerDefaults();
    }

    public static ResourceLocation id(String string) {
        return new ResourceLocation(MOD_ID, string);
    }

    public static ClientHelper getClientHelper() {
        if (ESPlatform.INSTANCE.isPhysicalClient()) {
            return new ClientSideHelper();
        } else {
            return new EmptyClientHelper();
        }
    }
}
