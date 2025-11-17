package com.solar.vacumpunk.content;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
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
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

import javax.annotation.Nullable;

public class SocketBoardBlock extends Block implements IElectricalProperties, EntityBlock {
    public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;
    public static final BooleanProperty NORTH = BooleanProperty.create("north");
    public static final BooleanProperty SOUTH = BooleanProperty.create("south");
    public static final BooleanProperty WEST = BooleanProperty.create("west");
    public static final BooleanProperty EAST = BooleanProperty.create("east");

    // 定义完整方块的碰撞箱
    protected static final VoxelShape SHAPE = Block.box(0, 0, 0, 16, 16, 16);

    // 电气参数
    private final int resistance; // 电阻 (mΩ)
    private final int inductance; // 电感 (μH)
    private final int capacitance; // 电容 (μF)

    public SocketBoardBlock() {
        super(BlockBehaviour.Properties.of()
                .mapColor(MapColor.WOOD)
                .strength(2.0F, 4.0F)
                .requiresCorrectToolForDrops()
                );
        this.registerDefaultState(this.stateDefinition.any()
                .setValue(FACING, Direction.NORTH)
                .setValue(NORTH, false)
                .setValue(SOUTH, false)
                .setValue(WEST, false)
                .setValue(EAST, false));
        
        // 插板的基本电气参数
        this.resistance = 5;   // 5mΩ
        this.inductance = 1;   // 1μH
        this.capacitance = 5;  // 5μF
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING, NORTH, SOUTH, WEST, EAST);
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return this.defaultBlockState().setValue(FACING, context.getHorizontalDirection().getOpposite());
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return SHAPE;
    }

    @Override
    public RenderShape getRenderShape(BlockState state) {
        return RenderShape.MODEL; // 使用模型渲染
    }

    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {

        if (level.isClientSide) {
            return InteractionResult.SUCCESS;
        }

        // 检查是否点击了上表面
        if (hit.getLocation().y - pos.getY() > 0.125) { // 大于2/16 (0.125)
            // 根据点击位置确定方向
            double x = hit.getLocation().x - pos.getX();
            double z = hit.getLocation().z - pos.getZ();
            
            // 判断点击的是哪个边缘
            if (x < 0.25) { // 西边缘
                state = state.cycle(WEST);
            } else if (x > 0.75) { // 东边缘
                state = state.cycle(EAST);
            } else if (z < 0.25) { // 北边缘
                state = state.cycle(NORTH);
            } else if (z > 0.75) { // 南边缘
                state = state.cycle(SOUTH);
            } else {
                return InteractionResult.PASS;
            }
            
            level.setBlock(pos, state, 3);
            return InteractionResult.CONSUME;
        }
        
        return InteractionResult.PASS;
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