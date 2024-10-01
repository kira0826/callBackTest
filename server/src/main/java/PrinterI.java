import java.math.BigInteger;

public class PrinterI implements Demo.Printer {

    public void printString(String msg, com.zeroc.Ice.Current current) {
        System.out.println(msg);
    }

    public void fact(long n, Demo.CallbackPrx callback, com.zeroc.Ice.Current current) {
        Thread thread = new Thread(() -> {
            BigInteger fact = BigInteger.ONE;
            for (long i = 1; i <= n; i++) {
                fact = fact.multiply(BigInteger.valueOf(i));
            }
            try {
                Thread.sleep(3000);
            } catch (Exception e) {
                // TODO: handle exception
            }
            String response = "Factorial of " + n + " is: " + fact;
            callback.reportResponse(response);
        });
        thread.start();
    }
    
}
