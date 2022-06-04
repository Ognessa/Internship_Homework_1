package carrira.elan.myapplication.ui.login

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import carrira.elan.myapplication.R
import carrira.elan.myapplication.databinding.FragmentLoginBinding

class LoginFragment : Fragment() {
    private val viewModel : LoginViewModel by viewModels()
    lateinit var binding : FragmentLoginBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_login, container, false)
        binding.loginViewModel = viewModel

        //TODO доробити data binding, не відображає текст
        viewModel.message(requireContext()).observe(viewLifecycleOwner, Observer {
            binding.tvLoginHint.text = it
        })

        binding.btnLogin.setOnClickListener{
            viewModel.checkEnteredData(
                binding.etEnterLogin.text.toString(),
                binding.etEnterPass.text.toString())
        }

        return binding.root
    }
}