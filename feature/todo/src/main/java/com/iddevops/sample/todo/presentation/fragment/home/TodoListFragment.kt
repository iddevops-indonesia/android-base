package com.iddevops.sample.todo.presentation.fragment.home

import android.annotation.SuppressLint
import com.iddevops.data.DataObserver
import com.iddevops.presentation.fragment.BaseFragment
import com.iddevops.sample.todo.databinding.FragmentTodoListBinding
import com.iddevops.sample.todo.domain.model.TodoData
import com.iddevops.utils.view.gone
import com.iddevops.utils.view.visible
import com.iddevops.utils.worker.wait
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.lang.StringBuilder

class TodoListFragment : BaseFragment<FragmentTodoListBinding>(),
    DataObserver<List<TodoData>> {

    private val vm: TodoListViewModel by viewModel()

    override fun initAction() {
        super.initAction()
        with(binding) {
            btnOne.setOnClickListener {
                btnOne.text = "Ouch!!"
                val id = tvInput.text.toString()

                if (id.isBlank()){
                    tvContent.text = "Please enter user id\nTry 1"
                    return@setOnClickListener
                }

                vm.getTodos(id)
                wait(300) {
                    btnOne.text = "Refresh"
                }
            }
        }
    }

    @SuppressLint("SetTextI18n")
    override suspend fun initObserver() {
        super.initObserver()
        vm.todos.observe(this)
    }

    override fun onRequestDefault() {
        super.onRequestDefault()
        with(binding) {
            pbOne.gone()
        }
    }
    override fun onRequestLoading() {
        super.onRequestLoading()
        with(binding) {
            pbOne.visible()
            tvContent.text = ""
        }
    }

    override fun onRequestSuccess(data: List<TodoData>?) {
        super.onRequestSuccess(data)

        val sb = StringBuilder()

        data?.forEach {
            sb.append(it.toString())
            sb.append("\n\n")
        }

        with(binding) {
            tvContent.text = sb.toString()
            pbOne.gone()
        }
    }

    override fun onRequestEmpty() {
        super.onRequestEmpty()

        with(binding) {
            tvContent.text = "No data found"
            pbOne.gone()
        }
    }

    @SuppressLint("SetTextI18n")
    override fun onRequestFailure(message: String) {
        super.onRequestFailure(message)
        with(binding) {
            pbOne.gone()
            tvContent.text = "Failed : $message"
        }
    }
}