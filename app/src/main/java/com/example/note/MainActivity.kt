package com.example.note

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.note.databinding.ActivityMainBinding
import com.example.note.entity.Note
import com.example.note.room.NoteDatabase

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    private val REQUEST_CODE_ADD_NOTE = 1

    var list = ArrayList<Note>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.imageAddNoteMain.setOnClickListener {
            resultluncher.launch(Intent(applicationContext,CreateNoteActivity::class.java))
        }
        val adapter =NoteAdapter(list)
        binding.noteRecyclerView.layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)

        NoteDatabase.getDatabase(this@MainActivity).noteDao().getallNote().observe(this, Observer {
            if(list.size == 0){
                list = it as ArrayList<Note>
                binding.noteRecyclerView.adapter = NoteAdapter(list)
            }
            else{
                list.add(0,it[0])
                adapter.notifyItemInserted(0)
            }

        })


    }
    private var resultluncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
        if(it.resultCode == REQUEST_CODE_ADD_NOTE && it.resultCode == RESULT_OK){
            Toast.makeText(this, "Note Saved", Toast.LENGTH_SHORT).show()
        }
    }

}