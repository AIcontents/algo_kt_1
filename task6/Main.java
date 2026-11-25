import java.io.*;
import java.util.*;

/**
 * КТ1, задача 6. Минимальная подстрока-покрытие.
 * Идея: скользящее окно. need[c] — сколько символов c ещё не хватает окну (может быть < 0, если лишние).
 * Когда окно покрывает target (missing == 0) — пробуем сузить слева и запоминаем кратчайшее.
 * Время O(|source| + |target|), память O(алфавит).
 */
public class Main {
    static String minWindow(String source, String target) {
        int[] need = new int[128];
        for (int i = 0; i < target.length(); i++) need[target.charAt(i)]++;
        int missing = target.length();
        int bestL = 0, bestLen = Integer.MAX_VALUE, l = 0;
        for (int r = 0; r < source.length(); r++) {
            if (need[source.charAt(r)]-- > 0) missing--;
            while (missing == 0) {
                if (r - l + 1 < bestLen) { bestLen = r - l + 1; bestL = l; }
                if (++need[source.charAt(l++)] > 0) missing++;
            }
        }
        return bestLen == Integer.MAX_VALUE ? "" : source.substring(bestL, bestL + bestLen);
    }

    public static void main(String[] args) throws IOException {
        String[] st = parseTwoStrings(readAll());  // ввод: source = "ADOBECODEBANC"  и  target = "ABC"
        System.out.println("\"" + minWindow(st[0], st[1]) + "\"");
    }

    /** Две строки: либо в кавычках, либо по одной в строке (допускается префикс вида «source = »). */
    private static String[] parseTwoStrings(String text) {
        List<String> res = new ArrayList<>();
        int i = 0;
        while (true) {
            int a = text.indexOf('"', i);
            if (a < 0) break;
            int b = text.indexOf('"', a + 1);
            if (b < 0) break;
            res.add(text.substring(a + 1, b));
            i = b + 1;
        }
        if (res.size() < 2) {
            res.clear();
            for (String line : text.split("\\R")) {
                line = line.trim();
                if (line.isEmpty()) continue;
                int eq = line.indexOf('=');
                if (eq >= 0) line = line.substring(eq + 1).trim();
                res.add(line);
            }
        }
        return new String[]{res.get(0), res.get(1)};
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
