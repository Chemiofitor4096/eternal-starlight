package cn.leolezury.eternalstarlight.common.client.renderer.entity;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.entity.projectile.ChainOfSouls;
import cn.leolezury.eternalstarlight.common.registry.ESItems;
import cn.leolezury.eternalstarlight.common.util.Color;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;

@Environment(EnvType.CLIENT)
public class ChainOfSoulsRenderer extends EntityRenderer<ChainOfSouls> {
	private static final ResourceLocation ENTITY_TEXTURE = EternalStarlight.id("textures/entity/chain_of_souls.png");

	public ChainOfSoulsRenderer(EntityRendererProvider.Context context) {
		super(context);
	}

	public void render(ChainOfSouls chain, float f, float g, PoseStack stack, MultiBufferSource bufferSource, int i) {
		Player player = chain.getPlayerOwner();
		if (player != null) {
			stack.pushPose();
			float attackAnim = player.getAttackAnim(g);
			Vec3 vec3 = getPlayerHandPos(player, Mth.sin(Mth.sqrt(attackAnim) * 3.1415927F), g);
			Vec3 vec32 = new Vec3(Mth.lerp(g, chain.xo, chain.getX()), Mth.lerp(g, chain.yo, chain.getY()) + (double) chain.getEyeHeight(), Mth.lerp(g, chain.zo, chain.getZ()));
			float h = (float) chain.tickCount + g;
			float j = h * 0.15F % 1.0F;
			Vec3 vec33 = vec3.subtract(vec32);
			float k = (float) (vec33.length() + 0.1);
			vec33 = vec33.normalize();
			float l = (float) Math.acos(vec33.y);
			float m = (float) Math.atan2(vec33.z, vec33.x);
			stack.mulPose(Axis.YP.rotationDegrees((1.5707964F - m) * 57.295776F));
			stack.mulPose(Axis.XP.rotationDegrees(l * 57.295776F));
			float n = h * 0.05F * -1.5F;
			float width = 0.2F;
			float p = Mth.cos(n + 3.1415927F) * width;
			float q = Mth.sin(n + 3.1415927F) * width;
			float r = Mth.cos(n + 0.0F) * width;
			float s = Mth.sin(n + 0.0F) * width;
			float t = Mth.cos(n + 1.5707964F) * width;
			float u = Mth.sin(n + 1.5707964F) * width;
			float v = Mth.cos(n + 4.712389F) * width;
			float w = Mth.sin(n + 4.712389F) * width;
			float x = k;
			float y = 0.0F;
			float z = 0.4999F;
			float aa = -1.0F + j;
			float ab = k * 2.5F + aa;
			VertexConsumer vertexConsumer = bufferSource.getBuffer(RenderType.entityCutoutNoCull(ENTITY_TEXTURE));
			PoseStack.Pose pose = stack.last();
			vertex(vertexConsumer, pose, p, x, q, 0.4999F, ab);
			vertex(vertexConsumer, pose, p, 0.0F, q, 0.4999F, aa);
			vertex(vertexConsumer, pose, r, 0.0F, s, 0.0F, aa);
			vertex(vertexConsumer, pose, r, x, s, 0.0F, ab);
			vertex(vertexConsumer, pose, t, x, u, 0.4999F, ab);
			vertex(vertexConsumer, pose, t, 0.0F, u, 0.4999F, aa);
			vertex(vertexConsumer, pose, v, 0.0F, w, 0.0F, aa);
			vertex(vertexConsumer, pose, v, x, w, 0.0F, ab);
			stack.popPose();
			super.render(chain, f, g, stack, bufferSource, i);
		}
	}

	private static void vertex(VertexConsumer vertexConsumer, PoseStack.Pose pose, float x, float y, float z, float uvX, float uvY) {
		vertexConsumer.addVertex(pose, x, y, z).setColor(Color.WHITE.argb()).setUv(uvX, uvY).setOverlay(OverlayTexture.NO_OVERLAY).setLight(15728880).setNormal(0.0F, 1.0F, 0.0F);
	}

	private Vec3 getPlayerHandPos(Player player, float f, float g) {
		int i = player.getMainArm() == HumanoidArm.RIGHT ? 1 : -1;
		ItemStack itemStack = player.getMainHandItem();
		if (!itemStack.is(ESItems.CHAIN_OF_SOULS.get())) {
			i = -i;
		}

		if (this.entityRenderDispatcher.options.getCameraType().isFirstPerson() && player == Minecraft.getInstance().player) {
			double n = 960.0 / (double) this.entityRenderDispatcher.options.fov().get();
			float xRotFactor = player.getViewXRot(g) / 750f;
			Vec3 vec3 = this.entityRenderDispatcher.camera.getNearPlane().getPointOnPlane((float) i * (0.85F + xRotFactor), 0.1F - f * 0.1F).scale(n).yRot(f * 0.5F).xRot(-f * 0.7F);
			return player.getEyePosition(g).add(vec3);
		} else {
			float h = Mth.lerp(g, player.yBodyRotO, player.yBodyRot) * 0.017453292F;
			double d = Mth.sin(h);
			double e = Mth.cos(h);
			float j = player.getScale();
			double k = (double) i * 0.35 * (double) j;
			double l = 0.8 * (double) j;
			float m = player.isCrouching() ? -0.1875F : 0.0F;
			return player.getEyePosition(g).add(-e * k - d * l, (double) m - 0.45 * (double) j, -d * k + e * l);
		}
	}

	public ResourceLocation getTextureLocation(ChainOfSouls chain) {
		return TextureAtlas.LOCATION_BLOCKS;
	}
}