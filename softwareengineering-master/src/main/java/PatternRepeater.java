public class PatternRepeater {

    /**
     * Repeat a specific character n times.
     *
     * @param character character specified
     * @param n times to repeat character
     * @return the specified character repeated n times
     */
    public String repeatPattern(final char character, final int n) {

        String result = "";
        for (int i = 0; i < n; i++) {
            result = result + character;
        }
        return result;
    }

}
