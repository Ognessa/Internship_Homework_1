package carrira.elan.myapplication.ui.poll_result

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import carrira.elan.myapplication.ui.pages.models.PollModel

class PollResultViewModel : ViewModel() {
    private val _result = MutableLiveData<String>()
    val result : LiveData<String> get() = _result

    fun checkResult(list : PollModel) {
        val allAnswers = list.getQuizModelList().size +
                list.getSeveralAnswersModelList().size + list.getEnterAnswerModelList().size
        var correctAnswers = 0

        for(it in list.getQuizModelList()){
            if(it.checkAnswer()) correctAnswers++
        }
        for(it in list.getSeveralAnswersModelList()){
            if(it.checkAnswer()) correctAnswers++
        }
        for(it in list.getEnterAnswerModelList()){
            if(it.checkAnswer()) correctAnswers++
        }

        val res = "$correctAnswers/$allAnswers"
        _result.postValue(res)
    }
}