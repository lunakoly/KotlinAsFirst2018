@file:Suppress("UNUSED_PARAMETER", "ConvertCallChainIntoSequence")

package lesson6.task1

import lesson2.task2.daysInMonth
import lesson4.task1.ROMAN_SIGNS
import java.lang.Math.pow

/**
 * Пример
 *
 * Время представлено строкой вида "11:34:45", содержащей часы, минуты и секунды, разделённые двоеточием.
 * Разобрать эту строку и рассчитать количество секунд, прошедшее с начала дня.
 */
fun timeStrToSeconds(str: String): Int {
    val parts = str.split(":")
    var result = 0
    for (part in parts) {
        val number = part.toInt()
        result = result * 60 + number
    }
    return result
}

/**
 * Пример
 *
 * Дано число n от 0 до 99.
 * Вернуть его же в виде двухсимвольной строки, от "00" до "99"
 */
fun twoDigitStr(n: Int) = if (n in 0..9) "0$n" else "$n"

/**
 * Пример
 *
 * Дано seconds -- время в секундах, прошедшее с начала дня.
 * Вернуть текущее время в виде строки в формате "ЧЧ:ММ:СС".
 */
fun timeSecondsToStr(seconds: Int): String {
    val hour = seconds / 3600
    val minute = (seconds % 3600) / 60
    val second = seconds % 60
    return String.format("%02d:%02d:%02d", hour, minute, second)
}

/**
 * Пример: консольный ввод
 */
fun main(args: Array<String>) {
    println("Введите время в формате ЧЧ:ММ:СС")
    val line = readLine()
    if (line != null) {
        val seconds = timeStrToSeconds(line)
        if (seconds == -1) {
            println("Введённая строка $line не соответствует формату ЧЧ:ММ:СС")
        }
        else {
            println("Прошло секунд с начала суток: $seconds")
        }
    }
    else {
        println("Достигнут <конец файла> в процессе чтения строки. Программа прервана")
    }
}

/**
 * Returns a list of values of the
 * requested type extracted from the source
 *
 * @param source the string source
 */
inline fun <reified T> extract(source: String, delimiter: Char = ' '): List<T> {
    // remove tokens that can't be cast to the
    // requested type
    val tokens = source
            .split(delimiter)
            .filter {
                when {
                    it.toIntOrNull() != null -> Int::class == T::class
                    it.toDoubleOrNull() != null-> Double::class == T::class
                    else -> String::class == T::class
                }
            }

    // map values
    return when {
        Int::class == T::class -> tokens.map { it.toInt() as T }
        Double::class == T::class -> tokens.map { it.toDouble() as T }
        String::class == T::class -> tokens.map { it as T }
        else -> listOf()
    }
}

val MONTHS = listOf(
        "января", "февраля", "марта", "апреля",
        "мая", "июня", "июля", "августа",
        "сентября", "октябра", "ноября", "декабря")

/**
 * Средняя
 *
 * Дата представлена строкой вида "15 июля 2016".
 * Перевести её в цифровой формат "15.07.2016".
 * День и месяц всегда представлять двумя цифрами, например: 03.04.2011.
 * При неверном формате входной строки вернуть пустую строку.
 *
 * Обратите внимание: некорректная с точки зрения календаря дата (например, 30.02.2009) считается неверными
 * входными данными.
 */
fun dateStrToDigit(str: String): String {
    return try {
        if (!Regex("^[0-9]{1,2} \\S+ [0-9]{1,4}$").containsMatchIn(str))
            throw Exception()

        val (day, year) = extract<Int>(str)
        val (monthName) = extract<String>(str)

        val monthNumber = MONTHS.indexOf(monthName) + 1
        if (monthNumber !in 1..12) throw Exception()

        val maxDay = daysInMonth(monthNumber, year)
        if (day > maxDay) throw Exception()

        String.format("%02d.%02d.%d", day, monthNumber, year)
    } catch (e: Exception) { "" }
}

/**
 * Средняя
 *
 * Дата представлена строкой вида "15.07.2016".
 * Перевести её в строковый формат вида "15 июля 2016".
 * При неверном формате входной строки вернуть пустую строку
 *
 * Обратите внимание: некорректная с точки зрения календаря дата (например, 30 февраля 2009) считается неверными
 * входными данными.
 */
