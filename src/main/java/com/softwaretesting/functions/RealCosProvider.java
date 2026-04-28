package com.softwaretesting.functions;

/**
 * Реальная реализация CosProvider.
 * Использует BaseFunctions.cos() для вычисления косинуса.
 */
public class RealCosProvider implements CosProvider {
    
    @Override
    public double compute(double x, double epsilon) {
        return BaseFunctions.cos(x, epsilon);
    }
}
