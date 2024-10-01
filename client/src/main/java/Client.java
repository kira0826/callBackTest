import com.zeroc.Ice.ObjectAdapter;
import com.zeroc.Ice.ObjectPrx;
import com.zeroc.Ice.Util;

public class Client {
    public static void main(String[] args) {
        try (com.zeroc.Ice.Communicator communicator = com.zeroc.Ice.Util.initialize(args, "client.cfg")) {

            Demo.PrinterPrx chatManagerPrx = Demo.PrinterPrx
                    .checkedCast(communicator.propertyToProxy("Printer.Proxy"));

            try {

                ObjectAdapter adapter = communicator.createObjectAdapter("Callback");
                Demo.Callback callback = new CallbackI();

                ObjectPrx prx = adapter.add(callback, Util.stringToIdentity("callback"));
                Demo.CallbackPrx callbackPrx = Demo.CallbackPrx.checkedCast(prx);
                adapter.activate();

                System.out.println("Waiting for response...");
                long start = System.currentTimeMillis();
                chatManagerPrx.printString("Hello World");
                chatManagerPrx.fact(20, callbackPrx);
                // System.out.println("Factorial of 10 is: " );
                System.out.println("Time taken: " + (System.currentTimeMillis() - start) + "ms");

                communicator.waitForShutdown();

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }
}
