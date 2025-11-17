package com.solar.vacumpunk.content;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.DirectionProperty;

public class VacuumTubeBlock extends Block implements EntityBlock {
    public static final DirectionProperty FACING = DirectionProperty.create("facing", Direction.Plane.HORIZONTAL);
    
    // 定义真空管的碰撞箱形状
    protected static final VoxelShape SHAPE = Shapes.or(
        // 底部支撑结构
        Block.box(4, 0, 5, 12, 2, 11),
        Block.box(5, 0, 4, 11, 2, 5),
        Block.box(5, 0, 11, 11, 2, 12),
        // 主体部分
        Block.box(4, 2, 5, 5, 4, 11),
        Block.box(11, 2, 5, 12, 4, 11),
        Block.box(5, 2, 4, 11, 4, 5),
        Block.box(5, 2, 11, 11, 4, 12),
        Block.box(4, 4, 5, 5, 14, 11),
        Block.box(11, 4, 5, 12, 14, 11),
        Block.box(5, 4, 4, 11, 14, 5),
        Block.box(5, 4, 11, 11, 14, 12),
        Block.box(5, 14, 5, 11, 15, 11),
        Block.box(7, 15, 7, 9, 16, 9)
    );

    public VacuumTubeBlock() {
        super(BlockBehaviour.Properties.of()
                .mapColor(MapColor.COLOR_BLACK)
                .instrument(NoteBlockInstrument.HAT)
                .lightLevel((state) -> 10) // 发光等级改为10，降低亮度
                .strength(3.0F, 6.0F)
                .requiresCorrectToolForDrops()
                .noOcclusion() // 允许透明渲染
                .isViewBlocking((state, level, pos) -> false)); // 不阻挡视线
        this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH));
    }
    
    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING);
    }
    
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return this.defaultBlockState().setValue(FACING, context.getHorizontalDirection().getOpposite());
    }
    
    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return SHAPE;
    }
    
    @Override
    public int getLightEmission(BlockState state, BlockGetter level, BlockPos pos) {
        return 10; // 返回亮度值改为10
    }
    
    @Override
    public RenderShape getRenderShape(BlockState state) {
        return RenderShape.MODEL; // 使用模型渲染
    }
    
    @Override
    public boolean propagatesSkylightDown(BlockState state, BlockGetter level, BlockPos pos) {
        return true; // 允许天光穿透
    }
    
    @Override
    public float getShadeBrightness(BlockState state, BlockGetter level, BlockPos pos) {
        return 1.0F; // 返回最大亮度值，确保方块自身发光
    }
    
    @Override
    public boolean isSignalSource(BlockState state) {
        return true; // 作为红石能源方块
    }
    
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new VacuumTubeBlockEntity(pos, state);
    }
    
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> type) {
        return (level1, pos, state1, blockEntity) -> {
            if (blockEntity instanceof VacuumTubeBlockEntity) {
                // 触发渲染更新
                level1.getLightEngine().checkBlock(pos);
            }
        };
    }
}