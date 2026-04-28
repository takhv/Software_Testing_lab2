package com.softwaretesting.modules;

import com.softwaretesting.functions.LogarithmProvider;
import com.softwaretesting.functions.RealLogarithmProvider;


public class BasicLogarithmModule {
    
    private static final double DEFAULT_EPSILON = 1e-10;
    private static final BasicLogarithmModule DEFAULT_INSTANCE = new BasicLogarithmModule();
    private final LogarithmProvider logarithmProvider;

    public BasicLogarithmModule(LogarithmProvider logarithmProvider) {
        this.logarithmProvider = logarithmProvider;
    }

    public BasicLogarithmModule() {
        this(new RealLogarithmProvider());
    }
    
    /**
     * Вычисляет log_2(x) = ln(x) / ln(2)
     *
     * @param x Значение аргумента (должно быть > 0)
     * @return Значение log_2(x)
     * @throws IllegalArgumentException если x <= 0
     */
    public static double log2(double x) {
        return DEFAULT_INSTANCE.computeLog2(x, DEFAULT_EPSILON);
    }
    
    /**
     * Вычисляет log_2(x) с заданной точностью
     *
     * @param x Значение аргумента (должно быть > 0)
     * @param epsilon Требуемая точность
     * @return Значение log_2(x)
     * @throws IllegalArgumentException если x <= 0
     */
    public static double log2(double x, double epsilon) {
        return DEFAULT_INSTANCE.computeLog2(x, epsilon);
    }

    public double computeLog2(double x) {
        return computeLog2(x, DEFAULT_EPSILON);
    }

    public double computeLog2(double x, double epsilon) {
        return logarithmProvider.log(x, 2, epsilon);
    }
    
    /**
     * Вычисляет log_3(x) = ln(x) / ln(3)
     *
     * @param x Значение аргумента (должно быть > 0)
     * @return Значение log_3(x)
     * @throws IllegalArgumentException если x <= 0
     */
    public static double log3(double x) {
        return DEFAULT_INSTANCE.computeLog3(x, DEFAULT_EPSILON);
    }
    
    /**
     * Вычисляет log_3(x) с заданной точностью
     *
     * @param x Значение аргумента (должно быть > 0)
     * @param epsilon Требуемая точность
     * @return Значение log_3(x)
     * @throws IllegalArgumentException если x <= 0
     */
    public static double log3(double x, double epsilon) {
        return DEFAULT_INSTANCE.computeLog3(x, epsilon);
    }

    public double computeLog3(double x) {
        return computeLog3(x, DEFAULT_EPSILON);
    }

    public double computeLog3(double x, double epsilon) {
        return logarithmProvider.log(x, 3, epsilon);
    }
    
    /**
     * Вычисляет log_5(x) = ln(x) / ln(5)
     *
     * @param x Значение аргумента (должно быть > 0)
     * @return Значение log_5(x)
     * @throws IllegalArgumentException если x <= 0
     */
    public static double log5(double x) {
        return DEFAULT_INSTANCE.computeLog5(x, DEFAULT_EPSILON);
    }
    
    /**
     * Вычисляет log_5(x) с заданной точностью
     *
     * @param x Значение аргумента (должно быть > 0)
     * @param epsilon Требуемая точность
     * @return Значение log_5(x)
     * @throws IllegalArgumentException если x <= 0
     */
    public static double log5(double x, double epsilon) {
        return DEFAULT_INSTANCE.computeLog5(x, epsilon);
    }

    public double computeLog5(double x) {
        return computeLog5(x, DEFAULT_EPSILON);
    }

    public double computeLog5(double x, double epsilon) {
        return logarithmProvider.log(x, 5, epsilon);
    }
    
    /**
     * Вычисляет log_10(x) = ln(x) / ln(10)
     *
     * @param x Значение аргумента (должно быть > 0)
     * @return Значение log_10(x)
     * @throws IllegalArgumentException если x <= 0
     */
    public static double log10(double x) {
        return DEFAULT_INSTANCE.computeLog10(x, DEFAULT_EPSILON);
    }
    
    /**
     * Вычисляет log_10(x) с заданной точностью
     *
     * @param x Значение аргумента (должно быть > 0)
     * @param epsilon Требуемая точность
     * @return Значение log_10(x)
     * @throws IllegalArgumentException если x <= 0
     */
    public static double log10(double x, double epsilon) {
        return DEFAULT_INSTANCE.computeLog10(x, epsilon);
    }

    public double computeLog10(double x) {
        return computeLog10(x, DEFAULT_EPSILON);
    }

    public double computeLog10(double x, double epsilon) {
        return logarithmProvider.log(x, 10, epsilon);
    }
    
    /**
     * Вычисляет натуральный логарифм ln(x)
     *
     * @param x Значение аргумента (должно быть > 0)
     * @return Значение ln(x)
     * @throws IllegalArgumentException если x <= 0
     */
    public static double ln(double x) {
        return DEFAULT_INSTANCE.computeLn(x, DEFAULT_EPSILON);
    }
    
    /**
     * Вычисляет натуральный логарифм с заданной точностью
     *
     * @param x Значение аргумента (должно быть > 0)
     * @param epsilon Требуемая точность
     * @return Значение ln(x)
     * @throws IllegalArgumentException если x <= 0
     */
    public static double ln(double x, double epsilon) {
        return DEFAULT_INSTANCE.computeLn(x, epsilon);
    }

    public double computeLn(double x) {
        return computeLn(x, DEFAULT_EPSILON);
    }

    public double computeLn(double x, double epsilon) {
        return logarithmProvider.ln(x, epsilon);
    }
}
