package org.llm4s.metadata.vendors

import org.llm4s.metadata.Vendor

case object Anthropic extends Vendor {
  override def name: String = "anthropic"

  override def displayName: String = "Anthropic"
}