fun dateDigitToStr(digital: String): String {
    return try {
        if (!Regex("^[0-9]{2}.[0-9]{2}.[0-9]{1,4}$").containsMatchIn(digital))
            throw Exception()

        val (day, month, year) = extract<Int>(digital, '.')
        val maxDay = daysInMonth(month, year)

        if (day > maxDay || month !in 1..12)
            throw Exception()

        String.format("%d %s %d", day, MONTHS[month - 1], year)
    } catch (e: Exception) { "" }
}

/**
 * Средняя
 *
 * Номер телефона задан строкой вида "+7 (921) 123-45-67".
 * Префикс (+7) может отсутствовать, код города (в скобках) также может отсутствовать.
 * Может присутствовать неограниченное количество пробелов и чёрточек,
 * например, номер 12 --  34- 5 -- 67 -98 тоже следует считать легальным.
 * Перевести номер в формат без скобок, пробелов и чёрточек (но с +), например,
 * "+79211234567" или "123456789" для приведённых примеров.
 * Все символы в номере, кроме цифр, пробелов и +-(), считать недопустимыми.
 * При неверном формате вернуть пустую строку
 */
fun flattenPhoneNumber(phone: String): String {
    try {
        var closingBracketExpected = false
        val first = phone.firstOrNull()

        var number = when (first) {
            '+' -> "+"
            in '0'..'9' -> first.toString()
            else -> throw Exception()
        }

        for (it in 1 until phone.length) {
            when (phone[it]) {
                '(' -> {
                    closingBracketExpected = true
                }

                ')' -> {
                    if (!closingBracketExpected)
                        throw Exception()
                    closingBracketExpected = false
                }

                in '0'..'9' -> {
                    number += phone[it]
                }

                '-' -> {}
                ' ' -> {}
                else -> throw Exception("Met: " + phone[it] + ", when " + number)
            }
        }

        return number
    } catch (e: Exception) {
        return ""
    }
}

/**
 * Средняя
 *
 * Результаты спортсмена на соревнованиях в прыжках в длину представлены строкой вида
 * "706 - % 717 % 703".
 * В строке могут присутствовать числа, черточки - и знаки процента %, разделённые пробелами;
 * число соответствует удачному прыжку, - пропущенной попытке, % заступу.
 * Прочитать строку и вернуть максимальное присутствующее в ней число (717 в примере).
 * При нарушении формата входной строки или при отсутствии в ней чисел, вернуть -1.
 */
fun bestLongJump(jumps: String): Int {
    try {
        var maxJump = -1
        var number = 0

        jumps.forEach {
            when (it) {
                in '0'..'9' -> {
                    number = number * 10 + (it - '0')
                    if (number > maxJump) maxJump = number
                }
                ' ' -> number = 0
                '-' -> number = 0
                '%' -> number = 0
                else -> throw Exception()
            }
        }

        return maxJump
    } catch (e: Exception) {
        return -1
    }
}

/**
 * Сложная
 *
 * Результаты спортсмена на соревнованиях в прыжках в высоту представлены строкой вида
 * "220 + 224 %+ 228 %- 230 + 232 %%- 234 %".
 * Здесь + соответствует удачной попытке, % неудачной, - пропущенной.
 * Высота и соответствующие ей попытки разделяются пробелом.
 * Прочитать строку и вернуть максимальную взятую высоту (230 в примере).
 * При нарушении формата входной строки вернуть -1.
 */
fun bestHighJump(jumps: String): Int {
    try {
        var locked = false
        var maxJump = -1
        var number = 0

        // lock read number until start
        // reading the next one

        jumps.forEach {
            when (it) {
                in '0'..'9' -> {
                    if (locked) {
                        number = 0
                        locked = false
                    }
                    number = number * 10 + (it - '0')
                }
                '+' -> {
                    locked = true
                    if (number > maxJump) maxJump = number
                }
                ' ' -> locked = true
                '-' -> locked = true
                '%' -> locked = true
                else -> throw Exception()
            }
        }

        return maxJump
    } catch (e: Exception) {
        return -1
    }
}

/**
 * Сложная
 *
 * В строке представлено выражение вида "2 + 31 - 40 + 13",
 * использующее целые положительные числа, плюсы и минусы, разделённые пробелами.
 * Наличие двух знаков подряд "13 + + 10" или двух чисел подряд "1 2" не допускается.
 * Вернуть значение выражения (6 для примера).
 * Про нарушении формата входной строки бросить исключение IllegalArgumentException
 */
