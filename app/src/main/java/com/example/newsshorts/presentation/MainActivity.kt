package com.example.newsshorts.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.PageSize
import androidx.compose.foundation.pager.VerticalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.example.newsshorts.common.Resource
import com.example.newsshorts.domain.model.ArticleData
import com.example.newsshorts.ui.theme.NewsShortsTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()
        setContent {
            NewsShortsTheme {
                HomeScreen()
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(newsViewModel: NewsAppViewModel = hiltViewModel()) {
    val newsResponse by newsViewModel.newsState.collectAsState()
    val pagerState = rememberPagerState(
        initialPage = 0,
        initialPageOffsetFraction = 0f
    ) {
        val response = (newsResponse as? Resource.Success)?.data
        (response?.articles?.size ?: 0) + 1
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("News Shorts", style = MaterialTheme.typography.headlineSmall) },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                )
            )
        }
    ) { innerPadding ->
        VerticalPager(
            state = pagerState,
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            pageSize = PageSize.Fill,
            pageSpacing = 8.dp
        ) { page ->
            when (newsResponse) {
                is Resource.Loading -> Loading()
                is Resource.Success -> {
                    val response = (newsResponse as Resource.Success).data
                    if (response != null) {
                        if (page < response.articles.size) {
                            NewsRowComponent(page, response.articles[page])
                        } else {
                            EndOfNewsComponent { newsViewModel.getNews() }
                        }
                    }
                }

                is Resource.Error -> ErrorComponent()
            }
        }
    }
}

@Composable
fun Loading() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        CircularProgressIndicator(
            modifier = Modifier
                .size(50.dp)
                .padding(10.dp)
        )
    }
}

@Composable
fun EndOfNewsComponent(onFetchMoreNews: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .background(MaterialTheme.colorScheme.background),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "No more news available",
            style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold)
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = onFetchMoreNews) {
            Text(text = "Fetch More News")
        }
    }
}

@Composable
fun ErrorComponent() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .background(MaterialTheme.colorScheme.background),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "An error occurred. Please try again later.",
            style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold)
        )
    }
}

@Composable
fun NewsRowComponent(page: Int, articleData: ArticleData) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .padding(8.dp),
        shape = MaterialTheme.shapes.medium,
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
        ) {
            AsyncImage(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(240.dp),
                model = articleData.urlToImage,
                contentDescription = "",
                contentScale = ContentScale.Crop
            )
            Column(modifier = Modifier.padding(8.dp)) {
                Column(modifier = Modifier.padding(8.dp)) {
                    Text(
                        text = articleData.title ?: "",
                        style = MaterialTheme.typography.headlineSmall, // Headline style
                        fontWeight = FontWeight.Bold, // Bold for emphasis
                        color = MaterialTheme.colorScheme.onSurface, // Ensure contrast
                        modifier = Modifier.padding(bottom = 16.dp)
                    )

//                    Text(
//                        text = articleData.description ?: "",
//                        style = MaterialTheme.typography.bodyLarge, // Body for main content
//                        color = MaterialTheme.colorScheme.onSurfaceVariant, // Slightly lighter
//                        maxLines = 3, // Limit description lines for cleaner layout
//                        overflow = TextOverflow.Ellipsis // Add ellipsis (...) if text overflows
//                    )

//                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        text = articleData.content ?: "",
                        style = MaterialTheme.typography.bodyLarge, // Smaller body for extra details
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
//                        maxLines = 3, // Limit content lines
//                        overflow = TextOverflow.Ellipsis
                    )
                }
            }
        }
    }


}



