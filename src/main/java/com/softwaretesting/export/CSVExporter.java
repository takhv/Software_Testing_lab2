package com.softwaretesting.export;

import com.softwaretesting.modules.FunctionSystemModule;
import java.io.FileWriter;
import java.io.IOException;


public class CSVExporter {
    private static final CSVExporter DEFAULT_INSTANCE = new CSVExporter(new FunctionSystemModule());
    private final FunctionSystemModule functionSystemModule;

    public CSVExporter(FunctionSystemModule functionSystemModule) {
        this.functionSystemModule = functionSystemModule;
    }

    public CSVExporter() {
        this(new FunctionSystemModule());
    }
    
    /**
     * Экспортирует результаты вычисления в CSV файл
     *
     * @param filePath Путь к файлу для записи
     * @param startX Начальное значение X
     * @param endX Конечное значение X
     * @param step Шаг наращивания X
     * @param separator Разделитель полей в CSV (например, "," или ";")
     * @throws IOException если возникла ошибка при записи файла
     * @throws IllegalArgumentException если параметры некорректны
     */
    public static void exportFunctionResults(
            String filePath,
            double startX,
            double endX,
            double step,
            String separator) throws IOException {
        DEFAULT_INSTANCE.exportFunctionResultsInstance(filePath, startX, endX, step, separator);
        }

        public void exportFunctionResultsInstance(
            String filePath,
            double startX,
            double endX,
            double step,
            String separator) throws IOException {
        
        if (step <= 0) {
            throw new IllegalArgumentException("Шаг должен быть положительным");
        }
        if (startX > endX) {
            throw new IllegalArgumentException("Начальное значение не может быть больше конечного");
        }
        if (separator == null || separator.isEmpty()) {
            throw new IllegalArgumentException("Разделитель не может быть пустым");
        }
        
        try (FileWriter writer = new FileWriter(filePath)) {
            // Написать заголовок
            writer.append("X").append(separator).append("Result").append(separator).append("Module").append("\n");
            
            // Написать данные
            for (double x = startX; x <= endX; x += step) {
                try {
                    double result = functionSystemModule.computeInstance(x);
                    String module = functionSystemModule.getActiveModuleInstance(x);
                    
                    writer.append(String.format("%.6f", x))
                          .append(separator)
                          .append(String.format("%.6f", result))
                          .append(separator)
                          .append(module)
                          .append("\n");
                } catch (Exception e) {
                    // Записываем ошибку в CSV
                    writer.append(String.format("%.6f", x))
                          .append(separator)
                          .append("ERROR: " + e.getMessage())
                          .append(separator)
                          .append("N/A")
                          .append("\n");
                }
            }
        }
    }
    
    /**
     * Экспортирует результаты конкретного модуля
     *
     * @param filePath Путь к файлу для записи
     * @param startX Начальное значение X
     * @param endX Конечное значение X
     * @param step Шаг наращивания X
     * @param separator Разделитель полей в CSV
     * @param moduleType Тип модуля: "trigonometric" или "logarithmic"
     * @throws IOException если возникла ошибка при записи файла
     */
    public static void exportModuleResults(
            String filePath,
            double startX,
            double endX,
            double step,
            String separator,
            String moduleType) throws IOException {
        DEFAULT_INSTANCE.exportModuleResultsInstance(filePath, startX, endX, step, separator, moduleType);
        }

        public void exportModuleResultsInstance(
            String filePath,
            double startX,
            double endX,
            double step,
            String separator,
            String moduleType) throws IOException {
        
        if (step <= 0) {
            throw new IllegalArgumentException("Шаг должен быть положительным");
        }
        if (startX > endX) {
            throw new IllegalArgumentException("Начальное значение не может быть больше конечного");
        }
        if (separator == null || separator.isEmpty()) {
            throw new IllegalArgumentException("Разделитель не может быть пустым");
        }
        if (moduleType == null || moduleType.isEmpty()) {
            throw new IllegalArgumentException("Тип модуля не может быть пустым");
        }
        
        try (FileWriter writer = new FileWriter(filePath)) {
            writer.append("X").append(separator).append("Result").append("\n");
            
            for (double x = startX; x <= endX; x += step) {
                try {
                    double result;
                    
                    if ("trigonometric".equalsIgnoreCase(moduleType)) {
                        if (x <= 0) {
                            result = functionSystemModule.computeInstance(x);
                            writer.append(String.format("%.6f", x))
                                  .append(separator)
                                  .append(String.format("%.6f", result))
                                  .append("\n");
                        }
                    } else if ("logarithmic".equalsIgnoreCase(moduleType)) {
                        if (x > 0) {
                            result = functionSystemModule.computeInstance(x);
                            writer.append(String.format("%.6f", x))
                                  .append(separator)
                                  .append(String.format("%.6f", result))
                                  .append("\n");
                        }
                    }
                } catch (Exception e) {
                    writer.append(String.format("%.6f", x))
                          .append(separator)
                          .append("ERROR: " + e.getMessage())
                          .append("\n");
                }
            }
        }
    }
}
