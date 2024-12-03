import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;

public class Main {
    public static void solve1() throws IOException {
        List<String> lines = Files.readAllLines(Path.of("input.txt"));
        List<List<Integer>> levels = new LinkedList<>();
        for (String line : lines) {
            List<Integer> level = Arrays.stream(line.split(" ")).map((String s) -> Integer.parseInt(s)).toList();
            levels.add(level);
        }
        int res = 0;

        for (List<Integer> level : levels) {
            boolean increasing = true;
            Iterator<Integer> it = level.iterator();
            int last = it.next();
            while (it.hasNext()) {
                int tmp = it.next();
                if (tmp <= last || tmp > last + 3) {
                    increasing = false;
                    break;
                }
                last = tmp;
            }

            boolean decreasing = true;
            it = level.iterator();
            last = it.next();
            while (it.hasNext()) {
                int tmp = it.next();
                if (tmp >= last || tmp < last - 3) {
                    decreasing = false;
                    break;
                }
                last = tmp;
            }

            if (increasing || decreasing) {
                res++;
            }
        }
        System.out.println(res);
    }

    public static List<Integer> listExcept(List<Integer> lst, int skip) {
        List<Integer> res = new LinkedList<>();
        Iterator<Integer> it = lst.iterator();
        for (int i = 0; i < lst.size(); i++) {
            int x = it.next();
            if (i != skip) {
                res.add(x);
            }
        }
        return res;
    }

    public static void solve2() throws IOException {
        List<String> lines = Files.readAllLines(Path.of("input.txt"));
        List<List<Integer>> levels = new LinkedList<>();
        for (String line : lines) {
            List<Integer> level = Arrays.stream(line.split(" ")).map((String s) -> Integer.parseInt(s)).toList();
            levels.add(level);
        }
        int res = 0;

        for (List<Integer> level : levels) {
            boolean good = false;

            for (int skip = -1; skip < level.size(); skip++) {
                List<Integer> tmpLevel = listExcept(level, skip);

                boolean increasing = true;
                Iterator<Integer> it = tmpLevel.iterator();
                int last = it.next();
                while (it.hasNext()) {
                    int tmp = it.next();
                    if (tmp <= last || tmp > last + 3) {
                        increasing = false;
                        break;
                    }
                    last = tmp;
                }

                boolean decreasing = true;
                it = tmpLevel.iterator();
                last = it.next();
                while (it.hasNext()) {
                    int tmp = it.next();
                    if (tmp >= last || tmp < last - 3) {
                        decreasing = false;
                        break;
                    }
                    last = tmp;
                }

                if (increasing || decreasing) {
                    good = true;
                }
            }

            if (good) {
                res++;
            }
        }

        System.out.println(res);
    }

    public static void main(String[] args) {
        try {
            solve1();
            solve2();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
