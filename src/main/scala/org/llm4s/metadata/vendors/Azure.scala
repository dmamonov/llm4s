package org.llm4s.metadata.vendors

import org.llm4s.metadata.Vendor

case object Azure extends Vendor {
  override def name: String = "azure"

  override def displayName: String = "Azure"
}
