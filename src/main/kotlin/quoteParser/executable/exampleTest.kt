package quoteParser.executable

import quoteParser.*
import java.io.*
import javax.mail.internet.MimeMessage

private val pathEmails = "C:${File.separator}YT${File.separator}"

// todo modify
fun main(args: Array<String>) {
    val emlFile = File("""C:\Users\pavel.zhuk\Desktop\testEmls\SL\800.eml""")
//    val emlFile = File("""C:\YT\800.eml""")
    val start = System.currentTimeMillis()
    val msg: MimeMessage = getMimeMessage(emlFile)
    val emailText: String = getEmailText(msg)
    val isInReplyToHeader = containInReplyToHeader(msg)
    val trigger = true
    val content = QuoteParser.Builder()
            .hasInReplyToEMLHeader(isInReplyToHeader)
            .deleteQuoteMarks(trigger)
            .recursive(trigger)
            .build().parse(emailText.lines())
    val end = System.currentTimeMillis()
    println("Working time: ${end - start} ms.")

    println(content.toString(addMarks = trigger, uppercaseHeader = true))
}