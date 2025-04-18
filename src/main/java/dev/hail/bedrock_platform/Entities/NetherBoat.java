package dev.hail.bedrock_platform.Entities;

import dev.hail.bedrock_platform.Items.BPItems;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.world.entity.vehicle.ChestBoat;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.material.EmptyFluid;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.neoforged.fml.common.asm.enumextension.EnumProxy;
import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;

public class NetherBoat extends Boat {
    public NetherBoat(EntityType<? extends Boat> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }
    public NetherBoat(Level pLevel, double pX, double pY, double pZ) {
        this(BPEntities.NETHER_BOAT.get(), pLevel);
        this.setPos(pX, pY, pZ);
        this.xo = pX;
        this.yo = pY;
        this.zo = pZ;
    }

    @Override
    public void tick(){
        super.tick();
        this.outOfControlTicks = 0;
    }
    @Override
    public void floatBoat() {
        double d0 = -this.getGravity();
        double d1 = 0.0;
        this.invFriction = 0.05F;
        if (this.oldStatus == Boat.Status.IN_AIR && this.status != Boat.Status.IN_AIR && this.status != Boat.Status.ON_LAND) {
            this.waterLevel = this.getY(1.0);
            double d2 = (double)(this.getWaterLevelAbove() - this.getBbHeight()) + 0.101;
            if (this.level().noCollision(this, this.getBoundingBox().move(0.0, d2 - this.getY(), 0.0))) {
                this.setPos(this.getX(), d2, this.getZ());
                this.setDeltaMovement(this.getDeltaMovement().multiply(1.0, 0.0, 1.0));
                this.lastYd = 0.0;
            }

            this.status = Boat.Status.IN_WATER;
        } else {
            if (this.status == Boat.Status.IN_WATER) {
                d1 = (this.waterLevel - this.getY()) / (double)this.getBbHeight();
                this.invFriction = 0.9F;
            } else if (this.status == Boat.Status.UNDER_FLOWING_WATER) {
                d0 = 0.01F;
                this.invFriction = 0.9F;
            } else if (this.status == Boat.Status.UNDER_WATER) {
                d1 = 1F;
                this.invFriction = 0.45F;
            } else if (this.status == Boat.Status.IN_AIR) {
                this.invFriction = 0.9F;
            } else if (this.status == Boat.Status.ON_LAND) {
                this.invFriction = this.landFriction;
            }

            Vec3 vec3 = this.getDeltaMovement();
            this.setDeltaMovement(vec3.x * (double)this.invFriction, vec3.y + d0, vec3.z * (double)this.invFriction);
            this.deltaRotation = this.deltaRotation * this.invFriction;
            if (d1 > 0.0) {
                Vec3 vec31 = this.getDeltaMovement();
                this.setDeltaMovement(vec31.x, (vec31.y + d1 * (this.getDefaultGravity() / 0.65)) * 0.75, vec31.z);
            }
        }
    }

    @Override
    public boolean checkInWater() {
        AABB aabb = this.getBoundingBox();
        int i = Mth.floor(aabb.minX);
        int j = Mth.ceil(aabb.maxX);
        int k = Mth.floor(aabb.minY);
        int l = Mth.ceil(aabb.minY + 0.001);
        int i1 = Mth.floor(aabb.minZ);
        int j1 = Mth.ceil(aabb.maxZ);
        boolean flag = false;
        this.waterLevel = -Double.MAX_VALUE;
        BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();

        for (int k1 = i; k1 < j; k1++) {
            for (int l1 = k; l1 < l; l1++) {
                for (int i2 = i1; i2 < j1; i2++) {
                    blockpos$mutableblockpos.set(k1, l1, i2);
                    FluidState fluidstate = this.level().getFluidState(blockpos$mutableblockpos);
                    if (this.canBoatInFluid(fluidstate)) {
                        float f = (float)l1 + fluidstate.getHeight(this.level(), blockpos$mutableblockpos) + 0.25f;
                        this.waterLevel = Math.max(f, this.waterLevel);
                        flag |= aabb.minY < (double)f;
                    }
                }
            }
        }
        return flag;
    }
    @Override
    public boolean canBoatInFluid(@NotNull FluidState state) {
        return !(state.getType() instanceof EmptyFluid);
    }

