package carrira.elan.myapplication.ui.pages.poll_second_page

import android.content.Context
import android.widget.CheckBox
import android.widget.RadioGroup
import androidx.lifecycle.ViewModel
import carrira.elan.myapplication.ui.pages.models.SeveralAnswersModel

class PollSecondPageViewModel : ViewModel() {

    fun createRadioGroup(context : Context, it : SeveralAnswersModel) : RadioGroup {
        val radioGroup = RadioGroup(context)
        for(answer in it.getAnswers()){
            val checkBox = CheckBox(context)
            checkBox.text = answer
            radioGroup.addView(checkBox)
        }
        return radioGroup
    }

}