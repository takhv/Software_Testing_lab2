package com.softwaretesting.functions;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;


@DisplayName("Base Functions Tests")
class BaseFunctionsTest {
    
    private static final double EPSILON = 1e-6;
    
    @Nested
    @DisplayName("cos(x) Tests")
    class CosTests {
        
        @Test
        @DisplayName("cos(0) should equal 1")
        void testCosZero() {
            double result = BaseFunctions.cos(0);
            assertEquals(1.0, result, EPSILON);
        }
        
        @Test
        @DisplayName("cos(pi) should equal -1")
        void testCosPi() {
            double result = BaseFunctions.cos(Math.PI);
            assertEquals(-1.0, result, EPSILON);
        }
        
        @Test
        @DisplayName("cos(pi/2) should equal 0")
        void testCosPiHalf() {
            double result = BaseFunctions.cos(Math.PI / 2);
            assertEquals(0.0, result, EPSILON);
        }
        
        @ParameterizedTest
        @ValueSource(doubles = {0.0, Math.PI / 4, Math.PI / 2, Math.PI})
        @DisplayName("cos should return value in [-1, 1]")
        void cosRangeTest(double x) {
            double result = BaseFunctions.cos(x);
            assertTrue(result >= -1.0 && result <= 1.0);
        }
        
        @Test
        @DisplayName("cos is even function: cos(-x) = cos(x)")
        void testCosEvenFunction() {
            double x = 1.5;
            double cosX = BaseFunctions.cos(x);
            double cosNegX = BaseFunctions.cos(-x);
            assertEquals(cosX, cosNegX, EPSILON);
        }
        
        @Test
        @DisplayName("cos is periodic with period 2pi")
        void testCosPeriodicity() {
            double x = 1.0;
            double twoPi = 2 * Math.PI;
            double cosX = BaseFunctions.cos(x);
            double cosPlusTwoPi = BaseFunctions.cos(x + twoPi);
            assertEquals(cosX, cosPlusTwoPi, EPSILON);
        }
    }
    
    @Nested
    @DisplayName("sec(x) Tests")
    class SecTests {
        
        @Test
        @DisplayName("sec(0) should equal 1")
        void testSecZero() {
            double result = BaseFunctions.sec(0);
            assertEquals(1.0, result, EPSILON);
        }
        
        @Test
        @DisplayName("sec(x) = 1/cos(x)")
        void testSecDefinition() {
            double x = 0.5;
            double sec = BaseFunctions.sec(x);
            double cosX = BaseFunctions.cos(x);
            assertEquals(1.0 / cosX, sec, EPSILON);
        }
        
        @Test
        @DisplayName("sec near pi/2 should throw or return large value")
        void testSecNearPiHalf() {
            double piHalf = Math.PI / 2;
            try {
                double result = BaseFunctions.sec(piHalf);
                assertTrue(Math.abs(result) > 1000, "sec near pi/2 should be very large");
            } catch (ArithmeticException e) {
                assertTrue(true);
            }
        }
        
        @Test
        @DisplayName("sec should work for valid negative values")
        void testSecNegative() {
            double x = -1.0;
            double result = BaseFunctions.sec(x);
            assertNotNull(result);
            assertTrue(!Double.isNaN(result) && !Double.isInfinite(result));
        }
    }
    
    @Nested
    @DisplayName("ln(x) Tests")
    class LnTests {
        
        @Test
        @DisplayName("ln(1) should equal 0")
        void testLnOne() {
            double result = BaseFunctions.ln(1.0);
            assertEquals(0.0, result, EPSILON);
        }
        
        @Test
        @DisplayName("ln(e) should equal 1")
        void testLnE() {
            double result = BaseFunctions.ln(Math.E);
            assertEquals(1.0, result, EPSILON);
        }
        
        @Test
        @DisplayName("ln(0) should throw IllegalArgumentException")
        void testLnZeroThrows() {
            assertThrows(IllegalArgumentException.class, () -> BaseFunctions.ln(0));
        }
        
        @Test
        @DisplayName("ln(negative) should throw IllegalArgumentException")
        void testLnNegativeThrows() {
            assertThrows(IllegalArgumentException.class, () -> BaseFunctions.ln(-1.0));
        }
        
        @ParameterizedTest
        @CsvSource({
                "0.5, -0.69315",
                "2.0, 0.69315",
                "10.0, 2.30259"
        })
        @DisplayName("ln(x) known values")
        void testLnKnownValues(double x, double expected) {
            double result = BaseFunctions.ln(x);
            assertEquals(expected, result, 0.001);
        }
        
        @Test
        @DisplayName("ln(x*y) = ln(x) + ln(y)")
        void testLnProduct() {
            double x = 2.0;
            double y = 3.0;
            double lnXY = BaseFunctions.ln(x * y);
            double lnXPlusLnY = BaseFunctions.ln(x) + BaseFunctions.ln(y);
            assertEquals(lnXPlusLnY, lnXY, EPSILON);
        }
    }
    
    @Nested
    @DisplayName("log(x, base) Tests")
    class LogTests {
        
        @Test
        @DisplayName("log_2(1) should equal 0")
        void testLog2One() {
            double result = BaseFunctions.log(1.0, 2);
            assertEquals(0.0, result, EPSILON);
        }
        
        @Test
        @DisplayName("log_2(2) should equal 1")
        void testLog2Two() {
            double result = BaseFunctions.log(2.0, 2);
            assertEquals(1.0, result, EPSILON);
        }
        
        @Test
        @DisplayName("log(x, base=1) should throw IllegalArgumentException")
        void testLogBase1Throws() {
            assertThrows(IllegalArgumentException.class, () -> BaseFunctions.log(5, 1));
        }
        
        @Test
        @DisplayName("log(x, base<=0) should throw IllegalArgumentException")
        void testLogNegativeBaseThrows() {
            assertThrows(IllegalArgumentException.class, () -> BaseFunctions.log(5, -2));
        }
        
        @ParameterizedTest
        @CsvSource({
                "0.1, 2, -3.3219",
                "4.0, 2, 2.0",
                "100.0, 10, 2.0"
        })
        @DisplayName("log(x, base) known values")
        void testLogKnownValues(double x, double base, double expected) {
            double result = BaseFunctions.log(x, base);
            assertEquals(expected, result, 0.001);
        }
    }
}
