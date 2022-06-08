package carrira.elan.myapplication

import android.content.Context
import carrira.elan.myapplication.ui.pages.models.EnterAnswerModel
import carrira.elan.myapplication.ui.pages.models.PollModel
import carrira.elan.myapplication.ui.pages.models.QuizModel
import carrira.elan.myapplication.ui.pages.models.SeveralAnswersModel
import org.json.JSONObject
import org.json.JSONTokener
import java.io.IOException

class JSONHelper(private val context: Context){
    val fileName = "poll.json"

    fun getAllQuestions() : PollModel{
        val quizModelList : ArrayList<QuizModel> = arrayListOf()
        val severalAnswersModelList: ArrayList<SeveralAnswersModel> = arrayListOf()
        val enterAnswerModelList: ArrayList<EnterAnswerModel> = arrayListOf()
        val questionJsonArray = (JSONTokener(getJSONString()).nextValue() as JSONObject).getJSONArray("poll")

        for(i in 0 until questionJsonArray.length()){
            val it = questionJsonArray.get(i) as JSONObject
            val type = it.getString("type")
            when(type){
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

        return QuizModel(id, question, answers, correctAnswer)
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

        return SeveralAnswersModel(id, question, answers, correctAnswers)
    }

    private fun getEnterAnswerModelFromJsonArray(it : JSONObject) : EnterAnswerModel{
        val id = it.getInt("id")
        val question = it.getString("question")
        val correctAnswer = it.getString("correct_answer")
        return EnterAnswerModel(id, question, correctAnswer)
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