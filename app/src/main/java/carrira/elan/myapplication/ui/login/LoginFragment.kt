package carrira.elan.myapplication.ui.login

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.viewModels
import carrira.elan.myapplication.R

class LoginFragment : Fragment() {

    companion object {
        fun newInstance() = LoginFragment()
    }

    private lateinit var tvLoginHint : TextView
    private val viewModel : LoginViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        val view = inflater.inflate(R.layout.fragment_login, container, false)

        val etEnterLogin : EditText = view.findViewById(R.id.et_enter_login)
        val etEnterPass : EditText = view.findViewById(R.id.et_enter_pass)
        val btnLogin : Button = view.findViewById(R.id.btn_login)
        tvLoginHint = view.findViewById(R.id.tv_login_hint)

        btnLogin.setOnClickListener {
            checkEnteredData(
                etEnterLogin.text.toString(),
                etEnterPass.text.toString())
        }

        return view
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    fun checkEnteredData(login : String, pass : String){
        val loginBool = checkLogin(login)
        val passBool = checkPass(pass)
        lateinit var message : String
        tvLoginHint.visibility = View.VISIBLE

        if(loginBool && passBool) {
            message = requireContext().resources.getString(R.string.correct_login_and_pass)
            tvLoginHint.background = resources.getDrawable(R.drawable.correct_login_background, null)
        }
        else{
            if(!loginBool && passBool)
                message = requireContext().resources.getString(R.string.incorrect_login)
            else if(loginBool && !passBool)
                message = requireContext().resources.getString(R.string.incorrect_pass)
            else
                message = requireContext().resources.getString(R.string.incorrect_login) + "\n\n" +
                        requireContext().resources.getString(R.string.incorrect_pass)
            tvLoginHint.background = resources.getDrawable(R.drawable.incorrect_login_background, null)
        }

        tvLoginHint.text = message
    }

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