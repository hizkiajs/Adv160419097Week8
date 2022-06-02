package id.web.rpgfantasy.todoapp.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import id.web.rpgfantasy.todoapp.R
import id.web.rpgfantasy.todoapp.model.Todo
import kotlinx.android.synthetic.main.todo_item_list.view.*

class TodoListAdapter(val todoList: ArrayList<Todo>, val adapterOnClick: (Todo) -> Unit): RecyclerView.Adapter<TodoListAdapter.TodoViewHolder>() {
    class TodoViewHolder(var view: View): RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.todo_item_list, parent, false)

        return TodoViewHolder(view)
    }

    override fun onBindViewHolder(holder: TodoViewHolder, position: Int) {
        val todo = todoList[position]

        with(holder.view){
            val priority = when(todo.priority){
                1 -> "low"
                2 -> "medium"
                else -> "HIGH"
            }
            checkTask.text = "[${priority}] ${todo.title}"

            checkTask.setOnCheckedChangeListener { compoundButton, value ->
                if (value) adapterOnClick(todo)
            }

            imgEdit.setOnClickListener{
                val action = TodoListFragmentDirections.actionEditTodoFragment(todo.uuid)
                Navigation.findNavController(it).navigate(action)
            }
        }
    }

    override fun getItemCount(): Int {
        return todoList.size
    }

    fun updateTodoList(newTodoList: List<Todo>) {
        todoList.clear()
        todoList.addAll(newTodoList)
        notifyDataSetChanged()
    }
}