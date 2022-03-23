package den.ter.note.screens.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import den.ter.note.REPOSITORY
import den.ter.note.model.NoteModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DetailViewModel: ViewModel() {
    fun delete(noteModel: NoteModel, onSuccess:() -> Unit){
        viewModelScope.launch(Dispatchers.IO) {
            REPOSITORY.deleteNote(noteModel){
                onSuccess()
            }
        }
    }
}