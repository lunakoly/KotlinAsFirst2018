package lesson5.task1

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Tag
import org.junit.jupiter.api.Test

class Tests {
    @Test
    @Tag("Example")
    fun shoppingListCostTest() {
        val itemCosts = mapOf(
                "Хлеб" to 50.0,
                "Молоко" to 100.0
        )
        assertEquals(
                150.0,
                shoppingListCost(
                        listOf("Хлеб", "Молоко"),
                        itemCosts
                )
        )
        assertEquals(
                150.0,
                shoppingListCost(
                        listOf("Хлеб", "Молоко", "Кефир"),
                        itemCosts
                )
        )
        assertEquals(
                0.0,
                shoppingListCost(
                        listOf("Хлеб", "Молоко", "Кефир"),
                        mapOf()
                )
        )
    }

    @Test
    @Tag("Example")
    fun filterByCountryCode() {
        val phoneBook = mutableMapOf(
                "Quagmire" to "+1-800-555-0143",
                "Adam's Ribs" to "+82-000-555-2960",
                "Pharmakon Industries" to "+1-800-555-6321"
        )

        filterByCountryCode(phoneBook, "+1")
        assertEquals(2, phoneBook.size)

        filterByCountryCode(phoneBook, "+1")
        assertEquals(2, phoneBook.size)

        filterByCountryCode(phoneBook, "+999")
        assertEquals(0, phoneBook.size)
    }

    @Test
    @Tag("Example")
    fun removeFillerWords() {
        assertEquals(
                "Я люблю Котлин".split(" "),
                removeFillerWords(
                        "Я как-то люблю Котлин".split(" "),
                        "как-то"
                )
        )
        assertEquals(
                "Я люблю Котлин".split(" "),
                removeFillerWords(
                        "Я как-то люблю таки Котлин".split(" "),
                        "как-то",
                        "таки"
                )
        )
        assertEquals(
                "Я люблю Котлин".split(" "),
                removeFillerWords(
                        "Я люблю Котлин".split(" "),
                        "как-то",
                        "таки"
                )
        )
    }

    @Test
    @Tag("Example")
    fun buildWordSet() {
        assertEquals(
                buildWordSet("Я люблю Котлин".split(" ")),
                mutableSetOf("Я", "люблю", "Котлин")
        )
        assertEquals(
                buildWordSet("Я люблю люблю Котлин".split(" ")),
                mutableSetOf("Котлин", "люблю", "Я")
        )
        assertEquals(
                buildWordSet(listOf()),
                mutableSetOf<String>()
        )
    }

    @Test
    @Tag("Normal")
    fun mergePhoneBooks() {
        assertEquals(
                mapOf("Emergency" to "112"),
                mergePhoneBooks(
                        mapOf("Emergency" to "112"),
                        mapOf("Emergency" to "112")
                )
        )
        assertEquals(
                mapOf("Emergency" to "112", "Police" to "02"),
                mergePhoneBooks(
                        mapOf("Emergency" to "112"),
                        mapOf("Emergency" to "112", "Police" to "02")
                )
        )
        assertEquals(
                mapOf("Emergency" to "112, 911", "Police" to "02"),
                mergePhoneBooks(
                        mapOf("Emergency" to "112"),
                        mapOf("Emergency" to "911", "Police" to "02")
                )
        )
        assertEquals(
                mapOf("Emergency" to "112, 911", "Fire department" to "01", "Police" to "02"),
                mergePhoneBooks(
                        mapOf("Emergency" to "112", "Fire department" to "01"),
                        mapOf("Emergency" to "911", "Police" to "02")
                )
        )
    }

    @Test
    @Tag("Easy")
    fun buildGrades() {
        assertEquals(
                mapOf<Int, List<String>>(),
                buildGrades(mapOf())
        )
        // TODO: Sort the values here or let the students do it?
        assertEquals(
                mapOf(5 to listOf("Семён", "Михаил"), 3 to listOf("Марат")),
                buildGrades(mapOf("Марат" to 3, "Семён" to 5, "Михаил" to 5))
        )
        assertEquals(
                mapOf(3 to listOf("Семён", "Михаил", "Марат")),
                buildGrades(mapOf("Марат" to 3, "Семён" to 3, "Михаил" to 3))
        )
    }

