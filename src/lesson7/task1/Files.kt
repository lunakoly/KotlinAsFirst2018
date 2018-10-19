@file:Suppress("UNUSED_PARAMETER", "ConvertCallChainIntoSequence")

package lesson7.task1

import java.io.File
import kotlin.math.max

/**
 * Пример
 *
 * Во входном файле с именем inputName содержится некоторый текст.
 * Вывести его в выходной файл с именем outputName, выровняв по левому краю,
 * чтобы длина каждой строки не превосходила lineLength.
 * Слова в слишком длинных строках следует переносить на следующую строку.
 * Слишком короткие строки следует дополнять словами из следующей строки.
 * Пустые строки во входном файле обозначают конец абзаца,
 * их следует сохранить и в выходном файле
 */
fun alignFile(inputName: String, lineLength: Int, outputName: String) {
    val outputStream = File(outputName).bufferedWriter()
    var currentLineLength = 0
    for (line in File(inputName).readLines()) {
        if (line.isEmpty()) {
            outputStream.newLine()
            if (currentLineLength > 0) {
                outputStream.newLine()
                currentLineLength = 0
            }
            continue
        }
        for (word in line.split(" ")) {
            if (currentLineLength > 0) {
                if (word.length + currentLineLength >= lineLength) {
                    outputStream.newLine()
                    currentLineLength = 0
                }
                else {
                    outputStream.write(" ")
                    currentLineLength++
                }
            }
            outputStream.write(word)
            currentLineLength += word.length
        }
    }
    outputStream.close()
}

/**
 * Средняя
 *
 * Во входном файле с именем inputName содержится некоторый текст.
 * На вход подаётся список строк substrings.
 * Вернуть ассоциативный массив с числом вхождений каждой из строк в текст.
 * Регистр букв игнорировать, то есть буквы е и Е считать одинаковыми.
 *
 */
fun countSubstrings(inputName: String, substrings: List<String>): Map<String, Int> {
    val text = File(inputName).readText()
    return substrings
            .groupBy { it }
            .mapValues { it.key.toRegex(RegexOption.IGNORE_CASE).findAll(text).toSet().size }
}

/**
 * Средняя
 *
 * В русском языке, как правило, после букв Ж, Ч, Ш, Щ пишется И, А, У, а не Ы, Я, Ю.
 * Во входном файле с именем inputName содержится некоторый текст на русском языке.
 * Проверить текст во входном файле на соблюдение данного правила и вывести в выходной
 * файл outputName текст с исправленными ошибками.
 *
 * Регистр заменённых букв следует сохранять.
 *
 * Исключения (жюри, брошюра, парашют) в рамках данного задания обрабатывать не нужно
 *
 */
fun sibilants(inputName: String, outputName: String) {
    // it's VERY STRANGE to replace "жа" with "жя"
    val text = File(inputName).readText()

    File(outputName)
            .writeText(
                text.replace(Regex("""([жчшщ])([ыяю])""", RegexOption.IGNORE_CASE)) {
                    val char = it.groupValues[2][0]

                    it.groupValues[1] + when (char.toLowerCase()) {
                        'ы' -> char - 19
                        'я' -> char - 31
                        'ю' -> char - 11
                        else -> ""
                    }
                }
            )
}

/**
 * Средняя
 *
 * Во входном файле с именем inputName содержится некоторый текст (в том числе, и на русском языке).
 * Вывести его в выходной файл с именем outputName, выровняв по центру
 * относительно самой длинной строки.
 *
 * Выравнивание следует производить путём добавления пробелов в начало строки.
 *
 *
 * Следующие правила должны быть выполнены:
 * 1) Пробелы в начале и в конце всех строк не следует сохранять.
 * 2) В случае невозможности выравнивания строго по центру, строка должна быть сдвинута в ЛЕВУЮ сторону
 * 3) Пустые строки не являются особым случаем, их тоже следует выравнивать
 * 4) Число строк в выходном файле должно быть равно числу строк во входном (в т. ч. пустых)
 *
 */
