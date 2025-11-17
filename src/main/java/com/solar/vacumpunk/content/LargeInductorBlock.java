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

public class LargeInductorBlock extends Block implements IElectricalProperties, EntityBlock {
    public static final DirectionProperty FACING = DirectionProperty.create("facing", Direction.Plane.HORIZONTAL);
    
    // 定义大型电感的碰撞箱形状（假设为非完整方块）
    protected static final VoxelShape SHAPE = Shapes.or(
        Block.box(1, 0, 1, 15, 12, 15) // 示例碰撞箱，你需要根据实际模型调整
    );
    
    private final int inductance; // 电感值 (μH)
    private final int dcResistance; // 直流电阻 (mΩ)
    private final int maxCurrent; // 最大电流 (mA)

    public LargeInductorBlock() {
        super(BlockBehaviour.Properties.of()
                .mapColor(MapColor.COLOR_BROWN)
                .instrument(NoteBlockInstrument.HAT)
                .lightLevel((state) -> 0)
                .strength(3.0F, 6.0F)
                .requiresCorrectToolForDrops()
                .noOcclusion() // 允许透明渲染
                .isViewBlocking((state, level, pos) -> false)
                ); // 添加声音效果
        this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH));
        this.inductance = 1000000; // 默认1000000μH (1H)
        this.dcResistance = 1000; // 默认1000mΩ (1Ω)
        this.maxCurrent = 5000; // 默认5000mA (5A)
    }

    public LargeInductorBlock(int inductance, int dcResistance, int maxCurrent) {
        super(BlockBehaviour.Properties.of()
                .mapColor(MapColor.COLOR_BROWN)
                .instrument(NoteBlockInstrument.HAT)
                .lightLevel((state) -> 0)
                .strength(3.0F, 6.0F)
                .requiresCorrectToolForDrops()
                .noOcclusion() // 允许透明渲染
                .isViewBlocking((state, level, pos) -> false)
                ); // 添加声音效果
        this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH));
        this.inductance = inductance;
        this.dcResistance = dcResistance;
        this.maxCurrent = maxCurrent;
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
        return dcResistance;
    }
    
    @Override
    public int getInductance() {
        return inductance;
    }
    
    @Override
    public int getCapacitance() {
        // 理想电感没有电容
        return 0;
    }
    
    public int getMaxCurrent() {
        return maxCurrent;
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