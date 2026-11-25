import java.io.*;
import java.util.*;

/**
 * КТ1, задача 5. Максимальный объём воды между стенками.
 * Идея: два указателя с краёв. Площадь = расстояние * min(высот). Двигать выгодно только меньшую стенку:
 * при сдвиге большей стенки расстояние уменьшается, а высота не может вырасти выше меньшей.
 * Время O(n), память O(1). Площадь до 2·10^14, поэтому long.
 */
public class Main {
    static long maxArea(int[] height) {
        int l = 0, r = height.length - 1;
        long best = 0;
        while (l < r) {
            long area = (long) (r - l) * Math.min(height[l], height[r]);
            best = Math.max(best, area);
            if (height[l] < height[r]) l++; else r--;
        }
        return best;
    }

    public static void main(String[] args) throws IOException {
        int[] height = parseArray(readAll());      // ввод в виде [1, 8, 6, 2, 5, 4, 8, 3, 7]
        System.out.println(maxArea(height));
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