fun centerFile(inputName: String, outputName: String) {
    val lines = File(inputName).readLines().map { it.trim() }
    val maxLength = lines.map { it.length }.max() ?: 0

    File(outputName)
            .writeText(
                    lines.joinToString("\n") {
                        " ".repeat((maxLength - it.length) / 2) + it
                    }
            )
}

/**
 * Сложная
 *
 * Во входном файле с именем inputName содержится некоторый текст (в том числе, и на русском языке).
 * Вывести его в выходной файл с именем outputName, выровняв по левому и правому краю относительно
 * самой длинной строки.
 * Выравнивание производить, вставляя дополнительные пробелы между словами: равномерно по всей строке
 *
 * Слова внутри строки отделяются друг от друга одним или более пробелом.
 *
 * Следующие правила должны быть выполнены:
 * 1) Каждая строка входного и выходного файла не должна начинаться или заканчиваться пробелом.
 * 2) Пустые строки или строки из пробелов трансформируются в пустые строки без пробелов.
 * 3) Строки из одного слова выводятся без пробелов.
 * 4) Число строк в выходном файле должно быть равно числу строк во входном (в т. ч. пустых).
 *
 * Равномерность определяется следующими формальными правилами:
 * 5) Число пробелов между каждыми двумя парами соседних слов не должно отличаться более, чем на 1.
 * 6) Число пробелов между более левой парой соседних слов должно быть больше или равно числу пробелов
 *    между более правой парой соседних слов.
 *
 * Следует учесть, что входной файл может содержать последовательности из нескольких пробелов  между слвоами. Такие
 * последовательности следует учитывать при выравнивании и при необходимости избавляться от лишних пробелов.
 * Из этого следуют следующие правила:
 * 7) В самой длинной строке каждая пара соседних слов должна быть отделена В ТОЧНОСТИ одним пробелом
 * 8) Если входной файл удовлетворяет требованиям 1-7, то он должен быть в точности идентичен выходному файлу
 */
fun alignFileByWidth(inputName: String, outputName: String) {
    val lines = File(inputName).readLines()
            .map { it.trim().replace(Regex("""\s+"""), " ") }

    val maxLength = lines
            .map { it.length }
            .max() ?: 0

    File(outputName)
            .writeText(
                    lines.joinToString("\n") {
                        val words = it.split(" ")

                        if (words.size <= 1)
                            return@joinToString it

                        var contentSpaceLeft = words.fold(0) { old, new -> old + new.length } - words.last().length
                        var freeSpaceLeft = maxLength - words.last().length
                        var out = words.last()

                        // word word word word
                        //               |____|
                        //                read
                        //|______________|
                        // 2 spaces for 3 words left

                        for (i in words.size - 2 downTo 0) {
                            // i + 1 = wordsLeft - 1
                            val spacesPerPair = (freeSpaceLeft - contentSpaceLeft) / (i + 1)

                            freeSpaceLeft -= words[i].length + spacesPerPair
                            contentSpaceLeft -= words[i].length
                            out = words[i] + " ".repeat(spacesPerPair) + out
                        }

                        out
                    }
            )
}

/**
 * Средняя
 *
 * Во входном файле с именем inputName содержится некоторый текст (в том числе, и на русском языке).
 *
 * Вернуть ассоциативный массив, содержащий 20 наиболее часто встречающихся слов с их количеством.
 * Если в тексте менее 20 различных слов, вернуть все слова.
 *
 * Словом считается непрерывная последовательность из букв (кириллических,
 * либо латинских, без знаков препинания и цифр).
 * Цифры, пробелы, знаки препинания считаются разделителями слов:
 * Привет, привет42, привет!!! -привет?!
 * ^ В этой строчке слово привет встречается 4 раза.
 *
 * Регистр букв игнорировать, то есть буквы е и Е считать одинаковыми.
 * Ключи в ассоциативном массиве должны быть в нижнем регистре.
 *
 */
