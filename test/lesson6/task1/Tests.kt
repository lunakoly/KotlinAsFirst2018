package lesson6.task1

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Tag
import org.junit.jupiter.api.Test

class Tests {
    @Test
    @Tag("Example")
    fun timeStrToSeconds() {
        assertEquals(36000, timeStrToSeconds("10:00:00"))
        assertEquals(41685, timeStrToSeconds("11:34:45"))
        assertEquals(86399, timeStrToSeconds("23:59:59"))
    }

    @Test
    @Tag("Example")
    fun twoDigitStr() {
        assertEquals("00", twoDigitStr(0))
        assertEquals("09", twoDigitStr(9))
        assertEquals("10", twoDigitStr(10))
        assertEquals("99", twoDigitStr(99))
    }

    @Test
    @Tag("Example")
    fun timeSecondsToStr() {
        assertEquals("10:00:00", timeSecondsToStr(36000))
        assertEquals("11:34:45", timeSecondsToStr(41685))
        assertEquals("23:59:59", timeSecondsToStr(86399))
    }

    @Test
    @Tag("Normal")
    fun dateStrToDigit() {
        assertEquals("15.07.2016", dateStrToDigit("15 июля 2016"))
        assertEquals("", dateStrToDigit("3 мартобря 1918"))
        assertEquals("18.11.2018", dateStrToDigit("18 ноября 2018"))
        assertEquals("", dateStrToDigit("23"))
        assertEquals("03.04.2011", dateStrToDigit("3 апреля 2011"))
        assertEquals("", dateStrToDigit("32 сентября 2011"))
        assertEquals("", dateStrToDigit("29 февраля 1993"))
        assertEquals("29.07.1", dateStrToDigit("29 июля 1"))
        assertEquals("02.10.1581459", dateStrToDigit("02 октября 1581459"))
    }

    @Test
    @Tag("Normal")
    fun dateDigitToStr() {
        assertEquals("15 июля 2016", dateDigitToStr("15.07.2016"))
        assertEquals("", dateDigitToStr("01.02.20.19"))
        assertEquals("", dateDigitToStr("28.00.2000"))
        assertEquals("3 апреля 2011", dateDigitToStr("03.04.2011"))
        assertEquals("", dateDigitToStr("ab.cd.ef"))
        assertEquals("", dateStrToDigit("32.09.2011"))
        assertEquals("", dateStrToDigit("29.02.1993"))
        assertEquals("15 марта 1", dateDigitToStr("15.03.1"))
    }

    @Test
    @Tag("Normal")
    fun flattenPhoneNumber() {
        assertEquals("+79211234567", flattenPhoneNumber("+7 (921) 123-45-67"))
        assertEquals("123456798", flattenPhoneNumber("12 --  34- 5 -- 67 -98"))
        assertEquals("", flattenPhoneNumber("ab-123"))
        assertEquals("+12345", flattenPhoneNumber("+12 (3) 4-5"))
        assertEquals("", flattenPhoneNumber("134_+874"))
    }

