package com.softwaretesting.functions;

/**
 * Интерфейс для предоставления значений косинуса.
 * Позволяет подменять реальную реализацию на mock'и в тестах.
 */
public interface CosProvider {
    /**
     * Вычисляет cos(x) с заданной точностью
     *
     * @param x Значение аргумента в радианах
     * @param epsilon Требуемая точность вычисления
     * @return Значение cos(x)
     */
    double compute(double x, double epsilon);
}
