package carrira.elan.myapplication.ui.pages.models

class SeveralAnswersModel(
    private val id : Int,
    private val question : String,
    private val answers : ArrayList<String>,
    private val correctAnswers : ArrayList<Int>,
    private var userAnswers : ArrayList<Boolean>){

    fun getId() : Int {return id}
    fun getQuestion() : String {return question}
    fun getAnswers() : ArrayList<String> {return answers}

    fun getUserAnswers() : ArrayList<Boolean> {return userAnswers}
    fun setUserAnswers(ua : ArrayList<Boolean>) {
        userAnswers = ua
    }

    fun checkAnswer() : Boolean{
        for(i in 0 until userAnswers.size){
            if((correctAnswers.contains(i) && !userAnswers[i])
                || (!correctAnswers.contains(i) && userAnswers[i]))
                return false
        }
        return true
    }
}