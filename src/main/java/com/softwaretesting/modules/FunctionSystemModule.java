package com.softwaretesting.modules;


public class FunctionSystemModule {
    private static final FunctionSystemModule DEFAULT_INSTANCE = new FunctionSystemModule();
    private final TrigonometricModule trigonometricModule;
    private final LogarithmicExpressionModule logarithmicExpressionModule;

    public FunctionSystemModule(TrigonometricModule trigonometricModule,
                                LogarithmicExpressionModule logarithmicExpressionModule) {
        this.trigonometricModule = trigonometricModule;
        this.logarithmicExpressionModule = logarithmicExpressionModule;
    }

    public FunctionSystemModule() {
        this(new TrigonometricModule(), new LogarithmicExpressionModule());
    }
    
    /**
     * Вычисляет значение функции в зависимости от x
     *
     * @param x Значение аргумента
     * @return Результат вычисления функции
     * @throws IllegalArgumentException если входные данные некорректны
     * @throws ArithmeticException если возникла арифметическая ошибка
     */
    public static double compute(double x) {
        return DEFAULT_INSTANCE.computeInstance(x);
    }

    public double computeInstance(double x) {
        if (x <= 0) {
            return trigonometricModule.computeSecSquaredInstance(x);
        } else {
            return logarithmicExpressionModule.computeExpressionInstance(x);
        }
    }
    
    /**
     * Возвращает информацию о модуле, в котором выполнено вычисление
     *
     * @param x Значение аргумента
     * @return Название используемого модуля
     */
    public static String getActiveModule(double x) {
        return DEFAULT_INSTANCE.getActiveModuleInstance(x);
    }

    public String getActiveModuleInstance(double x) {
        if (x <= 0) {
            return "TrigModule";
        } else {
            return "LogModule";
        }
    }
}
