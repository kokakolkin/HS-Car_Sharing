import java.util.Scanner;

class Main {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        String line = scanner.nextLine();

        StringReverser reverser = new StringReverser() {
            @Override
            public String reverse(String str) {
                char[] tmp = new char[str.length()];
                for (int i = 0; i < str.length(); i++) {
                    tmp[i] = str.charAt(str.length()-i-1);
                }
               String out = new String(tmp);

                return out;
            };

        };
        System.out.println(reverser.reverse(line));
    }

    interface StringReverser {

        String reverse(String str);
    }

}
