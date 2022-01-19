package com.iddevops.sample.presentation.home

import android.annotation.SuppressLint
import com.iddevops.data.DataObserver
import com.iddevops.presentation.fragment.BaseFragment
import com.iddevops.sample.databinding.FragmentHomeBinding
import com.iddevops.sample.domain.model.TodoData
import com.iddevops.utils.view.gone
import com.iddevops.utils.view.visible
import com.iddevops.utils.worker.wait
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.lang.StringBuilder

class HomeFragment : BaseFragment<FragmentHomeBinding>(),
    DataObserver<List<TodoData>> {

    private val vm: HomeViewModel by viewModel()

    override fun initData() {
        super.initData()
        vm.getTodos("1")
    }

    override fun initAction() {
        super.initAction()
        with(binding) {
            btnOne.setOnClickListener {
                btnOne.text = "Ouch!!"
                vm.getTodos("1")
                wait(300) {
                    btnOne.text = "Touch me"
                }
            }
        }
    }

    @SuppressLint("SetTextI18n")
    override suspend fun initObserver() {
        super.initObserver()
        vm.todos.observe(this)
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