    @Test
    @Tag("Easy")
    fun containsIn() {
        assertTrue(containsIn(mapOf("a" to "z"), mapOf("a" to "z", "b" to "sweet")))
        assertFalse(containsIn(mapOf("a" to "z"), mapOf("a" to "zee", "b" to "sweet")))
    }

    @Test
    @Tag("Normal")
    fun averageStockPrice() {
        assertEquals(
                mapOf<String, Double>(),
                averageStockPrice(listOf())
        )
        assertEquals(
                mapOf("MSFT" to 100.0, "NFLX" to 40.0),
                averageStockPrice(listOf("MSFT" to 100.0, "NFLX" to 40.0))
        )
        assertEquals(
                mapOf("MSFT" to 150.0, "NFLX" to 40.0),
                averageStockPrice(listOf("MSFT" to 100.0, "MSFT" to 200.0, "NFLX" to 40.0))
        )
        assertEquals(
                mapOf("MSFT" to 150.0, "NFLX" to 45.0),
                averageStockPrice(listOf("MSFT" to 100.0, "MSFT" to 200.0, "NFLX" to 40.0, "NFLX" to 50.0))
        )
    }

    @Test
    @Tag("Normal")
    fun findCheapestStuff() {
        assertNull(
                findCheapestStuff(
                        mapOf("Мария" to ("печенье" to 20.0), "Орео" to ("печенье" to 100.0)),
                        "торт"
                )
        )
        assertEquals(
                "Мария",
                findCheapestStuff(
                        mapOf("Мария" to ("печенье" to 20.0), "Орео" to ("печенье" to 100.0)),
                        "печенье"
                )
        )
    }

    @Test
    @Tag("Hard")
    fun propagateHandshakes() {
        assertEquals(
                mapOf(
                        "Marat" to setOf("Mikhail", "Sveta"),
                        "Sveta" to setOf("Mikhail"),
                        "Mikhail" to setOf()
                ),
                propagateHandshakes(
                        mapOf(
                                "Marat" to setOf("Sveta"),
                                "Sveta" to setOf("Mikhail")
                        )
                )
        )
        assertEquals(
                mapOf(
                        "Marat" to setOf("Mikhail", "Sveta"),
                        "Sveta" to setOf("Marat", "Mikhail"),
                        "Mikhail" to setOf("Sveta", "Marat")
                ),
                propagateHandshakes(
                        mapOf(
                                "Marat" to setOf("Mikhail", "Sveta"),
                                "Sveta" to setOf("Marat"),
                                "Mikhail" to setOf("Sveta")
                        )
                )
        )
    }

    @Test
    @Tag("Easy")
    fun subtractOf() {
        val from = mutableMapOf("a" to "z", "b" to "c")

        subtractOf(from, mapOf())
        assertEquals(from, mapOf("a" to "z", "b" to "c"))

        subtractOf(from, mapOf("b" to "z"))
        assertEquals(from, mapOf("a" to "z", "b" to "c"))

        subtractOf(from, mapOf("a" to "z"))
        assertEquals(from, mapOf("b" to "c"))
    }

    @Test
    @Tag("Easy")
    fun whoAreInBoth() {
        assertEquals(
                emptyList<String>(),
                whoAreInBoth(emptyList(), emptyList())
        )
        assertEquals(
                listOf("Marat"),
                whoAreInBoth(listOf("Marat", "Mikhail"), listOf("Marat", "Kirill"))
        )
        assertEquals(
                emptyList<String>(),
                whoAreInBoth(listOf("Marat", "Mikhail"), listOf("Sveta", "Kirill"))
        )
    }

    @Test
    @Tag("Normal")
    fun canBuildFrom() {
        assertFalse(canBuildFrom(emptyList(), "foo"))
        assertTrue(canBuildFrom(listOf('a', 'b', 'o'), "baobab"))
        assertFalse(canBuildFrom(listOf('a', 'm', 'r'), "Marat"))
    }

    @Test
    @Tag("Normal")
    fun extractRepeats() {
        assertEquals(
                emptyMap<String, Int>(),
                extractRepeats(emptyList())
        )
        assertEquals(
                mapOf("a" to 2),
                extractRepeats(listOf("a", "b", "a"))
        )
        assertEquals(
                emptyMap<String, Int>(),
                extractRepeats(listOf("a", "b", "c"))
        )
    }