    public static class NetherChestBoat extends ChestBoat {
        public NetherChestBoat(EntityType<? extends Boat> pEntityType, Level pLevel) {
            super(pEntityType, pLevel);
        }
        public NetherChestBoat(Level pLevel, double pX, double pY, double pZ) {
            this(BPEntities.NETHER_CHEST_BOAT.get(), pLevel);
            this.setPos(pX, pY, pZ);
            this.xo = pX;
            this.yo = pY;
            this.zo = pZ;
        }

        @Override
        public void floatBoat() {
            double d0 = -this.getGravity();
            double d1 = 0.0;
            this.invFriction = 0.05F;
            if (this.oldStatus == Boat.Status.IN_AIR && this.status != Boat.Status.IN_AIR && this.status != Boat.Status.ON_LAND) {
                this.waterLevel = this.getY(1.0);
                double d2 = (double)(this.getWaterLevelAbove() - this.getBbHeight()) + 0.101;
                if (this.level().noCollision(this, this.getBoundingBox().move(0.0, d2 - this.getY(), 0.0))) {
                    this.setPos(this.getX(), d2, this.getZ());
                    this.setDeltaMovement(this.getDeltaMovement().multiply(1.0, 0.0, 1.0));
                    this.lastYd = 0.0;
                }

                this.status = Boat.Status.IN_WATER;
            } else {
                if (this.status == Boat.Status.IN_WATER) {
                    d1 = (this.waterLevel - this.getY()) / (double)this.getBbHeight();
                    this.invFriction = 0.9F;
                } else if (this.status == Boat.Status.UNDER_FLOWING_WATER) {
                    d0 = 0.01F;
                    this.invFriction = 0.9F;
                } else if (this.status == Boat.Status.UNDER_WATER) {
                    d1 = 1F;
                    this.invFriction = 0.45F;
                } else if (this.status == Boat.Status.IN_AIR) {
                    this.invFriction = 0.9F;
                } else if (this.status == Boat.Status.ON_LAND) {
                    this.invFriction = this.landFriction;
                }

                Vec3 vec3 = this.getDeltaMovement();
                this.setDeltaMovement(vec3.x * (double)this.invFriction, vec3.y + d0, vec3.z * (double)this.invFriction);
                this.deltaRotation = this.deltaRotation * this.invFriction;
                if (d1 > 0.0) {
                    Vec3 vec31 = this.getDeltaMovement();
                    this.setDeltaMovement(vec31.x, (vec31.y + d1 * (this.getDefaultGravity() / 0.65)) * 0.75, vec31.z);
                }
            }
        }

        @Override
        public boolean checkInWater() {
            AABB aabb = this.getBoundingBox();
            int i = Mth.floor(aabb.minX);
            int j = Mth.ceil(aabb.maxX);
            int k = Mth.floor(aabb.minY);
            int l = Mth.ceil(aabb.minY + 0.001);
            int i1 = Mth.floor(aabb.minZ);
            int j1 = Mth.ceil(aabb.maxZ);
            boolean flag = false;
            this.waterLevel = -Double.MAX_VALUE;
            BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();

            for (int k1 = i; k1 < j; k1++) {
                for (int l1 = k; l1 < l; l1++) {
                    for (int i2 = i1; i2 < j1; i2++) {
                        blockpos$mutableblockpos.set(k1, l1, i2);
                        FluidState fluidstate = this.level().getFluidState(blockpos$mutableblockpos);
                        if (this.canBoatInFluid(fluidstate)) {
                            float f = (float)l1 + fluidstate.getHeight(this.level(), blockpos$mutableblockpos) + 0.25f;
                            this.waterLevel = Math.max(f, this.waterLevel);
                            flag |= aabb.minY < (double)f;
                        }
                    }
                }
            }
            return flag;
        }
        @Override
        public boolean canBoatInFluid(@NotNull FluidState state) {
            return !(state.getType() instanceof EmptyFluid);
        }
    }

    public static final EnumProxy<Boat.Type> CRIMSON = new EnumProxy<>(Boat.Type.class,
            (Supplier<Block>)()-> Blocks.CRIMSON_PLANKS, "bedrock_platform:crimson",
            BPItems.CRIMSON_BOAT, BPItems.CRIMSON_CHEST_BOAT,
            (Supplier<Item>)()-> Items.STICK, false);
    public static final EnumProxy<Boat.Type> WARPED = new EnumProxy<>(Boat.Type.class,
            (Supplier<Block>)()-> Blocks.WARPED_BUTTON, "bedrock_platform:warped",
            BPItems.WARPED_BOAT, BPItems.WARPED_CHEST_BOAT,
            (Supplier<Item>)()-> Items.STICK, false);
}
