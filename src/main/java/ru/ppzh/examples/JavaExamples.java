package ru.ppzh.examples;

import ru.ppzh.quoteParser.Content;
import ru.ppzh.quoteParser.ParseKt;
import ru.ppzh.quoteParser.QuoteParser;

import javax.mail.internet.MimeMessage;
import java.io.File;
import java.util.Arrays;
import java.util.List;

public class JavaExamples {
    public static void main(String[] args) {
        File f = new File(args[0]);
        example(f);
        linesExample(f);
    }

    private static void example(File file) {
        Content c = new QuoteParser.Builder()
                .build()
                .parse(file);

        System.out.println(c.toString(true, true));
    }

    private static void linesExample(File file) {
        MimeMessage msg = ParseKt.getMimeMessage(file);
        boolean hasInReplyTo = ParseKt.containInReplyToHeader(msg);
        String emailText = ParseKt.getEmailText(msg);
        List<String> lines = Arrays.asList(emailText.split("\n"));

        Content c = new QuoteParser.Builder()
                .recursive(true)
                .deleteQuoteMarks(true)
                .build()
                .parse(lines, hasInReplyTo);

        System.out.println(c.toString());
    }
}