fun top20Words(inputName: String): Map<String, Int> {
    val aWord = Regex("""([a-zA-Zа-яА-ЯёЁ]+)""", RegexOption.IGNORE_CASE)

    val words = aWord
            .findAll(File(inputName).readText())
            .groupingBy { it.value.toLowerCase() }
            .eachCount()

    return words.keys
            .sortedByDescending { words[it] }
            .take(20)
            .associateBy({ it }, { words[it] ?: 0 })
}

/**
 * Modulates the letter case according to another string
 * `("thesuperbob", "aaaAaaaaAAA") -> "theSuperBOB"`
 *
 * This function could have been used in sibilants() and
 * transliterate() if they had a bit different task
 * I decided to leave it here in case I'll ever need it
 *
 * @param source the string to be modulated
 * @param pattern the rule for the modulation
 */
@Suppress("unused")
fun modulate(source: String, pattern: String): String {
    return source.mapIndexed { index, it ->
        it + if (index < pattern.length) (pattern[index] - pattern[index].toLowerCase()) else 0
    }.joinToString("")
}

/**
 * Средняя
 *
 * Реализовать транслитерацию текста из входного файла в выходной файл посредством динамически задаваемых правил.

 * Во входном файле с именем inputName содержится некоторый текст (в том числе, и на русском языке).
 *
 * В ассоциативном массиве dictionary содержится словарь, в котором некоторым символам
 * ставится в соответствие строчка из символов, например
 * mapOf('з' to "zz", 'р' to "r", 'д' to "d", 'й' to "y", 'М' to "m", 'и' to "yy", '!' to "!!!")
 *
 * Необходимо вывести в итоговый файл с именем outputName
 * содержимое текста с заменой всех символов из словаря на соответствующие им строки.
 *
 * При этом регистр символов в словаре должен игнорироваться,
 * но при выводе символ в верхнем регистре отображается в строку, начинающуюся с символа в верхнем регистре.
 *
 * Пример.
 * Входной текст: Здравствуй, мир!
 *
 * заменяется на
 *
 * Выходной текст: Zzdrавствуy, mир!!!
 *
 * Пример 2.
 *
 * Входной текст: Здравствуй, мир!
 * Словарь: mapOf('з' to "zZ", 'р' to "r", 'д' to "d", 'й' to "y", 'М' to "m", 'и' to "YY", '!' to "!!!")
 *
 * заменяется на
 *
 * Выходной текст: Zzdrавствуy, mир!!!
 *
 * Обратите внимание: данная функция не имеет возвращаемого значения
 */
fun transliterate(inputName: String, dictionary: Map<Char, String>, outputName: String) {
    val regex = dictionary.keys
            .joinToString("|") { Regex.escape(it.toString()) }
            .toRegex(RegexOption.IGNORE_CASE)

    val lowerCaseDictionary = dictionary
            .mapValues { it.value.toLowerCase() }
            .mapKeys { it.key.toLowerCase() }

    val text = File(inputName).readText().replace(regex) {
        if (it.value.isEmpty())
            return@replace ""

        val value = lowerCaseDictionary[it.value[0].toLowerCase()] ?: ""
        when {
            it.value[0].toLowerCase() == it.value[0] -> value
            value.isNotEmpty() -> value[0].toUpperCase() + value.substring(1)
            else -> ""
        }
    }

    File(outputName).writeText(text)
}

/**
 * Средняя
 *
 * Во входном файле с именем inputName имеется словарь с одним словом в каждой строчке.
 * Выбрать из данного словаря наиболее длинное слово,
 * в котором все буквы разные, например: Неряшливость, Четырёхдюймовка.
 * Вывести его в выходной файл с именем outputName.
 * Если во входном файле имеется несколько слов с одинаковой длиной, в которых все буквы разные,
 * в выходной файл следует вывести их все через запятую.
 * Регистр букв игнорировать, то есть буквы е и Е считать одинаковыми.
 *
 * Пример входного файла:
 * Карминовый
 * Боязливый
 * Некрасивый
 * Остроумный
 * БелогЛазый
 * ФиолетОвый

 * Соответствующий выходной файл:
 * Карминовый, Некрасивый
 *
 * Обратите внимание: данная функция не имеет возвращаемого значения
 */
