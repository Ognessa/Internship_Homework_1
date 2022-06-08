package carrira.elan.myapplication.ui.pages.poll_first_page

import android.content.Context
import android.widget.RadioButton
import android.widget.RadioGroup
import androidx.lifecycle.ViewModel
import carrira.elan.myapplication.JSONHelper
import carrira.elan.myapplication.ui.pages.models.QuizModel

class PollFirstPageViewModel : ViewModel() {

    fun createRadioGroup(context : Context, it : QuizModel) : RadioGroup{
        val radioGroup = RadioGroup(context)
        for(answer in it.getAnswers()){
            val radioButton = RadioButton(context)
            radioButton.text = answer
            radioGroup.addView(radioButton)
        }
        return radioGroup
    }

}