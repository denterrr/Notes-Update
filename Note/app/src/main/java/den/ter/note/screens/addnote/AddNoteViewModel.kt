package den.ter.note.screens.addnote

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import den.ter.note.REPOSITORY
import den.ter.note.model.NoteModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AddNoteViewModel: ViewModel() {

    fun insert(noteModel: NoteModel, onSuccess:() -> Unit){
        viewModelScope.launch(Dispatchers.IO) {
            REPOSITORY.insertNote(noteModel){
                onSuccess()
            }
        }
    }
    fun delete(noteModel: NoteModel, onSuccess:() -> Unit){
        viewModelScope.launch(Dispatchers.IO) {
            REPOSITORY.deleteNote(noteModel){
                onSuccess()
            }
        }
    }



}