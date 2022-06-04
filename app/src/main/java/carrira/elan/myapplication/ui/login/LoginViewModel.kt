package carrira.elan.myapplication.ui.login

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import carrira.elan.myapplication.R

enum class MessageValues {
    ALL_CORRECT, INCORRECT_LOGIN, INCORRECT_PASSWORD, ALL_INCORRECT
}

class LoginViewModel : ViewModel() {

    private val _message = MutableLiveData<MessageValues>()

    fun message(context : Context) : LiveData<String> {
        val str = MutableLiveData<String>()
        when(_message){
            MutableLiveData(MessageValues.ALL_CORRECT) ->{
                str.postValue(context.resources.getString(R.string.correct_login_and_pass))}
            MutableLiveData(MessageValues.INCORRECT_LOGIN) ->{
                str.postValue(context.resources.getString(R.string.incorrect_login))
            }
            MutableLiveData(MessageValues.INCORRECT_PASSWORD) ->{
                str.postValue(context.resources.getString(R.string.incorrect_pass))
            }
            MutableLiveData(MessageValues.ALL_INCORRECT) ->{
                str.postValue(context.resources.getString(R.string.incorrect_login) +
                "\n\n" + context.resources.getString(R.string.incorrect_pass))
            }
        }
        return str
    }

    fun checkEnteredData(login : String, pass : String){
        val loginBool = checkLogin(login)
        val passBool = checkPass(pass)

        if(loginBool && passBool) {
            _message.postValue(MessageValues.ALL_CORRECT)
        }
        else{
            if(!loginBool && passBool)
                _message.postValue(MessageValues.INCORRECT_LOGIN)
            else if(loginBool && !passBool)
                _message.postValue(MessageValues.INCORRECT_PASSWORD)
            else
                _message.postValue(MessageValues.ALL_INCORRECT)
        }
    }

    private fun checkLogin(login : String) : Boolean{
        if(login.contains(" ") || login.isEmpty())
            return false
        return true
    }

    private fun checkPass(pass : String) : Boolean{
        val regexNum = Regex("[0-9]")
        val regexSmallLetters = Regex("[a-z]")
        val regexBigLetters = Regex("[A-Z]")
        val length = 8

        if(pass.contains(regexNum)
            && pass.contains(regexSmallLetters)
            && pass.contains(regexBigLetters)
            && pass.length >= length)
            return true
        return false
    }
}