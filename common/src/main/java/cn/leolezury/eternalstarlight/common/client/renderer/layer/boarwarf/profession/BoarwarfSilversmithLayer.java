package cn.leolezury.eternalstarlight.common.client.renderer.layer.boarwarf.profession;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.client.model.entity.boarwarf.BoarwarfModel;
import cn.leolezury.eternalstarlight.common.client.model.entity.boarwarf.profession.BoarwarfSilversmithModel;
import cn.leolezury.eternalstarlight.common.entity.living.npc.boarwarf.Boarwarf;
import cn.leolezury.eternalstarlight.common.registry.ESBoarwarfProfessions;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.resources.ResourceLocation;

@Environment(EnvType.CLIENT)
public class BoarwarfSilversmithLayer<T extends Boarwarf> extends RenderLayer<T, BoarwarfModel<T>> {
	private static final ResourceLocation TEXTURE = EternalStarlight.id("textures/entity/boarwarf/profession/silversmith.png");
	private final BoarwarfSilversmithModel<T> professionModel;

	public BoarwarfSilversmithLayer(RenderLayerParent<T, BoarwarfModel<T>> parent, EntityModelSet modelSet) {
		super(parent);
		this.professionModel = new BoarwarfSilversmithModel<>(modelSet.bakeLayer(BoarwarfSilversmithModel.LAYER_LOCATION));
	}

	@Override
	public void render(PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, T entity, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
		if (!entity.isInvisible() && entity.getProfession() == ESBoarwarfProfessions.SILVERSMITH.get()) {
			getParentModel().copyPropertiesTo(this.professionModel);
			this.professionModel.prepareMobModel(entity, limbSwing, limbSwingAmount, partialTicks);
			this.professionModel.setupAnim(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
			this.professionModel.copyPropertiesFrom(getParentModel());
			VertexConsumer vertexConsumer = bufferSource.getBuffer(RenderType.entityCutout(TEXTURE));
			this.professionModel.renderToBuffer(poseStack, vertexConsumer, packedLight, LivingEntityRenderer.getOverlayCoords(entity, 0.0F));
		}
	}
}
