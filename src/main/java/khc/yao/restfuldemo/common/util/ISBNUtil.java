package khc.yao.restfuldemo.common.util;

import org.apache.commons.validator.routines.ISBNValidator;

public class ISBNUtil {
    /**
     * 判斷此 ISBN 10 / ISBN 13 是否有效
     * 可帶純數字的 ISBN，例如 9781566199094、1566199093
     * 也可帶有分隔符號的 ISBN，例如 978-1-56619-909-4、1-56619-909-3
     */
    public static boolean isValid(String ISBN) {
        return ISBNValidator.getInstance().isValid(ISBN);
    }

    /**
     * 判斷此 ISBN 是否為 ISBN 10
     */
    public static boolean isISBN10(String ISBN) {
        return ISBNValidator.getInstance().isValidISBN10(ISBN);
    }

    /**
     * 拿掉此 ISBN 10 的分隔符號，回傳純數字 ISBN 10
     * 例如傳入 1-56619-909-3，回傳 1566199093
     */
    public static String getISBN10WithoutSeparator(String ISBN10) {
        return ISBNValidator.getInstance().validateISBN10(ISBN10);
    }

    /**
     * 如果傳入 ISBN 10，會轉換成 ISBN 13，並回傳純數字 ISBN 13
     * 如果傳入 ISBN 13，直接回傳純數字 ISBN 13
     */
    public static String getISBN13WithoutSeparator(String ISBN) {
        return ISBNValidator.getInstance().validate(ISBN);
    }
}
