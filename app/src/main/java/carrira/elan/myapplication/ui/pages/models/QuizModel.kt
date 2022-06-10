package carrira.elan.myapplication.ui.pages.models

class QuizModel(
    private val id : Int,
    private val question : String,
    private val answers : ArrayList<String>,
    private val correctAnswer : Int,
    private var userAnswer : Int){

    fun getId() : Int {return id}
    fun getQuestion() : String {return question}
    fun getAnswers() : ArrayList<String> {return answers}

    fun getUserAnswer() : Int {return userAnswer}
    fun setUserAnswer(ua : Int) {userAnswer = ua}

    fun checkAnswer() : Boolean{
        if(userAnswer == correctAnswer)
            return true

        return false
    }
}