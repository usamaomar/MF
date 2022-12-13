package com.example.myapplication.myapplication.usamas.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.*
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import coil.ImageLoader
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import com.example.myapplication.myapplication.usamas.data.models.SearchResult
import com.example.myapplication.myapplication.usamas.ui.compose.CompleteDialogContent
import com.example.myapplication.myapplication.usamas.utils.collectAsStateWithLifecycle
import dagger.hilt.android.AndroidEntryPoint


@OptIn(ExperimentalMaterial3Api::class)
@AndroidEntryPoint
class HomeFragment : Fragment() {

    private val viewModel by viewModels<MainActivityViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = ComposeView(requireContext()).apply {
        setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
        setContent {
            val uiState by viewModel.uiState.collectAsStateWithLifecycle()
            var text by remember { mutableStateOf("") }
            var byCountry by remember { mutableStateOf(false) }
            var byCCategories by remember { mutableStateOf(false) }
            Scaffold(
                topBar = {
                    TextField(
                        value = text,
                        onValueChange = { value ->
                            text = value
                            viewModel.getSearchValues(value)
                        },
                        modifier = Modifier
                            .background(Color.White)
                            .fillMaxWidth()
                            .height(55.dp),
                    )
                }
            ) { contentPadding ->
                if (byCountry) {
                    SortByCountry(
                        { dialogState -> byCountry = dialogState },
                        { selectedValue ->
                            byCountry = false
                            viewModel.selectedCountry(selectedValue)
                        },
                        viewModel.getCountry()
                    )
                }
                if (byCCategories) {
                    SortByCategories(
                        { dialogState -> byCCategories = dialogState },
                        { selectedValue ->
                            byCCategories = false
                            viewModel.selectedCategories(selectedValue)
                        },
                        viewModel.getCategories()
                    )
                }
                Box(modifier = Modifier.padding(top = contentPadding.calculateTopPadding())) {
                    val modifier = Modifier.fillMaxWidth()
                    Row(
                        modifier = modifier.background(Color.LightGray),
                        horizontalArrangement = Arrangement.SpaceAround
                    ) {
                        ClickableText(
                            modifier = Modifier.padding(16.dp),
                            text = AnnotatedString("By Country"),
                            style = TextStyle(
                                fontWeight = FontWeight.Bold,
                                color = Color.White,
                                fontSize = 15.sp
                            ),
                            onClick = {
                                byCountry = !byCountry
                            }
                        )
                        ClickableText(
                            modifier = Modifier.padding(16.dp),
                            text = AnnotatedString("By Categories"),
                            style = TextStyle(
                                fontWeight = FontWeight.Bold,
                                color = Color.White,
                                fontSize = 15.sp
                            ),
                            onClick = {
                                byCCategories = !byCCategories
                            }
                        )

                    }
                    uiState.isLoading?.let { Loading(it) }
                    uiState.newsList?.let {
                        SearchContentBody(it) { itemSearched ->
                            navigateToDetails(itemSearched)
                        }
                    }
                }
            }
        }
    }

    @Composable
    fun SortByCountry(
        dialogState: (Boolean) -> Unit,
        selectedValue: (Int) -> Unit,
        array: ArrayList<String>
    ) {
        Dialog(
            onDismissRequest = {
            },
            content = {
                CompleteDialogContent("Sort By Country", dialogState, selectedValue, array)
            },
            properties = DialogProperties(
                dismissOnBackPress = true,
                dismissOnClickOutside = true
            )
        )
    }


    @Composable
    private fun SortByCategories(
        dialogState: (Boolean) -> Unit,
        selectedValue: (Int) -> Unit,
        array: ArrayList<String>
    ) {
        Dialog(
            onDismissRequest = {
            },
            content = {
                CompleteDialogContent("Sort By Categories", dialogState, selectedValue, array)
            },
            properties = DialogProperties(
                dismissOnBackPress = true,
                dismissOnClickOutside = true
            )
        )
    }

    private fun navigateToDetails(searchResult: SearchResult) {
        val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(searchResult.url))
        startActivity(browserIntent)
    }


    @Composable
    private fun Loading(isLoading: Boolean) {
        val strokeWidth = 5.dp
        AnimatedVisibility(visible = isLoading) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                CircularProgressIndicator(
                    modifier = Modifier.drawBehind {
                        drawCircle(
                            Color.Blue,
                            radius = size.width / 2 - strokeWidth.toPx() / 2,
                            style = Stroke(strokeWidth.toPx())
                        )
                    },
                    color = Color.LightGray,
                    strokeWidth = strokeWidth
                )
            }
        }
    }


    @Composable
    private fun SearchContentBody(array: ArrayList<SearchResult>, onClick: (SearchResult) -> Unit) {
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, end = 16.dp, top = 55.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(
                count = array.size,
            ) { index ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable(
                            onClick = {
                                onClick.invoke(array[index])
                            },
                        ),
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    if (array[index].imageUrl != null) {
                        AsyncImage(
                            model = array[index].imageUrl,
                            contentDescription = null,
                            modifier = Modifier
                                .padding(10.dp)
                                .height(100.dp)
                                .width(100.dp)
                                .clip(RoundedCornerShape(20)),
                            contentScale = ContentScale.Crop,
                        )
                    } else {
                        Box(
                            modifier = Modifier
                                .padding(10.dp)
                                .height(100.dp)
                                .width(100.dp)
                                .clip(RoundedCornerShape(20)),
                        )
                    }
                    Column() {
                        array[index].title?.let {
                            Text(
                                text = it,
                                color = Color.Black,
                                fontSize = 15.sp
                            )
                        }
                        array[index].author?.let {
                            Text(
                                text = it,
                                color = Color.Black,
                                fontSize = 12.sp
                            )
                        }
                        array[index].postDate?.let {
                            Text(
                                text = it,
                                color = Color.Black,
                                fontSize = 10.sp
                            )
                        }
                    }
                }

            }
        }
    }
}