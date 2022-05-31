package carrira.elan.myapplication.ui.animation

import android.os.*
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import carrira.elan.myapplication.R

class AnimationFragment : Fragment() {

    companion object {
        fun newInstance() = AnimationFragment()
    }

    private val viewModel: AnimationViewModel by viewModels()
    private lateinit var handler : Handler

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        val view = inflater.inflate(R.layout.fragment_animation, container, false)
        viewModel.moveToLoginFragment()
        viewModel.isValid().observe(viewLifecycleOwner, Observer {
            if(it){
                val action = AnimationFragmentDirections.actionAnimationFragmentToLoginFragment()
                Navigation.findNavController(view).navigate(action)
            }
        })

        return view
    }
}