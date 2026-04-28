package com.softwaretesting.functions;


public class BaseFunctions {
    
    private static final double DEFAULT_EPSILON = 1e-10;
    private static final int MAX_ITERATIONS = 1000;
    
    /**
     * Вычисляет cos(x) через разложение в ряд Тейлора
     * cos(x) = 1 - x^2/2! + x^4/4! - x^6/6! + ...
     *
     * @param x Значение аргумента в радианах
     * @param epsilon Требуемая точность вычисления
     * @return Значение cos(x)
     * @throws IllegalArgumentException если epsilon <= 0
     */
    public static double cos(double x, double epsilon) {
        if (epsilon <= 0) {
            throw new IllegalArgumentException("Epsilon might be positive");
        }
        
        x = normalizeAngle(x);
        
        double result = 1.0;
        double term = 1.0;
        double x2 = x * x;
        
        for (int n = 1; n < MAX_ITERATIONS; n++) {
            term *= -x2 / ((2 * n - 1) * (2 * n));
            if (Math.abs(term) < epsilon) {
                break;
            }
            result += term;
        }
        
        return result;
    }
    
    /**
     * Вычисляет cos(x) с точностью по умолчанию (1e-10)
     *
     * @param x Значение аргумента в радианах
     * @return Значение cos(x)
     */
    public static double cos(double x) {
        return cos(x, DEFAULT_EPSILON);
    }
    
    /**
     * Вычисляет sec(x) = 1 / cos(x)
     *
     * @param x Значение аргумента в радианах
     * @return Значение sec(x)
     * @throws ArithmeticException если cos(x) близко к нулю
     */
    public static double sec(double x) {
        return sec(x, DEFAULT_EPSILON);
    }
    
    /**
     * Вычисляет sec(x) = 1 / cos(x) с заданной точностью
     *
     * @param x Значение аргумента в радианах
     * @param epsilon Требуемая точность вычисления
     * @return Значение sec(x)
     * @throws ArithmeticException если cos(x) близко к нулю
     */
    public static double sec(double x, double epsilon) {
        double cosValue = cos(x, epsilon);
        if (Math.abs(cosValue) < 1e-12) {
            throw new ArithmeticException("sec(x) not defined: cos(x) is close to zero");
        }
        return 1.0 / cosValue;
    }
    
    /**
     * Вычисляет натуральный логарифм через разложение в ряд
     * Используется формула: ln(x) = 2 * sum((1/(2n+1)) * ((x-1)/(x+1))^(2n+1))
     * Работает для x > 0
     *
     * @param x Значение аргумента (должно быть > 0)
     * @param epsilon Требуемая точность вычисления
     * @return Значение ln(x)
     * @throws IllegalArgumentException если x <= 0 или epsilon <= 0
     */
    public static double ln(double x, double epsilon) {
        if (x <= 0) {
            throw new IllegalArgumentException("ln(x) is defined only for x > 0");
        }
        if (epsilon <= 0) {
            throw new IllegalArgumentException("Epsilon might be positive");
        }
        
        double y = (x - 1.0) / (x + 1.0);
        double y2 = y * y;
        double result = 0.0;
        double term = y;
        
        for (int n = 0; n < MAX_ITERATIONS; n++) {
            double divisor = 2 * n + 1;
            if (Math.abs(term / divisor) < epsilon) {
                break;
            }
            result += term / divisor;
            term *= y2;
        }
        
        return 2 * result;
    }
    
    /**
     * Вычисляет натуральный логарифм с точностью по умолчанию (1e-10).
     *
     * @param x Значение аргумента (должно быть > 0)
     * @return Значение ln(x)
     * @throws IllegalArgumentException если x <= 0
     */
    public static double ln(double x) {
        return ln(x, DEFAULT_EPSILON);
    }
    
    /**
     * Вычисляет логарифм по произвольному основанию: log_base(x) = ln(x) / ln(base).
     *
     * @param x Значение аргумента (должно быть > 0)
     * @param base Основание логарифма (должно быть > 0 и != 1)
     * @return Значение log_base(x)
     * @throws IllegalArgumentException если x <= 0, base <= 0 или base == 1
     */
    public static double log(double x, double base) {
        return log(x, base, DEFAULT_EPSILON);
    }
    
    /**
     * Вычисляет логарифм по произвольному основанию с заданной точностью.
     *
     * @param x Значение аргумента (должно быть > 0)
     * @param base Основание логарифма (должно быть > 0 и != 1)
     * @param epsilon Требуемая точность вычисления
     * @return Значение log_base(x)
     * @throws IllegalArgumentException если x <= 0, base <= 0, base == 1 или epsilon <= 0
     */
    public static double log(double x, double base, double epsilon) {
        if (base <= 0 || base == 1) {
            throw new IllegalArgumentException("Logarithm base must be > 0 and != 1");
        }
        return ln(x, epsilon) / ln(base, epsilon);
    }
    
    /**
     * Приводит угол к диапазону [0, 2pi)
     *
     * @param x Угол в радианах
     * @return Нормализованный угол
     */
    private static double normalizeAngle(double x) {
        double twoPi = 2 * Math.PI;
        while (x < 0) {
            x += twoPi;
        }
        while (x >= twoPi) {
            x -= twoPi;
        }
        return x;
    }
}
