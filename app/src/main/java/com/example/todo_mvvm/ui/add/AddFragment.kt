package com.example.todo_mvvm.ui.add

import android.app.AlertDialog
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.todo_mvvm.R
import com.example.todo_mvvm.databinding.FragmentAddBinding
import com.example.todo_mvvm.db.TodoModel
import com.example.todo_mvvm.viewmodel.TodoViewModel
import com.example.todo_mvvm.viewmodel.ViewModelFactory
import org.json.JSONArray
import org.json.JSONException

class AddFragment: Fragment() {

    private lateinit var binding: FragmentAddBinding
    private var dropdownItems = ArrayList<String>()
    private lateinit var sharedPref: SharedPreferences
//    private lateinit var todoViewModel: TodoViewModel
    private val todoViewModel: TodoViewModel by activityViewModels { ViewModelFactory(requireContext()) }

    override fun onCreateView(
        // 여기서 pref저장한 값 초기화
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_add, container, false)
//        todoViewModel = ViewModelProvider(this, ViewModelFactory(requireContext()))[TodoViewModel::class.java]
        binding.viewModel = todoViewModel
        binding.todoModel = TodoModel("todo","todo","todo","2021","2022", false)
//        TodoModel(
//            binding.todoInputEditText.text.toString(),
//            binding.descriptionInputEditText.text.toString(),
//            binding.folderAutoTextView.text.toString(),
//            binding.startDayInputEditText.text.toString(),
//            binding.endDayInputEditText.text.toString()
//        )

        sharedPref = requireActivity().getSharedPreferences("dropdown_prefs",0)
        loadDropdownMenu()

        binding.calendarButton.setOnClickListener {
            showBottomSheetDialog()
        }

        binding.addFolder.setOnClickListener {
            addFolder()
        }

        todoViewModel.addTodoEvent.observe(viewLifecycleOwner) {
            it.getContentIfNotHandled()?.let {
                Toast.makeText(requireContext(), "Todo추가 완료!",Toast.LENGTH_SHORT).show()
                binding.todoInputEditText.setText("")
                binding.descriptionInputEditText.setText("")
                binding.startDayInputEditText.setText("")
                binding.endDayInputEditText.setText("")
                binding.folderAutoTextView.text.clear()
            }
        }
        todoViewModel.addErrorTodoEvent.observe(viewLifecycleOwner){
            it.getContentIfNotHandled()?.let {
                Toast.makeText(requireContext(), "모든 칸을 입력해주세요!", Toast.LENGTH_SHORT).show()
            }
        }

        return binding.root
    }

    private fun showBottomSheetDialog() {

        val bottomSheetDialog = BottomSheetDialogFragment()
        bottomSheetDialog.show(parentFragmentManager, "bottomSheetDialog")

        bottomSheetDialog.setonDateSelectListener(object :
            BottomSheetDialogFragment.BottomSheetFragmentInterface {
            override fun onButtonClick(startDay: String, endDay: String) {
                binding.startDayInputEditText.setText(startDay)
                binding.endDayInputEditText.setText(endDay)
            }
        })

    }

    private fun addFolder() {
        val dialogView = LayoutInflater.from(requireContext()).inflate(R.layout.dialog_add_folder, null)
        val folderDialog = AlertDialog.Builder(requireContext(), R.style.CustomAlertDialog)
            .setView(dialogView)
            .setCancelable(false)
            .create()
        val editTextView = dialogView.findViewById<EditText>(R.id.folderEditText)
        val addButton = dialogView.findViewById<Button>(R.id.addButton)
        val cancelButton = dialogView.findViewById<Button>(R.id.cancelButton)

        addButton.setOnClickListener {
            // dropdown menu를 관리하는 list에 추가하고 dropdown menu를 다시 셋팅
            val newFolder = editTextView?.text.toString()
//            binding.folderAutoTextView.setText(newFolder)
            dropdownItems.add(newFolder)
            folderDialog.dismiss()
            setupDropdown()
        }

        cancelButton.setOnClickListener {
            folderDialog.dismiss()
        }

        folderDialog.show()
    }

    private fun setupDropdown() {
        val dropdownAdapter = ArrayAdapter<String>(requireContext(),
            R.layout.item_dropdown, dropdownItems)
        binding.folderAutoTextView.setAdapter(dropdownAdapter)
        binding.folderAutoTextView.setOnItemClickListener { parent, view, position, id ->
//            val clickItem = parent.getItemAtPosition(position).toString()
            binding.folderAutoTextView.clearFocus()
        }
    }

    private fun saveDropdownMenu() {
        val jsonArray = JSONArray()
        for (dropdownItem in dropdownItems) {
            jsonArray.put(dropdownItem)
        }
        val result = jsonArray.toString()
        sharedPref.edit().apply {
            putString("dropdownItems",result)
            apply()
        }
    }

    private fun loadDropdownMenu() {
        val getShared = sharedPref.getString("dropdownItems",null)
        if (getShared != null) {
            try {
                val jsonArray = JSONArray(getShared)
                for (i in 0 until jsonArray.length()) {
                    dropdownItems.add(jsonArray.optString(i))
                }
            } catch (e: JSONException) {
                e.printStackTrace()
            }
        }
        setupDropdown()
    }

    override fun onStop() {
        super.onStop()
        saveDropdownMenu()
    }
    override fun onStart() {
        super.onStart()
        // AutoTextView에 데이터가 남아있는 상태에서 프래그먼트를 전환하면 해당 프래그먼트로 돌아올 경우
        // 남아있는 데이터와 일치하는 드롭다운 메뉴만 보여지게됨 따라서, AutoTextView의 text를 clear시킨다.
        // -> 원래는 onCreateView에서 진행했지만, 제대로 clear되지 않아서 onStart에서 진행
        binding.folderAutoTextView.text.clear()
    }


}

// todo ERROR Folder textview에 값이 셋팅된 상태에서 네비게이션을 통해 화면을 이동하면 세팅된 값만 드롭다운 메뉴에 나옴
// bottomNavigation 뒤로가기를 눌렀을경우와 바텀네비게이션을 통해 이동할 경우의 차이점
// todo 목표는 Folder TextView를 clear시켜야함 -> 이게 잘 안대서 Folder TextView를 invalidate()해서 성공할 수 있는지 해봐야 할듯