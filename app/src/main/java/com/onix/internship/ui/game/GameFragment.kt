package com.onix.internship.ui.game

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.view.ContextThemeWrapper
import androidx.appcompat.widget.AppCompatButton
import androidx.navigation.fragment.findNavController
import com.onix.internship.R
import com.onix.internship.arch.BaseFragment
import com.onix.internship.databinding.DialogButtonBinding
import com.onix.internship.databinding.GameFragmentBinding
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.jar.Attributes

class GameFragment : BaseFragment<GameFragmentBinding>(R.layout.game_fragment) {

    override val viewModel: GameViewModel by viewModel()
    var pause_flag = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = super.onCreateView(inflater, container, savedInstanceState)

        binding.fabPauseMenu.setOnClickListener { showPauseMenu() }

        binding.llTextContainer.setOnClickListener { viewModel.nextLine() }

        return view
    }

    override fun setObservers() {
        viewModel.currentText.observe(this){
            binding.tvText.text = it
        }
        viewModel.hasMenu.observe(this){
            if(it){
                showDialogMenu(binding)
                binding.llTextContainer.isClickable = false
            }
            else{
                binding.llDialogContainer.removeAllViewsInLayout()
                binding.llTextContainer.isClickable = true
            }
        }
    }

    private fun showPauseMenu(){
        val action = GameFragmentDirections.actionGameFragmentToPauseFragment()
        findNavController().navigate(action)
    }

    private fun showDialogMenu(binding : GameFragmentBinding){
        val buttonsList = viewModel.getMenuButtons()
        buttonsList.forEach {
            val layout = LayoutInflater.from(requireContext()).inflate(R.layout.dialog_button, null)
            val button = layout.findViewById<AppCompatButton>(R.id.btn_dialog)
            button.text = it.title
            button.setOnClickListener { i -> viewModel.jump(it.jump) }
            binding.llDialogContainer.addView(layout)
        }
    }

}