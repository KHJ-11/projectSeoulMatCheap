package kr.co.mapo.project_seoulmatcheap.ui.fragment

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import android.opengl.Visibility
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.*
import android.view.inputmethod.EditorInfo
import androidx.fragment.app.Fragment
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.skydoves.balloon.balloon
import kr.co.mapo.project_seoulmatcheap.R
import kr.co.mapo.project_seoulmatcheap.databinding.FragmentSearch0101Binding
import kr.co.mapo.project_seoulmatcheap.system.SEARCH_HISTROY
import kr.co.mapo.project_seoulmatcheap.system.SeoulMatCheap
import kr.co.mapo.project_seoulmatcheap.ui.adpater.AutoCompleteAdapter
import kr.co.mapo.project_seoulmatcheap.ui.adpater.SearchHistoryAdapter

//검색성공
class SEARCH_01_01(
    private val owner : AppCompatActivity,
    private val word : String
    ) : Fragment() {

    companion object {
        fun newInstance(owner: AppCompatActivity, word: String) : Fragment {
            return SEARCH_01_01(owner, word)
        }
    }

    private lateinit var binding : FragmentSearch0101Binding
    private lateinit var preferences : SharedPreferences
    private lateinit var filterAdapter : AutoCompleteAdapter
    private lateinit var searchHistoryAdapter: SearchHistoryAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSearch0101Binding.inflate(inflater, container, false)
        binding.searchEditText.setText(word)
        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    private fun init() {
        owner.setSupportActionBar(binding.toolbar)
        val test = arrayListOf("자동", "자동완성", "자동완성테스트", "자동완성테스트1", "자동완성테스트2", "자동완성테스트3", "완성", "테스트")
        filterAdapter = AutoCompleteAdapter(test, owner)
        preferences = owner.getSharedPreferences(SEARCH_HISTROY, Application.MODE_PRIVATE)
        searchHistoryAdapter = SearchHistoryAdapter(preferences.all.values.toMutableList(), owner)
        setView()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        menu.clear()
        inflater.inflate(R.menu.menu_search, menu)
    }

    private fun setView() {
        with(owner.supportActionBar) {
            this!!.setDisplayHomeAsUpEnabled(true)
            this.setHomeAsUpIndicator(R.drawable.ic_back_icon)
        }
        with(binding) {
            autoCompleteRecyclerView.apply {
                layoutManager = LinearLayoutManager(owner, LinearLayoutManager.VERTICAL, false)
                adapter = filterAdapter
                setOnTouchListener { v, event ->
                    val imm = owner.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                    imm.hideSoftInputFromWindow(owner.currentFocus?.windowToken, 0)
                    autoCompleteRecyclerView.visibility = View.GONE
                    searchEditText.clearFocus()
                    return@setOnTouchListener true
                }
            }
            searchEditText.apply {
                //텍스트입력 이벤트
                addTextChangedListener(object : TextWatcher {
                    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                    }
                    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                        with(autoCompleteRecyclerView) {
                            if(!s.isNullOrEmpty()) {
                                filterAdapter.filter.filter(s)
                                visibility = View.VISIBLE
                                Log.e("[자동완성 어댑터]", "${filterAdapter.itemCount}")
                            } else {
                                visibility = View.GONE
                            }
                        }
                    }
                    override fun afterTextChanged(s: Editable?) {
                    }
                })
                //포커스 이벤트
                setOnFocusChangeListener { v, hasFocus ->
                    filterAdapter.filter.filter(searchEditText.text.toString().trim())
                    binding.autoCompleteRecyclerView.visibility = if(hasFocus) View.VISIBLE else View.GONE
                }
                //엔터키 이벤트
                setOnEditorActionListener { v, actionId, event ->
                    if(searchEditText.text?.isNotEmpty() == true) {
                        SeoulMatCheap.getInstance().showToast(owner, "검색요청")
                        if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                            goFail(searchEditText.text.toString().trim())
                        } else { //그냥 엔터 쳤을 때
                            goFail(searchEditText.text.toString().trim())
                        }
                    } else SeoulMatCheap.getInstance().showToast(owner, "검색어를 입력해주세요")
                    return@setOnEditorActionListener false
                }
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            android.R.id.home -> {
                owner.supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.container, SEARCH_01.newInstance(owner))
                    .commit()
            }
            R.id.search -> {
                SeoulMatCheap.getInstance().showToast(owner, "검색 버튼 누름")
                if (binding.searchEditText.text?.isNotEmpty() == true) goSearch(binding.searchEditText.text.toString().trim())
                else SeoulMatCheap.getInstance().showToast(owner, "검색어를 입력해주세요")
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun savePreference(word : String) {
        val edit = preferences.edit()
        edit.putString(word, word).apply()
    }

    fun goSearch(word: String) {
        savePreference(word)
        owner.supportFragmentManager
            .beginTransaction()
            .replace(R.id.container, SEARCH_01_01.newInstance(owner, word))
            .commit()
    }

    fun goFail(word: String) {
        savePreference(word)
        owner.supportFragmentManager
            .beginTransaction()
            .replace(R.id.container, SEARCH_01_02.newInstance(owner, word))
            .commit()
    }

}