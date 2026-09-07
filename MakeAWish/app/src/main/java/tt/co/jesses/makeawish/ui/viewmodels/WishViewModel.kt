package tt.co.jesses.makeawish.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import tt.co.jesses.makeawish.data.local.Wish
import tt.co.jesses.makeawish.domain.repository.WishRepository

class WishViewModel(private val repository: WishRepository) : ViewModel() {
    private val _wishes = MutableStateFlow<List<Wish>>(emptyList())
    val wishes: StateFlow<List<Wish>> = _wishes.asStateFlow()

    init {
        viewModelScope.launch {
            _wishes.value = repository.getAllWishes().first()
        }
    }

    fun insertWish(wish: Wish) {
        viewModelScope.launch {
            repository.insertWish(wish)
            _wishes.value = repository.getAllWishes().first()
        }
    }

    fun deleteWish(wish: Wish) {
        viewModelScope.launch {
            repository.deleteWish(wish)
            _wishes.value = repository.getAllWishes().first()
        }
    }
}
