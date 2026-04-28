package com.softwaretesting;

import com.softwaretesting.export.CSVExporter;
import com.softwaretesting.functions.BaseFunctions;
import com.softwaretesting.modules.BasicLogarithmModule;
import com.softwaretesting.modules.FunctionSystemModule;
import com.softwaretesting.modules.LogarithmicExpressionModule;
import com.softwaretesting.modules.TrigonometricModule;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Locale;


public class Main {
    private static final FunctionSystemModule FUNCTION_SYSTEM = new FunctionSystemModule();
    private static final CSVExporter CSV_EXPORTER = new CSVExporter(FUNCTION_SYSTEM);
    private static final TrigonometricModule TRIG_MODULE = new TrigonometricModule();
    private static final BasicLogarithmModule BASIC_LOG_MODULE = new BasicLogarithmModule();
    private static final LogarithmicExpressionModule LOG_EXPR_MODULE = new LogarithmicExpressionModule();
    private static final double EPSILON = 1e-10;
    
    public static void main(String[] args) throws Exception {
        System.out.println("=== integration tests ===\n");
        
        demonstrateCalculations();
        exportResults();
    }
    
    private static void demonstrateCalculations() {
        System.out.println("example calculations:");
        
        double[] testPoints = {-2.0, -1.0, -0.5, 0.0, 0.5, 1.0, 2.0, 5.0};
        
        for (double x : testPoints) {
            try {
                double result = FUNCTION_SYSTEM.computeInstance(x);
                String module = FUNCTION_SYSTEM.getActiveModuleInstance(x);
                
                System.out.printf("   f(%.2f) = %.6f  [%s]\n", x, result, module);
            } catch (Exception e) {
                System.out.printf("   f(%.2f) = ERROR: %s\n", x, e.getMessage());
            }
        }
        
        System.out.println("\n");
    }
    
    private static void exportResults() throws Exception {
        System.out.println("export results to CSV:");

        // Level 1: базовые функции
        exportBaseCosCsv("level1_cos.csv", -3.0, 0.0, 0.1);
        exportBaseLnCsv("level1_ln.csv", 0.1, 10.0, 0.1);

        // Level 2: модули над базовыми функциями
        exportTrigonometricLevelCsv("level2_sec2.csv", -3.0, 0.0, 0.1);
        exportBasicLogarithmLevelCsv("level2_logs.csv", 0.1, 10.0, 0.1);

        // Level 3: логарифмическое выражение
        exportLogarithmicExpressionLevelCsv("level3_log_expression.csv", 0.1, 10.0, 0.1);

        // Level 4: итоговая система
        String outputFile = "level4_function_system.csv";
        System.out.println("exporting function system to " + outputFile);
        CSV_EXPORTER.exportFunctionResultsInstance(outputFile, -2.0, 5.0, 0.1, ",");

        CSV_EXPORTER.exportFunctionResultsInstance("function_output.csv", -2.0, 5.0, 0.1, ",");
    }

    private static void exportBaseCosCsv(String filePath, double startX, double endX, double step) throws IOException {
        System.out.println("exporting level 1 base cos to " + filePath);
        try (FileWriter writer = new FileWriter(filePath)) {
            writer.append("X,cos(X)\n");
            for (double x = startX; x <= endX; x += step) {
                double cos = BaseFunctions.cos(x, EPSILON);
                writer.append(format(x)).append(",").append(format(cos)).append("\n");
            }
        }
    }

    private static void exportBaseLnCsv(String filePath, double startX, double endX, double step) throws IOException {
        System.out.println("exporting level 1 base ln to " + filePath);
        try (FileWriter writer = new FileWriter(filePath)) {
            writer.append("X,ln(X)\n");
            for (double x = startX; x <= endX; x += step) {
                double ln = BaseFunctions.ln(x, EPSILON);
                writer.append(format(x)).append(",").append(format(ln)).append("\n");
            }
        }
    }

    private static void exportTrigonometricLevelCsv(String filePath, double startX, double endX, double step) throws IOException {
        System.out.println("exporting level 2 trigonometric module to " + filePath);
        try (FileWriter writer = new FileWriter(filePath)) {
            writer.append("X,sec^2(X)\n");
            for (double x = startX; x <= endX; x += step) {
                try {
                    double secSquared = TRIG_MODULE.computeSecSquaredInstance(x);
                    writer.append(format(x)).append(",").append(format(secSquared)).append("\n");
                } catch (Exception e) {
                    writer.append(format(x)).append(",ERROR: ").append(e.getMessage()).append("\n");
                }
            }
        }
    }

    private static void exportBasicLogarithmLevelCsv(String filePath, double startX, double endX, double step) throws IOException {
        System.out.println("exporting level 2 basic logarithm module to " + filePath);
        try (FileWriter writer = new FileWriter(filePath)) {
            writer.append("X,ln(X),log2(X),log3(X),log5(X),log10(X)\n");
            for (double x = startX; x <= endX; x += step) {
                double ln = BASIC_LOG_MODULE.computeLn(x);
                double log2 = BASIC_LOG_MODULE.computeLog2(x);
                double log3 = BASIC_LOG_MODULE.computeLog3(x);
                double log5 = BASIC_LOG_MODULE.computeLog5(x);
                double log10 = BASIC_LOG_MODULE.computeLog10(x);

                writer.append(format(x)).append(",")
                        .append(format(ln)).append(",")
                        .append(format(log2)).append(",")
                        .append(format(log3)).append(",")
                        .append(format(log5)).append(",")
                        .append(format(log10)).append("\n");
            }
        }
    }

    private static void exportLogarithmicExpressionLevelCsv(String filePath, double startX, double endX, double step) throws IOException {
        System.out.println("exporting level 3 logarithmic expression module to " + filePath);
        try (FileWriter writer = new FileWriter(filePath)) {
            writer.append("X,numerator,denominator,fraction,log5^3,expression\n");
            for (double x = startX; x <= endX; x += step) {
                try {
                    double numerator = LOG_EXPR_MODULE.computeNumeratorInstance(x);
                    double denominator = LOG_EXPR_MODULE.computeDenominatorInstance(x);
                    double fraction = LOG_EXPR_MODULE.computeFractionInstance(x);
                    double log5Cubed = LOG_EXPR_MODULE.computeLog5CubedInstance(x);
                    double expression = LOG_EXPR_MODULE.computeExpressionInstance(x);

                    writer.append(format(x)).append(",")
                            .append(format(numerator)).append(",")
                            .append(format(denominator)).append(",")
                            .append(format(fraction)).append(",")
                            .append(format(log5Cubed)).append(",")
                            .append(format(expression)).append("\n");
                } catch (Exception e) {
                    writer.append(format(x)).append(",ERROR: ").append(e.getMessage()).append("\n");
                }
            }
        }
    }

    private static String format(double value) {
        return String.format(Locale.US, "%.6f", value);
    }
}