    @Test
    @Tag("Normal")
    fun hasAnagrams() {
        assertFalse(hasAnagrams(emptyList()))
        assertTrue(hasAnagrams(listOf("рот", "свет", "тор")))
        assertFalse(hasAnagrams(listOf("рот", "свет", "код", "дверь")))
        assertFalse(hasAnagrams(listOf(
                "",
                "\"`GB<~/Fd\\;&Sh<IF9h_:\$k;iWy!~XX-e)MQv}PBn%TG? Lz@1M91NtX2V'`!?5^A_,M))!e%HPKXsg+hk.F",
                "\"",
                "2+-o6l.t$99OM]:\"d6",
                "2cu'<S =E\$_S$",
                "7",
                "b-i<",
                "^Q*b/szrdKS%o5G@<m3yDQ",
                "CT:BBXfk5v1%+S,OTL{04$6iy(3R\"mi9V6-7(vWlR,(_+3o%*!NZ1FRtjx",
                "64@)j*6\\ewu*",
                "a\"dQ5\t}VHW=Y?!H6)L2?js8 u0TuW6`?5omEVB\n5>K+8Se Y7rC'jEvJ1e4G@\"`B<v@)kd :Py]zKC[Pd;\\122~O{wOZ,V/hIm&py5@R\n(\"(3YK|T0xW&E=_hKqvQbJg)_{FM&T:nP35H,DPP:3[Q_\"Q!H~Pj"
        )))
    }

