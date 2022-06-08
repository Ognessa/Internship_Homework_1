package carrira.elan.myapplication.ui.pages.models

class SeveralAnswersModel(
    private val id : Int,
    private val question : String,
    private val answers : ArrayList<String>,
    private val correctAnswers : ArrayList<Int>){

    fun getId() : Int {return id}
    fun getQuestion() : String {return question}
    fun getAnswers() : ArrayList<String> {return answers}
    fun getCorrectAnswers() : ArrayList<Int> {return correctAnswers}
}