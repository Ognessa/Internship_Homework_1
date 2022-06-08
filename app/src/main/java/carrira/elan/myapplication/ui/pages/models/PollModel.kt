package carrira.elan.myapplication.ui.pages.models

class PollModel(
    private val quizModelList : ArrayList<QuizModel>,
    private val severalAnswersModelList: ArrayList<SeveralAnswersModel>,
    private val enterAnswerModelList: ArrayList<EnterAnswerModel>) {

    fun getQuizModelList () : ArrayList<QuizModel> {return quizModelList}
    fun getSeveralAnswersModelList () : ArrayList<SeveralAnswersModel> {return severalAnswersModelList}
    fun getEnterAnswerModelList () : ArrayList<EnterAnswerModel> {return enterAnswerModelList}
}