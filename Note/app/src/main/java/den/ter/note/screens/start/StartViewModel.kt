package den.ter.note.screens.start

import android.annotation.SuppressLint
import android.app.Application
import android.text.Editable
import android.view.View
import android.widget.TextView
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import den.ter.note.REPOSITORY
import den.ter.note.adapter.NoteAdapter
import den.ter.note.db.NoteDatabase
import den.ter.note.db.repository.NoteRealization
import den.ter.note.model.NoteModel

class StartViewModel(application: Application): AndroidViewModel(application) {

    val context = application


    fun initDatabase(){
        val daoNote = NoteDatabase.getInstance(context).getNoteDao()
        REPOSITORY = NoteRealization(daoNote)
    }

    fun getAllNotes(): LiveData<List<NoteModel>>{
        return REPOSITORY.allNotes
    }

    @SuppressLint("NotifyDataSetChanged")
    fun search(s: Editable, adapter: NoteAdapter, tv1: TextView, listNotes: List<NoteModel>){
        if(s.isEmpty()){
            adapter.setList(listNotes.asReversed())
            if(listNotes.isEmpty()){
                tv1.text = "Пока заметок нет..."
                tv1.visibility = View.VISIBLE
            }else{
                tv1.visibility = View.INVISIBLE
            }
        }else{
            adapter.setList(listNotes.filter {
                it.title.startsWith(s.toString(),true) || it.title.contains(s.toString(),true)
            }.asReversed())
            if(listNotes.filter {
                    it.title.startsWith(
                        s.toString(),
                        true
                    ) || it.title.contains(s.toString(), true)
                }.isEmpty()){
                tv1.text = "Таких заметок не найдено..."
                tv1.visibility = View.VISIBLE
            }else{
                tv1.visibility = View.INVISIBLE
            }
        }
        adapter.notifyDataSetChanged()
    }

}