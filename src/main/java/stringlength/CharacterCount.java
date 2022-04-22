package stringlength;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.concurrent.*;

public class CharacterCount {
    public static void main(String[] args) {
        if (args.length != 1) {
            System.err.println("Provide a filename to count");
            System.exit(-1);
        }
        System.out.println("Working Directory = " + System.getProperty("user.dir"));

        String filename = args[0];
        int count = 0;

        CompletionService<Integer> cs = new ExecutorCompletionService<>(Executors.newFixedThreadPool(5));
        try (BufferedReader textFile = new BufferedReader(new FileReader(filename))) {
            String line;

            while ((line = textFile.readLine()) != null) {
                StringLengthCallable slc = new StringLengthCallable(line);
                cs.submit(slc);
                count++;
            }
        } catch (FileNotFoundException ffe) {
            System.err.println("File not found: " + filename);
        } catch (IOException ioe) {
            System.err.printf("I/O Exception: %s%n", ioe.getMessage());
        }

        int total = 0;
        while (count-- > 0) {
            try {
                total += cs.take().get();
                System.out.printf("Running total: %d%n", total);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }

        System.out.printf("Grand total: %d%n", total);


    }
}
