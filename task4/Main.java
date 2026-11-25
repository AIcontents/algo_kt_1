import java.io.*;
import java.util.*;

/**
 * КТ1, задача 4. Количество подмассивов с заданной суммой.
 * Идея: префиксные суммы. Подмассив (i, j] имеет сумму k, если P[j] - P[i] = k.
 * Идём слева направо и храним, сколько раз встречалась каждая префиксная сумма: к ответу добавляем count[P - k].
 * Работает с отрицательными числами и нулями. Время O(n), память O(n).
 * Ответ может достигать ~2·10^10, поэтому он хранится в long.
 */
public class Main {
    static long subarraySum(int[] nums, long k) {
        HashMap<Long, Integer> seen = new HashMap<>(nums.length * 2 + 1);
        seen.put(0L, 1);
        long prefix = 0, ans = 0;
        for (int x : nums) {
            prefix += x;
            Integer c = seen.get(prefix - k);
            if (c != null) ans += c;
            seen.merge(prefix, 1, Integer::sum);
        }
        return ans;
    }

    public static void main(String[] args) throws IOException {
        String text = readAll();                   // ввод: nums = [1, 1, 1]  и  k = 2
        int[] nums = parseArray(text);
        long k = parseAfterBracket(text);
        System.out.println(subarraySum(nums, k));
    }

    /** Первое целое число после закрывающей скобки массива (значение k). */
    private static long parseAfterBracket(String s) {
        int i = s.indexOf(']') + 1;
        while (i < s.length() && !(Character.isDigit(s.charAt(i))
                || (s.charAt(i) == '-' && i + 1 < s.length() && Character.isDigit(s.charAt(i + 1))))) i++;
        int j = i + 1;
        while (j < s.length() && Character.isDigit(s.charAt(j))) j++;
        return Long.parseLong(s.substring(i, j));
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
