package com.octo.tvtracker

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform