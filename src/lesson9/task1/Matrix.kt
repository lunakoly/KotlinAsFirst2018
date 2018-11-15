@file:Suppress("UNUSED_PARAMETER", "unused")
package lesson9.task1

/**
 * Ячейка матрицы: row = ряд, column = колонка
 */
data class Cell(val row: Int, val column: Int)

/**
 * Интерфейс, описывающий возможности матрицы. E = тип элемента матрицы
 */
interface Matrix<E> {
    /** Высота */
    val height: Int

    /** Ширина */
    val width: Int

    /**
     * Доступ к ячейке.
     * Методы могут бросить исключение, если ячейка не существует или пуста
     */
    operator fun get(row: Int, column: Int): E
    operator fun get(cell: Cell): E

    /**
     * Запись в ячейку.
     * Методы могут бросить исключение, если ячейка не существует
     */
    operator fun set(row: Int, column: Int, value: E)
    operator fun set(cell: Cell, value: E)
}

/**
 * Простая
 *
 * Метод для создания матрицы, должен вернуть РЕАЛИЗАЦИЮ Matrix<E>.
 * height = высота, width = ширина, e = чем заполнить элементы.
 * Бросить исключение IllegalArgumentException, если height или width <= 0.
 */
fun <E> createMatrix(height: Int, width: Int, e: E): Matrix<E> {
    if (height <= 0 || width <= 0)
        throw IllegalArgumentException()
    return MatrixImpl(width, height, e)
}

/**
 * Средняя сложность
 *
 * Реализация интерфейса "матрица"
 */
class MatrixImpl<E>(override val width: Int, override val height: Int, initialValue: E) : Matrix<E> {
    private val contents = MutableList(width * height) { initialValue }

    override fun get(row: Int, column: Int): E {
        if (row !in 0 until height || column !in 0 until width)
            throw IllegalArgumentException("Requested cell ($row, $column) is not inside of the matrix")
        return contents[column + row * width]
    }

    override fun get(cell: Cell) = get(cell.row, cell.column)

    override fun set(row: Int, column: Int, value: E) {
        if (row !in 0 until height || column !in 0 until width)
            throw IllegalArgumentException("Requested cell ($row, $column) is not inside of the matrix")
        contents[column + row * width] = value
    }

    override fun set(cell: Cell, value: E) = set(cell.row, cell.column, value)

    // May I write here '= other is Matrix<*> && other.hashCode() == hashCode()'?
    // And make all possible matrix implementations return the same hashCode if equal
    override fun equals(other: Any?): Boolean {
        if (other !is Matrix<*> || other.height != height || other.width != width)
            return false

        for (row in 0 until height)
            for (column in 0 until width)
                if (get(row, column) != other[row, column])
                    return false

        return true
    }

    override fun hashCode(): Int {
        var result = width
        result = 31 * result + height
        result = 31 * result + contents.hashCode()
        return result
    }

    override fun toString(): String {
        val result = StringBuilder()

        for (row in 0 until height) {
            result.append('|')
            for (column in 0 until width)
                result.append(' ' + get(row, column).toString())
            result.append(" |\n")
        }

        return result.trimEnd().toString()
    }
}

