@file:Suppress("UNUSED_PARAMETER", "ConvertCallChainIntoSequence")

package lesson4.task1

import lesson1.task1.discriminant
import kotlin.math.sqrt

/**
 * Пример
 *
 * Найти все корни уравнения x^2 = y
 */
fun sqRoots(y: Double) =
        when {
            y < 0 -> listOf()
            y == 0.0 -> listOf(0.0)
            else -> {
                val root = sqrt(y)
                // Результат!
                listOf(-root, root)
            }
        }

/**
 * Пример
 *
 * Найти все корни биквадратного уравнения ax^4 + bx^2 + c = 0.
 * Вернуть список корней (пустой, если корней нет)
 */
fun biRoots(a: Double, b: Double, c: Double): List<Double> {
    if (a == 0.0) {
        return if (b == 0.0) listOf()
        else sqRoots(-c / b)
    }
    val d = discriminant(a, b, c)
    if (d < 0.0) return listOf()
    if (d == 0.0) return sqRoots(-b / (2 * a))
    val y1 = (-b + sqrt(d)) / (2 * a)
    val y2 = (-b - sqrt(d)) / (2 * a)
    return sqRoots(y1) + sqRoots(y2)
}

/**
 * Пример
 *
 * Выделить в список отрицательные элементы из заданного списка
 */
fun negativeList(list: List<Int>): List<Int> {
    val result = mutableListOf<Int>()
    for (element in list) {
        if (element < 0) {
            result.add(element)
        }
    }
    return result
}

/**
 * Пример
 *
 * Изменить знак для всех положительных элементов списка
 */
fun invertPositives(list: MutableList<Int>) {
    for (i in 0 until list.size) {
        val element = list[i]
        if (element > 0) {
            list[i] = -element
        }
    }
}

/**
 * Пример
 *
 * Из имеющегося списка целых чисел, сформировать список их квадратов
 */
fun squares(list: List<Int>) = list.map { it * it }

/**
 * Пример
 *
 * Из имеющихся целых чисел, заданного через vararg-параметр, сформировать массив их квадратов
 */
fun squares(vararg array: Int) = squares(array.toList()).toTypedArray()

/**
 * Пример
 *
 * По заданной строке str определить, является ли она палиндромом.
 * В палиндроме первый символ должен быть равен последнему, второй предпоследнему и т.д.
 * Одни и те же буквы в разном регистре следует считать равными с точки зрения данной задачи.
 * Пробелы не следует принимать во внимание при сравнении символов, например, строка
 * "А роза упала на лапу Азора" является палиндромом.
 */
fun isPalindrome(str: String): Boolean {
    val lowerCase = str.toLowerCase().filter { it != ' ' }
    for (i in 0..lowerCase.length / 2) {
        if (lowerCase[i] != lowerCase[lowerCase.length - i - 1]) return false
    }
    return true
}

/**
 * Пример
 *
 * По имеющемуся списку целых чисел, например [3, 6, 5, 4, 9], построить строку с примером их суммирования:
 * 3 + 6 + 5 + 4 + 9 = 27 в данном случае.
 */
fun buildSumExample(list: List<Int>) = list.joinToString(separator = " + ", postfix = " = ${list.sum()}")

/**
 * Простая
 *
 * Найти модуль заданного вектора, представленного в виде списка v,
 * по формуле abs = sqrt(a1^2 + a2^2 + ... + aN^2).
 * Модуль пустого вектора считать равным 0.0.
 */
fun abs(v: List<Double>) = sqrt(v.fold(0.0) { prev, that -> prev + that * that })

/**
 * Простая
 *
 * Рассчитать среднее арифметическое элементов списка list. Вернуть 0.0, если список пуст
 */
fun mean(list: List<Double>) = if (list.isEmpty()) 0.0 else list.sum() / list.size

/**
 * Средняя
 *
 * Центрировать заданный список list, уменьшив каждый элемент на среднее арифметическое всех элементов.
 * Если список пуст, не делать ничего. Вернуть изменённый список.
 *
 * Обратите внимание, что данная функция должна изменять содержание списка list, а не его копии.
 */
fun center(list: MutableList<Double>): MutableList<Double> {
    val mean = mean(list)
    for (it in list.indices) list[it] -= mean
    return list
}

/**
 * Средняя
 *
 * Найти скалярное произведение двух векторов равной размерности,
 * представленные в виде списков a и b. Скалярное произведение считать по формуле:
 * C = a1b1 + a2b2 + ... + aNbN. Произведение пустых векторов считать равным 0.0.
 */
fun times(a: List<Double>, b: List<Double>) = a.foldIndexed(0.0) { i, prev, _ -> prev + a[i] * b[i] }

