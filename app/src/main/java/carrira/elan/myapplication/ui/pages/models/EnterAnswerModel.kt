package carrira.elan.myapplication.ui.pages.models

class EnterAnswerModel(
    private val id : Int,
    private val question : String,
    private val correctAnswer : String) {

    fun getId() : Int {return id}
    fun getQuestion() : String {return question}
    fun getCorrectAnswer() : String {return correctAnswer}
}