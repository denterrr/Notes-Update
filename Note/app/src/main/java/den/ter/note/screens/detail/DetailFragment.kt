package den.ter.note.screens.detail

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import den.ter.note.APP
import den.ter.note.R
import den.ter.note.databinding.FragmentDetailBinding
import den.ter.note.model.NoteModel

class DetailFragment : Fragment() {

    lateinit var binding: FragmentDetailBinding
    lateinit var currentNote: NoteModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDetailBinding.inflate(layoutInflater,container,false)
        currentNote = arguments?.getSerializable("note") as NoteModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    private fun init() {
        val viewModel = ViewModelProvider(this).get(DetailViewModel::class.java)
        binding.tvTitleDetail.text = currentNote.title
        binding.tvDescDetail.text = currentNote.description

        binding.ivDelete.setOnClickListener {
            showDialog()
        }

        binding.ivBack.setOnClickListener {

                APP.navController.navigate(R.id.action_detailFragment_to_startFragment)
            }
        binding.ivEdit.setOnClickListener {
            val code = "1"
            val bundle = Bundle()
            bundle.putSerializable("note_edit",NoteModel(currentNote.id,currentNote.title,currentNote.description))
            bundle.putString("code", code)
            APP.navController.navigate(R.id.action_detailFragment_to_addNoteFragment,bundle)

        }


    }

    private fun showDialog(){
        val viewModel = ViewModelProvider(this).get(DetailViewModel::class.java)
        val listener = DialogInterface.OnClickListener{_,which ->
            when(which){
                DialogInterface.BUTTON_POSITIVE -> {
                    viewModel.delete(currentNote){}
                    APP.navController.navigate(R.id.action_detailFragment_to_startFragment)
                }
                DialogInterface.BUTTON_NEGATIVE -> null
                DialogInterface.BUTTON_NEUTRAL -> null

            }
        }
        val dialog = AlertDialog.Builder(APP)
            .setCancelable(false)
            .setTitle("Вы уверены что хотите удалить заметку?")
            .setMessage("Эти данные не получится восстановить")
            .setPositiveButton("Да",listener)
            .setNegativeButton("Нет",listener)
            .setNeutralButton("Отмена",listener)
            .create()
        dialog.show()
    }

}