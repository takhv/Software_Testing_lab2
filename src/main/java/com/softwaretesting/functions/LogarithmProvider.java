package com.softwaretesting.functions;

/**
 * Интерфейс для логарифмических вычислений.
 * Позволяет внедрять реальные или mock реализации в модули.
 */
public interface LogarithmProvider {
    /**
     * Вычисляет натуральный логарифм ln(x).
     */
    double ln(double x, double epsilon);

    /**
     * Вычисляет логарифм по основанию base: log_base(x).
     */
    double log(double x, double base, double epsilon);
}