    @Test
    @Tag("Normal")
    fun bestLongJump() {
        assertEquals(717, bestLongJump("706 % - 717 - 703"))
        assertEquals(-1, bestLongJump("% - - % -"))
        assertEquals(754, bestLongJump("700 717 707 % 754"))
        assertEquals(-1, bestLongJump("700 + 700"))
        assertEquals(2147483647, bestLongJump("1858768552 0 %  - 0 1  1045270984  0 - 2147483647 2147483647 - 2147483647  - 1 %  -   -  1677582720 1573180930  - 385672583    %    %  2147483647 - - -   - %    - %   - % 2147483647 - 2147483647 408265894  -    %   - 0    149155181  0 -  - 1524700801 - - - - % 1611341044  % 0 - - 2147483647 %  1748024125 741520687 2147483647   - 2147483647 -   -  2147483647 315335075  % -  %  % 884564568 - 1    170654968 453122444  -  - - 2147483647 0     87436985  1 -  -  2147483647   1 -  1  - - -  - 2147483647 %  1771037721 -     1918006018   - - 1029489498 2146948249 0 - - - 1924028616 225755915 -   808340662 -   2147483647 1412516830     -   - -   2147483647    - - 1186302211 1431777414 - 1  2147483647  - % -  %   1587016695   % 441479263  -  458964590 %  2147483647   - %   - 218864932   1 - - 2147483647 - % -  %   % 1613712624  - 1498905000   1  1516481260  2147483647 2147483647   - % -  - 1331648061   -  - - - 2147483647   1 1 - %  513285397   - - 2147483647 2147483647 - 934628581  0  506785376 1 %   -    % - -   121214711 0 2147483647   1 %  0  2147483647  %  -    745424468 -      2147483647 2147483647  - 2147483647 -  2147483647 0   0 -    1 2147483647 % - 2147483647 1301127594 - 1324579160 -  - %  1  2147483647 2079318514 2147483647 - 0 - - 2147483647  % - 23353353 95832754 2147483647 - - -   - 1  % 0  -  - 1000901798 - - %       - % - -    2046752146 2147483647 % 589195848 0 2147483647 0 -   -  - 1680953667   1 -    2147483647 0 % 0     1156191211    -    1  % -  2147483647 -      - 2147483647 -   % 674805945 %  190940109 - 1837880197 2147483647 847695529 -  - %    - % -  %   %  1622619041  2147483647 1597937015 759005166 279390221  266009966     - 2147483647 - 519522871 -    %   2147483647  - - -  - 745541194   166261571  - -  - 0 - - -  -  1769143673 2147483647   512745017   934883766 952186050 -   1605390192 - 0 % 0 1  % - 1667158893 985641969    - 2147483647    %   % -    %  0  1216157935 - - - -  2147483647 2147483647    % 2147483647  2147483647 -  437410546 2147483647  0 0 -     - 1383549758 %     2147483647  - 524450595  %     0   2147483647 - 1300473784 - - 2132534018 306045134 % %   2147483647 1727828258  - 2147483647 2147483647 -   2147483647 - % %  % 2147483647 - % 1203852395   1306375098 707441206  % 996837861  1 - - - 2147483647 2147483647 2147483647 1042398457 %    - 2147483647 2147483647   -  - -    498436496 513510409 - - 0  -  2147483647 - 262100933 - -  %    1415692587 2147483647  1 0 %  - -  1266552393  657689656     - 541479916    % % 2147483647  -  - -  -  - -   2147483647 %  - %  - % 600451270  %     %  - - 203832146  -  % - -    2147483647  2147483647 -  2147483647 314331690  - % - - -  2147483647  297828751 - 1599985012 - - - - -   1309880933 - 2147483647  1   -  2147483647 % %  1274841140  1032037972 0  2147483647 -  % % % % 21850539 - - -  329493212 % %   2147483647 - 1107448611 -  - - %   2147483647 -   2147483647  -  -   1 1 - 583828342 - -  1717971303 % 1973016941 - 2147483647 - 1918278939  507215360  2147483647  - -   % %   % 0 -   163743759 - % - 922119598 810168976 2147483647 - - - - -  % %    - % 1693557888 2147483647 %   - -  2147483647 %   0 -  0  -  -  180377897 1 -  -  %  0 362301810 - 2147483647 - - %   1  2147483647 2147483647 1    %  - % % 0  766428330  1171496431 - % -  1  - -  1   1 471279546 %   502408321 -      - %     % 1877332967 460336179 % -  % 0  - % 2147483647 -  1 0  -  -    %  -    % 0    909165764 %  -  %  - 0 -       0  2147483647   % 522228831 2103996923  -  614143578 1817859501 2147483647 - 0 %  - 1342308846  0 - - -  0 -  2147483647 % - -  -  - 35274090   0  - - 2147483647   - 1 - 1 0 -      -   -  % 2147483647 972684790 - % - -  -   877951405  -  1 -      -        -   2147483647 - - - 1 872969723  - -  232695532   1602916111  -  %  1   - - - % -  % %  97182451 1322080844   1 %  370889106 - 2147483647 1834446624 617792088 -     %  -  48685021  - - 2147483647    - -     %   2147483647 1198173130 -   382721726 - -  - 0 - % %  -   -  %  1 2147483647 -  1137117056  708371661 - 105254402 824526266 1119488893    - 1 - 440058711 2147483647   % - 1522414997 2147483647 -  %  %   1870102747 - - 417969732 -   -  1434268509  426043015    2147483647 -  1 - - % 83636774 1488073708 -  2147483647 -  0 1230474913  % 164874511  2147483647 1 1018302018 - -  2147483647  0 215270894 % %   - 2147483647 1645220121 260645146   - 1     130601662 -    - %  182153917 %  % -   - 2147483647  - 2147483647 1526124850 1    - 1 1785159363  - - % 1463733281 -  -  % 1118167613  -    2147483647   569516590  - - -        % 611821240  -  396127557 - %  506858948 2147483647  2147483647 1  1511596479 - -   2147483647 739866080 -  % 2147483647    2147483647  1908339618 %   514295129 - 1583703915  2147483647 - -     -    - 0 - - 532304505 -     %    -  2147483647    - % - % %  - -  717116042   - - 26310248 -  580465064  % 2147483647 2147483647 932240640  0   - -  344530524 %   1 2147483647 0 2147483647  % 1 2147483647 - -   2147483647  - % 2147483647 -  %  -   93744327 1 875116808 2147483647  0   1793699470  % -     -  211276708   1   - % -  0  - - 2147483647 2049185821 -  -    - 1867564361   -  %  - 0 - 2147483647   - 2002579331 1670904103 963190008 203888056  0  - - 139655942 -  1243503698   1966689008 1868615871 % % % 1104856343 2147483647 315241789 647588410    1   - 1 -  2147483647 %  1437324943  1685729281  - - - - %  1376465166   - %  1 - 1993969470   - - %  0 2147483647 - 2147483647 1 - - 1  1104834032 - 1529489003 338949659    - -    -     - 124144730 610120986 - -  2147483647   - -    1826008845 %  % - 252465381 -    1597122976 1230938398 - % -   2147483647 % -  354446840 -  - -  - %  - 1 2147483647  -  %  - -  -    -  %  472932512 -  0 - -    0  % %  -  2147483647 % 2147483647  - % 1285095162 -  -  510152066 - 1166981785 - -  - %  - % - 2147483647  - 737532247    %   -     %      -   - - 630043565 1016690920  251787636  - - 2147483647  - - - 506992844 2147483647 -  -  0 %  2046260414  0 - - 1416419242 151768387 - 676718971 1660925403     2147483647 - -   - % % -  2147483647 2147483647 2147483647  0  - % -   820242605  -  - 1566768818 % - 0  - -     1733142179   790407436  1195017216  - %  1869451635 2147483647 1568478371 2147483647 0 1 1884140543  2125198771 - -     1764520258"))
        assertEquals(2147483647, bestLongJump("% 1324478003   - - 804811283 0 - % - 987786405  % - -  2147483647    2147483647   -   - % % % 823393153 % 0  - - 738253487   995292870 -    2147483647 1348763387 906079433   - 0    % - 2073750889 -  2147483647 -  698261700  -      - 1 176383890 - -  0 - 2147483647 - -  - - % - 0  %  -   1   - 2147483647  2147483647  - 1 %  0  - %  %  -   - 2147483647  -  %   0  -  % - % - 1354541310 -  0 1 -  1751768865 1442257070 - -     % - % -   526568784 0    0 1405856407  -  % 188044408  - 0    - -  -  0  %     2147483647 178538446 - % - - -  - -  - -       %  2147483647 2147483647 -      - 2147483647 0 631198740  % 1 % % 1285119386  - -  %  342016421  % -  -  1263672619 641249036 %   -  - 2147483647    1681289704"))
    }

