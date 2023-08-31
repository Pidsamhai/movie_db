package com.github.psm.moviedb.ui.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.github.psm.moviedb.R
import com.github.psm.moviedb.ui.theme.Stared
import com.github.psm.moviedb.ui.widget.DetailAppBar
import com.github.psm.moviedb.ui.widget.Gauge
import com.valentinilk.shimmer.shimmer

@Composable
fun DetailContentShimmer(
    appBarTitle: String,
) {
    val scrollState = rememberScrollState()
    val genreScrollState = rememberScrollState()

    Column(
        modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally
    ) {

        DetailAppBar(
            title = appBarTitle,
            scrollState = scrollState,
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .verticalScroll(scrollState),
            horizontalAlignment = Alignment.End,
            verticalArrangement = Arrangement.Top
        ) {

            Box(
                modifier = Modifier.aspectRatio(16f / 9), contentAlignment = Alignment.BottomStart
            ) {

                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .shimmer(),
                ) {

                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(Color.Gray)
                    )
                }

                Card(
                    modifier = Modifier
                        .padding(8.dp)
                        .height(150.dp)
                        .aspectRatio(2f / 3),
                    elevation = 8.dp,
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .shimmer(),
                    ) {

                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(Color.Gray)
                        )
                    }
                }

            }

            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
                    .clip(RoundedCornerShape(topStart = 8.dp, topEnd = 8.dp)),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start
            ) {
                Gauge(
                    Modifier
                        .size(60.dp)
                        .padding(4.dp)
                        .clip(CircleShape),
                    value = 0f,
                    bgColor = Color.Black,
                    strokeSize = 8f,
                    fontSize = 12.sp,
                    animated = true
                )
                Column(
                    modifier = Modifier.padding(start = 8.dp), horizontalAlignment = Alignment.Start
                ) {
                    Box(
                        modifier = Modifier
                            .padding(horizontal = 8.dp)
                            .height(16.dp)
                            .fillMaxWidth()
                            .clip(CircleShape)
                            .shimmer(),
                    ) {

                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(Color.Gray)
                        )
                    }

                    Row(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .padding(start = 8.dp)
                                .height(16.dp)
                                .width(52.dp)
                                .clip(CircleShape)
                                .shimmer(),
                        ) {

                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .background(Color.Gray)
                            )
                        }

                        Icon(
                            imageVector = Icons.Default.Star,
                            contentDescription = null,
                            tint = Stared
                        )
                    }
                }
            }


            Row(
                modifier = Modifier.padding(start = 8.dp, end = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    modifier = Modifier.size(14.dp),
                    painter = painterResource(id = R.drawable.ic_round_access_time),
                    contentDescription = null
                )

                Spacer(modifier = Modifier.size(8.dp))

                Box(
                    modifier = Modifier
                        .padding(horizontal = 8.dp)
                        .height(16.dp)
                        .width(52.dp)
                        .clip(CircleShape)
                        .shimmer(),
                ) {

                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(Color.Gray)
                    )
                }
            }
        }

        Spacer(modifier = Modifier.size(8.dp))

        /**
         * Genre
         */

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 8.dp, end = 8.dp)
                .horizontalScroll(genreScrollState),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            arrayOfNulls<Int>(10).forEach { _ ->

                Box(
                    modifier = Modifier
                        .padding(horizontal = 8.dp)
                        .height(32.dp)
                        .width(64.dp)
                        .clip(CircleShape)
                        .shimmer(),
                ) {

                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(Color.Gray)
                    )
                }
            }
        }


        Box(
            modifier = Modifier
                .padding(horizontal = 8.dp, vertical = 16.dp)
                .height(32.dp)
                .fillMaxWidth(fraction = 0.3f)
                .align(Alignment.Start)
                .clip(CircleShape)
                .shimmer(),
        ) {

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Gray)
            )
        }

        Box(
            modifier = Modifier
                .padding(horizontal = 8.dp)
                .height(16.dp)
                .fillMaxWidth()
                .clip(CircleShape)
                .shimmer(),
        ) {

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Gray)
            )
        }

        Spacer(modifier = Modifier.height(4.dp))

        Box(
            modifier = Modifier
                .padding(horizontal = 8.dp)
                .height(16.dp)
                .fillMaxWidth()
                .clip(CircleShape)
                .shimmer(),
        ) {

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Gray)
            )
        }

        Spacer(modifier = Modifier.height(4.dp))

        Box(
            modifier = Modifier
                .padding(horizontal = 8.dp)
                .height(16.dp)
                .fillMaxWidth()
                .clip(CircleShape)
                .shimmer(),
        ) {

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Gray)
            )
        }

        Spacer(modifier = Modifier.height(4.dp))

        Box(
            modifier = Modifier
                .padding(horizontal = 8.dp)
                .height(16.dp)
                .fillMaxWidth()
                .clip(CircleShape)
                .shimmer(),
        ) {

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Gray)
            )
        }

        Spacer(modifier = Modifier.height(4.dp))

        Box(
            modifier = Modifier
                .padding(horizontal = 8.dp)
                .height(16.dp)
                .fillMaxWidth()
                .clip(CircleShape)
                .shimmer(),
        ) {

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Gray)
            )
        }

        Spacer(modifier = Modifier.height(4.dp))

        Box(
            modifier = Modifier
                .padding(horizontal = 8.dp)
                .height(16.dp)
                .fillMaxWidth(fraction = 0.5f)
                .align(Alignment.Start)
                .clip(CircleShape)
                .shimmer(),
        ) {

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Gray)
            )
        }

        Spacer(modifier = Modifier.height(4.dp))

    }
}


@Composable
@Preview(showBackground = true)
private fun DetailShimmerPreview() {
    Scaffold {
        Column(
            modifier = Modifier
                .padding(it)
                .fillMaxSize()
        ) {
            DetailContentShimmer(
                appBarTitle = "Tv",
            )
        }
    }
}