package org.llm4s.metadata.vendors

import org.llm4s.metadata.Endpoint.{ Assistants, Batch, ChatCompletions, Responses }
import org.llm4s.metadata.Feature.{ FunctionCalling, Streaming, StructuredOutputs }
import org.llm4s.metadata.MediaType.{ Image, Text }
import org.llm4s.metadata.{ Endpoint, Feature, MediaType, ModelSeries, ModelVersion, Vendor, Vocabulary }

import java.time.LocalDate

case object OpenAI extends Vendor {
  override def name: String = "openai"

  override def displayName: String = "OpenAI"
}

case object R50K_BASE   extends Vocabulary { override def name = "r50k_base"   }
case object P50K_BASE   extends Vocabulary { override def name = "p50k_base"   }
case object P50K_EDIT   extends Vocabulary { override def name = "p50k_edit"   }
case object CL100K_BASE extends Vocabulary { override def name = "cl100k_base" }
case object O200K_BASE  extends Vocabulary { override def name = "o200k_base"  }

case object GPT_4o extends ModelSeries {

  override def vendor: Vendor = OpenAI

  override def name: String = "gpt-4o"
}

case object GPT_4o_2024_08_06 extends ModelVersion {
  override def model: ModelSeries = GPT_4o
  override def name: String       = "gpt-4o-2024-08-06"

  override def vocabulary: Option[Vocabulary] = Some(CL100K_BASE)

  override def inputs: Set[MediaType] = Set(Text, Image)

  override def outputs: Set[MediaType] = Set(Text)

  override def features: Option[Set[Feature]] = None

  override def endpoints: Option[Set[Endpoint]] = None

  override def contextWindowSize: Option[Int] = Some(128_000)

  override def maxOutputTokens: Option[Int] = Some(16_384)

  override def knowledgeCutoff: Option[LocalDate] = Some(LocalDate.of(2023, 10, 1))
}

case object GPT_4_5_Preview extends ModelSeries {
  override def vendor: Vendor = OpenAI

  override def name: String = "gpt-4.5-preview"
}

case object GPT_4_5_Preview_2025_02_27 extends ModelVersion {
  override def model: ModelSeries = GPT_4_5_Preview

  override def name: String = "gpt-4.5-preview-2025-02-27"

  override def vocabulary: Option[Vocabulary] = None

  override def inputs: Set[MediaType] = Set(Text, Image)

  override def outputs: Set[MediaType] = Set(Text)

  override def features: Some[Set[Feature]] = Some(Set(Streaming, StructuredOutputs, FunctionCalling))

  override def endpoints: Some[Set[Endpoint]] = Some(Set(ChatCompletions, Batch, Responses, Assistants))

  override def contextWindowSize: Option[Int] = Some(128_000)

  override def maxOutputTokens: Option[Int] = Some(16_384)

  override def knowledgeCutoff: Option[LocalDate] = Some(LocalDate.of(2023, 10, 1))
}
