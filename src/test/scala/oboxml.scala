package com.bio4j.data.go.test

import org.scalatest.FunSuite
import com.bio4j.data.go._
import scala.xml._

class OBOXML extends FunSuite {

  private lazy val MyXML = new factory.XMLLoader[Elem] {

    private lazy val parserFactory = {
      val f0 = javax.xml.parsers.SAXParserFactory.newInstance()
      f0.setNamespaceAware(false)
      f0.setValidating(false)
      f0.setXIncludeAware(false)
      f0
    }

    private lazy val parser0 = parserFactory.newSAXParser()

    override def parser: SAXParser = parser0
  }


  test("parse whole GO oboxml file, access all data") {

    val go = oboxml.GOOBOXML(MyXML.loadFile("go_daily-termdb.obo-xml"))

    val terms = go.terms
    println { s"number of terms: ${terms.size}" }

    terms foreach { term =>

      val id          = term.ID
      val name        = term.name
      val definition  = term.definition
      val namespace   = term.namespace
      val comments    = term.comments
    }

    val isA = go.isA
    println { s"number of isA: ${isA.size}" }

    isA foreach { rel =>

      val source = rel.sourceID
      val target = rel.targetID
    }

    val partOf = go.partOf
    println { s"number of partOf: ${partOf.size}" }

    partOf foreach { rel =>

      val source = rel.sourceID
      val target = rel.targetID
    }

    val regulates = go.regulates
    println { s"number of regulates: ${regulates.size}" }

    regulates foreach { rel =>

      val source = rel.sourceID
      val target = rel.targetID
    }

    val positivelyRegulates = go.positivelyRegulates
    println { s"number of positivelyRegulates: ${positivelyRegulates.size}" }

    positivelyRegulates foreach { rel =>

      val source = rel.sourceID
      val target = rel.targetID
    }

    val negativelyRegulates = go.negativelyRegulates
    println { s"number of negativelyRegulates: ${negativelyRegulates.size}" }

    negativelyRegulates foreach { rel =>

      val source = rel.sourceID
      val target = rel.targetID
    }
  }
}
