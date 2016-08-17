package quoteParser.features

/**
 * Created by Pavel.Zhuk on 16.08.2016.
 */
class DateFeature(name: String) : AbstractQuoteFeature(name) {
    override fun getRegex() =
            Regex("(.*\\s)?((([0-3]?[0-9][\\.,]{0,2}\\s+)(\\S+\\s+)?(\\S+\\s+)?(20\\d\\d[\\.,]{0,2}))|" + // full date
                    "((20\\d\\d[\\.,]{0,2}\\s+)(\\S+\\s+)?(\\S+\\s+)?([0-3]?[0-9][\\.,]{0,2}))|" +

                    "((([0-3]?[0-9][/.-][0-3]?[0-9][/.-](?:[0-9]{2})?[0-9]{2})|" + // short date
                    "((?:[0-9]{2})?[0-9]{2}[/.-][0-3]?[0-9][/.-][0-3]?[0-9]))[,\\.]?:?" +
                    "))(\\s.*)?")

}