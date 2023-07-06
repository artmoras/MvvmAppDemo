package sinno.seacs.mvvmappdemo

import android.app.Activity
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.LinearLayout
import android.widget.PopupMenu
import android.widget.SearchView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.cardview.widget.CardView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import sinno.seacs.mvvmappdemo.activities.AddNoteActivity
import sinno.seacs.mvvmappdemo.adapters.NotesRecyclerViewAdapter
import sinno.seacs.mvvmappdemo.database.Db
import sinno.seacs.mvvmappdemo.databinding.ActivityMainBinding
import sinno.seacs.mvvmappdemo.models.EntityNote
import sinno.seacs.mvvmappdemo.viewmodels.NoteViewModel

class MainActivity : AppCompatActivity(), NotesRecyclerViewAdapter.NotesClickListener, PopupMenu.OnMenuItemClickListener {

    private lateinit var binding: ActivityMainBinding
    private lateinit var db: Db
    lateinit var vm: NoteViewModel
    lateinit var notesRecyclerViewAdapter: NotesRecyclerViewAdapter
    lateinit var selectedNote: EntityNote

    private val updateNote = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){ result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val note = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                result.data?.getSerializableExtra("note", EntityNote::class.java)
            } else {
                result.data?.getSerializableExtra("note") as? EntityNote
            }
            if (note != null) {
                vm.updateNote(note)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initUI()

        vm = ViewModelProvider(this)[NoteViewModel::class.java]
        vm.allNotes.observe(this) {
            list ->
            list?.let {
                notesRecyclerViewAdapter.updateList(list)
            }
        }
        db = Db.getDatabase(this)
    }

    private fun initUI() {
        binding.recyclerView.setHasFixedSize(true)
        binding.recyclerView.layoutManager = StaggeredGridLayoutManager(2,LinearLayout.VERTICAL)
        notesRecyclerViewAdapter = NotesRecyclerViewAdapter(this, this)
        binding.recyclerView.adapter = notesRecyclerViewAdapter

        val getContent = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if(result.resultCode == Activity.RESULT_OK){
                val note = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    result.data?.getSerializableExtra("note", EntityNote::class.java)
                } else {
                    result.data?.getSerializableExtra("note") as? EntityNote
                }
                if(note != null) {
                    vm.insertNote(note)
                }
            }
        }

        binding.fbAddNote.setOnClickListener {
            val intent = Intent(this, AddNoteActivity::class.java)
            getContent.launch(intent)
        }

        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(p0: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if(newText != null) {
                    notesRecyclerViewAdapter.filterList(newText)
                }
                return true
            }
        })
    }

    private fun popUpDisplay(cardView: CardView) {
        val popup = PopupMenu(this, cardView)
        popup.setOnMenuItemClickListener(this@MainActivity)
        popup.inflate(R.menu.pop_up_menu)
        popup.show()
    }

    override fun onNoteClicked(note: EntityNote) {
        val intent = Intent(this@MainActivity, AddNoteActivity::class.java)
        intent.putExtra("currentNote", note)
        updateNote.launch(intent)
    }

    override fun onNoteLongClicked(note: EntityNote, cardView: CardView) {
        selectedNote = note
        popUpDisplay(cardView)
    }

    override fun onMenuItemClick(item: MenuItem?): Boolean {
        if (item?.itemId == R.id.delete_note){
            vm.deleteNote(selectedNote)
            return  true
        }
        return false
    }
}