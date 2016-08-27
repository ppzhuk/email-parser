package quoteParser.features

/**
 * Created by Pavel.Zhuk on 24.08.2016.
 */
enum class QuoteMarkMatchingResult() {
    V_EMPTY,
    V_NON_EMPTY,
    EMPTY,
    NON_EMPTY;

    fun hasQuoteMark() = this == V_EMPTY || this == V_NON_EMPTY
    fun isEmpty() = this == EMPTY
    fun isTextWithoutQuoteMark() = this == NON_EMPTY
}

class QuoteMarkFeature() : AbstractQuoteFeature() {
    override val name: String
        get() = "QUOTE_MARK"

    override fun getRegex(): Regex {
        // Regex for matching greater-than(>) symbol in the start of the line.
        return Regex("[\\s\\p{C}\\p{Z}]*>.*")
    }

    fun matchLines(lines: List<String>): List<QuoteMarkMatchingResult> {
        return lines.map {
            val matchesQuoteMark = this.matches(it)
            val containText: Boolean
            if (matchesQuoteMark) {
                val quoteMarkIndex = it.indexOfFirst { it == '>' }
                if (quoteMarkIndex == -1) {
                    throw IllegalStateException("Line \"$it\" must contain '>' symbol, but it is not.")
                }
                containText = !it.substring(quoteMarkIndex + 1).trim().isEmpty()
            } else {
                containText = !it.trim().isEmpty()
            }
            return@map when {
                matchesQuoteMark && containText -> QuoteMarkMatchingResult.V_NON_EMPTY
                matchesQuoteMark && !containText -> QuoteMarkMatchingResult.V_EMPTY
                !matchesQuoteMark && containText -> QuoteMarkMatchingResult.NON_EMPTY
                else -> QuoteMarkMatchingResult.EMPTY
            }
        }
    }
}