fun chooseLongestChaoticWord(inputName: String, outputName: String) {
    val words = mutableListOf<String>()
    var maxLength = -1

    File(inputName).readLines().forEach {
        val repetitions = mutableSetOf<Char>()

        // if has repeating chars
        for (char in it) {
            if (char.toLowerCase() in repetitions)
                return@forEach
            else
                repetitions.add(char.toLowerCase())
        }

        // add
        when {
            it.length > maxLength -> {
                words.clear()
                words.add(it)
                maxLength = it.length
            }

            it.length == maxLength -> {
                words.add(it)
            }

            else -> {}
        }
    }

    File(outputName).writeText(words.joinToString(", "))
}

/**
 * Сложная
 *
 * Реализовать транслитерацию текста в заданном формате разметки в формат разметки HTML.
 *
 * Во входном файле с именем inputName содержится текст, содержащий в себе элементы текстовой разметки следующих типов:
 * - *текст в курсивном начертании* -- курсив
 * - **текст в полужирном начертании** -- полужирный
 * - ~~зачёркнутый текст~~ -- зачёркивание
 *
 * Следует вывести в выходной файл этот же текст в формате HTML:
 * - <i>текст в курсивном начертании</i>
 * - <b>текст в полужирном начертании</b>
 * - <s>зачёркнутый текст</s>
 *
 * Кроме того, все абзацы исходного текста, отделённые друг от друга пустыми строками, следует обернуть в теги <p>...</p>,
 * а весь текст целиком в теги <html><body>...</body></html>.
 *
 * Все остальные части исходного текста должны остаться неизменными с точностью до наборов пробелов и переносов строк.
 * Отдельно следует заметить, что открывающая последовательность из трёх звёздочек (***) должна трактоваться как "<b><i>"
 * и никак иначе.
 *
 * Пример входного файла:
Lorem ipsum *dolor sit amet*, consectetur **adipiscing** elit.
Vestibulum lobortis, ~~Est vehicula rutrum *suscipit*~~, ipsum ~~lib~~ero *placerat **tortor***,

Suspendisse ~~et elit in enim tempus iaculis~~.
 *
 * Соответствующий выходной файл:
<html>
    <body>
        <p>
            Lorem ipsum <i>dolor sit amet</i>, consectetur <b>adipiscing</b> elit.
            Vestibulum lobortis. <s>Est vehicula rutrum <i>suscipit</i></s>, ipsum <s>lib</s>ero <i>placerat <b>tortor</b></i>.
        </p>
        <p>
            Suspendisse <s>et elit in enim tempus iaculis</s>.
        </p>
    </body>
</html>
 *
 * (Отступы и переносы строк в примере добавлены для наглядности, при решении задачи их реализовывать не обязательно)
 */
fun markdownToHtmlSimple(inputName: String, outputName: String) {
    val result = MarkdownReader(File(inputName).readText()).toHtml()
    File(outputName).writeText(result)
}

