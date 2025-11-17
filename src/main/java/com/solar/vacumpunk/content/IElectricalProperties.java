package com.solar.vacumpunk.content;

/**
 * 电气属性接口，定义了电路元件的基本电气参数
 * 所有参数使用带符号整型，电感单位为微亨(μH)，电容单位为微法(μF)
 */
public interface IElectricalProperties {
    /**
     * 获取电阻值 (毫欧)
     * @return 电阻值(单位:毫欧)
     */
    int getResistance();
    
    /**
     * 获取电感值 (微亨)
     * @return 电感值(单位:微亨)
     */
    int getInductance();
    
    /**
     * 获取电容值 (微法)
     * @return 电容值(单位:微法)
     */
    int getCapacitance();
    
    /**
     * 获取感抗值 (毫欧)
     * @param frequency 频率 (毫赫兹)
     * @return 感抗值(单位:毫欧)
     */
    default int getInductiveReactance(int frequency) {
        // 感抗 = 2πfL (转换为毫欧)
        // L单位为微亨，f单位为毫赫兹，结果转换为毫欧
        return (int) (2 * Math.PI * frequency * getInductance() / 1000.0);
    }
    
    /**
     * 获取容抗值 (毫欧)
     * @param frequency 频率 (毫赫兹)
     * @return 容抗值(单位:毫欧)
     */
    default int getCapacitiveReactance(int frequency) {
        // 容抗 = 1/(2πfC) (转换为毫欧)
        // C单位为微法，f单位为毫赫兹，结果转换为毫欧
        int capacitance = getCapacitance();
        if (capacitance <= 0) {
            return Integer.MAX_VALUE;
        }
        return (int) (1000000.0 / (2 * Math.PI * frequency * capacitance));
    }
    
    /**
     * 获取阻抗值 (毫欧)
     * @param frequency 频率 (毫赫兹)
     * @return 阻抗值(单位:毫欧)
     */
    default int getImpedance(int frequency) {
        int resistance = getResistance();
        int inductiveReactance = getInductiveReactance(frequency);
        int capacitiveReactance = getCapacitiveReactance(frequency);
        // 阻抗 = √(R² + (XL - XC)²)
        int reactance = inductiveReactance - capacitiveReactance;
        return (int) Math.sqrt(resistance * (long) resistance + reactance * (long) reactance);
    }
    
    /**
     * 获取电流值 (毫安)
     * @param voltage 电压 (毫伏)
     * @param frequency 频率 (毫赫兹)
     * @return 电流值(单位:毫安)
     */
    default int getCurrent(int voltage, int frequency) {
        // 欧姆定律: I = V/Z
        int impedance = getImpedance(frequency);
        if (impedance <= 0) {
            return 0;
        }
        return (int) (voltage * 1000L / impedance);
    }
    
    /**
     * 获取压降 (毫伏)
     * @param current 电流 (毫安)
     * @param frequency 频率 (毫赫兹)
     * @return 压降(单位:毫伏)
     */
    default int getVoltageDrop(int current, int frequency) {
        // V = I * Z
        return (int) (current * (long) getImpedance(frequency) / 1000);
    }
    
    /**
     * 获取相位差 (毫弧度)
     * @param frequency 频率 (毫赫兹)
     * @return 相位差(单位:毫弧度)
     */
    default int getPhase(int frequency) {
        int resistance = getResistance();
        int inductiveReactance = getInductiveReactance(frequency);
        int capacitiveReactance = getCapacitiveReactance(frequency);
        // 相位 = arctan((XL - XC)/R)
        int reactance = inductiveReactance - capacitiveReactance;
        return (int) (Math.atan2(reactance, resistance) * 1000);
    }
    
    /**
     * 计算功率 (毫瓦)
     * @param voltage 电压 (毫伏)
     * @param current 电流 (毫安)
     * @param phase 相位差 (毫弧度)
     * @return 功率(单位:毫瓦)
     */
    default int getPower(int voltage, int current, int phase) {
        // P = V * I * cos(φ)
        double cosPhase = Math.cos(phase / 1000.0);
        return (int) (voltage * (long) current * cosPhase / 1000);
    }
}