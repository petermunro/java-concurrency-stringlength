package stringlength;

import java.util.concurrent.Callable;

public class StringLengthCallable implements Callable<Integer> {
    private String word;

    public StringLengthCallable(String word) {
        this.word = word;
    }

    @Override
    public Integer call() throws Exception {
        return word.length();
    }
}
