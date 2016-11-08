package com.bio4j.data.go

trait AnyGO extends Any {

  def terms: Seq[AnyTerm]

  // relationships
  def isA                 : Seq[IsA]
  def partOf              : Seq[PartOf]
  def regulates           : Seq[Regulates]
  def negativelyRegulates : Seq[NegativelyRegulates]
  def positivelyRegulates : Seq[PositivelyRegulates]
}

trait AnyTerm extends Any {

  def ID: String
  def name: String
  def definition: String
  def namespace: String // TODO finite known set of namespaces
  def comments: String
}

sealed trait AnyRel {

  def sourceID: String
  def targetID: String
}
case class IsA(val sourceID: String, val targetID: String) extends AnyRel
case class PartOf(val sourceID: String, val targetID: String) extends AnyRel
case class Regulates(val sourceID: String, val targetID: String) extends AnyRel
case class NegativelyRegulates(val sourceID: String, val targetID: String) extends AnyRel
case class PositivelyRegulates(val sourceID: String, val targetID: String) extends AnyRel
