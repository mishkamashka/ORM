package ru.ifmo.se;

import org.junit.Test;

public class TesterTest {
    @Test
    public void test(){
        Tester tester = new Tester("ru.ifmo.se");
        tester.run();
    }
}
