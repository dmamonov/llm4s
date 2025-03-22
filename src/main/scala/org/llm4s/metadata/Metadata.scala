package org.llm4s.metadata

import java.time.LocalDate

/*sealed*/
trait Vendor {
  def name: String

  def displayName: String = name
}
case class CustomVendor(name: String) extends Vendor {}

/*sealed*/
trait ModelSeries {
  def vendor: Vendor
  def displayName: String = name
  def name: String
}
case class CustomSeries(name: String, vendor: Vendor) extends ModelSeries {}

sealed trait MediaType
object MediaType {
  case object Text  extends MediaType
  case object Image extends MediaType
  case object Audio extends MediaType
}

sealed trait Feature
object Feature {
  case object Streaming         extends Feature
  case object FunctionCalling   extends Feature
  case object StructuredOutputs extends Feature
  case object FineTuning        extends Feature
  case object Distillation      extends Feature
  case object PredictedOutputs  extends Feature
}

sealed trait Endpoint
object Endpoint {
  case object ChatCompletions   extends Endpoint
  case object Responses         extends Endpoint
  case object Realtime          extends Endpoint
  case object Assistants        extends Endpoint
  case object Batch             extends Endpoint
  case object FineTuning        extends Endpoint
  case object Embeddings        extends Endpoint
  case object ImageGeneration   extends Endpoint
  case object SpeechGeneration  extends Endpoint
  case object Transcription     extends Endpoint
  case object Translation       extends Endpoint
  case object Moderation        extends Endpoint
  case object CompletionsLegacy extends Endpoint
}

/*sealed*/
trait Vocabulary {
  def name: String
  def displayName: String = name
}
case class CustomVocabulary(name: String) extends Vocabulary

/*sealed*/
trait ModelVersion {
  def model: ModelSeries

  def name: String

  def displayName: String = name

  def vocabulary: Option[Vocabulary]

  def inputs: Set[MediaType]

  def outputs: Set[MediaType]

  def features: Option[Set[Feature]]

  def endpoints: Option[Set[Endpoint]]

  def contextWindowSize: Option[Int]

  def maxOutputTokens: Option[Int]

  def knowledgeCutoff: Option[LocalDate]
}

case class CustomModelVersion(
  model: ModelSeries,
  name: String,
  vocabulary: Option[Vocabulary] = None,
  inputs: Set[MediaType],
  outputs: Set[MediaType],
  features: Option[Set[Feature]] = None,
  endpoints: Option[Set[Endpoint]] = None,
  contextWindowSize: Option[Int],
  maxOutputTokens: Option[Int],
  knowledgeCutoff: Option[LocalDate]
) extends ModelVersion
