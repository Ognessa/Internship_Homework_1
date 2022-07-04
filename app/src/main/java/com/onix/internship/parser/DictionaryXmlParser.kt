package com.onix.internship.parser

import android.content.Context
import com.onix.internship.data.Dictionary
import org.xmlpull.v1.XmlPullParser
import org.xmlpull.v1.XmlPullParserFactory

class DictionaryXmlParser(val context : Context) {
    val dictionary : ArrayList<Dictionary> = arrayListOf()

    fun parseAllDicts(){
        val filesNames = context.assets.list("")
        val dictNames = arrayListOf<String>()

        filesNames?.forEach {
            if(it.contains(".xdxf")) dictNames.add(it)
        }

        dictNames.forEach {
            parseDict(it)
        }
    }

    private fun parseDict(title : String) {
        lateinit var normDict : Dictionary
        lateinit var reverseDict : Dictionary
        var key = ""
        var value: String

        val inputStream =  context.assets.open(title)
        val parserFactory: XmlPullParserFactory = XmlPullParserFactory.newInstance()
        val parser: XmlPullParser = parserFactory.newPullParser()
        parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, true)
        parser.setInput(inputStream, null)
        var tag: String?
        var text = ""
        var event = parser.eventType
        while (event != XmlPullParser.END_DOCUMENT) {
            tag = parser.name
            when (event) {
                XmlPullParser.START_TAG -> when (tag) {
                    "xdxf" -> {
                        val from = parser.getAttributeValue(null, "lang_from")
                        val to = parser.getAttributeValue(null, "lang_to")
                        normDict = Dictionary("$from-$to")
                        reverseDict = Dictionary("$to-$from")
                    }
                }
                XmlPullParser.TEXT -> {
                    text = parser.text
                }
                XmlPullParser.END_TAG -> when (tag) {
                    "k" -> key = text
                    "ar" -> {
                        value = text.substring(text.indexOf(' ')+2, text.length)
                            .replace("\"", "")
                            .lowercase()
                        normDict.setDataToDict(key, value)
                        reverseDict.setDataToDict(value, key)
                    }
                }
            }
            event = parser.next()
        }

        dictionary.add(normDict)
        dictionary.add(reverseDict)
    }
}