/**
 * Сложная
 *
 * Реализовать транслитерацию текста в заданном формате разметки в формат разметки HTML.
 *
 * Во входном файле с именем inputName содержится текст, содержащий в себе набор вложенных друг в друга списков.
 * Списки бывают двух типов: нумерованные и ненумерованные.
 *
 * Каждый элемент ненумерованного списка начинается с новой строки и символа '*', каждый элемент нумерованного списка --
 * с новой строки, числа и точки. Каждый элемент вложенного списка начинается с отступа из пробелов, на 4 пробела большего,
 * чем список-родитель. Максимально глубина вложенности списков может достигать 6. "Верхние" списки файла начинются
 * прямо с начала строки.
 *
 * Следует вывести этот же текст в выходной файл в формате HTML:
 * Нумерованный список:
 * <ol>
 *     <li>Раз</li>
 *     <li>Два</li>
 *     <li>Три</li>
 * </ol>
 *
 * Ненумерованный список:
 * <ul>
 *     <li>Раз</li>
 *     <li>Два</li>
 *     <li>Три</li>
 * </ul>
 *
 * Кроме того, весь текст целиком следует обернуть в теги <html><body>...</body></html>
 *
 * Все остальные части исходного текста должны остаться неизменными с точностью до наборов пробелов и переносов строк.
 *
 * Пример входного файла:
///////////////////////////////начало файла/////////////////////////////////////////////////////////////////////////////
* Утка по-пекински
    * Утка
    * Соус
* Салат Оливье
    1. Мясо
        * Или колбаса
    2. Майонез
    3. Картофель
    4. Что-то там ещё
* Помидоры
* Фрукты
    1. Бананы
    23. Яблоки
        1. Красные
        2. Зелёные
///////////////////////////////конец файла//////////////////////////////////////////////////////////////////////////////
 *
 *
 * Соответствующий выходной файл:
///////////////////////////////начало файла/////////////////////////////////////////////////////////////////////////////
<html>
  <body>
    <ul>
      <li>
        Утка по-пекински
        <ul>
          <li>Утка</li>
          <li>Соус</li>
        </ul>
      </li>
      <li>
        Салат Оливье
        <ol>
          <li>Мясо
            <ul>
              <li>
                  Или колбаса
              </li>
            </ul>
          </li>
          <li>Майонез</li>
          <li>Картофель</li>
          <li>Что-то там ещё</li>
        </ol>
      </li>
      <li>Помидоры</li>
      <li>
        Яблоки
        <ol>
          <li>Красные</li>
          <li>Зелёные</li>
        </ol>
      </li>
    </ul>
  </body>
</html>
///////////////////////////////конец файла//////////////////////////////////////////////////////////////////////////////
 * (Отступы и переносы строк в примере добавлены для наглядности, при решении задачи их реализовывать не обязательно)
 */
fun markdownToHtmlLists(inputName: String, outputName: String) {
    val result = MarkdownReader(File(inputName).readText()).toHtml()
    File(outputName).writeText(result)
}

open class TextReader(protected var source: String) {
    protected var index = 0

    protected fun failAcceptation(backup: Int): Boolean {
        index = backup
        return false
    }

    protected fun accept(token: String): Boolean {
        token.forEachIndexed { offset, it ->
            if (source.getOrNull(index + offset) != it)
                return false
        }
        index += token.length
        return true
    }
}

class MarkdownReader(source: String): TextReader(source) {
    private var lastReadIndent = 0
    private var lastReadNumber = 0

    init {
        // dos2unix
        this.source = source.replace("\r\n", "\n")
    }

    private fun readPrimitive(terminator: String): String {
        var result = ""
        while (index < source.length) {
            // this is the ideal solution from my point of view
            // because if can automatically handle *** as
            // b,i or i,b depending on the context (whether
            // we need to close text or open)
            // but the task said *** -> b,i ((9(

//            result += when {
//                accept(terminator) -> return result
//                accept("**") -> "<b>" + readPrimitive("**") + "</b>"
//                accept("~~") -> "<s>" + readPrimitive("~~") + "</s>"
//                accept("*") -> "<i>" + readPrimitive("*") + "</i>"
//                else -> source[index++]
//            }

            result += when {
                accept("**") -> {
                    if ("**" == terminator)
                        return result
                    "<b>" + readPrimitive("**") + "</b>"
                }
                accept("~~") -> {
                    if ("~~" == terminator)
                        return result
                    "<s>" + readPrimitive("~~") + "</s>"
                }
                accept("*") -> {
                    if ("*" == terminator)
                        return result
                    "<i>" + readPrimitive("*") + "</i>"
                }
                else -> source[index++]
            }
        }
        return result
    }

    private fun acceptNumber(): Boolean {
        var isNumber = false
        var number = 0

        while (index < source.length && source[index] in '0'..'9') {
            number = number * 10 + (source[index] - '0')
            isNumber = true
            index++
        }

        if (!isNumber)
            return false

        lastReadNumber = number
        return true
    }

    private fun acceptUlList(): Boolean {
        val backup = index
        var indent = 0

        while (index < source.length && accept(" "))
            indent += 1

        if (!accept("*"))
            return failAcceptation(backup)

        lastReadIndent = indent
        return true
    }

