import java.util.Scanner;
public class Main {
    public Main(){

    }
    public static void main(String[] args) {
//        Scanner scanner = new Scanner(System.in);
//        int a = scanner.nextInt();
//        int b=scanner.nextInt();
        int a = Integer.parseInt(args[0]);
        int b = Integer.parseInt(args[1]);
        System.out.println("结果:"+(a+b));
    }
}
