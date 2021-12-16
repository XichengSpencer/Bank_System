public class Utility {
    public static final double RMBToUSD = 0.16;
    public static final double GBPToUSD = 1.33;

    public static double getRate(String fromCurrency, String toCurrency) {
        if (fromCurrency.equals("RMB") && toCurrency.equals("USD")) {
            return RMBToUSD;
        } else if (fromCurrency.equals("GBP") && toCurrency.equals("USD")) {
            return GBPToUSD;
        } else if (fromCurrency.equals("RMB") && toCurrency.equals("GBP")) {
            return RMBToUSD / GBPToUSD;
        } else if (fromCurrency.equals("GBP") && toCurrency.equals("RMB")) {
            return GBPToUSD / RMBToUSD;
        } else if (fromCurrency.equals("USD") && toCurrency.equals("RMB")) {
            return 1 / RMBToUSD;
        } else {
            return 1 / GBPToUSD;
        }
    }
    public static boolean checkIsInt() {
        return false;
    }
}