    private fun acceptOlList(): Boolean {
        val backup = index
        var indent = 0

        while (index < source.length && accept(" "))
            indent += 1

        if (!acceptNumber())
            return failAcceptation(backup)

        if (!accept("."))
            return failAcceptation(backup)

        lastReadIndent = indent
        return true
    }

    private fun readList(type: String, level: Int): String {
        var previous = '\n'
        var result = ""

        while (index < source.length) {
            val backup = index

            result += when {
                previous == '\n' && acceptUlList() -> {
                    when {
                        lastReadIndent == level && type == "*" -> "</li><li>"
                        lastReadIndent < level -> {
                            // return caret back before list item
                            // in order to let higher acceptor
                            // read it again :/
                            failAcceptation(backup)
                            return result
                        }
                        else -> "<ul><li>" + readList("*", lastReadIndent) + "</li></ul>"
                    }
                }
                previous == '\n' && acceptOlList() -> {
                    when {
                        lastReadIndent == level && type == "." -> "</li><li>"
                        lastReadIndent < level -> {
                            failAcceptation(backup)
                            return result
                        }
                        else -> "<ol><li>" + readList(".", lastReadIndent) + "</li></ol>"
                    }
                }
                accept("\n\n") -> {
                    failAcceptation(backup)
                    return result
                }
                else -> source[index++]
            }
            previous = source[index - 1]
        }

        return result
    }

    private fun readParagraph(): String {
        var result = ""

        while (index < source.length) {
            result += when {
                accept("\n\n") -> return result + "</p><p>" + readParagraph()
                accept("~~") -> "<s>" + readPrimitive("~~") + "</s>"
                accept("**") -> "<b>" + readPrimitive("**") + "</b>"
                accept("*") -> "<i>" + readPrimitive("*") + "</i>"
                else -> source[index++]
            }
        }

        return result
    }

    private fun readListOrParagraph(): String {
        var optionalPAtStart = ""
        var optionalPAtEnd = ""
        var result = ""

        while (index < source.length) {
            result += when {
                acceptUlList() -> {
                    if (result.isNotEmpty()) {
                        optionalPAtStart = "<p>"
                        optionalPAtEnd = "</p>"
                        result += "</p><p>"
                    }

                    "<ul><li>" + readList("*", lastReadIndent) + "</li></ul>"
                }
                acceptOlList() -> {
                    if (result.isNotEmpty()) {
                        optionalPAtStart = "<p>"
                        optionalPAtEnd = "</p>"
                        result += "</p><p>"
                    }

                    "<ol><li>" + readList(".", lastReadIndent) + "</li></ol>"
                }
                accept("\n\n") -> "</p><p>"
                else -> "<p>" + readParagraph() + "</p>"
            }
        }

        return optionalPAtStart + result + optionalPAtEnd
    }

    fun toHtml(): String {
        var content = readListOrParagraph()

        /*
        very dirty thing(
        but it's required for markdownToHtmlSimple to complete
        the tests like "<html><body><p></p></body></html>".
        actually this task can be improoved and I wrote Marat about it
        */

        if (content.isEmpty())
            content = "<p>$content</p>"

        return "<html><body>$content</body></html>"
    }
}

/**
 * Очень сложная
 *
 * Реализовать преобразования из двух предыдущих задач одновременно над одним и тем же файлом.
 * Следует помнить, что:
 * - Списки, отделённые друг от друга пустой строкой, являются разными и должны оказаться в разных параграфах выходного файла.
 *
 */
fun markdownToHtml(inputName: String, outputName: String) {
    val result = MarkdownReader(File(inputName).readText())
    File(outputName).writeText(result.toHtml())
}

/**
 * Средняя
 *
 * Вывести в выходной файл процесс умножения столбиком числа lhv (> 0) на число rhv (> 0).
 *
 * Пример (для lhv == 19935, rhv == 111):
   19935
*    111
--------
   19935
+ 19935
+19935
--------
 2212785
 * Используемые пробелы, отступы и дефисы должны в точности соответствовать примеру.
 * Нули в множителе обрабатывать так же, как и остальные цифры:
  235
*  10
-----
    0
+235
-----
 2350
 *
 */
