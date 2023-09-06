package cn.leolezury.eternalstarlight.common.util;

import cn.leolezury.eternalstarlight.common.entity.interfaces.PersistentDataHolder;
import cn.leolezury.eternalstarlight.common.platform.ESPlatform;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.Vec3;
import org.joml.Matrix4f;
import org.joml.Quaternionf;
import org.joml.Vector3f;

import java.util.function.Supplier;

public class ESUtil {
    public static void runWhenOnClient(Supplier<Runnable> toRun) {
        if (ESPlatform.INSTANCE.isClientSide()) {
            toRun.get().run();
        }
    }

    public static float positionToPitch(Vec3 start, Vec3 end) {
        return positionToPitch(start.x, end.x, start.y, end.y, start.z, end.z);
    }

    public static float positionToYaw(Vec3 start, Vec3 end) {
        return positionToYaw(start.x, end.x, start.z, end.z);
    }

    public static float positionToPitch(double startX, double endX, double startY, double endY, double startZ, double endZ) {
        double d0 = endX - startX;
        double d1 = endY - startY;
        double d2 = endZ - startZ;
        double d3 = Math.sqrt(d0 * d0 + d2 * d2);
        return !(Math.abs(d1) > (double)1.0E-5F) && !(Math.abs(d3) > (double)1.0E-5F) ? 0 : (float)((Mth.atan2(d1, d3) * (double)(180F / (float)Math.PI)));
    }

    public static float positionToYaw(double startX, double endX, double startZ, double endZ) {
        double d0 = endX - startX;
        double d1 = endZ - startZ;
        return !(Math.abs(d1) > (double)1.0E-5F) && !(Math.abs(d0) > (double)1.0E-5F) ? 0 : (float)(Mth.atan2(d1, d0) * (double)(180F / (float)Math.PI));
    }

    public static Vec3 rotationToPosition(Vec3 startPos, float radius, float pitch, float yaw) {
        double endPosX = startPos.x + radius * Math.cos(yaw * Math.PI / 180d) * Math.cos(pitch * Math.PI / 180d);
        double endPosY = startPos.y + radius * Math.sin(pitch * Math.PI / 180d);
        double endPosZ = startPos.z + radius * Math.sin(yaw * Math.PI / 180d) * Math.cos(pitch * Math.PI / 180d);
        return new Vec3(endPosX, endPosY, endPosZ);
    }

    public static BlockPos rotateBlockPos(BlockPos centerPos, BlockPos pos, float pitch, float roll) {
        Vec3 posVec = pos.getCenter();
        Vec3 centerVec = centerPos.getCenter();
        Vector3f posVec3f = new Vector3f((float) posVec.x, (float) posVec.y, (float) posVec.z);
        Vector3f centerVec3f = new Vector3f((float) centerVec.x, (float) centerVec.y, (float) centerVec.z);

        Quaternionf quaternion = new Quaternionf().rotateX(pitch * Mth.PI / 180f).rotateZ(roll * Mth.PI / 180f);

        Matrix4f translationToOrigin = new Matrix4f().translation(-centerVec3f.x, -centerVec3f.y, -centerVec3f.z);
        Matrix4f inverseTranslation = new Matrix4f().translation(centerVec3f.x, centerVec3f.y, centerVec3f.z);

        Matrix4f rotationMatrix = new Matrix4f().rotate(quaternion);

        Matrix4f transformMatrix = new Matrix4f();
        transformMatrix.mul(inverseTranslation).mul(rotationMatrix).mul(translationToOrigin);

        posVec3f.mulPosition(transformMatrix);

        return new BlockPos((int) posVec3f.x, (int) posVec3f.y, (int) posVec3f.z);
    }

    public static CompoundTag getPersistentData(Entity entity) {
        if (entity instanceof PersistentDataHolder holder) {
            return holder.esGetPersistentData();
        }
        return new CompoundTag();
    }
}
