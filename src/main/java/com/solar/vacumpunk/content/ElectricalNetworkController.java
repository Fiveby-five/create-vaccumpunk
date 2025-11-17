package com.solar.vacumpunk.content;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;

import java.util.HashSet;
import java.util.Set;

/**
 * 电气网络控制器
 * 负责管理电气网络中的方块更新，避免使用性能消耗大的ticker
 */
public class ElectricalNetworkController {
    // 网络中的所有电气方块位置
    private final Set<BlockPos> electricalBlocks = new HashSet<>();
    
    // 网络所在的维度
    private final Level level;
    
    public ElectricalNetworkController(Level level) {
        this.level = level;
    }
    
    /**
     * 添加电气方块到网络中
     * @param pos 方块位置
     */
    public void addBlock(BlockPos pos) {
        electricalBlocks.add(pos);
    }
    
    /**
     * 从网络中移除电气方块
     * @param pos 方块位置
     */
    public void removeBlock(BlockPos pos) {
        electricalBlocks.remove(pos);
    }
    
    /**
     * 检查方块是否在网络中
     * @param pos 方块位置
     * @return 是否在网络中
     */
    public boolean containsBlock(BlockPos pos) {
        return electricalBlocks.contains(pos);
    }
    
    /**
     * 更新整个网络
     * 由外部定时器定期调用
     */
    public void updateNetwork() {
        if (level.isClientSide) {
            return;
        }
        
        // 更新网络中的所有方块
        for (BlockPos pos : electricalBlocks) {
            BlockEntity blockEntity = level.getBlockEntity(pos);
            if (blockEntity instanceof ElectricalBlockEntity electricalBlockEntity) {
                electricalBlockEntity.update();
            }
        }
    }
    
    /**
     * 更新网络中的特定方块
     * 当某个方块状态发生变化时调用
     * @param pos 发生变化的方块位置
     */
    public void updateBlock(BlockPos pos) {
        if (level.isClientSide) {
            return;
        }
        
        BlockEntity blockEntity = level.getBlockEntity(pos);
        if (blockEntity instanceof ElectricalBlockEntity electricalBlockEntity) {
            electricalBlockEntity.update();
        }
    }
    
    /**
     * 获取网络中的所有方块数量
     * @return 方块数量
     */
    public int getBlockCount() {
        return electricalBlocks.size();
    }
    
    /**
     * 清空网络
     */
    public void clear() {
        electricalBlocks.clear();
    }
}