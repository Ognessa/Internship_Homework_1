package carrira.elan.myapplication.ui.poll

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import carrira.elan.myapplication.R

class PollFragment : Fragment() {

    companion object {
        fun newInstance() = PollFragment()
    }

    private lateinit var viewModel: PollViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        return inflater.inflate(R.layout.fragment_poll, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(PollViewModel::class.java)
        // TODO: Use the ViewModel
    }

}