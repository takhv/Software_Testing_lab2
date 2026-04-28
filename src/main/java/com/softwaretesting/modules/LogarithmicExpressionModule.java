package com.softwaretesting.modules;


public class LogarithmicExpressionModule {
    private static final LogarithmicExpressionModule DEFAULT_INSTANCE = new LogarithmicExpressionModule();
    private final BasicLogarithmModule logarithmModule;

    public LogarithmicExpressionModule(BasicLogarithmModule logarithmModule) {
        this.logarithmModule = logarithmModule;
    }

    public LogarithmicExpressionModule() {
        this(new BasicLogarithmModule());
    }

    /**
     * Вычисляет числитель: ((log_2(x)^2 + log_3(x)) + log_10(x))
     *
     * @param x Значение аргумента (должно быть > 0)
     * @return Значение числителя
     */
    public static double computeNumerator(double x) {
        return DEFAULT_INSTANCE.computeNumeratorInstance(x);
    }

    public double computeNumeratorInstance(double x) {
        double log2x = logarithmModule.computeLog2(x);
        double log3x = logarithmModule.computeLog3(x);
        double log10x = logarithmModule.computeLog10(x);
        
        return ((log2x * log2x) + log3x) + log10x;
    }
    
    /**
     * Вычисляет знаменатель: ((log_5(x) - log_3(x)) - (ln(x)^2))
     *
     * @param x Значение аргумента (должно быть > 0)
     * @return Значение знаменателя
     * @throws ArithmeticException если значение близко к нулю
     */
    public static double computeDenominator(double x) {
        return DEFAULT_INSTANCE.computeDenominatorInstance(x);
    }

    public double computeDenominatorInstance(double x) {
        double log5x = logarithmModule.computeLog5(x);
        double log3x = logarithmModule.computeLog3(x);
        double lnx = logarithmModule.computeLn(x);
        
        return (log5x - log3x) - (lnx * lnx);
    }
    
    /**
     * Вычисляет значение дроби
     *
     * @param x Значение аргумента (должно быть > 0)
     * @return Значение дроби
     * @throws ArithmeticException если знаменатель близко к нулю
     */
    public static double computeFraction(double x) {
        return DEFAULT_INSTANCE.computeFractionInstance(x);
    }

    public double computeFractionInstance(double x) {
        double numerator = computeNumeratorInstance(x);
        double denominator = computeDenominatorInstance(x);
        
        if (Math.abs(denominator) < 1e-12) {
            throw new ArithmeticException("Знаменатель близко к нулю: " + denominator);
        }
        
        return numerator / denominator;
    }
    
    /**
     * Вычисляет куб log_5(x)^3
     *
     * @param x Значение аргумента (должно быть > 0)
     * @return Значение log_5(x)^3
     */
    public static double computeLog5Cubed(double x) {
        return DEFAULT_INSTANCE.computeLog5CubedInstance(x);
    }

    public double computeLog5CubedInstance(double x) {
        double log5x = logarithmModule.computeLog5(x);
        return log5x * log5x * log5x;
    }
    
    /**
     * Вычисляет полное логарифмическое выражение:
     * (((log_2(x)^2 + log_3(x)) + log_10(x)) / ((log_5(x) - log_3(x)) - (ln(x)^2))) + log_5(x)^3
     *
     * @param x Значение аргумента (должно быть > 0)
     * @return Результат выражения
     * @throws IllegalArgumentException если x <= 0
     * @throws ArithmeticException если знаменатель близко к нулю
     */
    public static double computeExpression(double x) {
        return DEFAULT_INSTANCE.computeExpressionInstance(x);
    }

    public double computeExpressionInstance(double x) {
        if (x <= 0) {
            throw new IllegalArgumentException("Для логарифмического модуля x должно быть > 0");
        }
        
        double fraction = computeFractionInstance(x);
        double log5Cubed = computeLog5CubedInstance(x);
        
        return fraction + log5Cubed;
    }
}
