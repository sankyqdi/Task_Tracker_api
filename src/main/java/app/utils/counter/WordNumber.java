package app.utils.counter;

public enum WordNumber {
    ONE("One"),
    TWO("Two"),
    THREE("Three"),
    FOUR("Four"),
    FIVE("Five"),
    SIX("Six"),
    SEVEN("Seven"),
    EIGHT("Eight"),
    NINE("Nine"),
    TEN("Ten"),
    ELEVEN("Eleven"),
    TWELVE("Twelve"),
    THIRTEEN("Thirteen"),
    FOURTEEN("Fourteen"),
    FIFTEEN("Fifteen"),
    SIXTEEN("Sixteen"),
    SEVENTEEN("Seventeen"),
    EIGHTEEN("Eighteen"),
    NINETEEN("Nineteen"),
    TWENTY("Twenty");

    private final String word;

    WordNumber(String word) {
        this.word = word;
    }

    public String getWord() {
        return word;
    }
}
