package com.indicode.util.xml_substitutor

import org.w3c.dom.Element
import org.w3c.dom.Node
import java.io.File
import java.lang.reflect.Array.getLength
import org.w3c.dom.NodeList
import javax.xml.parsers.DocumentBuilder
import javax.xml.parsers.DocumentBuilderFactory
import java.lang.reflect.Array.getLength
import javax.xml.stream.XMLOutputFactory
import java.io.StringWriter
import javax.xml.transform.TransformerException
import javax.xml.transform.stream.StreamResult
import javax.xml.transform.dom.DOMSource
import com.sun.xml.internal.ws.addressing.EndpointReferenceUtil.transform
import java.lang.Exception
import javax.swing.*
import javax.xml.transform.OutputKeys
import javax.xml.transform.TransformerFactory
import javax.xml.transform.Transformer
import javax.swing.UnsupportedLookAndFeelException
import javax.swing.UIManager
import com.sun.xml.internal.ws.streaming.XMLStreamReaderUtil.close
import java.io.IOException
import java.io.BufferedWriter
import java.io.FileWriter
import java.io.Writer





fun main(args: Array<String>) {
    if (args.size > 0) {
        replace(args[0], if (args.size > 1) args[1] else "substitute")
    } else {
        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName())

        InputWindow()
    }
}
fun replace(dir: String, tag: String, progressbar: JProgressBar? = null) {
    val file = File(dir + "/content")
    if (!file.exists()) error("Folder does not exist. (" + file.absolutePath + ")")
    if (!file.isDirectory) error("File is not a Directory. (" + file.absolutePath + ")")
    val content = getFiles(file)

    if (content.isEmpty()) error("Folder is empty (" + file.absolutePath + ")")
    progressbar?.maximum = content.size
    progressbar?.value = 0
    var idx = 0
    content.forEach {
        println("Substituting in: " + it)
        progressbar?.value = idx++;
        progressbar?.string = it.name + " - " + progressbar?.value.toString() + "/" + progressbar?.maximum.toString();
        val root = getRootElement(it)
        replaceTags(getTagElements(root, tag), File(dir))
        writeXML(File(dir + "/build/" + it.name), root)
    }
    progressbar?.value = content.size
    progressbar?.string = "DONE!"
    JOptionPane.showMessageDialog(InputWindow.frame, "Done!\nFiles are located at " + dir + "/build/", "Done", JOptionPane.INFORMATION_MESSAGE)
}
fun getFiles(root: File): ArrayList<File> {
    val files = ArrayList<File>()
    root.listFiles().forEach { if (it.isDirectory) files.addAll(getFiles(it)) else files.add(it)}
    return files
}
fun writeXML(out: File, xml: Node) {
    val factory = DocumentBuilderFactory.newInstance();
    val parser = factory.newDocumentBuilder();
    val xmls = nodeToString(xml);
    var bufferedWriter: BufferedWriter? = null
    try {
        if (!out.exists()) {
            out.parentFile.mkdirs()
            out.createNewFile()
        }
        val writer = FileWriter(out)
        bufferedWriter = BufferedWriter(writer)
        bufferedWriter.write(xmls)
    } catch (e: IOException) {
        e.printStackTrace()
    } finally {
        try {
            bufferedWriter?.close()
        } catch (ex: Exception) {

        }

    }
}
private fun nodeToString(node: Node): String {
    val sw = StringWriter()
    try {
        val t = TransformerFactory.newInstance().newTransformer()
        t.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes")
        t.setOutputProperty(OutputKeys.INDENT, "yes")
        t.transform(DOMSource(node), StreamResult(sw))
    } catch (te: TransformerException) {
        te.printStackTrace()
        println("nodeToString Transformer Exception; Node: " + node)
    }

    return sw.toString()
}
fun getRootElement(xml: File) : Element {
    try {
        val dbFactory = DocumentBuilderFactory.newInstance()
        val dBuilder = dbFactory.newDocumentBuilder()
        val doc = dBuilder.parse(xml)
        return doc.documentElement
    } catch (e: Exception) {
        error("There was a problem parsing \"" + xml.absolutePath + "\"; Exception: " + e.localizedMessage)
        throw Exception()
    }
}
fun getTagElements(root: Element, tag: String): List<Element> {
    var elements = ArrayList<Element>()
    val children = root.getElementsByTagName("*")
    for (i in 0 until children.getLength()) {
        val n = children.item(i)

        //if (n.hasChildNodes()) {
        //   elements.addAll(getTagElements(n as Element, tag))
        if (n.nodeName == tag) {

            elements.add(n as Element)
        }
    }
    return elements
}
fun replaceTags(tagMap: List<Element>, folder: File) {

    var idx = 0;
    for (entry in tagMap) {
        val replaceWith = File(folder.absolutePath + "/" + entry.getAttribute("src"))
        if (!replaceWith.exists()) error("Replacement file does not exist (" + replaceWith.absolutePath + ")")
        if (replaceWith.isDirectory) error("Replacement file is a directory (" + replaceWith.absolutePath + ")")
        println("Substituting tag in \'" + entry.parentNode.nodeName + "\' with data at: " + replaceWith)
        replaceTag(entry, getRootElement(replaceWith))
    }
}
fun replaceTag(tag: Element, rep: Node) {
    val parent = tag.parentNode
    val rep = tag.ownerDocument.importNode(rep, true)
    val sas = rep.attributes.getNamedItem("stays_after_sub");
    if (sas != null) {
        val strSas = sas.nodeValue.toString().toLowerCase()
        rep.attributes.removeNamedItem(sas.nodeName)
        if (strSas == "true") parent.insertBefore(rep, tag)
        else {
            var currentChild = rep.firstChild;
            val list = ArrayList<Node>();
            for (i in 0 until rep.childNodes.length) {
                list.add(currentChild)
                currentChild = currentChild.nextSibling;
            }
            for (child in list) {
                parent.insertBefore(child, tag)
            }
        }
    } else {
        var currentChild = rep.firstChild;
        val list = ArrayList<Node>();
        for (i in 0 until rep.childNodes.length) {
            list.add(currentChild)
            currentChild = currentChild.nextSibling;
        }
        for (child in list) {
            parent.insertBefore(child, tag)
        }
    }
    parent.removeChild(tag)
}
fun error(error: String) {
    if (InputWindow.frame != null) {
        JOptionPane.showMessageDialog(InputWindow.frame, error, "Error", JOptionPane.ERROR_MESSAGE)
    } else {
        System.err.println("Error: " + error)
    }
    Thread.currentThread().stop()
}