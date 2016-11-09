package com.bio4j.data.go.oboxml

import com.bio4j.data.go._
import scala.xml._
import scala.compat.java8.OptionConverters._

case class GOOBOXML(xml: Elem) extends AnyVal with AnyGO {

  def terms: Seq[XmlTerm] =
    (xml \ "term").filter( term => (term \ "isObsolete").isEmpty ) map { XmlTerm(_) }

  def isA: Seq[IsA] =
    terms flatMap { _.isA }

  def partOf: Seq[PartOf] =
    terms flatMap { _.partOf }

  def regulates: Seq[Regulates] =
    terms flatMap { _.regulates }

  def negativelyRegulates: Seq[NegativelyRegulates] =
    terms flatMap { _.negativelyRegulates }

  def positivelyRegulates: Seq[PositivelyRegulates] =
    terms flatMap { _.positivelyRegulates }
}

case class XmlTerm(val xml: Node) extends AnyVal with AnyTerm {

  def ID: String =
    (xml \ "id").head.text

  def name: String =
    (xml \ "name").head.text

  def comments: Seq[String] =
    (xml \ "comment") map { _.text }

  def namespace: Namespace =
    XmlTerm namespaceFrom (xml \ "namespace").head.text

  def definition: String =
    (xml \ "def" \ "defstr").head.text

  // for some reason isA has a different structure
  def isA: Seq[IsA] =
    (xml \ "is_a") map { elem => IsA(ID, elem.text) }

  def partOf: Seq[PartOf] =
    relationships("part_of") map { r => PartOf(r.sourceID, r.targetID)  }

  def regulates: Seq[Regulates] =
    relationships("regulates") map { r => Regulates(r.sourceID, r.targetID) }

  def positivelyRegulates: Seq[PositivelyRegulates] =
    relationships("positively_regulates") map { r => PositivelyRegulates(r.sourceID, r.targetID) }

  def negativelyRegulates: Seq[NegativelyRegulates] =
    relationships("negatively_regulates") map { r => NegativelyRegulates(r.sourceID, r.targetID) }

  private def relFromRelationship(relationship: Node): Rel =
    Rel(ID, (relationship \ "to").head.text)

  private def relationshipIs(name: String)(relationship: Node): Boolean =
    (relationship \ "type").head.text == name

  private def relationships(name: String) =
    (xml \ "relationship")
      .filter( relationshipIs(name) )
      .map( relFromRelationship )
}

case object XmlTerm {

  def namespaceFrom(rep: String): Namespace =
    rep match {
      case "cellullar_component" => cellullar_component
      case "molecular_function" => molecular_function
      case "biological_process" => biological_process
    }
}

case class Rel(sourceID: String, targetID: String)