fun plusMinus(expression: String): Int {
    var result = 0
    var state = "+"

    expression
            .split(' ')
            .forEach {
                when (it) {
                    "+" -> {
                        if (state != "")
                            throw IllegalArgumentException()
                        state = "+"
                    }
                    "-" -> {
                        if (state != "")
                            throw IllegalArgumentException()
                        state = "-"
                    }
                    else -> {
                        // forbid '+n' '-n'
                        if (it.firstOrNull() == '+' || it.firstOrNull() == '-')
                            throw IllegalArgumentException()

                        // try parse Int
                        val operand: Int

                        try {
                            operand = it.toInt()
                        } catch (e: Exception) {
                            throw IllegalArgumentException()
                        }

                        when (state) {
                            "" -> throw IllegalArgumentException()
                            "+" -> result += operand
                            "-" -> result -= operand
                        }
                        state = ""
                    }
                }
            }

    return result
}

/**
 * Сложная
 *
 * Строка состоит из набора слов, отделённых друг от друга одним пробелом.
 * Определить, имеются ли в строке повторяющиеся слова, идущие друг за другом.
 * Слова, отличающиеся только регистром, считать совпадающими.
 * Вернуть индекс начала первого повторяющегося слова, или -1, если повторов нет.
 * Пример: "Он пошёл в в школу" => результат 9 (индекс первого 'в')
 */
fun firstDuplicateIndex(str: String): Int {
    var prevStart = -1
    var prevWord = ""
    var start = -1
    var word = ""

    for (it in 0 until str.length) {
        if (str[it] == ' ') {
            if (word.isNotEmpty()) {
                if (word.toLowerCase() == prevWord)
                    return prevStart

                prevStart = start
                prevWord = word.toLowerCase()
                word = ""
            }
            start = it + 1
        } else {
            word += str[it]
        }
    }

    return prevStart
}

/**
 * Сложная
 *
 * Строка содержит названия товаров и цены на них в формате вида
 * "Хлеб 39.9; Молоко 62; Курица 184.0; Конфеты 89.9".
 * То есть, название товара отделено от цены пробелом,
 * а цена отделена от названия следующего товара точкой с запятой и пробелом.
 * Вернуть название самого дорогого товара в списке (в примере это Курица),
 * или пустую строку при нарушении формата строки.
 * Все цены должны быть больше либо равны нуля.
 */
fun mostExpensive(description: String): String {
    var theMostExpensivePrice = -1.0
    var theMostExpensiveName = ""

    description
            .split(Regex("; "))
            .map {
                it.split(' ').filter { each -> each.isNotEmpty() }
            }
            .forEach {
                if (it.size != 2)
                    return ""

                if (it[1].toDouble() > theMostExpensivePrice) {
                    theMostExpensivePrice = it[1].toDouble()
                    theMostExpensiveName = it[0]
                }
            }

    return theMostExpensiveName
}

/**
 * Сложная
 *
 * Перевести число roman, заданное в римской системе счисления,
 * в десятичную систему и вернуть как результат.
 * Римские цифры: 1 = I, 4 = IV, 5 = V, 9 = IX, 10 = X, 40 = XL, 50 = L,
 * 90 = XC, 100 = C, 400 = CD, 500 = D, 900 = CM, 1000 = M.
 * Например: XXIII = 23, XLIV = 44, C = 100
 *
 * Вернуть -1, если roman не является корректным римским числом
 */
fun fromRoman(roman: String): Int {
    try {
        if (roman.isEmpty())
            throw Exception()

        var result = 0
        var it = 0

        while (it < roman.length) {
            val lookahead = roman.getOrNull(it + 1)
            val index = ROMAN_SIGNS.indexOf(roman[it].toString())
            val isPrimary = index % 2 == 0

            // let's call 'I', 'X', 'C' and 'M' 'primary' ones
            // because they can be used with repetitions

            // test incorrect sign
            if (index == -1)
                throw Exception()

            // I actually test different situations here
            // It's routine
            if (lookahead != null) {
                val lookaheadIndex = ROMAN_SIGNS.indexOf(lookahead.toString())

                // test incorrect sign
                if (lookaheadIndex == -1)
                    throw Exception()

                if (isPrimary) {
                    // big sign after small
                    if (index < lookaheadIndex - 2)
                        throw Exception()

                    when (index) {
                        // like IV
                        lookaheadIndex - 1 -> {
                            result += pow(10.0, (index / 2).toDouble()).toInt() * 4
                            it++
                        }
                        // like IX
                        lookaheadIndex - 2 -> {
                            result += pow(10.0, (index / 2).toDouble()).toInt() * 9
                            it++
                        }
                        // just I
                        else -> result += pow(10.0, (index / 2).toDouble()).toInt()
                    }
                } else {
                    // big sign after small
                    if (index < lookaheadIndex)
                        throw Exception()

                    // just like single V
                    result += pow(10.0, (index / 2).toDouble()).toInt() * 5
                }
            } else {
                // actually the last symbol
                result += pow(10.0, (index / 2).toDouble()).toInt() * if (isPrimary) 1 else 5
            }

            it++
        }

        return result
    } catch (e: Exception) {
        return -1
    }
}

