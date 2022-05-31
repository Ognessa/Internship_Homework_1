package carrira.elan.myapplication.ui.login

import androidx.lifecycle.ViewModel

class LoginViewModel : ViewModel() {

    fun checkLogin(login : String) : Boolean{
        if(login.contains(" ") || login.isEmpty())
            return false
        return true
    }

    fun checkPass(pass : String) : Boolean{
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