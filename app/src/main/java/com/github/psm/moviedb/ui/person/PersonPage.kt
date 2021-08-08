package com.github.psm.moviedb.ui.person

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.github.psm.moviedb.db.model.person.Person
import com.github.psm.moviedb.db.model.person.movie.PersonMovieCredit
import com.github.psm.moviedb.db.model.person.tv.PersonTvCredit
import com.github.psm.moviedb.ui.widget.BaseAppBar
import com.github.psm.moviedb.ui.widget.Image
import com.github.psm.moviedb.ui.widget.InnerScaffold
import com.github.psm.moviedb.utils.ButtonIcons
import com.github.psm.moviedb.utils.toAge
import com.github.psm.moviedb.utils.toImgUrl
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.pagerTabIndicatorOffset
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun PersonPage(
    navigateBack: () -> Unit,
    viewModel: PersonViewModel = hiltViewModel(),
    navigateToMovieDetail: (movieId: Long) -> Unit
) {
    val person by viewModel.person.observeAsState()
    val personTvCredit by viewModel.personTvCredit.observeAsState()
    val personMovieCredit by viewModel.personMovieCredit.observeAsState()

    PersonPageContent(
        person = person,
        navigateBack = navigateBack,
        navigateToMovieDetail = navigateToMovieDetail,
        personTvCredit = personTvCredit,
        personMovieCredit = personMovieCredit
    )
}

@OptIn(ExperimentalPagerApi::class)
@Composable
private fun PersonPageContent(
    person: Person? = null,
    navigateBack: () -> Unit = { },
    navigateToMovieDetail: (movieId: Long) -> Unit = { },
    personTvCredit: PersonTvCredit? = null,
    personMovieCredit: PersonMovieCredit? = null
) {
    val pagerState = rememberPagerState(pageCount = PersonContentPage.PageCount)
    val currentPage = pagerState.currentPage
    val coroutineScope = rememberCoroutineScope()

    val changePage: (index: Int) -> Unit = {
        coroutineScope.launch(Dispatchers.Main) {
            pagerState.scrollToPage(it)
        }
    }

    InnerScaffold(
        modifier = Modifier.fillMaxSize(),
        innerPaddingValues = PaddingValues(8.dp),
        appBar = {
            BaseAppBar(
                startContent = { ButtonIcons.BackButton(navigateBack) },
                title = "Person",
                enableShadow = true
            )
        }
    ) {
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            Card(
                modifier = Modifier
                    .width(150.dp)
                    .aspectRatio(2f / 3),
                shape = RoundedCornerShape(8.dp),
                elevation = 8.dp
            ) {
                Image(
                    modifier = Modifier.fillMaxSize(),
                    data = person?.profilePath?.toImgUrl(),
                    enablePlaceHolder = true,
//                    error = { DefaultError() },
                    contentScale = ContentScale.FillBounds
                )
            }
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(8.dp)
            ) {
                Text(
                    text = person?.name ?: "",
                    style = MaterialTheme.typography.h6,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
                
                Spacer(modifier = Modifier.size(16.dp))

                Text(
                    text = "Birthday",
                    fontWeight = FontWeight.Bold
                )

                person?.birthday?.let {
                    Text(
                        text = "${it}(${it.toAge()} years old)",
                        fontSize = 14.sp
                    )
                }

                Text(
                    text = "Gender",
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = person?.genderString ?: "",
                    fontSize = 14.sp
                )
            }
        }

        Spacer(modifier = Modifier.size(8.dp))

        TabRow(
            backgroundColor = MaterialTheme.colors.surface,
            selectedTabIndex = PersonContentPage.Biography,
            indicator = { tabPositions ->
                TabRowDefaults.Indicator(
                    modifier = Modifier.pagerTabIndicatorOffset(pagerState, tabPositions)
                )
            }
        ) {

            PersonContentPage.title.forEachIndexed { index, title ->
                Tab(
                    selected = currentPage == index,
                    onClick = { changePage(index) }
                ) {
                    Text(
                        modifier = Modifier.padding(
                            start = 16.dp,
                            end = 16.dp,
                            top = 12.dp,
                            bottom = 12.dp
                        ),
                        text = title
                    )
                }
            }
        }

        HorizontalPager(state = pagerState) { page ->
            when (page) {
                PersonContentPage.Biography -> BiographyPage(person?.biography ?: "")
                PersonContentPage.Movie -> MovieActingHistory(
                    personMovieCredit = personMovieCredit,
                    onMovieClick = navigateToMovieDetail
                )
                PersonContentPage.Tv -> TvActingHistory(personTvCredit)
            }
        }

    }
}


@Preview(showBackground = true)
@Composable
fun PersonPagePreview() {
    Scaffold {
        PersonPageContent(
            person = Person(
                name = "Name"
            )
        )
    }
}