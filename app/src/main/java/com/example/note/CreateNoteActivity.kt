package com.example.note

import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.*
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.note.databinding.ActivityCreateNoteBinding
import com.example.note.entity.Note
import com.example.note.room.NoteDatabase
import com.google.android.material.bottomsheet.BottomSheetBehavior
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*
import java.util.jar.Manifest

class CreateNoteActivity : AppCompatActivity() {

    lateinit var binding: ActivityCreateNoteBinding

    lateinit var selectedNoteColor:String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityCreateNoteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.back.setOnClickListener {
            onBackPressed()
        }

        binding.datetime.text =
            SimpleDateFormat("EEEE ,dd MMMM yyyy HH:mm a", Locale.getDefault()).format(Date())

        binding.done.setOnClickListener {
            saveNote()
            setResult(RESULT_OK, intent)
            finish()
        }
        addmycolor()
        selectedNoteColor ="#333333"
        setNoteIndColor()
    }
    fun saveNote(){
        if(binding.noteTitle.text.trim().isEmpty()){
            Toast.makeText(this, "Please give a title", Toast.LENGTH_SHORT).show()
            return
        }
        else if(binding.noteSubTitle.text.trim().isEmpty() && binding.noteinput.text.trim().isEmpty()){
            Toast.makeText(this, "Note can't be empty", Toast.LENGTH_SHORT).show()
            return
        }
        val note =Note(0,
            binding.noteTitle.text.toString(),
            binding.datetime.text.toString(),
            binding.noteSubTitle.text.toString(),
            binding.noteinput.text.toString(),
            null,
            selectedNoteColor
        )

        GlobalScope.launch {
            val database =NoteDatabase.getDatabase(this@CreateNoteActivity)
            database.noteDao().insert(note)
        }

    }
    fun addmycolor(){
        val layout = findViewById<RelativeLayout>(R.id.my_color)
        val bottomSheetBehavior:BottomSheetBehavior<RelativeLayout> =BottomSheetBehavior.from(layout)
        layout.findViewById<TextView>(R.id.colortext).setOnClickListener {
            if(bottomSheetBehavior.state != BottomSheetBehavior.STATE_EXPANDED){
                bottomSheetBehavior.state=BottomSheetBehavior.STATE_EXPANDED
            }
            else{
                bottomSheetBehavior.state=BottomSheetBehavior.STATE_COLLAPSED
            }
        }
        layout.findViewById<ImageView>(R.id.img1).setOnClickListener {
            selectedNoteColor = "#333333"
            layout.findViewById<ImageView>(R.id.img1).setImageResource(R.drawable.ic_check)
            layout.findViewById<ImageView>(R.id.img2).setImageResource(0)
            layout.findViewById<ImageView>(R.id.img3).setImageResource(0)
            layout.findViewById<ImageView>(R.id.img4).setImageResource(0)
            layout.findViewById<ImageView>(R.id.img5).setImageResource(0)
            setNoteIndColor()
        }
        layout.findViewById<ImageView>(R.id.img2).setOnClickListener {
            selectedNoteColor = "#FDBE3B"
            layout.findViewById<ImageView>(R.id.img1).setImageResource(0)
            layout.findViewById<ImageView>(R.id.img2).setImageResource(R.drawable.ic_check)
            layout.findViewById<ImageView>(R.id.img3).setImageResource(0)
            layout.findViewById<ImageView>(R.id.img4).setImageResource(0)
            layout.findViewById<ImageView>(R.id.img5).setImageResource(0)
            setNoteIndColor()
        }
        layout.findViewById<ImageView>(R.id.img3).setOnClickListener {
            selectedNoteColor = "#FF4842"
            layout.findViewById<ImageView>(R.id.img1).setImageResource(0)
            layout.findViewById<ImageView>(R.id.img2).setImageResource(0)
            layout.findViewById<ImageView>(R.id.img3).setImageResource(R.drawable.ic_check)
            layout.findViewById<ImageView>(R.id.img4).setImageResource(0)
            layout.findViewById<ImageView>(R.id.img5).setImageResource(0)
            setNoteIndColor()
        }
        layout.findViewById<ImageView>(R.id.img4).setOnClickListener {
            selectedNoteColor = "#3A52Fc"
            layout.findViewById<ImageView>(R.id.img1).setImageResource(R.drawable.ic_check)
            layout.findViewById<ImageView>(R.id.img2).setImageResource(0)
            layout.findViewById<ImageView>(R.id.img3).setImageResource(0)
            layout.findViewById<ImageView>(R.id.img4).setImageResource(R.drawable.ic_check)
            layout.findViewById<ImageView>(R.id.img5).setImageResource(0)
            setNoteIndColor()
        }
        layout.findViewById<ImageView>(R.id.img5).setOnClickListener {
            selectedNoteColor = "#FF018786"
            layout.findViewById<ImageView>(R.id.img1).setImageResource(0)
            layout.findViewById<ImageView>(R.id.img2).setImageResource(0)
            layout.findViewById<ImageView>(R.id.img3).setImageResource(0)
            layout.findViewById<ImageView>(R.id.img4).setImageResource(0)
            layout.findViewById<ImageView>(R.id.img5).setImageResource(R.drawable.ic_check)
            setNoteIndColor()
        }
        layout.findViewById<LinearLayout>(R.id.linImage).setOnClickListener {
            bottomSheetBehavior.state =BottomSheetBehavior.STATE_COLLAPSED

            if(ContextCompat.checkSelfPermission(applicationContext, android.Manifest.permission.READ_EXTERNAL_STORAGE)
            != PackageManager.PERMISSION_GRANTED){
                ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE),1)
            }
            else{
                selectImage()
            }
        }


    }

    private fun selectImage() {
        val intent = Intent(Intent.ACTION_PICK , MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        if(intent.resolveActivity(packageManager) != null){
            resultluncher.launch(intent)
        }
    }

    private fun setNoteIndColor(){
        binding.colorbar.setBackgroundColor(Color.parseColor(selectedNoteColor))
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if(requestCode ==1 && grantResults.isNotEmpty()){
            if(grantResults[0] == PackageManager.PERMISSION_GRANTED){
                selectImage()
            }
            else{
                Toast.makeText(this, "Permisson Denied", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private var resultluncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
        if(it.resultCode == RESULT_OK){
            val imguri: Uri? = it.data?.data
            if(it != null &&  imguri!= null){
                try {
                    val inputStream =contentResolver.openInputStream(imguri)
                    val bitmap = BitmapFactory.decodeStream(inputStream)
                    binding.noteImage.setImageBitmap(bitmap)
                    binding.noteImage.visibility = View.VISIBLE
                }
                catch (ex:Exception){
                    Toast.makeText(this, ex.toString(), Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

}