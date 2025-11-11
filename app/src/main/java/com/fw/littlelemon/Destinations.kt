package com.fw.littlelemon

interface Destinations {
    val route: String
}

object Home : Destinations {
    override val route: String = "home"
}

object Profile : Destinations {
    override val route: String = "profile"
}

object Onboarding : Destinations {
    override val route: String = "onboarding"
}