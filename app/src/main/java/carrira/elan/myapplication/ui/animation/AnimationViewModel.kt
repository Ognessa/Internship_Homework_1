package carrira.elan.myapplication.ui.animation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class AnimationViewModel : ViewModel() {
    private val _isValid = MutableLiveData<Boolean>()

    fun isValid() : LiveData<Boolean> {
        return _isValid
    }

    fun moveToLoginFragment(){
        viewModelScope.launch {
            delay(5000)
            _isValid.postValue(true)
        }
    }
}