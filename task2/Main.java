import java.io.*;
import java.util.*;

/**
 * КТ1, задача 2. Тройки с нулевой суммой.
 * Идея: сортируем массив, фиксируем первый элемент a[i], а для пары ищем два указателя l и r.
 * Одинаковые значения пропускаем, чтобы не было повторяющихся троек.
 * Время O(n^2), память O(1) дополнительно (не считая ответа).
 */
public class Main {
    static List<List<Integer>> threeSum(int[] nums) {
        int[] a = nums.clone();
        Arrays.sort(a);
        int n = a.length;
        List<List<Integer>> res = new ArrayList<>();
        for (int i = 0; i + 2 < n; i++) {
            if (a[i] > 0) break;                        // дальше все числа положительные
            if (i > 0 && a[i] == a[i - 1]) continue;    // тот же первый элемент уже был
            int l = i + 1, r = n - 1;
            while (l < r) {
                int s = a[i] + a[l] + a[r];
                if (s < 0) {
                    l++;
                } else if (s > 0) {
                    r--;
                } else {
                    res.add(Arrays.asList(a[i], a[l], a[r]));
                    l++;
                    r--;
                    while (l < r && a[l] == a[l - 1]) l++;
                    while (l < r && a[r] == a[r + 1]) r--;
                }
            }
        }
        return res;
    }

    public static void main(String[] args) throws IOException {
        int[] nums = parseArray(readAll());        // ввод в виде [-1, 0, 1, 2, -1, -4]
        System.out.println(threeSum(nums));        // [[-1, -1, 2], [-1, 0, 1]]
    }

    // ============ разбор ввода в формате из условия: [1, 2, 3] ============
    private static String readAll() throws IOException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        byte[] buf = new byte[1 << 16];
        int len;
        while ((len = System.in.read(buf)) > 0) bos.write(buf, 0, len);
        return new String(bos.toByteArray(), java.nio.charset.StandardCharsets.UTF_8);
    }

    /** Целые числа из первой пары квадратных скобок (если скобок нет — из всего текста). */
    private static int[] parseArray(String s) {
        int l = s.indexOf('['), r = s.indexOf(']');
        String body = (l >= 0 && r > l) ? s.substring(l + 1, r) : s;
        int[] tmp = new int[body.length() / 2 + 1];
        int cnt = 0, i = 0, len = body.length();
        while (i < len) {
            char c = body.charAt(i);
            boolean minus = c == '-' && i + 1 < len && Character.isDigit(body.charAt(i + 1));
            if (minus || Character.isDigit(c)) {
                int j = i + 1;
                while (j < len && Character.isDigit(body.charAt(j))) j++;
                tmp[cnt++] = Integer.parseInt(body.substring(i, j));
                i = j;
            } else {
                i++;
            }
        }
        return Arrays.copyOf(tmp, cnt);
    }
}
