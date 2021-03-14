package auctions;

import java.io.*;

/**
 * Represents the website.
 * Has main method also.
 */
public class WebPlatform {

    /**
     * The administrator of the website.
     */
    private final Administrator admin;

    /**
     * A file output stream, for directly redirecting program output to a file.
     */
    private FileOutputStream output;

    /**
     * An output stream, for writing output.
     */
    private PrintStream printOut;

    /**
     * A file output stream, for directly redirecting program error output to a
     * file.
     */
    private FileOutputStream errOutput;

    /**
     * An output stream, for writing error output.
     */
    private PrintStream errPrint;

    /**
     * One parameter constructor.
     *
     * @param inputStream The input source.
     */
    private WebPlatform(InputStream inputStream) {
        admin = new Administrator(inputStream);
    }

    /**
     * Main method
     *
     * @param args Command line arguments.
     */
    public static void main(String[] args) {

        //Checks number of main parameters in order to choose if the desired
        // input/output streams should be default or on files
        WebPlatform platform = new WebPlatform(System.in);
        if (args.length == 3) {
            try {
                platform =
                        new WebPlatform(new FileInputStream(new File(args[0])));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            platform.setSystemOutput(args[1], args[2]);
        }

        //Starts a new thread for the admin, in order to handle commands
        // properly
        Thread adminThread = new Thread(platform.admin);
        adminThread.setPriority(Thread.MIN_PRIORITY);
        adminThread.start();
        try {
            adminThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
            Thread.currentThread().interrupt();
        }
        platform.closeOutputStreams();
    }

    /**
     * Private helper method.
     * Initialises the output and print streams.
     *
     * @param out The filename of the file that the standard output streams
     *            will be opened with, represented as a string.
     * @param err The filename of the file that the error streams will be
     *            opened with, represented as a string.
     */
    private void setSystemOutput(String out, String err) {

        //Normal output
        try {
            output = new FileOutputStream(out);
            printOut = new PrintStream(output);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        System.setOut(printOut);

        //Error output
        try {
            errOutput = new FileOutputStream(err);
            errPrint = new PrintStream(errOutput);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        System.setErr(errPrint);
    }

    /**
     * Private helper method.
     * Closes the output and print streams, if they have been opened.
     */
    private void closeOutputStreams() {

        //Close output streams
        if (printOut != null) {
            printOut.close();
        }

        if (output != null) {
            try {
                output.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        //Close error streams
        if (errPrint != null) {
            errPrint.close();
        }

        if (errOutput != null) {
            try {
                errOutput.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
