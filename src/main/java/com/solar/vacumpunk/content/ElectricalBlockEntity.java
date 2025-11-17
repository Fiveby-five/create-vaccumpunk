package com.solar.vacumpunk.content;

import com.solar.vacumpunk.BlockEntityReg;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

/**
 * 电气方块实体基类
 * 用于处理需要动态计算和网络通信的电气参数
 * 适用于电阻、电容、电感和磁体等电气方块
 * 所有参数使用带符号整型，电感单位为微亨(μH)，电容单位为微法(μF)
 */
public class ElectricalBlockEntity extends BlockEntity {
    // 电压 (毫伏)
    protected int voltage = 0;
    
    // 电流 (毫安)
    protected int current = 0;
    
    // 频率 (毫赫兹)
    protected int frequency = 50000;
    
    // 相位 (毫弧度)
    protected int phase = 0;
    
    // 功率 (毫瓦)
    protected int power = 0;

    public ElectricalBlockEntity(BlockPos pos, BlockState state) {
        super(BlockEntityReg.ELECTRICAL_BLOCK_ENTITY.get(), pos, state);
    }

    /**
     * 外部触发的更新方法
     * 由网络中的其他方块或外部定时器调用
     */
    public void update() {
        if (level != null && !level.isClientSide) {
            // 在这里执行电气网络计算
            updateElectricalProperties();
        }
    }

    /**
     * 更新电气属性
     * 这里应该实现具体的电气网络计算逻辑
     */
    protected void updateElectricalProperties() {
        // TODO: 实现电气网络计算逻辑
        // 这里可以根据连接的其他电气方块计算电压、电流等参数
        
        // 示例计算功率
        if (getBlockState().getBlock() instanceof IElectricalProperties electricalBlock) {
            this.power = electricalBlock.getPower(this.voltage, this.current, this.phase);
        }
    }

    /**
     * 获取阻抗
     * @param frequency 频率(毫赫兹)
     * @return 阻抗值(毫欧)
     */
    public int getImpedance(int frequency) {
        if (getBlockState().getBlock() instanceof IElectricalProperties electricalBlock) {
            return electricalBlock.getImpedance(frequency);
        }
        return 0;
    }

    /**
     * 获取电流
     * @param voltage 电压(毫伏)
     * @param frequency 频率(毫赫兹)
     * @return 电流值(毫安)
     */
    public int getCurrent(int voltage, int frequency) {
        if (getBlockState().getBlock() instanceof IElectricalProperties electricalBlock) {
            return electricalBlock.getCurrent(voltage, frequency);
        }
        return 0;
    }

    // 以下是一些基础的getter和setter方法

    public int getVoltage() {
        return voltage;
    }

    public void setVoltage(int voltage) {
        this.voltage = voltage;
    }

    public int getCurrent() {
        return current;
    }

    public void setCurrent(int current) {
        this.current = current;
    }

    public int getFrequency() {
        return frequency;
    }

    public void setFrequency(int frequency) {
        this.frequency = frequency;
    }

    public int getPhase() {
        return phase;
    }

    public void setPhase(int phase) {
        this.phase = phase;
    }

    public int getPower() {
        return power;
    }

    public void setPower(int power) {
        this.power = power;
    }

    @Override
    protected void loadAdditional(CompoundTag tag, net.minecraft.core.HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);
        this.voltage = tag.getInt("Voltage");
        this.current = tag.getInt("Current");
        this.frequency = tag.getInt("Frequency");
        this.phase = tag.getInt("Phase");
        this.power = tag.getInt("Power");
    }

    @Override
    protected void saveAdditional(CompoundTag tag, net.minecraft.core.HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);
        tag.putInt("Voltage", this.voltage);
        tag.putInt("Current", this.current);
        tag.putInt("Frequency", this.frequency);
        tag.putInt("Phase", this.phase);
        tag.putInt("Power", this.power);
    }
}