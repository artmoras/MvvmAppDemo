package sinno.seacs.mvvmappdemo.activities

import android.app.Activity
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import sinno.seacs.mvvmappdemo.databinding.ActivityAddNoteBinding
import sinno.seacs.mvvmappdemo.models.EntityNote
import java.text.SimpleDateFormat
import java.util.*

class AddNoteActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddNoteBinding
    private lateinit var note: EntityNote
    private lateinit var oldNote: EntityNote
    private var isUpdate = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddNoteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        try {
            oldNote = if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                intent.getSerializableExtra("currentNote", EntityNote::class.java)!!
            } else {
                intent.getSerializableExtra("currentNote") as EntityNote
            }
            binding.etTitle.setText(oldNote.title)
            binding.etNote.setText(oldNote.note)
            isUpdate = true
        } catch (e : Exception) {
            e.printStackTrace()
        }
        binding.imgCheck.setOnClickListener {
            val title = binding.etTitle.text.toString()
            val noteDesc = binding.etNote.text.toString()
            if (title.isNotEmpty() || noteDesc.isNotEmpty()) {
                val formatter = SimpleDateFormat("EEE, d MMM yyyy HH:mm a")
                note = if(isUpdate){
                    EntityNote(oldNote.id, title, noteDesc, formatter.format(Date()).toString())
                } else {
                    EntityNote(null, title, noteDesc, formatter.format(Date()))
                }
                val intent = Intent()
                intent.putExtra("note", note)
                setResult(Activity.RESULT_OK, intent)
                finish()
            } else {
                Toast.makeText(this@AddNoteActivity, "Please enter some data", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
        }
        binding.imgBackArrow.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }
    }
}