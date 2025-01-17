package cn.leolezury.eternalstarlight.common.client.renderer.entity;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.entity.living.monster.LonestarSkeleton;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.SkeletonRenderer;
import net.minecraft.resources.ResourceLocation;

@Environment(EnvType.CLIENT)
public class LonestarSkeletonRenderer extends SkeletonRenderer<LonestarSkeleton> {
	private static final ResourceLocation LONESTAR_SKELETON_LOCATION = EternalStarlight.id("textures/entity/lonestar_skeleton.png");

	public static final ModelLayerLocation LONESTAR = new ModelLayerLocation(EternalStarlight.id("lonestar_skeleton"), "main");
	public static final ModelLayerLocation LONESTAR_INNER_ARMOR = new ModelLayerLocation(EternalStarlight.id("lonestar_skeleton"), "inner_armor");
	public static final ModelLayerLocation LONESTAR_OUTER_ARMOR = new ModelLayerLocation(EternalStarlight.id("lonestar_skeleton"), "outer_armor");

	public LonestarSkeletonRenderer(EntityRendererProvider.Context context) {
		super(context, LONESTAR, LONESTAR_INNER_ARMOR, LONESTAR_OUTER_ARMOR);
	}

	@Override
	public ResourceLocation getTextureLocation(LonestarSkeleton skeleton) {
		return LONESTAR_SKELETON_LOCATION;
	}
}
