package com.softwaretesting.functions;

/**
 * Реальная реализация логарифмического провайдера.
 * Делегирует вычисления в BaseFunctions.
 */
public class RealLogarithmProvider implements LogarithmProvider {

    @Override
    public double ln(double x, double epsilon) {
        return BaseFunctions.ln(x, epsilon);
    }

    @Override
    public double log(double x, double base, double epsilon) {
        return BaseFunctions.log(x, base, epsilon);
    }
}
