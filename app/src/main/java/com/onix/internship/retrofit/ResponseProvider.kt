package com.onix.internship.retrofit

import android.util.Log
import com.onix.internship.objects.DeviceData
import com.onix.internship.objects.JsonData
import kotlinx.serialization.DeserializationStrategy
import kotlinx.serialization.SerializationException
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.CompositeDecoder.Companion.DECODE_DONE
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.decodeStructure
import kotlinx.serialization.json.Json
import kotlinx.serialization.serializer
import org.jsoup.Jsoup
import org.jsoup.nodes.Document

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory

class ResponseProvider {

    val dataList = mutableListOf<DeviceData>()

    fun getDataFromApi() {
        val okHttpClient = OkHttpClient()
            .newBuilder()
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .build()

        val retrofit = Retrofit.Builder()
            .addConverterFactory(ScalarsConverterFactory.create())
            .baseUrl("https://onix-systems.github.io/OnixAndroidInternship2022/")
            .client(okHttpClient).build()

        val apiService = retrofit.create(ApiService::class.java)

        val stringCall: Call<String?>? = apiService.stringResponse

        stringCall?.enqueue(object : Callback<String?> {
            override fun onResponse(call: Call<String?>, response: Response<String?>) {
                if (response.isSuccessful) {
                    var responseString: String? = response.body()
                    val doc: Document = Jsoup.parse(responseString)
                    responseString = doc.text()
                    Log.d("DEBUG", getJson(responseString))
                }
            }

            override fun onFailure(call: Call<String?>, t: Throwable) {}
        })

    }

    private fun getJson(response : String): String {
        val json = response.subSequence(
            response.indexOf("Data started") + ("Data started".length),
            response.indexOf("Data ended")
        ).toString()

        val deserializationStrategy = object : DeserializationStrategy<JsonData>{
            override val descriptor: SerialDescriptor
                get() = JsonData

            override fun deserialize(decoder: Decoder): JsonData = decoder.decodeStructure(descriptor) {
                var version: String? = null
                var name : String? = null
                var house: List<DeviceData>? = null

                loop@ while (true) {
                    when (val index = decodeElementIndex(descriptor)) {
                        DECODE_DONE -> break@loop
                        0 -> {
                            version = decodeStringElement(descriptor, index = 0)
                        }
                        1 -> {
                            name = decodeStringElement(descriptor, index = 1)
                        }
                        2 -> {
                            house = decodeSerializableElement(descriptor, index = 2, serializer<List<DeviceData>>())
                        }
                        else -> throw SerializationException("Unexpected index $index")
                    }
                }
            }
        }

        val obj = Json.decodeFromString<JsonData>(deserializationStrategy, json)


        return json
    }

}