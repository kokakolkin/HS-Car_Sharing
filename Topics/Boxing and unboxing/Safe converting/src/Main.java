import java.util.Scanner;

public class Main {

    public static int convert(Long val) {
        if (val == null) {
            return 0;
        }
        else  {
            long l = val;
            int i;
            i = 0;
            if (l > Integer.MAX_VALUE) return i = Integer.MAX_VALUE;
            else if (l < Integer.MIN_VALUE) return i = Integer.MIN_VALUE;
            else return i = (int) l;
        }
    }

    /* Do not change code below */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String val = scanner.nextLine();
        Long longVal = "null".equals(val) ? null : Long.parseLong(val);
        System.out.println(convert(longVal));
    }
}