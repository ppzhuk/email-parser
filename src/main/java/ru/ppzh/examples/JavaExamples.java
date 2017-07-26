/*
 * Copyright 2016-2017 JetBrains s.r.o., 2017 Pavel Zhuk
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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
