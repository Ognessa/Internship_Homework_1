package com.onix.internship.data

data class Dictionary(val title : String) {

    val dict : HashMap<String, ArrayList<String>> = hashMapOf()

    fun setDataToDict(key : String, value : String){
        var arrayList = arrayListOf<String>()

        if(dict.containsKey(key)){
            arrayList = dict[key]!!
            arrayList.add(value)
        }
        else arrayList.add(value)

        dict[key] = arrayList
    }

    fun findWord(key : String) : ArrayList<String>{
        val values = arrayListOf<String>()
        if(dict.containsKey(key))
            dict[key]?.let { values.addAll(it) }

        return values
    }
}