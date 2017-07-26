# email-parser
This library provides ability to separate the quotation from the useful content in email messages. The main purpose of this library is to process as much different quotation formats as possible. It is also independent from the language used in email.

Efficiency estimation we have got during testing: **> 97.5 %** correctly processed emails.

# Usage
Gradle:
```
compile group: 'com.github.ppzhuk', name: 'email-parser', version: '1.0.1'
```

Maven:
```
<dependency>
    <groupId>com.github.ppzhuk</groupId>
    <artifactId>email-parser</artifactId>
    <version>1.0.1</version>
</dependency>
```

### Documentation

You can find documentation [here](https://ppzhuk.github.io/email-parser/index.html).

### Description
The main package of the library is [quoteParser](src/main/kotlin/ru/ppzh/quoteParser). Its main class is [QuoteParser](src/main/kotlin/ru/ppzh/quoteParser/QuoteParser.kt#L77). 

To use parser call `quoteParserObj.parse()` method. This method will return [Content](src/main/kotlin/ru/ppzh/quoteParser/Content.kt) object with a separate body, header of the quote if exists and quotation itself if exists.

You can use [quoteParserObj.parse(ListOfStrings)](src/main/kotlin/ru/ppzh/quoteParser/QuoteParser.kt#L229) to parse any email text.
 Another way is to use [quoteParserObj.parse(File)](src/main/kotlin/ru/ppzh/quoteParser/QuoteParser.kt#L212) with some email file as a parameter.
 ([email filename extensions](https://en.wikipedia.org/wiki/Email#Filename_extensions)).
 For the second case text/HTML Content-Type isn't supported by now.

For more information, read the documentation.

# Examples
Simple usage example (Kotlin):
```kotlin
val content = QuoteParser.Builder()
        .build()
        .parse(file)
```
You also can parse list of strings and customize parser parameters via different builder methods:
```kotlin
val content = QuoteParser.Builder()
        .deleteQuoteMarks(true)
        .recursive(false)
        .build()
        .parse(emailText.lines())
```
Kotlin-style builder is also supported:
```kotlin
val content = QuoteParser.Builder()
        .build {
            deleteQuoteMarks = true
            recursive = false
        }.parse(email.lines())
```

Usage examples (Java):
```java
Content c = new QuoteParser.Builder()
        .build()
        .parse(file);
```
```java
Content c = new QuoteParser.Builder()
        .recursive(true)
        .deleteQuoteMarks(false)
        .build()
        .parse(Arrays.asList(emailText.split("\n")));
```
Complete code of the examples is placed here: [Kotlin](src/main/kotlin/ru/ppzh/examples), [Java](src/main/java/ru/ppzh/examples).
