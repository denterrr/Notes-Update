package den.ter.note.screens.addnote

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import den.ter.note.APP
import den.ter.note.R
import den.ter.note.databinding.FragmentAddNoteBinding
import den.ter.note.model.NoteModel
import den.ter.note.screens.detail.DetailViewModel

class AddNoteFragment : Fragment() {

    lateinit var binding: FragmentAddNoteBinding
    lateinit var currentNote: NoteModel



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddNoteBinding.inflate(layoutInflater, container, false)


        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)
        try{
            currentNote = arguments?.getSerializable("note_edit") as NoteModel
            val title = currentNote.title
            val desc = currentNote.description
            binding.etAddTitle.setText(title)
            binding.etAddDesc.setText(desc)
            if(title.isNotEmpty()){
                binding.tv.text = "Изменить заметку"
            }

        }catch(e:Exception){}

        init()
    }

    @SuppressLint("ResourceAsColor")
    private fun init() {


        val viewModel = ViewModelProvider(this).get(AddNoteViewModel::class.java)

        binding.ivCheck.setOnClickListener {
            val title = binding.etAddTitle.text.toString()
            if (title.isEmpty()) {
                Toast.makeText(APP, "Введите название", Toast.LENGTH_SHORT).show()
            } else {
                try{
                    viewModel.delete(currentNote) {}
                }catch(e:Exception){}
                binding.ivCheck.setBackgroundColor(R.color.btn_click)
                val description = binding.etAddDesc.text.toString()
                viewModel.insert(NoteModel(title = title, description = description)) {}
                APP.navController.navigate(R.id.action_addNoteFragment_to_startFragment)
            }
        }

        binding.ivBack.setOnClickListener {
            if (binding.etAddTitle.text.isNotEmpty() || binding.etAddDesc.text.isNotEmpty()) {
                showDialog()
            } else {
                APP.navController.navigate(R.id.action_addNoteFragment_to_startFragment)
            }
        }
    }

    private fun showDialog() {
        val listener = DialogInterface.OnClickListener { _, which ->
            when (which) {
                DialogInterface.BUTTON_POSITIVE -> {
                    APP.navController.navigate(R.id.action_addNoteFragment_to_startFragment)
                }
                DialogInterface.BUTTON_NEGATIVE -> null
                DialogInterface.BUTTON_NEUTRAL -> null

            }
        }
        val dialog = AlertDialog.Builder(APP)
            .setCancelable(false)
            .setTitle("Вы уверены что хотите вернуться?")
            .setMessage("Введенные вами данные исчезнут")
            .setPositiveButton("Да", listener)
            .setNegativeButton("Нет", listener)
            .setNeutralButton("Отмена", listener)
            .create()
        dialog.show()
    }

}