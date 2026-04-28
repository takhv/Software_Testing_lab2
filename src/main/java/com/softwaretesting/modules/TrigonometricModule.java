package com.softwaretesting.modules;

import com.softwaretesting.functions.CosProvider;
import com.softwaretesting.functions.RealCosProvider;


/**
 * Модуль для вычисления тригонометрических функций.
 * Использует Dependency Injection: CosProvider подставляется через конструктор.
 * Это позволяет легко подменять реальные вычисления на mock'и в тестах.
 */
public class TrigonometricModule {
    
    private static final double DEFAULT_EPSILON = 1e-10;
    private final CosProvider cosineProvider;
    private static final TrigonometricModule DEFAULT_INSTANCE = new TrigonometricModule();
    
    /**
     * Конструктор с явным указанием провайдера косинуса
     *
     * @param cosineProvider Провайдер для вычисления косинуса
     */
    public TrigonometricModule(CosProvider cosineProvider) {
        this.cosineProvider = cosineProvider;
    }
    
    /**
     * Конструктор, использующий реальный провайдер по умолчанию
     */
    public TrigonometricModule() {
        this(new RealCosProvider());
    }
    
    /**
     * Вычисляет sec^2(x) = 1 / cos^2(x)
     *
     * @param x Значение аргумента в радианах
     * @return Значение sec^2(x)
     * @throws ArithmeticException если cos(x) близко к нулю
     */
    public double computeSecSquaredInstance(double x) {
        return computeSecSquaredInstance(x, DEFAULT_EPSILON);
    }
    
    /**
     * Вычисляет sec^2(x) с заданной точностью
     *
     * @param x Значение аргумента в радианах
     * @param epsilon Требуемая точность
     * @return Значение sec^2(x)
     * @throws ArithmeticException если cos(x) близко к нулю
     */
    public double computeSecSquaredInstance(double x, double epsilon) {
        double cosValue = cosineProvider.compute(x, epsilon);
        
        if (Math.abs(cosValue) < epsilon) {
            throw new ArithmeticException(
                String.format("cos(%f) is too close to zero: %f", x, cosValue)
            );
        }
        
        double sec = 1.0 / cosValue;
        return sec * sec;
    }
    
    /**
     * Вычисляет sec(x) (вспомогательный метод)
     *
     * @param x Значение аргумента в радианах
     * @return Значение sec(x)
     */
    public double computeSecInstance(double x) {
        return computeSecInstance(x, DEFAULT_EPSILON);
    }
    
    /**
     * Вычисляет sec(x) с заданной точностью
     *
     * @param x Значение аргумента в радианах
     * @param epsilon Требуемая точность
     * @return Значение sec(x)
     */
    public double computeSecInstance(double x, double epsilon) {
        double cosValue = cosineProvider.compute(x, epsilon);
        
        if (Math.abs(cosValue) < epsilon) {
            throw new ArithmeticException(
                String.format("cos(%f) is too close to zero: %f", x, cosValue)
            );
        }
        
        return 1.0 / cosValue;
    }
    
    /**
     * Вычисляет sec^2(x) (статический метод обратной совместимости)
     *
     * @param x Значение аргумента в радианах
     * @return Значение sec^2(x)
     */
    public static double computeSecSquared(double x) {
        return DEFAULT_INSTANCE.computeSecSquaredInstance(x, DEFAULT_EPSILON);
    }
    
    /**
     * Вычисляет sec^2(x) с заданной точностью (статический метод обратной совместимости)
     *
     * @param x Значение аргумента в радианах
     * @param epsilon Требуемая точность
     * @return Значение sec^2(x)
     */
    public static double computeSecSquared(double x, double epsilon) {
        return DEFAULT_INSTANCE.computeSecSquaredInstance(x, epsilon);
    }
    
    /**
     * Вычисляет sec(x) (статический метод обратной совместимости)
     *
     * @param x Значение аргумента в радианах
     * @return Значение sec(x)
     */
    public static double computeSec(double x) {
        return DEFAULT_INSTANCE.computeSecInstance(x);
    }
}
