package carrira.elan.myapplication.ui.start_poll

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import carrira.elan.myapplication.R

class StartPollFragment : Fragment() {

    companion object {
        fun newInstance() = StartPollFragment()
    }

    private lateinit var viewModel: StartPollViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        return inflater.inflate(R.layout.fragment_start_poll, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(StartPollViewModel::class.java)
        // TODO: Use the ViewModel
    }

}