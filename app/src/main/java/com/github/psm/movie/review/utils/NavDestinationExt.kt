package com.github.psm.movie.review.utils

import androidx.navigation.NavDestination

val NavDestination.rawRoute: String?
get() {
    val routeList = this.route?.split("/")
    if (routeList?.size == 0) return null
    if (routeList == null) return null
    return routeList[0]
}