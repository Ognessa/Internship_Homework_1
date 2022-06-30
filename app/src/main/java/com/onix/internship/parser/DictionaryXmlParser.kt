package com.onix.internship.parser

import android.content.Context
import com.onix.internship.R
import org.xmlpull.v1.XmlPullParser
import org.xmlpull.v1.XmlPullParserFactory

class DictionaryXmlParser(val context : Context) {
    var dictionary : HashMap<String, HashMap<String, ArrayList<String>>> = hashMapOf()

    fun parseAllDicts() {
        val dictNames = context.resources.getStringArray(R.array.dictionaries)
        dictionary.clear()
        dictionary[dictNames[0]] = parseDict("ua_pol.xdxf")
    }

    private fun parseDict(title : String): HashMap<String, ArrayList<String>> {
        val list = hashMapOf<String, ArrayList<String>>()
        var key = ""
        var value = ""

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
                XmlPullParser.START_TAG -> if (tag == "ar") {
                    key = ""
                    value = ""
                }
                XmlPullParser.TEXT -> {
                    text = parser.text
                    text = text
                        .replace("\"", "")
                        .replace("\n", "")
                        .replace("  ", "-")
                }
                XmlPullParser.END_TAG -> when (tag) {
                    "k" -> key = text
                    "ar" -> {
                        value = text.substring(text.indexOf('-')+1, text.length).lowercase()
                        if(list.containsKey(key)){
                            val arrayList = list[key]
                            arrayList!!.add(value)
                            list[key] = arrayList
                        }
                        else{
                            val arrayList = arrayListOf(value)
                            list[key] = arrayList
                        }
                    }
                }
            }
            event = parser.next()
        }

        return list
    }

    fun getValue(currentDict : String, key : String): ArrayList<String>?{
        if(dictionary.containsKey(currentDict)
            && dictionary[currentDict]!!.containsKey(key))
            return dictionary[currentDict]?.get(key)

        return null
    }
}