/**
 * Очень сложная
 *
 * Имеется специальное устройство, представляющее собой
 * конвейер из cells ячеек (нумеруются от 0 до cells - 1 слева направо) и датчик, двигающийся над этим конвейером.
 * Строка commands содержит последовательность команд, выполняемых данным устройством, например +>+>+>+>+
 * Каждая команда кодируется одним специальным символом:
 *	> - сдвиг датчика вправо на 1 ячейку;
 *  < - сдвиг датчика влево на 1 ячейку;
 *	+ - увеличение значения в ячейке под датчиком на 1 ед.;
 *	- - уменьшение значения в ячейке под датчиком на 1 ед.;
 *	[ - если значение под датчиком равно 0, в качестве следующей команды следует воспринимать
 *  	не следующую по порядку, а идущую за соответствующей следующей командой ']' (с учётом вложенности);
 *	] - если значение под датчиком не равно 0, в качестве следующей команды следует воспринимать
 *  	не следующую по порядку, а идущую за соответствующей предыдущей командой '[' (с учётом вложенности);
 *      (комбинация [] имитирует цикл)
 *  пробел - пустая команда
 *
 * Изначально все ячейки заполнены значением 0 и датчик стоит на ячейке с номером N/2 (округлять вниз)
 *
 * После выполнения limit команд или всех команд из commands следует прекратить выполнение последовательности команд.
 * Учитываются все команды, в том числе несостоявшиеся переходы ("[" при значении под датчиком не равном 0 и "]" при
 * значении под датчиком равном 0) и пробелы.
 *
 * Вернуть список размера cells, содержащий элементы ячеек устройства после завершения выполнения последовательности.
 * Например, для 10 ячеек и командной строки +>+>+>+>+ результат должен быть 0,0,0,0,0,1,1,1,1,1
 *
 * Все прочие символы следует считать ошибочными и формировать исключение IllegalArgumentException.
 * То же исключение формируется, если у символов [ ] не оказывается пары.
 * Выход за границу конвейера также следует считать ошибкой и формировать исключение IllegalStateException.
 * Считать, что ошибочные символы и непарные скобки являются более приоритетной ошибкой чем выход за границу ленты,
 * то есть если в программе присутствует некорректный символ или непарная скобка, то должно быть выброшено
 * IllegalArgumentException.
 * IllegalArgumentException должен бросаться даже если ошибочная команда не была достигнута в ходе выполнения.
 *
 */
fun computeDeviceCells(cells: Int, commands: String, limit: Int): List<Int> {
    val tape = Array(cells) { 0 }
    var position = cells / 2
    var commandsRead = 0
    var caret = 0


    // testing unpaired brackets separately
    // otherwise test fails
    var bracketTestingStack = 0

    commands.forEach {
        when (it) {
            '[' -> bracketTestingStack++
            ']' -> bracketTestingStack--
        }

        // unexpected ']' met
        if (bracketTestingStack < 0)
            throw IllegalArgumentException()
    }

    // corresponding ']' has not been found
    if (bracketTestingStack > 0)
        throw IllegalArgumentException()


    while (commandsRead < limit && caret < commands.length) {
        val command = commands[caret]

        when (command) {
            ' ' -> { /* chill */ }
            '>' -> position++
            '<' -> position--
            '+' -> tape[position]++
            '-' -> tape[position]--
            '[' -> {
                if (tape[position] == 0) {
                    var stack = 1

                    while (stack > 0) {
                        caret++

                        // I would add here bracket validation
                        // testing instead of the beginning
                        // But the task is the task

                        if (commands[caret] == '[')
                            stack++
                        else if (commands[caret] == ']')
                            stack--
                    }
                }
            }
            ']' -> {
                if (tape[position] != 0) {
                    var stack = 1

                    while (stack > 0) {
                        caret--

                        if (commands[caret] == ']')
                            stack++
                        else if (commands[caret] == '[')
                            stack--
                    }
                }
            }
            else -> throw IllegalArgumentException()
        }

        caret++
        commandsRead++

        // index out of bounds
        if (position < 0 || position >= tape.size)
            throw IllegalStateException()
    }

    return tape.toList()
}