    @Test
    @Tag("Hard")
    fun bestHighJump() {
        assertEquals(226, bestHighJump("226 +"))
        assertEquals(-1, bestHighJump("???"))
        assertEquals(230, bestHighJump("220 + 224 %+ 228 %- 230 + 232 %%- 234 %"))
    }

    @Test
    @Tag("Hard")
    fun plusMinus() {
        assertEquals(0, plusMinus("0"))
        assertEquals(4, plusMinus("2 + 2"))
        assertEquals(6, plusMinus("2 + 31 - 40 + 13"))
        assertEquals(-1, plusMinus("0 - 1"))
        assertThrows(IllegalArgumentException::class.java) { plusMinus("+2") }
        assertThrows(IllegalArgumentException::class.java) { plusMinus("+ 4") }
        assertThrows(IllegalArgumentException::class.java) { plusMinus("4 - -2") }
        assertThrows(IllegalArgumentException::class.java) { plusMinus("44 - - 12") }
        assertThrows(IllegalArgumentException::class.java) { plusMinus("4 - + 12") }
        assertThrows(IllegalArgumentException::class.java) { plusMinus("wZYb93%fDQDHK}t9Y@!o") }
    }

    @Test
    @Tag("Hard")
    fun firstDuplicateIndex() {
        assertEquals(-1, firstDuplicateIndex("Привет"))
        assertEquals(9, firstDuplicateIndex("Он пошёл в в школу"))
        assertEquals(40, firstDuplicateIndex("Яблоко упало на ветку с ветки оно упало на на землю"))
        assertEquals(9, firstDuplicateIndex("Мы пошли прямо Прямо располагался магазин"))
        assertEquals(0, firstDuplicateIndex("` ` C , u B P ( 3 U b \$ B . U ~ b ' ) q e S @ a o y q M r P { j K 9 d y [ p ^ q I + S F p i K G \\\\ : B 8 M 8 B h # V , c * \$ z j 3 { ^ A S ! b W X P N 0 v % Q Z m + 8 % j C X = : ~ > # { o J 7 V 6 O 4 q \$ T { 8 | \\\\ s o O 4 1 # C 2 t g ( 5 1 W 2 b k I q d ^ 3 , d x b ? C @ L 4 H U 1 g h q k @"))
        assertEquals(118, firstDuplicateIndex("( ? c q K U / w i ( i c > 4 ( 2 ; i h | ; > i } 5 l z b k % 6 ? ! ? d < Q Y G k _ f { F d } i } _ A e q ! # U @ w e t j j"))
    }

