package tt.co.jesses.makeawish.ui.viewmodels

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.StateFlow
import tt.co.jesses.makeawish.data.local.Wish
import tt.co.jesses.makeawish.domain.repository.WishRepository

class WishViewModel(
    private val wishRepository: WishRepository
) : ViewModel() {

    val wishes = wishRepository.getAllWishes()

    fun insertWish(wish: Wish) {
        // This will need to be launched in a coroutine scope in a real app
        // For now, we just define the function
    }

    fun deleteWish(wish: Wish) {
        // This will need to be launched in a coroutine scope in a real app
    }
}
