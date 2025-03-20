package org.llm4s.samples.tokens

import org.llm4s.metadata.vendors.GPT_4o_2024_08_06
import org.llm4s.token.Tokenizer

object TokenizerExample {
  def main(args: Array[String]): Unit = {
    val tokenizer = Tokenizer.lookupStringTokenizer(GPT_4o_2024_08_06.vocabulary.get).get
    println(tokenizer.encode("Hello Scala!"))
  }
}
