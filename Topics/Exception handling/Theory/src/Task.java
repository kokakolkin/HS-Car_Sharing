// You can experiment here, it won’t be checked
import java.net.Inet4Address;
import java.util.Scanner;
import java.util.Arrays;

public class Task {
  public static void main(String[] args) {
    Scanner scanner = new Scanner(System.in);
    String[] input = new String[1];
    String[] output = new String[1];

    int i = 0;
    int tmp = 0;
    do {
    input[i] = scanner.nextLine();
    input = Arrays.copyOf(input,i+1);
    i++;
    try {
      tmp =  10*Integer.parseInt(input[i]);
      output[i] = Integer.toString(tmp);
    }
    catch (Exception e) {
      output[i] = "Invalid user input: " +input[i];
    }
    }
    while (!input[i-1].equals("0"));

  for (int k = 0; k < i-1; k++) {
    System.out.println(output[k]);
  }

  }


}