/**
 * Средняя
 *
 * Рассчитать значение многочлена при заданном x:
 * p(x) = p0 + p1*x + p2*x^2 + p3*x^3 + ... + pN*x^N.
 * Коэффициенты многочлена заданы списком p: (p0, p1, p2, p3, ..., pN).
 * Значение пустого многочлена равно 0.0 при любом x.
 */
fun polynom(p: List<Double>, x: Double): Double {
    var result = 0.0
    var theX = 1.0

    p.forEach {
        result += it * theX
        theX *= x
    }

    return result
}

/**
 * Средняя
 *
 * В заданном списке list каждый элемент, кроме первого, заменить
 * суммой данного элемента и всех предыдущих.
 * Например: 1, 2, 3, 4 -> 1, 3, 6, 10.
 * Пустой список не следует изменять. Вернуть изменённый список.
 *
 * Обратите внимание, что данная функция должна изменять содержание списка list, а не его копии.
 */
fun accumulate(list: MutableList<Double>): MutableList<Double> {
    list.foldIndexed(0.0) { it, prev, _ ->
        list[it] += prev
        list[it]
    }
    return list
}

/**
 * Средняя
 *
 * Разложить заданное натуральное число n > 1 на простые множители.
 * Результат разложения вернуть в виде списка множителей, например 75 -> (3, 5, 5).
 * Множители в списке должны располагаться по возрастанию.
 */
fun factorize(n: Int): List<Int> {
    val factors = mutableListOf<Int>()
    var number = n
    var it = 2

    while (it <= number) {
        if (number % it == 0) {
            factors.add(it)
            number /= it
        } else {
            it++
        }
    }

    return factors
}

/**
 * Сложная
 *
 * Разложить заданное натуральное число n > 1 на простые множители.
 * Результат разложения вернуть в виде строки, например 75 -> 3*5*5
 * Множители в результирующей строке должны располагаться по возрастанию.
 */
fun factorizeToString(n: Int) = factorize(n).joinToString(separator = "*")

/**
 * Средняя
 *
 * Перевести заданное целое число n >= 0 в систему счисления с основанием base > 1.
 * Результат перевода вернуть в виде списка цифр в base-ичной системе от старшей к младшей,
 * например: n = 100, base = 4 -> (1, 2, 1, 0) или n = 250, base = 14 -> (1, 3, 12)
 */
fun convert(n: Int, base: Int): List<Int> {
    val numbers = mutableListOf<Int>()
    var number = n

    do {
        numbers.add(number % base)
        number /= base
    } while (number > 0)

    return numbers.reversed()
}

/**
 * Сложная
 *
 * Перевести заданное целое число n >= 0 в систему счисления с основанием 1 < base < 37.
 * Результат перевода вернуть в виде строки, цифры более 9 представлять латинскими
 * строчными буквами: 10 -> a, 11 -> b, 12 -> c и так далее.
 * Например: n = 100, base = 4 -> 1210, n = 250, base = 14 -> 13c
 */
fun convertToString(n: Int, base: Int): String {
    var transformed = ""
    var number = n

    do {
        val sign = number % base
        transformed += if (sign < 10) sign.toString() else ('a' + (sign - 10))
        number /= base
    } while (number > 0)

    return transformed.reversed()
}

/**
 * Средняя
 *
 * Перевести число, представленное списком цифр digits от старшей к младшей,
 * из системы счисления с основанием base в десятичную.
 * Например: digits = (1, 3, 12), base = 14 -> 250
 */
fun decimal(digits: List<Int>, base: Int): Int {
    var result = 0
    var factor = 1

    digits.reversed().forEach {
        result += it * factor
        factor *= base
    }

    return result
}

/**
 * Сложная
 *
 * Перевести число, представленное цифровой строкой str,
 * из системы счисления с основанием base в десятичную.
 * Цифры более 9 представляются латинскими строчными буквами:
 * 10 -> a, 11 -> b, 12 -> c и так далее.
 * Например: str = "13c", base = 14 -> 250
 */
fun decimalFromString(str: String, base: Int): Int {
    var result = 0
    var factor = 1

    str.reversed().forEach {
        val sign = if (it <= '9') it - '0' else (it.toLowerCase() - 'a' + 10)
        result += sign * factor
        factor *= base
    }

    return result
}

/**
 * Calls callback for each digit of the number
 * @param callback code to be executed for each digit
 */
fun Int.forEachOfTheThreeRightmostDigits(callback: (Int) -> Unit) {
    var number = this
    var count = 0

    do {
        callback(number % 10)
        number /= 10
        count++
    } while (number > 0 && count < 3)
}

/**
 * Merely Roman digits in their increment order
 */
val ROMAN_SIGNS = listOf("I", "V", "X", "L", "C", "D", "M")

