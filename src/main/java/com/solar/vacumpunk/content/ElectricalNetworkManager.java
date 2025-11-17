package com.solar.vacumpunk.content;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;

import java.util.HashMap;
import java.util.Map;

/**
 * 电气网络管理器
 * 管理世界中所有的电气网络
 */
public class ElectricalNetworkManager {
    // 单例实例
    private static ElectricalNetworkManager instance;
    
    // 所有维度的电气网络控制器
    private final Map<Level, ElectricalNetworkController> controllers = new HashMap<>();
    
    private ElectricalNetworkManager() {
    }
    
    /**
     * 获取单例实例
     * @return 电气网络管理器实例
     */
    public static ElectricalNetworkManager getInstance() {
        if (instance == null) {
            instance = new ElectricalNetworkManager();
        }
        return instance;
    }
    
    /**
     * 获取指定维度的电气网络控制器
     * @param level 维度
     * @return 电气网络控制器
     */
    public ElectricalNetworkController getController(Level level) {
        return controllers.computeIfAbsent(level, ElectricalNetworkController::new);
    }
    
    /**
     * 添加电气方块到网络中
     * @param level 维度
     * @param pos 方块位置
     */
    public void addBlock(Level level, BlockPos pos) {
        getController(level).addBlock(pos);
    }
    
    /**
     * 从网络中移除电气方块
     * @param level 维度
     * @param pos 方块位置
     */
    public void removeBlock(Level level, BlockPos pos) {
        getController(level).removeBlock(pos);
    }
    
    /**
     * 更新指定维度的电气网络
     * @param level 维度
     */
    public void updateNetwork(Level level) {
        getController(level).updateNetwork();
    }
    
    /**
     * 更新网络中的特定方块
     * @param level 维度
     * @param pos 发生变化的方块位置
     */
    public void updateBlock(Level level, BlockPos pos) {
        getController(level).updateBlock(pos);
    }
    
    /**
     * 检查方块是否在电气网络中
     * @param level 维度
     * @param pos 方块位置
     * @return 是否在网络中
     */
    public boolean isBlockInNetwork(Level level, BlockPos pos) {
        return getController(level).containsBlock(pos);
    }
    
    /**
     * 移除指定维度的控制器
     * @param level 维度
     */
    public void removeController(Level level) {
        controllers.remove(level);
    }
}