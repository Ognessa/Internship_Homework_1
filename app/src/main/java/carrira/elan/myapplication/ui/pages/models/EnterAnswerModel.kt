package carrira.elan.myapplication.ui.pages.models

class EnterAnswerModel(
    private val id : Int,
    private val question : String,
    private val correctAnswer : String,
    private var userAnswer : String) {

    fun getId() : Int {return id}
    fun getQuestion() : String {return question}

    fun getUserAnswer() : String {return userAnswer}
    fun setUserAnswer(ua : String) {userAnswer = ua}

    fun checkAnswer() : Boolean{
        if(userAnswer == correctAnswer)
            return true

        return false
    }
}