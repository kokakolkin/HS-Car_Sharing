import java.util.Scanner;
import java.util.Arrays;

class Main {
    public static void main(String[] args) {
 Scanner scanner = new Scanner(System.in);
    String[] input = new String[1];
    String[] output = new String[1];

    int i = 0;
    int tmp = 0;
    
    do {
  //      System.out.println("i:" +i);

    input[i] = scanner.nextLine();
    
    try {
      tmp =  10*Integer.parseInt(input[i]);
      output[i] = Integer.toString(tmp);
      output = Arrays.copyOf(output,i+1);
    }
    catch (Exception e) {
      output[i] = "Invalid user input: " +input[i];
       output = Arrays.copyOf(output,i+1);
    }

    input = Arrays.copyOf(input,input.length+1);
//    System.out.println(input.length);
    output = Arrays.copyOf(output,output.length+1);
    i++;

    }
    while (!input[i-1].equals("0"));

  for (int k = 0; k < i-1; k++) {
    System.out.println(output[k]);
  }
    }
}
