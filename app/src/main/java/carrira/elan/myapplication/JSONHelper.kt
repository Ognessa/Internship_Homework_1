package carrira.elan.myapplication

import android.content.Context
import android.util.Log
import carrira.elan.myapplication.ui.pages.models.EnterAnswerModel
import carrira.elan.myapplication.ui.pages.models.PollModel
import carrira.elan.myapplication.ui.pages.models.QuizModel
import carrira.elan.myapplication.ui.pages.models.SeveralAnswersModel
import org.json.JSONObject
import org.json.JSONTokener
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

class JSONHelper(private val context: Context){
    val fileName = "poll.json"

    /**set all data to local file*/
    fun updateQuestions(quizItem : QuizModel){
        val jsonObject = readDataFromFile()
        val questionJsonArray = jsonObject.getJSONArray("poll")

        val item = questionJsonArray.get(quizItem.getId() - 1) as JSONObject
        item.put("user_answer", quizItem.getUserAnswer())

        writeDataToFile(jsonObject.toString())
    }

    fun updateQuestions(severalAnswersItem : SeveralAnswersModel){
        val jsonObject = readDataFromFile()
        val questionJsonArray = jsonObject.getJSONArray("poll")

        val item = questionJsonArray.get(severalAnswersItem.getId() - 1) as JSONObject
        val userAnswersArray = item.getJSONArray("user_answers")
        for(i in 0 until severalAnswersItem.getUserAnswers().size){
            userAnswersArray.put(i, severalAnswersItem.getUserAnswers()[i])
        }

        writeDataToFile(jsonObject.toString())
    }

    fun updateQuestions(enterAnswerItem : EnterAnswerModel){
        val jsonObject = readDataFromFile()
        val questionJsonArray = jsonObject.getJSONArray("poll")

        val item = questionJsonArray.get(enterAnswerItem.getId() - 1) as JSONObject
        item.put("user_answer", enterAnswerItem.getUserAnswer())

        writeDataToFile(jsonObject.toString())
    }

    /**get all data from local file*/
    fun getAllQuestions() : PollModel{
        val quizModelList : ArrayList<QuizModel> = arrayListOf()
        val severalAnswersModelList: ArrayList<SeveralAnswersModel> = arrayListOf()
        val enterAnswerModelList: ArrayList<EnterAnswerModel> = arrayListOf()
        val questionJsonArray = readDataFromFile().getJSONArray("poll")

        for(i in 0 until questionJsonArray.length()){
            val it = questionJsonArray.get(i) as JSONObject
            when(it.getString("type")){
                "quiz" -> quizModelList.add(getQuizModelFromJsonArray(it))
                "several_answers" -> severalAnswersModelList.add(getSeveralAnswersModelFromJsonArray(it))
                "enter_answer" -> enterAnswerModelList.add(getEnterAnswerModelFromJsonArray(it))
            }
        }

        return PollModel(quizModelList, severalAnswersModelList, enterAnswerModelList)
    }

    private fun getQuizModelFromJsonArray(it : JSONObject) : QuizModel{
        val id = it.getInt("id")
        val question = it.getString("question")

        val answers : ArrayList<String> = arrayListOf()
        val answersJSONArray = it.getJSONArray("answers")
        for(j in 0 until answersJSONArray.length()){
            answers.add(answersJSONArray.getString(j))
        }

        val correctAnswer = it.getInt("correct_answer")
        val userAnswer = it.getInt("user_answer")

        return QuizModel(id, question, answers, correctAnswer, userAnswer)
    }

    private fun getSeveralAnswersModelFromJsonArray(it : JSONObject) : SeveralAnswersModel{
        val id = it.getInt("id")
        val question = it.getString("question")

        val answers : ArrayList<String> = arrayListOf()
        val answersJSONArray = it.getJSONArray("answers")
        for(j in 0 until answersJSONArray.length()){
            answers.add(answersJSONArray.getString(j))
        }

        val correctAnswers : ArrayList<Int> = arrayListOf()
        val correctAnswersJSONArray = it.getJSONArray("correct_answers")
        for(j in 0 until correctAnswersJSONArray.length()){
            correctAnswers.add(correctAnswersJSONArray.getInt(j))
        }

        val userAnswers : ArrayList<Boolean> = arrayListOf()
        val userAnswersJSONArray = it.getJSONArray("user_answers")
        for(j in 0 until userAnswersJSONArray.length()){
            userAnswers.add(userAnswersJSONArray.getBoolean(j))
        }

        return SeveralAnswersModel(id, question, answers, correctAnswers, userAnswers)
    }

    private fun getEnterAnswerModelFromJsonArray(it : JSONObject) : EnterAnswerModel{
        val id = it.getInt("id")
        val question = it.getString("question")
        val correctAnswer = it.getString("correct_answer")
        val userAnswer = it.getString("user_answer")
        return EnterAnswerModel(id, question, correctAnswer, userAnswer)
    }

    /**
     * Create a file of data in local storage
     */
    fun createJSONDataFile(){
        writeDataToFile(getJSONString().toString())
    }

    /**
     * Get all data from local file
     */
    private fun readDataFromFile():JSONObject{
        val path = context.applicationContext.filesDir
        val file = File(path, fileName)
        return JSONTokener(file.bufferedReader().use { it.readText() }).nextValue() as JSONObject
    }

    /**
     * Write new data to local file
     */
    private fun writeDataToFile(str:String){
        val path = context.applicationContext.filesDir
        try{
            val fOut = FileOutputStream(File(path, fileName))
            fOut.write(str.toByteArray(Charsets.UTF_8))
            fOut.close()
        }catch (e:Exception){
            Log.d("DEBUG", "Cannot create/update file!")
        }
    }

    /**
     * Get JSON file from assets like String
     */
    private fun getJSONString() : String?{
        val jsonString : String
        try {
            jsonString = context.assets.open(fileName).bufferedReader().use { it.readText() }
        }catch(e : IOException){
            e.printStackTrace()
            return null
        }
        return jsonString
    }
}