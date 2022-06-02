package id.web.rpgfantasy.todoapp.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import id.web.rpgfantasy.todoapp.R
import id.web.rpgfantasy.todoapp.viewmodel.DetailTodoViewModel
import kotlinx.android.synthetic.main.fragment_create_todo.*

/**
 * A simple [Fragment] subclass.
 * Use the [EditTodoFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class EditTodoFragment : Fragment() {
    private lateinit var viewModel: DetailTodoViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_create_todo, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this).get(DetailTodoViewModel::class.java)

        val uuid = EditTodoFragmentArgs.fromBundle(requireArguments()).uuid

        viewModel.fetch(uuid)
        observeViewModel()

        textJudulTodo.text = "Edit Todo"
        btnAdd.text = "Save Changes"

        btnAdd.setOnClickListener {
            var radio = view.findViewById<RadioButton>(radioGroupPriority.checkedRadioButtonId)
            viewModel.update(
                txtTitle.text.toString(),
                txtNotes.text.toString(),
                radio.tag.toString().toInt(),
                uuid
            )
            Toast.makeText(view.context, "Todo Updated.", Toast.LENGTH_SHORT).show()
            Navigation.findNavController(it).popBackStack()
        }
    }

    fun observeViewModel() {
        viewModel.todoLD.observe(viewLifecycleOwner) {
            txtTitle.setText(it.title)
            txtNotes.setText(it.notes)

            when (it.priority) {
                1 -> radioLow.isChecked = true
                2 -> radioMedium.isChecked = true
                else -> radioHigh.isChecked = true
            }
        }
    }
}