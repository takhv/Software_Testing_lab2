package com.softwaretesting.functions;

/**
 * Интерфейс для предоставления значений секанса.
 * Зависит от CosineProvider для вычисления косинуса.
 */
public interface SecantProvider {
    /**
     * Вычисляет sec(x) = 1 / cos(x) с заданной точностью
     *
     * @param x Значение аргумента в радианах
     * @param epsilon Требуемая точность
     * @return Значение sec(x)
     * @throws ArithmeticException если cos(x) близко к нулю
     */
    double compute(double x, double epsilon);
}
