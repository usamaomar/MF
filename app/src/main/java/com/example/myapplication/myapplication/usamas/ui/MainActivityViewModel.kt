package com.example.myapplication.myapplication.usamas.ui

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.myapplication.usamas.data.models.RequestNewsIo
import com.example.myapplication.myapplication.usamas.data.models.RequestNewsOrg
import com.example.myapplication.myapplication.usamas.data.models.SearchResult
import com.example.myapplication.myapplication.usamas.domain.use_cases.business.GetDataUseCase
import com.example.myapplication.myapplication.usamas.domain.use_cases.business.GetNewsUseCase
import com.example.myapplication.myapplication.usamas.network.Resource
import com.example.myapplication.myapplication.usamas.utils.DateUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class MainActivityViewModel @Inject constructor(
    private val getNewsUseCase: GetNewsUseCase,
    private val getDataUseCase: GetDataUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow(LatestNewsUiState(ArrayList()))
    val uiState: StateFlow<LatestNewsUiState> = _uiState
    var list: ArrayList<SearchResult> = ArrayList()

    fun getSearchValues(query: String) {
        list.clear()
        viewModelScope.launch {
            loadSearchValues(query).debounce(800).collectLatest { resource ->
                resource.data?.let { runss(it) }
            }
        }
    }

    fun getCountry(): ArrayList<String> {
        val arrayList: ArrayList<String> = ArrayList()
        arrayList.add("united states of america")
        arrayList.add("united arab emirates")
        arrayList.add("jordan")
        return arrayList
    }

    fun getCategories(): ArrayList<String> {
        val arrayList: ArrayList<String> = ArrayList()
        arrayList.add("sports")
        arrayList.add("business")
        arrayList.add("health")
        return arrayList
    }


    fun match(lookUpName: String): ArrayList<SearchResult> {
        val arrayList = list.filter { searchResult ->
            searchResult.country?.isNotEmpty() == true && (searchResult.country.any { it == lookUpName })
        }
        return ArrayList(arrayList)
    }

    fun selectedCountry(index: Int) {
        try {
            if (index == 0) {
                _uiState.update { it.copy(newsList = match("united states of america")) }
            } else if (index == 1) {
                _uiState.update { it.copy(newsList = match("united arab emirates")) }
            } else if (index == 2) {
                _uiState.update { it.copy(newsList = match("jordan")) }
            }
        } catch (ex: Exception) {
            ex.message
        }
    }

    fun selectedCategories(index: Int) {
        try {
            if (index == 0) {
                _uiState.update { it.copy(newsList = match("sports")) }
            } else if (index == 1) {
                _uiState.update { it.copy(newsList = match("business")) }
            } else if (index == 2) {
                _uiState.update { it.copy(newsList = match("health")) }
            }
        } catch (ex: Exception) {
            ex.message
        }
    }

    fun runss(list: ArrayList<SearchResult>) {
        if (list.size == 0) {
            _uiState.value = LatestNewsUiState(isLoading = true)
        } else {
            _uiState.value = LatestNewsUiState(newsList = list)
        }
    }

    private fun loadSearchValues(query: String): Flow<Resource<ArrayList<SearchResult>>> =
        getDataUseCase.execute(
            RequestNewsIo(
                query = query, apiKey = "pub_145258bbd4d6e1670580462cbe56006120563"
            )
        ).combine(
            getNewsUseCase.execute(
                RequestNewsOrg(
                    query = query,
                    from = "2022-12-12",
                    sortBy = "publishedAt",
                    apiKey = "4b097d4fd1514f05814ccc9c41af9c27"
                )
            )
        ) { newsIoList, newsOrgList ->
            val list1 = newsIoList.data?.results
            list1?.forEach { newsDataModel ->
                list.add(
                    SearchResult(
                        type = "IO",
                        title = newsDataModel.title,
                        imageUrl = newsDataModel.imageUrl,
                        author = newsDataModel.author,
                        postDate = DateUtils.dateIoFormat(newsDataModel.pupDate),
                        url = newsDataModel.link,
                        country = newsDataModel.country,
                        category = newsDataModel.category
                    )
                )
            }
            val list2 = newsOrgList.data?.articles
            list2?.forEach { newsData ->
                list.add(
                    SearchResult(
                        type = "ORG",
                        title = newsData.title,
                        imageUrl = newsData.urlToImage,
                        author = newsData.author,
                        postDate = DateUtils.dateOrgFormat(newsData.publishedAt),
                        url = newsData.url
                    )
                )
            }
            return@combine Resource.Success(list)
        }.flowOn(Dispatchers.IO).catch {
            println()
        }
}

data class LatestNewsUiState(
    val newsList: ArrayList<SearchResult>? = ArrayList(),
    val isLoading: Boolean? = false,
    val dialogState: MutableState<Boolean>? = mutableStateOf(false),
)