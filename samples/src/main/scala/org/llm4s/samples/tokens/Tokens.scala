package org.llm4s.samples.tokens

import com.knuddels.jtokkit.Encodings
import com.knuddels.jtokkit.api.EncodingType

import java.io.{ ByteArrayOutputStream, InputStream }
import java.nio.charset.StandardCharsets
import java.util.Base64
import java.util.function.Supplier
import scala.io.Source
import scala.util.{ Try, Using }

//GPT_4O("gpt-4o", EncodingType.O200K_BASE, 128000),

case class Token(bytes: Array[Byte], id: Int) extends Comparable[Token] {
  override def compareTo(other: Token): Int = this.id.compareTo(other.id)
  override def toString: String             = "{`" + new String(bytes) + "`=" + id.toString + "}"
}

object TokenParsers {
  def parseTiktoken(inputStreamSource: Supplier[InputStream]): Try[List[Token]] =
    Using(inputStreamSource.get()) { input =>
      val base64 = Base64.getDecoder
      Source
        .fromInputStream(input)
        .getLines()
        .filter(line => !line.isBlank)
        .map { line =>
          line.trim.split(" +") match {
            case Array(base64String, idString) =>
              val tokenBytes = base64.decode(base64String)
              val id         = idString.toInt
              Token(tokenBytes, id)
            case _ => throw new IllegalStateException(s"Wrong line `$line`")
          }
        }
        .toList
    }
}

trait StringTokenEncoder {
  def encode(text: String): Try[List[Token]]

  def decode(tokens: List[Int]): Try[String]
}

case class GenericStringTokenEncoder(utf8Encoder: Utf8TokenEncoder) extends StringTokenEncoder {
  override def encode(text: String): Try[List[Token]] = Try {
    utf8Encoder.encodeUtf8(text.getBytes(StandardCharsets.UTF_8)).get
  }

  override def decode(tokens: List[Int]): Try[String] = Try {
    val bytes: Array[Byte] = utf8Encoder.decodeUtf8(tokens).get
    new String(bytes, StandardCharsets.UTF_8)
  }
}

trait Utf8TokenEncoder {
  def encodeUtf8(bytes: Array[Byte]): Try[List[Token]]

  def decodeUtf8(tokens: List[Int]): Try[Array[Byte]]
}

case class BytePairUtf8TokenEncoder(tokens: List[Token]) extends Utf8TokenEncoder {
  private val decodeMap = tokens.map(token => token.id -> token).toMap

  override def encodeUtf8(bytes: Array[Byte]): Try[List[Token]] = ???

  override def decodeUtf8(tokens: List[Int]): Try[Array[Byte]] = Try {
    val result = new ByteArrayOutputStream()
    tokens.foreach { token =>
      result.write(decodeMap(token).bytes) // check absent key
    }
    result.toByteArray
  }
}

object TokensMain {
  def main1(args: Array[String]): Unit = {
    val registry = Encodings.newDefaultEncodingRegistry
    val encoder  = registry.getEncoding(EncodingType.CL100K_BASE)
    assert("hello world" == encoder.decode(encoder.encode("hello world")))
    println(encoder.encode("Hello World"))
  }

  def main(args: Array[String]): Unit = {
    // reference implementation:
    val referenceRegistry = Encodings.newDefaultEncodingRegistry
    val referenceEncoder  = referenceRegistry.getEncoding(EncodingType.O200K_BASE)

    // current implementation:
    val tokens: List[Token] =
      TokenParsers.parseTiktoken(() => TokensMain.getClass.getResourceAsStream("o200k_base.tiktoken")).get
    tokens.slice(0, 1000).foreach(token => println(token))
    println("...\n")


    val trialEncoder = GenericStringTokenEncoder(BytePairUtf8TokenEncoder(tokens))

    val exampleText =
      "Both methods give you flexibility, and you can choose based on your use case and whether you're working with standard Java libraries or Scala collections."
    println(s"Example:\n$exampleText\n\n")

    val referenceExampleTokens = referenceEncoder.encode(exampleText)

    val referenceReconstructedText = referenceEncoder.decode(referenceExampleTokens)
    val trialReconstructedText     = trialEncoder.decode(referenceExampleTokens.toArray.toList).get

    println(s"Reference:\n$referenceReconstructedText\n\n")
    println(s"Trial:\n$trialReconstructedText\n\n")
  }
}
