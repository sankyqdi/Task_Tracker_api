package app.utils.counter;

public class Counter {

    private int counterObject;

    public Counter() {

        this.counterObject = 0;

    }

    public String generateId(String startText) {

        if (checkCounterObject()) {

            String finalText = startText + getWordNumber();

            counterObject++;

            return finalText;

        }

        return startText + "Infinity";


    }

    private String getWordNumber() {

        return WordNumber.values()[counterObject].getWord();

    }

    private boolean checkCounterObject() {

        return counterObject < WordNumber.values().length;

    }
}
