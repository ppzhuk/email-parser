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

package quoteParser.features

import org.jetbrains.spek.api.Spek
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class PhraseFeatureSpecs : Spek() {
    init {
        val phraseFeature = PhraseFeature(KeyPhrases.default)

        given("header phrase") {
            val s = """   In reply to:   """
            on("checking regexes") {
                it("should match PHRASE regex") {
                    assertTrue { phraseFeature.matches(s) }
                }
            }
        }
        given("header phrase") {
            val s = """>> In reply to:   """
            on("checking regexes") {
                it("should match PHRASE regex") {
                    assertTrue { phraseFeature.matches(s) }
                }
            }
        }
        given("header phrase") {
            val s = """   In    RePly To:   """
            on("checking regexes") {
                it("should match PHRASE regex") {
                    assertTrue { phraseFeature.matches(s) }
                }
            }
        }
        given("header phrase") {
            val s = """##- Bitte geben Sie Ihre Antwort oberhalb dieser Zeile ein. -##"""
            on("checking regexes") {
                it("should match PHRASE regex") {
                    assertTrue { phraseFeature.matches(s) }
                }
            }
        }

        given("header phrase") {
            val s = """##- Bitte geben Sie Ihre Antwort oberhalb dieser Zeile ein. -##"""
            on("checking regexes") {
                it("should match PHRASE regex") {
                    assertTrue { phraseFeature.matches(s) }
                }
            }
        }

        given("header phrase") {
            val s = """> > ##- Bitte geben Sie Ihre Antwort oberhalb dieser Zeile ein. -##"""
            on("checking regexes") {
                it("should not match PHRASE regex") {
                    assertFalse { phraseFeature.matches(s) }
                }
            }
        }

        given("""header phrase with non-breaking whitespaces and invisible characters(u200e)""") {
            val s = """   In    RePly To:   """
            on("checking regexes") {
                it("should match PHRASE regex") {
                    assertTrue { phraseFeature.matches(s) }
                }
            }
        }
        given("header phrase with non-breaking whitespaces and invisible character(u200e)") {
            val s = """ ‎##- Bitte geben Sie Ihre Antwort oberhalb dieser Zeile ein. -## """
            on("checking regexes") {
                it("should match PHRASE regex") {
                    assertTrue { phraseFeature.matches(s) }
                }
            }
        }
        given("header phrase with some garbage") {
            val s = """ >>>>  In    RePly To:  <<< """
            on("checking regexes") {
                it("should not match PHRASE regex") {
                    assertFalse { phraseFeature.matches(s) }
                }
            }
        }
        given("header phrase with some garbage") {
            val s = """text ##- Bitte geben Sie Ihre Antwort oberhalb dieser Zeile ein. -##"""
            on("checking regexes") {
                it("should not match PHRASE regex") {
                    assertFalse { phraseFeature.matches(s) }
                }
            }
        }
        given("header phrase with some garbage") {
            val s = """  In    RePly To:  garbage"""
            on("checking regexes") {
                it("should not match PHRASE regex") {
                    assertFalse { phraseFeature.matches(s) }
                }
            }
        }
        given("header phrase with some garbage") {
            val s = """##- Bitte geben Sie Ihre Antwort oberhalb dieser Zeile ein. -## garbage"""
            on("checking regexes") {
                it("should not match PHRASE regex") {
                    assertFalse { phraseFeature.matches(s) }
                }
            }
        }
    }
}