    @Test
    @Tag("Hard")
    fun mostExpensive() {
        assertEquals("", mostExpensive(""))
        assertEquals("Курица", mostExpensive("Хлеб 39.9; Молоко 62.5; Курица 184.0; Конфеты 89.9"))
        assertEquals("Вино", mostExpensive("Вино 255.0"))
    }

    @Test
    @Tag("Hard")
    fun fromRoman() {
        assertEquals(1, fromRoman("I"))
        assertEquals(3000, fromRoman("MMM"))
        assertEquals(1978, fromRoman("MCMLXXVIII"))
        assertEquals(694, fromRoman("DCXCIV"))
        assertEquals(49, fromRoman("XLIX"))
        assertEquals(-1, fromRoman("Z"))
    }

    @Test
    @Tag("Impossible")
    fun computeDeviceCells() {
        assertEquals(listOf(0, 0, 0, 0, 0, 1, 1, 1, 1, 1), computeDeviceCells(10, "+>+>+>+>+", 10000))
        assertEquals(listOf(-1, -1, -1, -1, -1, 0, 0, 0, 0, 0), computeDeviceCells(10, "<-<-<-<-<-", 10000))
        assertEquals(listOf(1, 1, 1, 1, 1, 0, 0, 0, 0, 0), computeDeviceCells(10, "- <<<<< +[>+]", 10000))
        assertEquals(listOf(0, 8, 7, 6, 5, 4, 3, 2, 1, 0, 0),
                computeDeviceCells(11, "<<<<< + >>>>>>>>>> --[<-] >+[>+] >++[--< <[<] >+[>+] >++]", 10000))

        assertEquals(listOf(0, 0, 0, 0, 0, 1, 1, 0, 0, 0), computeDeviceCells(10, "+>+>+>+>+", 4))
        assertEquals(listOf(0, 0, -1, -1, -1, 0, 0, 0, 0, 0), computeDeviceCells(10, "<-<-<-<-<-", 6))
        assertEquals(listOf(1, 1, 1, 0, 0, -1, 0, 0, 0, 0), computeDeviceCells(10, "- <<<<< +[>+]", 17))
        assertEquals(listOf(0, 6, 5, 4, 3, 2, 1, 0, -1, -1, -2),
                computeDeviceCells(11, "<<<<< + >>>>>>>>>> --[<-] >+[>+] >++[--< <[<] >+[>+] >++]", 256))
        assertThrows(IllegalArgumentException::class.java) { computeDeviceCells(10, "===", 3) }
        assertThrows(IllegalArgumentException::class.java) { computeDeviceCells(10, "+>+>[+>", 3) }
        assertThrows(IllegalStateException::class.java) { computeDeviceCells(20, ">>>>>>>>>>>>>", 12) }
        assertThrows(IllegalStateException::class.java) { computeDeviceCells(1, "<", 12) }
    }
}