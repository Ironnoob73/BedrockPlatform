package dev.hail.bedrock_platform.block.light.amethyst;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;

import static net.minecraft.util.Mth.floor;

public interface AmethystCandleLogic {
    BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
    IntegerProperty LIGHT = IntegerProperty.create("light",0,15);
    static int getLightFromHeight(int Y){
        int result = 0;
        if (Y <= 0){
            result = 15;
        }else if (Y <= 64){
            result = 15 - floor((float) Y / 4);
        }
        return result < 0 ? 0 : Math.min(result, 15);
    }
    static int getLightFromEnvironment(Level level, BlockPos blockPos) {
        int light = 0;
        if(!level.dimensionType().ultraWarm() && !(level.dimensionType().ambientLight() > 0)){
            light = AmethystCandleLogic.getLightFromHeight(blockPos.getY());
        }
        return light;
    }
    static int getLight (BlockState blockState){
        if(blockState.getValue(WATERLOGGED)){
            return 15;
        }
        return blockState.getValue(LIGHT);
    }

    static boolean canEmitParticle (BlockState pState){
        return pState.getValue(LIGHT) > 4 || pState.getValue(AmethystCandleLogic.WATERLOGGED);
    }
}
