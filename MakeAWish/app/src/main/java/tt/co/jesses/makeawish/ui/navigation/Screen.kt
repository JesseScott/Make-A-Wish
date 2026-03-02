package tt.co.jesses.makeawish.ui.navigation

import java.io.Serializable

enum class Screen(val route: String) : Serializable {
    MAIN("main"),
    SETTINGS("settings"),
    NOTIFICATION("notification");

    companion object {
        fun fromRoute(route: String?): Screen? {
            return entries.find { it.route == route }
        }
    }
}
