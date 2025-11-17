package com.solar.vacumpunk.content;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;

import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.BlockHitResult;

public class LargeMagnetBlock extends Block implements IElectricalProperties, EntityBlock {
    // 大型铁永磁体是一个完整方块
    
    private final int resistance; // 电阻值 (mΩ)

    public LargeMagnetBlock() {
        super(BlockBehaviour.Properties.of()
                .mapColor(MapColor.COLOR_BLACK)
                .instrument(NoteBlockInstrument.HAT)
                .lightLevel((state) -> 0)
                .strength(3.0F, 6.0F)
                .requiresCorrectToolForDrops()
                ); // 添加声音效果
        this.resistance = 1; // 默认1mΩ，磁体通常导电性很好
    }

    public LargeMagnetBlock(int resistance) {
        super(BlockBehaviour.Properties.of()
                .mapColor(MapColor.COLOR_BLACK)
                .instrument(NoteBlockInstrument.HAT)
                .lightLevel((state) -> 0)
                .strength(3.0F, 6.0F)
                .requiresCorrectToolForDrops()
                ); // 添加声音效果
        this.resistance = resistance;
    }

    @Override
    public net.minecraft.world.level.block.RenderShape getRenderShape(BlockState state) {
        return net.minecraft.world.level.block.RenderShape.MODEL; // 使用模型渲染
    }
    
    @Override
    public int getResistance() {
        return resistance;
    }
    
    @Override
    public int getInductance() {
        // 永磁体本身不具有电感特性
        return 0;
    }
    
    @Override
    public int getCapacitance() {
        // 永磁体本身不具有电容特性
        return 0;
    }
    

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new ElectricalBlockEntity(pos, state);
    }
    
    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> type) {
        // 不使用ticker，由外部定时器控制更新
        return null;
    }
}