package com.solar.vacumpunk.content;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.BlockHitResult;

public class ConnectorBlock extends Block implements IElectricalProperties, EntityBlock {
    public static final DirectionProperty FACING = BlockStateProperties.FACING;

    // 定义不同方向的碰撞箱 (根据模型实际方向调整)
    protected static final VoxelShape DOWN_SHAPE = Shapes.or(
        Block.box(5, 13, 5, 11, 16, 11),  // 顶部连接器主体
        Block.box(6, 10, 6, 10, 13, 10)   // 下部连接部分
    );
    
    protected static final VoxelShape UP_SHAPE = Shapes.or(
        Block.box(5, 0, 5, 11, 3, 11),    // 底部连接器主体
        Block.box(6, 3, 6, 10, 6, 10)     // 上部连接部分
    );
    
    protected static final VoxelShape NORTH_SHAPE = Shapes.or(
        Block.box(5, 5, 13, 11, 11, 16),  // 南面连接器主体
        Block.box(6, 6, 10, 10, 10, 13)   // 北面连接部分
    );
    
    protected static final VoxelShape SOUTH_SHAPE = Shapes.or(
        Block.box(5, 5, 0, 11, 11, 3),    // 北面连接器主体
        Block.box(6, 6, 3, 10, 10, 6)     // 南面连接部分
    );
    
    protected static final VoxelShape WEST_SHAPE = Shapes.or(
        Block.box(13, 5, 5, 16, 11, 11),  // 东面连接器主体
        Block.box(10, 6, 6, 13, 10, 10)   // 西面连接部分
    );
    
    protected static final VoxelShape EAST_SHAPE = Shapes.or(
        Block.box(0, 5, 5, 3, 11, 11),    // 西面连接器主体
        Block.box(3, 6, 6, 6, 10, 10)     // 东面连接部分
    );

    // 电气参数
    private final int resistance; // 电阻 (mΩ)
    private final int inductance; // 电感 (μH)
    private final int capacitance; // 电容 (μF)

    public ConnectorBlock() {
        super(BlockBehaviour.Properties.of()
                .mapColor(MapColor.METAL)
                .strength(3.0F, 6.0F)
                .requiresCorrectToolForDrops()
                .noOcclusion() // 允许透明渲染
                .isViewBlocking((state, level, pos) -> false) // 不阻挡视线
                .pushReaction(PushReaction.NORMAL)
                ); // 添加声音效果
        this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH));
        
        // 连接器的基本电气参数
        this.resistance = 10; // 10mΩ
        this.inductance = 1;  // 1μH
        this.capacitance = 10; // 10μF
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING);
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return this.defaultBlockState().setValue(FACING, context.getClickedFace());
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return switch (state.getValue(FACING)) {
            case DOWN -> DOWN_SHAPE;
            case UP -> UP_SHAPE;
            case NORTH -> NORTH_SHAPE;
            case SOUTH -> SOUTH_SHAPE;
            case WEST -> WEST_SHAPE;
            case EAST -> EAST_SHAPE;
        };
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
        return 1.0F;
    }

    // 电气属性接口实现
    @Override
    public int getResistance() {
        return resistance;
    }

    @Override
    public int getInductance() {
        return inductance;
    }

    @Override
    public int getCapacitance() {
        return capacitance;
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