
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class MainActivity {

    static class LabTestInfo {
        double min;
        double max;
        String unit;
        String arabicName;

        public LabTestInfo(double min, double max,
                           String unit, String arabicName) {
            this.min = min;
            this.max = max;
            this.unit = unit;
            this.arabicName = arabicName;
        }
    }

    private static final Map<String, LabTestInfo> database =
            new HashMap<>();

    static {
        database.put("GLUCOSE",
                new LabTestInfo(70, 99, "mg/dL",
                        "السكر الصائم (Glucose)"));

        LabTestInfo hb = new LabTestInfo(12, 16, "g/dL",
                "الهيموغلوبين (Hemoglobin)");
        database.put("HB", hb);
        database.put("HEMOGLOBIN", hb);

        database.put("WBC",
                new LabTestInfo(4000, 11000, "/uL",
                        "كريات الدم البيضاء (WBC)"));

        database.put("CREATININE",
                new LabTestInfo(0.6, 1.2, "mg/dL",
                        "الكرياتينين (Creatinine)"));
    }

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        System.out.println("==================================");
        System.out.println("   نظام قارئ التحاليل الطبية");
        System.out.println("==================================");

        while (true) {

            System.out.println("\nالتحاليل المتاحة:");
            System.out.println("GLUCOSE - HB - WBC - CREATININE");
            System.out.print("أدخل اسم التحليل أو EXIT للخروج: ");

            String testName = scanner.nextLine()
                    .trim().toUpperCase();

            if (testName.equals("EXIT")) {
                System.out.println("شكراً لاستخدام البرنامج!");
                break;
            }

            LabTestInfo info = database.get(testName);

            if (info == null) {
                System.out.println("التحليل غير موجود.");
                continue;
            }

            System.out.print("أدخل نتيجة التحليل: ");
            String input = scanner.nextLine().trim();

            double value;

            try {
                value = Double.parseDouble(input);
            } catch (NumberFormatException e) {
                System.out.println("خطأ: أدخل رقماً صحيحاً أو عشرياً.");
                continue;
            }

            if (Double.isNaN(value)
                    || Double.isInfinite(value)
                    || value < 0) {
                System.out.println("خطأ: أدخل قيمة موجبة أو صفراً.");
                continue;
            }

            analyzeAndPrintResult(info, value);
        }

        scanner.close();
    }

    public static void analyzeAndPrintResult(
            LabTestInfo info, double value) {

        System.out.println("\n======= تقرير التحليل =======");
        System.out.println("اسم الفحص: " + info.arabicName);
        System.out.println("النتيجة: " + value + " " + info.unit);
        System.out.println("المدى المرجعي: "
                + info.min + " - " + info.max
                + " " + info.unit);

        if (value < info.min) {
            System.out.println("التصنيف: أقل من المدى المرجعي");
        } else if (value > info.max) {
            System.out.println("التصنيف: أعلى من المدى المرجعي");
        } else {
            System.out.println("التصنيف: ضمن المدى المرجعي");
        }

        System.out.println(
                "ملاحظة: تُفسر النتيجة حسب مرجع المختبر "
                + "والحالة الصحية بواسطة مختص.");

        System.out.println("==============================");
    }
}