fun printMultiplicationProcess(lhv: Int, rhv: Int, outputName: String) {
    val answer = lhv.toLong() * rhv
    val maxLengthNumber = max(max(lhv, rhv).toLong(), answer)
    val maxLength = maxLengthNumber.toString().length + 1
    val separator = "-".repeat(maxLength) + '\n'
    val rhvString = rhv.toString()

    println(maxLength)

    // upper part
    var result = alignLine(lhv.toString(), maxLength)
    result += '*' + alignLine(rhvString, maxLength - 1)
    result += separator

    // subroutine for middle block lines
    // index starts with 0 from the right
    fun generateFactorLine(digitIndex: Int): String {
        val digit = rhvString[rhvString.length - 1 - digitIndex] - '0'
        val factor = digit * lhv
        return " ".repeat(maxLength - factor.toString().length - 1 - digitIndex) + factor.toString()
    }

    // first line without +
    result += ' ' + generateFactorLine(0) + '\n'
    for (it in 1 until rhv.toString().length)
        result += '+' + generateFactorLine(it) + '\n'
    result += separator

    // there will be \n at the end, so trim it
    result += alignLine(answer.toString(), maxLength)
    File(outputName).writeText(result.trimEnd())
}

/**
 * Returns string with length >= `minWidth`
 * and with `\n` at the end
 * @param data the content to be aligned
 * @param minWidth the required width
 */
fun alignLine(data: String, minWidth: Int) = String.format("%${minWidth}s", data) + '\n'


/**
 * Сложная
 *
 * Вывести в выходной файл процесс деления столбиком числа lhv (> 0) на число rhv (> 0).
 *
 * Пример (для lhv == 19935, rhv == 22):
  19935 | 22
 -198     906
 ----
    13
    -0
    --
    135
   -132
   ----
      3

 * Используемые пробелы, отступы и дефисы должны в точности соответствовать примеру.
 *
 */
fun printDivisionProcess(lhv: Int, rhv: Int, outputName: String) {
    val result = mutableListOf(" $lhv | $rhv")
    val lhvString = lhv.toString()
    var printingAllowed = false
    var answer = ""
    var indent = 1

    fun generateIndent(indent: Int) = " ".repeat(indent)

    var minuend = ""
    var minuendSearchingIndex = 0

    while (minuendSearchingIndex < lhvString.length) {
        // trailing 0 at the beginning can be trimmed later :)
        minuend += lhvString[minuendSearchingIndex]
        minuendSearchingIndex++

        if (printingAllowed)
            result.add(generateIndent(indent) + minuend)    // automatic toString()

        // and... Action!
        val answerDigit = minuend.toInt() / rhv
        val nearest = (rhv * answerDigit).toString()
        answer += answerDigit

        // allow print everything
        if (answerDigit != 0)
            printingAllowed = true

        // save old indent because ---separator---
        // might need it
        val oldIndent = indent
        // -1 for '-'
        indent += minuend.length - nearest.length - 1

        // -value and ---separator---
        if (printingAllowed || minuendSearchingIndex >= lhvString.length) {
            result.add(generateIndent(indent) + "-$nearest")
            // nearest is shorter than minuend
            if (nearest.length + 1 >= minuend.length)
                result.add(generateIndent(indent) + "-".repeat(nearest.length + 1))
            else
                result.add(generateIndent(oldIndent) + "-".repeat(minuend.length))
        }

        // no '-' needed to print so leave +1
        val difference = (minuend.toInt() - nearest.toInt()).toString()
        indent += 1 + nearest.length - difference.length

        // if we can't proceed calculations
        // display the result. The next while check will
        // fail and no minuend will be print
        if (minuendSearchingIndex >= lhvString.length)
            result.add(generateIndent(indent) + difference)

        minuend = difference
    }

    val spaceLength = 4 + lhvString.length - result[1].length
    result[1] = result[1] + " ".repeat(spaceLength) + answer.toInt()
    File(outputName).writeText(result.joinToString("\n"))
}

