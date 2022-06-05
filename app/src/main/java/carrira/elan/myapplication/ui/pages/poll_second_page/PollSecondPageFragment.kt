package carrira.elan.myapplication.ui.pages.poll_second_page

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import carrira.elan.myapplication.R

class PollSecondPageFragment : Fragment() {

    companion object {
        fun newInstance() = PollSecondPageFragment()
    }

    private lateinit var viewModel: PollSecondPageViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        return inflater.inflate(R.layout.fragment_poll_second_page, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(PollSecondPageViewModel::class.java)
        // TODO: Use the ViewModel
    }

}