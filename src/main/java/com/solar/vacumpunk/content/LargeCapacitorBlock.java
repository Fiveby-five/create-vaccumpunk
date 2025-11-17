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
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.DirectionProperty;

import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.BlockHitResult;

public class LargeCapacitorBlock extends Block implements IElectricalProperties, EntityBlock {
    public static final DirectionProperty FACING = DirectionProperty.create("facing", Direction.Plane.HORIZONTAL);
    
    // 定义大型电容的碰撞箱形状（假设为非完整方块）
    protected static final VoxelShape SHAPE = Shapes.or(
        Block.box(3, 0, 3, 13, 16, 13) // 示例碰撞箱，你需要根据实际模型调整
    );
    
    private final int capacitance; // 电容值 (μF)
    private final int maxVoltage; // 最大电压 (mV)

    public LargeCapacitorBlock() {
        super(BlockBehaviour.Properties.of()
                .mapColor(MapColor.COLOR_BLUE)
                .instrument(NoteBlockInstrument.HAT)
                .lightLevel((state) -> 0)
                .strength(3.0F, 6.0F)
                .requiresCorrectToolForDrops()
                .noOcclusion() // 允许透明渲染
                .isViewBlocking((state, level, pos) -> false)
                ); // 添加声音效果
        this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH));
        this.capacitance = 1000000; // 默认1000000μF (1F)
        this.maxVoltage = 10000; // 默认10000mV (10V)
    }

    public LargeCapacitorBlock(int capacitance, int maxVoltage) {
        super(BlockBehaviour.Properties.of()
                .mapColor(MapColor.COLOR_BLUE)
                .instrument(NoteBlockInstrument.HAT)
                .lightLevel((state) -> 0)
                .strength(3.0F, 6.0F)
                .requiresCorrectToolForDrops()
                .noOcclusion() // 允许透明渲染
                .isViewBlocking((state, level, pos) -> false)
                ); // 添加声音效果
        this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH));
        this.capacitance = capacitance;
        this.maxVoltage = maxVoltage;
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
    public net.minecraft.world.level.block.RenderShape getRenderShape(BlockState state) {
        return net.minecraft.world.level.block.RenderShape.MODEL; // 使用模型渲染
    }
    
    @Override
    public boolean propagatesSkylightDown(BlockState state, BlockGetter level, BlockPos pos) {
        return true; // 允许天光穿透
    }
    
    @Override
    public float getShadeBrightness(BlockState state, BlockGetter level, BlockPos pos) {
        return 1.0F;
    }
    
    @Override
    public int getResistance() {
        // 理想电容在直流情况下电阻无限大，在交流情况下有ESR（等效串联电阻）
        // 这里简化处理，返回一个很小的值表示漏电流路径
        return 1000000000; // 1000000000mΩ (1MΩ)
    }
    
    @Override
    public int getInductance() {
        // 理想电容没有电感
        return 0;
    }
    
    @Override
    public int getCapacitance() {
        return capacitance;
    }
    
    public int getMaxVoltage() {
        return maxVoltage;
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