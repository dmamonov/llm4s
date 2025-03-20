package org.llm4s.token

import com.knuddels.jtokkit.Encodings
import com.knuddels.jtokkit.api.EncodingType
import org.llm4s.metadata.Vocabulary
import org.llm4s.metadata.vendors.CL100K_BASE

case class Token(tokenId: Int) {
  override def toString: String = s"$tokenId"
}

trait StringTokenizer {
  def encode(text: String): List[Token]
}

object Tokenizer {
  private val registry = Encodings.newDefaultEncodingRegistry

  def lookupStringTokenizer(vocabulary: Vocabulary): Option[StringTokenizer] =
    if (vocabulary == CL100K_BASE) {
      val encoder = registry.getEncoding(EncodingType.CL100K_BASE)
      // noinspection ConvertExpressionToSAM
      Some(new StringTokenizer {
        override def encode(text: String): List[Token] =
          encoder.encode(text).toArray.map(tokenId => Token(tokenId)).toList
      })
    } else None
}
