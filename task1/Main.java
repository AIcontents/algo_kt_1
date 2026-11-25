import java.io.*;
import java.util.*;

/**
 * КТ1, задача 1. Самая длинная последовательность номеров.
 * Идея: кладём все числа в множество. Последовательность начинается с x, если в множестве нет x-1.
 * От каждого такого x идём вверх (x+1, x+2, ...) — каждый элемент посещается не более одного раза.
 * Время O(n), память O(n).
 */
public class Main {
    static int longestConsecutive(int[] nums) {
        HashSet<Integer> set = new HashSet<>(nums.length * 2 + 1);
        for (int x : nums) set.add(x);
        int best = 0;
        for (int x : set) {
            if (!set.contains(x - 1)) {            // x — начало последовательности
                int len = 1;
                while (set.contains(x + len)) len++;
                best = Math.max(best, len);
            }
        }
        return best;
    }

    public static void main(String[] args) throws IOException {
        int[] nums = parseArray(readAll());        // ввод в виде [100, 4, 200, 1, 3, 2]
        System.out.println(longestConsecutive(nums));
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
