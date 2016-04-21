package practice.email.parser

import java.util.regex.Pattern

enum class ContentParseMode {
    /**
     * Return plain email body content without any parsing.
     */
    MODE_SIMPLE,

    /**
     * Parse just outer quote and signature.
     */
    MODE_ONE,

    /**
     * Parse all quotes and signatures recursively.
     */
    MODE_DEEP
}

internal fun reverseAndJoin(buffer: Array<String>, bufferLength: Int): String =
        if (bufferLength == 0) {
            ""
        } else {
            val res = buffer.take(bufferLength).toTypedArray()
            res.reverse()
            res.joinToString(separator = "\n")
        }

internal fun getEditingDistance(a: String, b: String): Int {
    val firstTokens = getTokens(a)
    val secondTokens = getTokens(b)

    val n = firstTokens.size + 1;
    val m = secondTokens.size + 1;
    var prev = IntArray(n) { it }
    var curr = IntArray(n)
    for (j in 1..m - 1) {
        curr[0] = j;
        for (i in 1..n - 1) {
            val ins = prev[i] + insCost(secondTokens[j - 1]);
            val del = curr[i - 1] + delCost(firstTokens[i - 1]);
            val repl = prev[i - 1] + replCost(firstTokens[i - 1], secondTokens[j - 1]);
            curr[i] = Math.min(Math.min(ins, del), repl);
        }
        val temp = curr
        curr = prev
        prev = temp
    }
    return prev[n - 1]
}

internal fun getEditingDistance2(a: String, b: String, resA: MutableList<Token>, resB: MutableList<Token>): Int {
    val firstTokens = getTokens(a)
    val secondTokens = getTokens(b)

    var n = firstTokens.size + 1;
    var m = secondTokens.size + 1;
    var d = Array(m) { IntArray(n) { 0 } }
    for (i in d[0].indices) {
        d[0][i] = i
    }

    for (j in 1..m - 1) {
        d[j][0] = j;
        for (i in 1..n - 1) {
            val ins = d[j - 1][i] + insCost(secondTokens[j - 1]);
            val del = d[j][i - 1] + delCost(firstTokens[i - 1]);
            val repl = d[j - 1][i - 1] + replCost(firstTokens[i - 1], secondTokens[j - 1]);
            d[j][i] = Math.min(Math.min(ins, del), repl);
        }
    }

    val x = --n
    val y = --m

    while (n > 0 || m > 0) {
        if  (n == 0) {
            val dash = getDash(secondTokens.elementAt(m-1).text.length)
            resA.add(0, Token(dash))
            resB.add(0, secondTokens.elementAt(m-1))
            m--
        }else if (m == 0) {
            val dash = getDash(firstTokens.elementAt(n-1).text.length)
            resA.add(0, firstTokens.elementAt(n-1))
            resB.add(0, Token(dash))
            n--
        } else {
            val ins = d[m - 1][n] + insCost(secondTokens[m - 1]);
            val del = d[m][n - 1] + delCost(firstTokens[n - 1]);
            val repl = d[m - 1][n - 1] + replCost(firstTokens[n - 1], secondTokens[m - 1]);

            if (ins <= del && ins <= repl) {
                val dash = getDash(secondTokens.elementAt(m-1).text.length)
                resA.add(0, Token(dash))
                resB.add(0, secondTokens.elementAt(m-1))
                m--
            } else {
                if (del <= repl) {
                    val dash = getDash(firstTokens.elementAt(n-1).text.length)
                    resA.add(0, firstTokens.elementAt(n-1))
                    resB.add(0, Token(dash))
                    n--
                } else {
                    var t1: Token = firstTokens.elementAt(n-1)
                    var t2: Token = secondTokens.elementAt(m-1)
                    if (t1 == t2) {
                        t1 = Token("+${t1.text}")
                        t2 = Token("+${t2.text}")
                    }
                    resA.add(0, t1)
                    resB.add(0, t2)
                    n--
                    m--
                }
            }
        }
    }

    return d[y][x]
}

fun getDash(length: Int): String {
    val sb = StringBuilder()
    for (i in 1..length)
        sb.append("-")
    return sb.toString()
}

private fun insCost(t: Token): Int = 1
private fun delCost(t: Token): Int = 1
private fun replCost(t1: Token, t2: Token): Int = if (t1 == t2) 0 else 1 //t1.getDifference(t2)

fun getTokens(text: String) = text.split(" ").map { Token(it) }