    @Test
    @Tag("Hard")
    fun findSumOfTwo() {
        assertEquals(
                Pair(-1, -1),
                findSumOfTwo(emptyList(), 1)
        )
        assertEquals(
                Pair(0, 2),
                findSumOfTwo(listOf(1, 2, 3), 4)
        )
        assertEquals(
                Pair(-1, -1),
                findSumOfTwo(listOf(1, 2, 3), 6)
        )
        assertEquals(
                Pair(223, 309),
                findSumOfTwo(listOf(
                        1,
                        1,
                        12546,
                        40700,
                        21844,
                        4219,
                        11462,
                        45497,
                        40699,
                        41864,
                        0,
                        40700,
                        19177,
                        1,
                        0,
                        18365,
                        0,
                        1,
                        1,
                        40700,
                        40699,
                        1,
                        41566,
                        40700,
                        44259,
                        41941,
                        40699,
                        40699,
                        40700,
                        32461,
                        0,
                        40699,
                        28001,
                        40699,
                        3176,
                        40699,
                        40699,
                        40700,
                        43930,
                        40699,
                        26412,
                        40700,
                        32490,
                        34601,
                        40699,
                        30176,
                        40700,
                        0,
                        1,
                        44515,
                        40699,
                        27390,
                        25350,
                        1,
                        0,
                        0,
                        8949,
                        40699,
                        16796,
                        35484,
                        13020,
                        11675,
                        40700,
                        40700,
                        40700,
                        0,
                        0,
                        40699,
                        1,
                        40700,
                        9813,
                        0,
                        17091,
                        40700,
                        1,
                        0,
                        32156,
                        40700,
                        1,
                        29636,
                        39285,
                        40699,
                        18223,
                        40700,
                        12758,
                        1,
                        40699,
                        40700,
                        10993,
                        43534,
                        23173,
                        40699,
                        1,
                        40699,
                        47951,
                        40700,
                        40699,
                        10347,
                        25638,
                        11491,
                        272,
                        31557,
                        40699,
                        43227,
                        1,
                        1,
                        0,
                        17835,
                        1,
                        1,
                        40699,
                        44503,
                        40699,
                        1587,
                        40699,
                        1,
                        25081,
                        1,
                        18870,
                        40700,
                        0,
                        21626,
                        34375,
                        21945,
                        45909,
                        40700,
                        40700,
                        24249,
                        40699,
                        32362,
                        29450,
                        0,
                        18122,
                        1081,
                        31189,
                        31506,
                        0,
                        0,
                        7661,
                        36187,
                        40699,
                        0,
                        40700,
                        23909,
                        18671,
                        40699,
                        0,
                        40700,
                        13991,
                        0,
                        40700,
                        40700,
                        0,
                        1,
                        40699,
                        1,
                        1,
                        0,
                        38941,
                        35000,
                        37119,
                        46141,
                        27938,
                        0,
                        40700,
                        40699,
                        0,
                        32003,
                        40699,
                        1,
                        21976,
                        31540,
                        47862,
                        40700,
                        40699,
                        0,
                        1,
                        0,
                        25235,
                        40499,
                        8922,
                        35475,
                        12073,
                        48571,
                        1,
                        40700,
                        40700,
                        47354,
                        40700,
                        3697,
                        23322,
                        40699,
                        11917,
                        1,
                        45439,
                        42783,
                        1,
                        0,
                        20951,
                        40699,
                        45220,
                        1,
                        1,
                        40700,
                        0,
                        34749,
                        11805,
                        27532,
                        41542,
                        9355,
                        32543,
                        11070,
                        40700,
                        20086,
                        40700,
                        30014,
                        1,
                        43123,
                        40699,
                        39236,
                        18308,
                        18378,
                        30744,
                        20710,
                        11147,
                        40699,
                        25613,
                        45061,
                        15010,
                        40700,
                        0,
                        40700,
                        40699,
                        40700,
                        1,
                        1,
                        40754,
                        10902,
                        40700,
                        18781,
                        1,
                        40699,
                        18004,
                        23011,
                        4583,
                        0,
                        28363,
                        1,
                        25947,
                        40700,
                        1,
                        1,
                        37503,
                        49579,
                        49886,
                        0,
                        10720,
                        1,
                        40699,
                        0,
                        1,
                        40700,
                        0,
                        9624,
                        40699,
                        40699,
                        0,
                        40699,
                        35043,
                        862,
                        7859,
                        1586,
                        36796,
                        0,
                        40700,
                        0,
                        1,
                        9863,
                        1,
                        0,
                        40699,
                        1,
                        38884,
                        25603,
                        46367,
                        1,
                        40699,
                        34216,
                        40699,
                        40700,
                        24260,
                        1,
                        48627,
                        1,
                        15113,
                        40699,
                        38987,
                        20613,
                        49326,
                        40699,
                        40699,
                        20650,
                        39421,
                        1,
                        40700,
                        40699,
                        28492,
                        48178,
                        36752,
                        41463,
                        24763,
                        31729,
                        14441,
                        40699,
                        1,
                        0,
                        40700,
                        0,
                        1,
                        27651,
                        40700,
                        40699,
                        2127,
                        0,
                        32923,
                        1,
                        40699,
                        30448,
                        1,
                        40700,
                        1,
                        8197,
                        40699,
                        0,
                        6496,
                        40700,
                        41047,
                        40699,
                        40699,
                        0,
                        48066,
                        30346,
                        9941,
                        5212,
                        9130,
                        1,
                        40700,
                        29141,
                        0,
                        27625,
                        20543,
                        40699,
                        1,
                        1,
                        0,
                        1,
                        40699,
                        881,
                        0,
                        0,
                        13334,
                        18296,
                        20123,
                        40699,
                        40700,
                        1,
                        40700,
                        1782,
                        0,
                        0,
                        23823,
                        24694,
                        0,
                        40699,
                        40700,
                        6102,
                        1,
                        14297,
                        20883,
                        14212,
                        0,
                        24699,
                        41704,
                        40699,
                        1,
                        40700,
                        17688,
                        40700,
                        40699,
                        0,
                        0,
                        1,
                        40699,
                        49640,
                        0,
                        0,
                        30802,
                        19739,
                        40699,
                        40699,
                        40700,
                        40700,
                        0,
                        40699,
                        1,
                        36207,
                        18746,
                        40699,
                        17540,
                        0,
                        39705,
                        7498,
                        35201,
                        0,
                        42719,
                        37521,
                        3780,
                        6350,
                        10527,
                        40700,
                        43127,
                        1,
                        1,
                        8090,
                        0,
                        11451,
                        40699,
                        8177,
                        5617,
                        40699,
                        40700,
                        36589,
                        4946,
                        40699,
                        0,
                        0,
                        0,
                        40699,
                        0,
                        40700,
                        1,
                        32822,
                        40700,
                        0,
                        15135,
                        2729,
                        17875,
                        0,
                        40700,
                        40699,
                        1,
                        38721,
                        0,
                        1,
                        1837,
                        0,
                        25042,
                        44678,
                        1,
                        0,
                        11235,
                        0,
                        46216,
                        40699,
                        40700,
                        0,
                        1,
                        40700,
                        0,
                        40699,
                        1,
                        40699,
                        24506,
                        35524,
                        40699,
                        0,
                        47200,
                        43377,
                        1,
                        40700,
                        1,
                        28705,
                        1,
                        40699,
                        0,
                        40699,
                        18038,
                        1097,
                        35412,
                        34526,
                        40700,
                        0,
                        47468,
                        40700,
                        384,
                        4807,
                        0,
                        1,
                        12412,
                        1,
                        1185,
                        43098,
                        40699,
                        1,
                        24274,
                        10783,
                        0,
                        1,
                        1,
                        38462,
                        8341,
                        15424,
                        39975,
                        40699,
                        29952,
                        17433,
                        40700,
                        40699,
                        20398,
                        25041,
                        21625,
                        23046,
                        0,
                        40699,
                        0,
                        1,
                        15688,
                        40700,
                        11401,
                        40699,
                        0,
                        40699,
                        40700,
                        40700,
                        0,
                        0,
                        0,
                        0,
                        1,
                        40699,
                        40700,
                        23280,
                        40700,
                        0,
                        0,
                        46149,
                        5007,
                        0,
                        40699,
                        0,
                        40699,
                        0,
                        9322,
                        5190,
                        5250,
                        40699,
                        43794,
                        40700,
                        0,
                        7244,
                        0,
                        1,
                        1,
                        27176,
                        40700,
                        1,
                        1
                ), 62173)
        )
    }

