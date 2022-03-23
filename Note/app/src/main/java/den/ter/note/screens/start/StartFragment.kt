package den.ter.note.screens.start

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import den.ter.note.APP
import den.ter.note.R
import den.ter.note.adapter.NoteAdapter
import den.ter.note.databinding.FragmentStartBinding
import den.ter.note.model.NoteModel


class StartFragment : Fragment() {

    lateinit var binding: FragmentStartBinding
    lateinit var recyclerView: RecyclerView
    lateinit var adapter: NoteAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentStartBinding.inflate(layoutInflater,container,false)
        binding.btnNext.setColorFilter(Color.rgb(205,196,196))
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    private fun init() {
        val viewModel = ViewModelProvider(this).get(StartViewModel::class.java)
        viewModel.initDatabase()
        recyclerView = binding.rvNotes
        val et = binding.etSearch
        val s = et.text
        val tv1 = binding.tvNet
        adapter = NoteAdapter()
        recyclerView.adapter = adapter
        viewModel.getAllNotes().observe(this,{listNotes ->


            if(s.isEmpty()){
                adapter.setList(listNotes.asReversed())
                if(listNotes.isEmpty()){
                    tv1.visibility = View.VISIBLE
                }else{
                    tv1.visibility = View.INVISIBLE
                }
            }
            et.addTextChangedListener(object: TextWatcher{
                override fun afterTextChanged(p0: Editable?) {

                }

                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                }

                @SuppressLint("NotifyDataSetChanged")
                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                    viewModel.search(s, recyclerView.adapter as NoteAdapter,tv1,listNotes)

                }
            })

        })

        binding.btnNext.setOnClickListener{
            val code = "0"
            val bundle = Bundle()
            bundle.putString("code",code)
            APP.navController.navigate((R.id.action_startFragment_to_addNoteFragment),bundle)
        }
    }


    companion object{
        fun clickNote(noteModel: NoteModel){
            val bundle = Bundle()
            bundle.putSerializable("note",noteModel)

            APP.navController.navigate(R.id.action_startFragment_to_detailFragment, bundle)
        }

    }

}