/**
 * Сложная
 *
 * Перевести натуральное число n > 0 в римскую систему.
 * Римские цифры: 1 = I, 4 = IV, 5 = V, 9 = IX, 10 = X, 40 = XL, 50 = L,
 * 90 = XC, 100 = C, 400 = CD, 500 = D, 900 = CM, 1000 = M.
 * Например: 23 = XXIII, 44 = XLIV, 100 = C
 */
fun roman(n: Int): String {
    var result = ""
    var index = 0

    // index will point to I, X, C and M

    n.forEachOfTheThreeRightmostDigits {
        // I decided to add useless '+ 0' to
        // improve readability
        result = when {
            it < 4  -> ROMAN_SIGNS[index + 0].repeat(it)
            it == 4 -> ROMAN_SIGNS[index + 0] + ROMAN_SIGNS[index + 1]
            it < 9  -> ROMAN_SIGNS[index + 1] + ROMAN_SIGNS[index + 0].repeat(it - 5)
            it == 9 -> ROMAN_SIGNS[index + 0] + ROMAN_SIGNS[index + 2]
            else    -> ROMAN_SIGNS[index + 2]
        } + result
        index += 2
    }

    return "M".repeat(n / 1000) + result
}

/**
 * Class that helps to present several
 * forms of the same word
 */
class Word(private vararg var variants: String) {
    init {
        if (variants.isEmpty())
            throw Exception("No variants specified for word")
    }
    operator fun get(index: Int) = if (index < variants.size) variants[index] else variants[0]
}

/**
 * List of russian words grouped by
 * usage context
 */
val RUSSIAN_NUMBERS = listOf(
        listOf(
                Word("один", "одна"),
                Word("два", "две"),
                Word("три"),
                Word("четыре"),
                Word("пять"),
                Word("шесть"),
                Word("семь"),
                Word("восемь"),
                Word("девять")
        ),
        listOf(
                Word("десять"),
                Word("одиннадцать"),
                Word("двенадцать"),
                Word("тринадцать"),
                Word("четырнадцать"),
                Word("пятнадцать"),
                Word("шестнадцать"),
                Word("семнадцать"),
                Word("восемнадцать"),
                Word("девятнадцать")
        ),
        listOf(
                Word("двадцать"),
                Word("тридцать"),
                Word("сорок"),
                Word("пятьдесят"),
                Word("шестьдесят"),
                Word("семьдесят"),
                Word("восемьдесят"),
                Word("девяносто")
        ),
        listOf(
                Word("сто"),
                Word("двести"),
                Word("триста"),
                Word("четыреста"),
                Word("пятьсот"),
                Word("шестьсот"),
                Word("семьсот"),
                Word("восемьсот"),
                Word("девятьсот")
        )
)

/**
 * Translates a digit triad into russian number
 * word. Variant describes the word form the number
 * is presented in (двА букета, двЕ кружки)
 *
 * @param n the target number
 * @param variant 0 for 'just a number', 1 for 'тысяч'
 */
fun parseTriad(n: Int, variant: Int): MutableList<String> {
    val thirdDigit = n / 100 % 10
    val secondDigit = n / 10 % 10
    val firstDigit = n % 10
    var addFirstDigit = true
    val result = mutableListOf<String>()

    // should add 'hundreds'
    if (thirdDigit != 0)
        result.add(RUSSIAN_NUMBERS[3][thirdDigit - 1][0])

    // should add 'tens'
    if (secondDigit != 0) {
        if (secondDigit == 1) {
            result.add(RUSSIAN_NUMBERS[1][firstDigit][0])
            addFirstDigit = false
        } else {
            result.add(RUSSIAN_NUMBERS[2][secondDigit - 2][0])
        }
    }

    // should add 'ones'
    if (addFirstDigit && firstDigit != 0)
        result.add(RUSSIAN_NUMBERS[0][firstDigit - 1][variant])

    return result
}

/**
 * Очень сложная
 *
 * Записать заданное натуральное число 1..999999 прописью по-русски.
 * Например, 375 = "триста семьдесят пять",
 * 23964 = "двадцать три тысячи девятьсот шестьдесят четыре"
 */
fun russian(n: Int): String {
    val left = parseTriad(n / 1000, 1)
    val right = parseTriad(n, 0)
    val leftFirstDigit = n / 1000 % 10
    val leftSecondDigit = n / 10000 % 10

    // I split the word as
    //     XXX YYY
    //    left right

    if (left.isNotEmpty()) {
        val middle = when {
            leftSecondDigit == 1 -> "тысяч"
            leftFirstDigit == 1 -> "тысяча"
            leftFirstDigit in 2..4 -> "тысячи"
            else -> "тысяч"
        }
        return (left + middle + right).joinToString(" ")
    }

    return right.joinToString(" ")
}