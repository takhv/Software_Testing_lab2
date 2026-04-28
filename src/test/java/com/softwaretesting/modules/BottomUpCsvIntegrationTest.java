package com.softwaretesting.modules;

import com.softwaretesting.functions.BaseFunctions;
import com.softwaretesting.functions.CosProvider;
import com.softwaretesting.functions.LogarithmProvider;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayName("Bottom-Up CSV Integration Tests")
class BottomUpCsvIntegrationTest {

    private static final double EPSILON = 1e-6;
    private static final double[] COS_POINTS = {-2.0, -1.0, -0.5, 0.0};
    private static final double[] LN_POINTS = {0.5, 1.0, 2.0, 3.0, 5.0, 10.0};

    @Test
    @DisplayName("Снизу вверх: base -> CSV -> trig/log modules -> function system")
    void testBottomUp(@TempDir Path tempDir) throws IOException {
        Path cosCsv = tempDir.resolve("cos_base.csv");
        Path lnCsv = tempDir.resolve("ln_base.csv");

        // 1) Проверяем базовые функции и сохраняем их эталонные значения в CSV.
        generateCosCsv(cosCsv, COS_POINTS);
        generateLnCsv(lnCsv, LN_POINTS);

        assertTrue(Files.exists(cosCsv), "CSV с cos должен быть создан");
        assertTrue(Files.exists(lnCsv), "CSV с ln должен быть создан");

        // 2) Загружаем значения из CSV и используем их как единственный источник для верхнего уровня.
        Map<Double, Double> cosMap = readTwoColumnCsv(cosCsv);
        Map<Double, Double> lnMap = readTwoColumnCsv(lnCsv);

        CosProvider cosProviderFromCsv = (x, epsilon) -> requireValue(cosMap, x, "cos");
        LogarithmProvider logarithmProviderFromCsv = new LogarithmProvider() {
            @Override
            public double ln(double x, double epsilon) {
                return requireValue(lnMap, x, "ln");
            }

            @Override
            public double log(double x, double base, double epsilon) {
                double numerator = requireValue(lnMap, x, "ln");
                double denominator = requireValue(lnMap, base, "ln(base)");
                return numerator / denominator;
            }
        };

        // 3) Поднимаемся на уровень выше cos: TrigonometricModule, используя только cos из CSV.
        TrigonometricModule trigonometricModule = new TrigonometricModule(cosProviderFromCsv);
        for (double x : COS_POINTS) {
            double cosValue = requireValue(cosMap, x, "cos");
            double expected = 1.0 / (cosValue * cosValue);
            double actual = trigonometricModule.computeSecSquaredInstance(x);
            assertEquals(expected, actual, EPSILON, "sec^2 должен считаться из CSV cos для x=" + x);
        }

        // 4) Поднимаемся на уровень выше ln/log: логарифмические модули, используя только ln из CSV.
        BasicLogarithmModule basicLogarithmModule = new BasicLogarithmModule(logarithmProviderFromCsv);
        LogarithmicExpressionModule logarithmicExpressionModule =
                new LogarithmicExpressionModule(basicLogarithmModule);

        for (double x : LN_POINTS) {
            if (x <= 0) {
                continue;
            }
            if (x == 1.0) {
                // Для x=1 знаменатель выражения равен нулю, ожидаем исключение.
                assertThrows(ArithmeticException.class,
                        () -> logarithmicExpressionModule.computeExpressionInstance(x));
                continue;
            }
            double expectedExpression = computeExpectedExpressionFromLnMap(lnMap, x);
            double actualExpression = logarithmicExpressionModule.computeExpressionInstance(x);
            assertEquals(expectedExpression, actualExpression, EPSILON,
                    "Логарифмическое выражение должно считаться из CSV ln для x=" + x);
        }

        // 5) Верхний уровень: вся система, ветви <=0 и >0 строятся на нижних CSV-данных.
        FunctionSystemModule functionSystem =
                new FunctionSystemModule(trigonometricModule, logarithmicExpressionModule);

        double[] systemPoints = {-2.0, -1.0, -0.5, 0.0, 0.5, 2.0, 5.0};
        for (double x : systemPoints) {
            double actual = functionSystem.computeInstance(x);
            double expected = x <= 0
                    ? 1.0 / Math.pow(requireValue(cosMap, x, "cos"), 2)
                    : computeExpectedExpressionFromLnMap(lnMap, x);

            assertEquals(expected, actual, EPSILON,
                    "FunctionSystem должен использовать значения, поднятые из CSV для x=" + x);
        }
    }

    private static void generateCosCsv(Path path, double[] xValues) throws IOException {
        StringBuilder builder = new StringBuilder("x,cos(x)\n");
        for (double x : xValues) {
            double cosValue = BaseFunctions.cos(x, 1e-10);
            // Базовая проверка нижнего уровня перед передачей значения наверх.
            assertEquals(Math.cos(x), cosValue, 1e-6, "cos(x) должен быть корректен для x=" + x);
            builder.append(x).append(',').append(cosValue).append('\n');
        }
        Files.writeString(path, builder);
    }

    private static void generateLnCsv(Path path, double[] xValues) throws IOException {
        StringBuilder builder = new StringBuilder("x,ln(x)\n");
        for (double x : xValues) {
            double lnValue = BaseFunctions.ln(x, 1e-10);
            // Базовая проверка нижнего уровня перед передачей значения наверх.
            assertEquals(Math.log(x), lnValue, 1e-6, "ln(x) должен быть корректен для x=" + x);
            builder.append(x).append(',').append(lnValue).append('\n');
        }
        Files.writeString(path, builder);
    }

    private static Map<Double, Double> readTwoColumnCsv(Path path) throws IOException {
        List<String> lines = Files.readAllLines(path);
        assertFalse(lines.isEmpty(), "CSV не должен быть пустым");

        Map<Double, Double> data = new HashMap<>();
        for (int i = 1; i < lines.size(); i++) {
            String line = lines.get(i).trim();
            if (line.isEmpty()) {
                continue;
            }
            String[] parts = line.split(",");
            data.put(Double.parseDouble(parts[0].trim()), Double.parseDouble(parts[1].trim()));
        }
        return data;
    }

    private static double computeExpectedExpressionFromLnMap(Map<Double, Double> lnMap, double x) {
        double log2 = requireValue(lnMap, x, "ln") / requireValue(lnMap, 2.0, "ln(2)");
        double log3 = requireValue(lnMap, x, "ln") / requireValue(lnMap, 3.0, "ln(3)");
        double log5 = requireValue(lnMap, x, "ln") / requireValue(lnMap, 5.0, "ln(5)");
        double log10 = requireValue(lnMap, x, "ln") / requireValue(lnMap, 10.0, "ln(10)");
        double ln = requireValue(lnMap, x, "ln");

        double numerator = (log2 * log2) + log3 + log10;
        double denominator = (log5 - log3) - (ln * ln);
        double fraction = numerator / denominator;
        return fraction + (log5 * log5 * log5);
    }

    private static double requireValue(Map<Double, Double> values, double x, String valueType) {
        Double result = values.get(x);
        if (result == null) {
            throw new IllegalArgumentException("Нет значения " + valueType + " в CSV для x=" + x);
        }
        return result;
    }
}