    @Test
    @Tag("Impossible")
    fun bagPacking() {
        assertEquals(
                setOf("Кубок"),
                bagPacking(
                        mapOf("Кубок" to (500 to 2000), "Слиток" to (1000 to 5000)),
                        850
                )
        )
        assertEquals(
                emptySet<String>(),
                bagPacking(
                        mapOf("Кубок" to (500 to 2000), "Слиток" to (1000 to 5000)),
                        450
                )
        )
        assertEquals(
                setOf(
                        "15",
                        "14",
                        "13",
                        "12",
                        "11",
                        "10",
                        "9",
                        "8",
                        "7",
                        "6",
                        "5",
                        "4",
                        "3",
                        "1",
                        "0"
                ),
                bagPacking(
                        mapOf(
                                "0" to (1 to 148),
                                "1" to (149 to 464),
                                "2" to (427 to 16),
                                "3" to (148 to 384),
                                "4" to (41 to 149),
                                "5" to (148 to 148),
                                "6" to (3 to 148),
                                "7" to (2 to 47),
                                "8" to (222 to 328),
                                "9" to (1 to 148),
                                "10" to (149 to 268),
                                "11" to (1 to 212),
                                "12" to (148 to 170),
                                "13" to (283 to 149),
                                "14" to (1 to 143),
                                "15" to (1 to 149)
                        ),1677
                )
        )
        assertEquals(
                setOf(
                        "13",
                        "10",
                        "9",
                        "8",
                        "6",
                        "3",
                        "2",
                        "0"
                ),
                bagPacking(
                        mapOf(
                                "0" to (186 to 149),
                                "1" to (1 to 1),
                                "2" to (149 to 166),
                                "3" to (153 to 441),
                                "4" to (434 to 149),
                                "5" to (149 to 1),
                                "6" to (148 to 173),
                                "7" to (149 to 10),
                                "8" to (377 to 46),
                                "9" to (231 to 148),
                                "10" to (246 to 149),
                                "11" to (111 to 1),
                                "12" to (149 to 1),
                                "13" to (148 to 252),
                                "14" to (469 to 148)
                        ), 1638
                )
        )
//        assertEquals(
//                setOf(
//                        "19",
//                        "17",
//                        "15",
//                        "14",
//                        "13",
//                        "12",
//                        "11",
//                        "8",
//                        "7",
//                        "4",
//                        "2",
//                        "1",
//                        "0"
//                ),
//                bagPacking(
//                        mutableMapOf(
//                                "0" to (148 to 148),
//                                "1" to (486 to 149),
//                                "2" to (1 to 208),
//                                "3" to (495 to 2),
//                                "4" to (2 to 358),
//                                "5" to (408 to 148),
//                                "6" to (419 to 24),
//                                "7" to (135 to 12),
//                                "8" to (149 to 371),
//                                "9" to (149 to 1),
//                                "10" to (148 to 1),
//                                "11" to (148 to 338),
//                                "12" to (295 to 148),
//                                "13" to (388 to 348),
//                                "14" to (148 to 148),
//                                "15" to (148 to 149),
//                                "16" to (429 to 149),
//                                "17" to (149 to 148),
//                                "18" to (149 to 2),
//                                "19" to (2 to 148)
//                        ), 2260
//                )
//        )
    }

    // TODO: map task tests
}
