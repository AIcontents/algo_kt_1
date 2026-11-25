import java.io.*;
import java.util.*;

/**
 * КТ1, задача 3. Самый длинный участок с ограниченным числом типов (не более двух разных).
 * Идея: скользящее окно [l, r]. Расширяем r, а пока в окне больше двух типов — сдвигаем l.
 * Время O(n), память O(max(items[i])).
 */
public class Main {
    static int longestTwoTypes(int[] items) {
        int[] cnt = new int[1_000_001];
        int distinct = 0, best = 0, l = 0;
        for (int r = 0; r < items.length; r++) {
            if (cnt[items[r]]++ == 0) distinct++;
            while (distinct > 2) {
                if (--cnt[items[l]] == 0) distinct--;
                l++;
            }
            best = Math.max(best, r - l + 1);
        }
        return best;
    }

    public static void main(String[] args) throws IOException {
        int[] items = parseArray(readAll());       // ввод в виде [1, 2, 3, 2, 2]
        System.out.println(longestTwoTypes(items));
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
