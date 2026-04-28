package com.softwaretesting.functions;

/**
 * Реальная реализация SecantProvider.
 * Использует CosineProvider для вычисления косинуса,
 * а затем вычисляет секанс как 1/cos(x).
 */
public class RealSecantProvider implements SecantProvider {
    
    private final CosProvider cosineProvider;
    
    public RealSecantProvider(CosProvider cosineProvider) {
        this.cosineProvider = cosineProvider;
    }
    
    @Override
    public double compute(double x, double epsilon) {
        double cosValue = cosineProvider.compute(x, epsilon);
        
        if (Math.abs(cosValue) < epsilon) {
            throw new ArithmeticException(
                String.format("cos(%f) is too close to zero: %f", x, cosValue)
            );
        }
        
        return 1.0 / cosValue;
    }
}
